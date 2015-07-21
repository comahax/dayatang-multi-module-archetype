#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import ${package}.webapp.action.BaseAction;

/**
 * 单点工程列表
 *
 * @author zjzhai
 */
public class ListAction extends BaseAction {

    private static final long serialVersionUID = 7842876844559672520L;

    private Long id = 0l;


    public String execute() throws Exception {


        return SUCCESS;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
