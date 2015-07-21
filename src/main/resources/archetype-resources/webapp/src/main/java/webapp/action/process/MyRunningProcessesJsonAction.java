#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import ${package}.pager.Page;
import ${package}.process.ProcessConstants;
import ${package}.webapp.dto.ProcessInstanceDto;
import org.activiti.engine.runtime.ProcessInstanceQuery;

import java.util.List;

/**
 * 当前用户运行中的流程实例列表 -JSON格式
 *
 * @author yyang
 */
public class MyRunningProcessesJsonAction extends BaseProcessAction {

    private static final long serialVersionUID = 2644668803370797254L;

    private List<ProcessInstanceDto> results;

    private long total = 0;


    @Override
    public String execute() throws Exception {

        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery()
                .variableValueEqualsIgnoreCase(ProcessConstants.INITIATOR_USERNAME, getCurrentUser().getUsername());
        total = query.count();

        Page pager = new Page(rows, getPage() -1);
        results = ProcessInstanceDto.create(query.listPage(pager.getFirstIndex(), pager.getLastIndex()), runtimeService, repositoryService);
        return JSON;
    }

    public long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<ProcessInstanceDto> getResults() {
        return results;
    }
}
