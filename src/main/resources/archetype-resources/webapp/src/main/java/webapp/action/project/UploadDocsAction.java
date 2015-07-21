#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Document;
import ${package}.domain.Project;
import ${package}.webapp.action.UploadBaseAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 提交与项目有关的文档
 *
 * @author zjzhai
 */
public class UploadDocsAction extends UploadBaseAction {

    private static final long serialVersionUID = -5785587525189033271L;

    private long id = 0l;

    private List<Document> results = new ArrayList<Document>();

    public String execute() throws IOException {
        if (id <= 0l) {
            return JSON;
        }
        Project project = getProjectOf(id);

        if(null == project){
            return JSON;
        }

        results = saveDocuments(project);
        return JSON;
    }



    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<Document> getResults() {
        return results;
    }

    public void setId(long id) {
        this.id = id;
    }
}
