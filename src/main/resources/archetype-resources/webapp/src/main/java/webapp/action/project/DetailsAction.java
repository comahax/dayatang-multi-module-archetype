#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import ${package}.domain.Project;
import ${package}.domain.ProjectStatus;
import ${package}.domain.Receipt;
import ${package}.domain.ReceiptInvoice;
import ${package}.query.ProjectQuery;
import ${package}.webapp.action.BaseAction;

import java.math.BigDecimal;

import org.apache.struts2.convention.annotation.Result;

/**
 * 项目详细页
 *
 * @author zjzhai
 */
@Result(name="success", type="freemarker", location="details.ftl")
public class DetailsAction extends BaseAction {

    private static final long serialVersionUID = 76801225992587314L;

    private long id;

    private Project project;

    public String execute() throws Exception {
        project = ProjectQuery.getAuthenticateSuccessOf(getGrantedScope(), getCurrentPerson(), id);
        if (null == project) {
            return NOT_FOUND;
        }
        return SUCCESS;
    }

    /**
     * 可提交立项
     *
     * @return
     */
    public boolean getApplyable() {
        return ProjectQuery.responsibleOf(getGrantedScope(), getCurrentPerson()).projectStatus(ProjectStatus.DRAFT).id(id).enabled().getSingleResult() != null;
    }

    /**
     * 开票总额
     *
     * @return
     */
    public BigDecimal getTotalReceiptAmount() {
        return Receipt.getTotalReceiptAmountOf(project);
    }

    /**
     * 收款总额
     *
     * @return
     */
    public BigDecimal getTotalReceiptInvoiceAmount() {
        return ReceiptInvoice.getTotalInvoiceAmountOf(project);
    }


    /**
     * 回款率
     * @return
     */
    public BigDecimal getReceiptRatio() {
        return Receipt.getReceivableRatioOf(project);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
