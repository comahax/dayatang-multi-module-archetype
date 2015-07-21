#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.dayatang.domain.InstanceFactory;
import ${package}.process.ProcessConstants;

/**
 * 运行中的流程实例列表
 * @author yyang
 *
 */
public class RunningProcessesAction extends BaseProcessAction {
	
	private static final long serialVersionUID = 2644668803370797254L;

	public List<ProcessInstance> getProcessInstances() {
		return runtimeService.createProcessInstanceQuery().list();
	}
	
	public String getInitiator(ProcessInstance processInstance) {
		Map<String, Object> variables = runtimeService.getVariables(processInstance.getProcessInstanceId());
		return (String) variables.get(ProcessConstants.INITIATOR);
	}
	
	public String getTitle(ProcessInstance processInstance) {
		Map<String, Object> variables = runtimeService.getVariables(processInstance.getProcessInstanceId());
		return (String) variables.get(ProcessConstants.TITLE);
	}

	public String getProcessKey(ProcessInstance processInstance) {
		String processDefinitionId = processInstance.getProcessDefinitionId();
		return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult().getKey();
	}

	public String getProcessName(ProcessInstance processInstance) {
		String processDefinitionId = processInstance.getProcessDefinitionId();
		return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult().getName();
	}
	
	public String getActivityName(ProcessInstance processInstance) {
		String activityId = getActivityId(processInstance);
		RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) InstanceFactory.getInstance(ProcessEngine.class).getRepositoryService();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		for (ActivityImpl activity : processDefinition.getActivities()) {
			if (activity.getId().equals(activityId)) {
				return (String) activity.getProperty("name");
			}
		}
		return "";
	}
	
	private String getActivityId(ProcessInstance processInstance) {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstance.getProcessInstanceId()).singleResult();// 执行实例
		Object property = null;
		try {
			property = PropertyUtils.getProperty(execution, "activityId");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return StringUtils.defaultString(property.toString());
	}
}
