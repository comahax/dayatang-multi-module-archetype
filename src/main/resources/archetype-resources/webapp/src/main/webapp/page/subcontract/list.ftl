<!DOCTYPE html>
<html>
<head>
<#include "/page/scripts.ftl"/>
</head>
<body class="easyui-layout" fit="true">
<div data-options="region:'north',title:'分包合同列表',split:true" >
    <table id="subcontracts" style="height: 350px;" ></table>
    <div id="subcontracts_toolbar">
        <a class="easyui-linkbutton" href="javascript:;" onclick="add_subcontract();">添加分包合同</a>
        <a class="easyui-linkbutton" href="javascript:;" onclick="del_subcontract();">删除分包合同</a>
        <a class="easyui-linkbutton" href="javascript:;" onclick="manage_doc();">管理文件</a>
    </div>

</div>
<div data-options="region:'center'"  >
    <div id="invoice_layout" class="easyui-layout" fit="true"  >
        <div data-options="region:'west',collapsed:false,split:true" title="分包发票列表" style="width:400px">
             <table id="invoices"></table>
        </div>
        <div data-options="region:'center',split:true"   title="分包付款">
             <table id="payments"></table>
        </div>
    </div>
</div>

<div class="easyui-dialog" title="添加分包合同" id="add_subcontract_dialog" closed="true" modal="true" style="width:400px;">
    <form id="add_subcontract_form" action="${basePath}/subcontract/add.action" method="post">
        <p>
            合同名称：<input name="contractName" class="easyui-validatebox" data-options="required:true" style="width: 300px;"/>
        </p>
        <p>合同编号：<input name="serialNumber" class="easyui-validatebox" data-options="required:true"/></p>
        <p>合同金额：<input name="generalContractAmount" type="text" class="easyui-numberbox"
                       data-options="min:0,groupSeparator:',',suffix:'元'" data-options="required:true"/></p>
        <p>
            支付方式：<textarea name="modeOfPayment"  class="easyui-validatebox" data-options="required:true"></textarea>
        </p>
        <p>
            分包比例：<input
                name="subcontractRatio"
                class="easyui-numberspinner" style="width:80px;"
                data-options="required:true, max:100,increment:10, suffix:'%'" type="text"/>
        </p>

        <p>签定日期：<input name="signDate" type="text" class="easyui-datebox" data-options="required:true"/></p>

        <p>开工日期：<input id="add_one_form_startdate" name="startDate" type="text" data-options="required:true"/> &nbsp;
            &nbsp;
            完工日期：<input id="add_one_form_finishdate" name="finishDate" type="text" data-options="required:true"/>
        </p>

        <p>甲方：<input id="add_one_form_parta" name="partAId"/></p>

        <p>乙方：<input id="add_one_form_partb" name="partBId"/></p>
        <p>
            所属项目：<input id="add_subcontract_form_projectId" name="projectId" data-options="required:true"/>
        </p>
        <input type="file" name="uploads" id="subcontract_upload_input" />

        <a class="easyui-linkbutton" href="javascript:;" onclick="subcontract_add_form_submit();">提交</a>

    </form>
</div>

<div id="manage_doc_dialog" title="文件管理" class="easyui-dialog"  closed="true"  modal="true" style="width:500px;height: 350px;">
    <table id="doc_table"></table>
    <div id="doc_table_toolbar">
        <input type="file" name="uploads" id="manage_doc_upload_input" />
    </div>
</div>

<div id="invoice_add_dialog" class="easyui-dialog" closed="true" modal="true"
     style="width: 400px; height: 400px;" title="添加分包发票">
    <form id="invoice_add_form" method="post"
          action="${basePath}/subcontract-invoice/add.action">
        开票日期：<input name="billingDate" type="text" class="easyui-datebox" required="required"/>
        <p/>
        发票编号：<input name="serialNumber" class="easyui-validatebox" data-options="required:true"/>
        <p/>
        金额：<input name="amount" type="text" class="easyui-numberbox"
                  data-options="min:0,groupSeparator: ',',suffix:'元'"/>
        <p/>
        <input type="hidden" name="subContractId" id="invoice_add_form_subcontractid">
        <input type="file" name="uploads" id="invoice_add_form_upload_file"/>
        <a onclick="invoice_add_form_submit();"
           href="javascript:;"
           class="easyui-linkbutton">添加</a>
    </form>
</div>

