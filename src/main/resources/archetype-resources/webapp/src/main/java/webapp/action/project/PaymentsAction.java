#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Capitaltotakeup;
import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.domain.Project;
import ${package}.domain.ProjectBudgetHistory;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ProjectCapitalBudgetEntryDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Result;

/**
 * 显示项目的收支情况 User: zjzhai Date: 13-6-5 Time: 下午3:58
 */
@Result(name = "success", type="freemarker", location = "payments.ftl")
public class PaymentsAction extends BaseAction {

	private static final long serialVersionUID = -3122866444171000741L;
	/**
	 * 项目的ID
	 */
	private Long id = 0l;
	private Project project;
	
	/**
	 * 占用资金成本 合计
	 */
	private BigDecimal totalCostFunds;
	/**
	 * 占用资金 合计
	 */
	private BigDecimal totalExpectedFunds;
	/**
	 * 项目的预算历史
	 */
	private List<ProjectBudgetHistory> histories;

    /**
     * 项目的所有的成本条目
     */
	private List<ProjectCapitalBudgetEntryDto> budgets;

    /**
     * 项目的自定义的成本条目
     */
    private List<ProjectCapitalBudgetEntryDto> customBudgets;

    /**
     * 自定义成本的类型
     */
    private List<Dictionary> customBudgetTypes;

    /**
     * 项目的资金占用成本
     */
    private Set<Capitaltotakeup> occupationOfFunds;

	@Override
	public String execute() throws Exception {

		project = getProjectOf(id);



		if (null == project) {
			return NOT_FOUND;
		}

		histories = ProjectBudgetHistory.findByProjectDescCreateTime(project);

		totalCostFunds = project.getTotalCapitalCost();

		totalExpectedFunds = project.getTotalExpectedFunds();

	    budgets =	ProjectCapitalBudgetEntryDto.createProjectCapitalBudgetEntryDtos(project);

        occupationOfFunds = project.getCapitaltotakeups();

        customBudgets = ProjectCapitalBudgetEntryDto.createByCustomBudgetMap(project.getCustomBudgets());

        customBudgetTypes = Dictionary.findByCategory(DictionaryCategory.CUSTOM_BUDGET_TYPE);

		return SUCCESS;
	}

	public boolean isHasCapitaltotakeup() {
		Set<Capitaltotakeup> capi = project.getCapitaltotakeups();

		return capi != null && !capi.isEmpty();
	}

	public List<ProjectBudgetHistory> getHistories() {
		return histories;
	}

	public List<ProjectCapitalBudgetEntryDto> getBudgets() {
		return budgets;
	}

	public Project getProject() {
		return project;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalExpectedFunds() {
		return totalExpectedFunds;
	}

	public BigDecimal getTotalCostFunds() {
		return totalCostFunds;
	}

    public Set<Capitaltotakeup> getOccupationOfFunds() {
        return occupationOfFunds;
    }

    public List<ProjectCapitalBudgetEntryDto> getCustomBudgets() {
        return customBudgets;
    }

	public List<Dictionary> getCustomBudgetTypes() {
		return customBudgetTypes;
	}
    
    
}
