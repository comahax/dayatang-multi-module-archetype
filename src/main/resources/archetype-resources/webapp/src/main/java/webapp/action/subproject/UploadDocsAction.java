#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import ${package}.domain.Document;
import ${package}.domain.SubProject;
import ${package}.webapp.action.UploadBaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

import java.io.IOException;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-24
 * Time: 下午7:31
 */
public class UploadDocsAction extends UploadBaseAction {

    private static final long serialVersionUID = -5785587525189033271L;

    private long id = 0l;

    public String execute() throws IOException {
        if (id <= 0l) {
            return JSON;
        }
        SubProject project = getSubProjectQuery().id(id).getSingleResult();

        if(null == project){
            return JSON;
        }
        saveDocuments(project);
        return JSON;
    }

    // 保存附件
    private List<Document> saveDocuments(SubProject project) throws IOException {
        return saveDocumentsNowWith(createDefaultDocTagGenerater().append(DocumentTagGenerater.createSubProjectTag(project)).generate());
    }


    public void setId(long id) {
        this.id = id;
    }

}
