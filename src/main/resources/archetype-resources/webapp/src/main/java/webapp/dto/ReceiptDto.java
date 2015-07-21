#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import ${package}.domain.Dictionary;
import ${package}.domain.Receipt;

/**
 * 收款 User: zjzhai Date: 13-5-4 Time: 下午10:45
 */
public class ReceiptDto {

	private Long id = 0l;

	/**
	 * 合同
	 */
	private ContractDto contract;

	/**
	 * 发票
	 */

	private ReceiptInvoiceDto invoice;

	/**
	 * 收款类型
	 */

	private String receiptType;

	/**
	 * 收款金额
	 */
	private BigDecimal amount;

	/**
	 * 收款时间
	 */
	private Date receivedDate;

	/**
	 * 备注
	 */
	private String remark;

	public ReceiptDto(Receipt receipt) {
		id = receipt.getId();
		contract = new ContractDto(receipt.getContract());
		invoice = new ReceiptInvoiceDto(receipt.getInvoice());
		receiptType = Dictionary.getDictionaryTextBySerialNumBer(receipt.getReceiptType());
		amount = receipt.getAmount();
		receivedDate = receipt.getReceivedDate();
		remark = receipt.getRemark();
	}

	public static List<ReceiptDto> createBy(Collection<Receipt> receipts) {
		List<ReceiptDto> results = new ArrayList<ReceiptDto>();

		for (Receipt each : receipts) {
			results.add(new ReceiptDto(each));
		}

		return results;
	}

	public Long getId() {
		return id;
	}

	public ContractDto getContract() {
		return contract;
	}

	public ReceiptInvoiceDto getInvoice() {
		return invoice;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getReceivedDate() {
		return receivedDate;
	}

	public String getRemark() {
		return remark;
	}
}
