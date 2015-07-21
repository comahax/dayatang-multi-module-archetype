#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontractInvoice;

import java.util.ArrayList;
import java.util.List;

import ${package}.domain.SubContract;
import ${package}.domain.SubcontractInvoice;
import ${package}.pager.PageList;
import ${package}.query.SubContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SubContractInvoiceDto;

public class ListJsonAction extends BaseAction {
	private static final long serialVersionUID = 8834764884712641172L;

	private Long subContractId = 0l;

	private long total = 0l;

	private List<SubContractInvoiceDto> results;

	public String execute() {

		if (null == subContractId || subContractId <= 0l) {
			return JSON;
		}

		SubContract subContract = SubContractQuery.grantedScopeIn(getGrantedScope()).id(subContractId).descId().getSingleResult();

		if (null == subContract) {
			return JSON;
		}

		PageList<SubContractInvoiceDto> pageList = createPageList(SubContractInvoiceDto.createBy(SubcontractInvoice
				.findAllBySubContract(subContract)));

		if (null == pageList) {
			return JSON;
		}
		results = pageList.getData();
		total = pageList.getTotal();

		return JSON;
	}

	public long getTotal() {
		return total;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<SubContractInvoiceDto> getResults() {
		if (null == results) {
			return new ArrayList<SubContractInvoiceDto>();
		}
		return results;
	}

	public void setSubContractId(Long subContractId) {
		this.subContractId = subContractId;
	}

}
