#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="dictionary" uri="/dayatang-dictionary" %>

<div id="frameworkcontract_list_receiptinvoice_dialog"
     class="easyui-window"
     title="收款管理"
     minimizable="false"
     iconCls="icon-save"
     collapsed="false"
     resizable="true"
     closed="true"
     modal="true"
     style="width:850px;height:600px;">

    <table id="framworkcontract_list_receiptinvoice_table"
           nowrap="false"
           fit="true"
           fitColumns="true"
           singleSelect="true"
           rownumbers="true"
           pagination="true"
           pageSize="20"
           pageNumber="1"
           idField="id"
           toolbar="${symbol_pound}framworkcontract_list_receiptinvoice_table_toolbar"
            >
        <thead>
        <th data-options="field:'billingDate'" width="85">开票日期</th>
        <th data-options="field:'serialNumber'" width="100">发票编号</th>
        <th data-options="field:'amount',align:'right',formatter:amountNumberFormatter" width="100">金额(元)</th>
        <th data-options="field:'totalReceipt',align:'right',formatter:amountNumberFormatter" width="80">已回款(元)</th>
        <th data-options="field:'receivableRatio',align:'right',formatter:function(val){return val + '%'}" width="75">
            回款率
        </th>
        <th data-options="field:'contract',formatter:function(val){return val.contractName}" width="210">所属合同</th>
        <th data-options="field:'docs',formatter:frameworkcontract_list_receiptvoice_doc_formatter" width="100">附件</th>
        <th data-options="field:'id',formatter:framworkcontract_list_receiptinvoice_table_operation_formatter"
            width="50">操作
        </th>
        </thead>
    </table>

    <div id="framworkcontract_list_receiptinvoice_table_toolbar">

        是否收款完成：<select id="framworkcontract_list_receiptinvoice_table_toolbar_compeleted"
                       name="completed"
                       style="width:100px;">
        <option value="">所有</option>
        <option value="false">未完成收款</option>
        <option value="true">已完成收款</option>
    </select>
        发票编号关键字：<input id="framworkcontract_list_receiptinvoice_table_toolbar_serialNumber" name="serialNumber"
                       style="width:100px"/>
        开始日期：<input id="framworkcontract_list_receiptinvoice_table_toolbar_from" name="from" class="easyui-datebox"
                    type="text" style="width:100px;"/>&nbsp;
        结束日期：<input id="framworkcontract_list_receiptinvoice_table_toolbar_to" name="to" style="width:100px;"
                    class="easyui-datebox" type="text"/>
        <a href="javascript:;" onclick="framworkcontract_list_receiptinvoice_table_toolbar_search_handler();"
           iconCls="icon-search"
           class="easyui-linkbutton"></a>
        <br/>
        <a class="easyui-linkbutton" href="javascript:;"
           onclick="${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_add_dialog').dialog('open');">
            添加发票</a>
        &nbsp;<a class="easyui-linkbutton" href="javascript:;"
                 onclick="${symbol_dollar}('${symbol_pound}framworkcontract_list_receipt_add_dialog').dialog('open');">添加收款</a>
    </div>
</div>

<div id="framworkcontract_list_receiptinvoice_add_dialog" class="easyui-dialog" closed="true" modal="true"
     style="width: 400px; height: 400px;" title="添加发票">
    <form id="framworkcontract_list_receiptinvoice_add_form" method="post"
          action="${symbol_dollar}{basePath}/receipt-invoice/add.action">
        开票日期：<input name="billingDate" type="text" class="easyui-datebox" required="required"/>

        <p/>
        发票编号：<input name="serialNumber" class="easyui-validatebox" data-options="required:true"/>

        <p/>
        金额：<input name="amount" type="text" class="easyui-numberbox"
                  data-options="min:0,groupSeparator: ',',suffix:'元'"/>

        <p/>
        <input type="file" name="uploads" id="framworkcontract_list_receiptinvoice_add_form_upload_file"/>
        <a onclick="framworkcontract_list_receiptinvoice_add_form_submit();"
           href="javascript:;"
           class="easyui-linkbutton">添加</a>
    </form>
</div>

