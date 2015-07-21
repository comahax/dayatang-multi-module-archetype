#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.zip.ZipInputStream;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

/**
 * 发布新流程
 * 
 * @author yyang
 */
public class DeployProcessAction extends BaseProcessAction {

	private static final long serialVersionUID = 6742415506274189137L;

	private boolean result = false;

	public String execute() {
		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();
		if (multiWrapper.hasErrors()) {
			for (String error : multiWrapper.getErrors()) {
				addActionError(error);
			}
			return JSON;
		}
		Enumeration<String> uploadFields = multiWrapper.getFileParameterNames();
		while (uploadFields.hasMoreElements()) {
			String fieldName = uploadFields.nextElement();
			String[] contentTypes = multiWrapper.getContentTypes(fieldName);
			String[] fileNames = multiWrapper.getFileNames(fieldName);
			File[] files = multiWrapper.getFiles(fieldName);
			deployProcceses(files, fileNames, contentTypes);
		}
		result = true;
		return JSON;
	}

	private void deployProcceses(File[] files, String[] fileNames, String[] contentTypes) {
		for (int i = 0; i < files.length; i++) {
			System.out.println("fileName: " + fileNames[i]);
			System.out.println("contentType: " + contentTypes[i]);
			File file = files[i];
			System.out.println("file: " + file.getAbsolutePath());
			deployProccess(file, fileNames[i]);
		}
	}

	private void deployProccess(File file, String filename) {
		try {
			FileInputStream fin = new FileInputStream(file);
			String deploymentId = "";
			if (filename.endsWith(".bar")) {
				ZipInputStream in = new ZipInputStream(fin);
				deploymentId = repositoryService.createDeployment().addZipInputStream(in).deploy().getId();
			} else {
				deploymentId = repositoryService.createDeployment().addInputStream(filename, fin).deploy().getId();
			}
			addActionMessage("文件" + filename + "部署成功。部署ID为" + deploymentId);
		} catch (FileNotFoundException e) {
			addActionError("文件" + filename + "不存在");
			e.printStackTrace();
		}
	}

	public boolean isResult() {
		return result;
	}

}
