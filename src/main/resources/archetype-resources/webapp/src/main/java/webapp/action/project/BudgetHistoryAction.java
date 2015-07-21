#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Project;
import ${package}.domain.ProjectBudgetHistory;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ProjectCapitalBudgetEntryDto;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.convention.annotation.Result;
/**
 * 项目的收支预算历史记录
 * User: zjzhai
 * Date: 13-7-17
 * Time: 下午5:15
 */
@Result(name = "success", type="freemarker", location = "budget-history.ftl")
public class BudgetHistoryAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8462529195613087030L;

	/**
     * 项目的ＩＤ
     */
    private Long projectId = 0l;

    /**
     * 历史的ＩＤ
     */
    private Long id = 0l;

    private ProjectBudgetHistory history;

    /**
     * 项目的自定义的成本条目
     */
    private List<ProjectCapitalBudgetEntryDto> customBudgets = new ArrayList<ProjectCapitalBudgetEntryDto>();

    @Override
    public String execute() throws Exception {

        Project project = getProjectOf(projectId);

        if (null == project || id <= 0) {
            return NOT_FOUND;
        }

        history = ProjectBudgetHistory.findByProjectAndId(project, id);

        if (null != history) {
            customBudgets.addAll(ProjectCapitalBudgetEntryDto.createByCustomBudgetMap(history.getCustomBudgets()));
            customBudgets.addAll(ProjectCapitalBudgetEntryDto.createProjectCapitalBudgetEntryDtos(history.getBudgets
                    ())) ;
        }


        return SUCCESS;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public ProjectBudgetHistory getHistory() {
        return history;
    }

    public List<ProjectCapitalBudgetEntryDto> getCustomBudgets() {
        return customBudgets;
    }
}
