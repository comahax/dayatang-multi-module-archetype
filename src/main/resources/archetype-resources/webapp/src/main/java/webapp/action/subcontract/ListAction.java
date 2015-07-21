#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontract;

import org.apache.struts2.convention.annotation.Result;

import ${package}.webapp.action.BaseAction;

@Result(name = "success", type = "freemarker", location = "list.ftl")
public class ListAction extends BaseAction {

	private static final long serialVersionUID = 8768129905109440279L;

	public String execute() {
		return SUCCESS;
	}

}
