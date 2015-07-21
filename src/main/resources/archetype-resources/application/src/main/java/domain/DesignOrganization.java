#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.QuerySettings;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

/**
 * 设计单位
 * 
 * @author zjzhai
 * 
 */
@Entity
@DiscriminatorValue("DESIGN_ORGANIZATION")
public class DesignOrganization extends Organization {

	private static final long serialVersionUID = -1533384339375347213L;
	
	public static DesignOrganization get(long organizationId) {
		return get(DesignOrganization.class, organizationId);
	}

	/**
	 * 找到所有有效的单位
	 * 
	 * @return
	 */
	public static List<DesignOrganization> findAllEnabled() {
		boolean disabled = false;
		return findAll(disabled);
	}

	public static List<DesignOrganization> findAllDisabled() {
		boolean disabled = true;
		return findAll(disabled);
	}

    public static DesignOrganization getByName(String name){
        return getRepository().getSingleResult(QuerySettings.create(DesignOrganization.class).eq("name", name));
    }

	public static List<DesignOrganization> findAll(boolean disabled) {
		QuerySettings<DesignOrganization> settings = QuerySettings.create(DesignOrganization.class).eq("disabled", disabled);
		return getRepository().find(settings);
	}
}
