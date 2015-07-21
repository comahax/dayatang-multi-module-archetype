#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.frameworkContract;

import java.util.List;


import ${package}.domain.ContractCategory;
import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai Date: 13-4-17 Time: 下午3:58
 */
// @Result(name="success",type="freemarker",location="list.ftl")
public class ListAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942005007797081653L;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public List<Dictionary> getReceiptTypes() {
		return Dictionary.findByCategory(DictionaryCategory.RECEIPT_TYPE);
	}

	public String getReceiptTypeText(String serialNumber) {
		return Dictionary.getDictionaryTextBySerialNumBerAndCategory(serialNumber, DictionaryCategory.RECEIPT_TYPE);
	}

	public ContractCategory[] getCategoryies() {
		return ContractCategory.values();
	}
}
