#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.dictionary;

import ${package}.domain.Dictionary;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-3-25
 * Time: 上午11:15
 */
public class RemoveAction extends BaseAction {

	private static final long serialVersionUID = -5824214547602724739L;

	private Long id;

    private boolean result = false;

    public String execute() throws Exception {
        Dictionary dictionary = Dictionary.get(id);
        if (null == dictionary) {
            return JSON;
        }
        commonsApplication.removeEntity(dictionary);
        result = true;
        return JSON;
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
