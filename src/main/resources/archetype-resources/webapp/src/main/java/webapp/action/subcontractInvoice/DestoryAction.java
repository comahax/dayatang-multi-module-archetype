#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontractInvoice;

import ${package}.domain.SubcontractInvoice;
import ${package}.query.SubContractInvoiceQuery;
import ${package}.webapp.action.BaseAction;

public class DestoryAction extends BaseAction {

	private static final long serialVersionUID = 698865956741038992L;

	private Long id = 0l;

	public String execute() {
		if (null == id || id <= 0) {
			errorInfo = "分包合同的ID不合法";
			return JSON;
		}

		SubcontractInvoice invoice = SubContractInvoiceQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();

		try {
			projApplication.removeEntity(invoice);
		} catch (Exception e) {
			errorInfo = "删除失败";
			sendExceptionMsgAdmin(e.getMessage());
			return JSON;
		}

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
