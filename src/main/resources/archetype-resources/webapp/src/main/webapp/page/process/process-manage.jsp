#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <jsp:include page="/page/include.jsp"/>

    <link href="<%=basePath%>scripts/jquery-ui/css/smoothness/jquery-ui-1.9.1.custom.min.css" rel="stylesheet" />

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="tabbable">
					<ul class="nav nav-tabs" id="myTab">
							<li><a href="<%=basePath%>process/list-processes.action">流程定义</a></li>
							<li><a href="<%=basePath%>process/running-processes.action">当前流程实例</a></li>
							<li><a href="<%=basePath%>process/finished-processes.action">历史流程实例</a></li>
					</ul>
				</div>
			</div>
			<div class="span12">
				<iframe id="iframe" src=" " frameborder="0" scrolling="auto" height="800px" width="940px"></iframe>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>scripts/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/bootstrap/2.1/bootstrap-tab.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/application.js" type="text/javascript"></script>
	<script type="text/javascript">
		${symbol_dollar}(function() {
			${symbol_dollar}("ul.nav-tabs li:first").addClass("active").show();
			${symbol_dollar}('${symbol_pound}iframe').attr('src', ${symbol_dollar}('li.active>a').attr('href'));
			${symbol_dollar}('.nav-tabs  a').click(function() {
				${symbol_dollar}('.active').removeClass('active');
				${symbol_dollar}(this).parent().addClass('active');
				${symbol_dollar}('${symbol_pound}iframe').attr('src', ${symbol_dollar}(this).attr('href'));
				return false;
			});
		});
	</script>
</body>
</html>