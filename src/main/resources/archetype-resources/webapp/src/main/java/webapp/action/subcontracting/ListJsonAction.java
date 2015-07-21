#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontracting;

import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ProjectSubcontractingDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 某项目的分包比例的json数据 User: zjzhai Date: 13-4-12 Time: 上午11:12
 */
public class ListJsonAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8811182834063727862L;

	private List<ProjectSubcontractingDto> results;

	/**
	 * 项目ID
	 */
	private long id = 0l;

	@Override
	public String execute() throws Exception {

		Project project = getProjectOf(id);

		if (null == project) {
			return NOT_FOUND;
		}

		results = ProjectSubcontractingDto.createBy(project.getSubcontractings());

		return JSON;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<ProjectSubcontractingDto> getResults() {
		return results;
	}

	public void setId(long id) {
		this.id = id;
	}

}
