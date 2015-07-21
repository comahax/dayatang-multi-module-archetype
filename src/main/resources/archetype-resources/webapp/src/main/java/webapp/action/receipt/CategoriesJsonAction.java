#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receipt;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 * 返回所有的收款类型的json数据
 * User: zjzhai
 * Date: 13-5-5
 * Time: 上午2:20
 */
public class CategoriesJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4486202658364937609L;
	private List<Dictionary> results;

    public String execute() throws Exception {
        results = Dictionary.findByCategory(DictionaryCategory.RECEIPT_TYPE);
        return JSON;
    }

    public List<Dictionary> getResults() {
        return results;
    }


}
