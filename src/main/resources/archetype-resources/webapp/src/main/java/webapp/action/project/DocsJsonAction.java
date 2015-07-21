#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Document;
import ${package}.domain.Project;
import ${package}.pager.PageList;
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
     * 项目的ID
     */
    private Long id = 0l;

    private List<DocumentDto> results = new ArrayList<DocumentDto>();

    private long total = 0;


    @Override
    public String execute() throws Exception {

        if (id <= 0l) {
            return JSON;
        }

        Project project = getProjectQuery().id(id).getSingleResult();

        if (null == project) {
            return JSON;
        }

        PageList<Document> documentPageList = createPageList(Document.findByOneTag(DocumentTagConstans.PROJECT, String.valueOf(id)));

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
