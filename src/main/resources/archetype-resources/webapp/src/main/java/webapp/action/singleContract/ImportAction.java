#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.SingleContract;
import ${package}.excel.ImportResult;
import ${package}.webapp.action.UploadBaseAction;
import ${package}.webapp.dto.ImportResultDto;
import ${package}.webapp.dto.SingleContractDto;

import java.io.File;
import java.io.FileInputStream;

/**
 * 导入单项合同
 * User: zjzhai
 * Date: 13-4-28
 * Time: 上午9:59
 */
public class ImportAction extends UploadBaseAction {

	private static final long serialVersionUID = 18865121903128307L;

	private ImportResultDto<SingleContractDto> result;

    private Long projectId = 0l;

    @Override
    public String execute() throws Exception {

        if (getUploads() == null || getUploads().isEmpty()) {
            return JSON;
        }

        File file = getUploads().get(0);

        String fileName = getUploadsFileName().get(0);

        try {
            FileInputStream in = new FileInputStream(file);
            ImportResult<SingleContract> importResult = excelApplication.importSingleContractRightNow(in, getExcelVersion(fileName), getGrantedScope(), getCurrentPerson());
            FileInputStream in2 = new FileInputStream(file);
            projectId = excelApplication.getProjectId(in2, getExcelVersion(fileName));
            result = new ImportResultDto<SingleContractDto>(importResult.getErrorInfos(), SingleContractDto.createSingleContractDtosBy(importResult.getEntities()));
        } catch (Exception e) {
            errorInfo = "后台出错";
            sendExceptionMsgAdmin(e.getMessage());
        }

        return JSON;
    }

    public ImportResultDto<SingleContractDto> getResult() {
        return result;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public Long getProjectId() {
        return projectId;
    }
}
