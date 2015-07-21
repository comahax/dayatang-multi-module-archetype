#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.webapp.action.BaseAction;
import com.google.gson.Gson;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午3:32
 */
public class TreeJsonAction extends BaseAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6937242142153714297L;
	private String result;

    public String execute() {
        result = new Gson().toJson(getGrantedScope().getWholeAllOrganizationTree());
        return JSON;
    }

    public String getResult() {
        return result;
    }
}
