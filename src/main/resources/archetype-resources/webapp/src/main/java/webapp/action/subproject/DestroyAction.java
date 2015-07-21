#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import ${package}.domain.Project;
import ${package}.domain.SubProject;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 * 删除单点
 * User: zjzhai
 * Date: 13-4-24
 * Time: 上午8:52
 */
public class DestroyAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4618441704245684831L;

	private List<Long> id ;

    private long projectId = 0l;

    @Override
    public String execute() throws Exception {

        Project project = getProjectOf(projectId);

        if (null == project) {
            return NOT_FOUND;
        }

        if (null == id || id.isEmpty()) {
            return JSON;
        }

        for (Long each : id) {
            SubProject subProject = getSubProjectQuery().project(project).id(each).getSingleResult();
            projApplication.removeEntity(subProject);
        }

        return JSON;
    }


    public void setId(List<Long> id) {
        this.id = id;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
