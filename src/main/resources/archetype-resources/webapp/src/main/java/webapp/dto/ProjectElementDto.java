#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.math.BigDecimal;
import java.util.Date;

import ${package}.domain.Area;
import ${package}.domain.ProjectElement;
import org.apache.struts2.json.annotations.JSON;

public class ProjectElementDto {


    private Long id;

    private boolean disabled;

    /**
     * 工程项目名称
     */
    private String name;

    /**
     * 项目编号
     */
    private String projectNumber;

    /**
     * 开工日期
     */
    private Date startDate;

    /**
     * 完工日期
     */
    private Date finishDate;

    /**
     * 计划完工日期
     */

    private Date predictFinishDate;

    /**
     * 所属区域
     */
    private AreaDto area;

    /**
     * 项目关闭日期,关闭指的是项目结算完成
     */
    private Date closeDate;

    /**
     * 负责部门
     */
    private InternalOrganizationDto responsibleDivision;

    /**
     * 备注
     */
    private String remark;


    /**
     * 剩余工期
     */
    private Long remainingDuration;


    /**
     * 工期进展
     */
    private BigDecimal durationProcesser = BigDecimal.ZERO;

    /**
     * 工期
     */
    private Long duration;

    public ProjectElementDto() {

    }


    public ProjectElementDto(ProjectElement projectElement) {
        if (null == projectElement) {
            return;
        }
        disabled = projectElement.isDisabled();
        id = projectElement.getId();
        name = projectElement.getName();
        projectNumber = projectElement.getProjectNumber();
        startDate = projectElement.getStartDate();
        finishDate = projectElement.getFinishDate();
        predictFinishDate = projectElement.getPredictFinishDate();
        Area areaFoo = projectElement.getArea();
        if (null != area) {
            area = new AreaDto(areaFoo);
        }
        closeDate = projectElement.getCloseDate();
        responsibleDivision = new InternalOrganizationDto(projectElement.getResponsibleDivision());
        remark = projectElement.getRemark();
        duration = projectElement.getDuration();
        remainingDuration = projectElement.getRemainingDuration();
        durationProcesser = projectElement.getDurationProcesser();

    }

    public Long getRemainingDuration() {
        return remainingDuration;
    }

    public Long getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getFinishDate() {
        return finishDate;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getPredictFinishDate() {
        return predictFinishDate;
    }

    public AreaDto getArea() {
        return area;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public InternalOrganizationDto getResponsibleDivision() {
        return responsibleDivision;
    }

    public String getRemark() {
        return remark;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getDurationProcesser() {
        return durationProcesser;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
