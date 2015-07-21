#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ${package}.query.ExpenditureQuery;

/**
 * 项目实际支出
 * 
 * @author zjzhai
 * 
 */
@Entity
@Table(name = "expenditures")
public class Expenditure extends AbstractCoreEntity {

	private static final long serialVersionUID = -1023099268260106073L;

	@ManyToOne
	@JoinColumn(name = "projecte_id")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "subprojecte_id")
	private SubProject subProject;

	/**
	 * 支出类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "expenditure_type")
	private ExpenditureType expenditureType;

	private BigDecimal amount;

	/**
	 * 支出月度
	 */
	@Embedded
	private Monthly monthly;

	/**
	 * 支出时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "spend_date")
	private Date spendDate;

	@Lob
	private String remark;

	/**
	 * 新建一个分包支出
	 * 
	 * @param subProject
	 * @param project
	 * @param spendDate
	 * @param amount
	 * @param remark
	 * @return
	 */
	public static Expenditure createSubContractExpenditure(SubProject subProject, Project project, Date spendDate,
			BigDecimal amount, String remark) {
		Expenditure result = new Expenditure();
		result.setExpenditureType(ExpenditureType.SUBCONTRACT);
		result.setSubProject(subProject);
		result.setProject(project);
		result.setAmount(amount);
		result.changeSpendDateAndMonthly(spendDate);
		result.setRemark(remark);
		return result;
	}

	/**
	 * 设置支出时间的同时，设置月度
	 * 
	 * @param spendDate
	 * @return
	 */
	private Expenditure changeSpendDateAndMonthly(Date spendDate) {
		if (null == spendDate) {
			return this;
		}
		this.spendDate = spendDate;
		monthly = new Monthly(spendDate);
		return this;
	}

	public Expenditure() {

	}

	public Expenditure(BigDecimal amount, Date spendDate, String remark) {
		super();
		this.amount = amount;
		changeSpendDateAndMonthly(spendDate);
		this.remark = remark;
	}

	public Expenditure(Project project, ExpenditureType expenditureType, BigDecimal amount, Date spendDate, String remark) {
		super();
		this.project = project;
		this.expenditureType = expenditureType;
		this.amount = amount;
		changeSpendDateAndMonthly(spendDate);
		this.remark = remark;
	}

	/**
	 * 合计项目中某支出类型的支出金额
	 * 
	 * @param project
	 * @param expenditureType
	 * @return
	 */
	public static BigDecimal getTotalExpenditureOf(Project project, ExpenditureType expenditureType) {
		String sql = "SELECT SUM(o.amount) FROM Expenditure o WHERE o.project =:project AND o.expenditureType =:expenditureType";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("expenditureType", expenditureType);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 合计项目某个月的支出金额
	 * 
	 * @param project
	 * @param monthly
	 * @return
	 */
	public static BigDecimal getTotalExpenditureOf(Project project, Monthly monthly) {
		String sql = "SELECT SUM(o.amount) FROM Expenditure o WHERE o.project =:project AND o.monthly =:monthly";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("monthly", monthly);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 合计项目某月份，某支出类型的支出金额
	 * 
	 * @param project
	 * @param expenditureType
	 * @return
	 */
	public static BigDecimal getTotalExpenditureOf(Project project, Monthly monthly, ExpenditureType expenditureType) {
		String sql = "SELECT SUM(o.amount) FROM Expenditure o WHERE o.project =:project AND o.monthly =:monthly AND o.expenditureType =:expenditureType ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("monthly", monthly);
		params.put("expenditureType", expenditureType);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 合计项目的支出金额
	 * 
	 * @param project
	 * @param expenditureType
	 * @return
	 */
	public static BigDecimal getTotalExpenditureOf(Project project) {
		String sql = "SELECT SUM(o.amount) FROM Expenditure o WHERE o.project =:project";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 某个单点的所有分包付款总和
	 * 
	 * @param subProject
	 * @return
	 */
	public static BigDecimal getTotalSubContractExpenditureOf(SubProject subProject) {
		String sql = "SELECT SUM(o.amount) FROM Expenditure o WHERE o.subProject =:subProject AND o.expenditureType =:expenditureType";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subProject", subProject);
		params.put("expenditureType", ExpenditureType.SUBCONTRACT);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 所出某个单点的所有分包支出
	 * 
	 * @return
	 */
	public static List<Expenditure> getSubContractExpenditures(SubProject subProject) {
		ExpenditureQuery query = ExpenditureQuery.create().expenditureType(ExpenditureType.SUBCONTRACT).subProject(subProject);
		return getRepository().find(query.build());
	}

	/**
	 * 计算某单项合同下的单点分付款合计
	 * 
	 * @param contract
	 * @return
	 */
	public static BigDecimal getTotalSubContractExpenditureOf(SingleContract contract) {
		String sql = "SELECT SUM(o.amount) FROM Expenditure o WHERE o.subProject.singleContract =:contract AND o.expenditureType =:expenditureType";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract", contract);
		params.put("expenditureType", ExpenditureType.SUBCONTRACT);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 得到项目所有已报的月份
	 * 
	 * @param project
	 * @return
	 */
	public static List<Monthly> getAllReportedMonthlyOf(Project project) {
		String sql = "SELECT DISTINCT o.monthly FROM Expenditure o WHERE o.project = :project ORDER BY o.monthly ASC";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		return getRepository().find(sql, params, Monthly.class);
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Expenditure)) {
			return false;
		}
		Expenditure that = (Expenditure) other;
		return new EqualsBuilder().append(this.getProject(), that.getProject())
				.append(this.getSubProject(), that.getSubProject()).append(amount, that.getAmount())
				.append(spendDate, that.getSpendDate()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getProject()).append(getSubProject()).append(amount).append(spendDate)
				.toHashCode();
	}

	public String toString() {
		return new StringBuilder().append(project).append(amount).append(spendDate).toString();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public SubProject getSubProject() {
		return subProject;
	}

	public void setSubProject(SubProject subProject) {
		this.subProject = subProject;
	}

	public ExpenditureType getExpenditureType() {
		return expenditureType;
	}

	public void setExpenditureType(ExpenditureType expenditureType) {
		this.expenditureType = expenditureType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getSpendDate() {
		return spendDate;
	}

	public void setSpendDate(Date spendDate) {
		this.spendDate = spendDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Monthly getMonthly() {
		return monthly;
	}

	public void setMonthly(Monthly monthly) {
		this.monthly = monthly;
	}

}
