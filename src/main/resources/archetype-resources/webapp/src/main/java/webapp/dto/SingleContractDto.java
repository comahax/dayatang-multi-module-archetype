#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Receipt;
import ${package}.domain.ReceiptInvoice;
import ${package}.domain.SingleContract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SingleContractDto extends ContractDto {


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

    /**
     * 单项合同所对应到的框架合同
     */
    private Long frameworkContractId;


    public SingleContractDto(SingleContract contract) {
        super(contract);
        if (null == contract) {
            return;
        }
        if (contract.getFrameworkContract() != null) {
            frameworkContractId = contract.getFrameworkContract().getId();
        }

        if (!contract.isNew()) {
            totalReceiptInvoiceAmount = ReceiptInvoice.getTotalInvoiceAmountOf(contract);

            totalReceiptAmount = Receipt.getTotalReceiptAmountOf(contract);

            receivableRatio = Receipt.getReceivableRatioOf(contract);

        }

    }


    public static List<SingleContractDto> createSingleContractDtosBy(Collection<SingleContract> singleContracts) {
        List<SingleContractDto> results = new ArrayList<SingleContractDto>();

        if (null == singleContracts) {
            return results;
        }

        for (SingleContract each : singleContracts) {
            results.add(new SingleContractDto(each));
        }

        return results;
    }

    public BigDecimal getTotalReceiptInvoiceAmount() {
        return totalReceiptInvoiceAmount;
    }

    public Long getFrameworkContractId() {
        return frameworkContractId;
    }

    public BigDecimal getTotalReceiptAmount() {
        return totalReceiptAmount;
    }

    public BigDecimal getReceivableRatio() {
        return receivableRatio;
    }
}
