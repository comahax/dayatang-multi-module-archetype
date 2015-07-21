#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;


import ${package}.domain.ProjectSubcontracting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分包比例DTO
 * User: zjzhai
 * Date: 13-4-12
 * Time: 上午11:22
 */
public class ProjectSubcontractingDto {

    /**
     * 合作方
     */
    private OrganizationDto cooperationOrganization;

    /**
     * 分配份额
     */
    private BigDecimal distributiveShare;


    /**
     * 分包比例
     */
    private BigDecimal subcontractingRatio;


    /**
     * 应付
     */
    private BigDecimal payable;


    @SuppressWarnings("unused")
	private ProjectSubcontractingDto() {

    }

    public ProjectSubcontractingDto(ProjectSubcontracting subcontracting) {
        cooperationOrganization = new OrganizationDto(subcontracting.getCooperationOrganization());
        distributiveShare = subcontracting.getDistributiveShare();
        subcontractingRatio = subcontracting.getSubcontractingRatio();
        payable = subcontracting.getPayable();
    }

    public static List<ProjectSubcontractingDto> createBy(Collection<ProjectSubcontracting> subcontractings){
        List<ProjectSubcontractingDto> results = new ArrayList<ProjectSubcontractingDto>();
        for(ProjectSubcontracting each : subcontractings){
            results.add(new ProjectSubcontractingDto(each));
        }
        return results;
    }


    public OrganizationDto getCooperationOrganization() {
        return cooperationOrganization;
    }

    public BigDecimal getDistributiveShare() {
        return distributiveShare;
    }

    public BigDecimal getSubcontractingRatio() {
        return subcontractingRatio;
    }

    public BigDecimal getPayable() {
        return payable;
    }
}
