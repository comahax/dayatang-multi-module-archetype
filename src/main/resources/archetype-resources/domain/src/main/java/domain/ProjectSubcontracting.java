#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 项目的分包
 * User: zjzhai
 * Date: 13-4-8
 * Time: 下午3:31
 */
@Embeddable
public class ProjectSubcontracting implements ValueObject {

	private static final long serialVersionUID = 3542590136512914238L;


	/**
     * 合作方
     */
    @ManyToOne
    @JoinColumn(name = "cooperation_organization_id")
    private CooperationOrganization cooperationOrganization;


    /**
     * 分配份额
     */
    @Column(name = "distributive_share")
    private BigDecimal distributiveShare;


    /**
     * 分包比例
     */
    @Column(name = "subcontracting_ratio")
    private BigDecimal subcontractingRatio;


    /**
     * 应付
     */
    private BigDecimal payable;

    private ProjectSubcontracting() {

    }

    private ProjectSubcontracting(CooperationOrganization cooperationOrganization, BigDecimal distributive, BigDecimal ratio, BigDecimal payable) {
        this.cooperationOrganization = cooperationOrganization;
        distributiveShare = distributive;
        subcontractingRatio = ratio;
        this.payable = payable;
    }

    public static ProjectSubcontracting createBy(CooperationOrganization cooperationOrganization, BigDecimal distributive, BigDecimal ratio) {
        ProjectSubcontracting result = new ProjectSubcontracting();

        result.setCooperationOrganization(cooperationOrganization);

        result.setDistributiveShare(distributive == null ? BigDecimal.ZERO : distributive);

        result.setSubcontractingRatio(ratio == null ? BigDecimal.ZERO : ratio);

        result.setPayable(computePayable(result.getDistributiveShare(), result.getSubcontractingRatio()));

        return result;

    }

    /**
     * 计算应付 = 分配份额 * 分包比例
     *
     * @param distributive 分配份额
     * @param ratio        分包比例(%)
     * @return
     */
    public static BigDecimal computePayable(BigDecimal distributive, BigDecimal ratio) {
        if (null == distributive || BigDecimal.ZERO.equals(distributive) || null == ratio || BigDecimal.ZERO.equals(ratio)) {
            return BigDecimal.ZERO;
        }

        return distributive.multiply(ratio.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
    }


    public BigDecimal getDistributiveShare() {
        return distributiveShare;
    }

    public void setDistributiveShare(BigDecimal distributiveShare) {
        this.distributiveShare = distributiveShare;
    }

    public BigDecimal getSubcontractingRatio() {
        return subcontractingRatio;
    }

    public void setSubcontractingRatio(BigDecimal subcontractingRatio) {
        this.subcontractingRatio = subcontractingRatio;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public CooperationOrganization getCooperationOrganization() {
        return cooperationOrganization;
    }

    public void setCooperationOrganization(CooperationOrganization cooperationOrganization) {
        this.cooperationOrganization = cooperationOrganization;
    }


}
