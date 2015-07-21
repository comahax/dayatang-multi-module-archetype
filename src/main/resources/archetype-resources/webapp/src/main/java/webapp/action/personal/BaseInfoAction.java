#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.personal;

import ${package}.domain.Person;
import ${package}.domain.RoleAssignment;
import ${package}.process.ProcessConstants;
import ${package}.webapp.action.BaseAction;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-3
 * Time: 上午11:36
 */
public class BaseInfoAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    protected TaskService taskService;

    @Inject
    protected RuntimeService runtimeService;


    private Person person;


    @Override
    public String execute() throws Exception {

        person = getCurrentPerson();


        return SUCCESS;
    }

    /**
     * 可以编辑用户名
     */
    public boolean isEditUsernameAble() {


        //任务
        List<Task> tasks = new ArrayList<Task>();
        tasks.addAll(taskService.createTaskQuery().taskAssignee(getCurrentUsername()).list());
        tasks.addAll(taskService.createTaskQuery().taskCandidateUser(getCurrentUsername()).list());

        if (!tasks.isEmpty()) {
            return false;
        }

        //发起的在进行的流程
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                .variableValueEqualsIgnoreCase(ProcessConstants.INITIATOR_USERNAME, getCurrentUser().getUsername()).list();


        if (!processInstances.isEmpty()) {
            return false;
        }

        //用户名与邮箱名相同
        if (!getCurrentUsername().equals(getCurrentUserEmail())) {
            return false;
        }


        return true;
    }


    /**
     * 具体角色
     *
     * @return
     */
    public List<RoleAssignment> getRoleAssignments() {
        return RoleAssignment.findByUser(getCurrentUser());
    }

    public Person getPerson() {
        return person;
    }
}
