#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.*;
import ${package}.process.ProcessConstants;
import ${package}.query.DocumentQuery;
import ${package}.webapp.action.process.BaseProcessAction;
import ${package}.webapp.dto.ProjectCapitalBudgetEntryDto;
import com.dayatang.utils.Slf4jLogger;

import org.activiti.engine.runtime.ProcessInstance;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA. User: zjzhai Date: 13-7-7 Time: 下午1:58
 */
public class PreSubmitAction extends BaseProcessAction {

	private static final long serialVersionUID = -2388150919851953839L;

	private static final Slf4jLogger LOGGER = Slf4jLogger.getLogger(PreSubmitAction.class);

	private long id = 0l;

	@Override
	public String execute() throws Exception {
		Project project = getProjectOf(id);

		if (ProjectStatus.APPROVING.equals(project.getStatus())) {
			errorInfo = "不能重复进行项目申请";
			return JSON;
		}
		createProjectProcessInstance(project);
		return JSON;
	}

	/**
	 * 创建流程实例 如果创建成功返回true
	 * 
	 * @param project
	 */
	private boolean createProjectProcessInstance(Project project) {
		try {
			identityService.setAuthenticatedUserId(getCurrentUsername());
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstants.PROJECT_INITIATION_PROCESS_KEY, project.getId().toString(), createProcessVariables(project));
			project.setStatus(ProjectStatus.APPROVING);
			projApplication.saveEntity(project);
			LOGGER.debug("{} start a processinstance: {} at {}", getCurrentUsername(), processInstance, new Date());
			return true;
		} catch (Exception e) {
			sendExceptionMsgAdmin("出错,立项申请流程：" + e.getMessage());
			project.setStatus(ProjectStatus.DRAFT);
			projApplication.saveEntity(project);
			errorInfo = "系统出错，但已经将项目保存为草稿。请稍后再提交。";
			return false;
		}
	}

	/**
	 * 立项表单的流程变量的创建
	 * 
	 * @param project
	 * @return
	 */
	private Map<String, Object> createProcessVariables(Project project) {
		Map<String, Object> results = new HashMap<String, Object>();
		results.put(ProcessConstants.INITIATOR_USERNAME, getCurrentUser().getUsername());
		results.put(ProcessConstants.INITIATOR, getCurrentUser().getDisplayName());
		results.put(ProcessConstants.INITIATOR_ORG_ID, project.getResponsibleDivision().getId());
		results.put(ProcessConstants.INITIATOR_PERSON_ID, getCurrentPerson().getId());
		results.put(ProcessConstants.INITIATOR_ORG, project.getResponsibleDivision().getFullName());

		results.put(ProcessConstants.TITLE, project.getName());

		// 发起公司
		results.put(ProcessConstants.INITIATOR_COMPANY_ID, getGrantedScope().getCompany().getId());

		// 以哪个公司投的标
		InternalOrganization constructingOrg = project.getConstructingOrg();
		results.put(ProcessConstants.BIDDING_ORG_ID, constructingOrg.getId());
		results.put(ProcessConstants.BIDDING_ORG, constructingOrg.getName());
		results.put(ProcessConstants.PROJECT_ID, project.getId());

		results.put("projectName", project.getName());
		results.put("projectNumber", project.getProjectNumber());
		results.put("startDate", getDate(project.getStartDate()));
		results.put("predictFinishDate", getDate(project.getPredictFinishDate()));
		results.put("projectType", getDictionary(DictionaryCategory.PROJECT_TYPE, project.getProjectType()));
		results.put("owner", getOrgName(project.getOwnerInfo()));
		results.put("responsibleDivision", getOrgName(project.getResponsibleDivision()));
		results.put("area", project.getArea() == null ? null : project.getArea().getFullName());

		// 总收入
		results.put("estimatedIncome", convertYuanToTenThousand(project.getEstimatedIncome()));

		// 成本合计
		results.put("totalBudgetAmount", convertYuanToTenThousand(project.getTotalBudgetAmount()));

		// 企业管理成本
		results.put("enterpriseManagementCosts", convertYuanToTenThousand(project.getEnterpriseManagementCosts()));

		// 企业所得税
		results.put("enterpriseIncomeTax", convertYuanToTenThousand(project.getEnterpriseIncomeTax()));

		// 净利润
		results.put("netProfit", convertYuanToTenThousand(project.getNetProfit()));

		// 净利率
		results.put("netProfitMargin", project.getNetProfitMargin());

		// 成本预算
		Map<ExpenditureType, BigDecimal> budgets = project.getBudgets();
		for (ExpenditureType each : budgets.keySet()) {
			results.put(each.name(), convertYuanToTenThousand(budgets.get(each)));
		}

		// 自定义成本
		Map<String, BigDecimal> customBudgets = project.getCustomBudgets();
		if (null != customBudgets) {
			Map<String, Object> templateParams = new HashMap<String, Object>();
			templateParams.put("customBudgets", ProjectCapitalBudgetEntryDto.createByCustomBudgetMap(customBudgets));
			results.put("customBudgets", proccessTemplate("project-pre-custombudgets-show.ftl", templateParams));
		}

		results.put("grossProfit", convertYuanToTenThousand(project.getGrossProfit()));
		results.put("grossMargin", project.getGrossMargin().doubleValue());
		results.put("remark", project.getRemark());
		results.put("docs", docDownloadHtml(DocumentQuery.projectOf(project.getId()), project.getId()));

		if (null != project.getCapitaltotakeups()) {
			// 初始化资金占用情况
			initCapitaltotakeupVariable(results, convertYuanToTenThousand(project.getCapitaltotakeups()));

		}
		return results;
	}

	// 将以元为单位的资金占用记录转成以万元为单位的
	private List<Capitaltotakeup> convertYuanToTenThousand(Collection<Capitaltotakeup> capitaltotakeup) {
		List<Capitaltotakeup> results = new ArrayList<Capitaltotakeup>(capitaltotakeup.size());
		for (Iterator<Capitaltotakeup> i = capitaltotakeup.iterator(); i.hasNext();) {
			Capitaltotakeup each = (Capitaltotakeup) i.next();
			if (null == each) {
				continue;
			}
			Capitaltotakeup temp = new Capitaltotakeup();
			temp.setCostCaption(each.getCostCaption());
			temp.setCostFunds(convertYuanToTenThousand(each.getCostFunds()));
			temp.setExpectedFunds(convertYuanToTenThousand(each.getExpectedFunds()));
			temp.setInterestRate(each.getInterestRate());
			temp.setRemark(each.getRemark());
			temp.setStartDate(each.getStartDate());
			temp.setYearCount(each.getYearCount());
			results.add(temp);
		}
		return results;
	}
	
    /**
    *
    * @param variables
    * @param capitaltotakeups　以万元为单位
    */
   protected void initCapitaltotakeupVariable(Map<String, Object> variables,
                                              Collection<Capitaltotakeup> capitaltotakeups) {
       if (null == variables) {
           throw new RuntimeException("传入参数不能为空");
       }
       variables.put("capitaltotakeupsHtml", capitatotakeupHtml(capitaltotakeups));
   }


	public String getErrorInfo() {
		return errorInfo;
	}

	public void setId(long id) {
		this.id = id;
	}
}
