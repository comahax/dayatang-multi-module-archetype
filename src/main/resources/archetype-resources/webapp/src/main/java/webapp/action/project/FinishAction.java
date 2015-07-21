#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;

import java.util.Date;

/**
 * User: zjzhai
 * Date: 13-4-1
 * Time: 下午3:38
 */
public class FinishAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id = 0l;

    private Date date;

    private boolean result = false;

    @Override
    public String execute() throws Exception {

        Project project = getProjectQuery().id(id).getSingleResult();
        if (null == project) {
            result = false;
            return JSON;
        }

        project.finish(date);

        projApplication.saveEntity(project);



        result = true;

        return JSON;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isResult() {
        return result;
    }

}
