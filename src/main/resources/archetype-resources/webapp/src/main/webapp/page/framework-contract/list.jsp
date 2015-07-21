#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<jsp:include page="/page/include.jsp"/>

<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/jquery-download/download.js"></script>
<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/file_upload/upload.js"></script>
<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/easyui/datagrid-detailview.js"></script>

<table id="frameworkcontract_list"
       class="easyui-datagrid"
       nowrap="false"
       fit="true"
       singleSelect="true"
       rownumbers="true"
       pagination="true"
       pageSize="30"
       pageNumber="1"
       idField="id"
        <shiro:hasPermission name="frameworkContract:edit">
        toolbar="${symbol_pound}frameworkContact_list_toolbar"
        </shiro:hasPermission>
       data-options="url:'${symbol_dollar}{basePath}/framework-contract/list-json.action'">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true" rowspan="2">id</th>
        <th data-options="field:'contractName',align:'left'" rowspan="2" width="150">名称</th>
        <th data-options="field:'serialNumber',align:'left'" rowspan="2" width="100">合同编号</th>
        <th data-options="field:'generalContractAmount',align:'right',formatter:amountNumberFormatter" rowspan="2">合同金额(元)</th>
        <th data-options="field:'signDate',align:'left'" rowspan="2" width="75">签定日期</th>
        <th colspan="2">工期</th>
        <th colspan="3">回款(元)</th>
        <th data-options="field:'partAInfo',formatter:grid_show_orginfo_formatter"  rowspan="2" width="100">甲方</th>
        <th data-options="field:'supervisorInfo',formatter:grid_show_orginfo_formatter" rowspan="2" width="100">监理</th>
        <th data-options="field:'remark'" rowspan="2" >备注</th>
    </tr>
    <tr>
        <th data-options="field:'startDate'" width="75">开工日期</th>
        <th data-options="field:'finishDate'" width="75">完工日期</th>
        <th data-options="field:'totalReceiptInvoiceAmount',align:'right',formatter: amountNumberFormatter" >已开票</th>
        <th data-options="field:'totalReceiptAmount',align:'right',formatter: amountNumberFormatter"  >已回款</th>
        <th data-options="field:'receivableRatio',align:'center',formatter: function(value, row, index){return (value || 0) + '%'}" width="50">回款率</th>
    </tr>
    </thead>
</table>

<%--菜单--%>
<div id="frameworkContact_list_toolbar">
    <input  class="easyui-searchbox" style="width:300px"
            data-options="searcher:framework_contract_search_handler, prompt:'请输入',menu:'${symbol_pound}framworkcontract_list_search_menu'"/>

    <div id="framworkcontract_list_search_menu" style="width:120px">
        <div data-options="name:'name',iconCls:'icon-ok'">合同名</div>
        <div data-options="name:'number',iconCls:'icon-ok'">合同编号</div>
    </div>
<shiro:hasPermission name="frameworkContract:edit">

    <a href="javascript:;" onclick="frameworkcontract_list_relevancebtn_handler();"  class="easyui-linkbutton">单项合同管理</a>
    <a href="javascript:;" onclick="frameworkcontract_list_receiptinvoicebtn_handler();"  class="easyui-linkbutton">收款管理</a>
    <a href="javascript:;"  id="frameworkcontract_list_manager_file_btn" class="easyui-linkbutton">文件管理</a>    
    <shiro:hasPermission name="frameworkContract:edit">
    <a href="javascript:;" onclick="frameworkcontract_list_addbtn_handler();" iconCls="icon-add" class="easyui-linkbutton">添加</a>
    <a href="javascript:;" onclick="frameworkcontract_list_editbtn_handler();" iconCls="icon-edit" class="easyui-linkbutton">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="frameworkContract:remove">
    <a href="javascript:;" onclick="frameworkcontract_list_deletebtn_handler();" iconCls="icon-remove"  class="easyui-linkbutton">删除</a>
	</shiro:hasPermission>
</shiro:hasPermission>

</div>

<div id="frameworkcontract_list_upload_dialog" class="easyui-dialog" title="文件上传" data-options="closed:true,resizable:true,modal:true" style="width:400px;height:auto;padding: 20px; ">

