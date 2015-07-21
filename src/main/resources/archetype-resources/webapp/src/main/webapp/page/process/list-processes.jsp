#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/page/scripts.jsp"/>
</head>
<body>
	<div>
		<s:form id="deploy_process_form" class="form-inline"
			action="/process/deploy-process.action" method="post"
			enctype="multipart/form-data" theme="simple">
			<s:file name="upload" label="File" size="50" />
			<s:submit cssClass="btn" value="上传" />
		</s:form>

		<table id="process_def_table">
		</table>
		<script type="text/javascript">
${symbol_dollar}(function(){
	var process_def_table = ${symbol_dollar}('${symbol_pound}process_def_table');

	 ${symbol_dollar}('${symbol_pound}deploy_process_form').form({  
	    success:function(data){
            layout_center_refreshTab('流程定义');
	    }
	}); 
	
	
	process_def_table.datagrid({
		url :'<%=basePath%>process/list-processes-json.action',
			idField : 'id',
			singleSelect:true,
			columns : [ [ {
				field : 'id',
				title : 'id'
			}, {
				field : 'name',
				title : '名称'
			}, {
				field : 'key',
				title : 'KEY'
			}, {
				field : 'version',
				title : '版本'
			}, {
				field : 'resouceName',
				title : 'XML',
				formatter : function(value, row, index) {
					var redicUrl = "<%=basePath%>process/process-resource.action?deploymentId=" + row.deploymentId + "&resourceName=" + row.resouceName;
					return "<a target='_blank' href='"+ redicUrl +"'>" + row.resouceName + "</a>";
				}
			}, {
				field : 'diagramResourceName',
				title : '图片',
				formatter : function(value, row, index) {
					var redicUrl = "<%=basePath%>process/process-resource.action?deploymentId="
													+ row.deploymentId
													+ "&resourceName="
													+ row.diagramResourceName;
											return "<a target='_blank'  href='"+ redicUrl +"'>"
													+ row.diagramResourceName
													+ "</a>";
										}
									}, {
										field : 'description',
										title : '描述'
									} ] ]
						});
			});
		</script>
	</div>
</body>
</html>