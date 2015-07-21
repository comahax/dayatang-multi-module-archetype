#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.owner;

import ${package}.domain.OwnerOrganization;
import org.apache.commons.lang3.StringUtils;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 上午11:22
 */
public class AddAction extends AddBaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = -2611004455310182091L;

	/**
     * 如果是编辑ID就有值
     */
    private long id = 0l;

    private boolean result = false;

    @Override
    public String execute() throws Exception {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(ownerCategory)) {
            return JSON;
        }

        OwnerOrganization owner = new OwnerOrganization();

        if (id > 0l) {
            owner = OwnerOrganization.get(id);
            if (null == owner) {
                owner = new OwnerOrganization();
            }
        }

        init(owner);
        commonsApplication.saveEntity(owner);
        result = true;

        return JSON;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isResult() {
        return result;
    }


}
