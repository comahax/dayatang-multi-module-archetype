#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
    <link rel="stylesheet"  href="<%=basePath%>scripts/ztree/3.4/zTreeStyle/zTreeStyle.css" type="text/css">
    <style type="text/css">
        .ztree li span.button.ico_open{margin-right:2px; background: url(<%=basePath%>scripts/ztree/3.4/zTreeStyle/img/diy/1_open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
        .ztree li span.button.ico_close{margin-right:2px; background: url(<%=basePath%>scripts/ztree/3.4/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
    </style>
    <script src="<%=basePath%>scripts/ztree/3.4/jquery.ztree.core-3.4.js" type="text/javascript"></script>
    <script src="${symbol_dollar}{basePath}/scripts/ztree/3.4/jquery.ztree.excheck-3.4.min.js" type="text/javascript"></script>
    <script src="${symbol_dollar}{basePath}/scripts/ztree/3.4/jquery.ztree.exedit-3.4.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/jquery-download/download.js"></script>
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/file_upload/upload.js"></script>
</head>
<body>
<table id="role_list"  fit="true"></table>


<script type="text/javascript">
    ${symbol_dollar}(function(){
        ${symbol_dollar}('${symbol_pound}role_list').datagrid({
            fitColumns:true,
            idField : 'id',
            singleSelect:true,
            url:'${symbol_dollar}{basePath}/role/list-json.action',
            columns:[[
                {field:'name',title:'角色名'},
                {field:'description',title:'描述'},
                {field:'id',title:'包括权限',width:300, formatter:  role_permission_formatter}
            ]],
            toolbar: [
                {
                    text : '编辑',
                    iconCls: 'icon-edit',
                    handler : role_edit_handler
                },{
                    text : '添加',
                    iconCls: 'icon-add',
                    handler : role_add_handler
                },'-',{
                    text : '删除',
                    iconCls: 'icon-remove',
                    handler : role_destroy_handler
                }
            ]
        });
    });

    //显示角色包含的权限
    function role_permission_formatter(value, row, index){
        var elementId = 'role_list_permissions_tree_' + proj_app.integer_random();

        setTimeout(delay_init_tree, 1000);

        //延迟加载角色的权限树
        function delay_init_tree(){
            var data = getJSON('${symbol_dollar}{basePath}/role/permissions-of-role.action?id=' + value);
            data = proj_app.convert_permission_serverdata_to_clientdata(data.results);
            ${symbol_dollar}.fn.zTree.init(${symbol_dollar}('${symbol_pound}' + elementId), get_settings(), data);

            function get_settings(){
                return{
                    data: {
                        key:{
                            title : 'name',
                            name  : 'description'
                        },
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId",
                            rootPId: 0
                        }
                    }
                };
            }
        }
        return '<ul class="ztree" id="'+ elementId +'"></ul>';
    }



    //编辑
    function role_edit_handler(){
        var grid = ${symbol_dollar}('${symbol_pound}role_list');
        var row = grid.datagrid('getSelected');
        if (!row) {
            return;
        }
        role_list_open_dialog();
        var role_id = row.id;
        ${symbol_dollar}('${symbol_pound}role_list_form_role_id').val(role_id);
        var url = '${symbol_dollar}{basePath}/role/permissions-of-role.action?id='+ role_id;
        var roles_permissions = getJSON(url).results;
        var treeObj = ${symbol_dollar}.fn.zTree.getZTreeObj('role_list_form_permissions');
        ${symbol_dollar}.each(roles_permissions, function(i,v){
            var node = treeObj.getNodeByParam("id", v.id, null);
            treeObj.checkNode(node, true, false);
        });

        ${symbol_dollar}('${symbol_pound}role_list_form').form('load', row);

    }

    //添加
    function role_add_handler() {
        role_list_open_dialog();
        var treeObj =  ${symbol_dollar}.fn.zTree.getZTreeObj('role_list_form_permissions');
        treeObj.checkAllNodes(false);
        ${symbol_dollar}('${symbol_pound}role_list_form_role_id').val('');
    }

    //撤消角色
    function role_destroy_handler(){
        var grid = ${symbol_dollar}('${symbol_pound}role_list');
        var row = grid.datagrid('getSelected');
        if (!row) {
            return;
        }

        var role_id = row.id;
        getJSON('${symbol_dollar}{basePath}/role/remove.action?id=' + role_id);
        grid.datagrid('deleteRow', grid.datagrid('getRowIndex', role_id));
    }

    //打开弹出框
    function role_list_open_dialog(){
        var dialog = ${symbol_dollar}('${symbol_pound}role_manager_dialog');
        dialog.dialog('open');
    }
    //打开弹出框
    function role_list_close_dialog(){
        var dialog = ${symbol_dollar}('${symbol_pound}role_manager_dialog');
        dialog.dialog('close');
    }

</script>


<div id="role_manager_dialog" class="easyui-dialog"
     title="角色管理"
     data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true,
     onClick:role_manager_dialog_close"
     style="width: 650px;height:600px;padding: 10px;">
    <form id="role_list_form" action="${symbol_dollar}{basePath}/role/add.action" method="post">
        <p>
            角色名：<input name="name" class="easyui-validatebox" data-options="required:true"/>
        </p>

        <p>
            描述：<input name="description" type="text">
        </p>
        <input id="role_list_form_role_id" name="id" type="hidden"/>
        <fieldset>
            <legend>包含权限</legend>
            <ul id="role_list_form_permissions" class="ztree"></ul>
        </fieldset>
        <a href="javascript:;" class="easyui-linkbutton" onclick="role_list_form_submit();">提交</a>
    </form>
</div>
<script type="text/javascript">
    ${symbol_dollar}(function () {
        //初始化角色添加的表单
        init_form_tree();


        function init_form_tree(){
            var data = getJSON('${symbol_dollar}{basePath}/permission/all-json.action');
            data = proj_app.convert_permission_serverdata_to_clientdata(data.results);

            ${symbol_dollar}.fn.zTree.init(${symbol_dollar}('${symbol_pound}role_list_form_permissions'), get_settings(), data);
            function get_settings(){
                return {
                    data: {
                        key:{
                            title : 'name',
                            name  : 'description'
                        },
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId",
                            rootPId: 0
                        },
                        keep :{
                            parent: true
                        }
                    },
                    check: {
                        enable: true,
                        chkStyle: "checkbox",
                        chkboxType: {"Y": "ps", "N": "s"}
                    }
                };
            }
        }
    });

    //当角色管理弹出框关闭时
    function role_manager_dialog_close(){
        var treeObj = ${symbol_dollar}.fn.zTree.getZTreeObj('role_list_form_permissions');
       treeObj.checkAllNodes(false);

        ${symbol_dollar}('${symbol_pound}role_list_form').reset();

    }



    //提交表单
    function role_list_form_submit(){
        ${symbol_dollar}('${symbol_pound}role_list_form').form('submit',{
            onSubmit : function(param){
                var treeObj =  ${symbol_dollar}.fn.zTree.getZTreeObj('role_list_form_permissions');
                var nodes = treeObj.getCheckedNodes(true);
                if (nodes.length > 0 ) {
                    ${symbol_dollar}.each(nodes, function(i, v){
                        param['perms['+ i +']'] = v.id;
                    });
                }


            },
            success : function(){
                role_list_close_dialog();
                ${symbol_dollar}('${symbol_pound}role_list').datagrid('reload');

                imessager.success_tip();
            }
        });
    }
</script>
</body>
</html>