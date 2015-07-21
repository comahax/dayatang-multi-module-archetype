#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.contract;

import ${package}.domain.Contract;
import ${package}.pager.PageList;
import ${package}.query.ContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ContractDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有类型的合同的列表
 * User: zjzhai
 * Date: 13-4-17
 * Time: 下午9:46
 */
public class ListJsonAction extends BaseAction {

	private static final long serialVersionUID = -2529164853289155042L;

	private List<ContractDto> results = new ArrayList<ContractDto>();

    private Long total = 0l;

    @Override
    public String execute() throws Exception {

        PageList<Contract> pageList = createPageList(ContractQuery.grantedScopeIn(getGrantedScope()));

        if (null != pageList) {
            results = ContractDto.createBy(pageList.getData(), this);
            total = pageList.getTotal();
        }

        return JSON;
    }

    public Long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<ContractDto> getResults() {
        return results;
    }
}
