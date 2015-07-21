#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Project;
import ${package}.domain.ProjectSerialNumberAssignment;
import ${package}.domain.ProjectStatus;
import com.dayatang.utils.Slf4jLogger;

import java.util.Date;

/**
 * 将项目保存为草稿
 * User: zjzhai
 * Date: 13-7-11
 * Time: 上午10:25
 */
public class SaveDraftAction extends EditBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6315564256959727423L;

	private static final Slf4jLogger LOGGER = Slf4jLogger.getLogger(SaveDraftAction.class);

    private long id = 0l;

    @Override
    public String execute() {
        try {
            Project project = new Project();
            initBaseDetailsOfDraftProject(project);
            initProjectBudgetAndIncome(project);

            ProjectSerialNumberAssignment projectSerialNumberAssignment = project.computeAndSetProjectNumber();
            projApplication.saveEntity(projectSerialNumberAssignment);
            project.log(getCurrentPerson(), new Date());
            project.setStatus(ProjectStatus.DRAFT);
            projApplication.saveEntity(project);
            id = project.getId();

        } catch (Exception e) {
            errorInfo = "服务器出错！";
            LOGGER.error(e.getLocalizedMessage());
        }
        return JSON;



    }

    public long getId() {
        return id;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
