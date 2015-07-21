#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import java.util.List;

import org.activiti.engine.history.HistoricProcessInstanceQuery;

import ${package}.pager.Page;
import ${package}.webapp.dto.ProcessHistoryDto;

/**
 * 历史流程
 * User: zjzhai
 * Date: 13-6-18
 * Time: 上午10:49
 */
public class MyHistoriesAction extends BaseProcessAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4914926006160681753L;

	private List<ProcessHistoryDto> results;

    private long total = 0;

    @Override
    public String execute() throws Exception {

        Page pager = new Page(rows, page - 1);
        int firstIndex = pager.getFirstIndex();
        int lastIndex = pager.getLastIndex();
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery()
                .finished().orderByProcessDefinitionId().orderByProcessInstanceStartTime().desc();
        total = query.count();

        results= ProcessHistoryDto.create(query.listPage(firstIndex, lastIndex),historyService, runtimeService, repositoryService);
        return JSON;
    }

    public long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<ProcessHistoryDto> getResults() {
        return results;
    }
}
