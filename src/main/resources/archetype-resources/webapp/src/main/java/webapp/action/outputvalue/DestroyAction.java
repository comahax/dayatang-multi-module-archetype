#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.outputvalue;

import ${package}.domain.OutputValue;
import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-22
 * Time: 下午9:21
 */
public class DestroyAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6734360096745147664L;

	private Long id = 0l;

    private Long projectId = 0l;

    @Override
    public String execute() throws Exception {

        Project project = getProjectOf(projectId);

        if(null == project){
            return NOT_FOUND;
        }

        OutputValue outputValue = OutputValue.get(id);

        if(null != outputValue){
            projApplication.removeEntity(outputValue);
        }

        return JSON;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
