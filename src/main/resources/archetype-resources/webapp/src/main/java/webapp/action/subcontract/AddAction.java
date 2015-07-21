#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontract;

import java.math.BigDecimal;
import java.util.Date;

import ${package}.domain.ContractCategory;
import ${package}.domain.CooperationOrganization;
import ${package}.domain.InternalOrganization;
import ${package}.domain.OrganizationInfo;
import ${package}.domain.Project;
import ${package}.domain.SubContract;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 添加分包合同
 * 
 * @author zjzhai
 * 
 */
public class AddAction extends BaseAction {

	private static final long serialVersionUID = -2370305231516570943L;

	/**
	 * 项目ID
	 */
	private long projectId = 0l;

	private String contractName;

	/**
	 * 合同金额
	 */
	private BigDecimal generalContractAmount = BigDecimal.ZERO;

	private String serialNumber;

	/**
	 * 分包比例
	 */
	private BigDecimal subcontractRatio = BigDecimal.ZERO;

	/**
	 * 支付方式
	 */
	private String modeOfPayment;

	/**
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

	private Long partAId;

	private Long partAContactId;

	private Long partBId;

	private Long partBContactId;

	private String remark;

	/**
	 * 保存分包合同成功后的ID
	 */
	private long id;

	private String errorInfo;

	public String execute() {
		if (projectId <= 0) {
			errorInfo = "分包合同必须关联项目";
			return JSON;
		}

		Project project = getProjectOf(projectId);

		if (null == project) {
			errorInfo = "分包合同必须关联项目";
			return JSON;
		}

		if (null == partBId || partBId <= 0l || null == partAId || partAId <= 0) {
			errorInfo = "合同的甲乙方是必选的";
			return JSON;
		}

		CooperationOrganization cooperationOrg = CooperationOrganization.get(partBId);

		if (null == cooperationOrg) {
			errorInfo = "该合作单位不存在";
			return JSON;
		}

		InternalOrganization internalOrg = InternalOrganization.get(partAId);

		if (null == internalOrg) {
			errorInfo = "甲方单位不存在";
			return JSON;
		}

		SubContract subContract = createBy(cooperationOrg, internalOrg, project);

		projApplication.saveEntity(subContract);

		id = subContract.getId();

		return JSON;
	}

	private SubContract createBy(CooperationOrganization cooperationOrg, InternalOrganization internalOrg, Project project) {
		SubContract subContract = new SubContract();

		subContract.setContractCategory(ContractCategory.SUB_CONTRACT);
		subContract.setContractName(contractName);
		subContract.setFinishDate(finishDate);
		subContract.setStartDate(startDate);
		subContract.setGeneralContractAmount(generalContractAmount);
		subContract.setGrantedScope(getGrantedScope());
		subContract.setModeOfPayment(modeOfPayment);
		subContract.setRemark(remark);
		subContract.setProject(project);
		subContract.setSignDate(signDate);
		subContract.setSerialNumber(serialNumber);
		subContract.setSubcontractRatio(subcontractRatio);

		OrganizationInfo partA = new OrganizationInfo(internalOrg, PersonQuery.create().organization(internalOrg)
				.id(partAContactId).getSingleResult());
		OrganizationInfo partB = new OrganizationInfo(cooperationOrg, PersonQuery.create().organization(cooperationOrg)
				.id(partBContactId).getSingleResult());

		subContract.setPartA(partA);
		subContract.setPartB(partB);
		return subContract;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public void setGeneralContractAmount(BigDecimal generalContractAmount) {
		this.generalContractAmount = generalContractAmount;
	}

	public void setSubcontractRatio(BigDecimal subcontractRatio) {
		this.subcontractRatio = subcontractRatio;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public void setPartAId(Long partAId) {
		this.partAId = partAId;
	}

	public void setPartAContactId(Long partAContactId) {
		this.partAContactId = partAContactId;
	}

	public void setPartBId(Long partBId) {
		this.partBId = partBId;
	}

	public void setPartBContactId(Long partBContactId) {
		this.partBContactId = partBContactId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public long getId() {
		return id;
	}

}
