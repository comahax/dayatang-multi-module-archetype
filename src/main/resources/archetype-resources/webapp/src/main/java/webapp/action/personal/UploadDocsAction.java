#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.personal;

import ${package}.webapp.action.UploadBaseAction;

/**
 * User: zjzhai
 * Date: 13-4-22
 * Time: 下午7:07
 */
public class UploadDocsAction extends UploadBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8974186471887745065L;

	@Override
    public String execute() {

        try {
            saveDocumentsNowWith(null);
        } catch (Exception e) {
            errorInfo = "上传失败";
            return JSON;
        }

        return JSON;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

}
