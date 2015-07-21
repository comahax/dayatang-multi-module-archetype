#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.EqCriterion;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.query.InternalOrganizationQuery;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 内部机构
 * <p/>
 * 注意：内部机构的编码只有两位
 * 
 * @author zjzhai
 */
@Entity
@DiscriminatorValue("INTERNAL_ORGANIZATION")
public class InternalOrganization extends Organization {

	private static final long serialVersionUID = -5427962800284490098L;

	/**
	 * 内部机构树的根
	 */
	public static final long HEADQUARTERS_ID = 1l;

	/**
	 * 机构的分类
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "internal_category")
	private InternalOrganizationCategory internalCategory;

	@Transient
	private Set<InternalOrganization> children;

	@Transient
	private String fullName;

	public InternalOrganization() {
		super();
	}

	public InternalOrganization(String organizationName) {
		super(organizationName);
	}

	/**
	 * 得到整个组织机构树的根
	 * 
	 * @return
	 */
	public static InternalOrganization headquarter() {
		return InternalOrganization.get(HEADQUARTERS_ID);
	}

	/**
	 * 得到上级机构
	 * 
	 * @return
	 */
	public InternalOrganization getParent() {
		return InternalOrganizationQuery.create().parentOf(this).enabled().getSingleResult();
	}

	/**
	 * 得到直属子机构
	 * 
	 * @return
	 */
	public Set<InternalOrganization> getImmediateChildren() {
		return new HashSet<InternalOrganization>(InternalOrganizationQuery.create().immediateChildrenOf(this).enabled().list());
	}

	public Set<InternalOrganization> getAllImmediateChildren() {
		return new HashSet<InternalOrganization>(InternalOrganizationQuery.create().immediateChildrenOf(this).list());
	}

	/**
	 * 得所有直属与非直属子机构，包含自身
	 * 
	 * @return
	 */
	public Set<InternalOrganization> getSubordinateWithSelf() {
		Set<InternalOrganization> results = getSubordinate();
		results.add(this);
		return results;
	}

	/**
	 * 得所有直属与非直属子机构
	 * 
	 * @return
	 */
	public Set<InternalOrganization> getSubordinate() {
		Set<InternalOrganization> results = new HashSet<InternalOrganization>();
		results.addAll(InternalOrganizationQuery.create().subordinateOf(this).enabled().list());
		return results;
	}

	/**
	 * 得到所在公司
	 * 
	 * @return
	 */
	public InternalOrganization getCompany() {
		if (internalCategory == InternalOrganizationCategory.HEADQUARTERS || internalCategory == InternalOrganizationCategory.SUBSIDIARY) {
			return this;
		}
		InternalOrganization parent = getParent();
		if (parent == null) {
			return this;
		}
		return parent.getCompany();
	}

	public static InternalOrganization getByName(String name) {
		return getRepository().getSingleResult(QuerySettings.create(InternalOrganization.class).eq("name", name));
	}

	@Override
	public void disable() {
		for (InternalOrganization each : getImmediateChildren()) {
			each.disable();
			each.save();
		}
		super.disable();
		save();
	}

	@Override
	public void enable() {
		for (InternalOrganization each : getImmediateChildren()) {
			each.enable();
			each.save();
		}
		super.enable();
		save();
	}

	/**
	 * 彻底删除
	 */
	public void remove() {
		for (InternalOrganization each : getSubordinate()) {
			each.remove();
		}
		super.remove();
	}

	/**
	 * 得到所有的可作为合同方的机构,其实只包括子公司和总公司
	 * 
	 * @return
	 */
	public static List<InternalOrganization> getAllAbilityToBeParty() {
		List<InternalOrganization> results = new ArrayList<InternalOrganization>();
		results.addAll(InternalOrganizationQuery.create().immediateChildrenOf(InternalOrganization.headquarter()).list());
		results.add(InternalOrganization.headquarter());
		return results;
	}

	/**
	 * 添加下级机构
	 * 
	 * @param internalOrganization
	 * @return
	 */
	public InternalOrganization createChild(InternalOrganization internalOrganization) {
		int right = getRightValue() - 1;
		List<Organization> organizations = getRepository().find("select o from Organization o where o.rightValue > ?", new Object[] { right }, Organization.class);
		for (Organization each : organizations) {
			each.setRightValue(each.getRightValue() + 2);
			each.save();
		}
		organizations = getRepository().find("select o from Organization o where o.leftValue > ?", new Object[] { right }, Organization.class);
		for (Organization each : organizations) {
			each.setLeftValue(each.getLeftValue() + 2);
			each.save();
		}
		internalOrganization.setLeftValue(right + 1);
		internalOrganization.setRightValue(right + 2);
		internalOrganization.setLevel(getLevel() + 1);
		internalOrganization.save();
		return internalOrganization;
	}

