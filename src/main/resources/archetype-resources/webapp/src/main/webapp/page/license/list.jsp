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
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/jquery-download/download.js"></script>
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/file_upload/upload.js"></script>
</head>
<body>



<table id="license_list"
       fitColumns="true"
       singleSelect="true"
       rownumbers="true"
       pagination="true"
       pageSize="20"
       pageNumber="1"
       idField="id"
       data-options="url:'${symbol_dollar}{basePath}/license/list-json.action',fit:true">
</table>

<script type="text/javascript">
    ${symbol_dollar}(function(){
        ${symbol_dollar}('${symbol_pound}license_list').datagrid({
            columns:[[
                {field:'internalOrganizationDto',title:'所属机构',formatter: function(value,row,index){
                    if (row.internalOrganizationDto){
                        return row.internalOrganizationDto.name;
                    } else {
                        return '-';
                    }
                }},
                {field:'credType',title:'证书类别',hidden:true},
                {field:'credTypeText',title:'证书类别'},
                {field:'credNumber',title:'证书编号'},
                {field:'authority',title:'颁发机构'},
                {field:'awardDate',title:'颁发日期'},
                {field:'periodDate',title:'有效期'}
            ]]
            ,
            toolbar: [
                <shiro:hasPermission name="licenseQuery:editOrganization">

                {
                    text : '编辑',
                    iconCls: 'icon-edit',
                    handler : license_edit_handler
                },{
                    text : '添加',
                    iconCls: 'icon-add',
                    handler : license_add_handler
                },'-',{
                    text : '删除',
                    iconCls: 'icon-remove',
                    handler : license_destroy_handler
                },{
                    text : '上传文件',
                    iconCls: 'icon-upload',
                    handler : license_file_upload
                }
                </shiro:hasPermission>
                <shiro:hasPermission name="licenseQuery:organization">
                ,{
                    text : '下载文件',
                    iconCls: 'icon-download',
                    handler : license_file_download
                }
                </shiro:hasPermission>
            ]


        });
    });

    function getCurrentTree(){
        var treeId = ${symbol_dollar}('${symbol_pound}license_list_form_permissions ul:first').attr('id');
        var treeObj = ${symbol_dollar}.fn.zTree.getZTreeObj(treeId);
        return treeObj;
    }

    //编辑
    function license_edit_handler(){
        //TODO:修改获取并修改值
        var grid = ${symbol_dollar}('${symbol_pound}license_list');
        var row = grid.datagrid('getSelected');
        if (!row) {
            imessager.alert('请选择一条数据');
            return;
        }
        var dialog = ${symbol_dollar}('${symbol_pound}license_manager_dialog');
        dialog.dialog({
            onBeforeOpen:function(){
                node.expandNode();
            },
            onOpen : function(){
                //初始化数据
                ${symbol_dollar}('${symbol_pound}license_list_form_license_id').val(row.id);

                ${symbol_dollar}('${symbol_pound}license_credType').combobox('setValue',row.credType);

                ${symbol_dollar}('${symbol_pound}license_credNumber').val(row.credNumber);
                ${symbol_dollar}('${symbol_pound}license_authority').val(row.authority);
                ${symbol_dollar}('${symbol_pound}license_awardDate').datebox('setValue',row.awardDate);
                ${symbol_dollar}('${symbol_pound}license_periodDate').datebox('setValue',row.periodDate);

                ${symbol_dollar}('${symbol_pound}license_list_form_internalOrganizationDto_license_id').val(row.internalOrganizationDtoId);

                var treeObj = getCurrentTree();
                var node = treeObj.getNodeByParam('id',row.internalOrganizationDtoId);
                treeObj.selectNode(node,false);
                ${symbol_dollar}('div.organizationInfo').html("当前选择: "+node.name);
            },
            onClose:function(){
                clearPanel();
                ${symbol_dollar}("${symbol_pound}license_list_form").form('clear');
            },
            buttons:[{
                text:'保存',
                handler:function(obj){
                    var licenselistform = ${symbol_dollar}('${symbol_pound}license_list_form');
                    var url = "${symbol_dollar}{basePath}/license/add.action"+'';
                    var data = licenselistform.serialize();
                    ${symbol_dollar}.ajax({
                        url: url,
                        data : data,
                        beforeSend:function(){
                            //保存loading
                        },
                        success : function(response) {
                            imessager.ajaxInfo(response,function(){
                                dialog.dialog('close');
                                ${symbol_dollar}('${symbol_pound}license_list').datagrid('reload');
                                ${symbol_dollar}("${symbol_pound}license_list_form_license_id").val(response.license.id);
                            });
                        },
                        dataType : 'json'
                    });
                }
            },{
                text:'取消',
                handler:function(){
                    dialog.dialog('close');
                }
            }]
        }).dialog('open');
    }

    //添加证照
    function license_add_handler() {
        license_list_open_dialog();
        ${symbol_dollar}('${symbol_pound}license_list_form_license_id').val('');
    }

    //删除一条证照
    function license_destroy_handler(){
        var grid = ${symbol_dollar}('${symbol_pound}license_list');
        var row = grid.datagrid('getSelected');
        if (!row) {
            imessager.alert('请选择一条数据');
            return;
        }
        ${symbol_dollar}.messager.confirm('提示','你确定要删除吗？',function(r){
            if(r){
                var result = getJSON('${symbol_dollar}{basePath}/license/remove.action?id=' + row.id);
                imessager.ajaxInfo(result,function(){
                    grid.datagrid('deleteRow', grid.datagrid('getRowIndex', row.id));
                });
            }
        });

    }

    var node = {
        containerId : "license_list_form_permissions",
        setTree : function(divId){
            this.containerId = divId;
            return this;
        },
        getCurrentTree:function(){
            var treeId = ${symbol_dollar}('${symbol_pound}'+this.containerId).find('ul:first').attr('id');
            var treeObj = ${symbol_dollar}.fn.zTree.getZTreeObj(treeId);
            return treeObj;
        },
        expandNode:function(){
            var nodes_ =  this.getCurrentTree().getNodes();
            if (nodes_.length>0) {
                this.getCurrentTree().expandAll(false);
            }
        }
    }

    //清除弹出框
    function clearPanel(){
        //取消数选择项
        ${symbol_dollar}('div.organizationInfo').html('');
        //取消树的选中项
        var treeObj = getCurrentTree();
        var nodes = treeObj.getSelectedNodes();
        if (nodes.length>0) {
            treeObj.cancelSelectedNode(nodes[0]);
        }

        var nodes_ = treeObj.getNodes();
        if (nodes_.length>0) {
            treeObj.expandNode(nodes_[0],true,true,true);
        }
    }

    //上传license的附件
    function openLicenseUploadDialog(licenseId){
        ${symbol_dollar}('${symbol_pound}license_manager_upload_dialog').dupload({
            url:'${symbol_dollar}{basePath}/license/add!upload.action',
            uploadTarget : ${symbol_dollar}('${symbol_pound}license_pre_upload_file'),
            params:{
                'license.id': licenseId
            }
        });
    }

    //证照文件上传
    function license_file_upload(){
        var row = ${symbol_dollar}('${symbol_pound}license_list').datagrid('getSelected');
        if(!row){
            imessager.alert('请选择一条数据');
            return;
        }
        openLicenseUploadDialog(row.id);
    }

    <%--文件下载--%>
    function license_file_download(){
        var row = ${symbol_dollar}('${symbol_pound}license_list').datagrid('getSelected');
        if(!row){
            imessager.alert('请选择一条数据');
            return;
        }
        download_dialog_open(row.id);
    }

    <%--下载文件--%>
    function  download_dialog_open(licenseId){
        var downloadDialog = ${symbol_dollar}('${symbol_pound}license_manager_download_dialog');
        downloadDialog.dialog({
            onBeforeOpen:function(){
                var url = "${symbol_dollar}{basePath}/license/docs-json.action?license_id="+licenseId;
                bindFilesDataToTable(url);
            }
        }).dialog('open');
    }

    <%--绑定文件列表到表格中--%>
    function bindFilesDataToTable(url){
        var fileTable = ${symbol_dollar}('${symbol_pound}license_manager_download_fileList');
        fileTable.datagrid({
            url:url,
            columns: [
                [
                    {field: 'name', title: '文件名'},
                    {field: 'size', title: '大小'},
                    {field: 'uploadDate', title: '上传时间'},
                    {field: 'id', hidden: true, title: 'id'}
                ]
            ],
            toolbar:[{
                text:'下载',
                iconCls:'icon-download',
                handler:function(obj){
                    var row = fileTable.datagrid('getSelected');
                    if(!row){
                        imessager.alert('请选择一条数据');
                        return;
                    }
                    //  TODO
                    var downloadUrl = "${symbol_dollar}{basePath}/personal/download-doc.action?docId="+row.id;
                    ${symbol_dollar}.fileDownload(downloadUrl,{
                       /* failCallback:function(reponse,url){
                            imessager.alert('无法下载文件，错误信息：'+reponse+'点击：<a target="_blank" href="'+url+'">重试</a>');
                        }*/
                    });
                }
            }],
            buttons:[{
                text:'关闭',
                handler:function(){
                    downloadDialog.dialog('close');
                    downloadDialog.datagrid('clearSelections');
                }
            }]
        })
    }

    //打开弹出框
    function license_list_open_dialog(){
        var dialog = ${symbol_dollar}('${symbol_pound}license_manager_dialog');
        dialog.dialog({
            onOpen:function(){
                clearPanel();
                ${symbol_dollar}("${symbol_pound}license_list_form").form('clear');
            },
            buttons:[{
                text:'保存',
                handler:function(obj){
                    var licenselistform = ${symbol_dollar}('${symbol_pound}license_list_form');
                    var url = "${symbol_dollar}{basePath}/license/add.action"+'';
                    var data = licenselistform.serialize();
                    ${symbol_dollar}.ajax({
                        url: url,
                        data : data,
                        beforeSend:function(){
                            //保存loading
                        },
                        success : function(response) {
                            imessager.ajaxInfo(response,function(){
                                dialog.dialog('close');
                                clearPanel();
                                ${symbol_dollar}('${symbol_pound}license_list').datagrid('reload');
                                ${symbol_dollar}("${symbol_pound}license_list_form_license_id").val(response.license.id);
                                //显示上传控件
                                ${symbol_dollar}.messager.confirm('提示','保存成功，是否现在添加证照附件？',function(r){
                                    if(r){
                                        openLicenseUploadDialog(response.license.id);
                                    }
                                });
                            });
                        },
                        dataType : 'json'
                    });
                }
            },{
                text:'取消',
                handler:function(){
                    dialog.dialog('close');
                }
            }]
        }).dialog('open');
    }
