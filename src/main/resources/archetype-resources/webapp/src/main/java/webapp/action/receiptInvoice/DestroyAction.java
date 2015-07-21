#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receiptInvoice;

import ${package}.domain.ReceiptInvoice;
import ${package}.query.ReceiptInvoiceQuery;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai Date: 13-5-4 Time: 下午11:52
 */
public class DestroyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7688358634315273334L;
	private Long id = 0l;

	@Override
	public String execute() throws Exception {

		if (null == id || id <= 0) {
			return JSON;
		}

		ReceiptInvoice invoice = ReceiptInvoiceQuery
				.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();

		projApplication.removeEntity(invoice);

		return JSON;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
