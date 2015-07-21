#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.specialty;

import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-3
 * Time: 下午2:16
 */
// TODO 后续实现
public class DestroyAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7015448580372314581L;

	private Long id = 0l;

    private boolean result = false;

    @Override
    public String execute() throws Exception {


        return JSON;
    }

    public boolean isResult() {
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
