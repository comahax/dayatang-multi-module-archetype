#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.dayatang.domain.QuerySettings;
import ${package}.utils.DocumentTagConstans;

/**
 * 分包合同发票
 * 
 * @author zjzhai
 * 
 */
@Entity
@DiscriminatorValue("SUBCONTRACT")
public class SubcontractInvoice extends Invoice {

	private static final long serialVersionUID = 8684171265977620168L;

	@ManyToOne
	@JoinColumn(name = "subcontract_id")
	private SubContract contract;

	public SubContract getContract() {
		return contract;
	}

	public static List<SubcontractInvoice> findAllBySubContract(SubContract subContract) {
		return getRepository().find(QuerySettings.create(SubcontractInvoice.class).eq("contract.id", subContract.getId()));
	}

	public static BigDecimal getTotalOfSubcontractInvoiceAmount(SubContract subContract) {
		String sql = "SELECT SUM(o.amount) FROM SubcontractInvoice o WHERE o.contract = :contract";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract", subContract);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	public BigDecimal getTotalOfSubcontractPaymentAmount() {
		String sql = "SELECT SUM(o.amount) FROM SubcontractPayment o WHERE o.invoice = :invoice";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("invoice", this);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	public static BigDecimal getTotalOfSubcontractPaymentAmount(SubContract subContract) {
		String sql = "SELECT SUM(o.amount) FROM SubcontractPayment o WHERE o.invoice.contract = :contract";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract", subContract);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	public void setContract(SubContract contract) {
		this.contract = contract;
	}

	public void remove() {
		removeAllPayments();
		removeDocs();
		super.remove();
	}
	
	public void removeAllPayments(){
		for(SubcontractPayment each : SubcontractPayment.findByInvoice(this)){
			each.remove();
		}
	}

	/**
	 * 删除所有项目文档
	 */
	private void removeDocs() {
		for (Document each : Document.findByOneTag(new DocumentTag(DocumentTagConstans.SUBCONTRACT_INVOICE, getId()))) {
			each.remove();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contract == null) ? 0 : contract.hashCode());
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
		SubcontractInvoice other = (SubcontractInvoice) obj;
		if (contract == null) {
			if (other.contract != null)
				return false;
		} else if (!contract.equals(other.contract))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SubContractInvoice [contract=" + contract + "]";
	}

}
