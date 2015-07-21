#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.task;

import ${package}.webapp.action.process.BaseProcessAction;
import com.dayatang.utils.Slf4jLogger;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 填写任务表单
 *
 * @author yyang
 */
public class FillInTaskFormAction extends BaseProcessAction {
    private static final Slf4jLogger LOGGER = Slf4jLogger.getLogger(FillInTaskFormAction.class);

    private static final long serialVersionUID = -7828244253190565534L;

    private String taskId;

    private String taskForm;

    private long total = 0;

    private List<Task> tasks = new ArrayList<Task>();

    /**
     * 下一个任务ID
     */
    private String nextTaskId;


    @Override
    public String execute() throws Exception {

        if (StringUtils.isNotEmpty(taskId)) {
            try{
                taskForm = formService.getRenderedTaskForm(taskId).toString();
            }catch (org.activiti.engine.ActivitiObjectNotFoundException e){
                LOGGER.error("没有找到相应的任务");
            }

        }


        if (StringUtils.isNotEmpty(taskForm)) {
            taskService.claim(taskId, getCurrentUsername());
        }

        TaskQuery currentUserQuery = taskService.createTaskQuery().taskAssignee(getCurrentUsername());
        TaskQuery candidateUserQuery = taskService.createTaskQuery().taskCandidateUser(getCurrentUsername());
        tasks = new ArrayList<Task>();

        List<Task> currentUserTasks = currentUserQuery.list();
        List<Task> candidateUserTasks = candidateUserQuery.list();
        tasks.addAll(currentUserTasks);
        tasks.addAll(candidateUserTasks);

        total = currentUserQuery.count() + candidateUserQuery.count();

        nextTaskId = computeNextTaskId();

        return SUCCESS;
    }

    /**
     * 计算下一个任务的ID
     *
     * @return
     */
    private String computeNextTaskId() {
        if (total <= 1) {
            return null;
        }

        if (StringUtils.isEmpty(taskId)) {
            return tasks.get(0).getId();
        }

        for (Task task : tasks) {
            String id = task.getId();
            if (id.equals(taskId)) {
                continue;
            }
            return id;
        }
        return null;

    }

    public boolean isCurrentTask(Task task) {
        if (null == task) {
            return false;
        }
        return task.getId().equals(taskId);

    }

    public String getTaskForm() {
        return taskForm;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getTotal() {
        return total;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getNextTaskId() {
        return nextTaskId;
    }
}
