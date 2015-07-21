#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.contract;

import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ContractCategoryDto;

import java.util.List;

/**
 * 合同类型的json数据
 * User: zjzhai
 * Date: 13-4-18
 * Time: 上午8:48
 */
public class CategoriesJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8907958503522106544L;
	private List<ContractCategoryDto> results;

    @Override
    public String execute() throws Exception {

        results = ContractCategoryDto.all(this);

        return JSON;
    }

    public List<ContractCategoryDto> getResults() {
        return results;
    }
}
