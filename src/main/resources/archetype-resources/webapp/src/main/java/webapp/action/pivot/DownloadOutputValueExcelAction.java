#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.pivot;

import ${package}.domain.InternalOrganization;
import ${package}.domain.OutputValue;
import ${package}.query.InternalOrganizationQuery;
import ${package}.query.OutputValueQuery;
import ${package}.webapp.action.BaseAction;
import com.dayatang.utils.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * 下载产值报表EXCEL
 * User: Administrator
 * Date: 13-7-24
 * Time: 下午6:24
 */
@Result(name = "success", type = "stream", params = {"inputName", "inputStream", "contentType",
        "%{contentType}", "contentDisposition", "filename=${symbol_dollar}{downloadFileName}", "bufferSize", "4096000"})
public class DownloadOutputValueExcelAction extends BaseAction {

	private static final long serialVersionUID = -5676424098980541271L;
	//年份范围
    private Date start;
    private Date end;


    //机构范围
    private Long internalScopeId = 0l;


    private InputStream inputStream;

    private String downloadFileName;


    @Override
    public String execute() throws Exception {

        InternalOrganization internalOrganization = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), internalScopeId);
        if (null == internalOrganization) {
            internalOrganization = getGrantedScope();
        }

        List<OutputValue> datas = OutputValueQuery.immediateScopeOf(internalOrganization).between(start, end).list();
        inputStream = excelApplication.outputOutputValueExcel(datas);
        downloadFileName = assignDownloadFileName(start, end, internalOrganization);

        return SUCCESS;
    }

    private String assignDownloadFileName(Date start, Date end, InternalOrganization org) {
        return DateUtils.getYear(start) + "-" + DateUtils.getMonth(start) + "~" + DateUtils.getYear(end) + "-" + DateUtils.getMonth(end) + "~" + org.getId() + ".xls";
    }

    public String getDownloadFileName() throws UnsupportedEncodingException {

        if (null == downloadFileName) {
            return null;
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String Agent = request.getHeader("User-Agent");
        if (null != Agent) {
            Agent = Agent.toLowerCase();
            if (Agent.indexOf("firefox") != -1) {
                return new String(downloadFileName.getBytes(), "iso8859-1");
            }
            if (Agent.indexOf("msie") != -1) {
                return URLEncoder.encode(downloadFileName, "UTF-8");
            }
        }
        return URLEncoder.encode(downloadFileName, "UTF-8");
    }

    public String getContentType() {
        return "application/vnd.ms-excel";
    }

    public InputStream getInputStream() {
        return inputStream;
    }
    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


    public void setInternalScopeId(Long internalScopeId) {
        this.internalScopeId = internalScopeId;
    }
}
