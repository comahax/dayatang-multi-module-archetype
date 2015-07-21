#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.projectType;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-6-4
 * Time: 上午10:22
 */
@Result(name = "json", type = "json", params = {"contentType", "text/html", "excludeNullProperties", "false"})
public class ListJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4532982945390926636L;
	private List<Dictionary> results;

    @Override
    public String execute() throws Exception {

        results = Dictionary.findByCategory(DictionaryCategory.PROJECT_TYPE);

        return JSON;
    }

    public List<Dictionary> getResults() {
        if (null == results) {
            return new ArrayList<Dictionary>();
        }
        return results;
    }
}