</div>
<shiro:hasPermission name="frameworkContract:edit">
<div id="framworkcontract_list_add_dialog"
     class="easyui-dialog"
     title="添加框架合同"
     iconCls="icon-save"
     resizable="true"
     closed="true"
     modal="true"
     style="width:600px;height:600px;padding: 10px;"
     >
    <form id="framworkcontract_list_add_form" action="${symbol_pound}" method="post">
        <p>
            合同名称：<input name="contractName" class="easyui-validatebox" data-options="required:true" style="width: 300px;"/>
        </p>

        <p>合同编号：<input name="serialNumber" class="easyui-validatebox" data-options="required:true"/></p>

        <p>总包合同金额：<input name="generalContractAmount" type="text" class="easyui-numberbox"
                         data-options="min:0,groupSeparator:',',suffix:'元'"/></p>

        <p>签定日期：<input name="signDate" type="text" class="easyui-datebox" data-options="required:true,editable:false"/></p>

        <p>开工日期：<input id="framworkcontract_list_add_form_startdate" name="startDate" type="text" data-options="required:true,editable:false"/> &nbsp;
            &nbsp;
       	     完工日期：<input id="framworkcontract_list_add_form_enddate" name="finishDate" type="text" data-options="required:true,editable:false"/>
        </p>

        <p>甲方：<input id="framworkcontract_list_add_form_parta" name="partAOrgId" data-options="editable:false"/></p>

        <p>乙方：<input id="framworkcontract_list_add_form_partb" name="partBOrgId" data-options="editable:false"/></p>

        <p>
          	  监理单位：<input id="framworkcontract_list_add_form_supervisor" name="supervisorOrgId" data-options="editable:false" />
        </p>

        <p>
            	所属项目：<input id="framworkcontract_list_add_form_projectid" name="projectId" data-options="required:true" style="width: 350px;"/>
        </p>

        <p>备注：<textarea name="remark"></textarea>
        </p>
        <input id="framworkcontract_list_add_form_contractid" name="id" type="hidden"/>
    </form>
</div>
</shiro:hasPermission>


<shiro:hasPermission name="frameworkContract:edit">
    <div id="framworkcontract_list_edit_dialog"
         class="easyui-dialog"
         title="编辑框架合同"
         iconCls="icon-edit"
         resizable="true"
         closed="true"
         modal="true"
         style="width:600px;height:600px;padding: 10px;">
        <form id="framworkcontract_list_edit_form" action="${symbol_pound}" method="post">
            <p>
                合同名称：<input name="contractName" class="easyui-validatebox" data-options="required:true" style="width: 300px;"/>
            </p>

            <p>合同编号：<input name="serialNumber" class="easyui-validatebox" data-options="required:true"/></p>

            <p>总包合同金额：<input name="generalContractAmount" type="text" class="easyui-numberbox"
                             data-options="min:0,groupSeparator:',',suffix:'元'"/></p>

            <p>签定日期：<input name="signDate" type="text" class="easyui-datebox" data-options="required:true,editable:false"/></p>

            <p>开工日期：<input id="framworkcontract_list_edit_form_startdate" name="startDate" type="text" data-options="required:true,editable:false"/> &nbsp;
                &nbsp;
                完工日期：<input id="framworkcontract_list_edit_form_enddate" name="finishDate" type="text" data-options="required:true,editable:false"/>
            </p>

            <p>甲方：<input id="framworkcontract_list_edit_form_parta" name="partAOrgId" data-options="editable:false"/></p>

            <p>乙方：<input id="framworkcontract_list_edit_form_partb" name="partBOrgId" data-options="editable:false"/></p>

            <p>
                监理单位：<input id="framworkcontract_list_edit_form_supervisor" name="supervisorOrgId" data-options="editable:false" />
            </p>

            <p>
                所属项目：<input id="framworkcontract_list_edit_form_projectid" name="projectId" data-options="required:true" style="width: 350px;"/>
            </p>

            <p>备注：<textarea name="remark"></textarea>
            </p>
            <input id="framworkcontract_list_edit_form_contractid" name="id" type="hidden"/>
        </form>
    </div>
</shiro:hasPermission>

<jsp:include page="receiptinvoices.jsp"/>

