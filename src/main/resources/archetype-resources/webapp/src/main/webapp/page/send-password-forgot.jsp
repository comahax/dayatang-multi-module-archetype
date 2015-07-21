#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <link id="easyuiTheme" rel="stylesheet" href="<%=basePath%>scripts/easyui/themes/metro-blue/easyui.css" type="text/css"/>
    <link rel="stylesheet" href="<%=basePath%>scripts/easyui/themes/icon.css" type="text/css"/>
    <link rel="stylesheet" href="<%=basePath%>styles/application.css"/>
    <!-- easyui控件 -->
    <script type="text/javascript" src="<%=basePath%>scripts/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>scripts/easyui/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%=basePath%>scripts/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
    <title>密码找回－－通信工程项目管理系统</title>
</head>
<body>



</body>
</html>