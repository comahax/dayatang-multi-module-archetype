#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ${package}.commons.BigDecimalUtils;
import ${package}.utils.DocumentTagConstans;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 单项合同
 * 
 * @author zjzhai
 */
@Entity
@DiscriminatorValue("FRAMEWORK")
public class FrameworkContract extends ChengBao {

	/**
	 * 初始总金额
	 */
	@Column(name = "framework_first_general_contract_amount")
	private BigDecimal firstGeneralContractAmount;

	private static final long serialVersionUID = 1L;

	public static FrameworkContract get(long id) {
		return FrameworkContract.get(FrameworkContract.class, id);
	}

	/**
	 * 是否指定初始金额
	 * 
	 * @return
	 */
	public static boolean isAssignFirstAmount(FrameworkContract contract) {
		if (null == contract.getFirstGeneralContractAmount()) {
			return false;
		}
		return BigDecimalUtils.geZero(contract.getFirstGeneralContractAmount());
	}

	@Override
	public void remove() {
		removeSingleContract();
		removeAllReceiptInvoices();
		removeDocs();
		super.remove();
	}

	/**
	 * 删除框架合同下的所有的单项合同
	 */
	public void removeSingleContract() {
		for (SingleContract singleContract : SingleContract.findByFrameworkContract(this)) {
			singleContract.setFrameworkContract(null);
			singleContract.save();

		}
	}

	public void removeAllReceiptInvoices() {
		for (ReceiptInvoice receiptInvoice : ReceiptInvoice.findBy(this)) {
			if (null == receiptInvoice) {
				continue;
			}
			receiptInvoice.remove();
		}
	}

	public void removeDocs() {
		DocumentTag frameworkTag = new DocumentTag(DocumentTagConstans.FRAMEWORK_CONTRACT, getId());
		for (Document doc : Document.findByOneTag(frameworkTag)) {
			doc.removeTag(frameworkTag);
			doc.save();
		}
	}

	/**
	 * 移除框架合同下面的单项合同
	 * 
	 * @param singleContracts
	 */
	public void removeSingleContractIn(List<SingleContract> singleContracts) {
		List<SingleContract> contracts = SingleContract.findByFrameworkContract(this);

		if (!contracts.containsAll(singleContracts)) {
			return;
		}

		BigDecimal totalAmount = BigDecimal.ZERO;
		DocumentTag frameworkTag = new DocumentTag(DocumentTagConstans.FRAMEWORK_CONTRACT, getId());
		Set<DocumentTag> tags = new HashSet<DocumentTag>();
		tags.add(frameworkTag);
		for (SingleContract singleContract : singleContracts) {
			singleContract.setFrameworkContract(null);
			singleContract.save();
			totalAmount.add(singleContract.getGeneralContractAmount());
			DocumentTag singleContractTag = new DocumentTag(DocumentTagConstans.SINGLE_CONTRACT, singleContract.getId());
			tags.add(singleContractTag);
			for (Document doc : Document.findByTags(tags)) {
				doc.removeTag(frameworkTag);
				doc.save();
			}
			tags.remove(singleContractTag);
		}
		if (!isAssignFirstAmount(this)) {
			setGeneralContractAmount(totalAmount);
			save();
		}
	}

	public String toString() {
		return getContractName();
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FrameworkContract)) {
			return false;
		}
		FrameworkContract that = (FrameworkContract) other;
		return new EqualsBuilder().append(getProject(), that.getProject()).append(getSerialNumber(), that.getSerialNumber()).append(getSerialNumber(), that.getSerialNumber()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getSerialNumber()).append(getProject()).append(getContractName()).toHashCode();
	}

	public BigDecimal getFirstGeneralContractAmount() {
		return firstGeneralContractAmount;
	}

	public void setFirstGeneralContractAmount(BigDecimal firstGeneralContractAmount) {
		this.firstGeneralContractAmount = firstGeneralContractAmount;
	}
}
