#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.layout;

import ${package}.domain.RoleAssignment;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.RoleAssignmentDto;

import java.util.List;

/**
 * 系统布局的头
 * User: zjzhai
 * Date: 13-3-13
 * Time: 上午10:14
 */
public class NorthAction extends BaseAction {



    /**
	 * 
	 */
	private static final long serialVersionUID = -8862122255382994679L;

	public List<RoleAssignmentDto> getAssignments() {
        return RoleAssignmentDto.createBy(RoleAssignment.findByUser(getCurrentUser()), this);
    }




}
