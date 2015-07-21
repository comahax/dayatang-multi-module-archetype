#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Project;
import ${package}.domain.ProjectSerialNumberAssignment;

/**
 * 编辑项目的信息
 * User: zjzhai
 * Date: 13-4-8
 * Time: 下午12:54
 */
public class EditDetailAction extends EditBaseAction {

	private static final long serialVersionUID = -4538491973624769685L;
	private Long id = 0l;

    public String execute() throws Exception {
        Project project = getProjectQuery().draftOrIsNullConstructingOrg().id(id).getSingleResult();
        if (null == project) {
            return NOT_FOUND;
        }

        //如果某一条件满足就重新生成流水号
        if (!project.getResponsibleDivision().getId().equals(responsibleDivisionId)
                || !project.getArea().getId().equals(areaId)
                || !project.getStartDate().equals(startDate)
                || !project.getOwnerInfo().getOrganization().getId().equals(ownerId)) {
            if (!initBaseDetailsOfDraftProject(project)) {
                return JSON;
            }
            ProjectSerialNumberAssignment projectSerialNumberAssignment = project.computeAndSetProjectNumber();
            projApplication.saveEntity(projectSerialNumberAssignment);
        } else{
            if (!initBaseDetailsOfDraftProject(project)) {
                return JSON;
            }
        }

        projApplication.saveEntity(project);
        id = project.getId();
        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
