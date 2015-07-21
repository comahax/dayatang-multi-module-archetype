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
<jsp:include page="/page/include.jsp"/>
	<div class="container">
		<div class="row">
			<div class="span12">
				<table class="table">
					<tr>
						<th>流程名称</th>
						<th>任务名称</th>
						<th>优先级</th>
						<th>发起人</th>
						<th>任务创建时间</th>
						<th>任务逾期时间</th>
						<th>任务描述</th>
						<th>任务所属人</th>
						<th>操作</th>
					</tr>
					
					<s:iterator id="task" value="tasks">
					<tr>
						<td><s:property value="getProcessName(${symbol_pound}task)"/></td>
						<td>${symbol_dollar}{task.name }</td>
						<td>${symbol_dollar}{task.priority }</td>
						<td><s:property value="getInitiator(${symbol_pound}task)"/></td>
						<td><s:date name="${symbol_pound}task.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td><s:date name="${symbol_pound}task.dueDate" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${symbol_dollar}{task.description }</td>
						<td>${symbol_dollar}{task.owner}</td>
						<td>
							<s:if test="${symbol_pound}task.assignee == null">
								<s:a cssClass="claim" action="claim-task.action">
									<s:param name="taskId">${symbol_dollar}{task.id}</s:param>
									签领
								</s:a>
							</s:if>
							<s:if test="${symbol_pound}task.assignee != null">
								<s:a cssClass="handle" action="fill-in-task-form.action">
									<s:param name="taskId">${symbol_dollar}{task.id}</s:param>
									办理
								</s:a>
							</s:if>
						</td>
					</tr>		
					</s:iterator>
				</table>
				<div id="Pagination" class="pagination"></div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>scripts/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/jquery-pagination/1.2/jquery.pagination.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/application.js"></script>
	<script type="text/javascript">
		${symbol_dollar}(function(){
			pagination("${symbol_pound}Pagination",<s:property value="page.rowCount"/>,{link_to:"list-tasks.action?currentPage=__id__",current_page:<s:property value='currentPage'/>});
		});
	</script>
</body>
</html>
