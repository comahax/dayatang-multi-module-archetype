#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.designOrg;

import java.util.ArrayList;
import java.util.List;

import ${package}.domain.DesignOrganization;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.DesignOrganizationDto;

/**
 * 设计单位的ID Name数据
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午3:28
 */

public class IdNameJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7130419055403316675L;
	private List<DesignOrganizationDto>  results;


    @Override
    public String execute() throws Exception {

        results = DesignOrganizationDto.designOrganizationDtoList(DesignOrganization.findAllEnabled());

        return JSON;
    }

    public List<DesignOrganizationDto> getResults() {
        if (null == results) {
            return new ArrayList<DesignOrganizationDto>();
        }
        return results;
    }
}