</script>

<div id="license_manager_upload_dialog" class="easyui-dialog" title="证照文件上传" data-options="closed:true,resizable:true,modal:true" style="width:400px;height:auto;padding: 20px; ">
    <div title="3 上传附件" data-options="closable:false" style="padding:20px;">
        <input type="file" name="uploads" id="license_pre_upload_file" />
        <br/>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-upload'"
           href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}license_pre_upload_file').uploadify('upload','*');">上传</a>&nbsp;
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
           href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}license_pre_upload_file').uploadify('cancel','*');">取消所有</a>
    </div>
</div>

<%--下载文件列表--%>
<div id="license_manager_download_dialog" class="easyui-dialog" title="证照文件下载" data-options="closed:true,resizable:true,modal:true" style="width:600px;height:auto;padding: 20px; ">
    <table id="license_manager_download_fileList"
           fitColumns="true"
           singleSelect="true"
           rownumbers="true"
           pagination="true"
           pageSize="20"
           pageNumber="1"
           idField="id"
           data-options="">
    </table>
</div>

<div id="license_manager_dialog" class="easyui-dialog"
     title="证照管理"
     data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true"
     style="width: 650px;height:600px;padding: 10px;">
    <form id="license_list_form" action="${symbol_dollar}{basePath}/license/add.action" method="post">
        <p>
            <fieldset style="position: relative;">
                <div class="organizationInfo" style="position: absolute; top: 20px; right: 15px; border: solid thin ${symbol_pound}cccccc;line-height: 20px;">

                </div>
                <legend>所属机构</legend>
                <div id="license_list_form_permissions">
                </div>

            </fieldset>
        </p>

        <p>
            证书类别：<input name="license.credType" type="text" id="license_credType">
        </p>

        <p>
            证书编号：<input name="license.credNumber" type="text" maxlength="125" id="license_credNumber">
        </p>

        <p>
            颁发机构：<input name="license.authority" type="text" maxlength="125" id="license_authority">
        </p>

        <p>
            颁发日期：<input name="license.awardDate" type="text" class="easyui-datebox" id="license_awardDate">
        </p>

        <p>
            有&nbsp;效&nbsp;期&nbsp;&nbsp;：<input name="license.periodDate" type="text" class="easyui-datebox" id="license_periodDate" >
        </p>
        <input id="license_list_form_license_id" name="license.id" type="hidden"/>
        <input id="license_list_form_internalOrganizationDto_license_id" name="license.internalOrganizationDtoId" type="hidden"/>
    </form>