	/**
	 * 找到所有的公司,包括总公司
	 * 
	 * @return
	 */
	public static List<InternalOrganization> findAllCompanies() {
		InternalOrganization header = headquarter();
		QuerySettings<InternalOrganization> querySettings = QuerySettings.create(InternalOrganization.class);
		querySettings.gt("leftValue", header.getLeftValue());
		querySettings.lt("rightValue", header.getRightValue());
		querySettings.eq("level", header.getLevel() + 1);
		List<InternalOrganization> results = new ArrayList<InternalOrganization>();
		results.add(header);
		results.addAll(getRepository().find(querySettings));
		return results;
	}

	public String getFullName() {
		if (internalCategory == InternalOrganizationCategory.HEADQUARTERS || internalCategory == InternalOrganizationCategory.SUBSIDIARY || null == getParent()) {
			return getName();
		}
		return getParent().getFullName() + "/" + getName();
	}

	/**
	 * 判断本机构是否参数organization的直接或间接上级机构
	 * 
	 * @param organization
	 * @return
	 */
	public boolean isAncestorOf(InternalOrganization organization) {
		return getSubordinateWithSelf().contains(organization);
	}

	/**
	 * 判断参数organization是不是本机构的直接或间接下属机构
	 * 
	 * @param organization
	 * @return
	 */
	public boolean isDescendantOf(InternalOrganization organization) {
		return organization.getSubordinateWithSelf().contains(this);
	}

	public static InternalOrganization get(long organizationId) {
		return InternalOrganization.get(InternalOrganization.class, organizationId);
	}

	/**
	 * 在某个机构下的,具有某个名字的机构
	 * 
	 * @param name
	 * @param parent
	 * @return
	 */
	public static InternalOrganization findByNameAndIn(String name, InternalOrganization parent) {
		return getRepository().getSingleResult(QuerySettings.create(InternalOrganization.class).and(new EqCriterion("name", name), new GeCriterion("leftValue", parent.getLeftValue()), new LeCriterion("rightValue", parent.getRightValue())));
	}

	/**
	 * 获得未撤销的机构树
	 * 
	 * @return
	 */
	public Organization getWholeEnabledOrganizationTree() {
		return assembledWholeEnabledOrganizationTree(this);
	}

	/**
	 * 获得所有的机构树
	 * 
	 * @return
	 */
	public Organization getWholeAllOrganizationTree() {
		return assembledWholeAllOrganizationTree(this);
	}

	/**
	 * 组装机构树
	 * 
	 * @param root
	 * @return
	 */
	private InternalOrganization assembledWholeEnabledOrganizationTree(InternalOrganization root) {
		root.setChildren(root.getImmediateChildren());
		if (root.isPappy()) {
			for (InternalOrganization child : root.getImmediateChildren()) {
				assembledWholeEnabledOrganizationTree(child);
			}
		}
		return root;
	}

	/**
	 * 组装有效机构的机构树
	 * 
	 * @param root
	 * @return
	 */
	private InternalOrganization assembledWholeAllOrganizationTree(InternalOrganization root) {
		root.setChildren(root.getAllImmediateChildren());
		if (root.isPappy()) {
			for (InternalOrganization child : root.getAllImmediateChildren()) {
				assembledWholeAllOrganizationTree(child);
			}
		}
		return root;
	}

	/**
	 * 判断自身是否有子机构
	 * 
	 * @return
	 */
	public boolean isPappy() {
		return (getImmediateChildren() != null && !getImmediateChildren().isEmpty());
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Organization)) {
			return false;
		}
		Organization that = (Organization) other;
		return new EqualsBuilder().append(getLeftValue(), that.getLeftValue()).append(getRightValue(), that.getRightValue()).append(getName(), that.getName()).append(getLevel(), that.getLevel()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getName()).append(getLeftValue()).append(getRightValue()).append(getLevel()).toHashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

	public InternalOrganizationCategory getInternalCategory() {
		return internalCategory;
	}

	public void setInternalCategory(InternalOrganizationCategory internalCategory) {
		this.internalCategory = internalCategory;
	}

	private void setChildren(Set<InternalOrganization> children) {
		this.children = children;
	}

}
