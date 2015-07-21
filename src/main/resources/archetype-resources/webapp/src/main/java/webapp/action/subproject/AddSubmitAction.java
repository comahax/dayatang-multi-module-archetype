#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import java.util.Date;

import ${package}.domain.*;
import ${package}.query.PersonQuery;
import org.apache.commons.lang3.StringUtils;

import ${package}.webapp.action.BaseAction;

public class AddSubmitAction extends BaseAction {

    private static final long serialVersionUID = -7256832540805147365L;

    private Long id = 0l;

    private String name;

    private Date startDate;

    private Date predictFinishDate;

    private Date finishDate;

    private String apType;

    private String address;

    private String remark;

    private Long areaId = 0l;

    private Long projectId = 0l;

    private Long designOrgId = 0l;

    private Long designPersonId = 0l;

    private Long supervisorOrgId = 0l;

    private Long supervisorPersonId = 0l;

    // 负责部门
    private Long responsibleDivisionId = 0l;


    public String execute() throws Exception {
        SubProject subProject = getSubProject();

        // 所属项目
        Project project = getProjectOf(projectId);
        if (null == project || StringUtils.isEmpty(name)) {
            return NOT_FOUND;
        }
        subProject.setProject(project);

        subProject.setName(name);
        subProject.setStartDate(startDate);
        subProject.setPredictFinishDate(predictFinishDate);
        subProject.setFinishDate(finishDate);
        subProject.setApType(apType);
        subProject.setAddress(address);
        subProject.setRemark(remark);
        initArea(areaId, project);
        InternalOrganization organization = InternalOrganization.get(responsibleDivisionId);
        if (organization != null) {
            subProject.setResponsibleDivision(organization);
        } else {
            subProject.setResponsibleDivision(project.getResponsibleDivision());
        }

        DesignOrganization designOrganization = DesignOrganization.get(designOrgId);
        subProject.setDesignInfo(new OrganizationInfo(designOrganization, PersonQuery.create().organization(designOrganization).id(designPersonId).getSingleResult()));


        SupervisorOrganization supervisorOrganization = SupervisorOrganization.get(supervisorOrgId);
        subProject.setSupervisorInfo(new OrganizationInfo(supervisorOrganization, PersonQuery.create().organization(supervisorOrganization).id(supervisorPersonId).getSingleResult()));


        projApplication.saveEntity(subProject);
        id = subProject.getId();
        return JSON;
    }

    public String getErrorInfo() {
        return errorInfo;
    }


    private SubProject getSubProject() {
        SubProject subProject = null;
        if (id > 0l) {
            subProject = getSubProjectQuery().id(id).getSingleResult();
        }

        if (null == subProject) {
            subProject = new SubProject();
        }
        return subProject;
    }

    // 根据项目的区域来设置单点的区域
    private Area initArea(long areaId, Project project) {
        Area area = Area.get(areaId);
        if (area == null) {
            area = project.getArea();
        }
        return area;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    public void setResponsibleDivisionId(long responsibleDivisionId) {
        this.responsibleDivisionId = responsibleDivisionId;
    }


    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }


    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setPredictFinishDate(Date predictFinishDate) {
        this.predictFinishDate = predictFinishDate;
    }

    public void setApType(String apType) {
        this.apType = apType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setResponsibleDivisionId(Long responsibleDivisionId) {
        this.responsibleDivisionId = responsibleDivisionId;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public String getName() {
        return name;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public Date getStartDate() {
        return startDate;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public Date getPredictFinishDate() {
        return predictFinishDate;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public Date getFinishDate() {
        return finishDate;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public String getApType() {
        return apType;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public String getAddress() {
        return address;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public String getRemark() {
        return remark;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public Long getAreaId() {
        return areaId;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public Long getProjectId() {
        return projectId;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public Long getResponsibleDivisionId() {
        return responsibleDivisionId;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)

    public Long getDesignOrgId() {
        return designOrgId;
    }

    public void setDesignOrgId(Long designOrgId) {
        this.designOrgId = designOrgId;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)

    public Long getDesignPersonId() {
        return designPersonId;
    }

    public void setDesignPersonId(Long dessignPersonId) {
        this.designPersonId = dessignPersonId;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)

    public Long getSupervisorOrgId() {
        return supervisorOrgId;
    }

    public void setSupervisorOrgId(Long supervisorOrgId) {
        this.supervisorOrgId = supervisorOrgId;
    }

    public Long getSupervisorPersonId() {
        return supervisorPersonId;
    }

    public void setSupervisorPersonId(Long supervisorPersonId) {
        this.supervisorPersonId = supervisorPersonId;
    }
}
