#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://code.google.com/p/jcaptcha4struts2/taglib/2.0" prefix="jcaptcha" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>欢迎登录日海通信工程项目管理系统</title>
</head>
<body>
<div class="easyui-window" title="登录--日海通信工程项目管理系统"
     data-options="modal:true,closed:false,collapsible:false,minimizable:false,maximizable:false,closable:false"
     style="width:600px;height:300px;padding:10px;">

    <div style="margin-left: auto;margin-right: auto; margin-bottom: 20px;text-align:center;">

    </div>
    <div style="padding-left: 35px;">
        <form method="post" id="formId" action="<%=basePath%>login-submit.action">
            <table >
                <tbody>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <img src="<%=basePath%>images/logo.jpg" style="display:inline-block; vertical-align:middle;"
                             alt="日海通信工程项目管理系统"/></td>
                </tr>
                <tr>
                    <td style="text-align: right;"> 邮箱/用户名/手机</td>
                    <td><input id="username" type="text" class="easyui-validatebox span3" data-options="required:true" name="username"
                              /></td>
                </tr>
                <tr>
                    <td style="text-align: right;">  密码</td>
                    <td> <input id="password" class="easyui-validatebox span3" data-options="required:true" type="password"
                                name="password"/>
                        <a href="javascript:;" onclick="forgot_password_btn_handler();">忘记密码？</a></td>
                    <script>
                        function forgot_password_btn_handler(){
                            ${symbol_dollar}('${symbol_pound}forgot_password_dialog').dialog('open');
                        }
                    </script>
                </tr>
                <tr>
                    <td colspan="2"><s:actionerror/>
                        <s:if test="hasFieldErrors()">
                            请输入验证码
                            <input type="text" name="jCaptchaResponse" value="" id="jCaptchaResponse"/>
                            <img id="jcaptchaImg"
                                 onclick="this.src='<%=basePath%>jcaptcha_image.action?'+Math.random()"
                                 src="<%=basePath%>jcaptcha_image.action" title="看不清楚，点击换一张"/>
                            <span class="muted">看不清楚，点击换一张</span>
                            <s:fielderror>
                                <s:param>jCaptchaResponse</s:param>
                            </s:fielderror>
                        </s:if></td>
                </tr>
                <tr>
                    <td></td>
                    <td> <a id="submit" class="easyui-linkbutton" href="javascript: ;" onclick="${symbol_dollar}('${symbol_pound}formId').submit();">登录</a></td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>


    <div id="forgot_password_dialog" class="easyui-dialog" title="密码找回" style="width:400px;height:200px;padding: 20px;"
         data-options="iconCls:'icon-boss',closed:true, resizable:false,modal:true">
        邮箱：
        <br/>
        <input id="forgot_password_email" name="email" class="easyui-validatebox"
               data-options="required:true,validType:'email'"
               type="text"/>
        <br/>
        <a href="javascript:;" onclick="forgot_password_form_handler();" class="easyui-linkbutton">确定</a>

    </div>
</div>
<link id="easyuiTheme" rel="stylesheet" href="${symbol_dollar}{basePath}/scripts/easyui/themes/metro-blue/easyui.css" type="text/css"/>
<link rel="stylesheet" href="${symbol_dollar}{basePath}/scripts/easyui/themes/icon.css" type="text/css"/>
<link rel="stylesheet" href="${symbol_dollar}{basePath}/styles/application.css"/>
<!-- easyui控件 -->
<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/easyui/jquery.easyui.min.js"></script>

<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<script type="text/javascript">

    function  forgot_password_form_handler(){
        if (!${symbol_dollar}('${symbol_pound}forgot_password_email').validatebox('isValid')) {
            return;
        }
        var email = ${symbol_dollar}('${symbol_pound}forgot_password_email').val();
        var data = getJSON('${symbol_dollar}{basePath}/send-password-forgot.action?email='+ email);
        if (data && data.errorInfo) {
            imessager.alert(data.errorInfo);
            return ;
        }
        alert('重置密码的链接已发送成功，请登录邮箱点击重置链接！');
        ${symbol_dollar}('${symbol_pound}forgot_password_dialog').dialog('close')

    }

    ${symbol_dollar}(function () {
        ${symbol_dollar}('${symbol_pound}username').focus();
    });

    function showJcaptcha() {
        var verify = '<s:property value="${symbol_pound}parameters.verify"/>';
        if (verify.length > 0) {
            ${symbol_dollar}('${symbol_pound}jcaptcha').show();
        }
    }


    //实现回车提交表单
    document.onkeydown = function (e) {
        // 兼容FF和IE和Opera
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            ${symbol_dollar}('${symbol_pound}formId').trigger('submit');
            return false;
        }
        return true;
    };

    /**
     * 对${symbol_dollar}.getJSON的二次封装
     * @param url
     * @param callback
     */
    function getJSON(url, callback) {
        var result = {};
        ${symbol_dollar}.getJSON(url, function (data) {
            if (typeof(data) == "string") {
                data = evalJSON(data);
            }
            if (callback) {
                callback(data);
            }
            result = data;
        });
        return result;
    }



    /**
     * 将string转成json对象
     * @param string
     * @return {*}
     */
    function evalJSON(string) {
        try{
            return (window.JSON && JSON.parse) ? JSON.parse(string) : eval('(' + string + ')');
        }catch(e){
            return {};
        }
    }
</script>
</body>
</html>