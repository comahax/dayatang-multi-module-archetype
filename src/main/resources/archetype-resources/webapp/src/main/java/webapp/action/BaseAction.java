#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

import com.dayatang.configuration.WritableConfiguration;
import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;
import com.dayatang.domain.InstanceFactory;
import ${package}.Constants;
import ${package}.application.*;
import ${package}.commons.BigDecimalUtils;
import ${package}.commons.SystemVariablesUtils;
import ${package}.domain.Dictionary;
import ${package}.domain.*;
import ${package}.pager.PageList;
import ${package}.query.BaseQuery;
import ${package}.query.ProjectQuery;
import ${package}.query.SubProjectQuery;
import ${package}.webapp.dto.SupervisorOrganizationDto;
import ${package}.webapp.freemarkModel.ConvertYuanToTenThousand;
import ${package}.webapp.utils.DocumentTagGenerater;
import com.opensymphony.xwork2.ActionSupport;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.*;

import static ${package}.commons.SystemVariablesUtils.getSysnoticeTitle;

/**
 * Action的共同基类。
 * 
 * @author yyang
 */
@ParentPackage("web")
@Results(value = {
		@Result(name = "json", type = "json", params = { "contentType", "text/html", "excludeNullProperties", "true" }),
		@Result(name = "login", type = "redirect", location = "/login.jsp"),
		@Result(name = "not_found", type = "redirect", location = "/not-found.action"),
		@Result(name = "worktable", type = "redirect", location = "/worktable.action"),
		@Result(name = "error", location = "/page/error.jsp") })
