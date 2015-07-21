#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

/**
 * 字典类型
 * 
 */
public enum DictionaryCategory {
	PROJECT_TYPE, // 项目类型
	AP_TYPE, // 接入点类型
	ORGANIZATION_CREDENTIALS_TYPE, // 单位证书类型
	PERSON_CREDENTIALS_TYPE, // 人员证书类型
	EXPENDITURE_TYPE, // 支出类型
	RECEIPT_TYPE, // 收款类型
	OWNER_TYPE, // 业主单位类型
	ORGANIZATION_TYPE, // 组织机构类型
	CUSTOM_BUDGET_TYPE // 自定义成本类型

	// SUB_PROJECT_TYPE, //子项目类型
	// EQUIPMENT_TYPE, //设备类型
	// DELISTING_ENTRUST_TYPE, //摘牌委托类型
	// PROJ_DOC_TYPE, //项目文件类型
	// MONTHLY_WORK_REPORT_TYPE, //月度工作报告类型
	// SAFETY_QUALITY_REPORT_TYPE //安全质量月报类型

}