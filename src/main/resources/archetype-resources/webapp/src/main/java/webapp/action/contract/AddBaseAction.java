#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.contract;

import ${package}.domain.*;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.BaseAction;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: zjzhai
 * Date: 13-4-17
 * Time: 下午10:17
 */
public class AddBaseAction extends BaseAction {
	private static final long serialVersionUID = -4825548949692367365L;

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


    //甲乙方
    private Long partAOrgId = 0l;

    private Long partAContractId = 0l;

    private Long partBOrgId = 0l;

    private Long partBContractId = 0l;


    //监理单位
    private Long supervisorOrgId = 0l;
    private Long supervisorPersonId = 0l;

    protected void init(Contract contract) {
        if (null == contract) {
            return;
        }

        contract.setSerialNumber(serialNumber);

        contract.setContractName(contractName);

        contract.setGeneralContractAmount(generalContractAmount);

        contract.setStartDate(startDate);

        contract.setSignDate(signDate);

        contract.setFinishDate(finishDate);

        contract.setRemark(remark);

        if (partAOrgId != null && partAOrgId > 0l) {
            Organization parta = Organization.get(partAOrgId);
            if (parta != null) {
                Person partAcontact = null;
                if (partAContractId != null && partAContractId > 0) {
                    partAcontact = PersonQuery.create().organization(parta).id(partAContractId).getSingleResult();
                }
                contract.setPartA(new OrganizationInfo(parta, partAcontact));
            }
        }

        if (partBOrgId != null && partBOrgId >0l) {
            Organization partb = Organization.get(partBOrgId);
            if (partb != null) {
                Person partBcontact = null;
                if (partBContractId != null) {
                    partBcontact = PersonQuery.create().organization(partb).id(partBContractId).getSingleResult();
                }
                contract.setPartB(new OrganizationInfo(partb, partBcontact));
            }
        }

        if (supervisorOrgId != null&& supervisorOrgId > 0l) {
            Organization supervisorOrg = Organization.get(supervisorOrgId);
            if (supervisorOrg != null) {
                Person supervisorPerson = null;
                if (supervisorPersonId != null && supervisorPersonId > 0l) {
                    supervisorPerson = Person.get(supervisorPersonId);
                }
                contract.setSupervisor(new OrganizationInfo(supervisorOrg, supervisorPerson));
            }
        }


    }


    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public BigDecimal getGeneralContractAmount() {
        return generalContractAmount;
    }

    public void setGeneralContractAmount(BigDecimal generalContractAmount) {
        this.generalContractAmount = generalContractAmount;
    }

    public ContractCategory getContractCategory() {
        return contractCategory;
    }

    public void setContractCategory(ContractCategory contractCategory) {
        this.contractCategory = contractCategory;
    }


    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getPartAOrgId() {
        return partAOrgId;
    }

    public void setPartAOrgId(Long partAOrgId) {
        this.partAOrgId = partAOrgId;
    }

    public Long getPartAContractId() {
        return partAContractId;
    }

    public void setPartAContractId(Long partAContractId) {
        this.partAContractId = partAContractId;
    }

    public Long getPartBOrgId() {
        return partBOrgId;
    }

    public void setPartBOrgId(Long partBOrgId) {
        this.partBOrgId = partBOrgId;
    }

    public Long getPartBContractId() {
        return partBContractId;
    }

    public void setPartBContractId(Long partBContractId) {
        this.partBContractId = partBContractId;
    }

    public Long getSupervisorOrgId() {
        return supervisorOrgId;
    }

    public void setSupervisorOrgId(Long supervisorOrgId) {
        this.supervisorOrgId = supervisorOrgId;
    }

    public Long getSupervisorPersonId() {
        return supervisorPersonId;
    }

    public void setSupervisorPersonId(Long supervisorPersonId) {
        this.supervisorPersonId = supervisorPersonId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
