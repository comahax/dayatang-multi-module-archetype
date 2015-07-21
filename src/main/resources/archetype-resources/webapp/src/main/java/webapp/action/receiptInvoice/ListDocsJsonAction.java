#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receiptInvoice;

import java.util.List;

import ${package}.domain.ReceiptInvoice;
import ${package}.query.DocumentQuery;
import ${package}.query.ReceiptInvoiceQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.DocumentDto;
import ${package}.webapp.utils.DocumentTagGenerater;

/**
 * 列出所有收款发票的附件
 * User: zjzhai
 * Date: 13-5-4
 * Time: 下午11:16
 */
public class ListDocsJsonAction extends BaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = -4674760392377284047L;

	/**
     * 发票的ID
     */
    private Long id = 0l;

    private List<DocumentDto> results;

    @Override
    public String execute() throws Exception {
        ReceiptInvoice invoice =  ReceiptInvoiceQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();
        if (null == invoice) {
            return JSON;
        }

        results = DocumentDto.createBy(DocumentQuery.findByTags(
                new DocumentTagGenerater().receiptInvoice(id).generate()));
        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<DocumentDto> getResults() {
        return results;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
