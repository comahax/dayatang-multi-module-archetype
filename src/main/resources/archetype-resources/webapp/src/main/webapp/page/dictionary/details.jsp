#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
            + "/";
%>
<jsp:include page="/page/scripts.jsp"/>
<shiro:hasPermission name="dictionary:edit">
    <div style="margin-bottom: 15px;">
        <form id="dictionary_add_form_${symbol_dollar}{category}" method="post">
            <span>编号</span>
            <input name="serialNumber" type="text" class="easyui-validatebox" data-options="required: true"/>
            <span>文本</span>
            <input name="text" type="text" class="easyui-validatebox" data-options="required:true"/>
            <span>序号</span>
            <input name="sortOrder" type="text" class="easyui-numberbox" data-options="min:0,precision:0"/>
            <span>备注</span>
            <input name="remark" type="text" />
            <input name="category" type="hidden" value="${symbol_dollar}{category}" />
            <button type="submit">添加</button>
            <a style="margin-left: 20px;" href="javascript:${symbol_dollar}('${symbol_pound}dictionary_add_form_${symbol_dollar}{category}').trigger('reset');">重置</a>
        </form>
    </div>
</shiro:hasPermission>

<table id="dictionary_datagrid_${symbol_dollar}{category}" class="easyui-datagrid"
       nowrap="false"

       singleSelect="true"

       pagination="true"
       style="width:500px;"
       url="<%=basePath%>dictionary/list-json.action?category=${symbol_dollar}{category}"
       pageSize="50"
        <shiro:hasPermission name="dictionary:edit">
            data-options="
            toolbar: [
            {
            text : '编辑',
            iconCls: 'icon-edit',
            handler : dictionary_edit_handler_${symbol_dollar}{category}
            },{
            text : '删除',
            iconCls: 'icon-remove',
            handler : dictionary_remove_handler_${symbol_dollar}{category}
            }
            ]"
        </shiro:hasPermission>
        >
    <thead>
    <tr>
        <th data-options="field:'id',hidden : true">id</th>
        <th data-options="field:'serialNumber'" width="100">编号</th>
        <th data-options="field:'sortOrder'" width="50">序号</th>
        <th data-options="field:'text'" width="100">文本</th>
        <th data-options="field:'remark'" width="200">备注</th>
        <th data-options="field:'category',hidden : true">category</th>
    </tr>
    </thead>
</table>
<shiro:hasPermission name="dictionary:edit">

    <div id="dictionary_edit_dialog_${symbol_dollar}{category}"class="easyui-dialog" title="添加/编辑字典" style="width:300px;height:300px;padding: 10px 0 0 10px;"
         data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
        <form id="dictionary_edit_form_${symbol_dollar}{category}" method="post">
            <label>编号</label>
            <input name="serialNumber" type="text" class="easyui-validatebox" data-options="required: true"/>
            <p/>
            <label>文本</label>
            <input name="text" type="text" class="easyui-validatebox" data-options="required:true"/>
            <p/>
            <label>序号</label>
            <input name="sortOrder" type="text" class="easyui-validatebox" data-options="required:true,precision:0"/>
            <p/>
            <label>备注</label>
            <input name="remark" type="text" />
            <p/>
            <input name="category" type="hidden" value="${symbol_dollar}{category}" />
            <input name="id" type="hidden"/>
            <input type="submit" value="确定" />
        </form>
    </div>
    <script type="text/javascript">
        ${symbol_dollar}(function(){
            var add_form =  ${symbol_dollar}('${symbol_pound}dictionary_add_form_${symbol_dollar}{category}');
            add_form.form({
                url : '<%=basePath%>dictionary/add.action',
                success:function(data){
                    data = evalJSON(data);
                    if(data.error){
                        imessager.alert(data.error);
                        return ;
                    }
                    add_form.trigger('reset');
                    ${symbol_dollar}('${symbol_pound}dictionary_datagrid_${symbol_dollar}{category}').datagrid('reload');
                }
            });

            var edit_form = ${symbol_dollar}('form${symbol_pound}dictionary_edit_form_${symbol_dollar}{category}');
            edit_form.form({
                url : '<%=basePath%>dictionary/edit.action',
                success:function(data){
                    data = evalJSON(data);
                    if(data.error){
                        imessager.alert(data.error);
                        return ;
                    }
                    ${symbol_dollar}('${symbol_pound}dictionary_edit_dialog_${symbol_dollar}{category}').dialog('close');
                    ${symbol_dollar}('${symbol_pound}dictionary_datagrid_${symbol_dollar}{category}').datagrid('reload');
                }
            });

        });


        //编辑
        function dictionary_edit_handler_${symbol_dollar}{category}(){
            var datagrid = ${symbol_dollar}('${symbol_pound}dictionary_datagrid_${symbol_dollar}{category}');
            var row = datagrid.datagrid('getSelected');
            if(!row){
                return;
            }
            var dialog = ${symbol_dollar}('${symbol_pound}dictionary_edit_dialog_${symbol_dollar}{category}');
            var form = ${symbol_dollar}('${symbol_pound}dictionary_edit_form_${symbol_dollar}{category}');
            form.form('load', row);
            dialog.dialog('open');
        }

        //删除
        function dictionary_remove_handler_${symbol_dollar}{category}(){
            if (!confirm("确定删除？")) {
                return;
            }
            var datagrid = ${symbol_dollar}('${symbol_pound}dictionary_datagrid_${symbol_dollar}{category}');
            var row = datagrid.datagrid('getSelected');
            if(!row){
                return;
            }
            var id = row.id;
            var data = getJSON('<%=basePath%>dictionary/remove.action?id=' + id);
            if (!data.result) {
                imessager.alert("删除失败");
            }
            datagrid_utils.delete_grid_selected_row(datagrid);
        }



    </script>
</shiro:hasPermission>


