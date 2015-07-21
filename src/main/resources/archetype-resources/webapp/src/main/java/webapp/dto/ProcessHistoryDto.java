#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.apache.struts2.json.annotations.JSON;

import ${package}.process.ProcessConstants;

/**
 * User: tune
 * Date: 13-6-24
 * Time: 下午4:40
 */
public class ProcessHistoryDto {

    private HistoricProcessInstance historicProcessInstance;

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

    /**
     * 描述
     */
    private String branchRemrk;

    /**
     * 是否同意
     */
    private String branchApproved;
    
    private Date startTime;
    
    private Date endTime;



    public static List<ProcessHistoryDto> create(Collection<HistoricProcessInstance> historicProcessInstances,HistoryService historyService, RuntimeService runtimeService, RepositoryService repositoryService) {
        List<ProcessHistoryDto> processInstances = new ArrayList<ProcessHistoryDto>();
        for (HistoricProcessInstance processInstance : historicProcessInstances) {
            processInstances.add(new ProcessHistoryDto(processInstance, historyService, runtimeService, repositoryService));
        }
        return processInstances;
    }

    public ProcessHistoryDto(HistoricProcessInstance instance,HistoryService historyService, RuntimeService runtimeService, RepositoryService repositoryService) {
        this.processName = repositoryService.createProcessDefinitionQuery().processDefinitionId(instance.getProcessDefinitionId()).singleResult().getName();
        
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(instance.getId()).list();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        for (HistoricVariableInstance variable : list) {
            variableMap.put(variable.getVariableName(),variable.getValue());
            this.id = variable.getProcessInstanceId();
        }
        this.branchRemrk = variableMap.get("branchRemrk").toString();
        this.startTime = instance.getStartTime();
        this.endTime = instance.getEndTime();
        Boolean approved = (Boolean) variableMap.get("branchApproved");
        this.branchApproved = approved == Boolean.TRUE ? "是" : "否";
        this.title = variableMap.get(ProcessConstants.TITLE).toString();
        this.activityName = "已完成";
        this.initiator = variableMap.get(ProcessConstants.INITIATOR).toString();
        this.historicProcessInstance = instance;
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

    public String getId() {
        return id;
    }


    public String getBranchRemrk() {
        return branchRemrk;
    }

    public String getBranchApproved() {
        return branchApproved;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getStartTime(){
        return startTime;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getEndTime(){
        return endTime;
    }

}
