#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.domain.InternalOrganizationCategory;
import ${package}.webapp.action.BaseAction;

/**
 * 添加，编辑机构的基类
 * User: zjzhai
 * Date: 13-5-6
 * Time: 上午12:11
 */
public abstract class AddBaseAction extends BaseAction {

	private static final long serialVersionUID = 7355898060641030421L;

	protected String name;

    protected String homePage;

    protected String tel;

    protected String email;

    protected String address;

    /**
     * 公司代码
     */
    protected String serialNumber;

    /**
     * 缩写
     */
    protected String abbr;

    protected InternalOrganizationCategory internalCategory;

    protected void init(InternalOrganization org) {
        org.setName(name);
        org.setHomePage(homePage);
        org.setTel(tel);
        org.setEmail(email);
        org.setAddress(address);
        org.setInternalCategory(internalCategory);
        org.setAbbr(abbr);
        org.setSerialNumber(serialNumber);
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

    public void setInternalCategory(InternalOrganizationCategory internalCategory) {
        this.internalCategory = internalCategory;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
