#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.license;

import ${package}.domain.License;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.LicenseDto;

import java.util.List;

/**
 * User: tune
 * Date: 13-6-4
 * Time: 上午10:29
 */
public class ListJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3643243829445595229L;

	private List<LicenseDto> results;

    private int total = 0;

    @Override
    public String execute() throws Exception {

        List<License> licenses = License.findAll(License.class);
        results = LicenseDto.createBy(licenses);

        total = results.size();
        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<LicenseDto> getResults() {
        return results;
    }

    public int getTotal() {
        return total;
    }
}
