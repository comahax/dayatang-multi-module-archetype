#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;
import com.dayatang.utils.Assert;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产值
 *
 * @author zjzhai
 */
@Entity
@Table(name = "output_values", uniqueConstraints = @UniqueConstraint(columnNames = {"montly_year", "montly_month",
        "specialtyProject_id"}))
public class OutputValue extends AbstractEntity {

    private static final long serialVersionUID = 2143376586870197221L;

    /**
     * 项目
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    /**
     * 项目类型
     */
    @Column(name = "project_type")
    private String projectType;

    /**
     * 单点
     */
    @ManyToOne
    @JoinColumn(name = "subProject_id")
    private SubProject subProject;

    /**
     * 专业工程
     */
    @ManyToOne
    @JoinColumn(name = "specialtyProject_id")
    private SpecialtyProject specialtyProject;

    /**
     * 单项合同
     */
    @ManyToOne
    @JoinColumn(name = "single_contract_id")
    private SingleContract singleContract;

    /**
     * 月度
     */

    @Embedded
    @NotNull
    private Monthly monthly;

    /**
     * 产值
     */
    @Column(name = "numerical_value")
    private BigDecimal numericalValue;

    /**
     * 完成季度
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "to_quarter")
    private Quarter quarter;

    /**
     * 省
     */
    @ManyToOne
    @JoinColumn(name = "province")
    private Area province;

    /**
     * 市
     */
    @ManyToOne
    @JoinColumn(name = "city")
    private Area city;

    /**
     * 县
     */
    @ManyToOne
    @JoinColumn(name = "county")
    private Area county;

    /**
     * 产值所属机构
     */
    @ManyToOne
    @JoinColumn(name = "responsibleDivision")
    private InternalOrganization responsibleDivision;

    @ManyToOne
    @JoinColumn(name = "org1_id")
    private InternalOrganization org1;

    @ManyToOne
    @JoinColumn(name = "org2_id")
    private InternalOrganization org2;

    @ManyToOne
    @JoinColumn(name = "org3_id")
    private InternalOrganization org3;

    @ManyToOne
    @JoinColumn(name = "org4_id")
    private InternalOrganization org4;

    @ManyToOne
    @JoinColumn(name = "org5_id")
    private InternalOrganization org5;

    @ManyToOne
    @JoinColumn(name = "org6_id")
    private InternalOrganization org6;

    /**
     * 业主类型
     */
    private String ownerCategory;

    OutputValue() {
    }

    public OutputValue(Monthly monthly, Project project, BigDecimal numericalValue) {
        Assert.notNull(project);
        this.project = project;
        if (project.getOwnerInfo() != null && project.getOwnerInfo().getOrganization() != null) {
            ownerCategory = ((OwnerOrganization) project.getOwnerInfo().getOrganization()).getOwnerCategory();
        }
        projectType = project.getProjectType();
        this.numericalValue = numericalValue;
        Assert.notNull(monthly);
        this.monthly = monthly;
        quarter = Quarter.getQuarter(monthly.getMonth());
        setArea(project.getArea());
        setOrganization(project.getResponsibleDivision());
    }


    public OutputValue(Monthly monthly, SpecialtyProject specialtyProject, BigDecimal numericalValue) {
        this.specialtyProject = specialtyProject;
        this.subProject = specialtyProject.getSubProject();
        this.project = subProject.getProject();
        Assert.notNull(project);
        projectType = project.getProjectType();
        if (project.getOwnerInfo() != null && project.getOwnerInfo().getOrganization() != null) {
            ownerCategory = ((OwnerOrganization) project.getOwnerInfo().getOrganization()).getOwnerCategory();
        }
        this.singleContract = subProject.getSingleContract();
        this.numericalValue = numericalValue;
        Assert.notNull(monthly);
        this.monthly = monthly;
        quarter = Quarter.getQuarter(monthly.getMonth());
        setArea(subProject.getProject().getArea());
        setOrganization(subProject.getResponsibleDivision());
    }

    private void setArea(Area area) {
        int level = area.getLevel();
        if (level == Area.PROVINCE_LEVEL) {
            province = area;
            return;
        }
        if (level == Area.CITY_LEVEL) {
            city = area;
            province = city.getParent();
            return;
        }
        county = area;
        city = county.getParent();
        province = city.getParent();
    }

    private void setOrganization(InternalOrganization responsibleDivision) {
        // 设置机构
        setResponsibleDivision(responsibleDivision);
        OrganizationInfo ownerInfo = project.getOwnerInfo();
        if (null != ownerInfo && null != ownerInfo.getOrganization()
                && ownerInfo.getOrganization() instanceof OwnerOrganization) {
            ownerCategory = ((OwnerOrganization) ownerInfo.getOrganization()).getOwnerCategory();
        } else {
            ownerCategory = "";
        }

    }

