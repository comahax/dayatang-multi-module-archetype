#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.license;

import ${package}.domain.InternalOrganization;
import ${package}.domain.License;
import ${package}.webapp.action.BaseAction;

/**
 * User: tune Date: 13-6-4 Time: 下午2:48
 */
public class RemoveAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8513932010745178787L;
	private Long id = 0l;

	@Override
	public String execute() throws Exception {
		License license = License.get(License.class, id);
		if (license == null) {
			errorInfo = "删除失败";
			return JSON;
		}

		// 检查当前用户所属的机构是不是被删对象的机构的下属机构，如果是，则无权删除上属机构的内容
		InternalOrganization organization = license.getOrganization();
		boolean descendantOf = organization.isDescendantOf(getGrantedScope());
		if (!descendantOf) {
			projApplication.removeEntity(license);
		}

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
