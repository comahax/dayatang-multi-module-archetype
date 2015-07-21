#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.InternalOrganization;
import ${package}.domain.Project;
import ${package}.domain.ProjectStatus;
import ${package}.pager.PageList;
import ${package}.query.InternalOrganizationQuery;
import ${package}.query.ProjectQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ProjectDto;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目列表
 *
 * @author zjzhai
 */
public class ListJsonAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    /**
     * 负责单位
     */
    private Long responsibleDivisionId = 0l;

    /**
     * 项目名
     */
    private String projectName;
    
    /**
     * 项目状态
     */
    private ProjectStatus projectStatus;

    private List<ProjectDto> results;

    private long total = 0;

    public String execute() throws Exception {
        ProjectQuery query = getProjectQuery();
        if (StringUtils.isNotEmpty(projectName)) {
            query.nameLike(projectName);
        }

        if (null != responsibleDivisionId && responsibleDivisionId > 1) {
            InternalOrganization internalOrganization = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), responsibleDivisionId);
            query.subordinateOf(internalOrganization);
        }else{
            query.subordinateOf(getGrantedScope());
        }
        
        if(null != projectStatus){
        	query.projectStatus(projectStatus);
        }

        PageList<Project> pageList = createPageList(query.descId().enabled());
        if (null == pageList) {
            return JSON;
        }
        total = pageList.getTotal();
        results = ProjectDto.createListBy(pageList.getData());

        return JSON;
    }

    @JSON(name = "rows")
    public List<ProjectDto> getResults() {
        if (results == null) {
            results = new ArrayList<ProjectDto>();
        }
        return results;
    }

    public void setResults(List<ProjectDto> results) {
        this.results = results;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setResponsibleDivisionId(Long responsibleDivisionId) {
        this.responsibleDivisionId = responsibleDivisionId;
    }

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}
    
    
}
