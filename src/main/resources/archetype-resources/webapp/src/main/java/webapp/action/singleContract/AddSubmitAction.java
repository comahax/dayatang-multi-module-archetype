#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.ContractCategory;
import ${package}.domain.Project;
import ${package}.domain.SingleContract;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.contract.AddBaseAction;

import java.util.Date;

/**
 * 添加单项合同
 * User: zjzhai
 * Date: 13-4-28
 * Time: 下午4:36
 */
public class AddSubmitAction extends AddBaseAction {

	private static final long serialVersionUID = -4419644927405908171L;


	/**
     * 项目ID
     */
    private Long projectId = 0l;


    /**
     * 单项合同的ID
     */
    private long id = 0l;

    private Long result = 0l;


    @Override
    public String execute() throws Exception {

        Project project = getProjectOf(projectId);

        if (null == project) {
            errorInfo = "该项目不存在";
            return JSON;
        }


        SingleContract contract = new SingleContract();
        if ( id > 0l) {
            contract = SingleContractQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();
        }
        if (null == contract) {
            contract = new SingleContract();
        }

        contract.setContractCategory(ContractCategory.SINGLE_PROJECT_CONTRACT);
        init(contract);
        contract.setProject(project);
        contract.log(getCurrentPerson(), new Date());
        contract.setGrantedScope(project.getResponsibleDivision());

        projApplication.saveEntity(contract);

        result = contract.getId();

        return JSON;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getResult() {
        return result;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
