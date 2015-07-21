#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.outputvalue;

import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;

import java.math.BigDecimal;

/**
 * 修改项目的预估总产值
 * User: zjzhai
 * Date: 13-4-20
 * Time: 下午6:28
 */
public class EditTotalValueOfProjectAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4662687040031799154L;


	/**
     * 项目ID
     */
    private long id = 0l;


    private BigDecimal numberValue = BigDecimal.ZERO;

    @Override
    public String execute() throws Exception {

        Project project = getProjectOf(id);

        if (null == project) {
            return NOT_FOUND;
        }

        project.setTotalOutputvalue(numberValue);

        projApplication.saveEntity(project);

        return JSON;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(BigDecimal numberValue) {
        this.numberValue = numberValue;
    }
}
