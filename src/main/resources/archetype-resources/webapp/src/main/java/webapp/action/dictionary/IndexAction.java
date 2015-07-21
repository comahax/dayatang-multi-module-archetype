#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.dictionary;

import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

public class IndexAction extends BaseAction {

    private static final long serialVersionUID = -5329294527272564350L;

    private List<DictionaryCategory> dictionaryCategories = new ArrayList<DictionaryCategory>();

    @Override
    public String execute() throws Exception {
        for (DictionaryCategory category : DictionaryCategory.values()) {
            if (category.equals(DictionaryCategory.PROJECT_TYPE)) {
                continue;
            }
            dictionaryCategories.add(category);
        }
        return SUCCESS;
    }

    public List<DictionaryCategory> getDictionaryCategories() {
        return dictionaryCategories;
    }
}
