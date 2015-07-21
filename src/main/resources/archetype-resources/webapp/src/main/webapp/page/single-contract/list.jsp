#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="/page/scripts.jsp"/>

<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/jquery-download/download.js"></script>
<table id="singlecontract_list_table"
       nowrap="false"
       singleSelect="true"
       rownumbers="true"
       pagination="true"
       pageSize="20"
       fit="true"
       toolbar="${symbol_pound}singlecontract_list_toolbar"
        >
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true" rowspan="2">id</th>
        <th data-options="field:'contractName'" rowspan="2" width="150">名称</th>
        <th data-options="field:'serialNumber'" rowspan="2" width="100">合同编号</th>
        <th data-options="field:'generalContractAmount', formatter:amountNumberFormatter,align:'right'" rowspan="2" width="100">
            合同金额
        </th>
        <th data-options="field:'signDate'" rowspan="2" width="75">签定日期</th>
        <th colspan="2">工期</th>
        <th colspan="3">回款</th>
        <th data-options="field:'partAInfo', formatter:grid_show_orginfo_formatter"  rowspan="2" width="200">甲方</th>
        <th data-options="field:'supervisorInfo', formatter:grid_show_orginfo_formatter" rowspan="2" width="200">监理</th>
        <th data-options="field:'remark'" rowspan="2" >备注</th>
    </tr>
    <tr>
        <th data-options="field:'startDate'" width="75">开工日期</th>
        <th data-options="field:'finishDate'" width="75">完工日期</th>
        <th data-options="field:'totalReceiptInvoiceAmount',formatter:amountNumberFormatter,align:'right'" width="75">已开票</th>
        <th data-options="field:'totalReceiptAmount',formatter:amountNumberFormatter,align:'right'" width="75">已回款</th>
        <th data-options="field:'receivableRatio', formatter: function(value, row, index){return (value || 0) + '%'}" width="75">回款率</th>
    </tr>
    </thead>
</table>

<div id="singlecontract_list_toolbar">
    <input  class="easyui-searchbox" style="width:300px"
            data-options="searcher:single_contract_search_handler, prompt:'请输入',menu:'${symbol_pound}single_contract_search_menu'"/>

    <div id="single_contract_search_menu" style="width:120px">
        <div data-options="name:'name',iconCls:'icon-ok'">合同名</div>
    </div>
    <a href="javascript:;" onclick="singlecontract_list_excel_import_btn_handler();"
       iconCls="icon-upload" class="easyui-linkbutton">Excel导入单项合同</a>
    <a href="javascript:;" class="easyui-linkbutton" onclick="singlecontract_list_receiptinvoicebtn_handler();">收款管理</a>   
    <a href="javascript:;" onclick="single_contract_list_docs_manage_handler();" class="easyui-linkbutton">文件管理</a>
    
    <shiro:hasPermission name="singleContract:edit" >
     <a href="javascript:;" onclick="single_contract_list_add_one_handler();" iconCls="icon-add"
       class="easyui-linkbutton">添加</a>
    <a href="javascript:;" onclick="singlecontract_list_edit_btn_handler();" iconCls="icon-edit"
       class="easyui-linkbutton">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="singleContract:remove" >
    <a href="javascript:;" onclick="singlecontract_list_remove_btn_handler();" iconCls="icon-warn"
       class="easyui-linkbutton">删除</a>
    </shiro:hasPermission>   
       
</div>

<div id="singlecontract_import_contracts" title="导入单项合同" class="easyui-dialog" style="width:400px;height: 400px;" data-options="closed:true,resizable:true,modal:true">
    <h4>导入步骤</h4>

    <div class="alert alert-info">提示：请务必按导入步骤导入，以免导入失败</div>
    <p>
        一、单项合同所属项目 <br/>
        <select id="singlecontract_list_project_import" class="easyui-combobox" name="id"
                style="width:200px;">
            <s:iterator value="projects" id="project">
                <option value="${symbol_dollar}{project.id}">${symbol_dollar}{project.name}</option>
            </s:iterator>
        </select>
    </p>
    <p>
        二、<a id="singlecontract_list_download_tpl_btn"
             href="${symbol_dollar}{basePath}/single-contract/download-import-template.action?id=${symbol_dollar}{project.id}"
             target="_blank">下载模板</a>
    </p>

    <p> 三、向Excel模板写入内容</p>

    <p>四、上传<br/>

    <div id="singlecontract_list_import_upload_file"></div>
    <a class="easyui-linkbutton"
       data-options="iconCls:'icon-upload'"
       href="javascript:;"
       onclick="${symbol_dollar}('${symbol_pound}singlecontract_list_import_upload_file').uploadify('upload','*');">上传</a>&nbsp;
