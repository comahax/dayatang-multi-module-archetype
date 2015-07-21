#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ${package}.query.BiddingQuery;

/**
 * 标的实体
 * 
 * @author zjzhai
 * 
 */
@Entity
@Table(name = "biddings")
public class Bidding extends AbstractCoreEntity {

	private static final long serialVersionUID = 9121272591832148791L;

	/**
	 * 项目名 冗余
	 */
	@Column(name = "project_name")
	private String projectName;

	/**
	 * 业主单位
	 */
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private OwnerOrganization owner;

	/**
	 * 项目金额
	 */
	@Column(name = "project_amount")
	private BigDecimal projectAmount;
	
	
	/**
	 * 报名时间范围
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "startDate", column = @Column(name = "signup_start_date")),
		@AttributeOverride(name = "endDate", column = @Column(name = "signup_end_date")) })
	private TimeRange signUpDateRange;

	/**
	 * 资格预审时间范围
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "startDate", column = @Column(name = "prequalification_start_date")),
			@AttributeOverride(name = "endDate", column = @Column(name = "prequalification_end_date")) })
	private TimeRange prequalificationRange;
	

	/**
	 * 报名时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "apply_date")
	private Date applyDate;
	
	/**
	 * 资格预审时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "prequalification_date")
	private Date prequalificationDate;

	/**
	 * 委托人
	 */
	@ManyToOne
	@JoinColumn(name = "principal_id")
	private Person principal;

	/**
	 * 保证金信息
	 */
	@Embedded
	private EarnestMoneyInfo earnestMoneyInfo = new EarnestMoneyInfo();

	/**
	 * 实际投标日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "tender_date")
	private Date tenderDate;

	/**
	 * 投标状态
	 */
	@Enumerated(EnumType.STRING)
	private BiddingStatus biddingStatus = BiddingStatus.BIDDING_UNKNOW;

	/**
	 * 当中标后，可设置负责部门，负责部门才有权利立项
	 */
	@ManyToOne
	@JoinColumn(name = "bidding_responsible_division_id")
	private InternalOrganization responsibleDivision;

	/**
	 * 未报名
	 */
	public Boolean isNotApplied() {
		return !isApplied();
	}

	/**
	 * 已报名
	 */
	public Boolean isApplied() {
		return (applyDate != null);
	}

	/**
	 * 以投标结果未知
	 */
	public Boolean isUnkown() {
		return BiddingStatus.BIDDING_UNKNOW.equals(biddingStatus);
	}

	/**
	 * 中标
	 */
	public Boolean isWin() {
		return BiddingStatus.BIDDING_UNKNOW.equals(biddingStatus);
	}

	/**
	 * 不中标
	 */
	public Boolean isFail() {
		return BiddingStatus.BIDDING_FAIL.equals(biddingStatus);
	}

	/**
	 * 保证金已归还
	 * 
	 * @return
	 */
	public Boolean isEarnestMoneyReturned() {
		return earnestMoneyInfo != null && earnestMoneyInfo.getReturnTime() != null;
	}

	public Boolean isEarnestMoneyNotReturned() {
		return !isEarnestMoneyReturned();
	}

	/**
	 * 根据某个审批通过的投标请求生成一个标
	 * 
	 * @param request
	 * @return
	 */
	public static Bidding createBy(BiddingRequest request) {
		Bidding result = new Bidding();
		result.setPrequalificationRange(request.getPrequalificationRange());
		result.setSignUpDateRange(request.getSignUpDateRange());
		result.setProjectName(request.getProjectName());
		result.setProjectAmount(request.getProjectAmount());
		result.setResponsibleDivision((InternalOrganization) request.getReleasePerson().getOrganization());
		result.setOwner(request.getOwner());
		return result;
	}

	/**
	 * 查出某机构的中标率
	 * 
	 * @param org
	 * @return
	 */
	public static String rateOfSuccessfulBiddings(InternalOrganization org) {
		// 投标总数量
		double countOfBiddings = 0;
		// 中标总数量
		double countOfSuccessfulBiddings = 0;
		countOfBiddings = BiddingQuery.createResponsibleOf(org).resultsSize();
		countOfSuccessfulBiddings = BiddingQuery.createResponsibleOf(org).resultsSize();
		NumberFormat nf = NumberFormat.getPercentInstance();
		// 设定需要显示几位小数,比如33.33%百分号左边有2个小数
		nf.setMinimumFractionDigits(2);
		return nf.format(countOfSuccessfulBiddings / countOfBiddings);
	}

	public static Bidding get(long biddingId) {
		return Bidding.get(Bidding.class, biddingId);
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Bidding))
			return false;
		Bidding that = (Bidding) other;
		return new EqualsBuilder().append(biddingStatus, that.getBiddingStatus()).append(tenderDate, that.getTenderDate())
				.append(responsibleDivision, that.getResponsibleDivision()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(tenderDate).append(biddingStatus).append(responsibleDivision).toHashCode();
	}

	public String toString() {
		return "tenderDate:" + tenderDate + " responsibleDivision:" + responsibleDivision + " biddingStatus:" + biddingStatus;
	}

	public Date getTenderDate() {
		return tenderDate;
	}

	public void setTenderDate(Date tenderDate) {
		this.tenderDate = tenderDate;
	}

	public BiddingStatus getBiddingStatus() {
		return biddingStatus;
	}

	public void setBiddingStatus(BiddingStatus biddingStatus) {
		this.biddingStatus = biddingStatus;
	}

	public InternalOrganization getResponsibleDivision() {
		return responsibleDivision;
	}

	public void setResponsibleDivision(InternalOrganization responsibleDivision) {
		this.responsibleDivision = responsibleDivision;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public EarnestMoneyInfo getEarnestMoneyInfo() {
		return earnestMoneyInfo;
	}

	public void setEarnestMoneyInfo(EarnestMoneyInfo earnestMoneyInfo) {
		this.earnestMoneyInfo = earnestMoneyInfo;
	}

	public BigDecimal getProjectAmount() {
		return projectAmount;
	}

	public void setProjectAmount(BigDecimal projectAmount) {
		this.projectAmount = projectAmount;
	}

	public OwnerOrganization getOwner() {
		return owner;
	}

	public void setOwner(OwnerOrganization owner) {
		this.owner = owner;
	}

	public Date getPrequalificationDate() {
		return prequalificationDate;
	}

	public void setPrequalificationDate(Date prequalificationDate) {
		this.prequalificationDate = prequalificationDate;
	}

	public TimeRange getSignUpDateRange() {
		return signUpDateRange;
	}

	public void setSignUpDateRange(TimeRange signUpDateRange) {
		this.signUpDateRange = signUpDateRange;
	}

	public TimeRange getPrequalificationRange() {
		return prequalificationRange;
	}

	public void setPrequalificationRange(TimeRange prequalificationRange) {
		this.prequalificationRange = prequalificationRange;
	}

}
