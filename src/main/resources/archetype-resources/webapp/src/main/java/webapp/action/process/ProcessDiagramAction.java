#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import java.io.InputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * 获得流程实例对应的流程图
 * @author yyang
 *
 */
public class ProcessDiagramAction extends BaseProcessAction {
	private static final long serialVersionUID = 4379590211561913498L;

	private String processInstanceId;

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}


	@Override
	@Action(results = {@Result(name = "success", type = "stream", params = { "inputName", "inputStream",
			"bufferSize", "4096000" })})
	public String execute() throws Exception {
		return SUCCESS;
	}

	public InputStream getInputStream() {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
		return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
	}
}
