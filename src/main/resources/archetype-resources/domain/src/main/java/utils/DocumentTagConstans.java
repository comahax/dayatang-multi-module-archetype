#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;

/**
 * User: zjzhai Date: 13-4-12 Time: 上午11:28
 */
public interface DocumentTagConstans {

	// 所属项目
	public final static String PROJECT = "PROJECT";

	// 所属单点
	public final static String SUBPROJECT = "SUBPROJECT";

	// 由谁上传
	public final static String UPLOADED_BY = "UPLOADED_BY";

	// 所属机构
	public final static String HOLDER_ORGANIZATION = "HOLDER_ORGANIZATION";

	// 所属发票
	public final static String INVOICE = "INVOICE";

	// 所属收款发票
	public final static String RECEIPT_INVOICE = "RECEIPT_INVOICE";

	// 所属投标请求
	public final static String BIDDING_REQUEST = "BIDDING_REQUEST";

	// 所属招标公告审批信息
	public final static String BIDDING_APPROVE = "BIDDING_APPROVE";

	// 所属招标公告报名信息
	public final static String BIDDING_APPLY = "BIDDING_APPLY";

	// 文档是否删除
	public final static String DISABLED = "DISABLED";

	// 分包发票
	public final static String SUBCONTRACT_INVOICE = "SUBCONTRACT_INVOICE";

	// 分包付款
	public final static String SUBCONTRACT_EXPENDITURE = "SUBCONTRACT_EXPENDITURE";

	// 项目审批
	public final static String PROJECT_APPROVE = "PROJECT_APPROVE";

	// 所属合同
	public final static String CONTRACT = "CONTRACT";

	// 所属单项合同
	public final static String SINGLE_CONTRACT = "SINGLE_CONTRACT";
	// 所属分包合同
	public final static String SUB_CONTRACT = "SUB_CONTRACT";
	// 所属框架合同
	public final static String FRAMEWORK_CONTRACT = "FRAMEWORK_CONTRACT";

	// 所属框架合同
	public final static String RECEIPT = "RECEIPT";

	public final static String LICENSE = "LICENSE";

	// 分包付款
	public static final String SUBCONTRACT_PAYMENT = "SUBCONTRACT_PAYMENT";

}
