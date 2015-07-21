#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;


import ${package}.domain.InternalOrganization;
import ${package}.domain.InternalOrganizationCategory;
import ${package}.domain.Project;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 设置项目以哪家公司开展 User: zjzhai Date: 13-8-17 Time: 下午7:59
 */

public class SetConstructingAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4602995559326511115L;

	/**
	 * 项目ID
	 */
	private long projectId = 0l;

	/**
	 * 名义公司ID
	 */
	private long constructingOrgId = 0l;

	/**
	 * 用于返回名义公司的名称
	 */
	private String name;

	public String execute() {

		if (projectId <= 0 || constructingOrgId <= 0) {
			errorInfo = "找不到项目";
			return JSON;
		}

		Project project = getProjectQuery().isNullConstructingOrg().id(projectId).getSingleResult();

		if (null == project) {
			errorInfo = "找不到项目";
			return JSON;
		}

		InternalOrganization internalOrganization = InternalOrganizationQuery.create().id(constructingOrgId).getSingleResult();
		/**
		 * 只有子公司和总公司可作为名义开展项目
		 */
		if (null == internalOrganization || (!InternalOrganizationCategory.HEADQUARTERS.equals(internalOrganization
                .getInternalCategory()) && !InternalOrganizationCategory.SUBSIDIARY.equals(internalOrganization
                .getInternalCategory()))) {

			errorInfo = "该机构无法作为被挂靠公司";
			return JSON;
		}

		project.setConstructingOrg(internalOrganization);
		projApplication.saveEntity(project);
		name = internalOrganization.getName();

		return JSON;

	}
    public String getErrorInfo(){
        return errorInfo;
    }

	public String getName() {
		return name;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public void setConstructingOrgId(long constructingOrgId) {
		this.constructingOrgId = constructingOrgId;
	}

}
