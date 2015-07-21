#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.commons.SystemVariablesUtils;
import ${package}.domain.Person;
import ${package}.domain.PersonUser;
import ${package}.query.PersonQuery;
import ${package}.query.PersonUserQuery;
import ${package}.webapp.ErrorConstants;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 *
 * 恢复人员
 * User: zjzhai
 * Date: 13-4-10
 * Time: 下午2:44
 */
public class ResumePersonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3125632731484707250L;

	private Long id = 0l;

    private String errorInfo;

    @Override
    public String execute() throws Exception {

        Person person = PersonQuery.create().id(id).getSingleResult();

        if (null == person) {
            errorInfo = getText(ErrorConstants.RESOUCES_NOT_FOUND);
            return JSON;
        }

        if(!person.isDisabled()){
            return JSON;
        }

        resumePerson(person);

        noticeApplication.notice(person.getEmail(), "恢复帐号通知--" + SystemVariablesUtils.getSysnoticeTitle(),
                "您在" + SystemVariablesUtils.getTheSystemName() + "的" + person.getEmail() + "已被系统恢复，密码不变。");

        return JSON;
    }

    private void resumePerson(Person person){
        List<PersonUser> personUsers = PersonUserQuery.create().person(person).list();
        for (PersonUser each : personUsers) {

                each.enable();

            projApplication.saveEntity(each);
        }
        person.enable();
        projApplication.saveEntity(person);
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
