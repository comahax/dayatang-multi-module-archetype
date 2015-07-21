#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.user;

import ${package}.domain.User;
import ${package}.webapp.action.BaseAction;
import org.activiti.engine.TaskService;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

/**
 * 修改用户名
 * User: Administrator
 * Date: 13-5-16
 * Time: 下午3:04
 */
public class EditUsernameSubmitAction extends BaseAction {


	private static final long serialVersionUID = 5002534491465748036L;


	@Inject
    protected TaskService taskService;


    private String username;



    @Override
    public String execute() throws Exception {

        if (StringUtils.isEmpty(username)) {
            return JSON;
        }

        User user = getCurrentUser();

        user.setUsername(username);

        try {
            securityApplication.saveEntity(user);
        } catch (Exception e){
            errorInfo = "用户名已经存在";
        }
        return JSON;
    }

    public String getErrorInfo(){
        return errorInfo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
