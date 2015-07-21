#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Invoice;
import org.apache.struts2.json.annotations.JSON;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: zjzhai
 * Date: 13-5-3
 * Time: 下午10:08
 */
public class InvoiceDto {

    private Long id;


    /**
     * 开票日期
     */

    private Date billingDate;

    /**
     * 发票编号
     */

    private String serialNumber;

    /**
     * 发票金额
     */
    private BigDecimal amount = BigDecimal.ZERO;


    public InvoiceDto(Invoice invoice) {

        if (null == invoice) {
            return;
        }

        id = invoice.getId();
        billingDate = invoice.getBillingDate();
        serialNumber = invoice.getSerialNumber();
        amount = invoice.getAmount();
    }

    public Long getId() {
        return id;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getBillingDate() {
        return billingDate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
