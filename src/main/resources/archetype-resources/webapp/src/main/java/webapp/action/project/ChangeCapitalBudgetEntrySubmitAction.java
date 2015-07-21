#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.beanutils.BeanUtils;

import ${package}.commons.BigDecimalUtils;
import ${package}.domain.Capitaltotakeup;
import ${package}.domain.ExpenditureType;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Project;
import ${package}.process.ProcessConstants;
import ${package}.webapp.dto.ProjectCapitalBudgetEntryDto;

/**
 * User: zjzhai Date: 13-6-26 Time: 下午3:37
 */
public class ChangeCapitalBudgetEntrySubmitAction extends EditBaseAction {

	/**
     *
     */
	private static final long serialVersionUID = -1713990045258462934L;
	/**
	 * 项目收支预算变更审批模板
	 */
	private static final String BUDGET_FORM_TEMPLATE = "budget-form-comparative.ftl";
	private Long projectId = 0l;

	@Override
	public String execute() throws Exception {

		Project project = getProjectOf(projectId);
		if (null == project) {
			return NOT_FOUND;
		}
		// 如果项目处于草稿状态，则不创建流程
		if (project.isDraft()) {
			// 设置成本
			initProjectBudgetAndIncome(project);
			projApplication.saveEntity(project);
			return JSON;
		} else if (project.isBusinessOperationsable()) {
			createBudgetChangeProccessinstance(project);

		}
		return JSON;
	}

	private boolean createBudgetChangeProccessinstance(Project project) {
		// 创建项目收支预算变更流程实例
		try {
			identityService.setAuthenticatedUserId(getCurrentUsername());
			Map<String, Object> variables = createProcessVariables(project);

			variables.put("budgetFormComparative", proccessTemplate(BUDGET_FORM_TEMPLATE, createFreemarkerVariables(project)));
			runtimeService.startProcessInstanceByKey(ProcessConstants.BUDGET_CHANGE_KEY, getRandomStr(), variables);
			return true;
		} catch (Exception e) {
			sendExceptionMsgAdmin("项目" + project.getName() + "收支预算变更失败" + this.getClass().toString() + e.getMessage());
			errorInfo = "系统出错，请联系子公司系统管理员！";
			return false;
		}
	}

	private Map<String, Object> createFreemarkerVariables(Project project) {
		Map<String, Object> results = createCommonVariables(project);
		results.put("project", project);
		return results;
	}

	private Map<String, Object> createProcessVariables(Project project) {
		return createCommonVariables(project);
	}

	private Map<String, Object> createCommonVariables(Project project) {
		Map<String, Object> results = new HashMap<String, Object>();

		results.put(ProcessConstants.PROJECT_ID, project.getId());
		results.put(ProcessConstants.INITIATOR_USERNAME, getCurrentUser().getUsername());
		results.put(ProcessConstants.INITIATOR_PERSON_ID, getCurrentPerson().getId());
		results.put(ProcessConstants.INITIATOR, getCurrentUser().getDisplayName());
		results.put(ProcessConstants.INITIATOR_ORG_ID, project.getResponsibleDivision().getId());
		results.put(ProcessConstants.INITIATOR_ORG, project.getResponsibleDivision().getFullName());
		results.put(ProcessConstants.TITLE, project.getName());
		results.put("projectName", project.getName());

		// 发起公司
		results.put(ProcessConstants.INITIATOR_COMPANY_ID, getGrantedScope().getCompany().getId());

		// 以哪个公司投的标
		/**
		 * 历史版本的项目是没有设置以哪个公司投的标的
		 */
		InternalOrganization constructingOrg = project.getConstructingOrg();
		if (null != constructingOrg) {
			results.put(ProcessConstants.BIDDING_ORG_ID, constructingOrg.getId());
			results.put(ProcessConstants.BIDDING_ORG, constructingOrg.getName());
		}

		// 总收入
		results.put("estimatedIncome", estimatedIncome);

		// 成本合计
		results.put("totalBudgetAmount", getTotalBudgetAmount());

		// 企业管理成本
		results.put("enterpriseManagementCosts", enterpriseManagementCosts);

		// 企业所得税
		results.put("enterpriseIncomeTax", enterpriseIncomeTax);

		// 净利润
		results.put("netProfit", netProfit);

		// 净利率
		results.put("netProfitMargin", netProfitMargin);

		results.put(ExpenditureType.OPERATION.name(), operation);
		results.put(ExpenditureType.SALARY.name(), salary);
		results.put(ExpenditureType.MARKET.name(), market);
		results.put(ExpenditureType.DEVICE_DEPRECIATION.name(), deviceDepreciation);
		results.put(ExpenditureType.AUXILIARY_MATERIAL.name(), auxiliaryMaterial);
		results.put(ExpenditureType.SUBCONTRACT.name(), subcontract);
		results.put(ExpenditureType.MAIN_MATERIAL.name(), mainMaterial);
		results.put(ExpenditureType.FUND_OCCUPATION.name(), fundOccupation);
		results.put(ExpenditureType.TAXES.name(), taxes);
		results.put(ExpenditureType.OTHER.name(), other);

		results.put("grossProfit", grossProfit);
		if (null != grossMargin) {
			results.put("grossMargin", grossMargin.doubleValue());
		}

		/**
		 * 自定义成本费用
		 */
		results.put("customBudgets", ProjectCapitalBudgetEntryDto.createHasPreEditAmountsBy(project.getCustomBudgets(), getCustomBudgetsMap()));
		results.put("newCustomBudgets", getCustomBudgetsMap());

		// 变更时间
		results.put("changeDate", new Date());

		results.put("remark", remark);

		// 初始化资金占用情况
		initCapitaltotakeupVariable(results, capitaltotakeup, convertYuanToThousand(project.getCapitaltotakeups()));

		return results;
	}

