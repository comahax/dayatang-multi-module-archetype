#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import java.util.List;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

/**
 * 立项申请
 * 
 * @author zjzhai
 */
public class PreAction extends BaseAction {

	private static final long serialVersionUID = 8693898622004307333L;

	/**
	 * 自定义成本
	 */
	private List<Dictionary> customBudgets;

	public String execute() throws Exception {

		customBudgets = Dictionary.findByCategory(DictionaryCategory.CUSTOM_BUDGET_TYPE);

		return SUCCESS;
	}

	public List<Dictionary> getCustomBudgets() {
		return customBudgets;
	}

}