public abstract class BaseAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = -1754469889670152640L;

	protected static final String WORKTABLE = "worktable";

	protected static final String JSON = "json";

	protected static final String NOT_FOUND = "not_found";

	public static final String ROLE_ASSIGNMENT_KEY = "ROLE_ASSIGNMENT";

	private static final String TEMPLATE_URL = "freemarker.template.url";

	private static WritableConfiguration configuration;

	private Map<String, Object> session;

	@Inject
	protected CommonsApplication commonsApplication;

	@Inject
	protected ProjApplication projApplication;

	@Inject
	protected SecurityApplication securityApplication;

	@Inject
	protected EventApplication eventApplication;

	@Inject
	protected NoticeApplication noticeApplication;

	@Inject
	protected ExcelApplication excelApplication;

	private Map<DictionaryCategory, Map<String, String>> dictionaryMap = new HashMap<DictionaryCategory, Map<String, String>>();

	public String path = ServletActionContext.getRequest().getContextPath();
	public String basePath = ServletActionContext.getRequest().getScheme() + "://"
			+ ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort()
			+ path + "/";

	@Inject
	private EntityRepository repository;

	/**
	 * 用于返回前端的错误提示信息
	 */
	protected String errorInfo;

	// 用于分页
	protected int page = 0;

	// 分页过程中每页多少记录
	protected int rows = 20;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	/**
	 * 获得系统配置接口，用于获取和更改设置项。
	 * 
	 * @return
	 */
	public WritableConfiguration getConfiguration() {
		if (configuration == null) {
			configuration = InstanceFactory.getInstance(WritableConfiguration.class);
		}
		return configuration;
	}

	public void setConfiguration(WritableConfiguration configuration) {
		BaseAction.configuration = configuration;
	}

	public void setCommonsApplication(CommonsApplication commonsApplication) {
		this.commonsApplication = commonsApplication;
	}

	public void setProjApplication(ProjApplication projApplication) {
		this.projApplication = projApplication;
	}

	public void setSecurityApplication(SecurityApplication securityApplication) {
		this.securityApplication = securityApplication;
	}

	public void setEventApplication(EventApplication eventApplication) {
		this.eventApplication = eventApplication;
	}

	public String getSessionId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getSession().getId();
	}

	public EntityRepository getRepository() {
		if (repository == null) {
			repository = InstanceFactory.getInstance(EntityRepository.class);
		}
		return repository;
	}

	public void setRepository(EntityRepository repository) {
		this.repository = repository;
	}

	protected String processDownloadFileName(String filename) throws UnsupportedEncodingException {
		if (null == filename) {
			return null;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String Agent = request.getHeader("User-Agent");
		if (null != Agent) {
			Agent = Agent.toLowerCase();
			if (Agent.indexOf("firefox") != -1) {
				return new String(filename.getBytes(), "iso8859-1");
			}
			if (Agent.indexOf("msie") != -1) {
				return URLEncoder.encode(filename, "UTF-8");
			}
		}
		return URLEncoder.encode(filename, "UTF-8");
	}

	/**
	 * 获得当前用户的用户名
	 * 
	 * @return
	 */
	public String getCurrentUsername() {
		return securityApplication.getCurrentUsername();
	}

	/**
	 * 获得当前用户
	 * 
	 * @return
	 */
	public User getCurrentUser() {
		return securityApplication.getCurrentUser();
	}

	/**
	 * 获得当前用户对应的人员
	 * 
	 * @return
	 */
	public Person getCurrentPerson() {
		User user = getCurrentUser();
		if (user instanceof PersonUser) {
			return ((PersonUser) user).getPerson();
		}
		return null;
	}

	/**
	 * 获得当前角色信息，包括角色和授权机构范围等。
	 * 
	 * @return
	 */
	public RoleAssignment getAssignment() {
		Long assignmentId = (Long) session.get(ROLE_ASSIGNMENT_KEY);
		if (assignmentId == null) {
			List<RoleAssignment> assignments = RoleAssignment.findByUser(getCurrentUser());
			if (assignments.isEmpty()) {
				return null;
			}
			assignmentId = assignments.get(0).getId();
			session.put(ROLE_ASSIGNMENT_KEY, assignmentId);
		}
		return RoleAssignment.get(RoleAssignment.class, assignmentId);
	}

	/**
	 * 获得当前用户的授权访问机构范围
	 * 
	 * @return
	 */
	public InternalOrganization getGrantedScope() {
		return getAssignment().getOrganization();
	}

	/**
	 * 获得当前用户的所在机构的类型
	 * 
	 * @return
	 */
	public String getCurrentOrganizationType() {
		return getAssignment().getOrganization().getInternalCategory().toString();
	}

	/**
	 * 获得当前用户的当前角色
	 * 
	 * @return
	 */
	public Role getRole() {
		return getAssignment().getRole();
	}

	/**
	 * 得到项目web的基本路径
	 * 
	 * @return
	 */
	public String getBasePath() {
		return ServletActionContext.getRequest().getContextPath();
	}

	public String getDateFormat() {
		return getConfiguration().getString(Constants.DATE_FORMAT);
	}

	public String getTimeFormat() {
		return getConfiguration().getString(Constants.TIME_FORMAT);
	}

	public String getDateTimeFormat() {
		return getConfiguration().getString(Constants.DATE_TIME_FORMAT);
	}

	public String getNumberFormat() {
		return getConfiguration().getString(Constants.NUMBER_FORMAT);
	}

	public String getPercentageFormat() {
		return getConfiguration().getString(Constants.PERCENTAGE_FORMAT);
	}

	public String getCharset() {
		return getConfiguration().getString(Constants.CHARSET, "utf-8");
	}

	public String getDictionary(DictionaryCategory category, String key) {
		Map<String, String> dictionaries = dictionaryMap.get(category);
		if (dictionaries == null) {
			dictionaries = Dictionary.getMap(category);
			dictionaryMap.put(category, dictionaries);
		}
		return dictionaries.get(key);
	}

	/**
	 * 得到用户IP
	 * 
	 * @return
	 */
	public String getRemoteAddr() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 将万元转成元
	 * 
	 * @param tenThousand
	 */
	public BigDecimal convertTenThousandToYuan(BigDecimal tenThousand) {
		return BigDecimalUtils.convertTenThousandToYuan(tenThousand);
	}

	/**
	 * 将元转成万元
	 * 
	 * @param yuan
	 */
	public BigDecimal convertYuanToTenThousand(BigDecimal yuan) {
		return BigDecimalUtils.convertYuanToTenThousand(yuan);
	}

	/**
	 * 得到用户访问的URL
	 * 
	 * @return
	 */
	public String getRequestUrl() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getRequestURL().toString();
	}

	/**
	 * 得到当前用户的联系邮箱
	 * 
	 * @return
	 */
	public String getCurrentUserEmail() {
		if (getCurrentPerson() != null) {
			return getCurrentPerson().getEmail();
		}
		return getCurrentUser().getEmail();
	}

	public String getProjectTypeName(String key) {
		return getDictionary(DictionaryCategory.PROJECT_TYPE, key);
	}

	public String formatNumber(Object number) {
		if (number == null) {
			return "";
		}
		NumberFormat numberFormat = NumberFormat.getInstance();
		return numberFormat.format(number);
	}

	/**
	 * 发送异常内容给管理员
	 * 
	 * @param content
	 */
	protected void sendExceptionMsgAdmin(String content) {
		noticeApplication.notice(getSysMonitorEmailAddress(), "系统异常--" + getSysnoticeTitle(), content);
	}

	// 将用户名和尝试密码发送给管理员 TODO 待优化
	protected void sendLoginErrorMsgToAdmin(String username, String password) {
		noticeApplication.notice(getSysMonitorEmailAddress(), " 登录尝试--" + getSysnoticeTitle(), new Date() + " userName: "
				+ username + " password:" + password);
	}

	/**
	 * 新建用户后发邮箱到用户的邮箱
	 * 
	 * @param user
	 * @param mingCode
	 */
	protected void sendNewUserEmail(User user, String mingCode) {
		if (null == user || StringUtils.isEmpty(mingCode)) {
			return;
		}
		noticeApplication.notice(user.getEmail(), "通知--" + getSysnoticeTitle(), "您在通信工程项目管理系统中的用户名为本邮箱地址，密码为：" + mingCode);
	}

	/**
	 * 授权访问的机构是否有下属机构
	 * 
	 * @return
	 */
	public boolean getHasSubordinateOrgs() {
		return !getGrantedScope().getSubordinateWithSelf().isEmpty();
	}

	/**
	 * 获得直属下属机构
	 * 
	 * @return
	 */
	public Set<InternalOrganization> getImmediateChildren() {
		return getGrantedScope().getImmediateChildren();
	}

	/**
	 * 得到模板文件的路径
	 * 
	 * @return
	 */
	protected String getTemplateUrl() {
		return ServletActionContext.getServletContext().getRealPath("/") + File.separator
				+ getConfiguration().getString(TEMPLATE_URL);
	}

	/**
	 * 得到模板文件
	 */
	protected Template getTemplate(String fileName) {
		Configuration cfg = new Configuration();
		Template template = null;
		try {
			String url = getTemplateUrl();
			File templteFile = new File(url);
			cfg.setDirectoryForTemplateLoading(templteFile);
			template = cfg.getTemplate(fileName);
		} catch (IOException e) {
			e.printStackTrace();
			sendExceptionMsgAdmin(e.getMessage());
		}
		return template;
	}

	/**
	 * 获得子机构，子孙机构，包括自身
	 * 
	 * @return
	 */
	public Set<InternalOrganization> getSubordinateWithSelf() {
		return getGrantedScope().getSubordinateWithSelf();
	}

	/**
	 * 得到系统管理员的id
	 * 
	 * @return
	 */
	protected Long getSystemAdminPersonId() {
		List<RoleAssignment> roleAssignments = RoleAssignment.findByRole(Role.get(Role.SYSTEM_ROLE_ID));
		if (null == roleAssignments || roleAssignments.isEmpty()) {
			throw new RuntimeException("系统未设置系统管理员角色");
		}
		PersonUser admin = (PersonUser) roleAssignments.get(0).getUser();

		if (null == admin) {
			throw new RuntimeException("系统未设置系统管理员用户");
		}

		if (null == admin.getPerson()) {
			return null;
		}

		return admin.getPerson().getId();
	}

	/**
	 * 得到所有的监理单位
	 * 
	 * @return
	 */
	public List<SupervisorOrganizationDto> getSupervisorsIdName() {
		return SupervisorOrganizationDto.idNameSupervisorOrgOf(SupervisorOrganization.findAllEnabled());
	}

	protected <T extends AbstractEntity> PageList<T> createPageList(BaseQuery<T> query) {
		if (null == query) {
			return null;
		}
		return PageList.create(query.build(), page - 1, rows);
	}

	protected <T> PageList<T> createPageList(List<T> datas) {
		if (null == datas || datas.isEmpty()) {
			return null;
		}
		return PageList.create(datas, page - 1, rows);
	}

	protected ProjectQuery getProjectQuery() {
		return ProjectQuery.responsibleOf(getGrantedScope(), getCurrentPerson());
	}

	/**
	 * 返回当前用户可访问的id 为{id}的项目，
	 * 
	 * @param id
	 * @return
	 */
	protected Project getProjectOf(Long id) {
		if (id == null || id <= 0l) {
			return null;
		}
		return getProjectQuery().id(id).getSingleResult();
	}

	/**
	 * 得到一个随带整数，用于前端jsp页面
	 * 
	 * @return
	 */
	public String getRandomStr() {
		return (Math.random() * 1000 + "").replace(".", "0");
	}

	protected SubProjectQuery getSubProjectQuery() {
		return SubProjectQuery.createResponsibleOf(getGrantedScope());
	}

	/**
	 * 系统默认密码
	 * 
	 * @return
	 */
	public String getSystemDefaultPassword() {
		return getConfiguration().getString("default.password");
	}

	protected String proccessTemplate(String templateName, Map<String, Object> variables) {
		Template template = getTemplate(templateName);
		template.setEncoding(getCharset());
		StringWriter stringWriter = new StringWriter();
		try {
			variables.put("convertYuanToTenThousand", new ConvertYuanToTenThousand());
			template.process(variables, stringWriter);
			stringWriter.flush();
		} catch (TemplateException e) {
			e.printStackTrace();
			sendExceptionMsgAdmin(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			sendExceptionMsgAdmin(e.getMessage());
		}

		return stringWriter.toString();
	}

	protected DocumentTagGenerater createDefaultDocTagGenerater() {
		return new DocumentTagGenerater().append(createDefaultDocumentTags());
	}

	protected Set<DocumentTag> createDefaultDocumentTags() {
		DocumentTagGenerater generater = new DocumentTagGenerater();
		generater.uploadedBy(getCurrentUser().getId()).able();
		if (getGrantedScope() != null) {
			generater.holderOrganization(getGrantedScope().getId());
		}
		Set<DocumentTag> results = generater.generate();
		return results;
	}

	public String getSysMonitorEmailAddress() {
		return SystemVariablesUtils.getSysMonitorEmailAddress();
	}

	protected Boolean strToBoolean(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		return Boolean.valueOf(str);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
