#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.json.annotations.JSON;

import ${package}.webapp.dto.ProcessDefinitionDto;

/**
 * 流程定义的json数据
 * 
 * @author zjzhai
 * 
 */
public class ListProcessesJsonAction extends BaseProcessAction {

	private static final long serialVersionUID = -1613610613172813191L;

	private List<ProcessDefinitionDto> results;

	public String execute() throws Exception {

		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
		if (null == processDefinitions) {
			return JSON;
		}
		results = new ArrayList<ProcessDefinitionDto>();
		for (ProcessDefinition each : processDefinitions) {

			ProcessDefinitionDto dto = new ProcessDefinitionDto(each.getId(), each.getDeploymentId(), each.getCategory(),
					each.getName(), each.getKey(), each.getDescription(), each.getVersion(), each.getResourceName(),
					each.getDiagramResourceName());
			results.add(dto);
		}

		return JSON;
	}

	@JSON(name = "rows")
	public List<ProcessDefinitionDto> getResults() {
		return results;
	}

}
