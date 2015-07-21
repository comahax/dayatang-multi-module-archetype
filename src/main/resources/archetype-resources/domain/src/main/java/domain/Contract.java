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
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dayatang.domain.QuerySettings;

/**
 * 合同基类
 * 
 * @author zjzhai
 * 
 */

@Entity
@Table(name = "contracts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category", discriminatorType = DiscriminatorType.STRING)
public abstract class Contract extends AbstractCoreEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联到的项目
	 */
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	/**
	 * 合同编号
	 */
	@Column(name = "serial_number")
	private String serialNumber;

	/**
	 * 合同名称
	 */
	@Column(name = "contract_name")
	private String contractName;

	/**
	 * 总包合同金额
	 */
	private BigDecimal generalContractAmount = BigDecimal.ZERO;

	/**
	 * 合同类型
	 * 
	 */
	@Enumerated(EnumType.STRING)
	private ContractCategory contractCategory;

	/**
	 * 分包合同金额
	 */
	@Deprecated
	private BigDecimal subcontractAmount;

	/**
	 * 总包实际结算金额
	 */
	@Deprecated
	@Column(name = "general_settlement_amount")
	private BigDecimal generalSettlementAmount = BigDecimal.ZERO;

	/**
	 * 分包实际结算金额
	 */
	@Deprecated
	@Column(name = "subcontract_settlement_amount")
	private BigDecimal subcontractSettlementAmount = BigDecimal.ZERO;

	/**
	 * 折扣
	 */
	@Deprecated
	@Lob
	private String discount;

	/**
	 * 签定日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "sign_date")
	private Date signDate;

	/**
	 * 开工日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;

	/**
	 * 完工日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "finish_date")
	private Date finishDate;

	@Lob
	private String remark;

	@Embedded
	@AssociationOverrides({ @AssociationOverride(name = "organization", joinColumns = @JoinColumn(name = "part_a_org")),
			@AssociationOverride(name = "person", joinColumns = @JoinColumn(name = "part_a_person")) })
	private OrganizationInfo partA = new OrganizationInfo();

	@Embedded
	@AssociationOverrides({ @AssociationOverride(name = "organization", joinColumns = @JoinColumn(name = "part_b_org")),
			@AssociationOverride(name = "person", joinColumns = @JoinColumn(name = "part_b_person")) })
	private OrganizationInfo partB = new OrganizationInfo();

	@Embedded
	@AssociationOverrides({ @AssociationOverride(name = "organization", joinColumns = @JoinColumn(name = "supervisor_org")),
			@AssociationOverride(name = "person", joinColumns = @JoinColumn(name = "supervisor_person")) })
	private OrganizationInfo supervisor = new OrganizationInfo();

	/**
	 * 哪个机构范围能访问这个合同
	 */
	@OneToOne
	@JoinColumn(name = "granted_scope_id")
	private InternalOrganization grantedScope;

	/**
	 * 关联的一批单点
	 */
	@OneToMany(mappedBy = "singleContract")
	private Set<SubProject> subProjects = new HashSet<SubProject>();

	/*
	 * 关联到某个项目
	 */
	public Contract associateWith(Project project) {
		setProject(project);
		save();
		return this;
	}

	/**
	 * 此合同已关联到项目
	 * 
	 * @return
	 */
	public boolean isContacted() {
		return project != null;
	}

	public boolean isNotContacted() {
		return !isContacted();
	}

	/**
	 * 根据项目找到合同
	 * 
	 * @param project
	 * @return
	 */
	public static List<Contract> findByProject(Project project) {
		QuerySettings<Contract> settings = QuerySettings.create(Contract.class).eq("project", project);
		return getRepository().find(settings);
	}

	public static Contract get(long contractId) {
		return Contract.get(Contract.class, contractId);
	}

	public void remove() {
		removeDocs();
		super.remove();
	}

	private void removeDocs() {
		for (Document each : Document.findByOneTag(new DocumentTag(CONTRACT, getId()))) {
			each.remove();
		}
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public BigDecimal getGeneralContractAmount() {
		return generalContractAmount;
	}

	public void setGeneralContractAmount(BigDecimal generalContractAmount) {
		this.generalContractAmount = generalContractAmount;
	}

	public ContractCategory getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(ContractCategory contractCategory) {
		this.contractCategory = contractCategory;
	}

	public BigDecimal getSubcontractAmount() {
		return subcontractAmount;
	}

	public void setSubcontractAmount(BigDecimal subcontractAmount) {
		this.subcontractAmount = subcontractAmount;
	}

	public BigDecimal getGeneralSettlementAmount() {
		return generalSettlementAmount;
	}

	public void setGeneralSettlementAmount(BigDecimal generalSettlementAmount) {
		this.generalSettlementAmount = generalSettlementAmount;
	}

	public BigDecimal getSubcontractSettlementAmount() {
		return subcontractSettlementAmount;
	}

	public void setSubcontractSettlementAmount(BigDecimal subcontractSettlementAmount) {
		this.subcontractSettlementAmount = subcontractSettlementAmount;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public OrganizationInfo getPartA() {
		return partA;
	}

	public void setPartA(OrganizationInfo partA) {
		this.partA = partA;
	}

	public OrganizationInfo getPartB() {
		return partB;
	}

	public void setPartB(OrganizationInfo partB) {
		this.partB = partB;
	}

	public Set<SubProject> getSubProjects() {
		return new HashSet<SubProject>(subProjects);
	}

	public void setSubProjects(Set<SubProject> subProjects) {
		this.subProjects = new HashSet<SubProject>(subProjects);
	}

	public InternalOrganization getGrantedScope() {
		return grantedScope;
	}

	public void setGrantedScope(InternalOrganization grantedScope) {
		this.grantedScope = grantedScope;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public OrganizationInfo getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(OrganizationInfo supervisor) {
		this.supervisor = supervisor;
	}

}
