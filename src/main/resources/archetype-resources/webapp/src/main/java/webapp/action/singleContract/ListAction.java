#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-28
 * Time: 下午2:25
 */
public class ListAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6912117039609689415L;
	private List<Project> projects;

    @Override
    public String execute() throws Exception {

        projects = getProjectQuery().isBusinessOperationsabled().list();

        return SUCCESS;
    }
    
    
    public List<Dictionary> getReceiptTypes() {
		return Dictionary.findByCategory(DictionaryCategory.RECEIPT_TYPE);
	}

	public String getReceiptTypeText(String serialNumber) {
		return Dictionary.getDictionaryTextBySerialNumBerAndCategory(serialNumber, DictionaryCategory.RECEIPT_TYPE);
	}

    public List<Project> getProjects() {
        return projects;
    }
}
