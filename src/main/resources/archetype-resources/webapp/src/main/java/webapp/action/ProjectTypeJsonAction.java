#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;

import java.util.Iterator;
import java.util.List;

/**
 * 返回所有项目类型
 * 
 * @author zjzhai
 * 
 */
public class ProjectTypeJsonAction extends BaseAction {

	private static final long serialVersionUID = -8208815496219709705L;

	private List<Dictionary> results;

	public String execute() throws Exception {
		results = Dictionary.findByCategory(DictionaryCategory.PROJECT_TYPE);

        //不显示有子类型的项目类型
        Iterator<Dictionary> it = results.iterator();
        while (it.hasNext()) {
            Dictionary dic = it.next();
            List<Dictionary> list = dic.findChildren();
            if (null != list && list.size() > 0) {
                it.remove();
            }
        }

        return JSON;
	}

    @org.apache.struts2.json.annotations.JSON(name="rows")
	public List<Dictionary> getResults() {
		return results;
	}

}
