#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.dictionary;

import ${package}.domain.Dictionary;
import org.apache.commons.lang3.StringUtils;


/**
 * User: zjzhai
 * Date: 13-3-24
 * Time: 上午10:10
 */
public class AddAction extends EditBaseAction {

	private static final long serialVersionUID = -498922978070715994L;

	public String error;

    public Dictionary result;

    public String execute() throws Exception {
        if (null == getCategory()) {
            error = getText(Dictionary.THE_CATEGORY_OF_DICTIONARY_IS_REQUIRED);
            return JSON;
        }
        if (StringUtils.isEmpty(getSerialNumber()) || Dictionary.isSerialNumberExist(getSerialNumber(), getCategory())) {
            error = getText(Dictionary.THE_SERIALNUMBER_OF_DICTIONARY_IS_EXIST);
            return JSON;
        }

        if (StringUtils.isEmpty(getText()) || Dictionary.isTextExist(getText(), getCategory())) {
            error = getText(Dictionary.THE_TEXT_OF_DICTIONARY_IS_EXIST);
            return JSON;
        }

        result = new Dictionary();
        result.setCategory(getCategory());
        result.setSerialNumber(getSerialNumber());
        result.setText(getText());
        result.setRemark(getRemark());
        result.setSortOrder(getSortOrder());

        projApplication.saveEntity(result);
        return JSON;
    }



    public Dictionary getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}
