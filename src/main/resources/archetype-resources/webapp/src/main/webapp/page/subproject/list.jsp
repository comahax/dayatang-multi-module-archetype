#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/page/include.jsp"/>

<s:set id="random" value="randomStr"/>
<div>
    <table id="subproject_list_table_${symbol_dollar}{random}" class="easyui-datagrid"
           fitColumns="true"
           singleSelect="false"
           rownumbers="true"
           pagination="true"
           idField="id"
           pageSize="20"
           width="auto"
           url="${symbol_dollar}{basePath}/subproject/list-json.action?id=${symbol_dollar}{id}"
<s:if test="project.businessOperationsable">
           toolbar="${symbol_pound}subproject_list_toolbar_${symbol_dollar}{random}"
</s:if>
            >
        <s:include value="list-title-tpl.jsp"/>
    </table>
<s:if test="project.businessOperationsable">

<div id="subproject_list_toolbar_${symbol_dollar}{random}">
        <a onclick="subproject_list_add_btn_handler_${symbol_dollar}{random}();"
           href="javascript:;"
           class="easyui-linkbutton"
           data-options="iconCls:'icon-add'">添加</a>
        <a onclick="subproject_list_remove_btn_handler_${symbol_dollar}{random}();"
           href="javascript:;" class="easyui-linkbutton"
           data-options="iconCls:'icon-remove'">删除</a>
    </div>
</s:if>

    <script type="text/javascript">
        function subproject_list_add_btn_handler_${symbol_dollar}{random}(){
            ${symbol_dollar}('${symbol_pound}subproject_add_dialog_${symbol_dollar}{random}').dialog('open');
        }

        function subproject_list_remove_btn_handler_${symbol_dollar}{random}(){
            var grid = ${symbol_dollar}('${symbol_pound}subproject_list_table_${symbol_dollar}{random}');
            var rows = grid.datagrid('getSelections');
            if(!rows){
                alert('请选择单点工程后再删除');
                return;
            }
            if(!confirm("确定删除?")){
                return;
            }
            ${symbol_dollar}.each(rows, function(i, v){
                var id = v.id;
                var result = getJSON('${symbol_dollar}{basePath}/subproject/destroy.action?projectId=${symbol_dollar}{id}&id=' + id);
            });
            ${symbol_dollar}('${symbol_pound}subproject_list_table_${symbol_dollar}{random}').datagrid('reload');
            imessager.success_tip();
        }
    </script>
</div>
<s:if test="project.businessOperationsable">

<s:include value="add.jsp"/>
</s:if>



