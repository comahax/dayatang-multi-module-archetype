#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/page/include.jsp"/>

<s:set id="random" value="randomStr"/>
<div class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'west',split:true" title="导入" style="width:250px;height:700px;padding: 10px;">
        <h3>导入步骤</h3>
        <p>一、<a
                target="_blank"
                href="${symbol_dollar}{basePath}/subproject/download-import-template.action?id=${symbol_dollar}{id}">下载</a>
            excel模板(下载后的模板文件的文件名不能修改！)</p>

        <p>二、向Excel模板写入内容</p>
        <p>三、上传填写好的excel</p>
        <div  id="of_project_import_subprojects_upload_file_${symbol_dollar}{random}" ></div>
        <a class="easyui-linkbutton"
           data-options="iconCls:'icon-upload'"
           href="javascript:;"
           onclick="${symbol_dollar}('${symbol_pound}of_project_import_subprojects_upload_file_${symbol_dollar}{random}').uploadify('upload','*');">上传</a>&nbsp;
        <script type="text/javascript">
            ${symbol_dollar}(function(){

                var upload_container = ${symbol_dollar}("${symbol_pound}of_project_import_subprojects_upload_file_${symbol_dollar}{random}");
                //开启附件上传
                upload_container.one('focus', function(){
                    uploadfile("${symbol_pound}of_project_import_subprojects_upload_file_${symbol_dollar}{random}",'${symbol_dollar}{basePath}/subproject/import.action',{
                        'formData':{
                            'projectId': '${symbol_dollar}{id}'
                        },
                        'onUploadSuccess': function (file, data, response) {

                            data = evalJSON(data);
                            var errors = data.result.errorInfos;
                            if(!errors){
                                imessager.success_tip();
                                ${symbol_dollar}('${symbol_pound}subproject_list_of_project_table_${symbol_dollar}{random}').datagrid('reload');
                                return;
                            }
                            var showError ='';
                            ${symbol_dollar}.each(errors, function(i, v){
                                showError += (v.row + 1) + '行:' + v.error + '<br/>';
                            });
                            showError = showError.length > 0 ? showError :'添加成功';

                            ${symbol_dollar}('${symbol_pound}import_subprojects_result_${symbol_dollar}{random}').html(showError);
                            ${symbol_dollar}('${symbol_pound}subproject_list_of_project_table_${symbol_dollar}{random}').datagrid('loadData',{rows:[]});
                            ${symbol_dollar}('${symbol_pound}subproject_list_of_project_table_${symbol_dollar}{random}').datagrid('reload');
                        }
                    });
                });
                upload_container.focus();

            });
        </script>
        <div class="alert alert-info">提示：请务必按导入步骤导入，以免导入失败</div>
        <p>导入结果：
        <div id="import_subprojects_result_${symbol_dollar}{random}" class="alert"></div>
        </p>
    </div>
    <div data-options="region:'center',title:'单点列表'" style="padding:10px;height:700px">
        <table id="subproject_list_of_project_table_${symbol_dollar}{random}" class="easyui-datagrid"
               fitColumns="true"
               singleSelect="false"
               rownumbers="true"
               pagination="true"
               pageSize="20"
               width="auto"
               toolbar="${symbol_pound}subproject_of_project_toolbar_${symbol_dollar}{random}"
               url="${symbol_dollar}{basePath}/subproject/list-json.action?id=${symbol_dollar}{id}"
                >
            <s:include value="list-title-tpl.jsp"/>
        </table>
        <div id="subproject_of_project_toolbar_${symbol_dollar}{random}">
            <a onclick="subproject_of_project_add_btn_handler_${symbol_dollar}{random}();" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
            <a onclick="subproject_of_project_remove_btn_handler_${symbol_dollar}{random}();" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
        </div>
        <script type="text/javascript">
            function subproject_of_project_add_btn_handler_${symbol_dollar}{random}(){
                ${symbol_dollar}('${symbol_pound}subproject_add_dialog_${symbol_dollar}{random}').dialog('open');
            }

            function subproject_of_project_remove_btn_handler_${symbol_dollar}{random}(){
                if(!confirm("确定删除")){
                    return;
                }
                var grid = ${symbol_dollar}('${symbol_pound}subproject_list_of_project_table_${symbol_dollar}{random}');
                var rows = grid.datagrid('getSelections');
                if(!rows){
                    return;
                }
                ${symbol_dollar}.each(rows, function(i, v){
                    var id = v.id;
                    var result = getJSON('${symbol_dollar}{basePath}/subproject/destroy.action?projectId=${symbol_dollar}{id}&id=' + id);
                });
                ${symbol_dollar}('${symbol_pound}subproject_list_of_project_table_${symbol_dollar}{random}').datagrid('reload');
                imessager.success_tip();
            }
        </script>

    </div>
</div>
<s:include value="add.jsp"/>
