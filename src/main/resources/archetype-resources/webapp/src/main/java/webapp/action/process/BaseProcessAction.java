#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import com.dayatang.domain.InstanceFactory;
import ${package}.domain.*;
import ${package}.process.ProcessConstants;
import ${package}.webapp.action.BaseAction;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 流程Action的共同基类
 *
 * @author yyang
 */
public abstract class BaseProcessAction extends BaseAction {



    private static final long serialVersionUID = 6169885374669707160L;

    /**
     * 有关资金占用的在流程表单中的显示的模版文件名
     */
    private String CAPITALTOTAKEUP_TEMPLATE_NAME = "process-capitaltotakeups.ftl";
    
    protected HttpServletRequest request = ServletActionContext.getRequest();

    @Inject
    protected RepositoryService repositoryService;

    @Inject
    protected FormService formService;

    @Inject
    protected TaskService taskService;

    @Inject
    protected IdentityService identityService;

    @Inject
    protected HistoryService historyService;

    @Inject
    protected RuntimeService runtimeService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormat());


    public String getInitiator(Task task) {
        Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
        return (String) variables.get(ProcessConstants.INITIATOR);
    }


    public String getProcessName(Task task) {
        String processDefinitionId = task.getProcessDefinitionId();
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult().getName();
    }

    public String getProcessName(HistoricProcessInstance instance) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(instance.getProcessDefinitionId()).singleResult().getName();
    }

    public String getInitiator(ProcessInstance processInstance) {
        Map<String, Object> variables = runtimeService.getVariables(processInstance.getProcessInstanceId());
        return (String) variables.get(ProcessConstants.INITIATOR);
    }

    public String getTitle(ProcessInstance processInstance) {
        Map<String, Object> variables = runtimeService.getVariables(processInstance.getProcessInstanceId());
        return (String) variables.get(ProcessConstants.TITLE);
    }

    public String getTitle(Task task) {
        if (null == task) {
            return "";
        }
        ProcessInstance processInstance = getProcessInstance(task.getProcessInstanceId());

        if (null == processInstance) {
            return "";
        }

        Map<String, Object> variables = runtimeService.getVariables(processInstance.getProcessInstanceId());
        return (String) variables.get(ProcessConstants.TITLE);
    }


    public String getProcessKey(ProcessInstance processInstance) {
        String processDefinitionId = processInstance.getProcessDefinitionId();
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult()
                .getKey();
    }

    public String getProcessName(ProcessInstance processInstance) {
        String processDefinitionId = processInstance.getProcessDefinitionId();
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult()
                .getName();
    }

    public ProcessInstance getProcessInstance(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 当前节点
     *
     * @param processInstance
     * @return
     */
    public String getActivityName(ProcessInstance processInstance) {
        String activityId = getActivityId(processInstance);
        RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) InstanceFactory.getInstance(ProcessEngine.class)
                .getRepositoryService();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        for (ActivityImpl activity : processDefinition.getActivities()) {
            if (activity.getId().equals(activityId)) {
                return (String) activity.getProperty("name");
            }
        }
        return "";
    }

    public String getActivityName(Task task) {
        if (null == task) {
            return "";
        }
        return getActivityName(getProcessInstance(task.getProcessInstanceId()));
    }

    private String getActivityId(ProcessInstance processInstance) {
        Execution execution = runtimeService.createExecutionQuery().executionId(processInstance.getProcessInstanceId())
                .singleResult();// 执行实例
        Object property = null;
        try {
            property = PropertyUtils.getProperty(execution, "activityId");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return StringUtils.defaultString(property.toString());
    }






    //将附件转成html格式显示
    protected String docDownloadHtml(Collection<Document> docs, Long projectId) {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("docs", docs);
        input.put("projectId", projectId);
        input.put("basePath", getBasePath());
        return proccessTemplate("approval-download-project-doc.ftl", input);
    }

    /**
     * @param capitaltotakeup 以万元为单位的成本
     * @return
     */
    protected String capitatotakeupHtml(Collection<Capitaltotakeup> capitaltotakeup) {
        if (null == capitaltotakeup) {
            return "";
        }
        for (Iterator<Capitaltotakeup> it = capitaltotakeup.iterator(); it.hasNext();) {
            Capitaltotakeup each  = it.next();
            if (null == each) {
                it.remove();
            }
        }
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("capitaltotakeup", capitaltotakeup);
        return proccessTemplate(CAPITALTOTAKEUP_TEMPLATE_NAME, input);
    }

    protected Object getDate(Date date) {
        return date == null ? null : dateFormat.format(date);
    }

    protected Object getOrgName(OrganizationInfo ownerInfo) {
        return ownerInfo == null || ownerInfo.getOrganization() == null ? null : ownerInfo.getOrganization()
                .getName();
    }

    protected Object getOrgName(InternalOrganization responsibleDivision) {
        return responsibleDivision == null ? null : responsibleDivision.getFullName();
    }


}
