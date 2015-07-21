#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receiptInvoice;

import ${package}.domain.Document;
import ${package}.domain.ReceiptInvoice;
import ${package}.query.ReceiptInvoiceQuery;
import ${package}.webapp.action.document.DownloadBaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * User: zjzhai
 * Date: 13-5-4
 * Time: 下午11:34
 */
public class DownloadDocAction extends DownloadBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1640586048173582187L;
	private Long invoiceId;

    @Override
    public String execute() {
        if (!SUCCESS.equals(super.execute())) {
            return NOT_FOUND;
        }

        ReceiptInvoice invoice = ReceiptInvoiceQuery.grantedScopeIn(getGrantedScope()).id(invoiceId).getSingleResult();
        if (null == invoice) {
            return NOT_FOUND;
        }

        Document document = getDoc();

        if (!document.containOneTag(new DocumentTagGenerater().receiptInvoice(invoiceId).getSingleResult())) {
            return NOT_FOUND;
        }

        return SUCCESS;
    }


    /**
     * 得到内容类型
     *
     * @return
     */
    public String getContentType() {
        return super.getContentTypeDefaultImpl();
    }


    public InputStream getInputStream() {
        InputStream result = null;
        try {
            result = super.getInputStreamDefaultImpl();
        } catch (FileNotFoundException e) {
            // TODO
        }

        return result;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setDocId(Long docId) {
        super.setDocId(docId);
    }
}
