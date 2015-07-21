#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.domain.InternalOrganizationCategory;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午5:39
 */
public class EditAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6185911230435149705L;


	private Long currentOrg = 0l;


    private InternalOrganizationCategory[] internalCategories = new InternalOrganizationCategory[3];

    public String execute() throws Exception {
        currentOrg = getCurrentPerson().getOrganization().getId();
        internalCategories[0] = InternalOrganizationCategory.DIVISION;
        internalCategories[1] = InternalOrganizationCategory.PROJECT_DEPARTMENT;
        internalCategories[2] = InternalOrganizationCategory.SUBSIDIARY;
        return SUCCESS;
    }

    public List<InternalOrganization> getCompanies(){
        return InternalOrganization.findAllCompanies();
    }

    public Long getCurrentOrg() {
        return currentOrg;
    }

    public InternalOrganizationCategory[] getInternalCategories() {
        return internalCategories;
    }
}
