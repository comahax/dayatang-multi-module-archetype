#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import java.io.IOException;

/**
 * 填写启动流程表单
 * @author yyang
 *
 */
public class FillInStartFormAction extends BaseProcessAction {
	
	private static final long serialVersionUID = -7828244253190565534L;

	private String processDefinitionId;

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getStartForm() throws IOException {
		return (String) formService.getRenderedStartForm(processDefinitionId);
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
