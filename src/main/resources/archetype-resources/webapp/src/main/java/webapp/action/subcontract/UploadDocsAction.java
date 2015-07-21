#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontract;

import ${package}.domain.SubContract;
import ${package}.query.SubContractQuery;
import ${package}.webapp.action.UploadBaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

/**
 * 上传分包合同附件 User: zjzhai Date: 13-5-5 Time: 上午10:57
 */
public class UploadDocsAction extends UploadBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5672133029451885305L;

	/**
	 * 分包合同的ID
	 */
	private Long id = 0l;


	@Override
	public String execute() throws Exception {

		SubContract contract = SubContractQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();

		if (null == contract) {
			return JSON;
		}

		DocumentTagGenerater tagGenerater = createDefaultDocTagGenerater().contract(id).subContract(id);

		if (contract.getProject() != null) {
			tagGenerater.project(contract.getProject().getId());
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
