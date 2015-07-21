#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.commons.BigDecimalUtils;
import ${package}.domain.*;
import ${package}.domain.Dictionary;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.ErrorConstants;
import ${package}.webapp.action.process.BaseProcessAction;

import java.math.BigDecimal;
import java.util.*;

/**
 * 立项过程中修改项目基本信息的基类 User: zjzhai Date: 13-4-8 Time: 下午12:55
 */
public abstract class EditBaseAction extends BaseProcessAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8060863268022162262L;
	// 区域ID
	protected long areaId = 0;
	// 负责单位的ID
	protected long responsibleDivisionId = 0;
	// 业主ID
	protected long ownerId = 0;
	// 业主联系人
	protected long ownerContacterId = 0;
	protected String name;
	// 开工日期
	protected Date startDate;
	// 计划完工日期
	protected Date predictFinishDate;
	// 项目类型
	protected String projectType;
	// 专业
	protected List<Long> specialtyIds;
	// 备注
	protected String remark;
	// 以哪家公司的名义开展项目
	protected Long constructingOrgId = 0l;

	/**
	 * 项目的预算基本信息
	 */
	/**
	 * 收入
	 */
	protected BigDecimal estimatedIncome = BigDecimal.ZERO;
	/**
	 * 毛利润
	 */
	protected BigDecimal grossProfit = BigDecimal.ZERO;
	/**
	 * 毛利率
	 */
	protected BigDecimal grossMargin = BigDecimal.ZERO;
	/**
	 * 企业管理成本
	 */
	protected BigDecimal enterpriseManagementCosts = BigDecimal.ZERO;
	/**
	 * 企业所得税
	 */
	protected BigDecimal enterpriseIncomeTax = BigDecimal.ZERO;
	/**
	 * 净利率
	 */
	protected BigDecimal netProfitMargin = BigDecimal.ZERO;
	/**
	 * 净利润
	 */
	protected BigDecimal netProfit = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal operation = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal salary = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal market = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal deviceDepreciation = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal auxiliaryMaterial = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal subcontract = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal mainMaterial = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal fundOccupation = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal taxes = BigDecimal.ZERO;
	/**
	 * 成本
	 */
	protected BigDecimal other = BigDecimal.ZERO;
	/**
	 * 占用资金成本信息的ID
	 */
	protected List<Capitaltotakeup> capitaltotakeup = new ArrayList<Capitaltotakeup>();
	/**
	 * 自定义成本费用金额
	 */
	protected List<BigDecimal> customBudgetAmount = new ArrayList<BigDecimal>();

	/**
	 * 自定义成本费用名称（字典的serialNumber）
	 */
	protected List<String> customBudgetName = new ArrayList<String>();

	/**
	 * 初始化基本信息 ,并且未设置项目的状态
	 * 
	 * @param project
	 * @return
	 */
	protected boolean initBaseDetailsOfDraftProject(Project project) {
		project.setName(name);
		project.setRemark(remark);
		project.setProjectType(projectType);
		project.setStartDate(startDate);
		project.setPredictFinishDate(predictFinishDate);
		project.setArea(Area.get(areaId));
		project.setOwnerInfo(new OrganizationInfo(OwnerOrganization.get(ownerId), Person.get(ownerContacterId)));

		InternalOrganization responsible = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), responsibleDivisionId);
		if (null == responsible) {
			errorInfo = getText(ErrorConstants.FORBIDDEN_TO_INTERNALORGANIZATION);
			return false;
		}
		project.setResponsibleDivision(responsible);

		InternalOrganization contructing = InternalOrganization.get(constructingOrgId);
		if (null == contructing) {
			errorInfo = "必须确定以哪家公司名义开展项目。";
			return false;
		}

		project.setConstructingOrg(contructing);

		return true;
	}

	/**
	 * 设置项目的预算信息
	 * 
	 * @param project
	 * @return
	 */
	protected Project initProjectBudgetAndIncome(Project project) {
		project.setEstimatedIncome(convertTenThousandToYuan(estimatedIncome));
		project.setEnterpriseIncomeTax(convertTenThousandToYuan(enterpriseIncomeTax));
		project.setEnterpriseManagementCosts(convertTenThousandToYuan(enterpriseManagementCosts));
		project.setGrossMargin(grossMargin);
		project.setNetProfitMargin(netProfitMargin);
		project.setGrossProfit(convertTenThousandToYuan(grossProfit));
		project.setNetProfit(convertTenThousandToYuan(netProfit));
		setCapitaltotakeupOf(project);
		initProjectCost(project);

		return project;
	}

	private Project setCapitaltotakeupOf(Project project) {
		if (null == capitaltotakeup) {
			return project;
		}
		for (Iterator<Capitaltotakeup> it = capitaltotakeup.iterator(); it.hasNext();) {
			Capitaltotakeup each = it.next();
			if (null == each) {
				continue;
			}
			each.setCostFunds(convertTenThousandToYuan(each.getCostFunds()));
			each.setExpectedFunds(convertTenThousandToYuan(each.getExpectedFunds()));
		}
		project.setCapitaltotakeups(new HashSet<Capitaltotakeup>(capitaltotakeup));
		return project;
	}

	private Project initProjectCost(Project project) {
		if (null == project) {
			throw new RuntimeException("project 不能为空");
		}

		Map<ExpenditureType, BigDecimal> budgets = new LinkedHashMap<ExpenditureType, BigDecimal>();
		budgets.put(ExpenditureType.OPERATION, convertTenThousandToYuan(operation));
		budgets.put(ExpenditureType.SALARY, convertTenThousandToYuan(salary));
		budgets.put(ExpenditureType.MARKET, convertTenThousandToYuan(market));
		budgets.put(ExpenditureType.DEVICE_DEPRECIATION, convertTenThousandToYuan(deviceDepreciation));
		budgets.put(ExpenditureType.AUXILIARY_MATERIAL, convertTenThousandToYuan(auxiliaryMaterial));
		budgets.put(ExpenditureType.SUBCONTRACT, convertTenThousandToYuan(subcontract));
		budgets.put(ExpenditureType.MAIN_MATERIAL, convertTenThousandToYuan(mainMaterial));
		budgets.put(ExpenditureType.FUND_OCCUPATION, convertTenThousandToYuan(fundOccupation));
		budgets.put(ExpenditureType.TAXES, convertTenThousandToYuan(taxes));
		budgets.put(ExpenditureType.OTHER, convertTenThousandToYuan(other));

		project.setBudgets(budgets);

		project.setCustomBudgets(getCustomBudgetsMap());

		return project;
	}

	@org.apache.struts2.json.annotations.JSON(serialize = false)
	protected boolean isCustomBudgetNotNull() {
		return customBudgetAmount != null && !customBudgetAmount.isEmpty() && customBudgetName != null && !customBudgetName.isEmpty() && customBudgetName.size() == customBudgetAmount.size();
	}

	@org.apache.struts2.json.annotations.JSON(serialize = false)
	protected Map<String, BigDecimal> getCustomBudgetsMap() {
		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();
		if (isCustomBudgetNotNull()) {
			for (int i = 0; i < customBudgetName.size(); i++) {
				String serialNumber = customBudgetName.get(i);
				BigDecimal amount = customBudgetAmount.get(i);
				Dictionary dic = Dictionary.getDictionaryBySerialNumBerAndCategory(serialNumber, DictionaryCategory.CUSTOM_BUDGET_TYPE);
				if (null == dic || null == amount || BigDecimalUtils.leZero(amount)) {
					continue;
				}
				map.put(serialNumber, BigDecimalUtils.convertTenThousandToYuan(amount));
			}
		}
		return map;
	}

    /**
     * 得到成本总和
     * @return
     */
    protected BigDecimal getTotalBudgetAmount() {
        BigDecimal result = BigDecimal.ZERO;
        result = operation.add(salary).add(market).add(deviceDepreciation).add(auxiliaryMaterial).add(subcontract)
                .add(mainMaterial).add(fundOccupation).add(taxes).add(other);
        for (BigDecimal eachCustomBudget : customBudgetAmount) {
            if (null == eachCustomBudget) {
                continue;
            }
            result = result.add(eachCustomBudget);
        }
        return result;
    }



    public List<String> getCustomBudgetName() {
        return customBudgetName;
    }

    public void setCustomBudgetName(List<String> customBudgetName) {
        this.customBudgetName = customBudgetName;
    }

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public long getResponsibleDivisionId() {
        return responsibleDivisionId;
    }

    public void setResponsibleDivisionId(long responsibleDivisionId) {
        this.responsibleDivisionId = responsibleDivisionId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getOwnerContacterId() {
        return ownerContacterId;
    }

    public void setOwnerContacterId(long ownerContacterId) {
        this.ownerContacterId = ownerContacterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getConstructingOrgId() {
        return constructingOrgId;
    }

    public void setConstructingOrgId(Long constructingOrgId) {
        this.constructingOrgId = constructingOrgId;
    }

    public BigDecimal getEstimatedIncome() {
        return estimatedIncome;
    }

    public void setEstimatedIncome(BigDecimal estimatedIncome) {
        this.estimatedIncome = estimatedIncome;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public BigDecimal getGrossMargin() {
        return grossMargin;
    }

    public void setGrossMargin(BigDecimal grossMargin) {
        this.grossMargin = grossMargin;
    }

    public BigDecimal getEnterpriseManagementCosts() {
        return enterpriseManagementCosts;
    }

    public void setEnterpriseManagementCosts(BigDecimal enterpriseManagementCosts) {
        this.enterpriseManagementCosts = enterpriseManagementCosts;
    }

    public BigDecimal getEnterpriseIncomeTax() {
        return enterpriseIncomeTax;
    }

    public void setEnterpriseIncomeTax(BigDecimal enterpriseIncomeTax) {
        this.enterpriseIncomeTax = enterpriseIncomeTax;
    }

    public BigDecimal getNetProfitMargin() {
        return netProfitMargin;
    }

    public void setNetProfitMargin(BigDecimal netProfitMargin) {
        this.netProfitMargin = netProfitMargin;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    public BigDecimal getOperation() {
        return operation;
    }

    public void setOperation(BigDecimal operation) {
        this.operation = operation;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getMarket() {
        return market;
    }

    public void setMarket(BigDecimal market) {
        this.market = market;
    }

    public BigDecimal getDeviceDepreciation() {
        return deviceDepreciation;
    }

    public void setDeviceDepreciation(BigDecimal deviceDepreciation) {
        this.deviceDepreciation = deviceDepreciation;
    }

    public BigDecimal getAuxiliaryMaterial() {
        return auxiliaryMaterial;
    }

    public void setAuxiliaryMaterial(BigDecimal auxiliaryMaterial) {
        this.auxiliaryMaterial = auxiliaryMaterial;
    }

    public BigDecimal getSubcontract() {
        return subcontract;
    }

    public void setSubcontract(BigDecimal subcontract) {
        this.subcontract = subcontract;
    }

    public BigDecimal getMainMaterial() {
        return mainMaterial;
    }

    public void setMainMaterial(BigDecimal mainMaterial) {
        this.mainMaterial = mainMaterial;
    }

    public BigDecimal getFundOccupation() {
        return fundOccupation;
    }

    public void setFundOccupation(BigDecimal fundOccupation) {
        this.fundOccupation = fundOccupation;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getPredictFinishDate() {
        return predictFinishDate;
    }

    public void setPredictFinishDate(Date predictFinishDate) {
        this.predictFinishDate = predictFinishDate;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public List<BigDecimal> getCustomBudgetAmount() {
        return customBudgetAmount;
    }

    public void setCustomBudgetAmount(List<BigDecimal> customBudgetAmount) {
        this.customBudgetAmount = customBudgetAmount;
    }

    public List<Long> getSpecialtyIds() {
        return specialtyIds;
    }

    public void setSpecialtyIds(List<Long> specialtyIds) {
        this.specialtyIds = specialtyIds;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public List<Capitaltotakeup> getCapitaltotakeup() {
        return capitaltotakeup;
    }

    public void setCapitaltotakeup(List<Capitaltotakeup> capitaltotakeup) {
        this.capitaltotakeup = capitaltotakeup;
    }
}
