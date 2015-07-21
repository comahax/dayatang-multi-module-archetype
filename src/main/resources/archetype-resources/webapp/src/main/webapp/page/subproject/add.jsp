#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--添加单点弹出框--%>

<div id="subproject_add_dialog_${symbol_dollar}{random}"
     class="easyui-dialog"
     title="添加单点" style="width:700px;height:600px;padding:20px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,onClose: subproject_add_dialog_${symbol_dollar}{random}_close_handler">
    <form id="subproject_add_dialog_form_${symbol_dollar}{random}" action="${symbol_dollar}{basePath}/subproject/add-submit.action" method="post">
        单点名称：
        <input name="name" class="span4 " class="easyui-validatebox" data-options="required:true" />
        <p/>
        负责部门： <input id="subproject_add_dialog_responsible_${symbol_dollar}{random}" name="responsibleDivisionId" type="text"/>
        <script type="text/javascript">
            ${symbol_dollar}(function(){
                proj_app.organizational_scope('${symbol_pound}subproject_add_dialog_responsible_${symbol_dollar}{random}',{required: false});
            });
        </script>
        <p/>
        区域：  <input name="areaId" id="subproject_add_dialog_areainput_${symbol_dollar}{random}" class="span3"/>
        <p/>
        开工日期：<input id="subproject_add_dialog_startdate_${symbol_dollar}{random}"  name="startDate"  />&nbsp;&nbsp;
        计划完工日期：<input id="subproject_add_dialog_predictFinishDate_${symbol_dollar}{random}"  name="predictFinishDate" />
        <br/>
        完工日期：<input id="subproject_add_dialog_finishDate_${symbol_dollar}{random}" name="finishDate" class="easyui-datebox"/>
        <p>
            站点类型：<input id="subproject_add_apType_input_${symbol_dollar}{random}" name="apType" type="text"/>
        </p>
        <p>
            设计单位：
            <input id="design_org_input_${symbol_dollar}{random}" name="designOrgId" type="text"/>
        </p>
        <p>
            监理单位：
            <input id="supervisor_org_input_${symbol_dollar}{random}" name="supervisorOrgId" type="text"/>
        </p>
        <p/>
        地址：<input type="text" name="address" class="span4"/>
        <p/>

        备注：<textarea name="remark"></textarea>

        <div id="subproject_add_dialog_upload_${symbol_dollar}{random}"> </div>

        <script type="text/javascript">
            ${symbol_dollar}(function(){

                //开工日期，谋利完工日期
                var startbox =${symbol_dollar}('${symbol_pound}subproject_add_dialog_startdate_${symbol_dollar}{random}');
                var predictFinishedbox = ${symbol_dollar}('${symbol_pound}subproject_add_dialog_predictFinishDate_${symbol_dollar}{random}');
                proj_app.start_finished_date_box({
                    startbox: startbox,
                    startbox_requied: false,
                    finishedbox: predictFinishedbox,
                    finishedbox_requied: false
                });

                //接入类型
                proj_app.aptype_selector('${symbol_pound}subproject_add_apType_input_${symbol_dollar}{random}');

                //设计单位
                proj_app.designorg_selector('${symbol_pound}design_org_input_${symbol_dollar}{random}');

                //监理单位
                proj_app.supervisor_selector('${symbol_pound}supervisor_org_input_${symbol_dollar}{random}');

                //上传附件
                uploadfile('${symbol_pound}subproject_add_dialog_upload_${symbol_dollar}{random}','${symbol_dollar}{basePath}/subproject/upload-docs.action',{
                    'buttonText': '上传附件'
                });


                //区域
                proj_app.area_selector('${symbol_pound}subproject_add_dialog_areainput_${symbol_dollar}{random}',{required: false});
            });

        </script>

        <input name="projectId" type="hidden" value="${symbol_dollar}{id}">

        <input id="subproject_add_subProjectId_${symbol_dollar}{random}" name="id" type="hidden">
        <hr/>
        <a href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}subproject_add_dialog_form_${symbol_dollar}{random}').trigger('reset');" class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>&nbsp;&nbsp;&nbsp;
        <a href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}subproject_add_dialog_form_${symbol_dollar}{random}').submit();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交</a>
    </form>
</div>


<script type="text/javascript">

    function subproject_add_dialog_${symbol_dollar}{random}_close_handler(){
        ${symbol_dollar}('${symbol_pound}subproject_add_dialog_form_${symbol_dollar}{random}').trigger('reset');
        ${symbol_dollar}('${symbol_pound}subproject_list_table_${symbol_dollar}{random}').datagrid('reload');
    }

    ${symbol_dollar}(function(){
        ${symbol_dollar}('${symbol_pound}subproject_add_dialog_form_${symbol_dollar}{random}').form({
            success : function(data){
                data = evalJSON(data);
                if(data && data.errorInfo){
                    imessager.alert(data.errorInfo);
                    return;
                }

                //设置单点的id属性
                ${symbol_dollar}('${symbol_pound}subproject_add_subProjectId_${symbol_dollar}{random}').attr('value', data.id);

                var id =  ${symbol_dollar}('${symbol_pound}subproject_add_subProjectId_${symbol_dollar}{random}').val();

                if( id ) {
                    var upload = ${symbol_dollar}('${symbol_pound}subproject_add_dialog_upload_${symbol_dollar}{random}');
                    upload.uploadify('settings', 'uploader', '${symbol_dollar}{basePath}/subproject/upload-docs.action');
                    upload.uploadify('settings', 'formData', {'id' : id});
                    upload.uploadify('upload', '*');
                }
                imessager.success_tip();

            }
        });
    });

</script>