</div>



<!-- 文件管理 -->
<div id="single_contract_list_docs_dialog"  class="easyui-dialog"
		title="文件管理"
     resizable="true"
     closed="true"
     modal="true"
     style="width:600px;height:600px;">
    <table id="single_contract_list_downloadfile_table"
		   class="easyui-datagrid"
           nowrap="false"
           fit="true"
           singleSelect="true"
           rownumbers="true"
           pagination="true"
           pageSize="20"
           pageNumber="1"
           idField="id"
           toolbar="${symbol_pound}single_contract_list_downloadfile_table_toolbar"
          >
        <thead>
            <th data-options="field:'name'" width="250">文件名</th>
            <th data-options="field:'size'"  width="80">大小</th>
            <th data-options="field:'uploadDate'" width="80">上传时间</th>
            <th data-options="field:'id',formatter:single_contract_list_deletefile_formatter" width="80">操作</th>
        </thead>
    </table>
    <div id="single_contract_list_downloadfile_table_toolbar">
    	<input type="file" name="uploads" id="singlecontract_list_upload_input" />
    </div> 
</div>

<%--添加单个合同--%>
<div id="single_contract_list_add_one"
     class="easyui-dialog"
     title="添加单项合同"
     iconCls="icon-save"
     resizable="true"
     closed="true"
     modal="true"
     style="width:600px;height:600px;padding: 10px;"
        >
    <form id="single_contract_list_add_one_form" action="${symbol_dollar}{basePath}/single-contract/add-submit.action" method="post">
        <p>
            合同名称：<input name="contractName" class="easyui-validatebox" data-options="required:true" style="width: 300px;"/>
        </p>

        <p>合同编号：<input name="serialNumber" class="easyui-validatebox" data-options="required:true"/></p>

        <p>合同金额：<input name="generalContractAmount" type="text" class="easyui-numberbox"
                         data-options="min:0,groupSeparator:',',suffix:'元'" data-options="required:true"/></p>

        <p>签定日期：<input name="signDate" type="text" class="easyui-datebox" data-options="required:true"/></p>

        <p>开工日期：<input id="add_one_form_startdate" name="startDate" type="text" data-options="required:true"/> &nbsp;
            &nbsp;
            完工日期：<input id="add_one_form_finishdate" name="finishDate" type="text" data-options="required:true"/>
        </p>

        <p>甲方：<input id="add_one_form_parta" name="partAOrgId"/></p>

        <p>乙方：<input id="add_one_form_partb" name="partBOrgId"/></p>

        <p>
            监理单位：<input id="add_one_form_supervisor" name="supervisorOrgId"/>
        </p>

        <p>
            所属项目：<input id="add_one_form_constructings" name="projectId" data-options="required:true"/>
        </p>

        <p>备注：<textarea name="remark"></textarea>
        </p>
        <input id="add_one_form_id" name="id" type="hidden"/>
        <div id="single_contract_list_add_one_upload_file"></div>
        <a onclick="single_contract_list_add_one_form_submit();" href="javascript:;" class="easyui-linkbutton">提交</a>
    </form>
</div>




<jsp:include page="receiptinvoices.jsp"/>

<script type="text/javascript">

/**
 * 单项合同的文件管理
 */
function single_contract_list_deletefile_formatter(value, row, index){
    <shiro:hasPermission name="singleContract:del" >
	 var deleteLink = '<a href="javascript:;" onclick="singlecontract_list_ajaxdeletefile('+ value +','+ index +')">删除</a>';
	 </shiro:hasPermission>
	 <shiro:hasPermission name="singleContract:download" >
     var download = '<a href="javascript:;" onclick="singlecontract_list_doc_download('+ value +',' + datagrid_utils.selected_row_id('${symbol_pound}singlecontract_list_table') +')">下载</a>';
     </shiro:hasPermission>
     return  <shiro:hasPermission name="singleContract:del" >deleteLink +</shiro:hasPermission> '&nbsp;&nbsp;' <shiro:hasPermission name="singleContract:download"> + download     </shiro:hasPermission>;
}
<shiro:hasPermission name="singleContract:del" >
/**
 * ajax删除文件
 */
function singlecontract_list_ajaxdeletefile(value, index){
    getJSON("${symbol_dollar}{basePath}/single-contract/remove-doc.action?docId=" + value + "&contractId=" + datagrid_utils.selected_row_id(${symbol_dollar}('${symbol_pound}singlecontract_list_table')));
    datagrid_utils.delete_row('${symbol_pound}single_contract_list_downloadfile_table', index);
    return false;
}
</shiro:hasPermission>
<shiro:hasPermission name="singleContract:download" >
/**
 * 下载文件
 */
