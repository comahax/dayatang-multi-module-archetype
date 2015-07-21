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
	<div data-options="region:'north',title:'客户单位列表',split:true" style="height: 400px;">
		<table id="organization_dategrid" >
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true">id</th>
					<th data-options="field:'ownerCategory'">客户类型</th>
					<th data-options="field:'name'">名称</th>
					<th data-options="field:'homePage'">主页</th>
					<th data-options="field:'tel'">电话</th>
					<th data-options="field:'email'">电子邮箱</th>
					<th data-options="field:'address'">地址</th>
				</tr>
			</thead>
		</table>
		<shiro:hasPermission name="ownerOrganization:edit">
			<div id="organization_add_dialog_" title="添加" class="easyui-dialog"
				data-options="modal:true,onClose:organization_add_dialog_close_"
				closed="true"
				style="width: 320px; height: 400px; padding: 10px 0 0 10px;">
				<form id="organization_add_form_" method="post"
					action="<%=basePath%>owner/add.action">
					名称: <input name="name" class="easyui-validatebox" type="text"
						data-options="required:true" />
					<p>
						客户类型： <select id="owner_list_ownercategory_selectoer"
							class="easyui-combobox" name="ownerCategory">
							<s:iterator id="each" value="categories">
								<option value="${symbol_dollar}{each.serialNumber}">${symbol_dollar}{each.text}</option>
							</s:iterator>
						</select>
					</p>

					<p>
						主页： <input name="homePage" type="text" />
					</p>

					<p>
						电话： <input name="tel" type="text" />
					</p>
					<p>
						电子邮箱： <input name="email" type="text" class="easyui-validatebox"
							data-options="validType:'email'" />
					</p>
					<p>
						地址： <input name="address" type="text"  />
					</p>
					<input type="hidden" name="id" />
					<hr />
					<button type="submit">确定</button>
				</form>
			</div>
		</shiro:hasPermission>

		<script type="text/javascript">
            //处理添加机构关闭
            function organization_add_dialog_close_(){
                ${symbol_dollar}('${symbol_pound}organization_add_form_').trigger('reset');
            }

            ${symbol_dollar}(function(){


                ${symbol_dollar}('${symbol_pound}organization_add_form_').form({
                    success : function(data){
                        data = evalJSON(data);
                        if(!data.result){
                            imessager.alert("传入参数不能为空");
                        }else{
                            ${symbol_dollar}('${symbol_pound}organization_add_dialog_').dialog('close');
                            ${symbol_dollar}('${symbol_pound}organization_dategrid').datagrid('reload');
                        }
                    }
                });

                ${symbol_dollar}('${symbol_pound}organization_dategrid').datagrid({
                    url : '<%=basePath%>owner/list-json.action',
                    fit: true,
                    fitColumns:true,
                    rownumbers: true,
                    singleSelect: true,
                    pageSize: 20,
                    pagination: true,
                    onClickRow :function (rowIndex, rowData){
                        var id = rowData.id;
                        ${symbol_dollar}('${symbol_pound}organization_person_dategrid').datagrid({
                            url : '${symbol_dollar}{basePath}/owner-person/list-json.action?ownerId=' + id
                        });
                    }
                    <shiro:hasPermission name="ownerOrganization:edit">
                    ,
                    toolbar: [
                        {
                            text : '添加',
                            iconCls: 'icon-add',
                            handler :  function(){
                                ${symbol_dollar}('${symbol_pound}organization_add_dialog_').dialog('open');
                            }
                        },{
                            text : '编辑',
                            iconCls: 'icon-edit',
                            handler :  function(){
                                ${symbol_dollar}('${symbol_pound}organization_dategrid').datagrid();
                                var row = datagrid_utils.selected_row('${symbol_pound}organization_dategrid');
                                if (!row) {
                                    alert('请选择需要编辑的机构');
                                    return;
                                }
                                ${symbol_dollar}('${symbol_pound}organization_add_form_').form('load',row );
                                //设置编辑业主表单的客户单位分类的值
                                ${symbol_dollar}.each(${symbol_dollar}('${symbol_pound}owner_list_ownercategory_selectoer').combobox('getData'), function(i, v){
                                    if (v.text == row.ownerCategory) {
                                        ${symbol_dollar}('${symbol_pound}owner_list_ownercategory_selectoer').combobox('select', v.value);
                                    }
                                });
                                ${symbol_dollar}('${symbol_pound}organization_add_dialog_').dialog('open');
                            }
                        },{
                        	text:'删除',
                        	iconCls:'icon-remove',
                            handler:function(){
                            	
                            	var table = ${symbol_dollar}('${symbol_pound}organization_dategrid');
                            	var id = datagrid_utils.selected_row_id(table);
                            	if(!id){
                            		alert('请选择需要删除的客户单位');
                            		return;
                            	}
                            	
                            	if(!confirm("删除客户单位的同时也会将该单位下的人员也删除，是否删除？")){
                            		return;
                            	};
                            	
                            	var url = '${symbol_dollar}{basePath}/owner/destory.action?id=' + id;
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
	<div data-options="region:'center',title:'联系人'">
		<table id="organization_person_dategrid" class="easyui-datagrid" fit="true" singleSelect="true" pageSize="20"
               pagination="true"
			<shiro:hasPermission name="exterOrgPerson:edit">
               data-options="toolbar: [
				{
				    text : '添加',
				    iconCls: 'icon-add',
				    handler : organization_contract_add_handler
				},
				{
				    text : '编辑',
				    iconCls: 'icon-edit',
				    handler : organization_contract_edit_handler
				},
				{
				    text : '移除',
				    iconCls: 'icon-remove',
				    handler : organization_contract_remove_handler
				}
				]"
                </shiro:hasPermission>>
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

			<div id="organization_contract_add_dialog_" class="easyui-dialog"
				title="添加联系人" modal="true" closed="true"
				data-options="onClose:organization_contract_add_dialog_close_"
				style="width: 320px; height: 400px; padding: 10px 0 0 10px;">
				<form id="organization_person_add_form_"
					action="<%=basePath%>owner-person/add.action" method="post">
					<p>
						姓名：<input name="name" type="text" class="easyui-validatebox"
							data-options="required:true" />
					</p>
					<p>
						性别： <select name="gender">
							<option value="MALE">男</option>
							<option value="FEMALE">女</option>
						</select>
					</p>
					<p>
						职位： <input name="title" type="text">
					</p>
					<p>
						移动电话：<input name="mobile" type="text">
					</p>
					<p>
						电话：<input name="tel" type="text">
					</p>
					<p>
						电子邮箱：<input name="email" type="text" class="easyui-validatebox"
							data-options="validType:'email'">
					</p>
					<p>
						QQ：<input name="qq" type="text">
					</p>
					<hr />
					<input id="org_list_add_person_form_orgid" type="hidden" name="id" />
					<button type="submit">确定</button>
				</form>

			</div>
		</shiro:hasPermission>

		<script type="text/javascript">
            ${symbol_dollar}(function(){
                ${symbol_dollar}('${symbol_pound}organization_person_add_form_').form({
                    success : function(data){
                       data = evalJSON(data);
                       if(data.errorInfo){
                    	   alert(data.errorInfo);
                    	   return;
                       }
                        ${symbol_dollar}('${symbol_pound}organization_person_add_form_').trigger('reset');
                        imessager.success_tip();
                    }
                });
            });

            //当添加联系人关闭
            function organization_contract_add_dialog_close_() {
                ${symbol_dollar}('${symbol_pound}organization_person_add_form_').trigger('reset');
                ${symbol_dollar}('${symbol_pound}organization_person_dategrid').datagrid('reload');
            }

            //编辑联系人
            function organization_contract_edit_handler(){
                var row =  datagrid_utils.selected_row('${symbol_pound}organization_person_dategrid');
                if (!row) {
                    alert('请选择联系人再编辑！');
                    return;
                }
                row.gender = row.gender == '男'? 'MALE' : 'FEMALE';
                ${symbol_dollar}('${symbol_pound}organization_contract_add_dialog_').dialog('open');
                ${symbol_dollar}('${symbol_pound}organization_person_add_form_').form('load',row);



            }

            function organization_contract_add_handler(){
                var row =  datagrid_utils.selected_row('${symbol_pound}organization_dategrid');
                var dialog = ${symbol_dollar}('${symbol_pound}organization_contract_add_dialog_');
                if(row){
                    ${symbol_dollar}('${symbol_pound}org_list_add_person_form_orgid').val(row.id);
                    dialog.dialog({
                        title : row.name + '添加联系人'
                    });
                    dialog.dialog('open');
                }else{
                    imessager.alert("请选择单位，再添加联系人");
                }
            }

            function organization_contract_remove_handler(){
                var row = ${symbol_dollar}('${symbol_pound}organization_person_dategrid').datagrid('getSelected');
                var org_id = datagrid_utils.selected_row_id('${symbol_pound}organization_dategrid');
                if(row){
                    if(!confirm('确定移除？已经有关联的人员不能删除')){
                        return;
                    }
                    getJSON('<%=basePath%>owner-person/remove.action?id=' + row.id + '&orgId=' + org_id);

                    datagrid_utils.delete_grid_selected_row('${symbol_pound}organization_person_dategrid');
                }else{
                    imessager.alert("请选择单位，再删除联系人");
                }
            }




        </script>

	</div>
</div>

</body>
</html>