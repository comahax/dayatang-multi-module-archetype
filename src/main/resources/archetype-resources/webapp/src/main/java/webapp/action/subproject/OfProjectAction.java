#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ProjectDto;

/**
 * User: zjzhai
 * Date: 13-4-23
 * Time: 上午10:46
 */
public class OfProjectAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3716802897203155809L;

	private Long id = 0l;

    private ProjectDto project;

    @Override
    public String execute() throws Exception {

        Project projectFoo = getProjectOf(id);

        if (null == projectFoo) {
            return NOT_FOUND;
        }

        project = new ProjectDto(projectFoo);

        return SUCCESS;
    }

    public ProjectDto getProject() {
        return project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