function singlecontract_list_doc_download(docId, contractId) {
    var downloadUrl = "${symbol_dollar}{basePath}/single-contract/download-doc.action?docId="
            + docId + "&contractId=" + contractId;
    ${symbol_dollar}.fileDownload(downloadUrl);
}
</shiro:hasPermission>
//单个合同的添加的提交
function single_contract_list_add_one_form_submit() {
    ${symbol_dollar}('${symbol_pound}single_contract_list_add_one_form').form('submit', {
        success: function (data) {
            data = evalJSON(data);
            if (!data.result) {
                alert('系统异常，请稍后再试');
                return;
            }
            var files = ${symbol_dollar}('${symbol_pound}single_contract_list_add_one_upload_file').find('.uploadify-queue-item');
            if (files && files.size() > 0) {
                ${symbol_dollar}('${symbol_pound}single_contract_list_add_one_upload_file').uploadify('upload', '*');
            } else{
                ${symbol_dollar}('${symbol_pound}single_contract_list_add_one_form').trigger('reset');
                ${symbol_dollar}('${symbol_pound}single_contract_list_add_one').dialog('close');

                ${symbol_dollar}('${symbol_pound}singlecontract_list_table').datagrid('reload');
            }


        }
    });
}



//导入合同的按钮处理
function singlecontract_list_excel_import_btn_handler() {
    ${symbol_dollar}('${symbol_pound}singlecontract_import_contracts').dialog('open');
}



//彻底删除单项合同
function singlecontract_list_remove_btn_handler() {
    var grid = ${symbol_dollar}('${symbol_pound}singlecontract_list_table');
    var id = datagrid_utils.selected_row_id(grid);
    if (!id) {
        imessager.alert('请选择单项合同');
        return;
    }
    if (!confirm('将会把与此合同相关的所有的发票，收款，文件都删除，确认删除？')) {
        return;
    }

    var url = '${symbol_dollar}{basePath}/single-contract/destroy.action?id=' + id;
    var data = getJSON(url);
    grid.datagrid('reload');
}

//添加单个合同
function single_contract_list_add_one_handler(){
    ${symbol_dollar}('${symbol_pound}single_contract_list_add_one').dialog('open');;
}

//搜索合同
function single_contract_search_handler(value,name){
    var param = {};
    param[name] = value;
    ${symbol_dollar}('${symbol_pound}singlecontract_list_table').datagrid('load', param);
}

//编辑某个合同
function singlecontract_list_edit_btn_handler(){
    var grid = ${symbol_dollar}('${symbol_pound}singlecontract_list_table');
    var row = datagrid_utils.selected_row(grid);
    if (!row) {
        imessager.alert('请选择单项合同');
        return;
    }
    ${symbol_dollar}('${symbol_pound}single_contract_list_add_one').dialog('open');
    var form = ${symbol_dollar}('${symbol_pound}single_contract_list_add_one_form');
    form.form('load', row);


    //项目
    proj_app.constructings_project_selector('${symbol_pound}add_one_form_constructings', row.project.id);

    if (row.partAInfo) {
        //甲方
        proj_app.all_owners_selector('${symbol_pound}add_one_form_parta',{
            contractInputName : 'partAContractId'
        }, row.partAInfo);
    }

    if (row.partBInfo) {
        //乙方
        proj_app.partiables_selector('${symbol_pound}add_one_form_partb',{
            contractInputName : 'partBContractId'
        }, row.partBInfo);
    }

    if (row.supervisorInfo) {
        //监理单位
        proj_app.supervisor_selector('${symbol_pound}add_one_form_supervisor',{
            contractInputName: 'supervisorPersonId'
        }, row.supervisorInfo);
    }

}

//单项合同管理
function single_contract_list_docs_manage_handler(){
	 var grid = ${symbol_dollar}('${symbol_pound}singlecontract_list_table');
     var row = datagrid_utils.selected_row(grid);
     if (!row) {
         imessager.alert('请选择单项合同');
         return;
     }
    var url = '${symbol_dollar}{basePath}/single-contract/docs-json.action?contractId=' + row.id;
	${symbol_dollar}('${symbol_pound}single_contract_list_docs_dialog').dialog('open');
	${symbol_dollar}('${symbol_pound}single_contract_list_downloadfile_table').datagrid({
		url :url
	});
     	
     
}




