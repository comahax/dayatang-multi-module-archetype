#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.license;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 * 证书类型字典
 * User: tune
 * Date: 13-6-5
 * Time: 下午3:05
 */
public class CredTypeJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8886790836727188277L;
	private List<Dictionary> results;

    public String execute() throws Exception {
        results = Dictionary.findByCategory(DictionaryCategory.ORGANIZATION_CREDENTIALS_TYPE);
        List<Dictionary> personsDate = Dictionary.findByCategory(DictionaryCategory.PERSON_CREDENTIALS_TYPE);
        results.addAll(personsDate);
        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name="rows")
    public List<Dictionary> getResults() {
        return results;
    }

}
