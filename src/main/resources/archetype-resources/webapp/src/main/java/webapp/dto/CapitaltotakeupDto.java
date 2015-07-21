#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.commons.BigDecimalUtils;
import ${package}.domain.Capitaltotakeup;
import org.apache.struts2.json.annotations.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目的资金占用
 * User: zjzhai
 * Date: 13-6-20
 * Time: 下午3:22
 */
public class CapitaltotakeupDto {


    private Date createTime;



    /**
     * 成本说明
     */
    private String costCaption;


    /**
     * 时间点
     */
    private Date startDate;


    /**
     * 预计占用资金
     */
    private java.math.BigDecimal expectedFunds = BigDecimal.ZERO;


    /**
     * 占用时间（年）
     */
    private double yearCount = 0;

    /**
     * 利率
     */
    private BigDecimal interestRate = BigDecimal.ZERO;

    /**
     * 占用资金成本
     */
    private BigDecimal costFunds;

    private String remark;

    public CapitaltotakeupDto(Capitaltotakeup capitaltotakeup) {
       
        
        costCaption = capitaltotakeup.getCostCaption();
        startDate = capitaltotakeup.getStartDate();
        expectedFunds = capitaltotakeup.getExpectedFunds();
        yearCount = capitaltotakeup.getYearCount();
        interestRate = capitaltotakeup.getInterestRate();
        costFunds = capitaltotakeup.getCostFunds();
        remark = capitaltotakeup.getRemark();

    }

    /**
     * 万元版的DTO
     *
     * @return
     */
    public static CapitaltotakeupDto thenThousandEditOf(Capitaltotakeup capitaltotakeup) {
        CapitaltotakeupDto result = new CapitaltotakeupDto(capitaltotakeup);
        result.expectedFunds = BigDecimalUtils.convertYuanToTenThousand(result.getExpectedFunds());
        result.costFunds = BigDecimalUtils.convertYuanToTenThousand(result.getCostFunds());
        return result;
    }

    public static List<CapitaltotakeupDto> createCapitaltotakeupDtosBy(List<Capitaltotakeup> datas) {
        List<CapitaltotakeupDto> results = new ArrayList<CapitaltotakeupDto>();
        for(Capitaltotakeup each : datas){
            results.add(new CapitaltotakeupDto(each));
        }
        return results;
    }

    public static List<CapitaltotakeupDto> createThousandOfCapitaltotakeupDtosBy(List<Capitaltotakeup> datas) {
        List<CapitaltotakeupDto> results = new ArrayList<CapitaltotakeupDto>();

        for(Capitaltotakeup each : datas){
            results.add(thenThousandEditOf(each));
        }
        return results;
    }



    public String getCostCaption() {
        return costCaption;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getCreateTime() {
        return createTime;
    }

    public BigDecimal getExpectedFunds() {
        return expectedFunds;
    }

    public double getYearCount() {
        return yearCount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public BigDecimal getCostFunds() {
        return costFunds;
    }

    public String getRemark() {
        return remark;
    }
}
