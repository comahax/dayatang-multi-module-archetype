#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!--内容由模板提供
<div>机构基本信息：</div>
<table id="organizational_struct_base_info">
</table> -->
<jsp:include page="/page/include.jsp" />

<table id="organizational_struct_person_dategrid" title="机构下的人员"
	class="easyui-datagrid" nowrap="false" fit="true" singleSelect="true"
	pageSize="20" pagination="true"
	toolbar="${symbol_pound}organizational_struct_tree_toolbar"
	url="<%=basePath%>organizational-struct/person-list-json.action">
	<thead>
		<tr>
			<th data-options="field:'id',hidden:true">id</th>
			<th style="width: auto;"
				data-options="field:'disabled',formatter:organizational_struct_tree_disabled_formatter"
				width="15"></th>
			<th data-options="field:'name'" width="80">姓名</th>
			<th data-options="field:'gender'" width="35">性别</th>
			<th data-options="field:'mobile'" width="75">移动电话</th>
			<th data-options="field:'tel'" width="85">电话</th>
			<th data-options="field:'email'" width="115">电子邮箱</th>
			<th data-options="field:'qq'" width="75">QQ</th>
			<th
				data-options="field:'roleAssignments',formatter:role_assignment_formatter"
				width="200">用户名－机构－角色</th>
			<th
				data-options="field:'org',formatter:organizational_struct_tree_org_formatter"
				width="200">所属机构</th>
		</tr>
	</thead>
</table>


<%--菜单--%>
<div id="organizational_struct_tree_toolbar">

	选择机构：<input id="organizational_strct_tree_org_tree" type="text"
		style="width: 300px"> &nbsp;|&nbsp; <input
		class="easyui-searchbox" style="width: 300px"
		data-options="searcher:organizational_struct_search_handler, prompt:'请输入',menu:'${symbol_pound}organizational_struct_tree_search_menu_name'" />

	<div id="organizational_struct_tree_search_menu_name"
		style="width: 120px">
		<div data-options="name:'name',iconCls:'icon-ok'">名字</div>
	</div>
	<shiro:hasPermission name="person:edit">
		<a href="javascript:;"
			onclick="${symbol_dollar}('${symbol_pound}organizational_struct_tree_import_people_dialog').dialog('open');"
			class="easyui-linkbutton"> 批量导入人员</a>
		<a href="javascript:;"
			onclick="organizational_struct_person_add_handler();"
			iconCls="icon-add" class="easyui-linkbutton">添加</a>
		<a href="javascript:;"
			onclick="organizational_struct_person_role_assignment_handler();"
			class="easyui-linkbutton">分配角色</a>
		<a href="javascript:;"
			onclick="organizational_struct_person_cancel_handler();"
			iconCls="icon-cancel" class="easyui-linkbutton">撤消</a>
		<a href="javascript:;"
			onclick="organizational_struct_person_resum_handler();"
			iconCls="icon-undo" class="easyui-linkbutton">恢复</a>
		<a href="javascript:;"
			onclick="organizational_struct_person_delete_handler();"
			class="easyui-linkbutton">删除</a>
	</shiro:hasPermission>
