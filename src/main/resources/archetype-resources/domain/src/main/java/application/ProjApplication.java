#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application;

import com.dayatang.domain.AbstractEntity;
import ${package}.domain.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 应用层门面接口。封装对领域层的访问。
 */
public interface ProjApplication {

	/**
	 * 汇报某个专业工程的产值
	 * 
	 * @param monthly
	 * @param specialtyProject
	 * @param value
	 * @return
	 */
	OutputValue reportOutputValue(Monthly monthly, SpecialtyProject specialtyProject, BigDecimal value);

	/**
	 * 汇报某个项目的产值
	 * 
	 * @param monthly
	 *            1 base
	 * @param project
	 * @param value
	 * @return
	 */
	OutputValue reportOutputValue(Monthly monthly, Project project, BigDecimal value);

	/**
	 * 记录下谁最后报的产值
	 * 
	 * @param reporter
	 * @param reported
	 * @param outputValue
	 */
	void logOutputValueReporter(Person reporter, Date reported, OutputValue outputValue);

	/**
	 * 添加框架合同
	 * 
	 * @param contract
	 * @return
	 */
	FrameworkContract addFrameworkContract(FrameworkContract contract);

	public void unRelevanceSinglecontractsByFramwork(FrameworkContract contract, List<SingleContract> singleContracts);

	/**
	 * 添加单项合同
	 * 
	 * @param contract
	 *            单项合同
	 * @param project
	 * @param frameworkContract
	 *            单项合同对应的框架合同，可空
	 * @param creator
	 * @param created
	 * @return
	 */
	SingleContract addSingleContractToProject(SingleContract contract, Project project, InternalOrganization createdOfInternal, FrameworkContract frameworkContract, Person creator, Date created);

	/**
	 * 添加分包合同
	 * 
	 * @param contract
	 * @param chengBaoContract
	 *            对应到的承包合同
	 * @param createdOfInternal
	 *            合作单位
	 * @param project
	 * @param creator
	 * @param created
	 * @return
	 */
	SubContract addSubContractToProject(SubContract contract, ChengBao chengBaoContract, Project project, InternalOrganization createdOfInternal, Person creator, Date created);

	/**
	 * 将合同关联到项目
	 * 
	 * @param contract
	 * @return
	 */
	Contract assosiateContractWith(Contract contract, Project project, Person lastUpdator, Date lastUpdated);

	/**
	 * 撤消项目
	 * 
	 * @param projectElement
	 * @param lastUpdator
	 * @param lastUpdated
	 */
	void cancelProjectElement(ProjectElement projectElement, Person lastUpdator, Date lastUpdated);

	/**
	 * 恢复撤消项目
	 * 
	 * @param projectElement
	 * @param lastUpdator
	 * @param lastUpdated
	 */
	void resumeProjectElement(ProjectElement projectElement, Person lastUpdator, Date lastUpdated);

	/**
	 * 将施工队关联到项目中
	 * 
	 * @param organizationInfo
	 * @param project
	 * @return
	 */
	Project assosiateCooperationToProject(OrganizationInfo organizationInfo, Project project);

	/**
	 * 添加月度预算对项目中
	 * 
	 * @param project
	 * @param projectMonthlyBudgets
	 */
	void addProjectMonthlyBudgetsToProject(Project project, List<ProjectMonthlyBudget> projectMonthlyBudgets);

	/**
	 * 在项目下新建一个单项合同
	 * 
	 * @param singleContract
	 * @param project
	 * @return
	 */
	SingleContract addSingleContractToProject(SingleContract singleContract, Project project);

	/**
	 * 在项目下单独建立一个单点
	 * 
	 * @param subProject
	 * @param project
	 * @return
	 */
	SubProject addSubProjectToProject(SubProject subProject, Project project);

	/**
	 * 添加专业到项目中
	 * 
	 * @param project
	 * @param specialties
	 * @return
	 */
	Project addSpecialtyToProject(Project project, Set<Specialty> specialties);

	/**
	 * 添加专业到单点工程中
	 * 
	 * @param subProject
	 * @param specialty
	 * @return
	 */
	SubProject addSpecialtyToSubProject(SubProject subProject, Specialty specialty);

	/**
	 * 在添加一个单点到单项合同下
	 * 
	 * @param subProject
	 * @param singleContract
	 * @return
	 */
	SubProject addSubProjectToSingleContract(SubProject subProject, SingleContract singleContract);

