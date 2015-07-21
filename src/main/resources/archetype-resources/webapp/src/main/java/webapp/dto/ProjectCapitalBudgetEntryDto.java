#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.domain.ExpenditureType;
import ${package}.domain.Project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 项目资本预算 User: zjzhai Date: 13-8-14 Time: 上午11:43
 */
public class ProjectCapitalBudgetEntryDto implements Serializable{
	
	private static final long serialVersionUID = 5275039327877729907L;

    private BigDecimal amount;
    private String serialNumber;
    /**
     * 上一版本的金额
     */
    private BigDecimal preEditAmount;
    private String name;
    private String remark;

    @SuppressWarnings("unused")
	private ProjectCapitalBudgetEntryDto(){}

    public ProjectCapitalBudgetEntryDto(BigDecimal amount, String name, String remark, String serialNumber,
                                        BigDecimal preEditAmount) {
        this.amount = amount;
        this.preEditAmount = preEditAmount;
        this.name = name;
        this.remark = remark;
        this.serialNumber = serialNumber;
    }

    public static List<ProjectCapitalBudgetEntryDto> createProjectCapitalBudgetEntryDtos(Project project) {
        List<ProjectCapitalBudgetEntryDto> results = new ArrayList<ProjectCapitalBudgetEntryDto>();
        results.addAll(createProjectCapitalBudgetEntryDtos(project.getBudgets()));
        results.addAll(createByCustomBudgetMap(project.getCustomBudgets()));
        return results;

    }

    public static List<ProjectCapitalBudgetEntryDto> createProjectCapitalBudgetEntryDtos( Map<ExpenditureType, BigDecimal> budgets){
        List<ProjectCapitalBudgetEntryDto> results = new ArrayList<ProjectCapitalBudgetEntryDto>();
        if (null != budgets) {
            for (ExpenditureType each : budgets.keySet()) {
                if (null == each) {
                    continue;
                }
                String name = each.getText();
                String remark = each.getDescription();
                BigDecimal amount = budgets.get(each);
                results.add(new ProjectCapitalBudgetEntryDto(amount, name, remark, null, null));
            }
        }
       return results;
    }

    public static List<ProjectCapitalBudgetEntryDto> createByCustomBudgetMap(Map<String, BigDecimal> customBudget) {
        List<ProjectCapitalBudgetEntryDto> results = new ArrayList<ProjectCapitalBudgetEntryDto>();
        if (null != customBudget) {
            for (String each : customBudget.keySet()) {
                if (null == each) {
                    continue;
                }
                Dictionary dic = Dictionary.getDictionaryBySerialNumBerAndCategory(each, DictionaryCategory.CUSTOM_BUDGET_TYPE);
                String name = dic.getText();
                String remark = dic.getRemark();
                BigDecimal amount = customBudget.get(each);
                results.add(new ProjectCapitalBudgetEntryDto(amount, name, remark, each, null));
            }
        }
        return results;
    }

    /**
     * 创建一批具有上一版本金额的自定义成本的条目DTO
     *
     * @param project
     * @param newCustomBudget
     * @return
     */
    public static List<ProjectCapitalBudgetEntryDto> createHasPreEditAmountsBy(Map<String,
            BigDecimal> preEditCustomBudget, Map<String,
            BigDecimal> newCustomBudget) {
        List<ProjectCapitalBudgetEntryDto> results = new ArrayList<ProjectCapitalBudgetEntryDto>();
        Set<String>  allKeys = new HashSet<String>();
        if (preEditCustomBudget != null) {
            allKeys.addAll(preEditCustomBudget.keySet());
        }

        if (newCustomBudget != null) {
            allKeys.addAll(newCustomBudget.keySet());
        }

        for (String key : allKeys) {
            Dictionary dic = Dictionary.getDictionaryBySerialNumBerAndCategory(key, DictionaryCategory.CUSTOM_BUDGET_TYPE);
            if (null == dic) {
                continue;
            }
            String name = dic.getText();
            String remark = dic.getRemark();
            BigDecimal preEditAmount = preEditCustomBudget.get(key);
            BigDecimal amount = newCustomBudget.get(key);
            results.add(new ProjectCapitalBudgetEntryDto(amount, name, remark, key, preEditAmount));

        }
        return results;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPreEditAmount() {
        return preEditAmount;
    }

    public void setPreEditAmount(BigDecimal preEditAmount) {
        this.preEditAmount = preEditAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
