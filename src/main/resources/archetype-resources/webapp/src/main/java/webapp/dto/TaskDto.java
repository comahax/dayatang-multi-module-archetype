#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import com.dayatang.domain.InstanceFactory;
import ${package}.process.ProcessConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class TaskDto {

    private Long id;

    /*
     * 流程名
     */
    private String processName;

    /**
     * 事项
     */
    private String title;


    /**
     * 任务名
     */
    private String name;

    /**
     * 优先级
     */
    private int priority;

    /**
     * 委托人
     */
    private String assignee;

    /**
     * 运行时ID
     */
    private String executionId;


    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 发起者
     */
    private String initiator;

    /**
     * 任务创建时间
     */
    private Date createTime;

    /**
     * 任务逾期时间
     */
    private Date dueDate;

    /**
     * 描述
     */
    private String description;

    /**
     * 任务拥有者
     */
    private String owner;

    /**
     * 当前节点
     */
    private String activityName;

    public static List<TaskDto> create(List<Task> tasks, RuntimeService runtimeService, RepositoryService repositoryService) {
        List<TaskDto> results = new ArrayList<TaskDto>();
        if (null == tasks) {
            return results;
        }
        for (Task each : tasks) {
            results.add(new TaskDto(each, runtimeService, repositoryService));
        }
        return results;
    }

    public TaskDto(Task task, RuntimeService runtimeService, RepositoryService repositoryService) {
        id = Long.valueOf(task.getId());

        name = task.getName();

        String processDefinitionId = task.getProcessDefinitionId();
        processName = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult().getName();
        assignee = task.getAssignee();
        priority = task.getPriority();

        executionId = task.getExecutionId();
        processInstanceId = task.getProcessInstanceId();

        Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
        initiator = (String) variables.get(ProcessConstants.INITIATOR);

        title = (String) variables.get(ProcessConstants.TITLE);

        description = task.getDescription();

        createTime = task.getCreateTime();

        dueDate = task.getDueDate();

        owner = task.getOwner();

        activityName = getActivityName(runtimeService);

    }


    public TaskDto(Long id, String processName, String name, int priority, String initiator, Date createTime, Date dueDate,
                   String description, String owner, String assignee, String executionId, String processInstanceId,
                   String processDefinitionId,String   activityName) {
        super();
        this.id = id;
        this.processName = processName;
        this.name = name;
        this.priority = priority;
        this.initiator = initiator;
        this.createTime = createTime;
        this.dueDate = dueDate;
        this.description = description;
        this.owner = owner;
        this.assignee = assignee;
        this.executionId = executionId;
        this.processInstanceId = processDefinitionId;
        this.processDefinitionId = processDefinitionId;
        this.activityName = activityName;
    }

    /**
     * 当前节点
     * @return
     */
    private String getActivityName(RuntimeService runtimeService) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId);
        ProcessInstance processInstance = query.singleResult();
        String activityId = getActivityId(processInstance, runtimeService);
        RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) InstanceFactory.getInstance(ProcessEngine.class)
                .getRepositoryService();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        for (ActivityImpl activity : processDefinition.getActivities()) {
            if (activity.getId().equals(activityId)) {
                return (String) activity.getProperty("name");
            }
        }
        return "";
    }

    private String getActivityId(ProcessInstance processInstance, RuntimeService runtimeService) {
        Execution execution = runtimeService.createExecutionQuery().executionId(processInstance.getProcessInstanceId())
                .singleResult();// 执行实例
        Object property = null;
        try {
            property = PropertyUtils.getProperty(execution, "activityId");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return StringUtils.defaultString(property.toString());
    }

    public long getId() {
        return id;
    }

    public String getProcessName() {
        return processName;
    }

    public String getName() {
        return name;
    }

    public String getInitiator() {
        return initiator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public int getPriority() {
        return priority;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getExecutionId() {
        return executionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public String getTitle() {
        return title;
    }

    public String getActivityName() {
        return activityName;
    }
}
