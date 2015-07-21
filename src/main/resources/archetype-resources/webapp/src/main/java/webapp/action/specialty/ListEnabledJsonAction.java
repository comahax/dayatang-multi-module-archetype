#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.specialty;

import java.util.List;


import ${package}.domain.Specialty;
import ${package}.webapp.action.BaseAction;

public class ListEnabledJsonAction extends BaseAction {

	private static final long serialVersionUID = -101877100657362833L;

	private List<Specialty> results;

	public String execute() throws Exception {

		results = Specialty.findEnabledAll();

		return JSON;
	}

	public List<Specialty> getResults() {
		return results;
	}

}
