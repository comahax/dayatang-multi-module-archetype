#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.List;

import com.dayatang.domain.QuerySettings;
import ${package}.utils.DocumentTagConstans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 单项合同
 * 
 * @author zjzhai
 */
@Entity
@DiscriminatorValue("SINGLE")
public class SingleContract extends ChengBao {

	private static final long serialVersionUID = 1L;

	/**
	 * 单项合同所对应到的框架合同
	 */
	@ManyToOne
	@JoinColumn(name = "singlecontract_frameworkcontract_id")
	private FrameworkContract frameworkContract;

	public SingleContract() {

	}

	public void remove() {
		// 设置单点的单项合同属性
		for (SubProject each : getSubProjects()) {
			each.cancelAssosiateToSingleContract();
			// 删除相应的单点付款
			for (Expenditure ex : Expenditure.getSubContractExpenditures(each)) {
				ex.remove();
			}
		}
		// 删除相应的收款发票
		for (ReceiptInvoice each : ReceiptInvoice.findBy(this)) {
			each.remove();
		}
		// 设置相应的产值
		for (OutputValue each : OutputValue.findBy(this)) {
			each.setSingleContract(null);
			each.save();
		}
		// 删除相应的文档
		removeDocs();
		super.remove();
	}

	public void removeDocs() {
		DocumentTag singleContractTag = new DocumentTag(DocumentTagConstans.SINGLE_CONTRACT, getId());
		for (Document doc : Document.findByOneTag(singleContractTag)) {
			doc.removeTag(singleContractTag);
			doc.save();
		}
	}

	public SingleContract(FrameworkContract frameworkContract) {
		this.frameworkContract = frameworkContract;
	}

	/**
	 * 找到某框架合同下的所有的单项合同
	 * 
	 * @param frameworkContract
	 * @return
	 */
	public static List<SingleContract> findByFrameworkContract(FrameworkContract frameworkContract) {
		return getRepository().find(QuerySettings.create(SingleContract.class).eq("frameworkContract", frameworkContract));
	}

	/**
	 * 单项合同是否已经绑定框架合同
	 * 
	 * @return
	 */
	public boolean notInAnyFramework() {
		return getFrameworkContract() == null ? true : false;
	}

	public static SingleContract get(Long singleContractId) {
		return SingleContract.get(SingleContract.class, singleContractId);
	}

	public String toString() {
		return getContractName();
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SingleContract)) {
			return false;
		}
		SingleContract that = (SingleContract) other;
		return new EqualsBuilder().append(this.getFrameworkContract(), that.getFrameworkContract()).append(getProject(), that.getProject()).append(getSerialNumber(), that.getSerialNumber()).append(getSerialNumber(), that.getSerialNumber()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getSerialNumber()).append(getProject()).append(getContractName()).toHashCode();
	}

	public FrameworkContract getFrameworkContract() {
		return frameworkContract;
	}

	public void setFrameworkContract(FrameworkContract frameworkContract) {
		this.frameworkContract = frameworkContract;
	}

}
