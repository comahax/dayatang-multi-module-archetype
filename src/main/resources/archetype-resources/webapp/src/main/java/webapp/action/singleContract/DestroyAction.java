#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.SingleContract;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 删除单项合同
 * User: zjzhai
 * Date: 13-4-28
 * Time: 下午4:20
 */
public class DestroyAction extends BaseAction {

	private static final long serialVersionUID = -4408695886772622339L;
	/**
     * 合同ID
     */
    private Long id = 0l;

    @Override
    public String execute() throws Exception {

        SingleContract contract = SingleContractQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();

        if (null == contract) {
            return JSON;
        }

        projApplication.removeEntity(contract);

        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
