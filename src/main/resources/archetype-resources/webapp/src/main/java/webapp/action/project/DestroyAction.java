#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Project;
import ${package}.domain.Role;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-1
 * Time: 下午3:02
 */
public class DestroyAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2673797882671099549L;

	private Long id = 0l;


    private boolean result = false;

    @Override
    public String execute() throws Exception {

        Project project = getProjectQuery().id(id).getSingleResult();

        if (null == project) {
            result = false;
            return JSON;
        }


        //如果是超级管理员或者分公司的管理员，可以直接销毁项目
        if (Role.BRANCHADMIN.equals(getRole().getName()) || getRole().getId().equals(Role.SYSTEM_ROLE_ID) || null != project) {
            projApplication.removeEntity(project);
        }

        result = true;

        return JSON;
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

    public void setResult(boolean result) {
        this.result = result;
    }

}
