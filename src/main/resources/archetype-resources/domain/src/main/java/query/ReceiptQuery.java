#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Receipt;
import ${package}.domain.ReceiptInvoice;
import com.dayatang.utils.Assert;

public class ReceiptQuery extends BaseQuery<Receipt> {

    private ReceiptQuery() {
        super(QuerySettings.create(Receipt.class));
    }

    /**
     * 负责机构在这些机构内的合同
     */
    public static ReceiptQuery grantedScopeIn(InternalOrganization scope) {
        Assert.notNull(scope);
        ReceiptQuery query = new ReceiptQuery();
        query.querySettings.and(new GeCriterion("contract.grantedScope.leftValue", scope.getLeftValue()), new LeCriterion(
                "contract.grantedScope.rightValue", scope.getRightValue()));
        return query;
    }


    public ReceiptQuery id(long id) {
        querySettings.eq("id", id);
        return this;
    }

}