</div>



<script type="text/javascript">

    //获取证书类别
    var getCredType = function(inputId){
        var result = returnJqObject(inputId);

        var data = getJSON('${symbol_dollar}{basePath}/license/cred-type-json.action');
        result.combobox({
            readonly:true,
            valueField: 'serialNumber',
            textField: 'text',
            data: data.rows,
            editable:false,
            multiple: false
        });

        return result;
    }

    ${symbol_dollar}(function () {
        getCredType('${symbol_pound}license_credType');
        var url = "${symbol_dollar}{basePath}/organizational-struct/tree-json.action";
        var tree = initInstitutionalFrameworkTreeMini('${symbol_pound}license_list_form_permissions', url, zTreeOnClick);

        function zTreeOnClick(event, treeId, treeNode) {
            event.stopPropagation();
            ${symbol_dollar}('${symbol_pound}license_list_form_internalOrganizationDto_license_id').val(treeNode.id);
            var selectedNode = tree.getSelectedNodes();
            ${symbol_dollar}('div.organizationInfo').html("当前选择: "+selectedNode[0].name);
            ${symbol_dollar}('input[name="license.credType"]').focus();
            if (selectedNode.length>0) {
                tree.expandAll(false);
            }
        }

        ${symbol_dollar}('${symbol_pound}license_awardDate,${symbol_pound}license_periodDate').datebox({
            required:false,
            editable:false
        });
    });
</script>

</body>
</html>