#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.license;

import ${package}.domain.Document;
import ${package}.pager.PageList;
import ${package}.query.DocumentQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.DocumentDto;

import java.util.ArrayList;
import java.util.List;

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
	private static final long serialVersionUID = -3393166171670394080L;

	private List<DocumentDto> results;

    private Long total = 0l;

    /**
     * 证照编号
     */
    private Long license_id = 0L;

    @Override
    public String execute() throws Exception {
        PageList<Document> pageList = createPageList(DocumentQuery.licenseOf(license_id));
        if(null != pageList){
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

    public void setLicense_id(Long license_id) {
        this.license_id = license_id;
    }
}
