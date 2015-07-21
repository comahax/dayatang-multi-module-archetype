#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.personal;

import ${package}.domain.Document;
import ${package}.pager.PageList;
import ${package}.query.DocumentQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.DocumentDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai Date: 13-4-22 Time: 下午6:56
 */
public class DocsJsonAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8622230672615743272L;

	private String name;

	private List<DocumentDto> results;

	private Long total = 0l;

	@Override
	public String execute() throws Exception {

		PageList<Document> pageList = createPageList(DocumentQuery
				.findPersnalDocuments(getCurrentUser().getId(), name));

		if (null != pageList) {
			results = DocumentDto.createBy(pageList.getData());
			total = pageList.getTotal();
		}

		return JSON;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<DocumentDto> getResults() {
		if (null == results) {
			return new ArrayList<DocumentDto>();
		}
		return results;
	}

	
	
	public void setName(String name) {
		this.name = name;
	}

	public Long getTotal() {
		return total;
	}
}
