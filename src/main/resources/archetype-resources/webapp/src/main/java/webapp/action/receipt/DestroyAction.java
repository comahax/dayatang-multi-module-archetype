#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receipt;

import ${package}.domain.Receipt;
import ${package}.query.ReceiptQuery;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai Date: 13-5-5 Time: 上午8:01
 */
public class DestroyAction extends BaseAction {

	private static final long serialVersionUID = 6349374402186523327L;
	/**
	 * 收款的ID
	 */
	private Long id = 0l;

	@Override
	public String execute() throws Exception {
		
		if (null == id || id <= 0l) {
			errorInfo = "找不到该收款！";
			return JSON;
		}

		
		Receipt receipt = ReceiptQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();
		if (null == receipt) {
			errorInfo = "找不到该收款！";
			return JSON;
		}

		projApplication.removeEntity(receipt);

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
