#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.personal;

import ${package}.commons.SystemVariablesUtils;
import ${package}.domain.Document;
import ${package}.webapp.action.document.DownloadBaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * 某人的文件下载的action
 * User: zjzhai
 * Date: 4/14/13
 * Time: 4:21 PM
 */

public class DownloadDocAction extends DownloadBaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = -7025656705078663326L;

	@Override
    public String execute() {
        if (!SUCCESS.equals(super.execute())) {
            return NOT_FOUND;
        }


        Document document = getDoc();


        if (!document.containOneTag(DocumentTagGenerater.createUploadByTag(getCurrentPerson()))) {
            return NOT_FOUND;
        }

        return SUCCESS;
    }


    /**
     * 得到内容类型
     *
     * @return
     */
    public String getContentType() {
        return super.getContentTypeDefaultImpl();
    }


    public InputStream getInputStream() {
        InputStream result = null;
        try {
            result = super.getInputStreamDefaultImpl();
        } catch (FileNotFoundException e) {
            // TODO
            noticeApplication.notice(getSysMonitorEmailAddress(), "文件不存在--" + SystemVariablesUtils.getSysnoticeTitle(), "");

        }

        return result;
    }

    public void setDocId(Long docId) {
        super.setDocId(docId);
    }
}