<div id="payments_add_dialog" class="easyui-dialog" closed="true" modal="true"   style="width: 400px; height: 400px;" title="添加付款">
    <form id="payments_add_form" method="post"
          action="${basePath}/subcontract-payment/add.action">
        付款金额：<input name="amount" type="text" class="easyui-numberbox"
                    data-options="min:0,groupSeparator: ',',suffix:'元'"/>
        <p/>
        付款日期：<input name="spendDate" type="text" class="easyui-datebox" required="required"/>
        <p/>
        备注：<textarea name="remark"></textarea>
        <p/>
        <input name="invoiceId" type="hidden" id="payments_add_form_invoiceId" />
        <a onclick="payments_add_form_submit();"
           href="javascript:;"
           class="easyui-linkbutton">添加</a>
    </form>

</div>


<script type="text/javascript">
$(function () {
    $('#subcontracts').datagrid({
        nowrap:false,
        singleSelect:true,
        pagination:true,
        url: '${basePath}/subcontract/list-json.action',
        toolbar:'#subcontracts_toolbar',
        onClickRow: function(rowIndex, rowData){
            var id = rowData.id;
            $('#invoices').datagrid({
                url : '${basePath}/subcontract-invoice/list-json.action?subContractId=' + id,
                pagination:true,
                singleSelect:true,
                nowrap:false,
                fit:true,
                toolbar:[
                    {   text:'添加',
                        iconCls:'icon-add',
                        handler : function(){
                            $('#invoice_add_form_subcontractid').val(id);
                            $('#invoice_add_dialog').dialog('open');
                        }
                    },{
                        text:'删除',
                        iconCls:'icon-remove',
                        handler: function(){
                            if(!confirm('确认删除？')){
                                return;
                            }
                            var id = datagrid_utils.selected_row_id('#invoices');
                            if(!id){
                                alert('请选择分包发票！');
                                return;
                            }
                            var url = '${basePath}/subcontract-invoice/destory.action?id=' + id;
                            getJSON(url, function(data){
                                if(data.errorInfo){
                                    alert(data.errorInfo);
                                    return;
                                }
                                $('#invoices').datagrid('reload');
                                $('#payments').datagrid('reload');
                            });
                        }
                    } ,{
                        text:'付款',
                        handler:function(){
                            var id = datagrid_utils.selected_row_id('#invoices');
                            if(!id){
                                alert('请选择分包发票！');
                                return;
                            }
                            $('#payments_add_form_invoiceId').val(id);
                            $('#payments_add_dialog').dialog('open');
                        }
                    }
                ],
                columns:[[
                    {field:'billingDate',title:'开票日期',width:70},
                    {field:'serialNumber',title:'发票编号',width:100},
                    {field:'amount',title:'金额(元)',width:100,align:'right',formatter:amountNumberFormatter},
                    {field:'subcontractPaymentTotalAmount', title:'已付(元)',align:'right',formatter:amountNumberFormatter} ,
                    {field:'docs',title:'文件',formatter:invoices_table_doc_formatter},
                    {field:'id',hidden:true}
                ]]
            });
            $('#payments').datagrid({
                url:'${basePath}/subcontract-payment/list-json-of-subcontract.action?subcontractId=' + id,
                nowrap:false,
                fit:true,
                columns:[[
                    {field:'invoice',title:'发票',width:150,formatter:function(value, row, index){
                        return value.serialNumber;
                    }},
                    {field:'spendDate',title:'支出时间',width:100},
                    {field:'amount',title:'金额',width:100,align:'right'},
                    {field:'remark',title:'备注',width:100},
                    {field:'invoice',hidden:true},
                    {field:'id',title:'操作',width:90, formatter:invoices_payment_id_formatter}
                ]]
            });

        },
        columns: [
            [
                {field: 'contractName', title: '合同名', width: 200},
                {field: 'serialNumber', title: '合编号', width: 100},
                {field: 'modeOfPayment',title:'支付方式',width:100},
                {field: 'generalContractAmount', title: '合同金额', width: 90, formatter:amountNumberFormatter,align:'right'},
                {field: 'subcontractInvoiceTotalAmount', title: '发票已开总额(元)', width: 90, formatter:amountNumberFormatter,align:'right'},
                {field: 'subcontractPaymentTotalAmount', title: '已付款总额(元)', width: 90, formatter:amountNumberFormatter,align:'right'},
                {field: 'subcontractRatio',title:'分包比例',width:100,align:'right', formatter: function(value){return value+'%'}},
                {field: 'remark', title: '备注', width: 100},
                {field: 'partAInfo', title: '甲方', width: 150,formatter:grid_show_orginfo_formatter},
                {field: 'partBInfo', title: '乙方', width: 150,formatter:grid_show_orginfo_formatter} ,
                {field: 'docs', title:'文件',formatter:doc_formatter}
            ]
        ]
    });


    //初始化合作单位
    proj_app.cooperation_selector('#project_details_cooperation_selector');

    //初始化添加分包的表单
    //甲方
    proj_app.partiables_selector('#add_one_form_parta',{
        contractInputName : 'partAContactId'
    });

    //乙方
    proj_app.cooperator_selector('#add_one_form_partb',{
        contractInputName : 'partBContactId'
    });

    proj_app.start_finished_date_box({
        startbox: $('#add_one_form_startdate'),
        finishedbox: $('#add_one_form_finishdate')
    });

    //项目
    proj_app.constructings_project_selector('#add_subcontract_form_projectId');


    $('#add_subcontract_form').form({
        success:function(data){
            data = evalJSON(data);
            if(data.errorInfo){
                alert(data.errorInfo);
                return;
            }

            if($('#add_subcontract_form').find('.uploadify-queue-item').size() > 0){
                upload(data.id);
            }else{
                refresh();
            }
            return;

            function upload(id){
                var upload_input = $('#subcontract_upload_input');
                upload_input.uploadify("settings", "formData", {
                    id: id
                });
                upload_input.uploadify("settings", 'onQueueComplete',function(queueData){
                    refresh();
                });
                upload_input.uploadify('upload', '*');
            }

            function refresh(){
                $('#add_subcontract_form').trigger('reset');
                $('#add_subcontract_dialog').dialog('close');
                $('#subcontracts').datagrid('reload');
            }
        }
    });

    uploadfile("#subcontract_upload_input",'${basePath}/subcontract/upload-docs.action', {
                buttonText: '上传附件',
                removeCompleted: false
    });

    //分包合同的文件管理
    uploadfile('#doc_table_upload', '${basePath}/subcontract/upload-docs.action',{
        buttonText: '上传文件',
        removeCompleted: true,
        'auto': true,
        'onUploadStart': function (file) {
            var contractId = datagrid_utils.selected_row_id($('#subcontracts'));
            var  upload = $("#doc_table_upload");
            upload.uploadify("settings",'formData', {
                id:contractId
            } );
        },onUploadSuccess : function(file, data, response){
            $('#doc_table').datagrid('reload');
        }
    }) ; // uploadfile

    //添加分包发票表单的上传按钮
    uploadfile('#invoice_add_form_upload_file','${basePath}/subcontract-invoice/upload-docs.action', {
        buttonText: '上传文件',
        removeCompleted: true ,
        onUploadSuccess : function(file, data, response){
            $('#invoices').datagrid('reload');
            $('#invoice_add_dialog').dialog('close');
        }
    });

    //分包合同的文件管理的上传按钮
    uploadfile('#manage_doc_upload_input','${basePath}/subcontract/upload-docs.action', {
        buttonText: '上传文件',
        'auto': true,
        removeCompleted: true,
        'onUploadStart': function (file) {
            var contractId = datagrid_utils.selected_row_id($('#subcontracts'));
            var  upload = $("#manage_doc_upload_input");
            upload.uploadify("settings",'formData', {
                id:contractId
            } );
        },
        onUploadSuccess : function(file, data, response){
            $('#doc_table').datagrid('reload');
        }
    });


});





