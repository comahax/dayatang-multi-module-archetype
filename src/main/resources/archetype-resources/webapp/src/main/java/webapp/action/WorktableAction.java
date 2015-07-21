#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;


import ${package}.webapp.action.process.BaseProcessAction;
import org.activiti.engine.task.TaskQuery;

/**
 * 工作台
 * 
 * @author zjzhai
 * 
 */
public class WorktableAction extends BaseProcessAction {

	private static final long serialVersionUID = -5628583249318536378L;

    private long tasksTotal = 0;

	public String execute() {
        TaskQuery currentUserQuery = taskService.createTaskQuery().taskAssignee(getCurrentUsername());
        TaskQuery candidateUserQuery = taskService.createTaskQuery().taskCandidateUser(getCurrentUsername());
        tasksTotal = currentUserQuery.count() + candidateUserQuery.count();
		return SUCCESS;
	}

    public long getTasksTotal() {
        return tasksTotal;
    }
}
