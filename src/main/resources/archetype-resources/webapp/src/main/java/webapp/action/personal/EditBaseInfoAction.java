#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.personal;

import ${package}.domain.Gender;
import ${package}.domain.Person;
import ${package}.domain.User;
import ${package}.webapp.action.BaseAction;
import org.apache.commons.lang3.StringUtils;

/**
 * 修改个人信息
 * User: zjzhai
 * Date: 13-5-15
 * Time: 下午8:11
 */
public class EditBaseInfoAction extends BaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = -5388537211944670673L;

	private String name;

    /**
     * 性别
     */
    private Gender gender;

    private String title;

    private String mobile;

    private String tel;

    private String email;

    private String qq;


    @Override
    public String execute() throws Exception {

        if (StringUtils.isEmpty(email)) {
            errorInfo = "邮箱不能为空！";
            return JSON;
        }

        if (null != User.getByEmail(email) && !getCurrentUser().getEmail().equals(email)) {
            errorInfo = "邮箱已被占用！";
            return JSON;
        }

        Person person = getCurrentPerson();

        person.setName(name);
        person.setTitle(title);
        person.setEmail(email);

        person.setGender(gender);
        person.setMobile(mobile);
        person.setQq(qq);
        person.setTel(tel);

        commonsApplication.saveEntity(person);


        User user = getCurrentUser();
        user.setEmail(email);
        securityApplication.saveEntity(user);

        return JSON;
    }



    public String getErrorInfo() {
        return errorInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
