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
 * Time: 下午3:00
 */
public class EditAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8272160758274636002L;

	private Long id;

    private String serialNumber;

    private String text;

    private String parentSn;

    private int sortOrder;


    @Override
    public String execute() throws Exception {

        if (null == id || id <= 0) {
            return NOT_FOUND;
        }


        Dictionary type = Dictionary.get(id);



        if (null == type) {
            return NOT_FOUND;
        }
        String oldTypeSerialNumber = type.getSerialNumber();

        Dictionary temp1 = Dictionary.getDictionaryBySerialNumBerAndCategory(serialNumber, DictionaryCategory.PROJECT_TYPE);
        if (temp1 != null && !temp1.getId().equals(id)) {
            errorInfo = "该编号已经存在，请换一个。";
            return JSON;
        }

        Dictionary temp2 = Dictionary.getDictionaryByTextAndCategory(text, DictionaryCategory.PROJECT_TYPE);
        if (temp2 != null && !temp2.getId().equals(id)) {
            errorInfo = "该项目类型已经存在，请换一个。";
            return JSON;
        }


        /**
         * 父类型不能移动到其他类型中去
         */
        if (!type.getSerialNumber().equals(parentSn) && Dictionary.hasChildrenOf(type)) {
            type.setParentSn(null);
        }else {
            type.setParentSn(parentSn);
        }

        type.setSerialNumber(serialNumber);
        type.setText(text);
        type.setSortOrder(sortOrder);


        projApplication.saveEntity(type);

        //更新已经存在的项目的项目类型的字段
        projApplication.editDictionary(oldTypeSerialNumber, type);

        return JSON;

    }

    public String getErrorInfo() {
        return errorInfo;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setParentSn(String parentSn) {
        this.parentSn = parentSn;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
