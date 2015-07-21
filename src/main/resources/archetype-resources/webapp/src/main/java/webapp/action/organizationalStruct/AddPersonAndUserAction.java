#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.*;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.ErrorConstants;
import ${package}.webapp.action.person.AddBaseAction;
import ${package}.webapp.dto.PersonDto;
import org.apache.commons.lang3.StringUtils;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午4:42
 */
public class AddPersonAndUserAction extends AddBaseAction {

	private static final long serialVersionUID = -3776995440235259094L;

	/**
     * 角色范围
     */
    private Long scopeId = 0l;

    /**
     * 角色的ID
     */
    private Long roleId = 0l;

    /**
     * 人员所在的机构的ID
     */
    private long orgId = 0l;

    private Person person;

    private String errorInfo;

    private PersonDto result;

    @Override
    public String execute() throws Exception {
        InternalOrganization org = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), orgId);
        if (null == org) {
            return JSON;
        }


        InternalOrganization scope = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), scopeId);
        if (null == scope || Role.isSuperSystemAdminRole(roleId)) {
            return NOT_FOUND;
        }

        if (person == null) {
            person = new Person();
        }

        init(person);

        person.setEmail(StringUtils.trim(getEmail()));
        if (User.isExist(person.getEmail())) {
            errorInfo = person.getEmail() + getText("THE_USERNAME_IS_EXIST");
            return JSON;
        }
        if (null == person.getGender()) {
            person.setGender(Gender.MALE);
        }

        person.setOrganization(org);
        commonsApplication.saveEntity(person);


        Role role = Role.get(roleId);

        if (null == role) {
            return NOT_FOUND;
        }

        //用户邮箱已存在
        if (User.getByEmail(person.getEmail()) !=null) {
            errorInfo = getText(ErrorConstants.THE_EMAIL_IS_EXISTS);
            return JSON;
        }

        PersonUser user = new PersonUser(person.getEmail(), securityApplication.getEncodeSystemDefaultPassword(), person);


        commonsApplication.saveEntity(user);

        securityApplication.assignRoleToUser(user, role, scope);

        result = new PersonDto(person);

        return JSON;
    }


    public PersonDto getResult() {
        return result;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
