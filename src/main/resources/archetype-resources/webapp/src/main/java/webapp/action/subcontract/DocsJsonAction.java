#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontract;

import ${package}.domain.Document;
import ${package}.domain.SubContract;
import ${package}.pager.PageList;
import ${package}.query.SubContractQuery;
import ${package}.utils.DocumentTagConstans;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.DocumentDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-11
 * Time: 下午7:58
 */
public class DocsJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -768425897628504885L;

	/**
     * 合同的ID
     */
    private Long id = 0l;

    private List<DocumentDto> results = new ArrayList<DocumentDto>();

    private long total = 0;


    @Override
    public String execute() throws Exception {

        if (id <= 0l) {
            return JSON;
        }

        SubContract subContract = SubContractQuery.grantedScopeIn(getGrantedScope()).id(id).getSingleResult();

        if (null == subContract) {
            return JSON;
        }

        PageList<Document> documentPageList = createPageList(Document.findByOneTag(DocumentTagConstans.SUB_CONTRACT, String.valueOf(id)));

        if (null == documentPageList) {
            return JSON;
        }
        results = DocumentDto.createBy(documentPageList.getData());

        total = documentPageList.getTotal();

        return JSON;
    }

    public long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<DocumentDto> getResults() {
        if (results == null) {
            results = new ArrayList<DocumentDto>();
        }
        return results;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
