#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receipt;

import ${package}.domain.Receipt;
import ${package}.domain.ReceiptInvoice;
import ${package}.query.ReceiptInvoiceQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ReceiptDto;

import java.util.List;

/**
 * User: zjzhai Date: 13-5-5 Time: 上午7:50
 */
/**
 * @author Administrator
 * 
 */
public class ListOfInvoiceJsonAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1050870322577243264L;

	/**
	 * 发票的ID
	 */
	private long invoiceId = 0l;

	private List<ReceiptDto> results;

	@Override
	public String execute() throws Exception {

		if (invoiceId <= 0) {
			errorInfo = "该发票不存在";
			return JSON;
		}

		ReceiptInvoice invoice = ReceiptInvoiceQuery.grantedScopeIn(getGrantedScope()).id(invoiceId).getSingleResult();

		if (null == invoice) {
			errorInfo = "该发票不存在";
			return JSON;
		}

		results = ReceiptDto.createBy(Receipt.findByReceiptInvoice(invoice));

		return JSON;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<ReceiptDto> getResults() {
		return results;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

}
