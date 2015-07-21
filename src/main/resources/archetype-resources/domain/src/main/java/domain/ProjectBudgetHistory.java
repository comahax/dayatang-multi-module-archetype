#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 项目的成本历史记录
 * User: zjzhai
 * Date: 13-6-24
 * Time: 下午5:13
 */
@Entity
@Table(name = "project_budget_history")
public class ProjectBudgetHistory extends AbstractEntity {

	private static final long serialVersionUID = 5778809914186018417L;


	/**
     * 预估收入
     */
    @Column(name = "estimated_income")
    private BigDecimal estimatedIncome = BigDecimal.ZERO;


    /**
     * 毛利润
     */
    @Column(name = "gross_profit")
    private BigDecimal grossProfit = BigDecimal.ZERO;

    /**
     * 毛利率
     */
    @Column(name = "gross_profit_margin")
    private BigDecimal grossMargin = BigDecimal.ZERO;

    /**
     * 企业管理成本
     */
    @Column(name = "enterprise_management_costs")
    private BigDecimal enterpriseManagementCosts = BigDecimal.ZERO;


    /**
     * 企业所得税
     */
    @Column(name = "enterprise_income_tax")
    private BigDecimal enterpriseIncomeTax = BigDecimal.ZERO;

    /**
     * 净利率
     */
    @Column(name = "net_profit_margin")
    private BigDecimal netProfitMargin = BigDecimal.ZERO;

    /**
     * 净利润
     */
    @Column(name = "net_profit")
    private BigDecimal netProfit = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 本历史版本的创建人
     */
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Person creator;

    /**
     * 资金占用费情况
     */
    @ElementCollection
    @CollectionTable(name = "proj_budget_history_capitaltotakeups",joinColumns = @JoinColumn(name = "project_budget_history_id", nullable = false))
    private Set<Capitaltotakeup> capitaltotakeups = new HashSet<Capitaltotakeup>();


