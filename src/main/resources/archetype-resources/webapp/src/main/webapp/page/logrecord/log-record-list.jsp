#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>styles/bootstrap/bootstrap.css"
	rel="stylesheet" />
<link href="<%=basePath%>styles/application.css" rel="stylesheet" />
<title>单位管理</title>
</head>
<body>
	
	<div class="container">
		<div class="row">
			<div class="span12">
				<table class="table">
					<thead>
						<tr>
							<td>当事人</td>
							<td>操作</td>
							<td>时间</td>
							<td>IP</td>
							<td>&nbsp;</td>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="results">
							<tr>
								<td><s:property value="username" /></td>
								<td><s:property value="operation" /></td>
								<td><s:date name="happenDate"/></td>
								<td><s:property value="ip"/></td>
								<td>&nbsp;</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<div id="Pagination" class="pagination"></div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>scripts/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/application.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/jquery-pagination/1.2/jquery.pagination.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/application.js" type="text/javascript"></script>
	<script type="text/javascript">
		${symbol_dollar}(function(){
			pagination("${symbol_pound}Pagination",'<s:property value="page.rowCount"/>',{link_to:"log-record-list.action?currentPage=__id__",current_page:'<s:property value='currentPage'/>'});
		});
	</script>
</body>
</html>