#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;

import ${package}.domain.InternalOrganization;
import ${package}.domain.Role;
import ${package}.excel.ExcelTemplateFactory;
import ${package}.excel.ExcelVersion;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.BaseAction;

@Result(name = "success", type = "stream", params = { "inputStream", "inputStream", "contentType", "%{contentType}", "contentDisposition", "filename=${symbol_dollar}{downloadFileName}", "bufferSize", "4096000" })
public class DownloadImportPeopleTemplateAction extends BaseAction {
	private static final long serialVersionUID = 4396482048890085916L;

	private String templateName;

	private InputStream inputStream;

	@Override
	public String execute() throws Exception {

		List<InternalOrganization> subs = getSubs();

		List<InternalOrganization> departments = new ArrayList<InternalOrganization>();
		for (InternalOrganization each : subs) {
			departments.addAll(getChrildrenOf(each));
		}

		List<InternalOrganization> divisions = new ArrayList<InternalOrganization>();
		for (InternalOrganization each : departments) {
			divisions.addAll(getChrildrenOf(each));
		}

		List<String> roles = Role.getAllRoleDescriptionWithoutSystemAdmin();
		for (Iterator<String> it = roles.iterator(); it.hasNext();) {
			if (Arrays.asList("广东日海总经理", "项目中心总监", "广东日海副总经理").contains(it.next())) {
				it.remove();
			}
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			templateName = ExcelTemplateFactory.create().getPeopleImportTemplate(out, subs, departments, divisions, roles);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			sendExceptionMsgAdmin(e.getMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			sendExceptionMsgAdmin(e.getMessage());
		} 

		return SUCCESS;

	}

	/**
	 * 得到所有的子公司
	 * 
	 * @return
	 */
	private List<InternalOrganization> getSubs() {
		if (getGrantedScope().getId().equals(InternalOrganization.HEADQUARTERS_ID)) {
			return getChrildrenOf(getGrantedScope());
		} else {
			return Arrays.asList(getGrantedScope().getCompany());
		}
	}

	/**
	 * 得到所有的事业部
	 */
	private List<InternalOrganization> getChrildrenOf(InternalOrganization sub) {
		return InternalOrganizationQuery.create().immediateChildrenOf(sub).enabled().list();
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

}
