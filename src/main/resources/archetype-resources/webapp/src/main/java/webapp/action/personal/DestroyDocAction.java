#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.personal;

import ${package}.commons.SystemVariablesUtils;
import ${package}.domain.Document;
import ${package}.domain.DocumentTag;
import ${package}.utils.DocumentTagConstans;
import ${package}.webapp.action.document.DestroyAction;

/**
 *
 * 个人文件的删除action
 * User: zjzhai
 * Date: 4/15/13
 * Time: 4:15 AM
 */
public class DestroyDocAction extends DestroyAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6739082043879265208L;

	@Override
    public String execute() {
        String baseResult = super.execute();
        if (!SUCCESS.equals(baseResult)) {
            return baseResult;
        }

        Document document = getDoc();

        if (document.containOneTag(new DocumentTag(DocumentTagConstans.UPLOADED_BY, Long.toString(getCurrentPerson().getId())))) {
            try {
                commonsApplication.destroy(document);
            } catch (Exception e) {
                noticeApplication.notice(getSysMonitorEmailAddress(), "删除文件失败" + SystemVariablesUtils.getSysnoticeTitle(), e.getMessage());
                errorInfo = "后台出错";
            }
        }
        return JSON;
    }

    @Override
    public String getErrorInfo() {
        return errorInfo;
    }
}
