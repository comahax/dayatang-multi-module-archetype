#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ${package}.domain.Contract;
import ${package}.domain.Document;
import ${package}.domain.SubContract;
import ${package}.domain.SubcontractInvoice;
import ${package}.webapp.utils.DocumentTagGenerater;

public class SubContractDto extends ContractDto {

	public SubContractDto(Contract contract) {
		super(contract);
	}

	/**
	 * 分包发票已开总额
	 */
	private BigDecimal subcontractInvoiceTotalAmount;

	private BigDecimal subcontractPaymentTotalAmount;

	/**
	 * 分包比例
	 */
	private BigDecimal subcontractRatio = BigDecimal.ZERO;

	private List<DocumentDto> docs = new ArrayList<DocumentDto>();

	/**
	 * 支付方式
	 */
	private String modeOfPayment;

	public SubContractDto(SubContract subContract) {
		super(subContract);
		this.subcontractRatio = subContract.getSubcontractRatio();
		this.modeOfPayment = subContract.getModeOfPayment();
		subcontractInvoiceTotalAmount = SubcontractInvoice.getTotalOfSubcontractInvoiceAmount(subContract);
		subcontractPaymentTotalAmount = SubcontractInvoice.getTotalOfSubcontractPaymentAmount(subContract);
		docs = DocumentDto.createBy(Document.findByOneTag(new DocumentTagGenerater().subContract(subContract.getId())
				.getSingleResult()));
	}

	public static List<SubContractDto> createBy(List<SubContract> subContracts) {
		if (null == subContracts) {
			return new ArrayList<SubContractDto>();
		}

		List<SubContractDto> results = new ArrayList<SubContractDto>();

		for (SubContract each : subContracts) {
			SubContractDto dto = new SubContractDto(each);
			results.add(dto);
		}

		return results;

	}

	public BigDecimal getSubcontractInvoiceTotalAmount() {
		return subcontractInvoiceTotalAmount;
	}

	public BigDecimal getSubcontractRatio() {
		return subcontractRatio;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public List<DocumentDto> getDocs() {
		return docs;
	}

	public BigDecimal getSubcontractPaymentTotalAmount() {
		return subcontractPaymentTotalAmount;
	}

}
