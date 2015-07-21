#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.outputvalue;

import ${package}.domain.OutputValue;
import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.OutputvalueDto;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-21
 * Time: 上午9:52
 */
public class ListJsonOfProjectAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Long id = 0l;

    private List<OutputvalueDto> results;

    private Long total = 0l;

    @Override
    public String execute() throws Exception {


        Project project = getProjectOf(id);

        if (null == project) {
            return NOT_FOUND;
        }

        results = OutputvalueDto.createBy(OutputValue.findBy(project));

        total =  (long)results.size();

        return JSON;
    }

    public Long getTotal() {
        return total;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<OutputvalueDto> getResults() {
        return results;
    }
}
