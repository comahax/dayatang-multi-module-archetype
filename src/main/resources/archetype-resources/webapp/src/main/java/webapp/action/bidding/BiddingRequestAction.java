#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.bidding;

import java.util.List;

import ${package}.domain.OwnerOrganization;
import ${package}.webapp.action.BaseAction;

public class BiddingRequestAction extends BaseAction {

	private static final long serialVersionUID = 3862965113710642623L;

	private List<OwnerOrganization> owners;

	public String execute() throws Exception {
		owners = OwnerOrganization.findAllOwnerEnabled();
		if (owners == null || owners.isEmpty()) {
			return WORKTABLE;
		}
		return SUCCESS;
	}

	public List<OwnerOrganization> getOwners() {
		return owners;
	}

}
