#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<jsp:include page="/page/include.jsp"/>
<div style="padding: 15px 0 0 10px;">
    <form id="modify_psw_form" action="<%=basePath%>personal/modify-psw-submit.action" method="post">
        <p><label>旧密码</label><br/>
        <input name="origin" type="password" class="easyui-validatebox" data-options="required:true"/>
        </p>
        <p>
        <label>密码</label><label class="muted">最少 6 个字符</label><br/>
        <input id="loginpass" name="loginpass" type="password"/>
        </p>
        <p>
        <label>重复密码</label><br/>
        <input id="verifypass" name="verifypass" type="password" />
        </p>
        <button type="submit">更新</button>
    </form>
    <script type="text/javascript">
        ${symbol_dollar}(function(){
            ${symbol_dollar}.extend(${symbol_dollar}.fn.validatebox.defaults.rules, {
                pswModify: {
                    validator: function(value, param){
                        return value == ${symbol_dollar}(param[0]).val() && ${symbol_dollar}(param[0]).val().length >= 6;
                    },
                    message: '密码与重复密码不相同或少于6个字符'
                }
            });
            ${symbol_dollar}('${symbol_pound}loginpass').validatebox({
                required: true,
                validType:"pswModify['${symbol_pound}verifypass']"
            });
            ${symbol_dollar}('${symbol_pound}verifypass').validatebox({
                required: true,
                validType: "pswModify['${symbol_pound}loginpass']"
            });

            ${symbol_dollar}('${symbol_pound}modify_psw_form').form({
                success : function(data){
                   if(data.errorInfo){
                       imessager.alert(data.errorInfo);
                   }else{
                       ${symbol_dollar}('${symbol_pound}modify_psw_form').trigger('reset');
                       imessager.success_tip();
                   }
                }
            });


        });

    </script>
</div>
