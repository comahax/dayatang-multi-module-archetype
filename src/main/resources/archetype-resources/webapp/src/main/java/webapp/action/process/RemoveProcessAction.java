#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;


/**
 * 删除流程定义
 * @author yyang
 *
 */
public class RemoveProcessAction extends BaseProcessAction {
	
	private static final long serialVersionUID = -7828244253190565534L;
	//private Slf4jLogger logger = Slf4jLogger.getLogger(getClass());
	private String deploymentId;

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
	@Action(results = @Result(name = "success", type = "redirectAction", location = "/process/list-processes"))
	@Override
	public String execute() throws Exception {
		repositoryService.deleteDeployment(deploymentId, true);
		return SUCCESS;
	}

}
