#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.projectType;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-6-4
 * Time: 上午10:35
 */
public class ListAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8781827930804272812L;

	/**
     * 得到所有的父类型
     *
     * @return
     */
    public List<Dictionary> getParentTypes() {
        return Dictionary.findByNullParentOf(DictionaryCategory.PROJECT_TYPE);
    }

}
