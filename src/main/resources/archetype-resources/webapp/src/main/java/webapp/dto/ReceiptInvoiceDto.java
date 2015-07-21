#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.commons.BigDecimalUtils;
import ${package}.domain.Receipt;
import ${package}.domain.ReceiptInvoice;
import ${package}.query.DocumentQuery;
import ${package}.webapp.utils.DocumentTagGenerater;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 收款发票 User: zjzhai Date: 13-5-3 Time: 下午10:07
 */
public class ReceiptInvoiceDto extends InvoiceDto {

	/**
	 * 本发票已经回款
	 */
	private BigDecimal totalReceipt = BigDecimal.ZERO;

	/**
	 * 回款率
	 */
	private BigDecimal receivableRatio = BigDecimal.ZERO;

	private ContractDto contract;

	private List<DocumentDto> docs = new ArrayList<DocumentDto>();

	public ReceiptInvoiceDto(ReceiptInvoice invoice) {
		super(invoice);
		contract = new ContractDto(invoice.getContract());
		totalReceipt = Receipt.getTotalReceiptAmountOf(invoice);
		receivableRatio = BigDecimalUtils.percentage(totalReceipt ,getAmount());
		docs = DocumentDto.createBy(DocumentQuery
				.findByTags(new DocumentTagGenerater().receiptInvoice(
						invoice.getId()).generate()));
	}

	public static List<ReceiptInvoiceDto> createBy(
			Collection<ReceiptInvoice> receiptInvoices) {
		List<ReceiptInvoiceDto> results = new ArrayList<ReceiptInvoiceDto>();

		if (null == receiptInvoices || receiptInvoices.isEmpty()) {
			return results;
		}

		for (ReceiptInvoice each : receiptInvoices) {
			results.add(new ReceiptInvoiceDto(each));

		}

		return results;

	}

	public List<DocumentDto> getDocs() {
		return docs;
	}

	public BigDecimal getTotalReceipt() {
		return totalReceipt;
	}

	public ContractDto getContract() {
		return contract;
	}

	public BigDecimal getReceivableRatio() {
		return receivableRatio;
	}

}
