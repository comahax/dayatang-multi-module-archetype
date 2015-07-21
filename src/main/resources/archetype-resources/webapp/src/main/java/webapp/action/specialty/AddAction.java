#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.specialty;

import ${package}.domain.Specialty;

/**
 * User: zjzhai
 * Date: 13-4-3
 * Time: 下午2:06
 */
public class AddAction extends EditBaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = -951355516894233021L;

	private boolean result = false;

    private String error_msg;

    @Override
    public String execute() throws Exception {

        Specialty specialty = new Specialty();

        if(Specialty.isSpecialtyNameExist(getName())){
            result = true;
            error_msg = getText(Specialty.THE_SPECIALTY_NAME_IS_EXIST);
            return JSON;
        }

        specialty.setName(getName());
        specialty.setRemark(getRemark());
        specialty.setSortOrder(getSortOrder());
        commonsApplication.saveEntity(specialty);
        result = true;

        return JSON;
    }

    public boolean isResult() {
        return result;
    }

    public String getError_msg() {
        return error_msg;
    }
}
