#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.frameworkContract;

import ${package}.domain.FrameworkContract;
import ${package}.domain.SingleContract;
import ${package}.pager.PageList;
import ${package}.query.FrameworkContractQuery;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SingleContractDto;

import java.util.List;

/**
 * 已关联单项合同
 * User: tune
 * Date: 13-6-17
 * Time: 上午10:12
 */
public class ListJsonOfRelevanceSingleAction extends BaseAction {

	private static final long serialVersionUID = 723636416233862313L;

	/**
     * 框架合同ID
     */
    private Long frameworkId = 0L;

    private List<SingleContractDto> results;

    private Long total = 0l;


    @Override
    public String execute() throws Exception {
    	FrameworkContract frameworkContract = FrameworkContractQuery.grantedScopeIn(getGrantedScope()).id(frameworkId).getSingleResult();
    	if(null == frameworkContract){
    		return JSON;
    	}
    	
        SingleContractQuery query = SingleContractQuery.grantedScopeIn(getGrantedScope()).frameworkContract(frameworkContract);
        PageList<SingleContract> pageList = createPageList(query);
        if (pageList != null) {
            results = SingleContractDto.createSingleContractDtosBy(pageList.getData());
            total = pageList.getTotal();
        }
        return JSON;
    }


    public void setFrameworkId(Long frameworkId) {
        this.frameworkId = frameworkId;
    }

    public Long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<SingleContractDto> getResults() {
        return results;
    }
}
