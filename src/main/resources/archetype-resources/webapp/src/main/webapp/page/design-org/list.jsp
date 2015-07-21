#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="dictionary" uri="/dayatang-dictionary"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/page/scripts.jsp"/>
</head>
<body>
<div class="easyui-layout" fit="true">
<div data-options="region:'north',title:'单位列表',split:true" style="height: 400px;" >
        <table id="organization_dategrid">
            <thead>
            <tr>
                <th data-options="field:'id',hidden:true">id</th>
                <th data-options="field:'name'">名称</th>
                <th data-options="field:'homePage'">主页</th>
                <th data-options="field:'tel'">电话</th>
                <th data-options="field:'email'">电子邮箱</th>
                <th data-options="field:'address'">地址</th>
            </tr>
            </thead>
        </table>
        <shiro:hasPermission name="supervisorOrganization:edit">
            <div id="organization_add_dialog"title="添加"
                 class="easyui-dialog"
                 modal="true"
                 closed="true"
                 data-options="onClose:organization_add_dialog_close"
                 style="width:320px; height:400px; padding: 10px 0 0 10px;">
                <form id="organization_add_form" method="post" action="${symbol_dollar}{basePath}/design-org/add.action">
                    名称:
                    <input name="name" class="easyui-validatebox" type="text"  data-options="required:true"/>

                    <p>主页：
                        <input name="homePage" type="text"/></p>
                    <p>电话：
                        <input name="tel" type="text"/>
                    </p>
                    <p>
                        电子邮箱：
                        <input name="email" type="text"/>
                    </p>
                    <p>
                        地址：
                        <input name="address" type="text"/>
                    </p>
                    <hr/>
                    <input name="id" type="hidden"/>

                    <button type="submit">确定</button>
                </form>
            </div>
        </shiro:hasPermission>
        <script type="text/javascript">

            /**
             * 关闭弹出框后清空
             * @private
             */
            function organization_add_dialog_close(){
                ${symbol_dollar}('${symbol_pound}organization_add_form').form('clear');


            }

            ${symbol_dollar}(function(){
                ${symbol_dollar}('${symbol_pound}organization_add_form').form({
                    success : function(data){
                        data = evalJSON(data);
                        imessager.success_tip();
                        ${symbol_dollar}(this).trigger('reset');
                        ${symbol_dollar}('${symbol_pound}organization_dategrid').datagrid('reload');
                        ${symbol_dollar}('${symbol_pound}organization_add_dialog').dialog('close');
                    }
                });

                ${symbol_dollar}('${symbol_pound}organization_dategrid').datagrid({
                    url: "${symbol_dollar}{basePath}/design-org/list-json.action",
                    fitColumns:true,
                    fit: true,
                    rownumbers: true,
                    singleSelect: true,
                    pageSize: 20,
                    pagination: true,
                    onClickRow :function (rowIndex, rowData){
                        var id = rowData.id;
                        ${symbol_dollar}('${symbol_pound}organization_person_dategrid').datagrid({'url':'${symbol_dollar}{basePath}/design-org/list-people-json.action?id=' + id});
                    }
                    <shiro:hasPermission name="supervisorOrganization:edit">
                    ,
                    toolbar: [
                        {
                            text : '添加',
                            iconCls: 'icon-add',
                            handler :  function(){
                                ${symbol_dollar}('${symbol_pound}organization_add_dialog').dialog('open');
                            }
                        },
                        {
                            text : '编辑',
                            iconCls: 'icon-edit',
                            handler :  function(){
                                var row = datagrid_utils.selected_row('${symbol_pound}organization_dategrid');
                                if (!row) {
                                    alert('请选择单位后编辑!');
                                    return;
                                }
                                ${symbol_dollar}('${symbol_pound}organization_add_form').form('load', row);
                                ${symbol_dollar}('${symbol_pound}organization_add_dialog').dialog('open');
                            }
                        },{
                        	text:'删除',
                        	iconCls:'icon-remove',
                        	handler:function(){
                        		
                        		var table = ${symbol_dollar}('${symbol_pound}organization_dategrid');
                        		var id = datagrid_utils.selected_row_id(table);
                        		if(!id){
                        			alert('请选择需要删除的单位');
                        			return;
                        		}
                        		if(!confirm("删除单位的同时也会将该单位下的人员也删除，是否删除？")){
                        			return;
                        		}
                        		var url = '${symbol_dollar}{basePath}/design-org/destory.action?id=' + id;
                        		getJSON(url,function(data){
                        			if(data.errorInfo){
                        				alert(errorInfo);
                        				return;
                        			}
                        			datagrid_utils.delete_grid_selected_row(table);
                        		});
                        	}
                        }
                    ]
                    </shiro:hasPermission>

                });
            });
        </script>

   