</div>
<shiro:hasPermission name="person:edit">
	<%--分配角色弹出框--%>
	<div title="分配角色" class="easyui-dialog"
		id="organizational_struct_assign_role_dialog" modal="true"
		resize="true" data-options="width:450,height:350," closed="true">
		<table id="organizational_struct_assign_role_grid"
			class="easyui-datagrid" fit=true
			data-options="fitColumns:true,singleSelect:true"
			toolbar="${symbol_pound}organizational_struct_assign_role_toolbar">
			<thead>
				<tr>
					<th data-options="field:'id'"></th>
					<th
						data-options="field:'organization',formatter:grid_show_name_formatter"
						width="150">所在机构</th>
					<th
						data-options="field:'role',formatter:function(role){return role.description}"
						width="150">拥有角色</th>
				</tr>
			</thead>
		</table>
		<div id="organizational_struct_assign_role_toolbar">
			<form id="organizational_struct_assign_role_form"
				action="${symbol_dollar}{basePath}/organizational-struct/assignment-role.action"
				method="post">
				在机构<input id="organizational_struct_assignrole_form_org"
					name="scopeId" type="text" /> <br /> 下拥有<input
					id="organizational_struct_assignrole_form_role" name="roleId"
					type="text" />
				<button type="submit">分配</button>
			</form>
			<br /> <a
				onclick="organizational_struct_assign_role_toolbar_remove();"
				iconCls="icon-remove" href="javascript:;" class="easyui-linkbutton">移除</a>
		</div>
	</div>

	<!-- 批量导入人员 -->
	<div id="organizational_struct_tree_import_people_dialog"
		class="easyui-dialog" modal="true" closed="true"
		style="width: 400px; height: 300px;" title="批量导入人员">
		<h4>一、下载模板，并填入数据</h4>
		<a target="_blank"
			href="${symbol_dollar}{basePath}/organizational-struct/download-import-people-template.action">点击下载</a>
		<h4>二、上传数据</h4>
		<form action="${symbol_dollar}{basePath}/organizational-struct/import-people.action"
			method="post" enctype="multipart/form-data"
			id="organizational_struct_import_people_form">
			<input type="file" name="uploads" id="import_people_btn" /> <input
				type="submit" value="提交" />
		</form>

		<div>提示：如果上传数据中有错，则返回包含错误的excel，请修改后提交。</div>

	</div>

	<div id="organizational_struct_tree_add_person_dialog"
		class="easyui-dialog" title="添加人员" modal="true" closed="true"
		data-options="onClose:organizational_struct_tree_add_person_dialog_close"
		style="width: 350px; height: 350px; padding: 10px 0 0 10px;">
		<form id="organizational_struct_tree_add_person_form"
			action="<%=basePath%>organizational-struct/add-person-and-user.action"
			method="post">
			<fieldset>
				<legend>基本信息</legend>
				姓名<span style="color: red;">*</span>： <input name="name" type="text"
					class="easyui-validatebox" data-options="required:true" /> <br />
				电子邮箱<span style="color: red;">*</span>： <input name="email"
					class="easyui-validatebox"
					data-options="required:true,validType:'email'" type="text">
				<br /> (默认的登录帐号)<br /> 移动电话： <input name="mobile" type="text">
				<br /> <input id="organizational_struct_tree_add_person_form_orgid"
					type="text" class="hide" name="orgId"> 所属机构：<input
					id="organizational_struct_tree_add_person_form_orgscope"
					type="text" />

			</fieldset>
			<fieldset>
				<legend>分配角色</legend>
				在机构<span style="color: red;">*</span> <input
					id="organizational_struct_assignrole_org" name="scopeId"
					type="text" /> <br /> 下拥有<span style="color: red;">*</span> <input
					id="organizational_struct_assignrole_role" name="roleId"
					type="text">
			</fieldset>
			<hr />
			<button type="submit">确定</button>
		</form>
		<div class="alert alert-info">
			人员的账号的密码默认为
			<s:property value="systemDefaultPassword" />
			。
		</div>

	</div>
	
	
	<div id="organizational_struct_tree_edit_person_dialog"
		class="easyui-dialog" title="修改所属机构" modal="true" closed="true"
		style="width: 350px; height: 150px; padding: 10px 0 0 10px;">
		
		<form id="organizational_struct_tree_edit_person_form"
			action="<%=basePath%>organizational-struct/reset-person-internal-org.action"
			method="post">
					所属机构：
					<input id="organizational_struct_tree_edit_person_form_orgscope" name="internalId"
					type="text"  />
                    <input id="edit_org_person_id"
					type="text" class="hide" name="personId"> 
			<button type="submit">确定</button>
		</form>
	</div>
	
	
