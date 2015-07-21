#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;

/**
 * 工程专业，如管线、光缆和设备专业等。
 * 
 * @author yyang
 * 
 */
@Entity
@Table(name = "specialties")
public class Specialty extends AbstractCommonEntity implements Comparable<Specialty> {
	private static final long serialVersionUID = 6466715310961363182L;

    public static final String THE_SPECIALTY_NAME_IS_EXIST = "THE_SPECIALTY_NAME_IS_EXIST";

	private String name;

	@Column(name = "sort_order")
	private int sortOrder;

	private String remark;

	public Specialty() {
		super();
	}

	public Specialty(String name) {
		super();
		this.name = name;
	}

	/**
	 * 根据专业的ID得到一批专业
	 * 
	 * @param specialtyIds
	 * @return
	 */
	public static Set<Specialty> getSpecialtiesFromId(Long[] specialtyIds) {
		Set<Specialty> results = new HashSet<Specialty>();
		if (null == specialtyIds) {
			return results;
		}
		for (long id : specialtyIds) {
			Specialty specialty = Specialty.get(id);
			if (null == specialty) {
				continue;
			}
			results.add(specialty);
		}
		return results;
	}

    /**
     * 根据专业的ID得到一批专业
     *
     * @param specialtyIds
     * @return
     */
    public static Set<Specialty> getSpecialtiesFromId(List<Long> specialtyIds) {
        if(specialtyIds == null){
            return new HashSet<Specialty>();
        }
        return getSpecialtiesFromId(specialtyIds.toArray(new Long[specialtyIds.size()-1]));
    }

	public static List<Specialty> findAll() {
		return getRepository().find(QuerySettings.create(Specialty.class).asc("sortOrder"));
	}

	public static List<Specialty> findEnabledAll() {
		return getRepository().find(QuerySettings.create(Specialty.class).eq("disabled", false).asc("sortOrder"));
	}

	public static boolean isSpecialtyNameExist(String specialtyName) {
		for (Specialty specialty : Specialty.findAll()) {
			if (specialtyName.equals(specialty.getName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSpecialtySortOrderExist(int sortOrder) {
		for (Specialty existSpecialty : Specialty.findAll()) {
			if (sortOrder == existSpecialty.getSortOrder()) {
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static Specialty get(Long id) {
		return AbstractEntity.get(Specialty.class, id);
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Specialty))
			return false;
		Specialty castOther = (Specialty) other;
		return new EqualsBuilder().append(name, castOther.name).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).toHashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	public int compareTo(final Specialty other) {
		return new CompareToBuilder().append(sortOrder, other.sortOrder).toComparison();
	}

}
