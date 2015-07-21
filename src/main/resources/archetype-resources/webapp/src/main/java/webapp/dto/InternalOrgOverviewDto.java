#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.InternalOrganization;
import ${package}.domain.OutputValue;
import ${package}.domain.Receipt;
import ${package}.domain.ReceiptInvoice;
import ${package}.query.OutputValueQuery;
import ${package}.query.ReceiptInvoiceQuery;
import ${package}.webapp.action.BaseAction;
import org.apache.struts2.json.annotations.JSON;

import java.math.BigDecimal;
import java.util.*;

/**
 * 机构的概况的DTO
 * User: zjzhai
 * Date: 13-5-7
 * Time: 上午3:05
 */
public class InternalOrgOverviewDto {

    /**
     * 机构的ID
     */
    private long id;

    /**
     * 机构名
     */
    private String name;

    /**
     * 机构
     */
    private InternalOrganizationDto organization;

    /**
     * 产值汇报的起始时间
     */
    private Date outputvalueStart;

    /**
     * 产值汇报的结束时间
     */

    private Date outputvalueEnd;

    /**
     * 累计产值
     */
    private BigDecimal outputvalue;

    /**
     * 总包发票的起始时间
     */
    private Date receiptInvoiceStart;

    /**
     * 总包发票的结束时间
     */
    private Date receiptInvoiceEnd;

    /**
     * 总包发票累计金额
     */
    private BigDecimal receiptIncoice;


    /**
     * 累计回款
     */
    private BigDecimal receipt;

    public InternalOrgOverviewDto(InternalOrganization organization, BaseAction action) {
        if (null == organization) {
            return;
        }
        id = organization.getId();
        name = organization.getName();
        this.organization = new InternalOrganizationDto(organization, action);
        outputvalueStart = computeOutputvalueStart(organization);
        outputvalueEnd = computeOutputvalueEnd(organization);
        outputvalue = OutputValue.totalOutputValueOf(organization);

        receiptInvoiceStart = computeReceiptInvoiceStart(organization);
        receiptInvoiceEnd = computeReceiptInvoiceEnd(organization);
        receiptIncoice = ReceiptInvoice.getTotalInvoiceAmountOf(organization);
        receipt = Receipt.getTotalReceiptAmountOf(organization);

    }

    public static List<InternalOrgOverviewDto> createBy(Collection<InternalOrganization> orgs, BaseAction action) {
        if (null == orgs) {
            return new ArrayList<InternalOrgOverviewDto>();
        }

        List<InternalOrgOverviewDto> results = new ArrayList<InternalOrgOverviewDto>();

        for (InternalOrganization each : orgs) {
            results.add(new InternalOrgOverviewDto(each,action));
        }

        return results;
    }

    private Date computeReceiptInvoiceStart(InternalOrganization organization){
        ReceiptInvoice receiptInvoice=  ReceiptInvoiceQuery.grantedScopeIn(organization).ascBillingDate().getSingleResult();
        return null == receiptInvoice ? null : receiptInvoice.getBillingDate();
    }
    private Date computeReceiptInvoiceEnd(InternalOrganization organization){
        ReceiptInvoice receiptInvoice=  ReceiptInvoiceQuery.grantedScopeIn(organization).descBillingDate().getSingleResult();
        return null == receiptInvoice ? null : receiptInvoice.getBillingDate();
    }

    private Date computeOutputvalueStart(InternalOrganization organization) {
        OutputValue ov = OutputValueQuery.immediateScopeOf(organization).ascMonthly().getSingleResult();
        if (null == ov) {
            return null;
        }
        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.YEAR,ov.getYear());
        calendar.set(Calendar.MONTH, ov.getMonth() -1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    private Date computeOutputvalueEnd(InternalOrganization organization) {
        OutputValue ov = OutputValueQuery.immediateScopeOf(organization).descMonthly().getSingleResult();
        if (null == ov) {
            return null;
        }
        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.YEAR,ov.getYear());
        calendar.set(Calendar.MONTH, ov.getMonth() -1);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public InternalOrganizationDto getOrganization() {
        return organization;
    }

    @JSON(format = "yyyy-MM")
    public Date getOutputvalueStart() {
        return outputvalueStart;
    }
    @JSON(format = "yyyy-MM")
    public Date getOutputvalueEnd() {
        return outputvalueEnd;
    }

    public BigDecimal getOutputvalue() {
        return outputvalue;
    }
    @JSON(format = "yyyy-MM")
    public Date getReceiptInvoiceStart() {
        return receiptInvoiceStart;
    }
    @JSON(format = "yyyy-MM")
    public Date getReceiptInvoiceEnd() {
        return receiptInvoiceEnd;
    }

    public BigDecimal getReceiptIncoice() {
        return receiptIncoice;
    }

    public BigDecimal getReceipt() {
        return receipt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
