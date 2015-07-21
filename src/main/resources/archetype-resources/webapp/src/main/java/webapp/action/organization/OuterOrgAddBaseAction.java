#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organization;

import ${package}.domain.Organization;
import ${package}.webapp.action.BaseAction;

/**
 * 添加外部机构的基类
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午4:05
 */
public abstract class OuterOrgAddBaseAction extends BaseAction {

	private static final long serialVersionUID = 6453668826737032913L;

	protected long id = 0l;

    private String name;

    private String homePage;

    private String tel;

    private String email;

    private String address;

    public void init(Organization org) {
        org.setName(name);
        org.setHomePage(homePage);
        org.setTel(tel);
        org.setEmail(email);
        org.setAddress(address);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(long id) {
        this.id = id;
    }
}
