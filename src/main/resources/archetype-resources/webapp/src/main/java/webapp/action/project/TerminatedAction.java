#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Project;
import ${package}.domain.ProjectStatus;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-2
 * Time: 下午5:49
 */
public class TerminatedAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6664897280375493969L;

	private Long id = 0l;

    private boolean result = false;


    @Override
    public String execute() throws Exception {

        Project project = getProjectQuery().id(id).getSingleResult();
        if (null == project || project.isTerminatedable()) {
            result = false;
            return JSON;
        }

        project.setStatus(ProjectStatus.TERMINATED);
        projApplication.saveEntity(project);


        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public boolean isResult() {
        return result;
    }
}
