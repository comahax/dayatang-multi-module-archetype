#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.owner;

import ${package}.domain.OwnerOrganization;
import ${package}.webapp.action.BaseAction;

/**
 * 添加业主的基类
 * User: zjzhai
 * Date: 13-5-6
 * Time: 下午5:58
 */
public class AddBaseAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = -402017570969836127L;

	protected String name;

    protected String homePage;

    protected String tel;

    protected String email;

    protected String address;

    protected String ownerCategory;

    /**
     * 公司代码
     */
    protected String serialNumber;

    /**
     * 缩写
     */
    protected String abbr;


    protected void init(OwnerOrganization org) {
        if (null == org) {
            return;
        }
        org.setName(name);
        org.setAddress(address);
        org.setHomePage(homePage);
        org.setTel(tel);
        org.setEmail(email);
        org.setSerialNumber(serialNumber);
        org.setAbbr(abbr);
        org.setOwnerCategory(ownerCategory);

    }

    public void setOwnerCategory(String ownerCategory) {
        this.ownerCategory = ownerCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
