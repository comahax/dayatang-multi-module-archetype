#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.frameworkContract;

import ${package}.domain.FrameworkContract;
import ${package}.pager.PageList;
import ${package}.query.FrameworkContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.FrameworkContractDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有类型的合同的列表
 * User: zjzhai
 * Date: 13-4-17
 * Time: 下午9:46
 */
public class ListJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -20995618789705748L;

	private List<FrameworkContractDto> results = new ArrayList<FrameworkContractDto>();

    private Long total = 0l;

    /**
     * 合同名
     */
    private String name;

    /**
     * 编号
     */
    private String number;


    @Override
    public String execute() throws Exception {

        FrameworkContractQuery query = FrameworkContractQuery.grantedScopeIn(getGrantedScope());

        if (StringUtils.isNotEmpty(name)) {
            name = StringUtils.trim(name);
            query.nameContainsText(name);
        }

        if (StringUtils.isNotEmpty(number)) {
            number = StringUtils.trim(number);
            query.serialNumber(number);
        }

        PageList<FrameworkContract> pageList = createPageList(query.descId());

        if (null != pageList) {
            results = FrameworkContractDto.createFrameworkContractDtosBy(pageList.getData());
            total = pageList.getTotal();
        }

        return JSON;
    }

    public Long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<FrameworkContractDto> getResults() {
        return results;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
