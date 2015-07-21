#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receiptInvoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ${package}.domain.InternalOrganization;
import ${package}.domain.ReceiptInvoice;
import ${package}.pager.PageList;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ReceiptInvoiceDto;

public class ListJsonAction extends BaseAction {

	private static final long serialVersionUID = 7488637541490818323L;

	/**
	 * 某个机构下的收款
	 */
	private Long scopeOrgId = null;

	private Date from;

	private Date to;

	private List<ReceiptInvoiceDto> results;

	/**
	 * 是否已经完成收款的
	 */
	private boolean completed = false;

	/**
	 * 结果数
	 */
	private long total = 0l;

	@Override
	public String execute() throws Exception {

		InternalOrganization scope = null;
		if (scopeOrgId != null && scopeOrgId > 0l) {
			scope = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), scopeOrgId);
		}
		if (null == scope) {
			scope = getGrantedScope();
		}

		PageList<ReceiptInvoice> pageList = ReceiptInvoice.findBy(from, to, completed, scope, page, rows);

		if (null == pageList) {
			return JSON;
		}

		total = pageList.getTotal();

		results = ReceiptInvoiceDto.createBy(pageList.getData());

		return JSON;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<ReceiptInvoiceDto> getResults() {
		if (null == results) {
			return new ArrayList<ReceiptInvoiceDto>();
		}
		return results;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public long getTotal() {
		return total;
	}

	public void setScopeOrgId(Long scopeOrgId) {
		this.scopeOrgId = scopeOrgId;
	}

}