/**
 * 添加分包合同
 */
function add_subcontract(){
    $('#add_subcontract_dialog').dialog('open');
}



//付款的格式化
function invoices_payment_id_formatter(id, row, index){
    return '<a href="javascript:;" onclick="subcontract_invoice_payment_del('+ id +',' + row.invoice.id  + ',' + index +')">删除</a>'
}

function invoices_table_doc_formatter(docs, row, index){
    var result = "";
    $.each(docs,
            function (i, v) {
                result += ('<a href="javascript:;" onclick="subcontract_invoice_doc_download('
                        + v.id
                        + ','
                        + row.id
                        + ')">' + v.name + '</a><br/>');
            });
    return result;
}

/**
 * 删除分包付款
 * @param id
 */
function subcontract_invoice_payment_del(id,invoiceId ,index) {
    var url = '${basePath}/subcontract-payment/destory.action?id=' + id + '&invoiceId=' + invoiceId;
    getJSON(url, function (data) {
        if (data.errorInfo) {
            alert(data.errorInfo);
            return;
        }
        datagrid_utils.delete_row($('#invoice-' + index), index);
    });
}

/**
 * 分包发票管理中的发票文件的下载
 */
function subcontract_invoice_doc_download(docId, invoiceId){
    var downloadUrl = "${basePath}/subcontract-invoice/download-doc.action?docId=" + docId + "&invoiceId=" + invoiceId;
    $.fileDownload(downloadUrl);
}




