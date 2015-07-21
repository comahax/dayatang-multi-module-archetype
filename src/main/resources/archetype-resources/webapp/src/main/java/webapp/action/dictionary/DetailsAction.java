#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.dictionary;

import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-3-22
 * Time: 下午4:22
 */
public class DetailsAction extends BaseAction {

	private static final long serialVersionUID = -1357925893287546511L;
	private DictionaryCategory category;

    @Override
    public String execute() throws Exception {

        if(null == category){
            return NOT_FOUND;
        }

        return SUCCESS;
    }

    public DictionaryCategory getCategory() {
        return category;
    }

    public void setCategory(DictionaryCategory category) {
        this.category = category;
    }
}
