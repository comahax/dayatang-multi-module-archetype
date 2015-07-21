#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.person;

import ${package}.domain.Gender;
import ${package}.domain.Person;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午4:28
 */
public class AddBaseAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7513800109591542673L;

	private String name;

    private Gender gender;

    private String title;

    private String mobile;

    private String tel;

    private String email;

    private String qq;

    public void init(Person person){
        person.setName(name);
        person.setGender(gender);
        person.setTitle(title);
        person.setMobile(mobile);
        person.setTel(tel);
        person.setEmail(email);
        person.setQq(qq);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
