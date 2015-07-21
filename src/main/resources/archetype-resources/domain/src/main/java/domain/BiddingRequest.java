#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ${package}.query.DocumentQuery;

/**
 * 投标请求
 * 
 * @author zjzhai
 * 
 */
@Entity
@Table(name = "bidding_requests")
public class BiddingRequest extends AbstractCoreEntity {

	private static final long serialVersionUID = 5275039327877729907L;

	/**
	 * 项目名
	 */
	@Column(name = "project_name")
	private String projectName;

	/**
	 * 项目金额
	 */
	@Column(name = "project_amount")
	private BigDecimal projectAmount;

	/**
	 * 业主单位
	 */
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private OwnerOrganization owner;

	/**
	 * 降点
	 */
	@Column(name = "discount")
	private String discount;

	/**
	 * 发布人
	 */
	@ManyToOne
	@JoinColumn(name = "release_person_id")
	private Person releasePerson;

	/**
	 * 发布日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "release_date")
	private Date releaseDate;

	/**
	 * 甲方招标时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "bidding_date")
	private Date partABiddingDate;

	/**
	 * 招标公告内容
	 */
	@Lob
	private String content;

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

	public static BiddingRequest get(long id) {
		return BiddingRequest.get(BiddingRequest.class, id);
	}

	/**
	 * 彻底删除
	 */
	public void remove() {
		removeDocs();
		super.remove();
	}

	private void removeDocs() {
		for (Document each : DocumentQuery.biddingRequest(getId())) {
			each.remove();
		}
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof BiddingRequest))
			return false;
		BiddingRequest that = (BiddingRequest) other;
		return new EqualsBuilder().append(getOwner(), that.getOwner()).append(getProjectAmount(), that.getProjectAmount())
				.append(getPartABiddingDate(), that.getPartABiddingDate()).append(getReleaseDate(), that.getReleaseDate())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPartABiddingDate()).append(getReleaseDate()).toHashCode();
	}

	public String toString() {
		return new StringBuilder().append("releaceDate : ").append(this.getReleaseDate()).append("  partABiddingDate  : ")
				.append(getPartABiddingDate()).toString();
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Person getReleasePerson() {
		return releasePerson;
	}

	public void setReleasePerson(Person releasePerson) {
		this.releasePerson = releasePerson;
	}

	public Date getPartABiddingDate() {
		return partABiddingDate;
	}

	public void setPartABiddingDate(Date partABiddingDate) {
		this.partABiddingDate = partABiddingDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