<div id="framworkcontract_list_relevance_dialog"
     class="easyui-dialog"
     title="关联单项合同"
     iconCls="icon-save"
     resizable="true"
     closed="true"
     modal="true"
     style="width:700px;height:600px;">

    <table id="framworkcontract_list_relevance_singlecontract_list"
           class="easyui-datagrid"
           nowrap="false"
           fitColumns="true"
           singleSelect="false"
           rownumbers="true"
           pagination="true"
           pageSize="10"
           pageNumber="1"
           idField="id"
           title="未关联单项合同"
           toolbar="${symbol_pound}framworkcontract_list_relevance_singlecontract_list_toolbar"
           url="${symbol_dollar}{basePath}/single-contract/list-outof-framework.action"
           style="height:250px;">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">id</th>
            <th data-options="field:'contractName'" width="150">名称</th>
            <th data-options="field:'serialNumber'"  width="100">合同编号</th>
            <th data-options="field:'signDate'"  width="75">签定日期</th>
            <th data-options="field:'partAInfo', formatter:grid_show_orginfo_formatter"  width="100">甲方</th>
            <th data-options="field:'supervisorInfo', formatter:grid_show_orginfo_formatter"   width="100">监理</th>
        </tr>
        </thead>
    </table>
    <%--已关联的单项合同--%>
    <table id="framworkcontract_list_relevance_singlecontract_list2"
           class="easyui-datagrid"
           nowrap="false"
           singleSelect="false"
           rownumbers="true"
           pagination="true"
           pageSize="10"
           pageNumber="1"
           idField="id"
           title="已关联单项合同"
           url="${symbol_dollar}{basePath}/framework-contract/list-json-of-relevance-single.action"
           toolbar="${symbol_pound}framworkcontract_list_relevance_singlecontract_list2_toolbar"
           style="height:240px">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">id</th>
            <th data-options="field:'contractName'" width="150">名称</th>
            <th data-options="field:'serialNumber'"  width="100">合同编号</th>
            <th data-options="field:'signDate'"  width="75">签定日期</th>
            <th data-options="field:'partAInfo', formatter:grid_show_orginfo_formatter"  width="100">甲方</th>
            <th data-options="field:'supervisorInfo', formatter:grid_show_orginfo_formatter"   width="100">监理</th>
        </tr>
        </thead>
    </table>

    <div id="framworkcontract_list_relevance_singlecontract_list_toolbar">
      <input id="framworkcontract_list_relevance_singlecontract_list_toolbar_search" class="easyui-searchbox" style="width:300px"
               data-options="searcher: framworkcontract_list_relevance_singlecontract_list_toolbar_search_handler,
               prompt:'请输入',
               menu:'${symbol_pound}framworkcontract_list_relevance_singlecontract_list_toolbar_search_menu'"/>
        <a id="framworkcontract_list_relevance_singlecontract_list_toolbar_btn" class="easyui-linkbutton" >添加关联</a>

        <div id="framworkcontract_list_relevance_singlecontract_list_toolbar_search_menu" style="width:120px">
            <div data-options="name:'contractName',iconCls:'icon-ok'">合同名</div>
            <div data-options="name:'serialNumber',iconCls:'icon-ok'">合同编号</div>
        </div>


    </div>

    <div id="framworkcontract_list_relevance_singlecontract_list2_toolbar">
        <a id="framworkcontract_list_relevance_singlecontract_list2_toolbar_btn" class="easyui-linkbutton">取消关联</a>
    </div>

</div>

<%--下载文件列表--%>
<div id="framworkcontract_list_download_dialog" class="easyui-dialog" title="管理合同文件"
     data-options="closed:true,resizable:true,modal:true" style="width:600px;height:400px; ">
    <table id="framworkcontract_list_downloadfile_table"
           class="easyui-datagrid"
           nowrap="false"
           fit="true"
           fitColumns="true"
           singleSelect="true"
           rownumbers="true"
           pagination="true"
           pageSize="20"
           pageNumber="1"
           idField="id"
           toolbar="${symbol_pound}framworkcontract_list_downloadfile_table_toolbar"
          >
        <thead>
            <th data-options="field:'name'"  width="250">文件名</th>
            <th data-options="field:'size'"  width="80">大小</th>
            <th data-options="field:'uploadDate'" width="80">上传时间</th>
            <th data-options="field:'id',formatter:frameworkcontract_list_deletefile_formatter" width="80">操作</th>
        </thead>
    </table>

    <div id="framworkcontract_list_downloadfile_table_toolbar">
        <input  class="easyui-searchbox" style="width:300px"
                data-options="searcher:framworkcontract_list_downloadfile_table_toolbar_search_handler, prompt:'请输入文件名的关键字'"/>
        <input type="file" name="uploads" id="frameworkcontract_list_upload_input" />
    </div>
</div>



