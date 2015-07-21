#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontracting;

import ${package}.domain.CooperationOrganization;
import ${package}.domain.ProjectSubcontracting;
import ${package}.webapp.action.BaseAction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 立项时设置项目的分包比例
 * User: zjzhai
 * Date: 13-4-8
 * Time: 下午2:31
 */
public class SetOutputvalueSubcontractingAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7002751492332660815L;

	private long id = 0l;

    private BigDecimal totalOutputvalue = BigDecimal.ZERO;

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

    /**
     * 应付款总和
     */
    private BigDecimal outputvaluePayableTotal = BigDecimal.ZERO;

    /**
     * 已经分配的产值总和
     */
    private  BigDecimal outputvalueDistributiveShareTotal = BigDecimal.ZERO;

    private BigDecimal grossMargin = BigDecimal.ZERO;

    private BigDecimal grossProfit = BigDecimal.ZERO;

    private boolean result = false;

    @Override
    public String execute() throws Exception {

        //TODO 暂时关闭产值
        result = true;
        return JSON;

        /*if (id <= 0) {
            return NOT_FOUND;
        }

        Project project = getProjectOf(id);

        if (null == project) {
            return NOT_FOUND;
        }

        if (totalOutputvalue == null || BigDecimal.ZERO.equals(totalOutputvalue)) {
            errorInfo = "产值必须设置一个值";
            return JSON;
        }


        project.setTotalOutputvalue(totalOutputvalue);
        project.setSubcontractings(createBy(orgIds, distributives, ratios));

        projApplication.saveEntity(project);

        grossMargin = project.getGrossMargin();

        grossProfit = project.getGrossProfit();

        outputvalueDistributiveShareTotal = project.getOutputvalueDistributiveShareTotal();

        outputvaluePayableTotal = project.getOutputvaluePayableTotal();

        result = true;
        return JSON;*/
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

    public BigDecimal getGrossMargin() {
        return grossMargin;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getTotalOutputvalue() {
        return totalOutputvalue;
    }

    public void setTotalOutputvalue(BigDecimal totalOutputvalue) {
        this.totalOutputvalue = totalOutputvalue;
    }

    public List<BigDecimal> getDistributives() {
        return distributives;
    }

    public void setDistributives(List<BigDecimal> distributives) {
        this.distributives = distributives;
    }

    public List<BigDecimal> getRatios() {
        return ratios;
    }

    public void setRatios(List<BigDecimal> ratios) {
        this.ratios = ratios;
    }

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }

    public boolean isResult() {
        return result;
    }

    public BigDecimal getOutputvaluePayableTotal() {
        return outputvaluePayableTotal;
    }

    public BigDecimal getOutputvalueDistributiveShareTotal() {
        return outputvalueDistributiveShareTotal;
    }
}