</div>



<div data-options="region:'center',title:'单位联系人'" >
   
        <table id="organization_person_dategrid" class="easyui-datagrid"  fit="true"
               singleSelect="true"
               pageSize="20"
               pagination="true"
                <shiro:hasPermission name="exterOrgPerson:edit">
                    data-options="toolbar: [
                    {
                    text : '添加',
                    iconCls: 'icon-add',
                    handler : organization_contract_add_handler
                    },
                    {
                    text : '移除',
                    iconCls: 'icon-remove',
                    handler : organization_contract_remove_handler
                    }
                    ]"
                </shiro:hasPermission>
                >
            <thead>
            <tr>
                <th data-options="field:'id',hidden:true">id</th>
                <th data-options="field:'organization',hidden:true">机构</th>
                <th data-options="field:'name'">姓名</th>
                <th data-options="field:'gender'">性别</th>
                <th data-options="field:'title'">职位</th>
                <th data-options="field:'mobile'">移动电话</th>
                <th data-options="field:'tel'">电话</th>
                <th data-options="field:'email'">电子邮箱</th>
                <th data-options="field:'qq'">QQ</th>
            </tr>
            </thead>
        </table>
        <shiro:hasPermission name="exterOrgPerson:edit">
            <div id="organization_contract_add_dialog" class="easyui-dialog" title="添加联系人" closed="true"
                 style="width:320px; height:400px; padding: 10px 0 0 10px;">
                <form id="organization_person_add_form" action="${symbol_dollar}{basePath}/design-org/add-person.action" method="post">
                    <p>
                        姓名：
                        <input name="name" type="text" class="easyui-validatebox" data-options="required:true" />
                    </p>
                    <p>
                        性别：
                        <select name="gender">
                            <option value="MALE">男</option>
                            <option value="FEMALE">女</option>
                        </select>
                    </p>
                    <p>
                        职位：
                        <input name="title" type="text">
                    </p>
                    <p>
                        移动电话：
                        <input name="mobile" type="text">
                    </p>
                    <p>
                        电话：
                        <input name="tel" type="text">
                    </p>
                    <p>
                        电子邮箱：
                        <input name="email" type="text">
                    </p>
                    <p>
                        QQ：
                        <input name="qq" type="text">
                    </p>
                    <hr/>
                    <input id="org_list_add_person_form_orgid" type="hidden" name="id" />
                    <button type="submit">确定</button>
                </form>

            </div>

            <script type="text/javascript">
                ${symbol_dollar}(function(){
                    ${symbol_dollar}('${symbol_pound}organization_person_add_form').form({
                        success : function(data){
                            data = evalJSON(data);
                            ${symbol_dollar}('${symbol_pound}organization_person_dategrid').datagrid('loadData',{total:0,rows:[]});
                            ${symbol_dollar}('${symbol_pound}organization_person_dategrid').datagrid('reload');
                            ${symbol_dollar}(this).trigger('reset');
                            ${symbol_dollar}('${symbol_pound}organization_contract_add_dialog').dialog('close');

                        }
                    });
                })

                function organization_contract_add_handler(){
                    var row = ${symbol_dollar}('${symbol_pound}organization_dategrid').datagrid('getSelected');
                    var dialog = ${symbol_dollar}('${symbol_pound}organization_contract_add_dialog');
                    if(row){
                        ${symbol_dollar}('${symbol_pound}org_list_add_person_form_orgid').attr('value', row.id);
                        dialog.dialog({
                            title : row.name + '添加联系人'
                        });
                        dialog.dialog('open');
                    }else{
                        alert("请选择单位，再添加联系人");
                    }
                }

                function organization_contract_remove_handler(){
                    var row = ${symbol_dollar}('${symbol_pound}organization_person_dategrid').datagrid('getSelected');
                    var org_id = datagrid_utils.selected_row_id('${symbol_pound}organization_dategrid');
                    if(row){
                        if(!confirm('确定移除？已经有关联的人员不能删除')){
                            return;
                        }
                        getJSON('${symbol_dollar}{basePath}/design-org/remove-person.action?id=' + row.id + '&orgId=' + org_id);
                        datagrid_utils.delete_grid_selected_row('${symbol_pound}organization_person_dategrid');
                    }else{
                        alert("请选择单位，再删除联系人");
                        return;
                    }
                }

            </script>
        </shiro:hasPermission>
   
</div>
</div>
</body>
</html>