    public void setResponsibleDivision(InternalOrganization responsibleDivision) {
        this.responsibleDivision = responsibleDivision;
        if (responsibleDivision.getLevel() == 0) {
            setOrg1(responsibleDivision);
            return;
        }
        if (responsibleDivision.getLevel() == 1) {
            setOrg2(responsibleDivision);
            setOrg1(org2.getParent());
            return;
        }
        if (responsibleDivision.getLevel() == 2) {
            setOrg3(responsibleDivision);
            setOrg2(org3.getParent());
            setOrg1(org2.getParent());
            return;
        }
        if (responsibleDivision.getLevel() == 3) {
            setOrg4(responsibleDivision);
            setOrg3(org4.getParent());
            setOrg2(org3.getParent());
            setOrg1(org2.getParent());
            return;
        }

        if (responsibleDivision.getLevel() == 4) {
            setOrg5(responsibleDivision);
            setOrg4(org5.getParent());
            setOrg3(org4.getParent());
            setOrg2(org3.getParent());
            setOrg1(org2.getParent());
            return;
        }
        if (responsibleDivision.getLevel() == 5) {
            setOrg6(responsibleDivision);
            setOrg5(org6.getParent());
            setOrg4(org5.getParent());
            setOrg3(org4.getParent());
            setOrg2(org3.getParent());
            setOrg1(org2.getParent());
            return;
        }
    }


    public static OutputValue get(Monthly monthly, SpecialtyProject specialtyProject) {
        QuerySettings<OutputValue> querySettings = QuerySettings.create(OutputValue.class)
                .eq("monthly.year", monthly.getYear()).eq("monthly.month", monthly.getMonth())
                .eq("specialtyProject", specialtyProject);
        return getRepository().getSingleResult(querySettings);
    }

    public static OutputValue get(Monthly monthly, Project project) {
        QuerySettings<OutputValue> querySettings = QuerySettings.create(OutputValue.class)
                .eq("monthly.year", monthly.getYear()).eq("monthly.month", monthly.getMonth())
                .eq("project", project);
        return getRepository().getSingleResult(querySettings);
    }

    public static BigDecimal totalOutputValueOf(List<OutputValue> outputValues) {
        BigDecimal result = BigDecimal.ZERO;
        if (null == outputValues) {
            return result;
        }

        for (OutputValue each : outputValues) {
            result = result.add(each.getNumericalValue());
        }
        return result;
    }


    /**
     *
     * @param oldType 旧类型
     * @param newType   新类型
     */
    public static void changeProjectType(String oldType, String newType) {
        String sql = "UPDATE OutputValue o SET o.projectType = :newType WHERE e.projectType = :oldType";
        Map<String ,Object> params = new HashMap<String, Object>();
        params.put("newType", newType);
        params.put("oldType", oldType);
        getRepository().executeUpdate(sql,params);
    }

    public static void changeOwnerType(String oldSerialNumber, String newSerialNumber) {
        String sql = "UPDATE OutputValue o SET o.ownerCategory = :newType WHERE e.ownerCategory = :oldType";
        Map<String ,Object> params = new HashMap<String, Object>();
        params.put("newType", newSerialNumber);
        params.put("oldType", oldSerialNumber);
        getRepository().executeUpdate(sql,params);
    }

    /**
     * 某机构的产值的总额
     * @param org
     * @return
     */
    public static BigDecimal totalOutputValueOf(InternalOrganization org){
        String sql = "SELECT SUM(o.numericalValue) FROM OutputValue o WHERE o." + getQueryKeyOf(org) + " = :org";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("org", org);
        return getRepository().getSingleResult(sql, params, BigDecimal.class);
    }

    public static String getQueryKeyOf(InternalOrganization scope) {
        int scopeLevel = scope.getLevel();
        String result = "org5";
        switch (scopeLevel) {
            case 0:
                result = "org1";
                break;
            case 1:
                result = "org2";
                break;

            case 2:
                result = "org3";
                break;
            case 3:
                result = "org4";
                break;
            case 4:
                result = "org5";
                break;
            default:
                break;
        }

        return result;

    }

    public static BigDecimal totalOutputValueOf(Project project) {
        String sql = "SELECT SUM(o.numericalValue) FROM OutputValue o WHERE o.project = :project";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("project", project);
        return getRepository().getSingleResult(sql, params, BigDecimal.class);
    }



