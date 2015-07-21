#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

public class LoginAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String username;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
