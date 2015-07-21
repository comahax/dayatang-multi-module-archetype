#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Organization;
import ${package}.domain.OrganizationInfo;
import ${package}.domain.Person;
import ${package}.webapp.action.BaseAction;

/**
 * 输出对象DTO,无默认构造函数
 *
 * @author zjzhai
 */
public class OrganizationInfoDto {

    private OrganizationDto organization;

    private PersonDto person;

    public OrganizationInfoDto(Organization organization, Person person, BaseAction action) {
        this.organization = new OrganizationDto(organization);
        this.person = new PersonDto(person);
    }

    public OrganizationInfoDto(OrganizationDto organization, PersonDto person) {
        this.organization = organization;
        this.person = person;
    }

    public OrganizationInfoDto(OrganizationInfo organizationInfo) {
        if (null != organizationInfo.getOrganization()) {
            this.organization = new OrganizationDto(organizationInfo.getOrganization());
        }
        if (null != organizationInfo.getPerson()) {
            this.person = new PersonDto(organizationInfo.getPerson());
        }
    }

    public OrganizationDto getOrganization() {
        return organization;
    }

    public PersonDto getPerson() {
        return person;
    }

}
