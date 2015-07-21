#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.task;

import ${package}.pager.Page;
import ${package}.webapp.action.process.BaseProcessAction;
import ${package}.webapp.dto.TaskDto;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * 列出用户的任务json数据
 *
 * @author zjzhai
 */
public class ListJsonAction extends BaseProcessAction {

    private static final long serialVersionUID = -5564156391377437103L;

    private List<TaskDto> results;

    private long total = 0;

    public String execute() throws Exception {

        List<Task> tasks = new ArrayList<Task>();

        TaskQuery currentUserQuery = taskService.createTaskQuery().taskAssignee(getCurrentUsername());
        TaskQuery candidateUserQuery = taskService.createTaskQuery().taskCandidateUser(getCurrentUsername());

        total = currentUserQuery.count() + candidateUserQuery.count();

        Page pager = new Page(rows, page - 1);
        int firstIndex = pager.getFirstIndex();
        int lastIndex = pager.getLastIndex();
        tasks.addAll(candidateUserQuery.listPage(firstIndex, lastIndex));
        tasks.addAll(currentUserQuery.listPage(firstIndex, lastIndex));
        results = TaskDto.create(tasks, runtimeService, repositoryService);

        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<TaskDto> getResults() {
        if (null == results) {
            return new ArrayList<TaskDto>();
        }
        return results;
    }

    public long getTotal() {
        return total;
    }
}
