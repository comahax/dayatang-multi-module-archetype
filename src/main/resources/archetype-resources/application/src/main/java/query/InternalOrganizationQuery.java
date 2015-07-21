#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GtCriterion;
import com.dayatang.domain.internal.LtCriterion;
import ${package}.domain.InternalOrganization;

public class InternalOrganizationQuery extends BaseQuery<InternalOrganization> {

    public InternalOrganizationQuery() {
        super(QuerySettings.create(InternalOrganization.class));
    }

    public InternalOrganizationQuery id(long id) {
        querySettings.eq("id", id);
        return this;
    }

    public static InternalOrganizationQuery create() {
        return new InternalOrganizationQuery();
    }

    /**
     * 查询 internalOrganization下所有的直属及间接子机构
     *
     * @param internalOrganization
     * @return
     */
    public InternalOrganizationQuery subordinateOf(InternalOrganization internalOrganization) {
        querySettings.and(new GtCriterion("leftValue", internalOrganization.getLeftValue()), new LtCriterion("rightValue",
                internalOrganization.getRightValue()));
        return this;
    }
    

    /**
     * 查询 internalOrganization下所有的直属子机构,不包括自身
     *
     * @param internalOrganization
     * @return
     */
    public InternalOrganizationQuery immediateChildrenOf(InternalOrganization internalOrganization) {
        querySettings.gt("leftValue", internalOrganization.getLeftValue());
        querySettings.lt("rightValue", internalOrganization.getRightValue());
        querySettings.eq("level", internalOrganization.getLevel() + 1);
        return this;
    }

    /**
     * 查询internalOrganization的直属父机构
     *
     * @param internalOrganization
     * @return
     */
    public InternalOrganizationQuery parentOf(InternalOrganization internalOrganization) {
        querySettings.lt("leftValue", internalOrganization.getLeftValue());
        querySettings.gt("rightValue", internalOrganization.getRightValue());
        querySettings.eq("level", internalOrganization.getLevel() - 1);
        return this;
    }

    /**
     * 是否用户所在的访问范围是否可以访问目标机构
     *
     * @param scope
     * @param targetInternalOrgId
     * @return
     */
    public static InternalOrganization abilitiToAccess(InternalOrganization scope, long targetInternalOrgId) {
        return abilitiToAccessIf(scope, targetInternalOrgId, false);
    }

    /**
     * 可以访问的机构，包括已撤销的机构
     *
     * @param scope
     * @param targetInternalOrgId
     * @return
     */
    public static InternalOrganization abilitiToAccessContainsDisabled(InternalOrganization scope, long targetInternalOrgId) {
        return abilitiToAccessIf(scope, targetInternalOrgId, null);
    }

    /**
     * 是否可访问该ID的机构
     *
     * @param scope
     * @param targetInternalOrgId
     * @param disabled            值为空时表示访问包括已撤销和未撤销的机构
     * @return
     */
    private static InternalOrganization abilitiToAccessIf(InternalOrganization scope, long targetInternalOrgId, Boolean disabled) {
        if (null == scope) {
            return null;
        }
        InternalOrganization targetInternalOrg = InternalOrganizationQuery.create().id(targetInternalOrgId).disabledIf(disabled)
                .getSingleResult();
        if (null == targetInternalOrg) {
            return null;
        }

        // scope的左值>目标机构的左值，scope的右值 < 目标机构的右值
        if (scope.getLeftValue() > targetInternalOrg.getLeftValue()
                && scope.getRightValue() < targetInternalOrg.getRightValue()) {
            return null;
        }
        return targetInternalOrg;
    }

}
