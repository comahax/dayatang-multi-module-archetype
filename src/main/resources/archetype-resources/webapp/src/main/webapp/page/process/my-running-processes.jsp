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
    <link href="<%=basePath%>scripts/jquery-ui/css/smoothness/jquery-ui-1.9.1.custom.min.css" rel="stylesheet" />

    <title>流程列表</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="span12">
				<button class="btn btn-primary closeBtn">关闭</button>
				<table class="table">
					<thead>
						<tr>
							<th>流程KEY</th>
							<th>流程名称</th>
							<th>事项</th>
							<th>当前节点</th>
							<th>发起人</th>
							<th>是否挂起</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator id="process" value="processInstances">
							<tr>
								<td><s:property value="getProcessKey(${symbol_pound}process)"/></td>
								<td><s:property value="getProcessName(${symbol_pound}process)"/></td>
								<td><s:property value="getTitle(${symbol_pound}process)"/></td>
								<td>
									<a class="trace" href='${symbol_pound}' pid="${symbol_dollar}{process.id }" title="点击查看流程图"><s:property value="getActivityName(${symbol_pound}process)"/></a>
								</td>
								<td><s:property value="getInitiator(${symbol_pound}process)"/></td>
								<td>${symbol_dollar}{process.suspended}</td>
							</tr>		
						</s:iterator>
					</tbody>
				</table>
				<div id="Pagination" class="pagination"></div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>scripts/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/jquery-ui/js/jquery-ui-1.9.1.custom.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/jquery-ui/js/jquery.ui.dialog.js" type="text/javascript"></script>
	<script type="text/javascript">
	var ctx = '<%=request.getContextPath() %>';
	${symbol_dollar}(function() {
		// 跟踪
	    ${symbol_dollar}('.trace').click(graphTrace);
	});
	</script>
	<script type="text/javascript" src="<%=basePath%>scripts/process/process-diagram.js"></script>
	<script src="<%=basePath%>scripts/jquery-pagination/1.2/jquery.pagination.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/application.js"></script>
	<script type="text/javascript">
		${symbol_dollar}(function(){
			pagination("${symbol_pound}Pagination",'<s:property value="page.rowCount"/>',{link_to:"running-processes.action?currentPage=__id__",current_page:'<s:property value='currentPage'/>'});
		});
	</script>
</body>
</html>