    public static BigDecimal totalOutputValueOf(SubProject subProject) {
        String sql = "SELECT SUM(o.numericalValue) FROM OutputValue o WHERE o.subProject = :project";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("project", subProject);
        return getRepository().getSingleResult(sql, params, BigDecimal.class);
    }



    public static List<OutputValue> findBy(Project project) {
        List<OutputValue> results = new ArrayList<OutputValue>();
        QuerySettings<OutputValue> querySettings = QuerySettings.create(OutputValue.class).eq("project", project)
                .asc("monthly.year").asc("monthly.month").asc("quarter");
        results.addAll(getRepository().find(querySettings));
        return results;
    }

    public static List<OutputValue> findBy(SingleContract singleContract) {
        List<OutputValue> results = new ArrayList<OutputValue>();
        QuerySettings<OutputValue> querySettings = QuerySettings.create(OutputValue.class)
                .eq("singleContract", singleContract).asc("monthly.year").asc("monthly.month").asc("quarter");
        results.addAll(getRepository().find(querySettings));
        return results;
    }

    public static List<OutputValue> findBySubproject(SubProject subProject) {
        List<OutputValue> results = new ArrayList<OutputValue>();
        QuerySettings<OutputValue> querySettings = QuerySettings.create(OutputValue.class).eq("subProject", subProject)
                .asc("monthly.year").asc("monthly.month").asc("quarter");
        results.addAll(getRepository().find(querySettings));
        return results;
    }

    public static List<OutputValue> findBy(SpecialtyProject specialtyProject) {
        List<OutputValue> results = new ArrayList<OutputValue>();
        QuerySettings<OutputValue> querySettings = QuerySettings.create(OutputValue.class).eq("specialtyProject",
                specialtyProject).asc("monthly.year").asc("monthly.month").asc("quarter");
        results.addAll(getRepository().find(querySettings));
        return results;
    }




    public static OutputValue get(Long id) {
        return OutputValue.get(OutputValue.class, id);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof OutputValue))
            return false;
        OutputValue that = (OutputValue) other;
        return new EqualsBuilder().append(specialtyProject, that.getSpecialtyProject()).append(monthly, that.getMonthly())
                .isEquals();
    }

    public int getYear(){
        return monthly.getYear();
    }

    public int getMonth(){
        return monthly.getMonth();
    }


    public void remove() {
        OutputValueReporter reporter = OutputValueReporter.findBy(this);
        reporter.remove();
        super.remove();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(specialtyProject).append(monthly).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append(monthly).append(numericalValue).toString();
    }

    public BigDecimal getNumericalValue() {
        return numericalValue;
    }

    public void setNumericalValue(BigDecimal numericalValue) {
        this.numericalValue = numericalValue;
    }

    public Monthly getMonthly() {
        return monthly;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public SubProject getSubProject() {
        return subProject;
    }

    public SpecialtyProject getSpecialtyProject() {
        return specialtyProject;
    }

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
    }

    public void setSpecialtyProject(SpecialtyProject specialtyProject) {
        this.specialtyProject = specialtyProject;
    }

    public Area getProvince() {
        return province;
    }

    public Area getCity() {
        return city;
    }

    public Area getCounty() {
        return county;
    }

    public InternalOrganization getResponsibleDivision() {
        return responsibleDivision;
    }

    public InternalOrganization getOrg1() {
        return org1;
    }

    public void setOrg1(InternalOrganization org1) {
        this.org1 = org1;
    }

    public InternalOrganization getOrg2() {
        return org2;
    }

    public void setOrg2(InternalOrganization org2) {
        this.org2 = org2;
    }

    public InternalOrganization getOrg3() {
        return org3;
    }

    public void setOrg3(InternalOrganization org3) {
        this.org3 = org3;
    }

    public InternalOrganization getOrg4() {
        return org4;
    }

    public void setOrg4(InternalOrganization org4) {
        this.org4 = org4;
    }

    public String getOwnerCategory() {
        return ownerCategory;
    }

    public void setOwnerCategory(String ownerCategory) {
        this.ownerCategory = ownerCategory;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public SingleContract getSingleContract() {
        return singleContract;
    }

    public void setSingleContract(SingleContract singleContract) {
        this.singleContract = singleContract;
    }

    public InternalOrganization getOrg5() {
        return org5;
    }

    public void setOrg5(InternalOrganization org5) {
        this.org5 = org5;
    }

    public InternalOrganization getOrg6() {
        return org6;
    }

    public void setOrg6(InternalOrganization org6) {
        this.org6 = org6;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }



}
