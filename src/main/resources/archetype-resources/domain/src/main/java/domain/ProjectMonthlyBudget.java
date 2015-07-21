#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import ${package}.commons.BigDecimalUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目预算项
 * 
 * @author zjzhai
 * 
 */
@Entity
@Table(name = "project_monthly_budget")
public class ProjectMonthlyBudget extends AbstractCoreEntity {

	private static final long serialVersionUID = -2160729429921769342L;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Enumerated(EnumType.STRING)
	@Column(name = "expenditure_type")
	private ExpenditureType expenditureType;

	/**
	 * 月度
	 */
	@Embedded
	private Monthly monthly;

	/**
	 * 预算金额
	 */
	private BigDecimal amount;

	@Lob
	@Column(name = "budget_remark")
	private String remark;

	public boolean amountIsEmpty() {
		return null == amount || BigDecimalUtils.leZero(amount);
	}

	public boolean isTotalBudget() {
		return null == monthly;
	}

	public ProjectMonthlyBudget() {

	}

	public ProjectMonthlyBudget(Project project, ExpenditureType expenditureType, Monthly monthly, BigDecimal amount) {
		this.project = project;
		this.expenditureType = expenditureType;
		this.monthly = monthly;
		this.amount = amount;
	}

	/**
	 * 得到某个月的项目预算总和
	 * 
	 * @param project
	 * @param monthly
	 * @return
	 */
	public static BigDecimal getTotalBudgetAmountOf(Project project, Monthly monthly) {
		String sql = "SELECT SUM(o.amount) FROM ProjectMonthlyBudget o WHERE o.project =:project AND o.monthly = :monthly";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("monthly", monthly);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	public static List<ProjectMonthlyBudget> findBy(Project project, Monthly monthly) {
		List<ProjectMonthlyBudget> results = new ArrayList<ProjectMonthlyBudget>();
		String sql = "SELECT o FROM ProjectMonthlyBudget o WHERE o.monthly =:monthly AND o.project =:project";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("monthly", monthly);
		params.put("project", project);
		results.addAll(getRepository().find(sql, params, ProjectMonthlyBudget.class));
		return results;
	}

	/**
	 * 计算这个项目总共预算了多少
	 * 
	 * @return
	 */
	public static BigDecimal getTotalBudgetAmountOf(Project project) {
		String sql = "SELECT SUM(o.amount) FROM ProjectMonthlyBudget o WHERE o.project =:project";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	public static ProjectMonthlyBudget get(long projectBudgetId) {
		return ProjectMonthlyBudget.get(ProjectMonthlyBudget.class, projectBudgetId);
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof ProjectMonthlyBudget))
			return false;
		ProjectMonthlyBudget that = (ProjectMonthlyBudget) other;
		return new EqualsBuilder().append(this.getProject(), that.getProject())
				.append(this.getExpenditureType(), that.getExpenditureType()).append(monthly, that.getMonthly()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(project).append(expenditureType).append(monthly).toHashCode();
	}

	public String toString() {
		return new StringBuilder().append(project).append(monthly).append(expenditureType).append(amount).toString();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ExpenditureType getExpenditureType() {
		return expenditureType;
	}

	public void setExpenditureType(ExpenditureType expenditureType) {
		this.expenditureType = expenditureType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Monthly getMonthly() {
		return monthly;
	}

	public void setMonthly(Monthly monthly) {
		this.monthly = monthly;
	}

}
