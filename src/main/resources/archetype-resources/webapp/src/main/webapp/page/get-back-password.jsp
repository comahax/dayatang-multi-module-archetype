#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
            + "/";
%>
<html>
<head>
    <link id="easyuiTheme" rel="stylesheet" href="${symbol_dollar}{basePath}/scripts/easyui/themes/metro-blue/easyui.css" type="text/css"/>
    <link rel="stylesheet" href="${symbol_dollar}{basePath}/scripts/easyui/themes/icon.css" type="text/css"/>
    <link rel="stylesheet" href="${symbol_dollar}{basePath}/styles/application.css"/>
    <!-- easyui控件 -->
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/easyui/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
    <title>重置密码</title>
</head>
<body>
<div class="easyui-dialog" title="重置密码" style="width:500px;height:400px;padding: 40px;"
     data-options="closable:false,modal:true">
    <div class="alert">${symbol_dollar}{msg}</div>
    <form id="reset_password_form" action="${symbol_dollar}{basePath}/reset-password.action">
        新密码：<input id="password" name="password" type="password" class="easyui-validatebox" data-options="required:true" />
        <p/>
        重复输入：<input id="re_password" name="repassword" type="password" class="easyui-validatebox" validType="equals['${symbol_pound}password']"/>
        <p/>
        <input type="hidden" name="symbol" value="${symbol_dollar}{symbol}">
        <input type="hidden" name="checksum" value="${symbol_dollar}{checksum}">
        <hr/>
        <button type="submit">确定</button>
        <div class="muted"><a href="${symbol_dollar}{basePath}/login.action?username=${symbol_dollar}{symbol}">使用新密码登录</a></div>
    </form>
</div>
<script>
    ${symbol_dollar}.ajaxSettings.async = false;
    ${symbol_dollar}(function(){
        ${symbol_dollar}.extend(${symbol_dollar}.fn.validatebox.defaults.rules, {
            equals: {
                validator: function(value,param){
                    return value == ${symbol_dollar}(param[0]).val();
                },
                message: '两次密码不一样！'
            }
        });

    });

    function  reset_password_form_submit(){
        ${symbol_dollar}('${symbol_pound}reset_password_form').form('submit', {
            success : function (data){
                data = evalJSON(data);
                if (data && data.errorInfo) {
                    imessager.alert(data.errorInfo);
                    return;
                }
                alert('设置成功！点击确定跳转到登录页！');
                location.href = "${symbol_dollar}{basePath}/login.action?username=${symbol_dollar}{symbol}";
            }
        });
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