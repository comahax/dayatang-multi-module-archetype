#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.bidding;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;

import ${package}.domain.BiddingRequest;
import ${package}.domain.DocumentTag;
import ${package}.webapp.utils.DocumentTagGenerater;
import ${package}.domain.Organization;
import ${package}.domain.OwnerOrganization;
import ${package}.process.ProcessConstants;
import ${package}.webapp.action.UploadBaseAction;
import com.dayatang.utils.CollectionUtils;
import com.dayatang.utils.Slf4jLogger;

@Result(name = "success", type = "json")
public class BiddingRequestSubmitAction extends UploadBaseAction {

	private static final long serialVersionUID = 6414237745449212314L;
	private static final Slf4jLogger LOGGER = Slf4jLogger.getLogger(BiddingRequestSubmitAction.class);

	@Inject
	private IdentityService identityService;

	@Inject
	private RuntimeService runtimeService;

	private BiddingRequest biddingRequest;

	// 业主单位
	private long ownerId;

	private SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormat());

	private boolean result = false;

	public String execute() throws IOException {
		if (biddingRequest == null) {
			return WORKTABLE;
		}
		biddingRequest.setOwner(OwnerOrganization.get(ownerId));
		biddingRequest = projApplication.originatingBiddingRequest(biddingRequest, getCurrentPerson());
		saveDocuments(biddingRequest.getId());
		result = createProcessInstance(biddingRequest);
		return SUCCESS;
	}

	// 保存附件
	private void saveDocuments(long biddingRequestId) throws IOException {
		Set<DocumentTag> tags = new DocumentTagGenerater().biddingRequest(biddingRequestId).generate();
		saveDocumentsNowWith(tags);
	}

	private boolean createProcessInstance(BiddingRequest biddingRequest) {
		try {
			identityService.setAuthenticatedUserId(getCurrentUsername());
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
					ProcessConstants.BIDDING_REQUEST_PROCESS_KEY, biddingRequest.getId().toString(), createProcessVariables());
			LOGGER.debug("{} start a processinstance: {} at {}", getCurrentUsername(), processInstance, new Date());
		} catch (Exception e) {
			biddingRequest.remove();
			return false;
		}
		return true;
	}

	private Map<String, Object> createProcessVariables() {
		Map<String, Object> results = new HashMap<String, Object>();
		results.put(ProcessConstants.INITIATOR_USERNAME, getCurrentUser().getUsername());
		results.put(ProcessConstants.INITIATOR, getCurrentUser().getDisplayName());
		results.put(ProcessConstants.INITIATOR_ORG_ID, getGrantedScope().getId());
		results.put(ProcessConstants.INITIATOR_ORG, getGrantedScope().getFullName());
		results.put(ProcessConstants.TITLE, biddingRequest.getProjectName());
		results.put(ProcessConstants.ATTACHMENT_KEY, CollectionUtils.join(getDocuments(), "id", ":"));
		results.put("projectName", biddingRequest.getProjectName());
		results.put("owner", getOrgName(biddingRequest.getOwner()));
		results.put("releaseDate", getDate(biddingRequest.getReleaseDate()));
		results.put("applyStartDate", getDate(biddingRequest.getSignUpDateRange().getStartDate()));
		results.put("applyEndDate", getDate(biddingRequest.getSignUpDateRange().getEndDate()));

		results.put("prequalificationStartDate", getDate(biddingRequest.getPrequalificationRange().getStartDate()));
		results.put("prequalificationEndDate", getDate(biddingRequest.getPrequalificationRange().getEndDate()));
		
		results.put("biddingDate", getDate(biddingRequest.getPartABiddingDate()));
		results.put("projectAmount", biddingRequest.getProjectAmount());
		results.put("content", biddingRequest.getContent());
		return results;
	}

	private Object getDate(Date date) {
		return date == null ? null : dateFormat.format(date);
	}

	@JSON(serialize = false)
	private Object getOrgName(Organization organization) {
		return organization == null ? null : organization.getName();
	}

	@JSON(serialize = false)
	public BiddingRequest getBiddingRequest() {
		return biddingRequest;
	}

	public void setBiddingRequest(BiddingRequest biddingRequest) {
		this.biddingRequest = biddingRequest;
	}

	public boolean isResult() {
		return result;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

}
