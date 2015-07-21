#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.specialty;

import ${package}.domain.Specialty;

/**
 * User: zjzhai
 * Date: 13-4-3
 * Time: 下午1:57
 */
public class EditAction extends EditBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3808719465120426950L;

	private Long id = 0l;

    private String error_msg;

    private boolean result = false;

    @Override
    public String execute() throws Exception {

        Specialty specialty = Specialty.get(id);

        if (null == specialty) {
            result = false;
            return JSON;
        }
        if (!specialty.getName().equals(getName()) && Specialty.isSpecialtyNameExist(getName())) {
            error_msg = getText(Specialty.THE_SPECIALTY_NAME_IS_EXIST);
            return JSON;
        }

        specialty.setName(getName());
        specialty.setRemark(getRemark());
        specialty.setSortOrder(getSortOrder());

        projApplication.saveEntity(specialty);
        result = true;

        return JSON;
    }

    public String getError_msg() {
        return error_msg;
    }

    public boolean isResult() {
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
