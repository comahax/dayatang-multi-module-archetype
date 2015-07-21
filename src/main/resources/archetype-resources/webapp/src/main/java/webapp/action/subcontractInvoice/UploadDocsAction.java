#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontractInvoice;

import ${package}.domain.SubcontractInvoice;
import ${package}.query.SubContractInvoiceQuery;
import ${package}.webapp.action.UploadBaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

/**
 * 上传分包发票附件 User: zjzhai Date: 13-5-5 Time: 上午10:57
 */
public class UploadDocsAction extends UploadBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5672133029451885305L;

	/**
	 * 分包合同发票的ID
	 */
	private Long id = 0l;

	@Override
	public String execute() throws Exception {

		SubcontractInvoice invoice = SubContractInvoiceQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();

		if (null == invoice) {
			return JSON;
		}

		DocumentTagGenerater tagGenerater = createDefaultDocTagGenerater().contract(invoice.getContract().getId())
				.subContract(invoice.getContract().getId()).subContractInvoice(id);

		if (invoice.getContract().getProject() != null) {
			tagGenerater.project(invoice.getContract().getProject().getId());
		}

		saveDocumentsNowWith(tagGenerater.generate());

		return JSON;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