/**
 * 分包合同的添加
 */
function invoice_add_form_submit(){
    $('#invoice_add_form').form('submit',{
        success: function(data){
            data = evalJSON(data);
            if(data.errorInfo){
                alert(data.errorInfo);
                return;
            }

            if($('#invoice_add_form').find('.uploadify-queue-item').size() > 0){
                upload(data.id);
            }else{
                refresh();
            }
            return;

            function upload(id){
                var upload_input = $('#invoice_add_form_upload_file');
                upload_input.uploadify("settings", "formData", {
                    id: id
                });
                upload_input.uploadify("settings", 'onQueueComplete',function(queueData){
                    refresh();
                });
                upload_input.uploadify('upload', '*');
            }

            function refresh(){
                $('#invoice_add_form').trigger('reset');
                $('#invoice_add_dialog').dialog('close');
                $('#invoices').datagrid('reload');
            }
        }
    });

}

/**
 * 删除分包合同
 */
function del_subcontract(){
    var id = datagrid_utils.selected_row_id($('#subcontracts'));
    if(!id){
        alert('请选择需要删除的分包合同!');
        return;
    }
    if(!confirm('删除分包合同的同时将会删除与分包关联的付款发票及付款，确认删除？')){
        return;
    }
    var url = '${basePath}/subcontract/destory.action?id=' + id;
    getJSON(url, function (data){
        if(data.errorInfo){
            alert(data.errorInfo);
            return;
        }
        datagrid_utils.delete_grid_selected_row($('#subcontracts'));
    });
}

/**
 * 单项合同的表单的提交
 */
function subcontract_add_form_submit(){
    $('#add_subcontract_form').submit();
}

/**
 * 付款提交
 */
function payments_add_form_submit(){
    $('#payments_add_form').form('submit', {
        success : function(data){
            data = evalJSON(data);
            if(data.errorInfo){
                alert(data.errorInfo);
                return;
            }

            $('#payments_add_dialog').dialog('close');
            $('#payments_add_form').trigger('reset');
            $('#invoices').datagrid('reload');
            $('#payments').datagrid('reload');
        }
    });
}

/**
 * 分包合同的文件的格式化
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function doc_formatter(value, row, index) {
    var result = "";
    $.each(value,
            function (i, v) {
                result += ('<a href="javascript:;" onclick="doc_download('
                        + v.id
                        + ','
                        + row.id
                        + ')">' + v.name + '</a>&nbsp;|');
            });
    return result;
}
/**
 * 分包合同的文件的下载
 * @param docId
 * @param contractId
 */
function doc_download(docId, contractId) {
    var downloadUrl = "${basePath}/subcontract/download-doc.action?docId=" + docId + "&contractId=" + contractId;
    $.fileDownload(downloadUrl);
}

/**
 * 打开文件管理弹出框
 */
function manage_doc(){
    var id = datagrid_utils.selected_row_id('#subcontracts');
    if(!id){
        alert('请选择需要删除的分包合同!');
        return;
    }
    $('#manage_doc_dialog').dialog('open');

    $('#doc_table').datagrid({
        url : '${basePath}/subcontract/docs-json.action?id=' + id,
        nowrap:false,
        width:500,
        singleSelect:true,
        pagination:true,
        fit:true,
        toolbar:'#doc_table_toolbar',
        columns:[[
            {field:'name',title:'文件名'},
            {field:'size',title:'大小'},
            {field:'uploadDate',title:'上传时间'},
            {field:'id', title : '操作', formatter: doc_table_formatter}
        ]]
    });
    function doc_table_formatter(id, row, index){
        var contractId =  datagrid_utils.selected_row_id($('#subcontracts'));
        var del_link = '<a href="javascript:;" onclick="doc_del('+ id +','+ contractId +');">删除</a>';
        var download_link = '<a href="javascript:;" onclick="doc_download('+ id +','+ contractId +');">下载</a>';
        return del_link + "|" + download_link;
    }
}




/**
 * 删除文件
 * @param id 文件ID
 * @param contractId
 */
function doc_del(id, contractId){
    var url = '${basePath}/subcontract/destory-doc.action?id=' + id + '&contractId=' + contractId;
    getJSON(url, function(data){
        if(data.errorInfo){
            alert(data.errorInfo);
            return;
        }
        $('#doc_table').datagrid('reload');
    });
}

function grid_formatter_organization(value, row, index) {
    return !value ? "" : value.name;
}
</script>
<script type="text/javascript" src="${basePath}/scripts/jquery-download/download.js"></script>

</body>
</html>
