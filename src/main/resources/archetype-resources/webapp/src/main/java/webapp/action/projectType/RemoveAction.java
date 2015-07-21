#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.projectType;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-6-4
 * Time: 下午3:42
 */
public class RemoveAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2630681405879933448L;
	private Long id = 0l;


    @Override
    public String execute() throws Exception {


        if (id <= 0) {
            return NOT_FOUND;
        }

        Dictionary type = Dictionary.get(id);
        if (!type.getCategory().equals(DictionaryCategory.PROJECT_TYPE)) {
            return NOT_FOUND;
        }

        projApplication.removeEntity(type);


        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
