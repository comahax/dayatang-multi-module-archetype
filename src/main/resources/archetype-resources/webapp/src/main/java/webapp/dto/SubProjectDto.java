#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.domain.SubProject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubProjectDto extends ProjectElementDto {

    private ProjectDto project;

    /**
     * 关联的单项合同
     */
    private SingleContractDto singleContract;


    /**
     * 接入点类型
     */
    private Dictionary apType;

    /**
     * 合同金额
     */
    private BigDecimal contractAmount;

    /**
     * 工程地址
     */
    private String address;


    private SupervisorOrganizationDto supervisorOrganization;

    private PersonDto supervisorPerson;

    private DesignOrganizationDto designOrganization;

    private PersonDto designPerson;


    public SubProjectDto(SubProject subProject) {
        super(subProject);
        if (null == subProject) {
            return;
        }
        project = new ProjectDto(subProject.getProject());
        if (null != subProject.getSingleContract()) {
            singleContract = new SingleContractDto(subProject.getSingleContract());

        }
        apType = Dictionary.getDictionaryBySerialNumBerAndCategory(subProject.getApType(), DictionaryCategory.AP_TYPE);
        contractAmount = subProject.getContractAmount();
        address = subProject.getAddress();
        if (null != subProject.getSupervisorInfo()) {
            supervisorOrganization = new SupervisorOrganizationDto(subProject.getSupervisorInfo().getOrganization());
            supervisorPerson = new PersonDto(subProject.getSupervisorInfo().getPerson());
        }
        if (null != subProject.getDesignInfo()) {
            designOrganization = new DesignOrganizationDto(subProject.getDesignInfo().getOrganization());
            designPerson = new PersonDto(subProject.getDesignInfo().getPerson());
        }




    }

    public static List<SubProjectDto> createBy(Collection<SubProject> subProjects) {
        List<SubProjectDto> results = new ArrayList<SubProjectDto>();

        if (null == subProjects) {
            return results;
        }

        for (SubProject each : subProjects) {
            results.add(new SubProjectDto(each));
        }

        return results;

    }

    public ProjectDto getProject() {
        return project;
    }

    public SingleContractDto getSingleContract() {
        return singleContract;
    }


    public Dictionary getApType() {
        return apType;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public String getAddress() {
        return address;
    }


    public SupervisorOrganizationDto getSupervisorOrganization() {
        return supervisorOrganization;
    }

    public PersonDto getSupervisorPerson() {
        return supervisorPerson;
    }

    public DesignOrganizationDto getDesignOrganization() {
        return designOrganization;
    }

    public PersonDto getDesignPerson() {
        return designPerson;
    }
}
