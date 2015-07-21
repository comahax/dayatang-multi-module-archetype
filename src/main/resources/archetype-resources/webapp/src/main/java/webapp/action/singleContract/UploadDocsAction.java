#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.SingleContract;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.UploadBaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

/**
 * 上传单项合同附件
 * User: zjzhai
 * Date: 13-5-5
 * Time: 上午10:57
 */
public class UploadDocsAction extends UploadBaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5672133029451885305L;

	/**
     * 单项合同的
     */
    private Long id = 0l;

    private Boolean result = false;

    @Override
    public String execute() throws Exception {

        SingleContract contract = SingleContractQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();

        if (null == contract) {
            return JSON;
        }

        DocumentTagGenerater tagGenerater =  createDefaultDocTagGenerater().singleContract(id);

        if (contract.getFrameworkContract() != null) {
            tagGenerater.frameworkContract(contract.getFrameworkContract().getId());
        }

        if (contract.getProject() != null) {
            tagGenerater.project(contract.getProject().getId());
        }

        saveDocumentsNowWith(tagGenerater.generate());

        result = true;

        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getResult() {
        return result;
    }
}
