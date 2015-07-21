
    <#include "/page/scripts.ftl"/>

<div style="clear: both;">
    项目当前状态：${project.status.cnText} &nbsp;
	<#if applyable>
	<a id="project_details_top_toolbar"
          href="javascript:;"
          class="easyui-linkbutton">提交审批</a>
        <script type="text/javascript">
            $(function(){
                $('#project_details_top_toolbar').click(function(){
                    var data = getJSON('${basePath}/project/pre-submit.action?id=${project.id.toString()}');
                    if(data && data.errorInfo ){
                        imessager.alert(data.errorInfo || '后台出错，请联系管理员');
                    }else{
                        layout_center_refreshTab('${project.name}');
                    }
                });
            });
        </script>
	</#if>

    <a href="javascript:;" class="easyui-menubutton"
       data-options="menu:'#project_details_other_operations'"> 其他操作</a>
    <div id="project_details_other_operations" style="width:150px;">
        <#if project.finishable>
        <div id="project_details_finish_btn" data-options="iconCls:'icon-blank'">
            	 竣工
        </div>
        </#if>

         <#if project.closeable >
        <div id="project_details_close_btn" data-options="iconCls:'icon-no'">
           		 结项
        </div>
        </#if>

        <#if project.terminatedable >
        <div id="project_details_terminated_btn" data-options="iconCls:'icon-cancel'">
           		 中止项目(意外中止)
        </div>
        </#if>
        <div class="menu-sep"></div>
        <div id="project_details_destroy_btn" data-options="iconCls:'icon-problem'">
          		  彻底删除项目
        </div>
    </div>
</div>


<#if  project.finishable >
<div id="project_details_finish_dialog" class="easyui-dialog" title="请录入竣工日期" style="width:300px;height:150px;padding: 10px 0 0 10px;"
     data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">
    <form id="project_details_finish_form" action="${basePath}/project/finish.action" method="post" >
        <label>竣工日期：</label>
        <input name="date" type="text" class="easyui-datebox" date-options="required:true"/>
        <br/>
        <input type="hidden" name="id" value="${project.id.toString()}" />
        <input type="submit" value="确定"/>
    </form>
    <script type="text/javascript">
        $(function(){
            $('#project_details_finish_form').form({
                success : function(data){
                    data = evalJSON(data);
                    if(data.result){
                        var project_details_finish_dialog = $('#project_details_finish_dialog');
                        if(project_details_finish_dialog){
                            project_details_finish_dialog.dialog('close');
                        }

                        layout_center_refreshTab('${project.name}');
                    }else{
                        imessager.alert("您没有权限设置项目竣工! ");
                        return;
                    }
                }
            });

            $('#project_details_finish_btn').click(function(){
                $('#project_details_finish_dialog').dialog('open');
            });
        });
    </script>
</div>
</#if>


<#if  project.closeable >
<div id="project_details_close_dialog" class="easyui-dialog" title="请录入项目结项日期" style="width:300px;height:150px;padding: 10px 0 0 10px;"
     data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">

    <form id="project_details_close_form" action="${basePath}/project/close.action" method="post">
        <label>结项日期：</label>
        <input name="date" type="text" class="easyui-datebox"  date-options="required:true"/>
        <br/>
        <input type="hidden" name="id" value="${project.id.toString()}" />
        <input type="submit" value="确定"/>
    </form>
    <script type="text/javascript">
        $(function(){
            $('#project_details_close_form').form({
                success : function(data){
                    data = evalJSON(data);
                    if(data.result){
                        var project_details_close_dialog = $('#project_details_close_dialog');
                        if(project_details_close_dialog){
                            project_details_close_dialog.dialog('close');
                        }
                        imessager.success_tip("保存成功");
                        layout_center_refreshTab('${project.name}');
                    }else{
                        imessager.alert("您没有权限结项");
                        return;
                    }
                }
            });

            $('#project_details_close_btn').click(function(){
                $('#project_details_close_dialog').dialog('open');
            });

        });
    </script>
</div>
</#if>

<#if  project.terminatedable >
<div id="project_details_terminated_dialog" class="easyui-dialog" title="中止项目" style="width:300px;height:150px;padding: 10px 0 0 10px;"
     data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">

    <form id="project_details_terminated_form" action="${basePath}/project/terminated.action" method="post">
        <label>中止日期：</label>
        <input name="date" type="text" class="easyui-datebox"  date-options="required:true"/>
        <br/>
        <label>备注</label>
        <textarea name="remark"></textarea>
        <br/>
        <input type="hidden" name="id" value="${project.id.toString()}" />
        <input type="submit" value="确定"/>
    </form>
    <script type="text/javascript">
        $(function(){
            $('#project_details_terminated_form').form({
                success : function(data){
                    data = evalJSON(data);
                    if(data.result){
                        var project_details_terminated_dialog = $('#project_details_terminated_dialog');
                        if(project_details_terminated_dialog){
                            project_details_terminated_dialog.dialog('close');
                        }
                        layout_center_refreshTab('${project.name}');
                    }else{
                        imessager.alert("您没有权限中止项目");
                        return;
                    }
                }
            });

            $('#project_details_terminated_btn').click(function(){
                $('#project_details_terminated_dialog').dialog('open');
            });

        });

    </script>
</div>
</#if>

<script type="text/javascript">
    $(function(){
        $('#project_details_destroy_btn').click(function(){
            imessager.confirm('删除将无法恢复，请确认是否删除？', function(yes){
                if(!yes){
                    return;
                }
                var data = getJSON('${basePath}/project/destroy.action?id=${project.id.toString()}');
                if(data.result){
                    layout_center_close_current_tab('${project.name}');
                }else{
                    imessager.alert("操作失败");
                }
            });
        });
    });
</script>

<div  class="easyui-tabs">
    <div title="收支预算情况">
        <iframe src="${basePath}/project/payments.action?id=${project.id.toString()}" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>
    </div>
    <#if project.businessOperationsable || project.draft>
        <div title="收支预算变更"
             data-options="href : '${basePath}/project/change-capital-budget-entry.action?id=${project.id.toString()}'">
        </div>
    </#if>
    <div title="基本信息" >
        <iframe src="${basePath}/project/details-info.action?id=${project.id.toString()}" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>
    </div>
    <div title="分包管理" href="${basePath}/project/subpackages.action?id=${project.id.toString()}">
    </div>
    
  <#--  <div title="单点工程" style="padding:10px"
         data-options="href:'${basePath}/subproject/list.action?id=${project.id.toString()}'">
    </div>-->


    <div title="项目文件">
        <iframe src="${basePath}/project/docs.action?id=${project.id.toString()}" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>
    </div>
</div>
