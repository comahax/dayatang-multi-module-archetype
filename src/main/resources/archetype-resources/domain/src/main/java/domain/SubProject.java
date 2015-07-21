#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static ${package}.utils.DocumentTagConstans.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ${package}.TwoDatasNotParallelException;
import ${package}.commons.BigDecimalUtils;
import ${package}.query.SubProjectQuery;

/**
 * 单点工程
 * 
 * @author zjzhai
 * 
 */
@Entity
@DiscriminatorValue("SUB_PROJECT")
public class SubProject extends ProjectElement {

	private static final long serialVersionUID = -4488030649156153482L;

	/**
	 * 单点工程所属的项目
	 */
	@ManyToOne
	@JoinColumn(name = "subproject_project_id")
	private Project project;

	/**
	 * 关联的单项合同
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "single_contract_to_subprojects", joinColumns = @JoinColumn(name = "subproject_id"), inverseJoinColumns = @JoinColumn(name = "contract_id"))
	private SingleContract singleContract;

	/**
	 * 包含专业
	 */
	@OneToMany(mappedBy = "subProject")
	private Set<SpecialtyProject> specialtyProjects = new HashSet<SpecialtyProject>();

	/**
	 * 接入点类型
	 */
	@Column(name = "ap_type")
	private String apType;

	/**
	 * 合同金额
	 */
	@Column(name = "subproject_contractamount")
	private BigDecimal contractAmount;

	/**
	 * 工程地址
	 */
	private String address;

	/**
	 * 监理单位 信息
	 */
	@Embedded
	@AssociationOverrides({ @AssociationOverride(name = "organization", joinColumns = @JoinColumn(name = "supervisor_org_id")),
			@AssociationOverride(name = "person", joinColumns = @JoinColumn(name = "supervisor_contact_id")) })
	private OrganizationInfo supervisorInfo = new OrganizationInfo();

	/**
	 * 设计单位 信息
	 */
	@Embedded
	@AssociationOverrides({ @AssociationOverride(name = "organization", joinColumns = @JoinColumn(name = "design_org_id")),
			@AssociationOverride(name = "person", joinColumns = @JoinColumn(name = "design_contact_id")) })
	private OrganizationInfo designInfo = new OrganizationInfo();

	public SubProject() {
		super();
	}

	public SubProject(String name) {
		this();
		setName(name);
	}

	/**
	 * 同时设置一批单点的合同金额
	 * 
	 * @param subProjectIds
	 * @param amounts
	 */
	public static void setContractAmount(List<Long> subProjectIds, List<BigDecimal> amounts, InternalOrganization scope) {
		if (null == subProjectIds || subProjectIds.isEmpty() || null == amounts || amounts.isEmpty()
				|| (subProjectIds.size() != amounts.size())) {
			throw new TwoDatasNotParallelException();
		}
		for (int i = 0; i < subProjectIds.size(); i++) {
			long subProjectId = subProjectIds.get(i);
			SubProject subProject = SubProjectQuery.createResponsibleOf(scope).id(subProjectId).getSingleResult();
			if (null == subProject) {
				continue;
			}
			BigDecimal amount = amounts.get(i);
			if (amount == null || BigDecimalUtils.eqZero(amount)) {
				continue;
			}
			subProject.setContractAmount(amount);
			subProject.save();
		}
	}

	/**
	 * 已经上了合同
	 * 
	 * @return
	 */
	public boolean hadSingleContract() {
		return getSingleContract() != null;
	}

	/**
	 * 取消与单项合同的关联
	 */
	public void cancelAssosiateToSingleContract() {
		setSingleContract(null);
		setContractAmount(null);
		save();
	}

	public SubProject addSpecialtyProjects(Set<SpecialtyProject> specialtyProjects) {
		if (null == specialtyProjects) {
			return this;
		}
		for (SpecialtyProject each : specialtyProjects) {
			addSpecialtyProject(each);
		}
		return this;
	}

	public SubProject addSpecialtyProject(SpecialtyProject specialtyProject) {
		if (getSpecialties().contains(specialtyProject.getSpecialty())) {
			return this;
		}
		specialtyProject.setResponsibleDivision(getResponsibleDivision());
		specialtyProject.setSubProject(this);
		specialtyProject.save();
		project.addSpecialty(specialtyProject.getSpecialty());
		return this;
	}

	public BigDecimal getTotalOutputValue() {
		BigDecimal result = BigDecimal.ZERO;
		result = result.add(OutputValue.totalOutputValueOf(this));
		return result;
	}

	public static Set<SubProject> getSubProjectsBy(long[] subProjectIds) {
		Set<SubProject> results = new HashSet<SubProject>();
		if (null == subProjectIds) {
			return results;
		}
		for (long each : subProjectIds) {
			SubProject subProject = SubProject.get(each);
			if (subProject != null) {
				results.add(subProject);
			}
		}
		return results;
	}

	/**
	 * 得到专业Specialty，注意这里不是指专业工程SpecialtyProject
	 * 
	 * @return
	 */
	public Set<Specialty> getSpecialties() {
		return new HashSet<Specialty>(SpecialtyProject.specialtiesOfSubProject(this));
	}

