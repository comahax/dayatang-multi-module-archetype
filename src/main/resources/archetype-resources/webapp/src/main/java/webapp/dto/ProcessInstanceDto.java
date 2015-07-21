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
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * User: zjzhai
 * Date: 13-6-5
 * Time: 下午9:22
 */
public class ProcessInstanceDto {

    /**
     * 实例ID
     */
    private String id;


    /**
     * 流程名称
     */
    private String processName;

    /**
     * 事项
     */
    private String title;


    /**
     * 当前节点
     */
    private String activityName;

    /**
     * 发起人
     */
    private String initiator;

    public static List<ProcessInstanceDto> create(Collection<ProcessInstance> instances, RuntimeService runtimeService, RepositoryService repositoryService) {
        List<ProcessInstanceDto> results = new ArrayList<ProcessInstanceDto>();
        for (ProcessInstance each : instances) {
            results.add(new ProcessInstanceDto(each, runtimeService, repositoryService));
        }
        return results;
    }

    public ProcessInstanceDto(ProcessInstance instance, RuntimeService runtimeService, RepositoryService repositoryService) {
        Map<String, Object> variables = runtimeService.getVariables(instance.getId());
        id = instance.getId();
        String processDefinitionId = instance.getProcessDefinitionId();
        processName = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult().getName();
        title = (String) variables.get(ProcessConstants.TITLE);
        activityName = getActivityName(instance, runtimeService);
        initiator = (String) variables.get(ProcessConstants.INITIATOR);
    }

    /**
     * 当前节点
     *
     * @param processInstance
     * @return
     */
    private String getActivityName(ProcessInstance processInstance, RuntimeService runtimeService) {
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

    public String getId() {
        return id;
    }

    public String getProcessName() {
        return processName;
    }

    public String getTitle() {
        return title;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getInitiator() {
        return initiator;
    }
}
