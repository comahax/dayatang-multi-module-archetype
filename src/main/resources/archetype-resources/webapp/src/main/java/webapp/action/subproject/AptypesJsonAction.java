#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 * 得到所有接入类型的json数据
 * User: zjzhai
 * Date: 13-4-24
 * Time: 下午5:21
 */
public class AptypesJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2048247311593102586L;
	private List<Dictionary> results;

    @Override
    public String execute() throws Exception {

        results = Dictionary.findByCategory(DictionaryCategory.AP_TYPE);

        return JSON;
    }

    public List<Dictionary> getResults() {
        return results;
    }
}
