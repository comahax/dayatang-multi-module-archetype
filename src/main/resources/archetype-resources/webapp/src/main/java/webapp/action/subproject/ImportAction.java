#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import ${package}.domain.SubProject;
import ${package}.excel.ImportResult;
import ${package}.webapp.action.UploadBaseAction;
import ${package}.webapp.dto.ImportResultDto;
import ${package}.webapp.dto.SubProjectDto;

import java.io.File;
import java.io.FileInputStream;

/**
 * 使用excel的方式导入单点
 * User: zjzhai
 * Date: 13-4-22
 * Time: 下午5:18
 */
public class ImportAction extends UploadBaseAction {
    private static final long serialVersionUID = 1L;

    private ImportResultDto<SubProjectDto> result;

    @Override
    public String execute() {
        if (getUploads() == null || getUploads().isEmpty()) {
            return JSON;
        }

        File file = getUploads().get(0);

        String fileName = getUploadsFileName().get(0);

        try {
            FileInputStream in = new FileInputStream(file);
            ImportResult<SubProject> subProjectImportResult = excelApplication.importSubProjects(in, getExcelVersion(fileName), getGrantedScope(), getCurrentPerson());
            result = new ImportResultDto<SubProjectDto>(subProjectImportResult.getErrorInfos(), SubProjectDto.createBy(subProjectImportResult.getEntities()));
        } catch (Exception e) {
            errorInfo = "后台出错";
            return JSON;
        }


        return JSON;
    }

    public ImportResultDto<SubProjectDto> getResult() {
        return result;
    }

    public String getErrorInfo() {
        return errorInfo;
    }


}
