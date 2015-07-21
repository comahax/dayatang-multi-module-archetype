#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontract;

import ${package}.domain.Document;
import ${package}.domain.SubContract;
import ${package}.query.DocumentQuery;
import ${package}.query.SubContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

/**
 * 删除分包合同的文件
 * 
 * @author zjzhai
 * 
 */
public class DestoryDocAction extends BaseAction {

	private static final long serialVersionUID = -1648756671097206001L;

	/**
	 * 文件的ID
	 */
	private Long id = 0l;

	private Long contractId = 0l;

	public String execute() {

		if (null == id || id <= 0 || null == contractId || contractId <= 0) {
			errorInfo = "文件ID或者合同的ID不正确";
			return JSON;
		}

		SubContract subContract = SubContractQuery.grantedScopeIn(getGrantedScope()).id(contractId).getSingleResult();

		if (null == subContract) {
			errorInfo = "该合同不存在";
			return JSON;
		}

		Document doc = DocumentQuery.create().id(id).getSingleResult();

		if (null == doc || !doc.containOneTag(new DocumentTagGenerater().subContract(contractId).getSingleResult())) {
			errorInfo = "该文件不存在";
			return JSON;
		}

		projApplication.removeEntity(doc);

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

}
