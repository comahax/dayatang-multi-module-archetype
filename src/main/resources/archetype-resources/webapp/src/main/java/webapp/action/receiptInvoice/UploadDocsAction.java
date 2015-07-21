#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receiptInvoice;

import ${package}.domain.Contract;
import ${package}.domain.ContractCategory;
import ${package}.domain.ReceiptInvoice;
import ${package}.query.ContractQuery;
import ${package}.query.ReceiptInvoiceQuery;
import ${package}.webapp.action.UploadBaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

/**
 * User: zjzhai Date: 13-5-4 Time: 下午4:26
 */
public class UploadDocsAction extends UploadBaseAction {

	private static final long serialVersionUID = 8108030888478747338L;

	/**
	 * 总包发票的ID
	 */
	private Long id = 0l;

	/**
	 * 单项合同的ID
	 */
	private Long contractId = 0l;

	@Override
	public String execute() throws Exception {
		ReceiptInvoice invoice = ReceiptInvoiceQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();
		Contract contract = ContractQuery.grantedScopeIn(getGrantedScope()).id(contractId).getSingleResult();

		if (null == invoice || null == contract) {
			return JSON;
		}

		DocumentTagGenerater tagGenerater = createDefaultDocTagGenerater().receiptInvoice(id).contract(contract.getId());

		if (ContractCategory.FRAMEWORK_PROJECT_CONTRACT.equals(contract.getContractCategory())) {
			tagGenerater.frameworkContract(contract.getId());
		} else if (ContractCategory.SINGLE_PROJECT_CONTRACT.equals(contract.getContractCategory())) {
			tagGenerater.singleContract(contract.getId());
		}

		if (null != contract.getProject()) {
			tagGenerater.project(contract.getProject().getId());
		}

		saveDocumentsNowWith(tagGenerater.generate());

		return JSON;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
}
