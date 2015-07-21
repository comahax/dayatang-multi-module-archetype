#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontractInvoice;

import ${package}.domain.Document;
import ${package}.domain.SubcontractInvoice;
import ${package}.query.DocumentQuery;
import ${package}.query.SubContractInvoiceQuery;
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

	private Long invoiceId = 0l;

	public String execute() {

		if (null == id || id <= 0 || null == invoiceId || invoiceId <= 0) {
			errorInfo = "文件ID或者合同的ID不正确";
			return JSON;
		}

		SubcontractInvoice invoice = SubContractInvoiceQuery.grantedScopeIn(getGrantedScope()).id(invoiceId).getSingleResult();

		if (null == invoice) {
			errorInfo = "该发票不存在";
			return JSON;
		}

		Document doc = DocumentQuery.create().id(id).getSingleResult();

		if (null == doc || !doc.containOneTag(new DocumentTagGenerater().subContractInvoice(invoiceId).getSingleResult())) {
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

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

}
