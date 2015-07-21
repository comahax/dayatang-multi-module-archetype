#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

/**
 * 合同类型
 * 
 * @author zjzhai
 * 
 */
public enum ContractCategory {

	// 框架合同
	FRAMEWORK_PROJECT_CONTRACT {
		public String getCnText() {
			return "框架合同";
		}
	},

	// 单项总包
	SINGLE_PROJECT_CONTRACT {
		public String getCnText() {
			return "单项合同";
		}
	},

	// 分包合同
	SUB_CONTRACT {
		public String getCnText() {
			return "分包合同";
		}
	};
	public abstract String getCnText();

}
