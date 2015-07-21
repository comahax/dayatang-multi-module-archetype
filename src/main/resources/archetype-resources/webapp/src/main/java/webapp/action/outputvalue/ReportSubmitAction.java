#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.outputvalue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import ${package}.domain.*;
import org.apache.struts2.json.annotations.JSON;

import ${package}.webapp.action.BaseAction;

/**
 * 产值汇报
 */
public class ReportSubmitAction extends BaseAction {

	private static final long serialVersionUID = 6662942449335773510L;

	// 所报月份
	private int month;

	private int year;

	private List<Long> projectIds;

	// 产值
	private List<BigDecimal> outputValues;

	private boolean result = false;

	public String execute() throws Exception {
		if (null == projectIds || null == outputValues) {
			result = false;
			return NOT_FOUND;
		}
		reportOutputValue(projectIds, outputValues);
		result = true;

		return JSON;
	}

	/**
	 * 根据ID得到专业工程
	 * 
	 * @param projectIds
	 * @return
	 */
	private void reportOutputValue(List<Long> projectIds, List<BigDecimal> outputValues) {
		for (int i = 0; i < projectIds.size(); i++) {
			BigDecimal value = outputValues.get(i);
			Long projectId = projectIds.get(i);
			Project project = getProjectOf(projectId);
			if (null == value || null == project) {
				continue;
			}
			OutputValue outputValue = projApplication.reportOutputValue(new Monthly(year, month), project, value);
			projApplication.logOutputValueReporter(getCurrentPerson(), new Date(), outputValue);
		}
	}

	public boolean isResult() {
		return result;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@JSON(serialize = false)
	public List<BigDecimal> getOutputValues() {
		return outputValues;
	}

	public void setOutputValues(List<BigDecimal> outputValues) {
		this.outputValues = outputValues;
	}

	@JSON(serialize = false)
	public List<Long> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
	}
}
