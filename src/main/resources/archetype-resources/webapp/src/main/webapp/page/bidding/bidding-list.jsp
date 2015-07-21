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
<link href="<%=basePath%>styles/bootstrap/bootstrap.css" rel="stylesheet" />
<link href="<%=basePath%>styles/application.css" rel="stylesheet" />
<style>
</style>
<link href="<%=basePath%>scripts/footable/0.1/footable.css" rel="stylesheet" />
<title>投标列表</title>
</head>
<body>
	<div class="container">
		<jsp:include page="/nav.jsp" />
		<div class="row">
			<shiro:hasPermission name="biddingRequest:add">
			<div class="span1">
				<a href="<%=basePath%>bidding/bidding-request.action"><img id="preBtn" src="<%=basePath%>images/bidding/bidding-request.png" /> </a>
				<div align="center" class=" muted">
					发起投标请求
				</div>
			</div>
			</shiro:hasPermission>
			<div class="span11">
				<div>
					<div class="btn-group">
					  <a class="btn dropdown-toggle" data-toggle="dropdown" href="${symbol_pound}">
					   	 筛选
					    <span class="caret"></span>
					  </a>
					  <ul class="dropdown-menu">
					  	<li><a href="<%=basePath%>bidding/bidding-list.action?currentPage=0&earnestMoneyReturned=false">保证金未归还</a></li>
					  	<li><a href="<%=basePath%>bidding/bidding-list.action?currentPage=0&earnestMoneyReturned=true">保证金已归还</a></li>
					  	<li><a href="<%=basePath%>bidding/bidding-list.action?currentPage=0&win=false">未中标</a></li>
					    <li><a href="<%=basePath%>bidding/bidding-list.action?currentPage=0">所有</a></li>
					  </ul>
					</div>
				</div>
				<s:if test="biddings == null || biddings.size == 0">
				<br/>
				<div class="alert alert-info">没有内容</div>
				</s:if>
				<s:if test="biddings.size > 0">
				<table class="table footable">
					<thead>
					<tr>
						<th>项目名称</th>
						<th>项目金额</th>
						<th>保证金</th>
						<th>归还时间</th>
						<th>业主单位</th>
						<th>是否中标</th>
					</tr>
					</thead>
					<tbody>
					<s:iterator id="each" value="biddings">
					<tr>
						<td>
						<s:property value="${symbol_pound}each.projectName"/>
						<shiro:hasPermission name="bidding:edit">
							<a href="<%=basePath%>bidding/bidding-details.action?biddingId=<s:property value='${symbol_pound}each.id'/>">
								<i class="icon-edit"></i>
							</a>
						</shiro:hasPermission>
						</td>
						<td class="numeral"><s:property value="${symbol_pound}each.projectAmount"/></td>
						<td class="numeral"><s:property value="${symbol_pound}each.earnestMoneyInfo.amountOfEarnestMoney"/></td>
						<td>
							<s:if test="${symbol_pound}each.earnestMoneyInfo.returnTime != null">
								<s:property value="${symbol_pound}each.earnestMoneyInfo.returnTime"/>
							</s:if>
							<s:else>
								<s:if test="${symbol_pound}each.earnestMoneyInfo.estimatedReturnTime != null">
									<s:property value="${symbol_pound}each.earnestMoneyInfo.estimatedReturnTime"/>(预估)
								</s:if>
							</s:else>
						</td>
						<td><s:property value="${symbol_pound}each.owner.name"/></td>
						<td><s:property value="getText(${symbol_pound}each.biddingStatus)"/></td>
					</tr>
					</s:iterator>
					</tbody>
				</table>
				<div id="Pagination" class="pagination"></div>
				</s:if>
			</div>
		</div>
		<!-- 反馈意见 -->
	</div>
	<script src="<%=basePath%>scripts/jquery-pagination/1.2/jquery.pagination.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/My97DatePicker/4.72/WdatePicker.js" 	type="text/javascript"></script>
	<script src="<%=basePath%>scripts/footable/0.1/footable.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/application.js"></script>
	<script type="text/javascript">
	${symbol_dollar}(function() {
		//拼装筛选条件
		var link_to = '<%=basePath%>bidding/bidding-list.action?' 
				+ '&winBidding=<s:property value="winBidding"/>'
				+ '&earnestMoneyReturned=<s:property value="earnestMoneyReturned"/>';
		pagination("${symbol_pound}Pagination",'<s:property value="page.rowCount"/>',{link_to : link_to + '&currentPage=__id__', current_page : '<s:property value='currentPage'/>'});
		${symbol_dollar}('table').footable();
	});
	</script>
</body>
</html>
