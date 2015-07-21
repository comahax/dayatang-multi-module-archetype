#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.dayatang.domain.QuerySettings;

/**
 * 合作单位
 * 
 * @author zjzhai
 * 
 */

@Entity
@DiscriminatorValue("COOPERATION_ORGANIZATION")
public class CooperationOrganization extends Organization {
	private static final long serialVersionUID = 1L;

	public CooperationOrganization() {

	}

	public CooperationOrganization(String name) {
		setName(name);
	}

	public static CooperationOrganization get(long organizationId) {
		return get(CooperationOrganization.class, organizationId);
	}

	/**
	 * 找到所有有效的单位
	 * 
	 * @return
	 */
	public static List<CooperationOrganization> findAllEnabled() {
		boolean disabled = false;
		return findAll(disabled);
	}

	public static List<CooperationOrganization> findAllDisabled() {
		boolean disabled = true;
		return findAll(disabled);
	}

	public static List<CooperationOrganization> findAll(boolean disabled) {
		QuerySettings<CooperationOrganization> settings = QuerySettings.create(CooperationOrganization.class).eq("disabled",
				disabled);
		return getRepository().find(settings);
	}

}
