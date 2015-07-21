#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.frameworkContract;

import ${package}.domain.FrameworkContract;
import ${package}.query.FrameworkContractQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 删除框架合同
 * User: tune
 * Date: 13-6-13
 * Time: 下午4:48
 */
public class RemoveAction extends BaseAction{

	private static final long serialVersionUID = 7221846133234666256L;
	private Long frameworkId = 0l;

 

    @Override
    public String execute() throws Exception {
    	
    	
    	if(frameworkId <= 0l){
    		return JSON;
    	}
    	
        FrameworkContract frameworkContract = FrameworkContractQuery.grantedScopeIn(getGrantedScope()).id(frameworkId).getSingleResult();

        if(frameworkContract == null){
            return JSON;
        }
        
        projApplication.removeEntity(frameworkContract);


        return JSON;
    }

    public void setFrameworkId(Long frameworkId) {
        this.frameworkId = frameworkId;
    }

    
}
