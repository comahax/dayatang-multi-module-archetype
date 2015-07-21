#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ${package}.domain.Specialty;
import ${package}.domain.SpecialtyProject;
import ${package}.webapp.action.BaseAction;

/**
 * 专业项目
 *
 * @author zjzhai
 */
public class SpecialtyProjectDto {

    private Long id;

    private SubProjectDto subProject;

    private Specialty specialty;

    /**
     * 合作单位
     */
    private OrganizationDto team;

    private PersonDto teamLeader;

    private boolean disabled;

    public SpecialtyProjectDto(SpecialtyProject specialtyProject, BaseAction action) {
        id = specialtyProject.getId();

        disabled = specialtyProject.isDisabled();

        subProject = new SubProjectDto(specialtyProject.getSubProject());

        specialty = specialtyProject.getSpecialty();

        team = new OrganizationDto(specialtyProject.getTeam());

        teamLeader = new PersonDto(specialtyProject.getTeamLeader());
    }

    public static List<SpecialtyProjectDto> createBy(Collection<SpecialtyProject> specialtyProjects, BaseAction action) {
        List<SpecialtyProjectDto> results = new ArrayList<SpecialtyProjectDto>();

        if (null == specialtyProjects) {
            return results;
        }

        for (SpecialtyProject each : specialtyProjects) {
            results.add(new SpecialtyProjectDto(each, action));
        }

        return results;

    }

    public SubProjectDto getSubProject() {
        return subProject;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public Long getId() {
        return id;
    }

    public OrganizationDto getTeam() {
        return team;
    }

    public PersonDto getTeamLeader() {
        return teamLeader;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
