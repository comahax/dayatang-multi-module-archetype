#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.owner;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 上午10:35
 */
public class ListAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1007005324788777847L;
	private List<Dictionary> categories;


    @Override
    public String execute() throws Exception {
        categories = Dictionary.findByCategory(DictionaryCategory.OWNER_TYPE);
        return SUCCESS;
    }

    public List<Dictionary> getCategories() {
        return categories;
    }
}
