#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import com.dayatang.domain.QuerySettings;
import ${package}.utils.DocumentTagConstans;

/**
 * 分包合同
 * 
 * @author zjzhai
 * 
 */
@Entity
@DiscriminatorValue("SUB")
public class SubContract extends Contract {

	private static final long serialVersionUID = 5462333800525607091L;

	/**
	 * 属于哪个承包合同
	 */
	@ManyToOne
	@JoinColumn(name = "subcontract_cheng_bao_id")
	private ChengBao chengBao;

	/**
	 * 分包比例
	 */
	@Column(name = "subcontract_ratio")
	private BigDecimal subcontractRatio = BigDecimal.ZERO;

	/**
	 * 支付方式
	 */
	@Column(name = "subcontract_model_of_payment")
	private String modeOfPayment;

	/**
	 * 找出项目的所有的合作单位的信息
	 * 
	 * @param project
	 * @return
	 */
	public static List<OrganizationInfo> findAllCooperatorsOfProject(Project project) {
		List<SubContract> subContracts = SubContract.findAllByProject(project);
		if (null == subContracts) {
			return new ArrayList<OrganizationInfo>();
		}
		List<OrganizationInfo> results = new ArrayList<OrganizationInfo>();

		for (SubContract each : subContracts) {
			if (null == each.getPartB()) {
				continue;
			}
			results.add(each.getPartB());
		}
		return results;
	}

	public void remove() {
		removeDocs();
		super.remove();
	}
	
	/**
	 * 删除所有项目文档
	 */
	private void removeDocs() {
		for (Document each : Document.findByOneTag(new DocumentTag(DocumentTagConstans.SUB_CONTRACT, getId()))) {
			each.remove();
		}
	}

	public static List<SubContract> findAllByProject(Project project) {
		return getRepository().find(QuerySettings.create(SubContract.class).eq("project", project).desc("id"));
	}

	public SubContract() {

	}

	public SubContract(ChengBao chengBao) {
		this.chengBao = chengBao;
	}

  
    public ChengBao getChengBao() {
		return chengBao;
	}

	public void setChengBao(ChengBao chengBao) {
		this.chengBao = chengBao;
	}

	public BigDecimal getSubcontractRatio() {
		return subcontractRatio;
	}

	public void setSubcontractRatio(BigDecimal subcontractRatio) {
		this.subcontractRatio = subcontractRatio;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	@Override
	public String toString() {
		return "SubContract [chengBao=" + chengBao + ", subcontractRatio=" + subcontractRatio + ", modeOfPayment="
				+ modeOfPayment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chengBao == null) ? 0 : chengBao.hashCode());
		result = prime * result + ((modeOfPayment == null) ? 0 : modeOfPayment.hashCode());
		result = prime * result + ((subcontractRatio == null) ? 0 : subcontractRatio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubContract other = (SubContract) obj;
		if (chengBao == null) {
			if (other.chengBao != null)
				return false;
		} else if (!chengBao.equals(other.chengBao))
			return false;
		if (modeOfPayment == null) {
			if (other.modeOfPayment != null)
				return false;
		} else if (!modeOfPayment.equals(other.modeOfPayment))
			return false;
		if (subcontractRatio == null) {
			if (other.subcontractRatio != null)
				return false;
		} else if (!subcontractRatio.equals(other.subcontractRatio))
			return false;
		return true;
	}
	
	

}
