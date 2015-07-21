#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <jsp:include page="/page/include.jsp"/>

<style>
</style>
<title>发起流程</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="span12">
			<s:form action="/process/start-process.action" method="post" theme="simple">
				${symbol_dollar}{startForm}
				<s:submit cssClass="btn" value="完成"/>
				<s:hidden name="processDefinitionId"/>
			</s:form>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>scripts/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/application.js"></script>
</body>
</html>
