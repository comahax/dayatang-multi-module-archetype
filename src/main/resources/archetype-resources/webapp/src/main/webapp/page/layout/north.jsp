#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
            + "/";
%>
<style>
    .vertical {
        display: inline-block;
        vertical-align: middle;
    }
</style>
<div>
    <img src="<%=basePath%>images/logo.jpg" alt="日海通信工程项目管理系统" class="vertical"/>
    <span class="vertical">日海通信工程项目管理系统|</span>
    <s:property value="currentPerson.name"/>
    <a href="javascript:;"
       class="easyui-splitbutton"
       data-options="menu:'${symbol_pound}noth_roleswitch'">
        <s:property value="assignment.organization.name"/>-<s:property value="assignment.role.description"/>
    </a>
    <div id="noth_roleswitch" style="width:350px;" >
        <s:iterator id="each" value="assignments">
            <div data-options="name:'${symbol_dollar}{each.id}',href:'<%=basePath%>user/switch-role.action?raid=${symbol_dollar}{each.id}'">
            ${symbol_dollar}{each.organization.name}-${symbol_dollar}{each.role.description}
            </div>
        </s:iterator>
    </div>

    <%--退出--%>
    <a class="easyui-linkbutton"
       title="退出"
       plain="true"
       onclick="javascript:location.href='<%=basePath%>logout.action'"
       href="javascript:;"
       iconCls="icon-exit">&nbsp;</a>


</div>