${symbol_dollar}(function () {
    proj_app.receipt_category_selector('${symbol_pound}single_contract_list_add_receipt_form_categories');

    uploadfile("${symbol_pound}singlecontract_list_add_receiptinvoice_upload_file",
            '${symbol_dollar}{basePath}/receipt-invoice/upload-docs.action', {
                buttonText: '上传附件',
                removeCompleted: false,
                onUploadStart : function(file) {
                    setUpload( "formData", {
                        'id': ${symbol_dollar}('${symbol_pound}singlecontract_list_receiptivoice_id').val(),
                        'contractId': datagrid_utils.selected_row_id('${symbol_pound}singlecontract_list_table')
                    });

                    setUpload('removeTimeout', 0);
                    setUpload('removeCompleted', true);
                    setUpload('onUploadComplete', function () {
                        //关闭弹出框
                        ${symbol_dollar}('${symbol_pound}singlecontract_list_add_receiptinvoice_dialog').dialog('close');
                    });



                    function setUpload(name,value){
                        var  upload = ${symbol_dollar}("${symbol_pound}singlecontract_list_add_receiptinvoice_upload_file");
                        upload.uploadify("settings",name, value );
                    }
                }
            });


    uploadfile('${symbol_pound}singlecontract_list_upload_input', '${symbol_dollar}{basePath}/single-contract/upload-docs.action', {
        'buttonText': '上传新文件',
        'auto': true,
        'removeCompleted': true,
        'onUploadSuccess': function (file, data, response) {
            ${symbol_dollar}('${symbol_pound}single_contract_list_downloadfile_table').datagrid('reload');
        }  ,
        'onUploadStart' : function(file) {
            ${symbol_dollar}("${symbol_pound}singlecontract_list_upload_input").uploadify("settings", "formData", {
            	id : datagrid_utils.selected_row_id('${symbol_pound}singlecontract_list_table')
            });
            
        }
    });

    proj_app.start_finished_date_box({
        startbox: ${symbol_dollar}('${symbol_pound}add_one_form_startdate'),
        finishedbox: ${symbol_dollar}('${symbol_pound}add_one_form_finishdate')
    });

    //项目
    proj_app.constructings_project_selector('${symbol_pound}add_one_form_constructings',null,{
        url :'${symbol_dollar}{basePath}/project/business-operationsabled.action'
    });

    //甲方
    proj_app.all_owners_selector('${symbol_pound}add_one_form_parta',{
        contractInputName : 'partAContractId'
    });

    //乙方
    proj_app.partiables_selector('${symbol_pound}add_one_form_partb',{
        contractInputName : 'partBContractId'
    });

    //监理单位
    proj_app.supervisor_selector('${symbol_pound}add_one_form_supervisor',{
        contractInputName: 'supervisorPersonId'
    });


    uploadfile("${symbol_pound}single_contract_list_add_one_upload_file", '${symbol_dollar}{basePath}/single-contract/upload-docs.action', {
        removeCompleted:true,
        'onQueueComplete': function(file, data, response){
            ${symbol_dollar}('${symbol_pound}single_contract_list_add_one_form').trigger('reset');
             ${symbol_dollar}('${symbol_pound}single_contract_list_add_one').dialog('close');

             ${symbol_dollar}('${symbol_pound}singlecontract_list_table').datagrid('reload');
        } ,
        'onUploadStart' : function(file) {
            ${symbol_dollar}("${symbol_pound}frameworkcontract_list_upload_input").uploadify("settings", "formData", {
                'id':${symbol_dollar}('${symbol_pound}add_one_form_id').val()
            });
        }
    });



    //展开单项合同
    var datagrid = ${symbol_dollar}('${symbol_pound}singlecontract_list_table');
    datagrid.datagrid({
        url:'${symbol_dollar}{basePath}/single-contract/list-json.action'
    });


	//单项合同的导入
    ${symbol_dollar}('${symbol_pound}singlecontract_list_project_import').combobox({
        onSelect: function (record) {
            var projectId = record.value;
            var url = '${symbol_dollar}{basePath}/single-contract/download-import-template.action?id=' + projectId;
            ${symbol_dollar}('${symbol_pound}singlecontract_list_download_tpl_btn').attr('href', url);
        }
    });

    uploadfile("${symbol_pound}singlecontract_list_import_upload_file", '${symbol_dollar}{basePath}/single-contract/import.action', {
        'onUploadSuccess': function(file, data, response){
            data = evalJSON(data);
            if (data.errorInfo) {
                imessager.alert(data.errorInfo);
                return;
            }
            ${symbol_dollar}('${symbol_pound}singlecontract_list_table').datagrid('reload');
        }
    });
});





</script>
