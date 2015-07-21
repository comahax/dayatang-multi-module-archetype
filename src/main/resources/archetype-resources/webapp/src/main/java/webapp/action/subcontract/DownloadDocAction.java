#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontract;

import ${package}.domain.Document;
import ${package}.domain.SubContract;
import ${package}.query.SubContractQuery;
import ${package}.webapp.action.document.DownloadBaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *  User: zjzhai Date: 13-8-7 Time: 下午2:45
 */
public class DownloadDocAction extends DownloadBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7832966792784620835L;

	private Long contractId = 0l;

	@Override
	public String execute() {

		SubContract contract = SubContractQuery.grantedScopeIn(getGrantedScope()).id(contractId).getSingleResult();

		if (null == contract || !SUCCESS.equals(super.execute())) {
			return NOT_FOUND;
		}

		Document document = getDoc();

		if (!document.containOneTag(new DocumentTagGenerater().subContract(contractId).getSingleResult())) {
			return NOT_FOUND;
		}

		return SUCCESS;
	}

	/**
	 * 得到内容类型
	 * 
	 * @return
	 */
	public String getContentType() {
		return super.getContentTypeDefaultImpl();
	}

	public InputStream getInputStream() {
		InputStream result = null;
		try {
			result = super.getInputStreamDefaultImpl();
		} catch (FileNotFoundException e) {
			sendExceptionMsgAdmin("文件不存在--" + getDocId());
		} catch (Exception e) {
			sendExceptionMsgAdmin(e.getMessage());
		}

		return result;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

}
