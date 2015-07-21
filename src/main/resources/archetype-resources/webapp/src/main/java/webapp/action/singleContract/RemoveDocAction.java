#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.Document;
import ${package}.domain.SingleContract;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

/**
 * 删除框架合同的文件 User: zjzhai Date: 13-8-7 Time: 下午9:58
 */
public class RemoveDocAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long docId = 0l;

	private Long contractId = 0l;

	public String execute() {
		if (null == docId || docId <= 0l || null == contractId || contractId <= 0l) {
			return JSON;
		}

		SingleContract contract = SingleContractQuery.grantedScopeIn(getGrantedScope()).id(contractId).getSingleResult();
		if (null == contract) {
			return JSON;
		}
		Document doc = Document.get(docId);
		if (doc.containOneTag(DocumentTagGenerater.createSingleContract(contract))) {
			commonsApplication.removeEntity(doc);
		}

		return JSON;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	
}
