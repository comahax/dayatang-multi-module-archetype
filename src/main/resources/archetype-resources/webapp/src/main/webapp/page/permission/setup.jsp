#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',split:true" style="width:320px; height: 600px;">
        <ul id="permission_tree" class="ztree"></ul>
    </div>
    <div data-options="region:'center'" style="padding:5px;background:${symbol_pound}eee;">
        <div class="easyui-accordion" style="width:auto;height:350px;">
            <div title="添加下级权限" data-options="iconCls:'icon-add'" style="overflow:auto;padding:10px;">
                 <form id="permission_add_form" action="${symbol_dollar}{basePath}/permission/add.action" method="post">
                     <p>
                         权限名称(必须英文)：<input name="name" id="permission_add_form_input_name" type="text" class="easyui-validatebox" data-options="required:true"/>
                     </p>
                     <p>
                         权限描述(建议中文)：<input name="description" type="text" class="easyui-validatebox" data-options="required:true"/>
                     </p>
                     <p>
                         序号：<input class="easyui-numberspinner" style="width:50px;" data-options="min:0,max:100,increment:1,precision:0">
                     </p>
                     是否为根权限：<input id="permission_add_form_parent_id" type="checkbox" value="0" name="id"/>
                     <a iconCls="icon-save" href="javascript:;" onclick="permission_add_form_submit();" class="easyui-linkbutton">提交</a>
                 </form>
                 <script>
                     function permission_add_form_submit(){
                         var treeObj = ${symbol_dollar}.fn.zTree.getZTreeObj('permission_tree');
                         var form = ${symbol_dollar}('${symbol_pound}permission_add_form');
                         form.form('submit', {
                             onSubmit : function(param){
                                 var nodes = treeObj.getSelectedNodes();
                                 if(nodes.length <= 0){
                                    return;
                                 }
                                 param.id = nodes[0].id;
                             },
                             success: function (data) {
                                 data = evalJSON(data);
                                 var node = data.result;
                                 var parentNode = {} ;
                                 if (!${symbol_dollar}('${symbol_pound}permission_add_form_parent_id').val()) {
                                     parentNode = null;
                                 } else if (treeObj.getSelectedNodes().length > 0) {
                                     parentNode = treeObj.getSelectedNodes()[0];
                                 }
                                 treeObj.addNodes(parentNode, node);
                                 node.isParent = true;
                                 form.form('reset');
                                 imessager.success_tip();
                             }
                         });
                     }
                 </script>

            </div>
            <div title="编辑" data-options="iconCls:'icon-edit'" style="padding:10px;">
                <form id="permission_edit_form" action="${symbol_dollar}{basePath}/permission/edit.action" method="post">
                    <p>
                        权限名称(必须英文)：<input name="name" type="text" class="easyui-validatebox" data-options="required:true"/>
                    </p>
                    <p>
                        权限描述(建议中文)：<input name="description" type="text" class="easyui-validatebox" data-options="required:true"/>
                    </p>
                    <p>
                        序号：<input id="permission_edit_form_sortorder" class="easyui-numberspinner" style="width:50px;" data-options="min:0,max:100,increment:1,precision:0">
                    </p>
                    <input id="permission_edit_form_origin_id" name="id" type="hidden" />
                    <a iconCls="icon-save" href="javascript:;" onclick="permission_edit_form_submit();" class="easyui-linkbutton">提交</a>

                </form>

                <script type="text/javascript">
                    function permission_edit_form_submit(){
                        ${symbol_dollar}('${symbol_pound}permission_edit_form').form('submit', {
                            success: function (data) {
                                var treeObj = ${symbol_dollar}.fn.zTree.getZTreeObj("permission_tree");
                                var nodes = treeObj.getSelectedNodes();
                                if (nodes.length <= 0) {
                                    return;
                                }
                                data = evalJSON(data);
                                var node = nodes[0];
                                var result =  data.result;
                                node.name = result.name;
                                node.description = result.description;
                                node.sortOrder = result.sortOrder;
                                data.isParent = true;
                                imessager.success_tip();
                            }
                        });
                    }
                </script>
            </div>
            <div title="删除" data-options="iconCls:'icon-warn'" style="padding:10px;">

                <a href="javascript:;" onclick="permission_remove_handler();" iconCls="icon-warn" class="easyui-linkbutton">删除</a>
                <script type="text/javascript">
                     function permission_remove_handler(){
                         if (!confirm('确定删除？')) {
                             return;
                         }
                         var treeObj = ${symbol_dollar}.fn.zTree.getZTreeObj('permission_tree');
                         var nodes = treeObj.getSelectedNodes();
                         if(nodes.length <= 0){
                             return;
                         }
                         var id = nodes[0].id;

                         var url = '${symbol_dollar}{basePath}/permission/destroy.action?id=' + id;
                         getJSON(url);
                         treeObj.removeNode(nodes[0]) ;


                     }
                </script>

            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    ${symbol_dollar}(function(){
        proj_app.init_permissions_tree('${symbol_pound}permission_tree', getSettings());

        function getSettings(){
            return {
                callback:{
                    onClick: function(event, treeId, treeNode){
                        //设置编辑表单中的ID字段
                        ${symbol_dollar}('${symbol_pound}permission_edit_form_origin_id').val(treeNode.id);
                        ${symbol_dollar}('${symbol_pound}permission_edit_form').form('load', treeNode);
                        ${symbol_dollar}('${symbol_pound}permission_edit_form_sortorder').numberspinner('setValue', treeNode.sortOrder);
                        ${symbol_dollar}('${symbol_pound}permission_add_form_input_name').val(treeNode.name);
                    }
                }
            };
        }

    });

</script>
</body>
</html>