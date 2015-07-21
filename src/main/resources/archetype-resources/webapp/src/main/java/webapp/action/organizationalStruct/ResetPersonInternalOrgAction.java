#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.domain.Person;
import ${package}.query.InternalOrganizationQuery;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.BaseAction;

public class ResetPersonInternalOrgAction extends BaseAction {

	private static final long serialVersionUID = 6813778590767556461L;

	private Long personId = 0l;

	private Long internalId = 0l;

	public String execute() {

		//获得当前用户的授权访问机构范围
		InternalOrganization internalOrganization = InternalOrganizationQuery
				.abilitiToAccess(getGrantedScope(), internalId);

		if (null == internalOrganization) {
			return JSON;
		}

		Person person = PersonQuery.create().id(personId).getSingleResult();

		if (null == person) {
			errorInfo = "该人员不存在";
			return JSON;
		}

		person.setOrganization(internalOrganization);
		projApplication.saveEntity(person);

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public void setInternalId(Long internalId) {
		this.internalId = internalId;
	}

}
