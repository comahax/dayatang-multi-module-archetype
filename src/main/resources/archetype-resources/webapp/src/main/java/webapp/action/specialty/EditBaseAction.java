#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.specialty;

import ${package}.webapp.action.BaseAction;


/**
 * User: zjzhai
 * Date: 13-4-3
 * Time: 下午1:55
 */
public abstract class EditBaseAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4202908532351796388L;

	private String name;

    private int sortOrder;

    private String remark;

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
