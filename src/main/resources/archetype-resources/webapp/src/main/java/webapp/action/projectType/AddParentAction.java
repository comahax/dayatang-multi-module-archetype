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
 * Time: 下午12:03
 */
public class AddParentAction extends BaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = 7626460956540165299L;
	private Dictionary type;


    @Override
    public String execute() throws Exception {



        if (Dictionary.isSerialNumberExist(type.getSerialNumber(), DictionaryCategory.PROJECT_TYPE)) {
            errorInfo = "该编号已经存在，请换一个。";
            return JSON;
        }

        if (Dictionary.isTextExist(type.getText(), DictionaryCategory.PROJECT_TYPE)) {
            errorInfo = "该项目类型已经存在，请换一个。";
            return JSON;
        }

        type.setCategory(DictionaryCategory.PROJECT_TYPE);

        projApplication.saveEntity(type);


        return JSON;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public Dictionary getType() {
        return type;
    }

    public void setType(Dictionary type) {
        this.type = type;
    }
}
