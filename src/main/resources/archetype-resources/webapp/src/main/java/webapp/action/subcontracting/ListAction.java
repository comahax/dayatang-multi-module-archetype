#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontracting;

import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;

/**
 * 显示项目的分包
 * User: zjzhai
 * Date: 13-4-12
 * Time: 上午11:07
 */
public class ListAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7091059991989716205L;

	/**
     * 项目ID
     */
    private Long id = 0l;

    private Project project;

    @Override
    public String execute() throws Exception {

        if (id <= 0l) {
            return NOT_FOUND;
        }

        project = getProjectOf(id);

        if (null == project) {
            return NOT_FOUND;
        }
        return SUCCESS;
    }

    public Project getProject() {
        return project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
