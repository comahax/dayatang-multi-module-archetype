#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.QuerySettings;

/**
 * 投票请求审批  
 * 
 * @author zjzhai
 * 
 */
@Entity
@Table(name = "bidding_request_approves")
public class BiddingRequestApprove extends AbstractCoreEntity {

	private static final long serialVersionUID = 1696381362539532804L;

	/**
	 * 是否通过
	 */
	@Column(name = "passed")
	private boolean passed = false;

	/**
	 * 审批人
	 */
	@OneToOne
	@JoinColumn(name = "approve_person_id")
	private Person approvePerson;

	/**
	 * 审批日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date approveDate;

	/**
	 * 评语
	 */
	@Lob
	@Column(name = "approve_comment")
	private String comment;

	@OneToOne
	@JoinColumn(name = "bidding_request_id")
	private BiddingRequest biddingRequest;

	public static BiddingRequestApprove getOneApproveOf(BiddingRequest biddingRequest) {
		QuerySettings<BiddingRequestApprove> settings = QuerySettings.create(BiddingRequestApprove.class).eq("biddingRequest",
				biddingRequest);
		return getRepository().getSingleResult(settings);
	}

	/**
	 * 所有已审批的投标请求
	 */
	public static List<BiddingRequest> getAllApprovedBiddingRequests() {
		String sql = "SELECT o.biddingRequest FROM BiddingRequestApprove o";
		return getRepository().find(sql, new Object[] {}, BiddingRequest.class);
	}

	public static List<Long> getAllApprovedBiddingRequestIds() {
		List<Long> results = new ArrayList<Long>();
		for (BiddingRequest each : getAllApprovedBiddingRequests()) {
			results.add(each.getId());
		}
		return results;
	}

	/**
	 * 所有通过审批的投标请求
	 */
	public static List<BiddingRequest> getAllPassedBiddingRequests() {
		String sql = "SELECT o.biddingRequest FROM BiddingRequestApprove o WHERE o.passed = true";
		return getRepository().find(sql, new Object[] {}, BiddingRequest.class);
	}

	public static List<Long> getAllPassedBiddingRequestIds() {
		List<Long> results = new ArrayList<Long>();
		for (BiddingRequest each : getAllPassedBiddingRequests()) {
			results.add(each.getId());
		}
		return results;
	}

	/**
	 * 所有未通过审批的投标请求
	 */
	public static List<BiddingRequest> getAllNotPassedBiddingRequests() {
		String sql = "SELECT o.biddingRequest FROM BiddingRequestApprove o WHERE o.passed = false";
		return getRepository().find(sql, new Object[] {}, BiddingRequest.class);
	}

	public static List<Long> getAllNotPassedBiddingRequestIds() {
		List<Long> results = new ArrayList<Long>();
		for (BiddingRequest each : getAllNotPassedBiddingRequests()) {
			results.add(each.getId());
		}
		return results;
	}

	/**
	 * 判断一个请求是否已经审批
	 */
	public static boolean isBiddingRequestApproved(BiddingRequest biddingRequest) {
		return getAllApprovedBiddingRequests().contains(biddingRequest);
	}

	public static boolean isBiddingRequestNotApproved(BiddingRequest biddingRequest) {
		return !isBiddingRequestApproved(biddingRequest);
	}

	public static boolean isBiddingRequestApproved(long biddingRequestId) {
		return getAllApprovedBiddingRequestIds().contains(biddingRequestId);
	}

	public static boolean isBiddingRequestNotApproved(long biddingRequestId) {
		return !isBiddingRequestApproved(biddingRequestId);
	}

	/**
	 * 是否审批通过
	 */
	public static boolean isBiddingRequestPassed(BiddingRequest biddingRequest) {
		return getAllPassedBiddingRequests().contains(biddingRequest);
	}

	public static boolean isBiddingRequestNotPassed(BiddingRequest biddingRequest) {
		return !isBiddingRequestPassed(biddingRequest);
	}

	public static boolean isBiddingRequestPassed(long biddingRequestId) {
		return getAllPassedBiddingRequestIds().contains(biddingRequestId);
	}

	public static boolean isBiddingRequestNotPassed(long biddingRequestId) {
		return !isBiddingRequestPassed(biddingRequestId);
	}

	public boolean isNotPassed() {
		return passed == false;
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof BiddingRequestApprove))
			return false;
		BiddingRequestApprove that = (BiddingRequestApprove) other;
		return new EqualsBuilder().append(isPassed(), that.isPassed()).append(getApproveDate(), that.getApproveDate())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(isPassed()).append(getApproveDate()).toHashCode();
	}

	public String toString() {
		return new StringBuilder().append("approveDate : ").append(getApproveDate()).append(" isPass : ").append(isPassed())
				.append(" approvePerson : ").append(getApprovePerson()).toString();
	}

	public Person getApprovePerson() {
		return approvePerson;
	}

	public void setApprovePerson(Person approvePerson) {
		this.approvePerson = approvePerson;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BiddingRequest getBiddingRequest() {
		return biddingRequest;
	}

	public void setBiddingRequest(BiddingRequest biddingRequest) {
		this.biddingRequest = biddingRequest;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

}
