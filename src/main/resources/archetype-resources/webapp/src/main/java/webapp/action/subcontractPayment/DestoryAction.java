#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontractPayment;
import ${package}.domain.SubcontractInvoice;
import ${package}.domain.SubcontractPayment;
import ${package}.query.SubContractInvoiceQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 删除分包付款
 * 
 * @author zjzhai
 * 
 */
public class DestoryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -171487138008663995L;

	/**
	 * 发票
	 */
	private long invoiceId = 0l;

	/**
	 * 收款的ID
	 */
	private long id = 0l;

	public String execute() {

		if (invoiceId <= 0l) {
			errorInfo = "传入参数不正确";
			return JSON;
		}

		SubcontractInvoice invoice = SubContractInvoiceQuery.grantedScopeIn(getGrantedScope()).id(invoiceId).getSingleResult();

		if (null == invoice) {
			errorInfo = "该发票不存在";
			return JSON;
		}

		SubcontractPayment payment = SubcontractPayment.findByInvoiceAndId(invoice, id);

		projApplication.removeEntity(payment);

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setId(long id) {
		this.id = id;
	}

}
