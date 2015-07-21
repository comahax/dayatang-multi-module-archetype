#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.document;

import ${package}.domain.Document;
import ${package}.webapp.action.BaseAction;
import org.apache.struts2.convention.annotation.Result;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 文件下载的基类
 * User: zjzhai
 * Date: 4/14/13
 * Time: 4:22 PM
 */

@Result(name = "success", type = "stream", params = {"inputName", "inputStream", "contentType",
        "%{contentType}", "contentDisposition", "filename=${symbol_dollar}{downloadFileName}", "bufferSize", "4096000"})
public abstract class DownloadBaseAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1193267535674486812L;

	private Long docId = 0l;

    protected Document doc;

    public String execute() {

        if (docId <= 0l) {
            return NOT_FOUND;
        }

        doc = Document.get(docId);

        if (null == doc) {
            return NOT_FOUND;
        }


        return SUCCESS;
    }

    public abstract InputStream getInputStream();

    public abstract String getContentType();


    /**
     * 得到内容类型
     *
     * @return
     */
    protected String getContentTypeDefaultImpl() {
        return getDoc().getContentType();
    }

    /**
     * @return
     * @throws FileNotFoundException
     */
    protected InputStream getInputStreamDefaultImpl() throws FileNotFoundException {
        return doc.getInputStream();
    }


    /**
     * 用于避免下载时文件名乱码
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getDownloadFileName() throws UnsupportedEncodingException {
        String fileName = getDoc().getName();
        return processDownloadFileName(fileName);
    }

    public Document getDoc() {

        return doc;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }
}
