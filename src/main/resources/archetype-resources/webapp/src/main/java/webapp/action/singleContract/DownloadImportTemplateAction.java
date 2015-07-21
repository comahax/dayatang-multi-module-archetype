#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.excel.ExcelTemplateFactory;
import ${package}.excel.ExcelVersion;
import ${package}.webapp.action.BaseAction;
import org.apache.struts2.convention.annotation.Result;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * User: zjzhai
 * Date: 13-4-28
 * Time: 下午3:23
 */
@Result(name = "success", type = "stream", params = {"inputStream", "inputStream", "contentType", "%{contentType}",
        "contentDisposition", "filename=${symbol_dollar}{downloadFileName}", "bufferSize", "4096000"})
public class DownloadImportTemplateAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2301477448352321730L;

	/**
     * 项目的ID
     */
    private Long id = 0l;

    private String templateName;

    private InputStream inputStream;

    @Override
    public String execute() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            templateName = ExcelTemplateFactory.create().getSingleContractExcelImportTemplate(out, id);
            inputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } 

        return SUCCESS;
    }

    /**
     * 用于避免下载时文件名乱码
     *
     * @return
     * @throws java.io.UnsupportedEncodingException
     *
     */
    public String getDownloadFileName() throws UnsupportedEncodingException {
        return processDownloadFileName(templateName);
    }

    public String getContentType() {
        return ExcelVersion.of(templateName).getContentType();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
