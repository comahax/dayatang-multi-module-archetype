#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.commons.BigDecimalUtils;
import ${package}.domain.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 项目DTO
 *
 * @author zjzhai
 */
public class ProjectDto extends ProjectElementDto {

    /**
     * 项目状态
     */
    private String status;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 预估收入
     */
    private BigDecimal estimatedIncome = BigDecimal.ZERO;

    /**
     * 毛利润
     */
    private BigDecimal grossProfit = BigDecimal.ZERO;

    /**
     * 毛利率
     */
    private BigDecimal grossMargin = BigDecimal.ZERO;

    private Set<Specialty> specialties;

    /**
     * 业主
     */
    private OrganizationInfoDto owner;


    /**
     * 是否可以向此项目合同
     * 是否可以向此项目添加单点
     */
    private boolean businessOperationsable = true;

    /**
     * 产值
     */
    private BigDecimal totalOutputvalue = BigDecimal.ZERO;


    /**
     * 产值已经分配出去的。
     */
    private BigDecimal outputvalueDistributiveShareTotal = BigDecimal.ZERO;

    /**
     * 应付款总和
     */
    private BigDecimal outputvaluePayableTotal = BigDecimal.ZERO;

    /**
     * 已完成产值
     */
    private BigDecimal outputvalueCompleted = BigDecimal.ZERO;


    /**
     * 产值完成比
     */
    private BigDecimal outputvalueCompletedRatio = BigDecimal.ZERO;


    /**
     * 回款率
     */
    private BigDecimal receivableRatio = BigDecimal.ZERO;

    /**
     * 收款总额
     */
    private BigDecimal totalReceiptAmount = BigDecimal.ZERO;

    /**
     * 总包发票的金额总和
     */
    private BigDecimal totalReceiptInvoiceAmount = BigDecimal.ZERO;


    public ProjectDto(Project project) {
        super(project);
        if (null == project) {
            return;
        }
        if (project.getStatus() != null) {
            status = project.getStatus().getCnText();
        }
        specialties = project.getSpecialties();
        projectType = Dictionary.getDictionaryTextBySerialNumBerAndCategory(project.getProjectType(),
                DictionaryCategory.PROJECT_TYPE);
        estimatedIncome = project.getEstimatedIncome();
        grossProfit = project.getGrossProfit();
        grossMargin = project.getGrossMargin();
        if (null != project.getOwnerInfo()) {
            owner = new OrganizationInfoDto(project.getOwnerInfo());
        }
        totalOutputvalue = project.getTotalOutputvalue();

        outputvalueDistributiveShareTotal = project.getOutputvalueDistributiveShareTotal();
        outputvaluePayableTotal = project.getOutputvaluePayableTotal();
        outputvalueCompleted = OutputValue.totalOutputValueOf(project);
        if (null == totalOutputvalue || BigDecimalUtils.eqZero(totalOutputvalue) ) {
            outputvalueCompletedRatio = new BigDecimal("100");
        } else {
            if (null == outputvalueCompleted  || BigDecimal.ZERO.equals(outputvalueCompleted) || BigDecimalUtils.eqZero(totalOutputvalue)) {
                outputvalueCompletedRatio = BigDecimal.ZERO;
            } else {
                outputvalueCompletedRatio = outputvalueCompleted.divide(totalOutputvalue, RoundingMode.CEILING).multiply(new BigDecimal(100));
            }
        }

        receivableRatio = Receipt.getReceivableRatioOf(project);
        totalReceiptAmount = Receipt.getTotalReceiptAmountOf(project);
        totalReceiptInvoiceAmount = ReceiptInvoice.getTotalInvoiceAmountOf(project);


        businessOperationsable = project.isBusinessOperationsable();
    }

    public static List<ProjectDto> createListBy(Collection<Project> projects) {
        if (null == projects) {
            return null;
        }
        List<ProjectDto> results = new ArrayList<ProjectDto>();
        for (Project each : projects) {
            if (null == each) {
                continue;
            }
            ProjectDto projectDto = new ProjectDto(each);

            results.add(projectDto);
        }
        return results;
    }

    public BigDecimal getOutputvalueCompleted() {
        return outputvalueCompleted;
    }

    public boolean isBusinessOperationsable() {
        return businessOperationsable;
    }

    public BigDecimal getOutputvalueCompletedRatio() {
        return outputvalueCompletedRatio;
    }

    public String getStatus() {
        return status;
    }

    public String getProjectType() {
        return projectType;
    }

    public BigDecimal getEstimatedIncome() {
        return estimatedIncome;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public BigDecimal getGrossMargin() {
        return grossMargin;
    }

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public OrganizationInfoDto getOwner() {
        return owner;
    }


    public BigDecimal getTotalOutputvalue() {
        return totalOutputvalue;
    }

    public BigDecimal getOutputvalueDistributiveShareTotal() {
        return outputvalueDistributiveShareTotal;
    }

    public BigDecimal getOutputvaluePayableTotal() {
        return outputvaluePayableTotal;
    }

    public BigDecimal getReceivableRatio() {
        return receivableRatio;
    }

    public BigDecimal getTotalReceiptAmount() {
        return totalReceiptAmount;
    }

    public BigDecimal getTotalReceiptInvoiceAmount() {
        return totalReceiptInvoiceAmount;
    }
}