    @ElementCollection
    @CollectionTable(name = "project_budget_history_expenditures", joinColumns = @JoinColumn(name = "project_budget_history_id"))
    @MapKeyColumn(name = "expenditure_type")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "budget_value")
    private Map<ExpenditureType, BigDecimal> budgets = new LinkedHashMap<ExpenditureType, BigDecimal>();


	/**
	 * 自定义成本
	 */
	@ElementCollection
	@CollectionTable(name = "project_custombudget_history_expenditures", joinColumns = @JoinColumn(name = "project_id"))
	@MapKeyColumn(name = "custom_expenditure_type")
	@MapKeyClass(String.class)
	@MapKeyJoinColumn
	@Column(name = "budget_value")
	private Map<String, BigDecimal> customBudgets = new LinkedHashMap<String, BigDecimal>();

    private String remark;

    public ProjectBudgetHistory() {
    }

    public ProjectBudgetHistory(Project project, Date date, Person person) {
        createTime = date;
        creator = person;
        estimatedIncome = project.getEstimatedIncome();
        grossProfit = project.getGrossProfit();
        grossMargin = project.getGrossMargin();
        enterpriseManagementCosts = project.getEnterpriseManagementCosts();
        enterpriseIncomeTax = project.getEnterpriseIncomeTax();
        netProfitMargin = project.getNetProfitMargin();
        netProfit = project.getNetProfit();
        budgets = project.getBudgets();
        this.project = project;
    }


    public ProjectBudgetHistory(Project project, Date date, Person person, Set<Capitaltotakeup> capitaltotakeups) {
        this(project, date, person);
        this.capitaltotakeups = capitaltotakeups;
    }

    public ProjectBudgetHistory(Project project, Date date, Person person, List<Capitaltotakeup> capitaltotakeups) {
        this(project, date, person);

        if (null != capitaltotakeups) {
            this.capitaltotakeups = new HashSet<Capitaltotakeup>(capitaltotakeups);
        }


    }

    public static ProjectBudgetHistory findByProjectAndId(Project project, Long historyId) {
       return getRepository().getSingleResult(QuerySettings.create(ProjectBudgetHistory.class).eq("project", project).eq("id", historyId));
    }


    /**
     * 找出项目的成本历史记录
     *
     * @param project
     * @return
     */
    public static List<ProjectBudgetHistory> findByProjectDescCreateTime(Project project) {
        return getRepository().find(QuerySettings.create(ProjectBudgetHistory.class).eq("project", project).desc("createTime"));
    }

    /**
     * 得到总的预算
     *
     * @return
     */
    public BigDecimal getTotalBudgetAmount() {
        BigDecimal result = BigDecimal.ZERO;
        Map<ExpenditureType, BigDecimal> budgets = getBudgets();
        for (ExpenditureType each : budgets.keySet()) {
            if (each == null || budgets.get(each) == null) {
                continue;
            }
            result = result.add(budgets.get(each));
        }
        return result;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectBudgetHistory)) return false;

        ProjectBudgetHistory that = (ProjectBudgetHistory) o;

        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (enterpriseIncomeTax != null ? !enterpriseIncomeTax.equals(that.enterpriseIncomeTax) : that.enterpriseIncomeTax != null) return false;
        if (enterpriseManagementCosts != null ? !enterpriseManagementCosts.equals(that.enterpriseManagementCosts) : that.enterpriseManagementCosts != null) return false;
        if (estimatedIncome != null ? !estimatedIncome.equals(that.estimatedIncome) : that.estimatedIncome != null) return false;
        if (grossMargin != null ? !grossMargin.equals(that.grossMargin) : that.grossMargin != null) return false;
        if (grossProfit != null ? !grossProfit.equals(that.grossProfit) : that.grossProfit != null) return false;
        if (netProfit != null ? !netProfit.equals(that.netProfit) : that.netProfit != null) return false;
        if (netProfitMargin != null ? !netProfitMargin.equals(that.netProfitMargin) : that.netProfitMargin != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = estimatedIncome != null ? estimatedIncome.hashCode() : 0;
        result = 31 * result + (grossProfit != null ? grossProfit.hashCode() : 0);
        result = 31 * result + (grossMargin != null ? grossMargin.hashCode() : 0);
        result = 31 * result + (enterpriseManagementCosts != null ? enterpriseManagementCosts.hashCode() : 0);
        result = 31 * result + (enterpriseIncomeTax != null ? enterpriseIncomeTax.hashCode() : 0);
        result = 31 * result + (netProfitMargin != null ? netProfitMargin.hashCode() : 0);
        result = 31 * result + (netProfit != null ? netProfit.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CostHistory{" +
                "estimatedIncome=" + estimatedIncome +
                ", grossProfit=" + grossProfit +
                ", grossMargin=" + grossMargin +
                ", enterpriseManagementCosts=" + enterpriseManagementCosts +
                ", enterpriseIncomeTax=" + enterpriseIncomeTax +
                ", netProfitMargin=" + netProfitMargin +
                ", netProfit=" + netProfit +
                ", project=" + project +
                ", createTime=" + createTime +
                ", creator=" + creator +
                '}';
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

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public BigDecimal getGrossMargin() {
        return grossMargin;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<Capitaltotakeup> getCapitaltotakeups() {
        return capitaltotakeups;
    }

    public void setCapitaltotakeups(Set<Capitaltotakeup> capitaltotakeups) {
        this.capitaltotakeups = capitaltotakeups;
    }

    public Map<ExpenditureType, BigDecimal> getBudgets() {
        return budgets;
    }

    public void setBudgets(Map<ExpenditureType, BigDecimal> budgets) {
        this.budgets = budgets;
    }

	public Map<String, BigDecimal> getCustomBudgets() {
		return customBudgets;
	}

	public void setCustomBudgets(Map<String, BigDecimal> customBudgets) {
		this.customBudgets = customBudgets;
	}
    
    
}
