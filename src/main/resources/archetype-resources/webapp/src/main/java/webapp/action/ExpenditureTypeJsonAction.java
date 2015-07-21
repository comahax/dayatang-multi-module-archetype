#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

import ${package}.webapp.dto.ExpenditureTypeDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 列出所有的成本类型
 * User: zjzhai
 * Date: 13-6-6
 * Time: 下午5:02
 */
public class ExpenditureTypeJsonAction extends BaseAction {


	private static final long serialVersionUID = 6511419198439282505L;
	private List<ExpenditureTypeDto> results = new ArrayList<ExpenditureTypeDto>();


    @Override
    public String execute() throws Exception {

        results = ExpenditureTypeDto.generate();

        return JSON;
    }

    public List<ExpenditureTypeDto> getResults() {
        return results;
    }
}
