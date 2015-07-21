#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ${package}.domain.Area;
import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.domain.InternalOrganization;
import ${package}.domain.OutputValue;
import ${package}.domain.Project;

/**
 * 输出对象DTO,无默认构造函数
 * 
 * @author wx.Zhou
 */
public class OutputvalueDto implements Serializable {

	public static final long serialVersionUID = 1388970930428301891L;

	private Long id;

	// 项目
	private String project;

	private Long projectId;

	// 产值
	private BigDecimal numericalValue;

	// 完成年份
	private String year;

	// 完成月份
	private String month;

	// 完成季度
	private String quarter;

	// 省
	private String province;

	// 市
	private String city;

	// 县
	private String county;

	// 业主类型
	private String ownerCategory;

	// 一级机构
	private String org1;

	// 二级机构
	private String org2;

	// 三级机构
	private String org3;

	// 四级机构
	private String org4;

	// 五级机构
	private String org5;

	// 业务分类
	private String projectType;

	private OutputvalueDto() {
	}

	public OutputvalueDto(Long id, String project, Long projectId, String singleContract, String subProject, String specialty,
			BigDecimal numericalValue, String year, String month, String quarter, String province, String city, String county,
			String ownerCategory, String org1, String org2, String org3, String org4, String org5, String projectType) {
		super();
		this.id = id;
		this.project = project;
		this.projectId = projectId;

		this.numericalValue = numericalValue;
		this.year = year;
		this.month = month;
		this.quarter = quarter;
		this.province = province;
		this.city = city;
		this.county = county;
		this.ownerCategory = ownerCategory;
		this.org1 = org1;
		this.org2 = org2;
		this.org3 = org3;
		this.org4 = org4;
		this.org5 = org5;
		this.projectType = projectType;
	}

	public static List<OutputvalueDto> createBy(Collection<OutputValue> outputValueCollection) {
		List<OutputvalueDto> results = new ArrayList<OutputvalueDto>();

		if (null == outputValueCollection) {
			return results;
		}

		for (OutputValue each : outputValueCollection) {

			results.add(createBy(each));

		}

		return results;
	}

	public static OutputvalueDto createBy(OutputValue outputValue) {
		OutputvalueDto result = new OutputvalueDto();
		result.id = outputValue.getId();
		result.project = getProjectName(outputValue);
		result.projectId = getProjectId(outputValue);
		result.numericalValue = outputValue.getNumericalValue();
		result.year = String.valueOf(outputValue.getMonthly().getYear());
		result.month = String.valueOf(outputValue.getMonthly().getMonth());
		result.quarter = outputValue.getQuarter().getZHText();
		result.province = getAreaName(outputValue.getProvince());
		result.city = getAreaName(outputValue.getCity());
		result.county = getAreaName(outputValue.getCity());
		result.county = getAreaName(outputValue.getCounty());
		result.ownerCategory = getOwnerCategory(outputValue);
		result.org1 = getOrgName(getOrg1Of(outputValue.getProject().getResponsibleDivision()));
		result.org2 = getOrgName(getOrg2Of(outputValue.getProject().getResponsibleDivision()));
		result.org3 = getOrgName(getOrg3Of(outputValue.getProject().getResponsibleDivision()));
		result.org4 = getOrgName(getOrg4Of(outputValue.getProject().getResponsibleDivision()));
		result.org5 = getOrgName(getOrg5Of(outputValue.getProject().getResponsibleDivision()));
		result.projectType = getProjectType(outputValue.getProject());
		return result;
	}

	/**
	 * 得到项目的第一级机构
	 * 
	 * @param responsibleDivision
	 * @return
	 */
	private static InternalOrganization getOrg1Of(InternalOrganization responsibleDivision) {
		if (responsibleDivision.getLevel() == 0) {
			return responsibleDivision;
		}
		if (responsibleDivision.getLevel() == 1) {
			return responsibleDivision.getParent();
		}
		if (responsibleDivision.getLevel() == 2) {
			return responsibleDivision.getParent().getParent();
		}
		if (responsibleDivision.getLevel() == 3) {
			return responsibleDivision.getParent().getParent().getParent();
		}
		if (responsibleDivision.getLevel() == 4) {
			return responsibleDivision.getParent().getParent().getParent().getParent();
		}
		if (responsibleDivision.getLevel() == 5) {
			return responsibleDivision.getParent().getParent().getParent().getParent().getParent();
		}
		return null;
	}

