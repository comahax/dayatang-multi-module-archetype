#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.QuerySettings;
import ${package}.commons.BigDecimalUtils;
import ${package}.utils.DocumentTagConstans;
import com.dayatang.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 项目抽象类.一个项目有可能对应多个合同
 * 
 * @author zjzhai
 */
@Entity
@DiscriminatorValue("PROJECT")
public class Project extends ProjectElement {

	private static final long serialVersionUID = 1919560392316984365L;

	public Project() {

	}

	public Project(String projectName) {
		setName(projectName);
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "project_status")
	private ProjectStatus status = ProjectStatus.APPROVING;

	/**
	 * 项目性质
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "project_nature")
	private ProjectNature projectNature;

	/**
	 * 项目类型
	 */
	@Column(name = "project_type")
	private String projectType;

	/**
	 * 关联到的合同
	 */
	@OneToMany(mappedBy = "project")
	private Set<Contract> contracts;

	/**
	 * 总产值
	 */
	@Column(name = "total_outputvalue")
	private BigDecimal totalOutputvalue = BigDecimal.ZERO;

	/**
	 * 分包比例
	 */
	@ElementCollection
	@CollectionTable(name = "project_subcontractings", joinColumns = { @JoinColumn(name = "project_id") })
	private List<ProjectSubcontracting> subcontractings = new ArrayList<ProjectSubcontracting>();

	/**
	 * 预估收入
	 */
	@Column(name = "project_estimated_income")
	private BigDecimal estimatedIncome = BigDecimal.ZERO;

	/**
	 * 毛利润
	 */
	@Column(name = "project_gross_margin")
	// TODO 这里表列名称有问题应该是project_gross_profit
	private BigDecimal grossProfit = BigDecimal.ZERO;

	/**
	 * 毛利率
	 */
	@Column(name = "project_gross_profit_margin")
	private BigDecimal grossMargin = BigDecimal.ZERO;

	/**
	 * 企业管理成本
	 */
	@Column(name = "project_enterprise_management_costs")
	private BigDecimal enterpriseManagementCosts = BigDecimal.ZERO;

	/**
	 * 企业所得税
	 */
	@Column(name = "project_enterprise_income_tax")
	private BigDecimal enterpriseIncomeTax = BigDecimal.ZERO;

	/**
	 * 净利率
	 */
	@Column(name = "project_net_profit_margin")
	private BigDecimal netProfitMargin = BigDecimal.ZERO;

	/**
	 * 净利润
	 */
	@Column(name = "project_net_profit")
	private BigDecimal netProfit = BigDecimal.ZERO;

	/**
	 * 包含的单点项目
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "project")
	private List<SubProject> subProjects = new ArrayList<SubProject>();

	/**
	 * 业主 信息
	 */
	@Embedded
	@AssociationOverrides({ @AssociationOverride(name = "organization", joinColumns = @JoinColumn(name = "owner_org_id")), @AssociationOverride(name = "person", joinColumns = @JoinColumn(name = "owner_contact_id")) })
	private OrganizationInfo ownerInfo = new OrganizationInfo();

