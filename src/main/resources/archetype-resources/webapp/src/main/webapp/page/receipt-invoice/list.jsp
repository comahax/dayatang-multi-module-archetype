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

<table id="receipt_invoice_list_table"
       nowrap="false"
       singleSelect="true"
       rownumbers="true"
       pagination="true"
       pageSize="20"
       fit="true"
       toolbar="${symbol_pound}receipt_invoice_list_tb"
        >
    <thead>
    <tr>
        <th data-options="field:'billingDate'"   width="75">开票日期</th>
        <th data-options="field:'serialNumber'"   width="100">发票编号</th>
        <th data-options="field:'amount',align:'right',formatter:amountNumberFormatter"   width="100">金额(元)</th>
        <th data-options="field:'totalReceipt',align:'right',formatter:amountNumberFormatter"   width="80">已回款(元)</th>
        <th data-options="field:'receivableRatio',align:'right',formatter:function(val){return val + '%'}"   width="75">回款率</th>
        <th data-options="field:'contract',formatter:function(val){return val.contractName}"   width="210">所属合同</th>
        <th data-options="field:'id',formatter:owner_formatter"   width="210">甲方</th>
        <th data-options="field:'docs',formatter:doc_download_formatter"   width="100">附件</th>
    </tr>
    </thead>
</table>
<!--工具栏-->
<div id="receipt_invoice_list_tb" style="padding:5px;height:auto">
    过滤：<select id="receipt_invoice_list_compeleted"   name="completed" style="width:100px;">
    	<option>所有</option>
        <option value="false">未完成收款</option>
        <option value="true">已完成收款</option>
        </select> &nbsp;|&nbsp;
      开始日期：<input id="receipt_invoice_list_from" name="from" class="easyui-datebox" type="text" style="width:100px;"/>
        &nbsp; 结束日期：<input  id="receipt_invoice_list_to" name="to"  style="width:100px;" class="easyui-datebox" type="text"/>
        <a id="receipt_invoice_list_search_btn" iconCls="icon-search" class="easyui-linkbutton">搜索</a>
     <br/>
    <!-- <a id="receipt_invoice_list_add_receipt" class="easyui-linkbutton">添加收款</a> -->

</div>



<script type="text/javascript">
${symbol_dollar}(function(){


    ${symbol_dollar}('${symbol_pound}receipt_invoice_list_compeleted').combobox({'onSelect':update_datagrid});

    ${symbol_dollar}('${symbol_pound}receipt_invoice_list_search_btn').click(update_datagrid);

    ${symbol_dollar}('${symbol_pound}receipt_invoice_list_add_receipt').click();

	${symbol_dollar}('${symbol_pound}receipt_invoice_list_table').datagrid({
		url :  '${symbol_dollar}{basePath}/receipt-invoice/list-json.action'
	});

	

    function update_datagrid(){
        var completed = ${symbol_dollar}('${symbol_pound}receipt_invoice_list_compeleted').val();
        var from = ${symbol_dollar}('${symbol_pound}receipt_invoice_list_from').datebox('getValue');
        var to = ${symbol_dollar}('${symbol_pound}receipt_invoice_list_to').datebox('getValue');

        var opts = {
        		 from:from,
                 to:to
        };

        if(completed == 'true' || completed == 'false'){
            opts.completed = completed;
        }

        ${symbol_dollar}('${symbol_pound}receipt_invoice_list_table').datagrid('load', opts);
    }
});


function owner_formatter(id, row, index){
	var contract = row.contract;
	if(!contract){
		return '[未关联合同]';
	}
	
	var partA = contract.partAInfo;
	var orgName = partA.organization.name;
	var contactName = partA.person ? partA.person.name:"";
	var contact = !partA.person ? partA.contact:"";
	
	return orgName + "-" + contactName + "-" + contact;
	
	
}

function doc_download_formatter(value, row, index) {
    var result = "";
    ${symbol_dollar}.each(value,
            function (i, v) {
                result += ('<a href="javascript:;" onclick="doc_download('
                        + v.id
                        + ','
                        + row.id
                        + ')">' + v.name + '</a>&nbsp;|');
    });

    return result;
}

function doc_download(docId, invoiceId) {
	 var downloadUrl = "${symbol_dollar}{basePath}/receipt-invoice/download-doc.action?docId="
         + docId + "&invoiceId=" + invoiceId;
	 ${symbol_dollar}.fileDownload(downloadUrl);
}
</script>
</body>
</html>



