#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.dictionary;

import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-3-27
 * Time: 上午9:10
 */
public class EditBaseAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2098714068617901318L;

	private String serialNumber;

    private String text;

    private int sortOrder;

    private DictionaryCategory category;

    private String remark;

    private int version;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public DictionaryCategory getCategory() {
        return category;
    }

    public void setCategory(DictionaryCategory category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
