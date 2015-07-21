#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.frameworkContract;


import ${package}.domain.ContractCategory;
import ${package}.domain.FrameworkContract;
import ${package}.domain.Project;
import ${package}.query.FrameworkContractQuery;
import ${package}.webapp.action.contract.AddBaseAction;

import java.math.BigDecimal;
import java.util.Date;


/**
 * User: zjzhai
 * Date: 13-4-17
 * Time: 下午10:04
 */
public class SaveAction extends AddBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1244574232422418928L;


	/**
     * 项目ID
     */
    private Long projectId = 0l;


    /**
     * 框架合同的ID
     */
    private Long id = 0l;

    private boolean result = false;


    @Override
    public String execute() throws Exception {

        Project project = getProjectOf(projectId);

        if (null == project) {
            errorInfo = "该项目不存在";
            return JSON;
        }

        FrameworkContract contract = null;
        BigDecimal oldGeneralContractAmount = BigDecimal.ZERO;
        if (null != id && id > 0l) {
            contract = FrameworkContractQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();
            oldGeneralContractAmount = contract.getGeneralContractAmount();
        }

        if (contract == null) {
            contract = new FrameworkContract();
            //初始金额
            contract.setFirstGeneralContractAmount(getGeneralContractAmount());
        }

        init(contract);

        //只允许修改一次
        if (!contract.isNew()) {
            contract.setGeneralContractAmount(oldGeneralContractAmount);
        }
        contract.setContractCategory(ContractCategory.FRAMEWORK_PROJECT_CONTRACT);
        contract.setProject(project);
        contract.log(getCurrentPerson(), new Date());
        contract.setGrantedScope(project.getResponsibleDivision());
        projApplication.addFrameworkContract(contract);
        result = true;
        return JSON;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public boolean isResult() {
        return result;
    }
}
