#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.dayatang.excel.Version;
import ${package}.domain.Person;
import ${package}.domain.PersonUser;
import ${package}.domain.RoleAssignment;
import ${package}.excel.DataExcelHandlerWriter;
import ${package}.excel.ExcelVersion;
import ${package}.excel.PeopleExcelImportResult;
import ${package}.webapp.action.UploadBaseAction;

/**
 * 批量导入人员
 * 
 * @author zjzhai
 * 
 */

@Results(value = { @Result(name = "importerror", type = "stream", params = { "inputName", "inputStream", "contentType", "%{contentType}", "contentDisposition", "filename=${symbol_dollar}{downloadFileName}", "bufferSize", "4096000" }) })
public class ImportPeopleAction extends UploadBaseAction {
	private static final long serialVersionUID = 5510294927060744716L;

	private String templateName;

	private InputStream inputStream;

	@Override
	public String execute() throws Exception {

		if (null == getUploads() || getUploads().isEmpty() || null == getUploads().get(0)) {
			errorInfo = "请上传文件！";
			return JSON;
		}

		File file = getUploads().get(0);
		String fileName = getUploadsFileName().get(0);
		FileInputStream in = new FileInputStream(file);
		PeopleExcelImportResult peopleExcelImportResult = excelApplication.importPeopleExcel(in, Version.of(fileName), getGrantedScope(), securityApplication.encodePassword(getSystemDefaultPassword()));

		if (peopleExcelImportResult.hasContent()) {
			List<Person> people = peopleExcelImportResult.getPeople();
			List<PersonUser> users = peopleExcelImportResult.getUsers();
			List<RoleAssignment> assignments = peopleExcelImportResult.getAssignments();
			for (int i = 0; i < people.size(); i++) {
				Person person = people.get(i);
				PersonUser user = users.get(i);
				RoleAssignment assignment = assignments.get(i);
				try {
					projApplication.saveSomeEntities(person, user, assignment);
				} catch (Exception e) {
					LOG.error("imort_people", e.getMessage());

				}

			}
		}

		if (peopleExcelImportResult.hasError()) {
			String destFileName = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator) + 1) + "请修改好重新上传-" + fileName;
			templateName = fileName;
			File destFile = new File(destFileName);
			file.renameTo(destFile);
			DataExcelHandlerWriter.writePeopleData(destFile, peopleExcelImportResult.getErrorData());
			inputStream = new FileInputStream(destFile);
			return "importerror";
		}

		return JSON;

	}

	public String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * 用于避免下载时文件名乱码
	 * 
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * 
	 */
	@org.apache.struts2.json.annotations.JSON(serialize = false)
	public String getDownloadFileName() throws UnsupportedEncodingException {
		return processDownloadFileName(templateName);
	}

	@org.apache.struts2.json.annotations.JSON(serialize = false)
	public String getContentType() {
		return ExcelVersion.of(templateName).getContentType();
	}

	@org.apache.struts2.json.annotations.JSON(serialize = false)
	public InputStream getInputStream() {
		return inputStream;
	}

}