	/**
	 * 项目成本
	 */
	@ElementCollection
	@CollectionTable(name = "project_budget_expenditures", joinColumns = @JoinColumn(name = "project_id"))
	@MapKeyColumn(name = "expenditure_type")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "budget_value")
	private Map<ExpenditureType, BigDecimal> budgets = new LinkedHashMap<ExpenditureType, BigDecimal>();

	/**
	 * 自定义成本
	 */
	@ElementCollection
	@CollectionTable(name = "project_custombudget_expenditures", joinColumns = @JoinColumn(name = "project_id"))
	@MapKeyColumn(name = "custom_expenditure_type")
	@MapKeyClass(String.class)
	@MapKeyJoinColumn
	@Column(name = "budget_value")
	private Map<String, BigDecimal> customBudgets = new LinkedHashMap<String, BigDecimal>();

	/**
	 * 合作单位信息
	 */
	@ElementCollection
	@CollectionTable(name = "proj_cooperation_org_infos")
	private Set<OrganizationInfo> projCooperationOrgInfos = new HashSet<OrganizationInfo>();

	/**
	 * 资金占用费情况
	 */
	@ElementCollection
	@CollectionTable(name = "proj_capitaltotakeups", joinColumns = @JoinColumn(name = "project_id", nullable = false))
	private Set<Capitaltotakeup> capitaltotakeups = new HashSet<Capitaltotakeup>();

	/**
	 * 一个项目所包括的专业
	 */
	@ManyToMany
	@JoinTable(name = "project_specialties", joinColumns = @JoinColumn(name = "project_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "speciaty_id"))
	private Set<Specialty> specialties = new HashSet<Specialty>();

	/**
	 * 在哪个公司的名义开展
	 */
	@ManyToOne
	@JoinColumn(name = "constructing_org_id")
	private InternalOrganization constructingOrg;

	/**
	 * 关联施工队
	 * 
	 * @param projCooperationOrgInfo
	 * @return
	 */
	public Project assosiateWithCooperationOrg(OrganizationInfo projCooperationOrgInfo) {
		Set<OrganizationInfo> cooperationOrganizations = getProjCooperationOrgInfos();
		if (cooperationOrganizations == null) {
			cooperationOrganizations = new HashSet<OrganizationInfo>();
		}
		cooperationOrganizations.add(projCooperationOrgInfo);
		setProjCooperationOrgInfos(cooperationOrganizations);
		save();
		return this;
	}

	/**
	 * 是否为建设中
	 * 
	 * @return
	 */
	public boolean isConstructing() {
		return ProjectStatus.CONSTRUCTING.equals(getStatus());
	}

	/**
	 * 是否允许向这个项目添加合同 是否允许向这个项目添加单点工程
	 */
	public boolean isBusinessOperationsable() {
		Set<ProjectStatus> projectStatuses = new HashSet<ProjectStatus>();
		projectStatuses.add(ProjectStatus.APPROVED);
		projectStatuses.add(ProjectStatus.CONSTRUCTING);
		projectStatuses.add(ProjectStatus.FINISHED);
		return projectStatuses.contains(getStatus());
	}

	/**
	 * 是否可以中止项目
	 * 
	 * @return
	 */
	public boolean isTerminatedable() {
		Set<ProjectStatus> projectStatuses = new HashSet<ProjectStatus>();
		projectStatuses.add(ProjectStatus.APPROVING);
		projectStatuses.add(ProjectStatus.CONSTRUCTING);
		projectStatuses.add(ProjectStatus.APPROVED);
		projectStatuses.add(ProjectStatus.FINISHED);
		return projectStatuses.contains(getStatus());
	}

	/**
	 * 运作费用
	 * 
	 * @return
	 */
	public BigDecimal getOperation() {
		return budgets != null ? budgets.get(ExpenditureType.OPERATION) : BigDecimal.ZERO;
	}

	/**
	 * 人力费用
	 * 
	 * @return
	 */
	public BigDecimal getSalary() {
		return budgets != null ? budgets.get(ExpenditureType.SALARY) : BigDecimal.ZERO;
	}

	/**
	 * 业务费用
	 * 
	 * @return
	 */
	public BigDecimal getMarket() {
		return budgets != null ? budgets.get(ExpenditureType.MARKET) : BigDecimal.ZERO;
	}

	/**
	 * 设备折旧费用
	 * 
	 * @return
	 */
	public BigDecimal getDeviceDepreciation() {
		return budgets != null ? budgets.get(ExpenditureType.DEVICE_DEPRECIATION) : BigDecimal.ZERO;
	}

	/**
	 * 耗材、辅材费用
	 * 
	 * @return
	 */
	public BigDecimal getAuxiliaryMaterial() {
		return budgets != null ? budgets.get(ExpenditureType.DEVICE_DEPRECIATION) : BigDecimal.ZERO;
	}

	/**
	 * 分包费用
	 * 
	 * @return
	 */
	public BigDecimal getSubcontractCost() {
		return budgets != null ? budgets.get(ExpenditureType.SUBCONTRACT) : BigDecimal.ZERO;
	}

	/**
	 * 主材
	 * 
	 * @return
	 */
	public BigDecimal getMainMaterial() {
		return budgets != null ? budgets.get(ExpenditureType.MAIN_MATERIAL) : BigDecimal.ZERO;
	}

	/**
	 * 资金占用费用
	 * 
	 * @return
	 */
	public BigDecimal getFundOccupation() {
		return budgets != null ? budgets.get(ExpenditureType.FUND_OCCUPATION) : BigDecimal.ZERO;
	}

	/**
	 * 税金费用
	 * 
	 * @return
	 */
	public BigDecimal getTaxes() {
		return budgets != null ? budgets.get(ExpenditureType.TAXES) : BigDecimal.ZERO;
	}

	/**
	 * 其他费用
	 * 
	 * @return
	 */
	public BigDecimal getOtherCost() {
		return budgets != null ? budgets.get(ExpenditureType.OTHER) : BigDecimal.ZERO;
	}

	/**
	 * 添加专业
	 * 
	 * @param specialty
	 */
	public void addSpecialty(Specialty specialty) {
		Set<Specialty> results = getSpecialties();
		results.add(specialty);
		setSpecialties(results);
		save();
	}

	/**
	 * 彻底删除项目 !谨慎
	 */
	public void remove() {
		removeProjectBudgetHistory();
		removeOutputValues();
		removeSubProjects();
		removeContracts();
		removeDocs();
		super.remove();
	}

	/**
	 * 删除已报产值
	 */
	private void removeOutputValues() {
		for (OutputValue each : OutputValue.findBy(this)) {
			each.remove();
		}
	}

	/**
	 * 删除项目的预算成本
	 */
	private void removeProjectBudgetHistory() {
		for (ProjectBudgetHistory each : ProjectBudgetHistory.findByProjectDescCreateTime(this)) {
			each.remove();
		}
	}

	/**
	 * 彻底删除单点工程
	 */
	private void removeSubProjects() {
		for (SubProject each : getSubProjects()) {
			each.remove();
		}
	}

	/**
	 * 删除所有项目文档
	 */
	private void removeDocs() {
		for (Document each : Document.findByOneTag(new DocumentTag(DocumentTagConstans.PROJECT, getId()))) {
			each.remove();
		}
	}

	/**
	 * 删除所有的合同
	 */
	private void removeContracts() {
		for (Contract each : Contract.findByProject(this)) {
			each.remove();
		}
	}

	/**
	 * 计算并设置项目的编码 项目编码规则＝公司代码 + 客户代码 + 项目区域代码 + 业务类型代码 + 年份代码(13) + 3位流水号
	 * 
	 * @return
	 */
	public ProjectSerialNumberAssignment computeAndSetProjectNumber() {

		StringBuilder builder = new StringBuilder();

		InternalOrganization responsibleCompany = getResponsibleDivision().getCompany();

		// 公司代码
		builder.append(responsibleCompany.getSerialNumber()).append("-");

		// 客户代码
		builder.append(((OwnerOrganization) getOwnerInfo().getOrganization()).getOwnerCategory()).append("-");

		// 区域代码
		builder.append(getArea().getShortPinyin().toUpperCase()).append("-");

		// 项目类型编码
		Dictionary projectType = Dictionary.getDictionaryBySerialNumBerAndCategory(getProjectType(), DictionaryCategory.PROJECT_TYPE);
        if (null != projectType) {
            builder.append(StringUtils.isNotEmpty(projectType.getParentSn()) ? projectType.getParentSn() : projectType.getSerialNumber()).append("-");
        }

		// 年代
		String yearCode = (DateUtils.getYear(getStartDate()) + "").substring(2, 4);
		builder.append(yearCode).append("-");

		// 流水号
		ProjectSerialNumberAssignment assignment = ProjectSerialNumberAssignment.getLastest(responsibleCompany.getId(), yearCode);

		if (null == assignment) {
			assignment = ProjectSerialNumberAssignment.createInitProjectSerialNumberAssignment(responsibleCompany.getId(), yearCode);
			builder.append(assignment.getSerialNumber());
			setProjectNumber(builder.toString());
			return assignment;
		} else {
			builder.append(assignment.getNextSerialNumber());
			setProjectNumber(builder.toString());
			return assignment.next();
		}

	}

	/**
	 * 此项目是否有上合同
	 */
	public boolean isAssociatedWithContract() {
		return getContracts() != null && !getContracts().isEmpty();
	}

	public static void changeProjectType(String oldProjectType, String newProjectType) {
		String sql = "UPDATE Project o SET o.projectType = :newType WHERE e.projectType = :oldType";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newType", newProjectType);
		params.put("oldType", oldProjectType);
		getRepository().executeUpdate(sql, params);

	}

	@Override
	public void finish(Date date) {

		// 只有在建的项目才有竣工的说法
		if (!isFinishable()) {
			return;
		}
		for (SubProject subProject : subProjects) {
			if (subProject.isFinished()) {
				continue;
			}
			subProject.finish(date);
		}
		setStatus(ProjectStatus.FINISHED);
		super.finish(date);
	}

	public boolean isFinishable() {
		return ProjectStatus.CONSTRUCTING.equals(getStatus());
	}

	@Override
	public void close(Date date) {

		// 竣工后才能关闭
		if (!isCloseable()) {
			return;
		}

		if (null == date) {
			date = new Date();
		}
		for (SubProject subProject : subProjects) {
			if (subProject.isClosed()) {
				continue;
			}
			subProject.close(date);
		}
		setStatus(ProjectStatus.CLOSED);
		super.close(date);
	}

	public boolean isCloseable() {
		return ProjectStatus.FINISHED.equals(getStatus());
	}

	/**
	 * 得到总的预算
	 * 
	 * @return
	 */
	public BigDecimal getTotalBudgetAmount() {
		BigDecimal result = BigDecimal.ZERO;
		if (null != getBudgets()) {
			Map<ExpenditureType, BigDecimal> budgets = getBudgets();
			for (ExpenditureType each : budgets.keySet()) {
				if (null == each || null == budgets.get(each)) {
					continue;
				}
				result = result.add(budgets.get(each));
			}
		}
		result = result.add(getTotalCustomBudgetAmount());

		return result;
	}

	/**
	 * 得到自定义成本金额总和
	 * 
	 * @return
	 */
	public BigDecimal getTotalCustomBudgetAmount() {
		BigDecimal result = BigDecimal.ZERO;
		Map<String, BigDecimal> map = getCustomBudgets();
		if (null == map) {
			return BigDecimal.ZERO;
		}
		for (String each : map.keySet()) {
			if (null == each || null == map.get(each)) {
				continue;
			}
			result = result.add(map.get(each));
		}
		return result;
	}

	/**
	 * 删除专业
	 * 
	 * @param specialty
	 * @return
	 */
	public Project removeSpecialty(Specialty specialty) {
		getSpecialties().remove(specialty);
		save();
		return this;
	}

	/**
	 * 是否为草稿
	 * 
	 * @return
	 */
	public boolean isDraft() {
		return ProjectStatus.DRAFT.equals(getStatus());
	}

	/**
	 * 是否为驳回状态
	 * 
	 * @return
	 */
	public boolean isRejected() {
		return ProjectStatus.REJECTED.equals(getStatus());
	}

	/**
	 * 返回产值已经分包出去的总和
	 * 
	 * @return
	 */
	public BigDecimal getOutputvalueDistributiveShareTotal() {
		BigDecimal result = BigDecimal.ZERO;
		for (ProjectSubcontracting each : getSubcontractings()) {
			if (null == each.getDistributiveShare()) {
				continue;
			}
			result = result.add(each.getDistributiveShare());
		}
		return result;
	}

	/**
	 * 得到应付给分包商的金额的总和
	 * 
	 * @return
	 */
	public BigDecimal getOutputvaluePayableTotal() {
		BigDecimal result = BigDecimal.ZERO;
		for (ProjectSubcontracting each : getSubcontractings()) {
			if (null == each.getPayable()) {
				continue;
			}
			result = result.add(each.getPayable());
		}
		return result;
	}

	/**
	 * 此项目未进行成本预算
	 * 
	 * @return
	 */
	public boolean isEmptyBudget() {
		return BigDecimalUtils.eqZero(getTotalBudgetAmount());
	}

	public Map<ExpenditureType, BigDecimal> getBudgets() {
		return new LinkedHashMap<ExpenditureType, BigDecimal>(budgets);
	}

	/**
	 * 返回万元的格式的预算
	 * 
	 * @return
	 */
	public Map<ExpenditureType, BigDecimal> getTenThousandFormatterBudgets() {
		Map<ExpenditureType, BigDecimal> result = new LinkedHashMap<ExpenditureType, BigDecimal>();
		for (ExpenditureType type : budgets.keySet()) {
			result.put(type, BigDecimalUtils.convertYuanToTenThousand(budgets.get(type)));
		}
		return result;
	}

	public void setBudgets(Map<ExpenditureType, BigDecimal> budgets) {
		this.budgets = new LinkedHashMap<ExpenditureType, BigDecimal>(budgets);
	}

	public Set<Specialty> getSpecialties() {
		if (specialties == null) {
			specialties = new HashSet<Specialty>();
		}
		return specialties;
	}

	public void setSpecialties(Set<Specialty> specialties) {
		this.specialties = specialties;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(37, 17).append(getName()).append(getCreated()).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Project)) {
			return false;
		}
		Project that = (Project) other;
		return new EqualsBuilder().append(this.getName(), that.getName()).append(this.getCreated(), that.getCreated()).isEquals();
	}

	@Override
	public String toString() {
		return getName();
	}

	public static Project get(long projectId) {
		return Project.get(Project.class, projectId);
	}

	@SuppressWarnings("unchecked")
	private <T extends Contract> Set<T> getContracts(Class<T> contractClass) {
		Set<T> results = new HashSet<T>();
		for (Contract contract : contracts) {
			if (contractClass.isAssignableFrom(contract.getClass())) {
				results.add((T) contract);
			}
		}
		return results;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public BigDecimal getEstimatedIncome() {
		return estimatedIncome;
	}

	public void setEstimatedIncome(BigDecimal estimatedIncome) {
		this.estimatedIncome = estimatedIncome;
	}

	public BigDecimal getGrossProfit() {
		return grossProfit;
	}

	public BigDecimal getGrossMargin() {
		return grossMargin;
	}

	public List<SubProject> getSubProjects() {
		return subProjects;
	}

	public void setSubProjects(List<SubProject> subProjects) {
		this.subProjects = subProjects;
	}

	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}

	public Set<FrameworkContract> getFrameworkContracts() {
		return getContracts(FrameworkContract.class);
	}

	public Set<SingleContract> getSingleContracts() {
		return getContracts(SingleContract.class);
	}

	public ProjectNature getProjectNature() {
		return projectNature;
	}

	public void setProjectNature(ProjectNature projectNature) {
		this.projectNature = projectNature;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public Set<OrganizationInfo> getProjCooperationOrgInfos() {
		return projCooperationOrgInfos;
	}

	public void setProjCooperationOrgInfos(Set<OrganizationInfo> projCooperationOrgInfos) {
		this.projCooperationOrgInfos = projCooperationOrgInfos;
	}

	public OrganizationInfo getOwnerInfo() {
		return ownerInfo;
	}

	public void setOwnerInfo(OrganizationInfo ownerInfo) {
		this.ownerInfo = ownerInfo;
	}

	public BigDecimal getTotalOutputvalue() {
		return totalOutputvalue;
	}

	public void setTotalOutputvalue(BigDecimal totalOutputvalue) {
		this.totalOutputvalue = totalOutputvalue;
		setGrossMarginAndGrossProfit(subcontractings);
	}

	public List<ProjectSubcontracting> getSubcontractings() {
		return subcontractings;
	}

	public void setSubcontractings(List<ProjectSubcontracting> subcontractings) {
		this.subcontractings = subcontractings;
		//setGrossMarginAndGrossProfit(subcontractings);

	}

	private void setGrossMarginAndGrossProfit(List<ProjectSubcontracting> subcontractings) {
		if (null == subcontractings || null == totalOutputvalue || BigDecimal.ZERO.equals(totalOutputvalue)) {
			return;
		}

		// 应付总和
		BigDecimal paypalTotal = BigDecimal.ZERO;

		for (ProjectSubcontracting each : subcontractings) {
			if (null != each.getPayable()) {
				paypalTotal = paypalTotal.add(each.getPayable());
			}
		}

		// 精度应该是可以配置的 TODO
		// 毛利率 ＝ 100% - (应付款总和 / 产值总和)
		this.grossMargin = new BigDecimal(100).subtract(new BigDecimal(100).multiply(paypalTotal.divide(totalOutputvalue, 2, RoundingMode.CEILING)));

		// 毛利润 = 项目总产值 - 项目应付
		this.grossProfit = totalOutputvalue.subtract(paypalTotal);

	}

	public Set<Capitaltotakeup> getCapitaltotakeups() {
		return capitaltotakeups;
	}

	public void setCapitaltotakeups(Set<Capitaltotakeup> capitaltotakeups) {
		this.capitaltotakeups = capitaltotakeups;
	}

	public InternalOrganization getConstructingOrg() {
		return constructingOrg;
	}

	public void setConstructingOrg(InternalOrganization constructingOrg) {
		this.constructingOrg = constructingOrg;
	}

	public void setGrossProfit(BigDecimal grossProfit) {
		this.grossProfit = grossProfit;
	}

	public void setGrossMargin(BigDecimal grossMargin) {
		this.grossMargin = grossMargin;
	}

	public BigDecimal getEnterpriseManagementCosts() {
		return enterpriseManagementCosts;
	}

	public void setEnterpriseManagementCosts(BigDecimal enterpriseManagementCosts) {
		this.enterpriseManagementCosts = enterpriseManagementCosts;
	}

	public BigDecimal getEnterpriseIncomeTax() {
		return enterpriseIncomeTax;
	}

	public void setEnterpriseIncomeTax(BigDecimal enterpriseIncomeTax) {
		this.enterpriseIncomeTax = enterpriseIncomeTax;
	}

	public BigDecimal getNetProfitMargin() {
		return netProfitMargin;
	}

	public void setNetProfitMargin(BigDecimal netProfitMargin) {
		this.netProfitMargin = netProfitMargin;
	}

	public BigDecimal getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(BigDecimal netProfit) {
		this.netProfit = netProfit;
	}

	public static List<Project> findByProjectType(String projectType) {
		return getRepository().find(QuerySettings.create(Project.class).eq("projectType", projectType));
	}

	/**
	 * 计算出资金占用成本总和
	 * 
	 * @return
	 */
	public BigDecimal getTotalCapitalCost() {
		if (null == capitaltotakeups) {
			return BigDecimal.ZERO;
		}

		BigDecimal totalCapitalCost = BigDecimal.ZERO;
		for (Capitaltotakeup each : capitaltotakeups) {
			if (null != each && null != each.getCostFunds()) {
				totalCapitalCost = totalCapitalCost.add(each.getCostFunds());
			}
		}
		return totalCapitalCost;
	}

	/**
	 * 计算出资金占用的总和
	 * 
	 * @return
	 */
	public BigDecimal getTotalExpectedFunds() {

		BigDecimal totalExpectedFunds = BigDecimal.ZERO;
		for (Capitaltotakeup each : capitaltotakeups) {
			if (null != each && null != each.getExpectedFunds()) {
				totalExpectedFunds = totalExpectedFunds.add(each.getExpectedFunds());
			}
		}

		return totalExpectedFunds;
	}

	public Map<String, BigDecimal> getCustomBudgets() {
		return customBudgets;
	}

	public void setCustomBudgets(Map<String, BigDecimal> customBudgets) {
		this.customBudgets = customBudgets;
	}

}
