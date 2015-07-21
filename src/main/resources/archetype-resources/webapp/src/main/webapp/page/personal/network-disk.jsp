#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<jsp:include page="/page/scripts.jsp" />


<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/jquery-download/download.js"></script>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
	
	<table id="network_disk_docs" class="easyui-datagrid" nowrap="false"
		fitColumns="true" singleSelect="true" fit="true" pageSize="50"
		pagination="true" toolbar="${symbol_pound}toolbar" rownumbers="true" idField="id" >
		<thead>
			<tr>
				<th data-options="field:'name'" width="250">文件名</th>
				<th data-options="field:'size'" width="80">大小</th>
				<th data-options="field:'uploadDate'" width="80">上传时间</th>
				<th
					data-options="field:'id',formatter:network_disk_list_deletefile_formatter"
					width="80">操作</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<input type="file" name="uploads" id="network_disk_docs_upload_btn" />
		<input class="easyui-searchbox" style="width: 300px"
			data-options="searcher:network_disk_search_handler, prompt:'请输入',menu:'${symbol_pound}network_disk_search_menu'" />
		<div id="network_disk_search_menu" style="width: 120px">
			<div data-options="name:'name',iconCls:'icon-ok'">文件名</div>
		</div>
	</div>
	<script type="text/javascript">
	//搜索文件
	function network_disk_search_handler(value,name){
	    var param = {};
	    param[name] = value;
	    ${symbol_dollar}('${symbol_pound}network_disk_docs').datagrid('load', param);
	}
	
	
    /**
     * 文件管理
     */
    function network_disk_list_deletefile_formatter(value, row, index){
    	 var deleteLink = '<a href="javascript:;" onclick="network_disk_list_ajaxdeletefile('+ value +','+ index +');">删除</a>';
         var download = '<a href="javascript:;" onclick="network_disk_list_doc_download('+ value +')">下载</a>';
         //return  <shiro:hasPermission name="singleContract:del" >deleteLink</shiro:hasPermission>;
         return  deleteLink +  '&nbsp;&nbsp;'  + download ;
    }
    
    /**
     * ajax删除文件
     */
    function network_disk_list_ajaxdeletefile(value, index){
        getJSON("${symbol_dollar}{basePath}/personal/destroy-doc.action?id=" + value, function(data){
        	if(data.errorInfo){
        		alert(data.errorInfo);
        		return;
        	}
        	${symbol_dollar}('${symbol_pound}network_disk_docs').datagrid('reload');
        });
    }

	
    
    /**
     * 下载文件
     */
    function network_disk_list_doc_download(docId) {
        var downloadUrl = "${symbol_dollar}{basePath}/personal/download-doc.action?docId="
                + docId;
        ${symbol_dollar}.fileDownload(downloadUrl);
    }
     
    ${symbol_dollar}(function () {
        var grid = ${symbol_dollar}('${symbol_pound}network_disk_docs');
         grid.datagrid({
            url: '<%=basePath%>personal/docs-json.action'
			});

			uploadfile("${symbol_pound}network_disk_docs_upload_btn",
					'${symbol_dollar}{basePath}/personal/upload-docs.action', {
						'formData' : {
							'id' : '${symbol_dollar}{id}'
						},
						buttonText : '上传文件',
						'removeCompleted' : true,
						auto : true,
						onUploadSuccess : function(file, data, response) {
							grid.datagrid('reload');
						}
					});
		});
	</script>
</body>
</html>