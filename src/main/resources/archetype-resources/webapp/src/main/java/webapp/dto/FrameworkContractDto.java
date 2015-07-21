#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.FrameworkContract;
import ${package}.domain.Receipt;
import ${package}.domain.ReceiptInvoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-17
 * Time: 下午11:03
 */
public class FrameworkContractDto extends ContractDto {

    /**
     * 收款发票的总额
     */
    private BigDecimal totalReceiptInvoiceAmount = BigDecimal.ZERO;


    /**
     * 收款总额
     */
    private BigDecimal totalReceiptAmount = BigDecimal.ZERO;

    /**
     * 回款率
     */
    private BigDecimal receivableRatio = BigDecimal.ZERO;

    public FrameworkContractDto(FrameworkContract contract) {
        super(contract);
        if (!contract.isNew()) {
            totalReceiptInvoiceAmount = ReceiptInvoice.getFrameworkContractInvoiceOf(contract);

            totalReceiptAmount = Receipt.getTotalReceiptAmountOf(contract);

            receivableRatio = Receipt.getFrameworkContractReceivableRatioOf(totalReceiptAmount,totalReceiptInvoiceAmount);
        }
    }

    public static List<FrameworkContractDto> createFrameworkContractDtosBy(List<FrameworkContract> datas) {
        List<FrameworkContractDto>  results = new ArrayList<FrameworkContractDto>();

        for(FrameworkContract each : datas){
            results.add(new FrameworkContractDto(each));
        }
        return results;
    }

    public BigDecimal getTotalReceiptInvoiceAmount() {
        return totalReceiptInvoiceAmount;
    }

    public BigDecimal getTotalReceiptAmount() {
        return totalReceiptAmount;
    }

    public BigDecimal getReceivableRatio() {
        return receivableRatio;
    }
}
