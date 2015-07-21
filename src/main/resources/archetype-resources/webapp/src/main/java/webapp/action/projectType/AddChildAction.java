#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.projectType;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;
import org.apache.commons.lang3.StringUtils;

/**
 * User: zjzhai
 * Date: 13-6-4
 * Time: 下午12:03
 */
public class AddChildAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3100775217253042685L;

	private String parent;

    private Dictionary type;


    @Override
    public String execute() throws Exception {
        if (null == type || StringUtils.isEmpty(parent)) {
            return NOT_FOUND;
        }

        Dictionary parentType = Dictionary.getDictionaryBySerialNumBerAndCategory(parent, DictionaryCategory.PROJECT_TYPE);
        if (null == parentType) {
            return NOT_FOUND;
        }


        if (Dictionary.isSerialNumberExist(type.getSerialNumber(), DictionaryCategory.PROJECT_TYPE)) {
            errorInfo = "该编号已经存在，请换一个。";
            return JSON;
        }

        if (Dictionary.isTextExist(type.getText(), DictionaryCategory.PROJECT_TYPE)) {
            errorInfo = "该项目类型已经存在，请换一个。";
            return JSON;
        }


        type.setParentSn(parent);

        type.setCategory(DictionaryCategory.PROJECT_TYPE);

        projApplication.saveEntity(type);


        return JSON;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }


    public Dictionary getType() {
        return type;
    }

    public void setType(Dictionary type) {
        this.type = type;
    }
}
