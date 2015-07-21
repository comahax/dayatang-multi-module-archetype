#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontractPayment;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import ${package}.domain.SubContract;
import ${package}.domain.SubcontractPayment;
import ${package}.query.SubContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SubcontractPaymentDto;

public class ListJsonOfSubcontractAction extends BaseAction {
	private static final long serialVersionUID = 4046754792023208727L;

	private Long subcontractId = 0l;

	private List<SubcontractPaymentDto> results;

	public String execute() {

		if (null == subcontractId || subcontractId <= 0l) {
			errorInfo = "传入参数错误";
			return JSON;
		}

		SubContract subContract = SubContractQuery.grantedScopeIn(getGrantedScope()).id(subcontractId).getSingleResult();

		if (null == subContract) {
			errorInfo = "分包合同不存在";
			return JSON;
		}

		List<SubcontractPayment> subcontractPayments = SubcontractPayment.findBySubcontract(subContract);

		results = SubcontractPaymentDto.createBy(subcontractPayments);

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	@JSON(name = "rows")
	public List<SubcontractPaymentDto> getResults() {
		if (null == results) {
			return new ArrayList<SubcontractPaymentDto>();
		}
		return results;
	}

	public void setSubcontractId(Long subcontractId) {
		this.subcontractId = subcontractId;
	}
	
	



}
