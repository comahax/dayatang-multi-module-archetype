#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.dictionary;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.pager.PageList;
import ${package}.webapp.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai Date: 13-3-22 Time: 下午4:24
 */
public class ListJsonAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 749731385999896543L;

	private DictionaryCategory category;

	private List<Dictionary> results = new ArrayList<Dictionary>();

	private long total = 0;

	public String execute() {
		if (null == category) {
			return JSON;
		}
		PageList<Dictionary> pageList = createPageList(Dictionary.findByCategory(category));
		if (null != pageList) {
			total = pageList.getTotal();
			results = pageList.getData();
		}

		return JSON;
	}

	public DictionaryCategory getCategory() {
		return category;
	}

	public void setCategory(DictionaryCategory category) {
		this.category = category;
	}

	public long getTotal() {
		return total;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<Dictionary> getResults() {
		if (null == results) {
			return new ArrayList<Dictionary>();
		}

		return results;
	}
}
