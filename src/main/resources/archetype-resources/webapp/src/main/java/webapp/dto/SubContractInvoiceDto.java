#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.SubcontractInvoice;
import ${package}.query.DocumentQuery;
import ${package}.webapp.utils.DocumentTagGenerater;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分包发票 User: zjzhai Date: 13-5-3 Time: 下午10:07
 */
public class SubContractInvoiceDto extends InvoiceDto {

	private SubContractDto contract;



	/**
	 * 分包发票已付款总额
	 */
	private BigDecimal subcontractPaymentTotalAmount;

	private List<DocumentDto> docs = new ArrayList<DocumentDto>();

	public SubContractInvoiceDto(SubcontractInvoice invoice) {
		super(invoice);
		contract = new SubContractDto(invoice.getContract());
		subcontractPaymentTotalAmount = invoice.getTotalOfSubcontractPaymentAmount();
		docs = DocumentDto.createBy(DocumentQuery.findByTags(new DocumentTagGenerater().subContractInvoice(invoice.getId())
				.generate()));
	}

	public static List<SubContractInvoiceDto> createBy(Collection<SubcontractInvoice> invoices) {
		List<SubContractInvoiceDto> results = new ArrayList<SubContractInvoiceDto>();
		if (null == invoices || invoices.isEmpty()) {
			return results;
		}
		for (SubcontractInvoice each : invoices) {
			results.add(new SubContractInvoiceDto(each));
		}
		return results;
	}

	public List<DocumentDto> getDocs() {
		return docs;
	}

	public SubContractDto getContract() {
		return contract;
	}

	

	public BigDecimal getSubcontractPaymentTotalAmount() {
		return subcontractPaymentTotalAmount;
	}

}
