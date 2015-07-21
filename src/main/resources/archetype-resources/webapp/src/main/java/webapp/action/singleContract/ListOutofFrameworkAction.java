#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.FrameworkContract;
import ${package}.domain.SingleContract;
import ${package}.pager.PageList;
import ${package}.query.FrameworkContractQuery;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SingleContractDto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 框架合同外的所有单项合同 User: tune Date: 13-6-14 Time: 上午11:42
 */
public class ListOutofFrameworkAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1357592874232908289L;

	private Long frameworkId = 0l;

	/**
	 * 合同编号
	 */
	private String serialNumber;

	/**
	 * 合同名
	 */
	private String contractName;

	private List<SingleContractDto> results;

	private Long total = 0l;

	@Override
	public String execute() throws Exception {

		if (null == frameworkId || frameworkId <= 0) {
			return JSON;
		}

		FrameworkContract frameworkContract = FrameworkContractQuery.grantedScopeIn(getGrantedScope()).id(frameworkId).getSingleResult();

		if (null == frameworkContract) {
			return JSON;
		}

		SingleContractQuery query = SingleContractQuery.grantedScopeIn(getGrantedScope()).allSingleContractOutOfFramework(frameworkContract);

		if (StringUtils.isNotEmpty(serialNumber)) {
			query.serialNumber(serialNumber);
		}

		if (StringUtils.isNotEmpty(contractName)) {
			query.nameLike(contractName);
		}

		PageList<SingleContract> pageList = createPageList(query.descId());
		if (pageList != null) {
			results = SingleContractDto.createSingleContractDtosBy(pageList.getData());
			total = pageList.getTotal();
		}
		return JSON;
	}

	public Long getTotal() {
		return total;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<SingleContractDto> getResults() {
		
		if(null == results){
			return new ArrayList<SingleContractDto>();
		}
		
		return results;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public void setFrameworkId(Long frameworkId) {
		this.frameworkId = frameworkId;
	}

}
