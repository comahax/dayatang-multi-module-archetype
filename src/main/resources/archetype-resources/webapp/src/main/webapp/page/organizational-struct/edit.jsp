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
<div id="organizational_struct_edit_container"  style="width: 350px;float: left;">

</div>
<div id="organizational_struct_accordion" class="easyui-accordion" style="width:600px;height:auto;">
    <div title="添加下级机构" data-options="iconCls:'icon-add',selected:true" style="overflow:auto;padding:10px;">
        <form id="organizational_struct_edit_addorg_form"
              action="<%=basePath%>organizational-struct/add-child.action">
            <table id="organizational_struct_edit_editorg_table">
                <tbody>
                <tr>
                    <th>名称</th>
                    <td colspan="3"><input name="name" style="width: 300px;" type="text"
                                           class="easyui-validatebox" data-options="required:true"/></td>
                </tr>
                <tr>

                    <th>简称</th>
                    <td colspan="3"><input name="abbr" type="text" class="easyui-validatebox"/>

                        <div class="muted">如果是部门，建议使用公司简称-部门简称</div>
                    </td>
                </tr>

                <tr>
                    <th>邮箱</th>
                    <td><input name="email" type="text" class="easyui-validatebox"
                               data-options="validType:'email'"/></td>
                    <th>地址</th>
                    <td><input name="address" type="text"/></td>
                </tr>
                <tr>
                    <th>电话</th>
                    <td><input name="tel" type="text"/></td>
                    <th>机构类型</th>
                    <td>
                        <select name="internalCategory" class="easyui-combobox">
                            <s:iterator value="internalCategories" id="each">
                                <option value="${symbol_dollar}{each}"><s:property value="getText(${symbol_pound}each)"/></option>
                            </s:iterator>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>主页</th>
                    <td colspan="3"><input name="homePage" type="text" class="span5"/></td>
                </tr>
                </tbody>
            </table>
            <input id="org_struct_add_parentid" name="parentId" type="hidden"/>
            <hr>
            <button type="submit">确定</button>

        </form>
        <div class="alert">已撤销机构不能为其添加下级机构</div>
    </div>
    <div title="修改" data-options="iconCls:'icon-edit'" style="padding:10px;">
        <form id="organizational_struct_edit_form"
              action="<%=basePath%>organizational-struct/edit-submit.action">
            <table id="organizational_struct_edit_table">
                <tbody>
                <tr>
                    <th>名称</th>
                    <td colspan="3"><input name="name" style="width: 300px;" type="text"
                                           class="easyui-validatebox" data-options="required:true"/></td>
                </tr>
                <tr>

                    <th>简称</th>
                    <td colspan="3"><input name="abbr" type="text" class="easyui-validatebox"/>

                        <div class="muted">如果是部门，建议使用公司简称-部门简称。注意公司简称不可修改！</div>
                    </td>
                </tr>

                <tr>
                    <th>邮箱</th>
                    <td><input name="email" type="text" class="easyui-validatebox"
                               data-options="validType:'email'"/></td>
                    <th>地址</th>
                    <td><input name="address" type="text"/></td>
                </tr>
                <tr>
                    <th>电话</th>
                    <td><input name="tel" type="text"/></td>
                    <th>机构类型</th>
                    <td>
                        <select name="internalCategory" class="easyui-combobox">
                            <s:iterator value="internalCategories" id="each">
                                <option value="${symbol_dollar}{each}"><s:property value="getText(${symbol_pound}each)"/></option>
                            </s:iterator>
                        </select>
                    </td>
                </tr>

                <tr>
                    <th>主页</th>
                    <td colspan="3"><input name="homePage" type="text" class="span5"/></td>
                </tr>
                </tbody>
            </table>
            <input id="org_struct_edit_id" name="id" type="hidden"/>
            <hr>
            <button type="submit">确定</button>
            <div class="alert">已撤销机构不能进行修改</div>
        </form>
    </div>
    <div title="调动" data-options="iconCls:'icon-edit'" style="padding:10px;">
        <form id="organizational_struct_edit_move_form" action="${symbol_dollar}{basePath}/organizational-struct/move.action">
            调动到：<input id="organizational_struct_edit_move_parent_id" type="text">
            <input type="hidden" name="parentId" id="organizational_struct_edit_move_form_parent_id" value=""/>
            <input type="hidden" name="id" id="organizational_struct_edit_move_form_id"/>
            <button type="submit">确定</button>
        </form>

    </div>
    <div title="撤销与恢复" style="padding: 20px;">
        <a orgid="" iconCls="icon-cancel" class="easyui-linkbutton" href="javascript:;"
           id="organization_struct_cancel_btn">确定撤销</a>
        &nbsp;&nbsp;&nbsp;
        <a orgid="" iconCls="icon-resume" class="easyui-linkbutton" href="javascript:;"
           id="organization_struct_resume_btn">确定恢复</a>

        <div class="alert alert-info">父机构撤销，其所有子机构也将被撤销</div>
    </div>
    <div title="彻底删除" style="padding: 20px;">
        <a orgid="" iconCls="icon-warn" class="easyui-linkbutton" href="javascript:;"
           id="organization_struct_destroy_btn">确定删除</a>

        <div class="alert alert-info">父机构彻底删除后，，其所有子机构也将被彻底删除，如果其中有机构被引用了，则操作不成功</div>
    </div>
