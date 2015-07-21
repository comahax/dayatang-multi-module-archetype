#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.specialty;

import ${package}.domain.Specialty;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-3
 * Time: 下午4:18
 */
public class ResumeAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9109225268272141451L;

	private Long id = 0l;

    private boolean result = false;

    @Override
    public String execute() throws Exception {

        Specialty specialty = Specialty.get(id);

        if(null == specialty){
            result = false;
            return JSON;
        }

        specialty.enable();

        commonsApplication.saveEntity(specialty);

        result = true;


        return JSON;
    }

    public boolean isResult() {
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
