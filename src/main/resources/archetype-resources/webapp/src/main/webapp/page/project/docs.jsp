#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/page/scripts.jsp"/>
</head>
<body>
<!-- style="400px;300px;" -->
	<table id="project_docs_grid${symbol_dollar}{id}" class="easyui-datagrid" nowrap="false"
		fitColumns="true" singleSelect="true" fit="true" pageSize="50"
		pagination="true" toolbar="${symbol_pound}project_docs_toolbar${symbol_dollar}{id}" rownumbers="true" idField="id">
		<thead>
			<tr>
				<th data-options="field:'name'" width="20">文件名</th>
				<th data-options="field:'size'" width="20">大小</th>
				<th data-options="field:'uploadDate'" width="20">上传时间</th>
				<th
					data-options="field:'id',formatter:project_list_deletefile_formatter"
					width="20">操作</th>
			</tr>
		</thead>
	</table>
	
	<div id="project_docs_toolbar${symbol_dollar}{id}" style="padding:5px;height:auto">
	    <div style="margin-bottom:5px">
	        <input type="file" name="uploads" id="project_docs_upload_file${symbol_dollar}{id}" />
	    </div>
	</div>

<script type="text/javascript">

	/**
	 * 文件管理
	 */
	function project_list_deletefile_formatter(value, row, index){
		 var deleteLink = '<a href="javascript:;" onclick="project_list_ajaxdeletefile('+ value +','+ index +');">删除</a>';
	     var download = '<a href="javascript:;" onclick="project_list_doc_download('+ value +')">下载</a>';
	     return  deleteLink +  '&nbsp;&nbsp;'  + download ;
	}
	
    /**
     * ajax删除文件
     */
    function project_list_ajaxdeletefile(value, index){
        getJSON("${symbol_dollar}{basePath}/project/destroy-doc.action?projectId=${symbol_dollar}{id}&id=" + value,function(data){
        	if(data.errorInfo){
        		alert(data.errorInfo);
        		return;
        	}
        	${symbol_dollar}('${symbol_pound}project_docs_grid${symbol_dollar}{id}').datagrid('reload');
        });
    }

	
    
    /**
     * 下载文件
     */
    function project_list_doc_download(docId) {
        var downloadUrl = "${symbol_dollar}{basePath}/project/download-doc.action?projectId=${symbol_dollar}{id}&docId="
                + docId;
        ${symbol_dollar}.fileDownload(downloadUrl);
    }

    
    ${symbol_dollar}(function(){
        var grid = ${symbol_dollar}('${symbol_pound}project_docs_grid${symbol_dollar}{id}');
        ${symbol_dollar}('${symbol_pound}project_docs_grid${symbol_dollar}{id}').datagrid({
            url:'${symbol_dollar}{basePath}/project/docs-json.action?id=${symbol_dollar}{id}'
        });

        uploadfile("${symbol_pound}project_docs_upload_file${symbol_dollar}{id}",'${symbol_dollar}{basePath}/project/upload-docs.action',{
            'formData':{
                'id': '${symbol_dollar}{id}'
            },
            buttonText : '上传文件',
            'removeCompleted' : true,
            auto : true,
            onUploadSuccess : function(file, data, response){
                grid.datagrid('reload');
            }
        });
    });
</script>
</body>
</html>
