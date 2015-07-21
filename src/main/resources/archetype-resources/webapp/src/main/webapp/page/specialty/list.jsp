#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="apache shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
            + "/";
%>
<jsp:include page="/page/scripts.jsp"/>

<div >
    <shiro:hasPermission name="specialty:edit">
<div id="specialty_edit_dialog" title="编辑" class="easyui-dialog" closed="true"
     style="width:320px; height:320px; padding: 10px 0 0 10px;">
    <form id="specialty_edit_form" method="post" action="<%=basePath%>specialty/edit.action">
        名称:
        <br/>
        <input name="name" type="text" data-options="min:0,required:true"/>
        <br/>
        序号：
        <br/>
        <input name="sortOrder" class="easyui-numberbox" data-options="min:0,precision:0" type="text"/>
        <br/>
        备注:
        <br/>
        <input name="remark" type="text"/>
        <br/>
        <input name="id" type="text" style="display:none;">
        <br/>
        <button type="submit">确定</button>
    </form>
</div>
    </shiro:hasPermission>
<div style="background:${symbol_pound}fafafa;overflow:hidden; ">
    <shiro:hasPermission name="specialty:edit">

    <div class="easyui-panel" title="添加专业"  style="margin-bottom: 10px;">

    <form id="specialty_add_form" method="post" action="<%=basePath%>specialty/add.action">
        名称：
        <input name="name" type="text" data-options="min:0,required:true"/>

        序号：
        <input name="sortOrder" class="easyui-numberbox" data-options="min:0,precision:0" type="text"/>

        备注：
        <input name="remark" type="text"/>
        <button type="submit">确定</button><span style="margin-left: 12px;"><input type="reset" value="重置"></span>

    </form>
        <div class="muted" style="width: 300px;">专业名不能有重复</div>
    </div>
    </shiro:hasPermission>

    <table id="specialty_list_datagrid" class="easyui-datagrid"
           data-options="
				rownumbers : true,
				singleSelect : true,
				autoRowHeight : false,
				pagination : true,
				onClickRow : specialty_list_datagrid_clickrow,
				url: '<%=basePath%>specialty/list-json.action',
				pageSize:20,toolbar:[
                    {
                    text : '编辑',
                    iconCls: 'icon-edit',
                    handler : specialty_edit_handler
                    },{
                    text : '撤销',
                    iconCls: 'icon-remove',
                    handler : specialty_disable_handler
                    },{
                    text : '恢复',
                    iconCls: 'icon-resume',
                    handler : specialty_resume_handler
                    },'-','-',{
                    text : '彻底删除',
                    iconCls: 'icon-problem',
                    handler : specialty_destroy_handler
                    }
                    ]"
    >

        <thead>
        <tr>
            <th data-options="field:'id',hidden : true">id</th>
            <th data-options="field:'sortOrder'">序号</th>
            <th data-options="field:'name'">名称</th>
            <th data-options="field:'remark'">备注</th>
            <th data-options="field:'disabled', formatter : specialty_disabled_formatter">是否有效</th>
        </tr>
        </thead>
    </table>
</div>

<script type="text/javascript">

    // TODO
    function specialty_list_datagrid_clickrow(rowIndex, rowData){
        /*var toolbarArray = ${symbol_dollar}('${symbol_pound}specialty_list_datagrid').datagrid('options').toolbar;
        ${symbol_dollar}.each(toolbarArray, function(i, v){
            ${symbol_dollar}(v).linkbutton('disable');
            if('撤销' === v.text){
                ${symbol_dollar}(v).linkbutton('disable');
            }

        });*/
    }

    function specialty_disabled_formatter(value,row,index){
        return value ? '×' : '√';
    }

    function specialty_resume_handler(){
        var datagrid = ${symbol_dollar}('${symbol_pound}specialty_list_datagrid');
        var row = datagrid.datagrid('getSelected');
        if(!row){
            alert('请选择专业后再操作');
            return;
        }
        if ( row.disabled) {
            return;
        }
        var data = getJSON('<%=basePath%>specialty/resume.action?id='+ row.id);
        if(!data || !data.result){
            imessager.alert('操作失败，请刷新页面后重试');
            return;
        }
        datagrid.datagrid('reload');
    }


    function specialty_edit_handler() {
        var datagrid = ${symbol_dollar}('${symbol_pound}specialty_list_datagrid');
        var row = datagrid.datagrid('getSelected');
        if (!row) {
            alert('请选择专业后再操作');
            return;
        }
        var form = ${symbol_dollar}('${symbol_pound}specialty_edit_form');
        form.form('load', row);
        ${symbol_dollar}('${symbol_pound}specialty_edit_dialog').dialog('open');

    }

    function specialty_disable_handler() {
        var datagrid = ${symbol_dollar}('${symbol_pound}specialty_list_datagrid');
        var row = datagrid.datagrid('getSelected');
        if(!row){
            alert('请选择专业后再操作');
            return;
        }
        if ( row.disabled) {
            return;
        }
        var id = row.id;
        var data = getJSON('<%=basePath%>specialty/disable.action?id=' + id);
        if(!data || !data.result){
            imessager.alert('操作失败，请刷新页面后重试');
            return;
        }
        datagrid.datagrid('reload');
    }

    /**
     *只有超级管理员才能使用    // TODO
     */
    function specialty_destroy_handler() {

    }

    ${symbol_dollar}(function () {
        ${symbol_dollar}('${symbol_pound}specialty_edit_form').form({
            success: function (data) {
                data = evalJSON(data);
                if (!data.result) {
                    imessager.alert(data.error_msg);
                    return;
                }
                ${symbol_dollar}('${symbol_pound}specialty_edit_dialog').dialog('close');
                ${symbol_dollar}('${symbol_pound}specialty_list_datagrid').datagrid('reload');
            }
        });

        ${symbol_dollar}('${symbol_pound}specialty_add_form').form({
            success: function (data) {
                data = evalJSON(data);

                if (!data.result) {
                    imessager.alert(data.error_msg);
                    return;
                }
                ${symbol_dollar}('${symbol_pound}specialty_add_form').trigger('reset');
                ${symbol_dollar}('${symbol_pound}specialty_list_datagrid').datagrid('reload');
            }
        });
    });

    //删除
    function specialty_remove_handler() {
        if (!confirm("确定删除？")) {
            return;
        }
        var datagrid = ${symbol_dollar}('${symbol_pound}specialty_list_datagrid');
        var row = datagrid.datagrid('getSelected');
        if (!row) {
            return;
        }
        var id = row.id;
        var data = getJSON('<%=basePath%>specialty/remove.action?id=' + id);
        if (!data.result) {
            imessager.alert("删除失败");
            return;
        }

        var index = datagrid.datagrid('getRowIndex', row);
        datagrid.datagrid('deleteRow', index);
        ${symbol_dollar}('${symbol_pound}specialty_edit_form').form('clear')

    }


</script>
</div>