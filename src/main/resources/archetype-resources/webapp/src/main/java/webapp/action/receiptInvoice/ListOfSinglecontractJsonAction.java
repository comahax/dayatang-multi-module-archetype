#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receiptInvoice;

import ${package}.domain.ReceiptInvoice;
import ${package}.domain.SingleContract;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ReceiptInvoiceDto;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-5-4
 * Time: 下午11:00
 */
public class ListOfSinglecontractJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 单项合同的ID
     */
    private Long id = 0l;

    private List<ReceiptInvoiceDto> results;


    @Override
    public String execute() throws Exception {

        SingleContract contract = SingleContractQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();

        if (null == contract) {
            return JSON;
        }

        results = ReceiptInvoiceDto.createBy(ReceiptInvoice.findBy(contract));

        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<ReceiptInvoiceDto> getResults() {
        return results;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
