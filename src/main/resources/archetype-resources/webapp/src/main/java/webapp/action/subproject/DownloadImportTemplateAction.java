#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import ${package}.excel.ExcelTemplateFactory;
import ${package}.excel.ExcelVersion;
import ${package}.webapp.action.BaseAction;

import org.apache.struts2.convention.annotation.Result;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * User: zjzhai Date: 13-4-23 Time: 下午2:17
 */
@Result(name = "success", type = "stream", params = {"inputStream", "inputStream", "contentType", "%{contentType}",
        "contentDisposition", "filename=${symbol_dollar}{downloadFileName}", "bufferSize", "4096000"})
public class DownloadImportTemplateAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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
            templateName = ExcelTemplateFactory.create().getSubProjectExcelImportTemplate(out, id);
            inputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            sendExceptionMsgAdmin("找不到单点工程导入的模板文件");
        } catch (IOException e) {
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