	public SubProject addSpecialty(Specialty specialty) {
		if (SpecialtyProject.hasSpecialtyOfSubProject(this, specialty)) {
			return this;
		}
		SpecialtyProject specialtyProject = new SpecialtyProject(specialty);
		specialtyProject.setSubProject(this);
		specialtyProject.save();
		Project project = getProject();
		project.addSpecialty(specialty);
		project.save();
		return this;
	}

	public static Set<SubProject> getSubProjectsByIds(List<Long> subProjectIds) {
		Set<SubProject> results = new HashSet<SubProject>();
		if (null == subProjectIds) {
			return results;
		}
		for (Long each : subProjectIds) {
			SubProject subProject = SubProject.get(each);
			results.add(subProject);
		}
		return results;
	}

	/**
	 * 检查这个单点是否已经报过产值
	 * 
	 * @return
	 */
	public boolean hadOutputValueReported() {
		List<OutputValue> results = OutputValue.findBySubproject(this);
		return results != null && !results.isEmpty();
	}

	/**
	 * 撤销此单点
	 */
	public void cancel(Person lastUpdator, Date lastUpdated) {
		for (SpecialtyProject each : getSpecialtyProjects()) {
			each.cancel(lastUpdator, lastUpdated);
		}
		super.cancel(lastUpdator, lastUpdated);
	}

	/**
	 * 恢复此单点
	 */
	public void resume(Person lastUpdator, Date lastUpdated) {
		for (SpecialtyProject each : getSpecialtyProjects()) {
			each.resume(lastUpdator, lastUpdated);
		}
		super.resume(lastUpdator, lastUpdated);
	}

	/**
	 * 产值转移:单点到单项合同
	 * 
	 * @return
	 */
	public SubProject transferOutputValue(SingleContract singleContract) {
		for (OutputValue each : OutputValue.findBySubproject(this)) {
			each.setSingleContract(singleContract);
			each.save();
		}
		return this;
	}

	/**
	 * 彻底删除此单点，包括其下的产值，专业，分包付款
	 */
	public void remove() {
		removeAllDocument();
		removeAllOutputValue();
		removeAllSpecialtyProject();
		removeAllSubContractExpenditure();
		super.remove();
	}

	/**
	 * 彻底删除此单点的分包付款
	 */
	private void removeAllSubContractExpenditure() {
		for (Expenditure each : Expenditure.getSubContractExpenditures(this)) {
			each.remove();
		}
	}

	/**
	 * 彻底删除此单点的专业工程
	 */
	private void removeAllSpecialtyProject() {
		if (null == getSpecialtyProjects() || getSpecialtyProjects().isEmpty()) {
			return;
		}
		for (SpecialtyProject each : getSpecialtyProjects()) {
			each.remove();
		}
	}

	/**
	 * 彻底删除此单点的产值
	 */
	private void removeAllOutputValue() {
		for (OutputValue each : OutputValue.findBySubproject(this)) {
			each.remove();
		}
	}

	/**
	 * 彻底删除此单点的文档
	 */
	private void removeAllDocument() {
		for (Document each : Document.findByOneTag(new DocumentTag(SUBPROJECT, getId()))) {
			each.remove();
		}
	}

	/**
	 * 竣工
	 */
	public void finish(Date date) {
		for (SpecialtyProject each : specialtyProjects) {
			if (each.isFinished()) {
				continue;
			}
			each.finish(date);
		}
		super.finish(date);
	}

	/**
	 * 关闭单点
	 */
	public void close(Date date) {
		for (SpecialtyProject each : specialtyProjects) {
			if (each.isClosed()) {
				continue;
			}
			each.close(date);
		}
		super.close(date);
	}

	public static SubProject get(long subProjectId) {
		return SubProject.get(SubProject.class, subProjectId);
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof SubProject))
			return false;
		SubProject that = (SubProject) other;
		return new EqualsBuilder().append(getName(), that.getName()).append(getContractAmount(), that.getContractAmount())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getName()).toHashCode();
	}

	public String toString() {
		return new StringBuilder().append(getName()).append("  ").toString();
	}

	public Set<SpecialtyProject> getSpecialtyProjects() {
		return specialtyProjects;
	}

	public void setSpecialtyProjects(Set<SpecialtyProject> specialties) {
		this.specialtyProjects = specialties;
	}

	public String getApType() {
		return apType;
	}

	public void setApType(String apType) {
		this.apType = apType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public SingleContract getSingleContract() {
		return singleContract;
	}

	public void setSingleContract(SingleContract singleContract) {
		this.singleContract = singleContract;
	}

	public OrganizationInfo getSupervisorInfo() {
		return supervisorInfo;
	}

	public void setSupervisorInfo(OrganizationInfo supervisorInfo) {
		this.supervisorInfo = supervisorInfo;
	}

	public OrganizationInfo getDesignInfo() {
		return designInfo;
	}

	public void setDesignInfo(OrganizationInfo designInfo) {
		this.designInfo = designInfo;
	}

}
