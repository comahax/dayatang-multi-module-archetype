#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import ${package}.domain.SupervisorOrganization;

/**
 * User: zjzhai
 * Date: 13-5-1
 * Time: 下午3:04
 */
public class SupervisorOrganizationQuery extends BaseQuery<SupervisorOrganization> {
    public static SupervisorOrganizationQuery create(){
        return new SupervisorOrganizationQuery(QuerySettings.create(SupervisorOrganization.class));
    }

    private SupervisorOrganizationQuery(QuerySettings<SupervisorOrganization> querySettings) {
        super(querySettings);
    }

    public SupervisorOrganizationQuery nameEq(String organizationName) {
        querySettings.eq("name", organizationName);
        return this;
    }

    public SupervisorOrganizationQuery nameLike(String organizationNamePiece) {
        querySettings.containsText("name", organizationNamePiece);
        return this;
    }

}
