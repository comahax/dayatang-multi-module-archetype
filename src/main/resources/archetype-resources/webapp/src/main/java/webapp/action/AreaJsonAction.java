#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

import java.util.List;

import ${package}.domain.Area;
import ${package}.webapp.dto.AreaDto;

/**
 * 得到区域的json数据
 * 
 * @author zjzhai
 * 
 */
public class AreaJsonAction extends BaseAction {

	private static final long serialVersionUID = -4795292171975700820L;

	private long parentId = 0;

	private List<AreaDto> results;

	public String execute() throws Exception {

		Area parent = Area.get(parentId);
		if (null == parent) {
			return WORKTABLE;
		}

		results = AreaDto.childrenOf(parent);

		return JSON;
	}

	public List<AreaDto> getResults() {
		return results;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

}
