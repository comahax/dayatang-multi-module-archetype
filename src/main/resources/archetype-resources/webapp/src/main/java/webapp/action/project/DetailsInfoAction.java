#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import org.apache.struts2.convention.annotation.Result;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;

/**
 * 项目详细页，包含了编辑 User: zjzhai Date: 13-3-22 Time: 上午9:29
 */
@Result(name = "success", type = "freemarker", location = "details-info.ftl")
public class DetailsInfoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8229881384195054679L;

	private long id;

	private Project project;

	@Override
	public String execute() throws Exception {

		project = getProjectQuery().id(id).getSingleResult();

		if (null == project) {
			return NOT_FOUND;
		}

		return SUCCESS;
	}

	public String getProjectType() {
		return Dictionary.getDictionaryTextBySerialNumBerAndCategory(project.getProjectType(), DictionaryCategory.PROJECT_TYPE);
	}
	
	/**
	 * 得到业主单位名
	 * @return
	 */
	public String getOwnerName(){
		if(project.getOwnerInfo() != null && project.getOwnerInfo().getOrganization() != null){
			return project.getOwnerInfo().getOrganization().getName();	
		}
		return "";
	}
	
	/**
	 * 得到业主联系人的人名
	 * @return
	 */
	public String getOwnerContactName(){
		if(project.getOwnerInfo() != null && project.getOwnerInfo().getPerson() != null){
			return project.getOwnerInfo().getPerson().getName();	
		}
		return "";
	}
	
	/**
	 * 得到业主联系人的ID
	 * @return
	 */
	public Long getOwnerContactId(){
		if(project.getOwnerInfo() != null && project.getOwnerInfo().getPerson() != null){
			return project.getOwnerInfo().getPerson().getId();
		}
		return null;
	}
	
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

}