</div>

<script type="text/javascript">
    ${symbol_dollar}(function () {
        //初始化调动标签
        proj_app.internalorg_selector(${symbol_dollar}('${symbol_pound}organizational_struct_edit_move_parent_id'), {onChange: organizational_struct_edit_move_selector_onchange}) ;
        function organizational_struct_edit_move_selector_onchange() {
            var parentId = ${symbol_dollar}('${symbol_pound}organizational_struct_edit_move_parent_id').combotree('getValue');
            ${symbol_dollar}('${symbol_pound}organizational_struct_edit_move_form_parent_id').val(parentId);
        }
        ${symbol_dollar}('${symbol_pound}organizational_struct_edit_move_form').form({
            success: function (data) {
                data = evalJSON(data);
                if (data && data.errorInfo) {
                    alert(data.errorInfo);
                }
                layout_center_refreshTab('编辑机构树');
            }
        });





    var treeObj_${symbol_dollar}{random} = {};
    var url = "<%=basePath%>organizational-struct/tree-json.action";
    treeObj_${symbol_dollar}{random} = initInstitutionalFrameworkTreeMini('${symbol_pound}organizational_struct_edit_container', url, zTreeOnClick);

    function zTreeOnClick(event, treeId, treeNode) {

        ${symbol_dollar}('${symbol_pound}organizational_struct_edit_form').form('load', treeNode);

        //设置添加下级机构所需要的parentId
        ${symbol_dollar}('${symbol_pound}org_struct_add_parentid').val(treeNode.id);

        //设置编辑机构的ID
        ${symbol_dollar}('${symbol_pound}org_struct_edit_id').val(treeNode.id);
        ${symbol_dollar}('${symbol_pound}organizational_struct_edit_category').combobox('setValue', treeNode.internalCategory);


        ${symbol_dollar}('${symbol_pound}organizational_struct_edit_move_form_id').val(treeNode.id);

        //设置删除按钮的机构的ID
        ${symbol_dollar}('${symbol_pound}organization_struct_destroy_btn').attr('orgid', treeNode.id);

        if (treeNode.disabled) {
            show_resume_or_cancel_according();
            show_resume_btn(treeNode.id);
        } else {
            show_eidt_according();
            show_cancel_btn(treeNode.id);
        }

    }
    ;

    //显示撤销与恢复according
    function show_resume_or_cancel_according() {
        ${symbol_dollar}('${symbol_pound}organizational_struct_accordion').accordion('select', 2);
    }

    //显示修改according
    function show_eidt_according() {
        ${symbol_dollar}('${symbol_pound}organizational_struct_accordion').accordion('select', 1);
    }


    //处理彻底删除
    ${symbol_dollar}('${symbol_pound}organization_struct_destroy_btn').click(function () {
        var self = ${symbol_dollar}(this);
        if (!confirm('确定删除？')) {
            return;
        }
        var url = '<%=basePath%>organizational-struct/destroy.action?id=' + self.attr('orgid');
        var data = getJSON(url);

        if (!data || !data.errorInfo) {
            var nodes = treeObj_${symbol_dollar}{random}.getNodes();
            if (nodes.length > 0) {
                treeObj_${symbol_dollar}{random}.removeNode(get_select_node());
                treeObj_${symbol_dollar}{random}.selectNode(nodes[0]);
                ${symbol_dollar}('${symbol_pound}organizational_struct_accordion').accordion('select', 1);
                return;
            }
        } else {
            alert(data.errorInfo);
        }


    });


    //处理撤销机构
    ${symbol_dollar}('${symbol_pound}organization_struct_cancel_btn').click(function () {
        var self = ${symbol_dollar}(this);
        if (!confirm('确定撤销？')) {
            return;
        }
        var url = '<%=basePath%>organizational-struct/cancel.action?id=' + self.attr('orgid');
        var data = getJSON(url);

        if (data.errorInfo) {
            alert(data.errorInfo);
            return;
        }

        if (data && data.result) {
            ${symbol_dollar}.extend(get_select_node(), data.result);
            show_resume_btn(data.result.id);
            return;
        }
        alert('撤销失败');
    });

    //处理恢复机构
    ${symbol_dollar}('${symbol_pound}organization_struct_resume_btn').click(function () {
        var self = ${symbol_dollar}(this);
        var url = '<%=basePath%>organizational-struct/resume.action?id=' + self.attr('orgid');
        var data = getJSON(url);
        if (data && data.result) {
            ${symbol_dollar}.extend(get_select_node(), data.result);
            show_cancel_btn(data.result.id);
            return;
        }
        alert('恢复失败');
    });

    //显示恢复按钮
    function show_resume_btn(org_id) {
        ${symbol_dollar}('${symbol_pound}organization_struct_resume_btn').attr('orgid', org_id);
        ${symbol_dollar}('${symbol_pound}organization_struct_cancel_btn').hide();
        ${symbol_dollar}('${symbol_pound}organization_struct_resume_btn').show();
    }

    //显示撤销按钮
    function show_cancel_btn(org_id) {
        ${symbol_dollar}('${symbol_pound}organization_struct_cancel_btn').attr('orgid', org_id);
        ${symbol_dollar}('${symbol_pound}organization_struct_cancel_btn').show();
        ${symbol_dollar}('${symbol_pound}organization_struct_resume_btn').hide();
    }

    //处理编辑机构
    ${symbol_dollar}('${symbol_pound}organizational_struct_edit_form').form({
        onSubmit: function () {
            if (!get_select_node()) {
                alert('请在机构树选择需要编辑的机构!');
                return false;
            }
        },
        success: function (data) {
            data = evalJSON(data);
            if (data && data.result) {
                ${symbol_dollar}.extend(get_select_node(), data.result);
                return;
            }
            alert('编辑失败');
            return;
        }
    });

    //处理添加下级机构
    ${symbol_dollar}('${symbol_pound}organizational_struct_edit_addorg_form').form({
        onSubmit: function (param) {
            var node = get_select_node();
            if (!node) {
                alert('在左栏选择父机构后才能为其添加子机构！');
                return;
            }
        },
        success: function (data) {
            data = evalJSON(data);
            if (data && data.result) {
                ${symbol_dollar}('${symbol_pound}organizational_struct_edit_addorg_form').trigger('reset');
                treeObj_${symbol_dollar}{random}.addNodes(get_select_node(), data.result);
            } else {
                alert('添加失败');
            }
        }
    });

    function get_select_nodes() {
        return treeObj_${symbol_dollar}{random}.getSelectedNodes();
    }

    function get_select_node() {
        var nodes = get_select_nodes();
        if (!nodes) {
            return {};
        }
        return nodes[0];
    }

    });

</script>
</body>
</html>