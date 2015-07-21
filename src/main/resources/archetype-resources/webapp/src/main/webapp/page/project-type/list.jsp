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
</head>
<body>

<div style="clear:both;">
    <a onclick="${symbol_dollar}('${symbol_pound}project_type_addchild_dialog').dialog('open');" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加子类型</a>
    <a onclick="${symbol_dollar}('${symbol_pound}project_type_addparent_dialog').dialog('open');" href="javascript:;" href="${symbol_pound}" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加父类型</a>
    <a onclick="project_type_edit_link_handler();" href="javascript:;" href="${symbol_pound}" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
    <a onclick="project_type_remove_link_handler();" href="javascript:;" href="${symbol_pound}" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
</div>
<div style="clear:both;margin: 10 0 0 20;">
    <div class="span3" style="float: left;"><ul id="project_type_tree" class="ztree"></ul></div>
    <div class="span3 alert alert-info" style="float: left;">
        类型名：<span id="project_type_list_typename"></span>
        <p/>
        编号：<span id="project_type_list_typeserialNumber"></span>
        <p/>
        序号：<span id="project_type_list_typesortOrder"></span>
    </div>
</div>

<div id="project_type_addchild_dialog"
     class="easyui-dialog"
     title="添加子类型"
     style="width:400px;height:300px;padding: 10px;"
     data-options="resizable:true,modal:true,closed:true ">
    <form id="project_type_addchild_form" method="post" action="${symbol_dollar}{basePath}/project-type/add-child.action">
        父类型：<s:select list="parentTypes" name="parent" listKey="serialNumber" listValue="text"> </s:select>
        <p/>
        类型名：<input name="type.text" class="easyui-validatebox" data-options="required:true"/>

        <p/>
        编号：<input name="type.serialNumber" class="easyui-validatebox" data-options="required:true"/>

        <p/>
        序号：<input name="type.sortOrder" class="easyui-numberspinner" style="width:80px;" data-options="min:0,max:100,precision:0">

        <p/>
        <a class="easyui-linkbutton" href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}project_type_addchild_form').submit();">提交</a>
    </form>
</div>


<div id="project_type_addparent_dialog" class="easyui-dialog" title="添加父类型" style="width:400px;height:300px;padding: 10px;"
     data-options="resizable:true,modal:true,closed:true ">
    <form id="project_type_addparent_form" method="post" action="${symbol_dollar}{basePath}/project-type/add-parent.action">
        类型名：<input name="type.text" class="easyui-validatebox" data-options="required:true"/>

        <p/>
        编号：<input name="type.serialNumber" class="easyui-validatebox" data-options="required:true"/>

        <p/>
        序号：<input name="type.sortOrder" class="easyui-numberspinner" style="width:80px;" data-options="min:0,max:100,precision:0">

        <p/>
        <a class="easyui-linkbutton" href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}project_type_addparent_form').submit();">提交</a>
    </form>
</div>

<div id="project_type_edit_dialog" class="easyui-dialog" title="编辑" style="width:400px;height:300px;padding: 10px;"
     data-options="resizable:true,modal:true,closed:true">
    <form id="project_type_edit_form" method="post" action="${symbol_dollar}{basePath}/project-type/edit.action">
        父类型：<s:select list="parentTypes" name="parentSn" listKey="serialNumber" listValue="text" cssClass="easyui-combobox"> </s:select>
        <p/>
        类型名：<input name="text" class="easyui-validatebox" data-options="required:true"/>

        <p/>
        编号：<input name="serialNumber" class="easyui-validatebox" data-options="required:true"/>

        <p/>
        序号：<input name="sortOrder" class="easyui-numberspinner" style="width:80px;" data-options="min:0,max:100,precision:0">

        <p/>
        <input id="project_type_edit_type_id" type="hidden" name="id">
        <a class="easyui-linkbutton" href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}project_type_edit_form').submit();">提交</a>
    </form>
</div>


<script type="text/javascript">

    function project_type_edit_link_handler() {
        var treeObj = ${symbol_dollar}.fn.zTree.getZTreeObj("project_type_tree");

        var nodes= treeObj.getSelectedNodes();
        if (!nodes || nodes.length == 0) {
            alert('请选择项目类型后再编辑！');
        }else{
            var node = nodes[0];
            ${symbol_dollar}('${symbol_pound}project_type_edit_form').form('load', node);
            ${symbol_dollar}('${symbol_pound}project_type_edit_dialog').dialog('open');
        }
    }

    function project_type_remove_link_handler(){
        var treeObj = ${symbol_dollar}.fn.zTree.getZTreeObj("project_type_tree");

        var nodes= treeObj.getSelectedNodes();
        if (!nodes || nodes.length == 0) {
            alert('请选择项目类型后再删除！');
            return;
        }

        if (!confirm('确定删除？')) {
             return;
        }

        var data = getJSON('${symbol_dollar}{basePath}/project-type/remove.action?id=' + nodes[0].id);

        imessager.success_tip();

        treeObj.removeNode(nodes[0]);


    }


    ${symbol_dollar}(function () {
        //初始化树
        var treeObj = init_tree();


        //初始化添加子类型表单
        init_child_form();

        //初始化添加父类型表单
        init_parent_form();

        //初始化类型编辑表单
        init_edit_form(treeObj);

        function init_edit_form(treeObj) {
            ${symbol_dollar}('${symbol_pound}project_type_edit_form').form({
                success: function (data) {
                    data = evalJSON(data);
                    if (data.errorInfo) {
                        alert(data.errorInfo);
                    }
                    ${symbol_dollar}('${symbol_pound}project_type_edit_dialog').dialog('close');
                    layout_center_refreshTab('项目类型管理');
                }
            });
        }


        function init_parent_form() {
            ${symbol_dollar}('${symbol_pound}project_type_addparent_form').form({
                success: function (data) {
                    data = evalJSON(data);
                    if (data.errorInfo) {
                        alert(data.errorInfo);
                    }
                    ${symbol_dollar}('${symbol_pound}project_type_addparent_dialog').dialog('close');
                    layout_center_refreshTab('项目类型管理');
                }
            });
        }


        function init_child_form() {
            ${symbol_dollar}('${symbol_pound}project_type_addchild_form').form({
                success: function (data) {
                    data = evalJSON(data);
                    if (data.errorInfo) {
                        alert(data.errorInfo);
                    }
                    ${symbol_dollar}('${symbol_pound}project_type_addchild_dialog').dialog('close');
                    layout_center_refreshTab('项目类型管理');
                }
            });
        }


        function init_tree() {
            var settings = {
                callback: {
                    onClick: zTreeOnClick
                },
                data: {
                    key: {
                        title: 'serialNumber',
                        name: 'text'
                    },
                    simpleData: {
                        enable: true,
                        idKey: "serialNumber",
                        pIdKey: "parentSn",
                        rootPId: null
                    },
                    keep: {
                        parent: true
                    }
                }
            };

            function zTreeOnClick(event, treeId, treeNode){
                  ${symbol_dollar}('${symbol_pound}project_type_list_typename').html(treeNode.text);
                  ${symbol_dollar}('${symbol_pound}project_type_list_typeserialNumber').html(treeNode.serialNumber);
                  ${symbol_dollar}('${symbol_pound}project_type_list_typesortOrder').html(treeNode.sortOrder);
            }

            var data = getJSON('${symbol_dollar}{basePath}/project-type/list-json.action');

            if (!data) {
                alert('项目类型没有初始化!请联系系统管理员');
            }
            return ${symbol_dollar}.fn.zTree.init(${symbol_dollar}('${symbol_pound}project_type_tree'), settings, data.results);
        }

    });


</script>