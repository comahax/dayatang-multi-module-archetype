#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.impl;

import com.dayatang.domain.AbstractEntity;
import ${package}.application.ProjApplication;
import ${package}.domain.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ProjApplicationImpl implements ProjApplication {

	public void saveEntity(AbstractEntity entity) {
		if (null == entity) {
			return;
		}
		entity.save();
	}

	public void removeEntity(AbstractEntity entity) {
		if (null == entity) {
			return;
		}
		entity.remove();
	}

	@Override
	public ProjectBudgetHistory saveProjectBudgetHistory(Project project, Date date, Person person, List<Capitaltotakeup> capitaltotakeups) {
		if (null == project) {
			throw new RuntimeException("项目不能为空！");
		}

		if (ProjectStatus.APPROVING.equals(project.getStatus())) {
			return null;
		}

		ProjectBudgetHistory history = new ProjectBudgetHistory(project, date, person, capitaltotakeups);
		history.save();

		return history;
	}

	@Override
	public void changeProjectsProjectTypeSerialNumber(String oldTypeSerialNumber, String newSerialNumber) {
		// 查出所有具有老序列号的的项目
		for (Project project : Project.findByProjectType(oldTypeSerialNumber)) {
			project.setProjectType(newSerialNumber);
			project.save();
		}
	}

	@Override
	public void editDictionary(String oldSerialNumber, Dictionary newDic) {
		newDic.save();
		proccessWhenDicIsProjectType(newDic, oldSerialNumber);
		proccessWhenDicIsOwnerType(newDic, oldSerialNumber);
		proccessWhenDicIsLicense(newDic, oldSerialNumber);
	}

	private void proccessWhenDicIsLicense(Dictionary dic, String oldSerialNumber) {
		if (!DictionaryCategory.ORGANIZATION_CREDENTIALS_TYPE.equals(dic.getCategory()) || !DictionaryCategory.PERSON_CREDENTIALS_TYPE.equals(dic.getCategory()) || oldSerialNumber.equals(dic.getSerialNumber())) {
			return;
		}
		License.changeLicenseType(oldSerialNumber, dic.getSerialNumber());
	}

	private void proccessWhenDicIsOwnerType(Dictionary dic, String oldSerialNumber) {
		if (!DictionaryCategory.OWNER_TYPE.equals(dic.getCategory()) || oldSerialNumber.equals(dic.getSerialNumber())) {
			return;
		}
		OutputValue.changeOwnerType(oldSerialNumber, dic.getSerialNumber());
		OwnerOrganization.changeOwnerType(oldSerialNumber, dic.getSerialNumber());
	}

	private void proccessWhenDicIsProjectType(Dictionary dic, String oldSerialNumber) {
		if (!DictionaryCategory.PROJECT_TYPE.equals(dic.getCategory()) || oldSerialNumber.equals(dic.getSerialNumber())) {
			return;
		}
		OutputValue.changeProjectType(oldSerialNumber, dic.getSerialNumber());
		Project.changeProjectType(oldSerialNumber, dic.getSerialNumber());
	}

	public Project assosiateCooperationToProject(OrganizationInfo organizationInfo, Project project) {
		project.assosiateWithCooperationOrg(organizationInfo);
		return project;
	}

	public OutputValue reportOutputValue(Monthly monthly, SpecialtyProject specialtyProject, BigDecimal value) {
		if (null == specialtyProject || specialtyProject.isDisabled() || null == monthly) {
			return null;
		}
		OutputValue outputValue = OutputValue.get(monthly, specialtyProject);
		if (null == outputValue) {
			outputValue = new OutputValue(monthly, specialtyProject, value);
		} else {
			outputValue.setNumericalValue(value);
		}
		outputValue.save();
		return outputValue;
	}

	@Override
	public OutputValue reportOutputValue(Monthly monthly, Project project, BigDecimal value) {
		if (null == project || !project.isBusinessOperationsable() || null == monthly) {
			return null;
		}
		OutputValue outputValue = OutputValue.get(monthly, project);
		if (outputValue == null) {
			outputValue = new OutputValue(monthly, project, value);
		} else {
			outputValue.setNumericalValue(value);
		}
		outputValue.save();
		return outputValue;
	}

	public void logOutputValueReporter(Person reporter, Date reported, OutputValue outputValue) {
		if (null == outputValue) {
			return;
		}

		OutputValueReporter outputValueReportor = OutputValueReporter.findBy(outputValue);
		if (null == outputValueReportor) {
			outputValueReportor = new OutputValueReporter(reporter, reported, outputValue);
		}
		outputValueReportor.setReportDate(reported);
		outputValueReportor.setReporter(reporter);
		outputValueReportor.save();
	}

	public void cancelProjectElement(ProjectElement projectElement, Person lastUpdator, Date lastUpdated) {
		projectElement.cancel(lastUpdator, lastUpdated);
		// TODO 也要将文档设为disabled
	}

	public void resumeProjectElement(ProjectElement projectElement, Person lastUpdator, Date lastUpdated) {
		projectElement.cancel(lastUpdator, lastUpdated);
	}

	public SpecialtyProject assignCooperation(SpecialtyProject specialtyProject, CooperationOrganization organization, Person person) {
		if (specialtyProject == null || specialtyProject.isDisabled()) {
			return null;
		}
		specialtyProject.assignCooperation(organization, person);
		specialtyProject.getSubProject().getProject().assosiateWithCooperationOrg(new OrganizationInfo(organization, person));
		return specialtyProject;
	}

	/**
	 * 设置项目的计划完工日期
	 */
	public ProjectElement predictFinish(ProjectElement projectElement, Date predictFinishDate) {
		if (projectElement.isDisabled()) {
			return projectElement;
		}
		projectElement.setPredictFinishDate(predictFinishDate);
		projectElement.save();
		return projectElement;
	}

	@Override
	public void setContractAmount(List<Long> subProjectIds, List<BigDecimal> amounts, InternalOrganization scope) {
		SubProject.setContractAmount(subProjectIds, amounts, scope);
	}

	public SubProject addSpecialtyProjectsToSubProject(Set<SpecialtyProject> specialtyProjects, SubProject subProject) {
		for (SpecialtyProject each : specialtyProjects) {
			subProject.addSpecialtyProject(each);
		}
		return subProject;
	}

	public SubProject addSubProjectToSingleContract(SubProject subProject, SingleContract singleContract) {
		if (null == subProject || subProject.isDisabled() || null == singleContract || singleContract.equals(subProject.getSingleContract())) {
			return subProject;
		}
		subProject.setSingleContract(singleContract);
		subProject.save();
		subProject.transferOutputValue(singleContract);
		return subProject;
	}

	// 完工
	public void finishedProjectElement(ProjectElement projectElement, Date finishDate) {
		if (projectElement.isDisabled() || projectElement.isClosed()) {
			return;
		}
		projectElement.finish(finishDate);
		projectElement.save();
	}

	public BiddingRequest originatingBiddingRequest(BiddingRequest biddingRequest, Person currentPerson) {
		biddingRequest.log(currentPerson, new Date());
		biddingRequest.setReleasePerson(currentPerson);
		biddingRequest.save();
		return biddingRequest;
	}

	public BiddingRequestApprove approveBiddingRequest(BiddingRequestApprove approveInfo, BiddingRequest biddingRequest, Person currentPerson) {
		approveInfo.log(currentPerson, new Date());
		approveInfo.setBiddingRequest(biddingRequest);
		approveInfo.setApprovePerson(currentPerson);
		approveInfo.save();
		return approveInfo;
	}

	public Bidding createBidding(BiddingRequest biddingRequest) {
		// 审批通过生成标
		Bidding bidding = Bidding.createBy(biddingRequest);
		bidding.save();
		return bidding;
	}

	public void applyBidding(Bidding bidding, Person principal) {
		bidding.setPrincipal(principal);
		bidding.save();
	}

	public void addProjectMonthlyBudgetsToProject(Project project, List<ProjectMonthlyBudget> projectMonthlyBudgets) {
		if (projectMonthlyBudgets == null) {
			return;
		}

		for (ProjectMonthlyBudget each : projectMonthlyBudgets) {
			each.setProject(project);
			each.save();
		}
	}

	public SingleContract addSingleContractToProject(SingleContract singleContract, Project project) {
		if (project.isBusinessOperationsable()) {
			singleContract.setProject(project);
			singleContract.save();
		}
		return singleContract;
	}

	@Override
	public SubProject addSubProjectToProject(SubProject subProject, Project project) {
		if (project.isBusinessOperationsable()) {
			subProject.setProject(project);
		}
		return subProject;
	}

	@Override
	public Expenditure addSubContractExpenditure(SubProject subProject, Expenditure expenditure, Person creator, Date created) {
		if (subProject.isDisabled()) {
			return null;
		}
		expenditure.setSubProject(subProject);
		expenditure.log(creator, created);
		expenditure.setLastUpdated(new Date());
		expenditure.save();
		return expenditure;
	}

	@Override
	public void assosiatedWith(SubProject subProject, SingleContract singleContract) {
		subProject.setSingleContract(singleContract);
		subProject.save();
	}

	public Project addProjectExpenditures(Project project, List<Expenditure> expenditures, Person creator, Date created) {
		if (expenditures == null || expenditures.isEmpty()) {
			return project;
		}
		for (Expenditure each : expenditures) {
			each.setProject(project);
			each.log(creator, created);
			each.save();
		}
		return project;
	}

	@Override
	public ReceiptInvoice addReceiptInvoiceToSingleContract(SingleContract singleContract, ReceiptInvoice invoice) {
		invoice.setContract(singleContract);
		invoice.save();
		return invoice;
	}

	@Override
	public Contract assosiateContractWith(Contract contract, Project project, Person lastUpdator, Date lastUpdated) {
		contract.lastUpdate(lastUpdator, lastUpdated);
		contract.associateWith(project);
		return contract;
	}

	@Override
	public FrameworkContract addFrameworkContract(FrameworkContract contract) {
		contract.save();
		return contract;
	}



	@Override
	public void unRelevanceSinglecontractsByFramwork(FrameworkContract contract, List<SingleContract> singleContracts) {
		if (null == singleContracts || null == contract) {
			return;
		}
		// 如果未指定初始值，则每次解除绑定更新
		contract.removeSingleContractIn(singleContracts);
	}

	@Override
	public SingleContract addSingleContractToProject(SingleContract contract, Project project, InternalOrganization grantedScope, FrameworkContract frameworkContract, Person creator, Date created) {
		contract.setFrameworkContract(frameworkContract);
		contract.log(creator, created);
		contract.setGrantedScope(grantedScope);
		contract.associateWith(project);
		return contract;
	}

	@Override
	public SubContract addSubContractToProject(SubContract contract, ChengBao chengBaoContract, Project project, InternalOrganization grantedScope, Person creator, Date created) {
		contract.setContractCategory(ContractCategory.SUB_CONTRACT);
		contract.log(creator, created);
		contract.setChengBao(chengBaoContract);
		contract.setGrantedScope(grantedScope);
		contract.associateWith(project);
		return contract;
	}

	@Override
	public Project addSpecialtyToProject(Project project, Set<Specialty> specialties) {
		if (specialties == null || specialties.isEmpty()) {
			return project;
		}
		project.getSpecialties().addAll(specialties);
		project.save();
		return project;
	}

	@Override
	public SubProject addSpecialtyToSubProject(SubProject subProject, Specialty specialty) {
		subProject.addSpecialty(specialty);
		return subProject;
	}

	@Override
	public void saveSomeEntities(AbstractEntity... entities) {
		if(null == entities || entities.length == 0){
			return;
		}
		for(AbstractEntity each : entities){
			each.save();
		}
		
		
	}
}
