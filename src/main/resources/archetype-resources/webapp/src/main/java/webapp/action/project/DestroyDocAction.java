#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Document;
import ${package}.domain.DocumentTag;
import ${package}.utils.DocumentTagConstans;
import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;

/**
 *
 * //用于删除与某项目相关的文件
 * User: zjzhai
 * Date: 13-4-11
 * Time: 下午2:48
 */
public class DestroyDocAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7066726497796790626L;

	private Long projectId;

    /**
     * 文档ID
     */
    private Long id;


    @Override
    public String execute() throws Exception {

        Project project = getProjectQuery().id(projectId).getSingleResult();

        if(null == project){
            return NOT_FOUND;
        }
        Document document = Document.get(id);

        if(isDestroyableOfDecumentOf(document, project)){
            commonsApplication.destroy(document);
        }


        return JSON;
    }

    private boolean isDestroyableOfDecumentOf(Document document, Project project){
        return getCurrentPerson().equals(project.getCreator()) ||
                (null != document && document.containOneTag(new DocumentTag(DocumentTagConstans.PROJECT,id)));
    }


    public String getErrorInfo(){
        return errorInfo;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
