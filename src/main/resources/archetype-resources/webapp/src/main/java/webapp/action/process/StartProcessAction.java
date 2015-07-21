#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import ${package}.process.ProcessConstants;
import com.dayatang.utils.Slf4jLogger;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 根据流程定义启动新的流程实例
 *
 * @author yyang
 */
public class StartProcessAction extends BaseProcessAction {

    private static final long serialVersionUID = -7828244253190565534L;
    private Slf4jLogger logger = Slf4jLogger.getLogger(getClass());

    private String processDefinitionId;

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    public String execute() throws Exception {
        Map<String, String> formProperties = extractFormProperties();
        identityService.setAuthenticatedUserId(getCurrentUsername());
        ProcessInstance processInstance = formService.submitStartFormData(processDefinitionId, formProperties);
        logger.debug("{} start a processinstance: {} at {}", getCurrentUsername(), processInstance, new Date());
        return SUCCESS;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> extractFormProperties() {
        Map<String, String> results = new HashMap<String, String>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> fieldNames = parameterMap.keySet();
        for (String fieldName : fieldNames) {
            results.put(fieldName, parameterMap.get(fieldName)[0]);
        }
        results.put(ProcessConstants.INITIATOR, getCurrentUsername());
        results.put(ProcessConstants.INITIATOR_ORG, getGrantedScope().getName());
        results.put(ProcessConstants.INITIATOR_ORG_ID, getGrantedScope().getId().toString());
        results.put(ProcessConstants.INITIATOR_COMPANY_ID, getGrantedScope().getCompany().getId().toString());
        logger.debug("start form parameters: {}", results);
        return results;
    }
}
