#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.webapp.action.BaseAction;

/**
 * 项目文件列表
 * User: zjzhai
 * Date: 13-4-11
 * Time: 下午8:28
 */
public class DocsAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7963844212361087252L;
	private Long id = 0l;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
