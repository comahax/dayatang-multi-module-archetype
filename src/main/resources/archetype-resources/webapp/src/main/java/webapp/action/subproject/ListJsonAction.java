#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import ${package}.domain.SubProject;
import ${package}.pager.PageList;
import ${package}.query.SubProjectQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SubProjectDto;
import org.apache.struts2.json.annotations.JSON;

import java.util.List;

/**
 * 列出所有单点工程
 *
 * @author zjzhai
 */
public class ListJsonAction extends BaseAction {

    private static final long serialVersionUID = 6115521015708602548L;

    private long total = 0l;

    private Long id = 0l;

    private List<SubProjectDto> results;

    public String execute() throws Exception {
        SubProjectQuery query = getSubProjectQuery();
        if (null != id) {
            query.projectId(id);
        }
        PageList<SubProject> pageList = createPageList(query);
        if (pageList != null) {
            results = SubProjectDto.createBy(pageList.getData());
            total = pageList.getTotal();
        }
        return JSON;
    }

    @JSON(name = "rows")
    public List<SubProjectDto> getResults() {
        return results;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

}
