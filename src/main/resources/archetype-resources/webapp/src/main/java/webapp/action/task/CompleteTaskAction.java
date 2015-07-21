#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.task;

import ${package}.webapp.action.process.BaseProcessAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 完成任务并提交
 * 
 * @author yyang
 * 
 */

public class CompleteTaskAction extends BaseProcessAction {

	private static final long serialVersionUID = -7828244253190565534L;

	private String taskId;

    private boolean result = false;



	@Override
	public String execute() throws Exception {
		Map<String, String> formProperties = extractFormProperties();
		identityService.setAuthenticatedUserId(getCurrentUsername());
		formService.submitTaskFormData(taskId, formProperties);
        result = true;
		return JSON;
	}

    public boolean isResult() {
        return result;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }



    @SuppressWarnings("unchecked")
	private Map<String, String> extractFormProperties() {
		Map<String, String> results = new HashMap<String, String>();
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<String> fieldNames = parameterMap.keySet();
		for (String fieldName : fieldNames) {
			results.put(fieldName, parameterMap.get(fieldName)[0]);
		}
		return results;
	}
}
