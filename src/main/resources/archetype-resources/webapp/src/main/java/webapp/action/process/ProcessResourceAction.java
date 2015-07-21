#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * 根据发布ID和资源名称获取流程资源
 * @author yyang
 *
 */
public class ProcessResourceAction extends BaseProcessAction {
	private static final long serialVersionUID = 4379590211561913498L;
	private String deploymentId;
	private String resourceName;

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Action(results = {@Result(name = "success", type = "stream", params = { "inputName", "inputStream",
			"bufferSize", "4096000" })})
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public InputStream getInputStream() {
		return repositoryService.getResourceAsStream(deploymentId, resourceName);
	}
	
}
