#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Project;
import ${package}.pager.PageList;
import ${package}.query.ProjectQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ProjectDto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 可进行业务操作的项目的json数据 User: zjzhai Date: 13-4-16 Time: 下午11:25
 */
public class BusinessOperationsabledAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String name;

	private List<ProjectDto> results;

	private long total = 0l;

	@Override
	public String execute() throws Exception {

		ProjectQuery query = getProjectQuery();

		if (StringUtils.isNotEmpty(name)) {
			query.nameLike(name);
		}

		PageList<Project> pageList = createPageList(query.isBusinessOperationsabled().descId());

		if (null != pageList) {

			results = ProjectDto.createListBy(pageList.getData());
			total = pageList.getTotal();
		}

		return JSON;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<ProjectDto> getResults() {
		return results;
	}

	public long getTotal() {
		return total;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
