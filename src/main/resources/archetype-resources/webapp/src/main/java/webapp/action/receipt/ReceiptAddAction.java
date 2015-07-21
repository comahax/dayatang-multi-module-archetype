#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receipt;

import java.math.BigDecimal;
import java.util.List;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.domain.Document;
import ${package}.domain.Receipt;
import ${package}.domain.ReceiptInvoice;
import ${package}.domain.SingleContract;
import ${package}.query.DocumentQuery;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 给收款发票添加收款
 * 
 * @author zjzhai
 * 
 */
public class ReceiptAddAction extends BaseAction {

	private static final long serialVersionUID = 7445899519618045425L;

	private long singleContractId;
	private SingleContract contract;

	/**
	 * 收款类型
	 */
	private List<Dictionary> receiptTypes;

	/*
	 * 已添加的收款发票
	 */
	private List<ReceiptInvoice> receiptInvoices;

	public String execute() throws Exception {
		contract = SingleContractQuery.grantedScopeIn(getGrantedScope()).id(singleContractId).enabled()
				.getSingleResult();
		if (null == contract) {
			return WORKTABLE;
		}
		receiptInvoices = ReceiptInvoice.findBy(contract);

		receiptTypes = Dictionary.findByCategory(DictionaryCategory.RECEIPT_TYPE);

		return SUCCESS;
	}

	/**
	 * 计算发票收款金额
	 * 
	 * @param invoice
	 * @return
	 */
	public BigDecimal totalValueOf(ReceiptInvoice invoice) {
		if (null == invoice) {
			return BigDecimal.ZERO;
		}
		return Receipt.getTotalReceiptAmountOf(invoice);

	}

	/**
	 * 找到发票的附件
	 * 
	 * @param receiptInvoice
	 * @return
	 */
	public List<Document> findInvoiceDocOf(ReceiptInvoice receiptInvoice) {
		if (null == receiptInvoice) {
			return null;
		}
		return DocumentQuery.receiptInvoice(receiptInvoice.getId());
	}

	public SingleContract getContract() {
		return contract;
	}

	public void setSingleContractId(long singleContractId) {
		this.singleContractId = singleContractId;
	}

	public List<ReceiptInvoice> getReceiptInvoices() {
		return receiptInvoices;
	}

	public List<Dictionary> getReceiptTypes() {
		return receiptTypes;
	}

}
