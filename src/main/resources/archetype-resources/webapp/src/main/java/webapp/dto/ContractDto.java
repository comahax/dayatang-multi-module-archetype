#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Contract;
import ${package}.domain.ContractCategory;
import ${package}.webapp.action.BaseAction;
import org.apache.struts2.json.annotations.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ContractDto {

    private Long id;

    /**
     * 关联到的项目
     */
    private ProjectDto project;

    /*
     * 合同编号
     */
    private String serialNumber;

    /*
     * 合同名称
     */
    private String contractName;

    /*
     * 总包合同金额
     */
    private BigDecimal generalContractAmount = BigDecimal.ZERO;

    /**
     * 合同类型
     */
    private ContractCategory contractCategory;


    /*
     * 签定日期
     */

    private Date signDate;

    /**
     * 开工日期
     */
    private Date startDate;

    /**
     * 完工日期
     */
    private Date finishDate;

    private String remark;

    private OrganizationInfoDto partAInfo;

    private OrganizationInfoDto partBInfo;

    private OrganizationInfoDto supervisorInfo;


    public static List<ContractDto> createBy(Collection<Contract> contracts, BaseAction action) {
        List<ContractDto> results = new ArrayList<ContractDto>();

        if (null == contracts) {
            return results;
        }

        for (Contract each : contracts) {
            results.add(new ContractDto(each));
        }

        return results;
    }

    public ContractDto(Contract contract) {
        id = contract.getId();
        project = new ProjectDto(contract.getProject());
        serialNumber = contract.getSerialNumber();
        contractName = contract.getContractName();
        generalContractAmount = contract.getGeneralContractAmount();
        contractCategory = contract.getContractCategory();
        signDate = contract.getSignDate();
        startDate = contract.getStartDate();
        finishDate = contract.getFinishDate();
        remark = contract.getRemark();
        if (contract.getPartA() != null) {
            partAInfo = new OrganizationInfoDto(contract.getPartA());
        }

        if (contract.getPartB() != null) {
            partBInfo = new OrganizationInfoDto(contract.getPartB());
        }

        if (contract.getSupervisor() != null) {
            supervisorInfo = new OrganizationInfoDto(contract.getSupervisor());
        }

    }

    public ProjectDto getProject() {
        return project;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getContractName() {
        return contractName;
    }

    public BigDecimal getGeneralContractAmount() {
        return generalContractAmount;
    }

    public ContractCategory getContractCategory() {
        return contractCategory;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getSignDate() {
        return signDate;
    }
    @JSON(format = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }
    @JSON(format = "yyyy-MM-dd")
    public Date getFinishDate() {
        return finishDate;
    }

    public String getRemark() {
        return remark;
    }

    public OrganizationInfoDto getPartAInfo() {
        return partAInfo;
    }

    public OrganizationInfoDto getPartBInfo() {
        return partBInfo;
    }

    public OrganizationInfoDto getSupervisorInfo() {
        return supervisorInfo;
    }
    public Long getId() {
        return id;
    }

}
