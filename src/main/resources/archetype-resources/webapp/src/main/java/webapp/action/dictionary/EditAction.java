#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.dictionary;

import ${package}.domain.Dictionary;

/**
 * User: zjzhai
 * Date: 13-3-27
 * Time: 上午9:10
 */
public class EditAction extends EditBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9136861420108436671L;

	private Long id = 0l;

    private String error;

    private Dictionary result;

    @Override
    public String execute() throws Exception {
        Dictionary oldDic = null;
        String newDicSerialNumber = getSerialNumber();
        if (id > 0l) {
            oldDic = Dictionary.get(id);
            if (null == oldDic) {
                return WORKTABLE;
            }
        }
        if (!oldDic.getSerialNumber().equals(newDicSerialNumber)) {
            if (Dictionary.isSerialNumberExist(newDicSerialNumber, getCategory())) {
                error = getText(Dictionary.THE_SERIALNUMBER_OF_DICTIONARY_IS_EXIST);
                return JSON;
            }
        }
        if (!oldDic.getText().equals(getText())) {
            if (Dictionary.isTextExist(getText(), getCategory())) {
                error = getText(Dictionary.THE_TEXT_OF_DICTIONARY_IS_EXIST);
                return JSON;
            }
        }

        result = oldDic;
        result.setCategory(getCategory());
        result.setRemark(getRemark());
        result.setSortOrder(getSortOrder());
        result.setSerialNumber(newDicSerialNumber);
        result.setText(getText());

        projApplication.editDictionary(oldDic.getSerialNumber(), result);

        return JSON;
    }


    public String getError() {
        return error;
    }

    public Dictionary getResult() {
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
