#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title></title>
</head>
<body>
    <div style="padding: 10px 0 0 10px;">
    <table class="easyui-datagrid"
           url="<%=basePath%>sys/logs-json.action"
           fitColumns="true"
           singleSelect="false"
           pageSize="20">
        <thead>
        <tr>
            <th data-options="field:'id'">ID</th>
            <th data-options="field:'occurTime'">发生时间</th>
            <th data-options="field:'operatorId'">操作者(user)Id</th>
            <th data-options="field:'operatorName'">人名</th>
            <th data-options="field:'content'">操作内容</th>
            <th data-options="field:'ip'">ip</th>
            <th data-options="field:''">country</th>
            <th data-options="field:''">province</th>
        </tr>
        </thead>
    </table>
    </div>

</body>
</html>