#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontract;

import java.util.ArrayList;
import java.util.List;

import ${package}.domain.Project;
import ${package}.domain.SubContract;
import ${package}.pager.PageList;
import ${package}.query.SubContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SubContractDto;

public class ListJsonAction extends BaseAction {
	private static final long serialVersionUID = 8834764884712641172L;

	private Long projectId = 0l;

	private long total = 0l;

	private List<SubContractDto> results;

	public String execute() {

		SubContractQuery query = SubContractQuery.grantedScopeIn(getGrantedScope());

		if (null != projectId && projectId > 0) {
			Project project = getProjectOf(projectId);
			if (null != project) {
				query.project(project);
			}
		}

		PageList<SubContract> pageList = createPageList(query);

		if (null == pageList) {
			return JSON;
		}

		results = SubContractDto.createBy(pageList.getData());
		total = pageList.getTotal();

		return JSON;
	}

	public long getTotal() {
		return total;
	}

	@org.apache.struts2.json.annotations.JSON(name = "rows")
	public List<SubContractDto> getResults() {
		if (null == results) {
			return new ArrayList<SubContractDto>();
		}
		return results;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

}