</shiro:hasPermission>
<script type="text/javascript">
	
	function organizational_struct_tree_org_formatter(value, row, index) {
		var editLink = '<a href="javascript:;" onclick="organizational_struct_tree_edit('+ row.id +');">修改</a>';
		return value.fullName +  '&nbsp;&nbsp;'  + editLink;
/* 		if (value) {
	          return value.fullName
	        }else{
	          return "";
	        }	
 */		
	}
			
	/**
	 * 修改  所属机构
	 */
	function organizational_struct_tree_edit(personId){
		   ${symbol_dollar}('${symbol_pound}edit_org_person_id').val(personId);
		   ${symbol_dollar}('${symbol_pound}organizational_struct_tree_edit_person_dialog').dialog('open');
	}


    //当弹出框关闭
    function organizational_struct_tree_add_person_dialog_close() {
        ${symbol_dollar}('${symbol_pound}organizational_struct_tree_add_person_form').trigger('reset');
    }
    
    //修改   当弹出框关闭
    function organizational_struct_tree_edit_person_dialog_close() {
        ${symbol_dollar}('${symbol_pound}organizational_struct_tree_edit_person_form').trigger('reset');
    }



    function organizational_struct_search_handler(value, name) {
        var param = {};
        param[name] = value;
        var id = ${symbol_dollar}('${symbol_pound}organizational_strct_tree_org_tree').combotree('getValue');
        param['orgId'] = id;
        ${symbol_dollar}('${symbol_pound}organizational_struct_person_dategrid').datagrid('load', param);
        /* setTimeout("var url = '
        ${symbol_dollar}{basePath}/organization-struct/children-of.action?parentId=' + id;var result = getJSON(url);if (!result) {return;}var org = result.parent;${symbol_dollar}('${symbol_pound}organizational_struct_base_info').html(Mustache.render(${symbol_dollar}('${symbol_pound}organizational_struct_tree_org_tpl').html(), org));"
         , 2500);*/

    }

    function role_assignment_formatter(value, row, index) {
        if (!value) {
            return '未分配角色';
        }
        var result = '';
        ${symbol_dollar}.each(value, function (i, v) {
            result += (v.user.username + '-' + v.organization.name + '-' + v.role.description + '<br/>');
        });
        return result;
    }

    function organizational_struct_tree_disabled_formatter(value, row, index) {
        return value ? "×" : "√";
    }


    <shiro:hasPermission name="person:edit">
    /**
     * 添加新员工
     */
    function organizational_struct_person_add_handler() {
        ${symbol_dollar}('${symbol_pound}organizational_struct_tree_add_person_dialog').dialog('open');
    }

    /**
     *   撤消员工
     */
    function organizational_struct_person_cancel_handler() {
        var datagrid = ${symbol_dollar}('${symbol_pound}organizational_struct_person_dategrid');
        var id = datagrid_utils.selected_row_id(datagrid);
        if (!id || !confirm('确定撤消该员工？')) {
            return;
        }

        getJSON("<%=basePath%>organizational-struct/cancel-person.action?id=" + id, function (data) {
            if (data.errorInfo) {
                imessager.alert(data.errorInfo);
                return;
            } else {
                datagrid_utils.delete_grid_selected_row(datagrid);
            }
        });
    }

    /**
     *   恢复员工
     */
    function organizational_struct_person_resum_handler() {
        var datagrid = ${symbol_dollar}('${symbol_pound}organizational_struct_person_dategrid');
        var row = datagrid_utils.selected_row(datagrid);
        if (!row) {
            return;
        }

        var id = row.id;
        getJSON("<%=basePath%>organizational-struct/resume-person.action?id="
				+ id, function(data) {
			if (data.errorInfo) {
				var index = datagrid_utils.selected_row_index(datagrid);
				datagrid.datagrid('deleteRow', index);
				imessager.alert(data.errorInfo);
			} else {
				datagrid.datagrid('reload');
			}
		});
	}

	//删除员工（误操作引起的）
	function organizational_struct_person_delete_handler() {
		var id = datagrid_utils
				.selected_row_id('${symbol_pound}organizational_struct_person_dategrid');
		if (!id) {
			alert('请选择需要删除的人员');
			return;
		}
		if (!confirm('确定删除员工？如果用户已经有立过项、报过产值等有关业务的操作，则无法删除，只能撤消。')) {
			return;
		}
		var data = getJSON('${symbol_dollar}{basePath}/person/destroy.action?id=' + id);
		if (data && data.errorInfo) {
			alert(data.errorInfo);
			return;
		}
		datagrid_utils
				.delete_grid_selected_row('${symbol_pound}organizational_struct_person_dategrid');
	}

	//为用户分配角色
	function organizational_struct_person_role_assignment_handler() {
		var row = datagrid_utils
				.selected_row('${symbol_pound}organizational_struct_person_dategrid');
		if (!row) {
			alert('请选择人员分配角色');
			return;
		}
		${symbol_dollar}('${symbol_pound}organizational_struct_assign_role_dialog').dialog('open');
		//设置grid
		${symbol_dollar}('${symbol_pound}organizational_struct_assign_role_grid').datagrid('loadData',
				row.roleAssignments);
	}

	//移除已经有角色
	function organizational_struct_assign_role_toolbar_remove() {
		var id = datagrid_utils
				.selected_row_id('${symbol_pound}organizational_struct_assign_role_grid');
		if (!id) {
			alert('请选择需要移除的角色分配');
			return;
		}
		getJSON('${symbol_dollar}{basePath}/role/remove-role-assignment.action?id=' + id);
		datagrid_utils
				.delete_grid_selected_row('${symbol_pound}organizational_struct_assign_role_grid');
	}
	</shiro:hasPermission>

	${symbol_dollar}(function() {
		<shiro:hasPermission name="person:edit">
		${symbol_dollar}('${symbol_pound}organizational_struct_import_people_form')
				.form(
						{
							success : function(data) {
								if (data && evalJSON(data.errorInfo).length > 0) {
									alert(evalJSON(data.errorInfo));
									return;
								}
								${symbol_dollar}.messager
										.confirm(
												'请确认',
												'人员添加成功，是否继续添加？',
												function(r) {
													if (r) {
														return;
													} else {
														${symbol_dollar}(
																'${symbol_pound}organizational_struct_tree_import_people_dialog')
																.dialog('close');
														${symbol_dollar}(
																'${symbol_pound}organizational_struct_person_dategrid')
																.datagrid(
																		'reload');
													}
												});
							}
						});

		/**
		 *添加人员
		 */
		${symbol_dollar}('${symbol_pound}organizational_struct_tree_add_person_form').form(
				{
					success : function(data) {
						data = evalJSON(data);
						if (data.errorInfo) {
							imessager.alert(data.errorInfo);
						} else {
							${symbol_dollar}('${symbol_pound}organizational_struct_tree_add_person_dialog')
									.dialog('close');
							${symbol_dollar}('${symbol_pound}organizational_struct_tree_add_person_form')
									.trigger('reset');
							${symbol_dollar}('${symbol_pound}organizational_struct_person_dategrid')
									.datagrid('appendRow', data.result);
						}
					}
				});
		
		/**
		 *修改 所属机构  
		 */
			${symbol_dollar}('${symbol_pound}organizational_struct_tree_edit_person_form').form(
					{
						success : function(data) {
							data = evalJSON(data);
							if (data.errorInfo) {
								imessager.alert(data.errorInfo);
							} else {
								${symbol_dollar}('${symbol_pound}organizational_struct_tree_edit_person_dialog')
										.dialog('close');
								//重置表单数据
								${symbol_dollar}('${symbol_pound}organizational_struct_tree_edit_person_form')
										.trigger('reset'); 
								//更新 表格datagrid
								${symbol_dollar}('${symbol_pound}organizational_struct_person_dategrid')
									.datagrid('reload');
							}
 						}
					});

		  
		
		
		//scopeId
		proj_app.organizational_scope('${symbol_pound}organizational_struct_assignrole_org',
				{
					width : 200
				});
		
		setTimeout(
				"proj_app.organizational_scope('${symbol_pound}organizational_struct_tree_add_person_form_orgscope', {width: 200, onChange: function () {var org_id = ${symbol_dollar}('${symbol_pound}organizational_struct_tree_add_person_form_orgscope').combotree('getValue');${symbol_dollar}('${symbol_pound}organizational_struct_tree_add_person_form_orgid').val(org_id);}});",
				1200);

		//roleId
		proj_app.assigned_role('${symbol_pound}organizational_struct_assignrole_role', {
			width : 200
		});
		
	   //设置 修改机构 的值
	   setTimeout(
				"proj_app.organizational_scope('${symbol_pound}organizational_struct_tree_edit_person_form_orgscope', {width: 200, onChange: function () {var org_id = ${symbol_dollar}('${symbol_pound}organizational_struct_tree_edit_person_form_orgscope').combotree('getValue');${symbol_dollar}('${symbol_pound}organizational_struct_tree_edit_person_form_orgid').val(org_id);}});",
				1200);

		
		//按机构过显示人员
		proj_app.organizational_scope('${symbol_pound}organizational_strct_tree_org_tree', {
			onChange : project_list_org_scope_selector_onSelect
		});
		function project_list_org_scope_selector_onSelect() {
			var id = ${symbol_dollar}('${symbol_pound}organizational_strct_tree_org_tree').combotree(
					'getValue');
			${symbol_dollar}('${symbol_pound}organizational_struct_person_dategrid').datagrid('load', {
				orgId : id
			});
		}

		proj_app.organizational_scope(
				'${symbol_pound}organizational_struct_assignrole_form_org', {
					width : 200
				});
		proj_app.assigned_role('${symbol_pound}organizational_struct_assignrole_form_role', {
			width : 200
		});
		${symbol_dollar}('${symbol_pound}organizational_struct_assign_role_form')
				.form(
						{
							onSubmit : function(param) {
								param.id = datagrid_utils
										.selected_row_id('${symbol_pound}organizational_struct_person_dategrid');
							},
							success : function(data) {
								data = evalJSON(data);
								if (data && data.errorInfo) {
									alert(data.errorInfo);
									return;
								}
								imessager.success_tip();
								${symbol_dollar}('${symbol_pound}organizational_struct_assign_role_grid')
										.datagrid('appendRow', data.result);
							}
						});
		</shiro:hasPermission>
	});
</script>

