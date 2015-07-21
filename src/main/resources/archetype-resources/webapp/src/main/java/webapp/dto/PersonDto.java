#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonDto extends BasePersonDto {

    private OrganizationDto organization;

    public PersonDto(Person person) {
        super(person);

    }


    public static List<PersonDto> createBy(Collection<Person> persons) {
        List<PersonDto> results = new ArrayList<PersonDto>();
        if (persons == null) {
            return results;
        }

        for (Person each : persons) {
            results.add(new PersonDto(each));
        }

        return results;
    }

    public static List<PersonDto> idNameOrgOf(Collection<Person> persons) {
        List<PersonDto> results = new ArrayList<PersonDto>();
        if (persons == null) {
            return results;
        }

        for (Person each : persons) {

            results.add(idNameOrgOf(each));
        }
        return results;
    }

    /**
     * 返回只有一部分属性的Dto
     *
     * @param person
     * @return
     */
    public static PersonDto idNameOrgOf(Person person) {
        PersonDto result = new PersonDto();
        if (null == person) {
            return result;
        }
        result.setId(person.getId());
        result.setName(person.getName());
        if (person.getOrganization() != null) {
            result.organization = new OrganizationDto(person.getOrganization());
        }
        return result;
    }

    private PersonDto() {
    }


    public OrganizationDto getOrganization() {
        return organization;
    }


}
