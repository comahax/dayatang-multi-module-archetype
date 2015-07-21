#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.frameworkContract;

import ${package}.domain.Document;
import ${package}.domain.FrameworkContract;
import ${package}.query.FrameworkContractQuery;
import ${package}.webapp.action.document.DownloadBaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: zjzhai
 * Date: 13-8-7
 * Time: 下午2:45
 */
public class DownloadDocAction extends DownloadBaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = 7832966792784620835L;
	
	private Long frameworkId = 0l;

    @Override
    public String execute() {

        FrameworkContract frameworkContract = FrameworkContractQuery.grantedScopeIn(getGrantedScope()).id(frameworkId).getSingleResult();

        if (null == frameworkContract || !SUCCESS.equals(super.execute())) {
            return NOT_FOUND;
        }

        Document document = getDoc();

        if (!document.containOneTag(DocumentTagGenerater.createFrameworkContract(frameworkContract))) {
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
        }
        catch (FileNotFoundException e) {
        	sendExceptionMsgAdmin("文件不存在--" + getDocId());
        } catch (Exception e){
            sendExceptionMsgAdmin(e.getMessage());
        }

        return result;
    }

    public void setFrameworkId(Long frameworkId) {
        this.frameworkId = frameworkId;
    }
}
