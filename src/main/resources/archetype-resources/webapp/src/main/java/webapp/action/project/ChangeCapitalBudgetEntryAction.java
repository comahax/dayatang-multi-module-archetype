#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import org.apache.struts2.convention.annotation.Result;


/**
 * User: zjzhai
 * Date: 13-8-14
 * Time: 下午3:10
 */
@Result(name="success",type="freemarker", location="change-capital-budget-entry.ftl")
public class ChangeCapitalBudgetEntryAction extends PaymentsAction {

	private static final long serialVersionUID = -3016484913564750165L;

	public String execute() throws Exception {
           return super.execute();
       }


}
