#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.frameworkContract;

import ${package}.domain.Document;
import ${package}.domain.FrameworkContract;
import ${package}.query.FrameworkContractQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.utils.DocumentTagGenerater;

/**
 * 删除框架合同的文件
 * User: zjzhai
 * Date: 13-8-7
 * Time: 下午9:58
 */
public class RemoveDocAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long docId = 0l;

    private Long frameworkId = 0l;

    public String execute(){
    	if(null == docId || docId <=0l || null == frameworkId || frameworkId <=0l){
    		return JSON;
    	}
    	
    	
        FrameworkContract frameworkContract = FrameworkContractQuery.grantedScopeIn(getGrantedScope()).id(frameworkId).getSingleResult();
        if (null == frameworkContract ) {
            return JSON;
        }
        Document doc = Document.get(docId);
        if(doc.containOneTag(DocumentTagGenerater.createFrameworkContract(frameworkContract))){
        	commonsApplication.removeEntity(doc);
        }


        return JSON;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public void setFrameworkId(Long frameworkId) {
        this.frameworkId = frameworkId;
    }
}