<div id="framworkcontract_list_receipt_add_dialog" class="easyui-dialog" closed="true" modal="true"
     style="width: 400px; height: 400px;" title="添加收款">
    <form id="framworkcontract_list_receipt_add_form" method="post"
          action="${symbol_dollar}{basePath}/receipt/add.action">
        <p>收款类型：
            <select name="receiptType" class="easyui-validatebox" data-options="required:true">
                <s:iterator value="receiptTypes" id="each">
                    <option value="<s:property value="${symbol_pound}each.serialNumber"/>">
                        <s:property value="getReceiptTypeText(${symbol_pound}each.serialNumber)"/>
                    </option>
                </s:iterator>
            </select>

        <p>收款金额：<input name="amount" type="text" class="easyui-numberbox"
                       data-options="min:0,groupSeparator:',',suffix:'元'" data-options="required:true"/></p>

        <p>收款时间：<input name="receivedDate" type="text" class="easyui-datebox" data-options="required:true"/></p>

        <p>备注：<textarea name="remark"></textarea></p>
        <hr/>
        <a onclick="framworkcontract_list_receipt_add_form_submit();" class="easyui-linkbutton" iconCls="icon-save"
           href="javascript:;">添加</a>
    </form>
</div>

<script>

    ${symbol_dollar}(function () {
        //上传收款发票
        var upload_url = '${symbol_dollar}{basePath}/receipt-invoice/upload-docs.action';
        uploadfile('${symbol_pound}framworkcontract_list_receiptinvoice_add_form_upload_file', upload_url, {
            'removeCompleted': true,
            'onUploadSuccess': function (file, data, response) {
                ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table').datagrid('reload');
            }
        });

    });


    //添加收款
    function framworkcontract_list_receipt_add_form_submit() {
        ${symbol_dollar}('${symbol_pound}framworkcontract_list_receipt_add_form').form('submit', {
            onSubmit: function (param) {
                param.invoiceId = datagrid_utils.selected_row_id('${symbol_pound}framworkcontract_list_receiptinvoice_table');
            },
            success: function (data) {
                data = evalJSON(data);
                if (data && data.errorInfo) {
                    alert(data.errorInfo);
                    return;
                }
                ${symbol_dollar}('${symbol_pound}framworkcontract_list_receipt_add_form').form('reset');
                ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table').datagrid('reload');
                ${symbol_dollar}('${symbol_pound}framworkcontract_list_receipt_add_dialog').dialog('close');
            }
        });
    }

    //总包发票管理
    function frameworkcontract_list_receiptinvoicebtn_handler() {
        var row = datagrid_utils.selected_row('${symbol_pound}frameworkcontract_list');
        if (!row) {
            imessager.alert('请选择一条数据');
            return;
        }

        ${symbol_dollar}('${symbol_pound}frameworkcontract_list_receiptinvoice_dialog').dialog('open');

        var url = "${symbol_dollar}{basePath}/framework-contract/receipt-invoice-json.action?frameworkId=" + row.id;
        ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table').datagrid({
            url: url,
            view: detailview,
            detailFormatter: function (index, row) {
                return '<table id="framework_contract_receiptinvoice_details_table' + index + '"></table>';
            },
            onExpandRow: function (index, row) {
                var tableName = '${symbol_pound}framework_contract_receiptinvoice_details_table' + index;
                var table = ${symbol_dollar}(tableName);
                table.datagrid({
                    url: '${symbol_dollar}{basePath}/receipt/list-of-invoice-json.action?invoiceId=' + row.id,
                    onLoadSuccess: function () {
                        datagrid_utils.fix_detail_row_height(table, index);
                    },
                    nowrap: false,
                    fitColumns: true,
                    columns: [
                        [
                            {field: 'receiptType', title: '收款类型', width: 50},
                            {field: 'receivedDate', title: '收款日期', width: 70, align: 'right'},
                            {field: 'amount', title: '收款金额(元)', width: 80, align: 'right', formatter: amountNumberFormatter},
                            {field: 'remark', title: '备注', width: 150},
                            {field: 'id', title: '操作', width: 80, formatter: function (value, row, index) {
                                return '<a href="javascript:;" rowId="'+ value +'" rowIndex="'+ index +'" tableName="'+ tableName +'" onclick="frameworkcontract_list_receipt_remove(this);">删除</a>';

                            }}
                        ]
                    ]
                });
                datagrid_utils.fix_detail_row_height(table, index);
            }
        });

    }


    //移除收款
    function frameworkcontract_list_receipt_remove(s) {
        var self = ${symbol_dollar}(s);
		var id = self.attr('rowId');
		var rowIndex = self.attr('rowIndex');
		var tableName = self.attr('tableName');
        
        if (!confirm('确定删除该收款？')) {
            return;
        }

        var result = getJSON('${symbol_dollar}{basePath}/receipt/destroy.action?id=' + id);
        if (result && result.errorInfo) {
            alert(result.errorInfo);
            return;
        }

        datagrid_utils.delete_row(tableName, rowIndex);
    }

    //添加总包发票
    function framworkcontract_list_receiptinvoice_add_form_submit() {
		var form =  ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_add_form');
		form.form('submit',
                {
                    onSubmit: function (param) {
                        param.contractId = datagrid_utils.selected_row_id('${symbol_pound}frameworkcontract_list');
                    },
                    success: function (data) {
                        data = evalJSON(data);
                        if (data && data.errorInfo) {
                            alert(data.errorInfo);
                            return;
                        }
						
                        var id = data.id;
                        ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_add_form').form('reset');
						if(form.find('.uploadify-queue-item').length > 0){
							upload(id);	
						}else{
							 ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_add_dialog').dialog('close');
						}
                        ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table').datagrid('reload');

                    }
                });

        //上传附件
        function upload(id) {
            var upload_input = ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_add_form_upload_file');
            upload_input.uploadify("settings", "formData", {
                id: id,
                contractId: datagrid_utils.selected_row_id('${symbol_pound}frameworkcontract_list')
            });
            upload_input.uploadify("settings", 'onQueueComplete',function(queueData){
                ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_add_dialog').dialog('close');
            });
            upload_input.uploadify('upload', '*');
        }
      
    }

    function framworkcontract_list_receiptinvoice_table_toolbar_search_handler() {
        var serialNumber = ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table_toolbar_serialNumber').val();
      
        var from = ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table_toolbar_from').datebox('getValue');
        var to = ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table_toolbar_to').datebox('getValue');
        var grid = ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table');
		var opts = {
	            serialNumber: serialNumber,
	            from: from,
	            to: to
	        };
        
        var completed = ${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table_toolbar_compeleted').val();
        if(completed != ''){
        	opts.completed = completed;
        }
        
        grid.datagrid('load', opts);

    }

    //操作收款发票
    function framworkcontract_list_receiptinvoice_table_operation_formatter(value, row, index) {
        return '<a href="javascript:;" onclick="framworkcontract_list_receiptinvoice_table_operation_formatter_delete('
                + value + ',' + index + ')">删除</a>';
    }

    function framworkcontract_list_receiptinvoice_table_operation_formatter_delete(id, index) {
        if (!confirm("删除收款发票的同时将会删除发票所关联的收款，请确认？")) {
            return;
        }
        var url = "${symbol_dollar}{basePath}/receipt-invoice/destroy.action?id=" + id;
        getJSON(url);
        datagrid_utils.delete_row(${symbol_dollar}('${symbol_pound}framworkcontract_list_receiptinvoice_table'), index);
    }

    function frameworkcontract_list_receiptvoice_doc_formatter(value, row, index) {
        if (!value) {
            return '<a href="${symbol_pound}" title="如果需要上传发票附件，请转去收款发票管理页面。" class="easyui-tooltip" data-options="cls-help"></a>  '
        }
        var result = "";
        ${symbol_dollar}.each(value,
                function (i, v) {
                    result += ('<a href="javascript:;" onclick="frameworkcontract_list_receiptvoice_doc_download('
                            + v.id
                            + ','
                            + datagrid_utils.selected_row_id('${symbol_pound}frameworkcontract_list')
                            + ')">' + v.name + '</a>&nbsp;|');
        });

        return result;
    }

    function frameworkcontract_list_receiptvoice_doc_download(docId, frameworkId) {
        var downloadUrl = "${symbol_dollar}{basePath}/framework-contract/download-doc.action?docId="
                + docId + "&frameworkId=" + frameworkId;
        ${symbol_dollar}.fileDownload(downloadUrl);
    }
</script>

