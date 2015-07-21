#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.QuerySettings;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业主单位/发包商
 * 
 * @author zjzhai
 * 
 */
@Entity
@DiscriminatorValue("OWNER_ORGANIZATION")
public class OwnerOrganization extends Organization {

	private static final long serialVersionUID = 1L;

	/**
	 * 业主单位类型,如广州移动属于移动 如果此字段没有值，那这个对象就是发包商，如果有值就是业主单位
	 * 
	 */
	@Column(name = "owner_category")
	private String ownerCategory;


	/**
	 * 找到所有有效的业主单位
	 * 
	 * @return
	 */
	public static List<OwnerOrganization> findAllOwnerEnabled() {
		boolean disabled = false;
		return findAllOwner(disabled);
	}

	public static List<OwnerOrganization> findAllOwnerDisabled() {
		boolean disabled = true;
		return findAllOwner(disabled);
	}

	/**
	 * 找到所有的业主单位
	 * 
	 * @param disabled
	 * @return
	 */
	public static List<OwnerOrganization> findAllOwner(Boolean disabled) {
		QuerySettings<OwnerOrganization> settings = QuerySettings.create(
				OwnerOrganization.class).notNull("ownerCategory");
		if (disabled != null) {
			settings.eq("disabled", disabled);
		}
		return getRepository().find(settings);
	}

    public static void changeOwnerType(String oldSerialNumber, String newSerialNumber) {
        String sql = "UPDATE OwnerOrganization o SET o.ownerCategory = :newType WHERE e.ownerCategory = :oldType";
        Map<String ,Object> params = new HashMap<String, Object>();
        params.put("newType", newSerialNumber);
        params.put("oldType", oldSerialNumber);
        getRepository().executeUpdate(sql,params);
    }

	public static OwnerOrganization get(long organizationId) {
		return OwnerOrganization.get(OwnerOrganization.class, organizationId);
	}

	public String getOwnerCategory() {
		return ownerCategory;
	}

	public void setOwnerCategory(String ownerCategory) {
		this.ownerCategory = ownerCategory;
	}


}