	/**
	 * 得到项目的第一级机构
	 * 
	 * @param responsibleDivision
	 * @return
	 */
	private static InternalOrganization getOrg2Of(InternalOrganization responsibleDivision) {
		if (responsibleDivision.getLevel() == 0) {
			return null;
		}
		if (responsibleDivision.getLevel() == 1) {
			return responsibleDivision;
		}
		if (responsibleDivision.getLevel() == 2) {
			return responsibleDivision.getParent();
		}
		if (responsibleDivision.getLevel() == 3) {
			return responsibleDivision.getParent().getParent();
		}
		if (responsibleDivision.getLevel() == 4) {
			return responsibleDivision.getParent().getParent().getParent();
		}
		if (responsibleDivision.getLevel() == 5) {
			return responsibleDivision.getParent().getParent().getParent().getParent();
		}
		return null;
	}

	/**
	 * 得到项目的第一级机构
	 * 
	 * @param responsibleDivision
	 * @return
	 */
	private static InternalOrganization getOrg3Of(InternalOrganization responsibleDivision) {
		if (responsibleDivision.getLevel() <= 1) {
			return null;
		}
		if (responsibleDivision.getLevel() == 2) {
			return responsibleDivision;
		}
		if (responsibleDivision.getLevel() == 3) {
			return responsibleDivision.getParent();
		}
		if (responsibleDivision.getLevel() == 4) {
			return responsibleDivision.getParent().getParent();
		}
		if (responsibleDivision.getLevel() == 5) {
			return responsibleDivision.getParent().getParent().getParent();
		}
		return null;
	}

	/**
	 * 得到项目的第一级机构
	 * 
	 * @param responsibleDivision
	 * @return
	 */
	private static InternalOrganization getOrg4Of(InternalOrganization responsibleDivision) {
		if (responsibleDivision.getLevel() <= 2) {
			return null;
		}
		if (responsibleDivision.getLevel() == 3) {
			return responsibleDivision;
		}
		if (responsibleDivision.getLevel() == 4) {
			return responsibleDivision.getParent();
		}
		if (responsibleDivision.getLevel() == 5) {
			return responsibleDivision.getParent().getParent();
		}
		return null;
	}

	/**
	 * 得到项目的第一级机构
	 * 
	 * @param responsibleDivision
	 * @return
	 */
	private static InternalOrganization getOrg5Of(InternalOrganization responsibleDivision) {
		if (responsibleDivision.getLevel() <= 3) {
			return null;
		}
		if (responsibleDivision.getLevel() == 4) {
			return responsibleDivision;
		}
		if (responsibleDivision.getLevel() == 5) {
			return responsibleDivision.getParent();
		}
		return null;
	}

	private static Long getProjectId(OutputValue outputValue) {
		Project project = outputValue.getProject();
		return null == project ? 0 : project.getId();
	}

	private static String getProjectName(OutputValue outputValue) {
		Project project = outputValue.getProject();
		return project == null ? "" : project.getName();
	}

	private static String getOwnerCategory(OutputValue outputValue) {
		return Dictionary.getDictionaryTextBySerialNumBerAndCategory(outputValue.getOwnerCategory(),
				DictionaryCategory.OWNER_TYPE);
	}

	public Long getId() {
		return id;
	}


	private static String getAreaName(Area area) {
		return null == area ? "" : area.getName();
	}

	private static String getOrgName(InternalOrganization org) {
		return null == org ? "" : org.getName();
	}

	public static String getProjectType(Project project) {
		if (null == project) {
			return "";
		}
		return Dictionary.getMap(DictionaryCategory.PROJECT_TYPE).get(project.getProjectType());
	}

	public String getProject() {
		return project;
	}

	public BigDecimal getNumericalValue() {
		return numericalValue;
	}

	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public String getQuarter() {
		return quarter;
	}

	public Long getProjectId() {
		return projectId;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getCounty() {
		return county;
	}

	public String getOwnerCategory() {
		return ownerCategory;
	}

	public String getOrg1() {
		return org1;
	}

	public String getOrg2() {
		return org2;
	}

	public String getOrg3() {
		return org3;
	}

	public String getOrg4() {
		return org4;
	}

	public String getOrg5() {
		return org5;
	}

	public String getProjectType() {
		return projectType;
	}

}
