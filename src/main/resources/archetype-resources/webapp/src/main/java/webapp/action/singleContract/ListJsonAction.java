#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.Project;
import ${package}.domain.SingleContract;
import ${package}.pager.PageList;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SingleContractDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-28
 * Time: 下午2:32
 */
public class ListJsonAction extends BaseAction {

	private static final long serialVersionUID = -2757213762068298133L;

	/**
     * 项目的ID
     */
    private Long id = 0l;

    /**
     * 合同名
     */
    private String name;

    private List<SingleContractDto> results;

    private Long total = 0l;


    @Override
    public String execute() throws Exception {

        Project project = getProjectOf(id);

        SingleContractQuery query = SingleContractQuery.grantedScopeIn(getGrantedScope());

        if (project != null) {
            query.project(project);
        }

        if (StringUtils.isNotEmpty(name)) {
            name = StringUtils.trim(name);
            query.nameLike(name);
        }

        PageList<SingleContract> pageList = createPageList(query.descId());

        if (pageList != null) {
            results = SingleContractDto.createSingleContractDtosBy(pageList.getData());
            total = pageList.getTotal();
    }
        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<SingleContractDto> getResults() {
        if (null == results) {
            results = new ArrayList<SingleContractDto>();
        }
        return results;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotal() {
        return total;
    }
}
