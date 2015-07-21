#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.InternalOrganization;
import ${package}.domain.Person;
import ${package}.domain.PersonUser;
import ${package}.domain.RoleAssignment;
import ${package}.query.PersonUserQuery;
import ${package}.webapp.action.BaseAction;

import java.util.*;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午5:09
 */
public class InternalPersonDto extends BasePersonDto {

    private InternalOrganizationDto org;

    /**
     * 内部机构的人员才会有角色分配
     */
    private Set<RoleAssignmentDto> roleAssignments;


    public InternalPersonDto(InternalOrganizationDto org) {
        this.org = org;
    }

    public static List<InternalPersonDto> createBy(Collection<Person> persons, BaseAction action) {
        List<InternalPersonDto> results = new ArrayList<InternalPersonDto>();
        if (persons == null) {
            return results;
        }

        for (Person each : persons) {
            results.add(new InternalPersonDto(each, action));
        }

        return results;
    }


    public InternalPersonDto(Person person, BaseAction action) {
        super(person);

        if (null != person && null != person.getOrganization()) {
            org = new InternalOrganizationDto((InternalOrganization) person.getOrganization(), action);
            roleAssignments = initRoleAssignment(person, action);

        }
    }


    private Set<RoleAssignmentDto> initRoleAssignment(Person person, BaseAction action) {
        Set<RoleAssignmentDto> roleAssignments = new HashSet<RoleAssignmentDto>();
        List<PersonUser> users = PersonUserQuery.create().person(person).list();
        for (PersonUser each : users) {
            List<RoleAssignment> assignments = RoleAssignment.findByUser(each);
            for (RoleAssignment assignment : assignments) {
                roleAssignments.add(new RoleAssignmentDto(assignment, action));
            }
        }
        return roleAssignments;
    }

    public Set<RoleAssignmentDto> getRoleAssignments() {
        return roleAssignments;
    }


    public InternalOrganizationDto getOrg() {
        return org;
    }
}
