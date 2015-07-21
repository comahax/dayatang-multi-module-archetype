#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.task;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;

import ${package}.webapp.action.process.BaseProcessAction;

/**
 * 获得当前用户的任务列表
 * @author yyang
 *
 */
public class ListAction extends BaseProcessAction {
	
	private static final long serialVersionUID = -824303250155866841L;
	private List<Task> tasks = new ArrayList<Task>();

	public List<Task> getTasks() {
		return tasks;
	}
	
	@Override
	public String execute() throws Exception {
		String currentUser = getCurrentUsername();
		tasks.addAll(taskService.createTaskQuery().taskAssignee(currentUser).list());
		tasks.addAll(taskService.createTaskQuery().taskCandidateUser(currentUser).list());
		return SUCCESS;
	}

}