	/**
	 * 项目竣工
	 * 
	 * @param projectElement
	 * @param finishDate
	 */
	void finishedProjectElement(ProjectElement projectElement, Date finishDate);

	/**
	 * 添加分包付款
	 * 
	 * @param subProject
	 * @param expenditure
	 * @param creator
	 * @param created
	 * @return
	 */
	Expenditure addSubContractExpenditure(SubProject subProject, Expenditure expenditure, Person creator, Date created);

	/**
	 * 设置项目的预估完工日期
	 * 
	 * @param projectElement
	 * @param predictFinishDate
	 * @return
	 */
	ProjectElement predictFinish(ProjectElement projectElement, Date predictFinishDate);

	/**
	 * 将单点工程与单项关联起来
	 * 
	 * @param subProject
	 * @param singleContract
	 */
	void assosiatedWith(SubProject subProject, SingleContract singleContract);

	/**
	 * 添加收款发票
	 * 
	 * @param singleContract
	 * @param invoice
	 * @return
	 */
	ReceiptInvoice addReceiptInvoiceToSingleContract(SingleContract singleContract, ReceiptInvoice invoice);

	/**
	 * 同时设置多个单点的合同金额
	 * 
	 * @param subProjectIds
	 * @param amounts
	 * @param scope
	 *            用户的访问范围
	 */
	void setContractAmount(List<Long> subProjectIds, List<BigDecimal> amounts, InternalOrganization scope);

	/**
	 * 分配施工队
	 * 
	 * @param specialtyProject
	 * @param organization
	 * @param person
	 * @return
	 */
	SpecialtyProject assignCooperation(SpecialtyProject specialtyProject, CooperationOrganization organization, Person person);

	/**
	 * 添加实际支出
	 * 
	 * @param project
	 * @param expenditures
	 * @param creator
	 * @param created
	 * @return
	 */
	Project addProjectExpenditures(Project project, List<Expenditure> expenditures, Person creator, Date created);

	/**
	 * 添加一批专业工程到单点
	 * 
	 * @param specialtyProjects
	 * @param subProject
	 * @return
	 */
	SubProject addSpecialtyProjectsToSubProject(Set<SpecialtyProject> specialtyProjects, SubProject subProject);

	/**
	 * 发起投标请求
	 * 
	 * @param biddingRequest
	 * @param currentPerson
	 * @return
	 */
	BiddingRequest originatingBiddingRequest(BiddingRequest biddingRequest, Person currentPerson);

	/**
	 * 审批投标请求
	 * 
	 * @param approveInfo
	 * @param biddingRequest
	 * @param currentPerson
	 * @return
	 */
	BiddingRequestApprove approveBiddingRequest(BiddingRequestApprove approveInfo, BiddingRequest biddingRequest, Person currentPerson);

	/**
	 * 审批通过后生成标
	 * 
	 * @param biddingRequest
	 * @return
	 */
	Bidding createBidding(BiddingRequest biddingRequest);

	void saveEntity(AbstractEntity entity);

	void removeEntity(AbstractEntity entity);

	/**
	 * * 保存一份项目的收支预算信息
	 * 
	 * @param project
	 *            项目
	 * @param date
	 *            收支预算信息些次版本的创建日期
	 * @param person
	 *            版本创建人
	 * @param capitaltotakeups
	 *            项目资金占用详细情况
	 * @return
	 */
	ProjectBudgetHistory saveProjectBudgetHistory(Project project, Date date, Person person, List<Capitaltotakeup> capitaltotakeups);

	/**
	 * 更新已经存在的项目的项目类型的字段
	 * 
	 * @param oldTypeSerialNumber
	 * @param newSerialNumber
	 */
	void changeProjectsProjectTypeSerialNumber(String oldTypeSerialNumber, String newSerialNumber);

	/**
	 * 编辑字典时需要处理的
	 * 
	 * @param oldSerialNumber
	 *            原来的序列号
	 * @param newDic
	 */
	void editDictionary(String oldSerialNumber, Dictionary newDic);

	/**
	 * 保存一批在一个事务中的实体,有先后顺序
	 * 
	 * @param abstractEntities
	 */
	void saveSomeEntities(AbstractEntity... entities);

}
