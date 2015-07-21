#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;

import ${package}.process.ProcessInstanceTraceService;

/**
 * 获得流程图的细节信息，主要用于在流程图上添加修饰
 * @author yyang
 *
 */
@Action(results = @Result(name = "success", type = "json"))
public class ProcessDiagramDetailAction extends BaseProcessAction {
	private static final long serialVersionUID = 4379590211561913498L;

	private String processInstanceId;
	
	@Inject
	private ProcessInstanceTraceService traceService;

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setTraceService(ProcessInstanceTraceService traceService) {
		this.traceService = traceService;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	@JSON
	public List<Map<String, Object>> getData() {
		List<Map<String, Object>> activityInfos = traceService.getProcessDiagramDetail(processInstanceId);
		return activityInfos;
		
	}
}
