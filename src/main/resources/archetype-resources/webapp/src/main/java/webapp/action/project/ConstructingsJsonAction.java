#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Project;
import ${package}.domain.ProjectStatus;
import ${package}.pager.PageList;
import ${package}.query.ProjectQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ProjectDto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 在建项目的json数据 User: zjzhai Date: 13-4-16 Time: 下午11:25
 */
public class ConstructingsJsonAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3821420987532945415L;

	/**
	 * 搜索名
	 */
	private String name;

	private List<ProjectDto> results;

	private long total = 0l;

	@Override
	public String execute() throws Exception {
		ProjectQuery query = getProjectQuery().projectStatus(ProjectStatus.CONSTRUCTING);

		if (StringUtils.isNotEmpty(name)) {
			query.nameLike(name);
		}

		PageList<Project> pageList = createPageList(query);

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
