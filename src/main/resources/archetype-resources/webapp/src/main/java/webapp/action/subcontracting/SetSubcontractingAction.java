#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontracting;

import ${package}.domain.CooperationOrganization;
import ${package}.domain.Project;
import ${package}.domain.ProjectSubcontracting;
import ${package}.webapp.action.BaseAction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

/**
 * 设置分包比例 User: zjzhai Date: 13-4-8 Time: 下午2:31
 */
public class SetSubcontractingAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6157931160784285589L;

	private Long id = 0l;

	/**
	 * 合作方
	 */
	private List<Long> orgIds = new ArrayList<Long>();

	/**
	 * 分配份额
	 */
	private List<BigDecimal> distributives = new ArrayList<BigDecimal>();

	/**
	 * 分包比例
	 */
	private List<BigDecimal> ratios = new ArrayList<BigDecimal>();

	private List<ProjectSubcontracting> results;

	/**
	 * 应付款总和
	 */
	private BigDecimal outputvaluePayableTotal = BigDecimal.ZERO;

	/**
	 * 已经分配的产值总和
	 */
	private BigDecimal outputvalueDistributiveShareTotal = BigDecimal.ZERO;


	@Override
	public String execute() throws Exception {

		Project project = getProjectOf(id);

		if (null == project) {
			return NOT_FOUND;
		}

		results = createBy(orgIds, distributives, ratios);

		project.setSubcontractings(results);

		projApplication.saveEntity(project);

		outputvalueDistributiveShareTotal = project.getOutputvalueDistributiveShareTotal();

		outputvaluePayableTotal = project.getOutputvaluePayableTotal();

		return JSON;
	}

	private List<ProjectSubcontracting> createBy(List<Long> orgIds, List<BigDecimal> distributives, List<BigDecimal> ratios) {
		if (null == orgIds || null == distributives || null == ratios) {
			return null;
		}
		int size = orgIds.size();
		if (distributives.size() != size || ratios.size() != size) {
			return null;
		}

		List<ProjectSubcontracting> results = new ArrayList<ProjectSubcontracting>();

		for (int i = 0; i < orgIds.size(); i++) {
			Long each = orgIds.get(i);
			if (null == each) {
				continue;
			}
			CooperationOrganization cooperationOrganization = CooperationOrganization.get(each);
			if (null == cooperationOrganization) {
				continue;
			}
			BigDecimal distributive = BigDecimal.ZERO;
			if (null != distributives) {
				distributive = distributives.get(i);
			}

			BigDecimal ratio = BigDecimal.ZERO;
			if (null != ratio) {
				ratio = ratios.get(i);
			}

			ProjectSubcontracting subcontracting = ProjectSubcontracting.createBy(cooperationOrganization, distributive, ratio);
			results.add(subcontracting);
		}

		return results;
	}

	public List<ProjectSubcontracting> getResults() {
		return results;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSON(serialize = false)
	public List<BigDecimal> getDistributives() {
		return distributives;
	}

	public void setDistributives(List<BigDecimal> distributives) {
		this.distributives = distributives;
	}

	@JSON(serialize = false)
	public List<BigDecimal> getRatios() {
		return ratios;
	}

	public void setRatios(List<BigDecimal> ratios) {
		this.ratios = ratios;
	}

	@JSON(serialize = false)
	public List<Long> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<Long> orgIds) {
		this.orgIds = orgIds;
	}


	public BigDecimal getOutputvaluePayableTotal() {
		return outputvaluePayableTotal;
	}

	public BigDecimal getOutputvalueDistributiveShareTotal() {
		return outputvalueDistributiveShareTotal;
	}
}
