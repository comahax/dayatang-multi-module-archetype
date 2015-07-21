#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontractPayment;

import java.util.ArrayList;
import java.util.List;

import ${package}.domain.SubcontractInvoice;
import ${package}.domain.SubcontractPayment;
import ${package}.query.SubContractInvoiceQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SubcontractPaymentDto;

/**
 * 列表发票的付款
 * 
 * @author Administrator
 * 
 */
public class ListJsonOfInvoiceAction extends BaseAction {

	private static final long serialVersionUID = -4929371109102419657L;

	/**
	 * 发票的ID
	 */
	private Long invoiceId = 0l;

	private List<SubcontractPaymentDto> results;

	public String execute() {
		if (null == invoiceId || invoiceId <= 0l) {
			errorInfo = "传入参数错误";
			return JSON;
		}

		SubcontractInvoice invoice = SubContractInvoiceQuery.grantedScopeIn(getGrantedScope()).id(invoiceId).getSingleResult();

		if (null == invoice) {
			errorInfo = "该发票不存在";
			return JSON;
		}

		results = SubcontractPaymentDto.createBy(SubcontractPayment.findByInvoice(invoice));

		return JSON;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<SubcontractPaymentDto> getResults() {
		if (null == results) {
			return new ArrayList<SubcontractPaymentDto>();
		}
		return results;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

}
