#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.frameworkContract;

import ${package}.domain.Document;
import ${package}.domain.DocumentTag;
import ${package}.domain.FrameworkContract;
import ${package}.domain.SingleContract;
import ${package}.query.FrameworkContractQuery;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 关联单项合同 User: tune Date: 13-6-14 Time: 上午11:46
 */
public class RelevanceSingleAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 单项合同ID列表
	 */
	private List<Long> singles = new ArrayList<Long>();

	/**
	 * 框架合同ID
	 */
	private Long frameworkId = 0L;

	@Override
	public String execute() throws Exception {
		FrameworkContract frameworkContract = FrameworkContractQuery.grantedScopeIn(getGrantedScope()).id(frameworkId).getSingleResult();
		List<SingleContract> singleContracts = getSingleContract();
		if (frameworkContract != null && singleContracts.size() > 0) {
			try {
				relevanceSinglecontractsByFramwork(frameworkContract, singleContracts);
			} catch (Exception e) {
				errorInfo = e.getMessage();
			}
		} else {
			errorInfo = "关联无效";
		}
		return JSON;
	}

	private void relevanceSinglecontractsByFramwork(FrameworkContract contract, List<SingleContract> singleContracts) {

		BigDecimal totalAmount = BigDecimal.ZERO;
		for (SingleContract singleContract : singleContracts) {
			if (null != singleContract.getGeneralContractAmount()) {
				totalAmount = totalAmount.add(singleContract.getGeneralContractAmount());
			}
			singleContract.setFrameworkContract(contract);
			// 如果单项合同没有设置项目，则与框架合同同一个项目
			if (null == singleContract.getProject()) {
				singleContract.setProject(contract.getProject());
			}
			projApplication.saveEntity(singleContract);
			updateSingleContractDocsToFramework(singleContract, contract);
		}
		// 更新框架合同的金额
		updateFrameworkContractByAmount(contract, totalAmount);
	}

	/**
	 * 将单项合同的文件加上框架合同的标签
	 * 
	 * @param singleContract
	 * @param frameworkContract
	 */
	private void updateSingleContractDocsToFramework(SingleContract singleContract, FrameworkContract frameworkContract) {
		List<Document> docs = Document.findByOneTag(DocumentTagGenerater.createSingleContract(singleContract));
		for (Document doc : docs) {
			DocumentTag tag = DocumentTagGenerater.createFrameworkContract(frameworkContract);
			if (doc.containOneTag(tag)) {
				continue;
			}
			doc.addTag(tag);
			commonsApplication.saveEntity(doc);
		}
	}

	

	private void updateFrameworkContractByAmount(FrameworkContract contract, BigDecimal totalAmount) {
		// 指定了框架合同的初始值，则不用再更新框架的合同金额
		if (null == contract || FrameworkContract.isAssignFirstAmount(contract)) {
			return;
		}
		contract.setGeneralContractAmount(totalAmount);
		projApplication.saveEntity(contract);
	}

	private List<SingleContract> getSingleContract() {
		List<SingleContract> results = new ArrayList<SingleContract>();
		if (null == singles) {
			return new ArrayList<SingleContract>();
		}
		for (Long single_id : singles) {
			if (null == single_id || single_id < 0) {
				continue;
			}

			SingleContract singleContract = SingleContractQuery.grantedScopeIn(getGrantedScope()).id(single_id).getSingleResult();

			if (null == singleContract) {
				continue;
			}
			results.add(singleContract);
		}
		return results;
	}

	/**
	 * 解除关联
	 * 
	 * @return
	 */
	public String unRelevance() {
		FrameworkContract frameworkContract = FrameworkContractQuery.grantedScopeIn(getGrantedScope()).id(frameworkId).getSingleResult();
		if (null == frameworkContract) {
			return JSON;
		}
		projApplication.unRelevanceSinglecontractsByFramwork(frameworkContract, getSingleContract());
		return JSON;
	}

	public void setSingles(List<Long> singles) {
		this.singles = singles;
	}

	@org.apache.struts2.json.annotations.JSON(serialize = false)
	public List<Long> getSingles() {
		return singles;
	}

	public void setFrameworkId(Long frameworkId) {
		this.frameworkId = frameworkId;
	}

	public String getErrorInfo() {
		return errorInfo;
	}
}