	/**
	 * 
	 * @param variables
	 * @param capitaltotakeups
	 *            新的资金预算
	 * @param historyCapitaltotakeup
	 */
	private void initCapitaltotakeupVariable(Map<String, Object> variables, List<Capitaltotakeup> capitaltotakeups, List<Capitaltotakeup> historyCapitaltotakeup) {
		if (null == variables) {
			throw new RuntimeException("传入参数不能为空");
		}
		variables.put("occupationOfFund_costCaption", getAllcostCaptionOf(capitaltotakeups));
		variables.put("occupationOfFund_startDate", getAllstartDateOf(capitaltotakeups));
		variables.put("occupationOfFund_expectedFunds", getAllexpectedFundsOf(capitaltotakeups));
		variables.put("occupationOfFund_yearCount", getAllyearCount(capitaltotakeups));
		variables.put("occupationOfFund_interestRate", getAllinterestRateOf(capitaltotakeups));
		variables.put("occupationOfFund_costFunds", getAllcostFundsOf(capitaltotakeups));
		variables.put("occupationOfFund_remarks", getAllremarkOf(capitaltotakeups));

		variables.put("historyCapitotakeupsHtml", capitatotakeupHtml(historyCapitaltotakeup));
		variables.put("capitaltotakeupsHtml", capitatotakeupHtml(capitaltotakeups));
	}

	/**
	 * 得到所有的实际占用成本
	 * 
	 * @param capitaltotakeups
	 * @return
	 */
	private List<BigDecimal> getAllcostFundsOf(List<Capitaltotakeup> capitaltotakeups) {
		List<BigDecimal> results = new ArrayList<BigDecimal>();
		for (Capitaltotakeup each : capitaltotakeups) {
			results.add(each.getCostFunds());
		}

		return results;
	}

	/**
	 * 得到所有的利率
	 * 
	 * @param capitaltotakeups
	 * @return
	 */
	private List<BigDecimal> getAllinterestRateOf(List<Capitaltotakeup> capitaltotakeups) {
		List<BigDecimal> results = new ArrayList<BigDecimal>();
		for (Capitaltotakeup each : capitaltotakeups) {
			results.add(each.getInterestRate());
		}

		return results;
	}

	/**
	 * 得到所有的占用年数
	 */
	private List<Double> getAllyearCount(List<Capitaltotakeup> capitaltotakeups) {
		List<Double> results = new ArrayList<Double>();
		for (Capitaltotakeup each : capitaltotakeups) {
			results.add(each.getYearCount());
		}

		return results;
	}

	/**
	 * 得到所有的预计占用成本
	 * 
	 * @param capitaltotakeups
	 * @return
	 */
	private List<BigDecimal> getAllexpectedFundsOf(List<Capitaltotakeup> capitaltotakeups) {
		List<BigDecimal> results = new ArrayList<BigDecimal>();
		for (Capitaltotakeup each : capitaltotakeups) {
			results.add(each.getExpectedFunds());
		}

		return results;
	}

	/**
	 * 得到所有的开始日期
	 * 
	 * @param capitaltotakeups
	 * @return
	 */
	private List<Date> getAllstartDateOf(List<Capitaltotakeup> capitaltotakeups) {
		List<Date> results = new ArrayList<Date>();

		for (Capitaltotakeup each : capitaltotakeups) {
			results.add(each.getStartDate());
		}

		return results;
	}

	/**
	 * 得到所有的备注
	 * 
	 * @param capitaltotakeups
	 * @return
	 */
	private List<String> getAllremarkOf(List<Capitaltotakeup> capitaltotakeups) {
		List<String> results = new ArrayList<String>();
		for (Capitaltotakeup each : capitaltotakeups) {
			results.add(each.getRemark());
		}
		return results;
	}

	/**
	 * 得到所有的costCaption
	 * 
	 * @param capitaltotakeups
	 * @return
	 */
	private List<String> getAllcostCaptionOf(List<Capitaltotakeup> capitaltotakeups) {
		List<String> results = new ArrayList<String>();
		for (Capitaltotakeup each : capitaltotakeups) {
			results.add(each.getCostCaption());
		}
		return results;
	}

	private List<Capitaltotakeup> convertYuanToThousand(Set<Capitaltotakeup> capitaltotakeups) {
		if (null == capitaltotakeups) {
			return new ArrayList<Capitaltotakeup>();
		}
		List<Capitaltotakeup> results = new ArrayList<Capitaltotakeup>();
		try {
			BeanUtils.copyProperties(results, capitaltotakeups);
			for (Iterator<Capitaltotakeup> it = results.iterator(); it.hasNext();) {
				Capitaltotakeup capitaltotakeup1 = it.next();
				capitaltotakeup1.setCostFunds(BigDecimalUtils.convertYuanToTenThousand(capitaltotakeup1.getCostFunds()));
				capitaltotakeup1.setExpectedFunds(BigDecimalUtils.convertYuanToTenThousand(capitaltotakeup1.getExpectedFunds()));
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendExceptionMsgAdmin(e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendExceptionMsgAdmin(e.getMessage());
		}
		return results;

	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

}
