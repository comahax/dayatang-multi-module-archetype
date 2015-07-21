#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.QuerySettings;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

/**
 * 监理单位
 * 
 * @author zjzhai
 * 
 */
@Entity
@DiscriminatorValue("SUPERVISOR_ORGANIZATION")
public class SupervisorOrganization extends Organization {

	private static final long serialVersionUID = -1182927034760573771L;
	
	public static SupervisorOrganization get(long organizationId) {
		return get(SupervisorOrganization.class, organizationId);
	}
	
	/**
	 * 找到所有有效的单位
	 * 
	 * @return
	 */
	public static List<SupervisorOrganization> findAllEnabled() {
		boolean disabled = false;
		return findAll(disabled);
	}

	public static List<SupervisorOrganization> findAllDisabled() {
		boolean disabled = true;
		return findAll(disabled);
	}

    public static SupervisorOrganization getByName(String name){
        return getRepository().getSingleResult(QuerySettings.create(SupervisorOrganization.class).eq("name",name));

    }

	public static List<SupervisorOrganization> findAll(boolean disabled) {
		QuerySettings<SupervisorOrganization> settings = QuerySettings.create(SupervisorOrganization.class).eq("disabled",
				disabled);
		return getRepository().find(settings);
	}
}
