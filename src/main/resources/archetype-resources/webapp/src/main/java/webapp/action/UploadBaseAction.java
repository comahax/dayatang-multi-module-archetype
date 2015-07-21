#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

import com.dayatang.excel.Version;
import ${package}.domain.Document;
import ${package}.domain.DocumentTag;
import ${package}.domain.Project;
import ${package}.webapp.utils.DocumentTagGenerater;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


public abstract class UploadBaseAction extends BaseAction {
    private static final long serialVersionUID = -4308558898437747911L;

    // 附件
    private List<File> uploads = new ArrayList<File>();
    private List<String> uploadsContentType = new ArrayList<String>();
    private List<String> uploadsFileName = new ArrayList<String>();
    private List<Document> documents = new ArrayList<Document>();

    // 保存附件
    protected List<Document> saveDocuments(Project project) throws IOException {
        return saveDocumentsNowWith(createDefaultDocTagGenerater().append(DocumentTagGenerater.createProjectTag(project)).generate());
    }

    protected List<Document> saveDocumentsNowWith(Set<DocumentTag> tags) throws IOException {
        return saveDocumentsWith(tags, new Date());
    }

    protected List<Document> saveDocumentsWith(Set<DocumentTag> tags, Date uploadDate) throws IOException {
        Set<DocumentTag> result = createDefaultDocumentTags();
        if (null != tags) {
            result.addAll(tags);
        }
        documents = createDocuments(getUploads(), uploadsContentType, getUploadsFileName());
        commonsApplication.saveSomeDocuments(documents, result, uploadDate);
        return documents;
    }

    // 根据用户上传的文件生成document
    protected List<Document> createDocuments(List<File> uploads, List<String> contentTypes, List<String> uploadsFileName) {
        return Document.createBy(uploads, contentTypes, uploadsFileName);
    }

    /**
     * 得到excel的版本
     *
     * @param fileName
     * @return
     */
    protected Version getExcelVersion(String fileName) {
        return Version.of(fileName);
    }


    protected DocumentTagGenerater createDefaultDocTagGenerater() {
        return new DocumentTagGenerater().append(createDefaultDocumentTags());
    }


    public List<File> getUploads() {
        return uploads;
    }

    public void setUploads(List<File> uploads) {
        this.uploads = uploads;
    }

    public List<String> getUploadsContentType() {
        return uploadsContentType;
    }

    public void setUploadsContentType(List<String> uploadsContentType) {
        this.uploadsContentType = uploadsContentType;
    }

    public List<String> getUploadsFileName() {
        return uploadsFileName;
    }

    public void setUploadsFileName(List<String> uploadsFileName) {
        this.uploadsFileName = uploadsFileName;
    }

    public List<Document> getDocuments() {
        return documents;
    }
}
