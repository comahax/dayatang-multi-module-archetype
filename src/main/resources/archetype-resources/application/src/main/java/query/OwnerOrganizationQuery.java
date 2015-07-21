#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import ${package}.domain.OwnerOrganization;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-5-1
 * Time: 下午2:31
 */
public class OwnerOrganizationQuery extends BaseQuery<OwnerOrganization> {

    public static OwnerOrganizationQuery create(){
        return new OwnerOrganizationQuery(QuerySettings.create(OwnerOrganization.class));
    }

    private OwnerOrganizationQuery(QuerySettings<OwnerOrganization> querySettings) {
        super(querySettings);
    }

    public OwnerOrganizationQuery nameEq(String organizationName) {
        querySettings.eq("name", organizationName);
        return this;
    }

    public OwnerOrganizationQuery nameLike(String organizationNamePiece) {
        querySettings.containsText("name", organizationNamePiece);
        return this;
    }

    /**
     * 得到所有的发包方
     * @return
     */
    public static List<OwnerOrganization> findAllPartyawardings() {
        return OwnerOrganizationQuery.create().nullOwnerCategory().list();
    }

    public OwnerOrganizationQuery nullOwnerCategory() {

        querySettings.isNull("ownerCategory");

        return this;
    }

    public OwnerOrganizationQuery ownerCategory(String category) {
        querySettings.eq("ownerCategory",category);
        return this;
    }
}