<script type="text/javascript">
    ${symbol_dollar}(function() {
        proj_app.start_finished_date_box({
            startbox: ${symbol_dollar}('${symbol_pound}framworkcontract_list_add_form_startdate'),
            finishedbox: ${symbol_dollar}('${symbol_pound}framworkcontract_list_add_form_enddate')
        });

        //项目
        proj_app.constructings_project_selector('${symbol_pound}framworkcontract_list_add_form_projectid');

        //甲方
        proj_app.all_owners_selector('${symbol_pound}framworkcontract_list_add_form_parta',{
            contractInputName : 'partAContractId'
        });

        //乙方
        proj_app.partiables_selector('${symbol_pound}framworkcontract_list_add_form_partb',{
            contractInputName : 'partBContractId'
        });

        //监理单位
        proj_app.supervisor_selector('${symbol_pound}framworkcontract_list_add_form_supervisor',{
            contractInputName: 'supervisorPersonId'
        });


        proj_app.start_finished_date_box({
            startbox: ${symbol_dollar}('${symbol_pound}framworkcontract_list_edit_form_startdate'),
            finishedbox: ${symbol_dollar}('${symbol_pound}framworkcontract_list_edit_form_enddate')
        });

        //项目
        proj_app.constructings_project_selector('${symbol_pound}framworkcontract_list_edit_form_projectid');

        //甲方
        proj_app.all_owners_selector('${symbol_pound}framworkcontract_list_edit_form_parta',{
            contractInputName : 'partAContractId'
        });

        //乙方
        proj_app.partiables_selector('${symbol_pound}framworkcontract_list_edit_form_partb',{
            contractInputName : 'partBContractId'
        });

        //监理单位
        proj_app.supervisor_selector('${symbol_pound}framworkcontract_list_edit_form_supervisor',{
            contractInputName: 'supervisorPersonId'
        });

        var unrelevance_single_contract = ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_singlecontract_list');
        var relevance_single_contract = ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_singlecontract_list2');
        //处理取消关联
        ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_singlecontract_list2_toolbar_btn').click(function(){
            var rows = ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_singlecontract_list2').datagrid('getSelections');
            if(!rows || rows.length === 0){
                imessager.alert('请选择一条数据');
                return;
            }
            ${symbol_dollar}.messager.confirm('提示','本次操作将导致选择的单项合同与框架合同取消关联，请确认?',function(r){
                if(!r){
                    return;
                }
                var singleIds = getSelectedRowsProperty(rows,'id');
                var frameworkId = get_selected_framework_id();
                ${symbol_dollar}.ajax('${symbol_dollar}{basePath}/framework-contract/relevance-single!unRelevance.action',{
                    type: "POST",
                    traditional:true,
                    dataType : 'json',
                    data:{frameworkId:frameworkId, singles:singleIds},
                    success : function(result){
                        //刷新两个datagrid的数据
                        imessager.ajaxInfo(result,function(){
                            unrelevance_single_contract.datagrid('reload');
                            relevance_single_contract.datagrid('reload');
                            relevance_single_contract.datagrid('clearSelections');
                            ${symbol_dollar}('${symbol_pound}frameworkcontract_list').datagrid('reload');
                        });
                    }
                });
            });//end of confirm
        });

        //添加框架合同与单项合同的关联
        ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_singlecontract_list_toolbar_btn').click(function(){
            var selectedSinglerows = ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_singlecontract_list').datagrid('getSelections');
            if(!selectedSinglerows || selectedSinglerows.length == 0){
                imessager.alert('请选择一条数据');
                return;
            }

            var singleIds = getSelectedRowsProperty(selectedSinglerows, 'id');
            var frameworkId = get_selected_framework_id();
            ${symbol_dollar}.ajax('${symbol_dollar}{basePath}/framework-contract/relevance-single.action',{
                type: "POST",
                traditional:true,
                dataType : 'json',
                data:{frameworkId: frameworkId,singles:singleIds},
                success : function(result){
                    //刷新两个datagrid的数据
                    imessager.ajaxInfo(result,function(){
                        unrelevance_single_contract.datagrid('reload');
                        unrelevance_single_contract.datagrid('clearSelections');
                        relevance_single_contract.datagrid('reload');
                        ${symbol_dollar}('${symbol_pound}frameworkcontract_list').datagrid('reload');
                    });
                }
            });// /ajax
        });


        function getSelectedRowsProperty(rows,propertyName){
            var single_ids = [];
            if(rows instanceof Array){
                for(var i=0; i<rows.length; i++){
                    single_ids.push(rows[i][propertyName]);
                }
                return single_ids;
            }else{
                return;
            }
        }

        //合同文件的管理
        ${symbol_dollar}('${symbol_pound}frameworkcontract_list_manager_file_btn').click(function () {
            var frameworkId = get_selected_framework_id();
            if (!frameworkId) {
                alert('请选择框架合同！');
                return;
            }
            ${symbol_dollar}('${symbol_pound}framworkcontract_list_download_dialog').dialog('open');
            var datagrid = ${symbol_dollar}('${symbol_pound}framworkcontract_list_downloadfile_table');
            datagrid.datagrid({
                url :  '${symbol_dollar}{basePath}/framework-contract/docs-json.action?frameworkId=' + frameworkId
            });

        });


        function get_selected_framework_id(){
            return datagrid_utils.selected_row_id(${symbol_dollar}('${symbol_pound}frameworkcontract_list'));
        }

        //上传文件
        var upload_doc_url = "${symbol_dollar}{basePath}/framework-contract/upload-docs.action";
        uploadfile('${symbol_pound}frameworkcontract_list_upload_input', upload_doc_url, {
            'buttonText': '上传新文件',
            'auto': true,
            'removeCompleted': true,
            'onUploadSuccess': function (file, data, response) {
                ${symbol_dollar}('${symbol_pound}framworkcontract_list_downloadfile_table').datagrid('reload');
            }  ,
            'onUploadStart' : function(file) {
                ${symbol_dollar}("${symbol_pound}frameworkcontract_list_upload_input").uploadify("settings", "formData", {
                    frameworkId : get_selected_framework_id()
                });
            }
        });




    }); // ${symbol_dollar}(function());

    <%--搜索框架合同--%>
    function framework_contract_search_handler(value,name){
        var param = {};
        param[name] = value;
        ${symbol_dollar}('${symbol_pound}frameworkcontract_list').datagrid('load', param);
    }
    //添加框架合同
    function frameworkcontract_list_addbtn_handler(){
        var dialog = ${symbol_dollar}('${symbol_pound}framworkcontract_list_add_dialog');
        var form = ${symbol_dollar}('${symbol_pound}framworkcontract_list_add_form');
        form.form('reset');
        dialog.dialog({
            buttons:[{
                text:'保存',
                handler:function(obj){

                    if(!form.form('validate')) return;
                    ${symbol_dollar}.post("${symbol_dollar}{basePath}/framework-contract/save.action", form.serialize(), function(response) {
                        var response_data = eval(response);
                        if(response_data.errorInfo == null && response_data.result == true){
                            dialog.dialog('close');
                            ${symbol_dollar}('${symbol_pound}frameworkcontract_list').datagrid('reload');
                        }else{
                            if(response_data.result == false){
                                imessager.alert('保存失败,错误信息：'+ response_data.errorInfo);
                            }
                        }
                    },'json').error(function() {
                                imessager.alert('系统异常，请联系管理员');
                            });
                }
            },{
                text:'取消',
                handler:function(){
                    dialog.dialog('close');
                }
            }]
        });
        dialog.dialog('open');
    }

    //编辑框架合同
    function frameworkcontract_list_editbtn_handler(){
        var row = ${symbol_dollar}('${symbol_pound}frameworkcontract_list').datagrid('getSelected');
        if (!row) {
            imessager.alert('请选择一条数据');
            return;
        }
        var dialog = ${symbol_dollar}('${symbol_pound}framworkcontract_list_edit_dialog');

        var form = ${symbol_dollar}('${symbol_pound}framworkcontract_list_edit_form');
        form.form('reset');
        initForm(form,row);

        dialog.dialog({
            buttons:[{
                text:'保存',
                handler:function(obj){
                    ${symbol_dollar}.post("${symbol_dollar}{basePath}/framework-contract/save.action", form.serialize(),function (response) {
                        var response_data = eval(response);
                        if (response_data.errorInfo == null && response_data.result == true) {
                            dialog.dialog('close');
                            ${symbol_dollar}('${symbol_pound}frameworkcontract_list').datagrid('reload');
                        } else {
                            if (response_data.result == false) {
                                imessager.alert('保存失败,错误信息：' + response_data.errorInfo);
                            }
                        }
                    }, 'json').error(function () {
                                imessager.alert('系统异常，请联系管理员');
                            });
                }
            },{
                text:'取消',
                handler:function(){
                    dialog.dialog('close');
                }
            }]
        });
        dialog.dialog('open');

        function initForm(form,row){
            //其他数据自动填充
            form.form('load', row);

            //项目
            proj_app.constructings_project_selector('${symbol_pound}framworkcontract_list_add_form_projectid', row.project.id);

            //甲方
            if (row.partAInfo) {
                proj_app.all_owners_selector('${symbol_pound}framworkcontract_list_add_form_parta',{
                    contractInputName : 'partAContractId'
                }, row.partAInfo);
            }

            if (row.partBInfo) {
                //乙方
                proj_app.partiables_selector('${symbol_pound}framworkcontract_list_add_form_partb',{
                    contractInputName : 'partBContractId'
                }, row.partBInfo);
            }

            if (row.supervisorInfo) {
                //监理单位
                proj_app.supervisor_selector('${symbol_pound}framworkcontract_list_add_form_supervisor',{
                    contractInputName: 'supervisorPersonId'
                }, row.supervisorInfo);
            }

        }

    }

    //删除框架合同
    function frameworkcontract_list_deletebtn_handler(){
        var row = datagrid_utils.selected_row('${symbol_pound}frameworkcontract_list');
        if(!row) {
           imessager.alert('请选择一条数据');
           return;
        }

        ${symbol_dollar}.messager.confirm('警告', '删除框架合同会解除与之关联的单项合同，请确认?', function(r){
            if (r){
                ${symbol_dollar}.post('${symbol_dollar}{basePath}/framework-contract/remove.action',{frameworkId: row.id},function(result){
                    imessager.ajaxInfo(result,function(){
                        datagrid_utils.delete_grid_selected_row('${symbol_pound}frameworkcontract_list');
                    });
                },'json');
            }
        });
    }

    //搜索文件
    function framworkcontract_list_downloadfile_table_toolbar_search_handler(value, name){
        ${symbol_dollar}('${symbol_pound}framworkcontract_list_downloadfile_table').datagrid('load', {
            name : value
        });
    }


    //搜索未关联框架合同的单项合同
    function framworkcontract_list_relevance_singlecontract_list_toolbar_search_handler(value, name){
        var unrelevance_single_contract = ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_singlecontract_list');
        var param = {};
        param[name] = value;
        unrelevance_single_contract.datagrid('load', param);
    }

    //关联单项合同
    function frameworkcontract_list_relevancebtn_handler(){
        //选择一条数据，弹出选择框
        var row = ${symbol_dollar}('${symbol_pound}frameworkcontract_list').datagrid('getSelected');
        if (!row) {
            imessager.alert('请选择一条数据');
            return;
        }

        ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_singlecontract_list2').datagrid('load',{frameworkId:row.id});
        ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_singlecontract_list').datagrid('load',{frameworkId:row.id});
        ${symbol_dollar}('${symbol_pound}framworkcontract_list_relevance_dialog').dialog('open');
    }
   
    //删除文件
    function frameworkcontract_list_deletefile_formatter(value, row, index){
    	<shiro:hasPermission name="frameworkContract:del">
        var deleteLink = '<a href="javascript:;" onclick="frameworkcontract_list_ajaxdeletefile('+ value +','+ index +')">删除</a>';
        </shiro:hasPermission>

    	<shiro:hasPermission name="frameworkContract:download">
        var download = '<a href="javascript:;" onclick="frameworkcontract_list_receiptvoice_doc_download('+ value +',' + datagrid_utils.selected_row_id('${symbol_pound}frameworkcontract_list') +')">下载</a>';
        </shiro:hasPermission>
        return <shiro:hasPermission name="frameworkContract:del">deleteLink+ </shiro:hasPermission> '&nbsp;&nbsp;'<shiro:hasPermission name="frameworkContract:download"> +  download</shiro:hasPermission>;
    }
    
    //ajax删除文件
    function frameworkcontract_list_ajaxdeletefile(value, index){
        getJSON("${symbol_dollar}{basePath}/framework-contract/remove-doc.action?docId=" + value + "&frameworkId=" + datagrid_utils.selected_row_id(${symbol_dollar}('${symbol_pound}frameworkcontract_list')));
        datagrid_utils.delete_row('${symbol_pound}framworkcontract_list_downloadfile_table', index);
        return false;
    }

</script>
