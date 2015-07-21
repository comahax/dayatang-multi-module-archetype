#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.document;


import ${package}.domain.Document;
import ${package}.webapp.action.BaseAction;

public abstract class DestroyAction extends BaseAction {

    private static final long serialVersionUID = -5313816603444753277L;

    private Long id;

    private Document doc;

    public String execute() {
        doc = Document.get(id);
        if (null == doc) {
            return NOT_FOUND;
        }

        return SUCCESS;
    }

    public abstract String getErrorInfo();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Document getDoc() {
        return doc;
    }
}
