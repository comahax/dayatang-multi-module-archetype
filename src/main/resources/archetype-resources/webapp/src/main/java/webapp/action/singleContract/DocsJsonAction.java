#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.singleContract;

import ${package}.domain.Document;
import ${package}.domain.SingleContract;
import ${package}.pager.PageList;
import ${package}.query.DocumentQuery;
import ${package}.query.SingleContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.DocumentDto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 附件数据
 * User: tune
 * Date: 13-6-18
 * Time: 上午10:50
 */
public class DocsJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7238452760771410321L;

	private List<DocumentDto> results;

    /**
     * 文件名
     */
    private String name;

    private Long total = 0l;

    /**
     * 框架合同编号
     */
    private Long contractId = 0L;

    @Override
    public String execute() throws Exception {
    	SingleContract contract = SingleContractQuery.grantedScopeIn(getGrantedScope()).id(contractId).getSingleResult();
    	if(null == contract){
    		return JSON;
    	}
    	
    	
    	List<Document> docs = DocumentQuery.getSingleContractOf(contractId);
    	
    	if(StringUtils.isNotEmpty(name)){
    		for(Iterator<Document> it = docs.iterator(); it.hasNext();){
        		Document doc = it.next();
        		if(!doc.getName().contains(name)){
        			it.remove();
        		}
        	}
    	}
        		
        PageList<Document> pageList = createPageList(docs);
        if (null != pageList) {
            results = DocumentDto.createBy(pageList.getData());
            total = pageList.getTotal();
        }

        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<DocumentDto> getResults() {
        if (null == results) {
            return new ArrayList<DocumentDto>();
        }
        return results;
    }

    public Long getTotal() {
        return total;
    }

   
    public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public void setName(String name) {
        this.name = name;
    }
}
