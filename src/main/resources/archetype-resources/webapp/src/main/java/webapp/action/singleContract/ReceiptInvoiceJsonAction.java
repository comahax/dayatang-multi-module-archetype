#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.ReceiptInvoice;
import ${package}.domain.SingleContract;
import ${package}.pager.PageList;
import ${package}.query.ReceiptInvoiceQuery;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ReceiptInvoiceDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 *单合同下的发票管理 User: zjzhai Date: 13-8-8 Time: 下午2:37
 */
public class ReceiptInvoiceJsonAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2979479297978852869L;

	private Long contractId = 0l;

	private String serialNumber;

	private Date from;

	private Date to;

	/**
	 * 结果数
	 */
	private long total = 0l;

	/**
	 * 是否收款完成
	 */
	private Boolean completed = null;

	private List<ReceiptInvoiceDto> results;

	public String execute() {
		if (null == contractId || contractId <= 0l) {
			return JSON;
		}

		SingleContract contract = SingleContractQuery.grantedScopeIn(getGrantedScope()).id(contractId).getSingleResult();

		if (null == contract) {
			return JSON;
		}

		ReceiptInvoiceQuery query = ReceiptInvoiceQuery.grantedScopeIn(getGrantedScope());

		query.between(from, to);

		if (StringUtils.isNotEmpty(serialNumber)) {
			query.serialNumberContains(serialNumber);
		}

		PageList<ReceiptInvoice> pageList = createPageList(query.singleContract(contract).descId());

		if (null == pageList) {
			return JSON;
		}

		total = pageList.getTotal();
        List<ReceiptInvoice> data = pageList.getData();
        
		if (null != completed) {
			for (Iterator<ReceiptInvoice> it = data.iterator(); it.hasNext();) {
				ReceiptInvoice each = it.next();
				if (null == each) {
					continue;
				}
                if (completed == true && true == each.isNotReceiptCompleted() ) {
                    it.remove();
                    total--;
                }
                if (completed == false && true == each.isReceiptCompleted()) {
                    it.remove();
                    total--;
                }
			}
		}

		results = ReceiptInvoiceDto.createBy(data);

		return JSON;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}



	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<ReceiptInvoiceDto> getResults() {
		if (null == results) {
			return new ArrayList<ReceiptInvoiceDto>();
		}
		return results;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public long getTotal() {
		return total;
	}

	public void setTo(Date to) {
		this.to = to;
	}

}
