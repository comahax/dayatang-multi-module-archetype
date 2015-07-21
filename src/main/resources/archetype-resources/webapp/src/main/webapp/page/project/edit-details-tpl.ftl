<a href="javascript:;" onclick="$('#details_info_edit_dialog_${project.id.toString()}').dialog('open');" 
	class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>

<div id="details_info_edit_dialog_${project.id.toString()}" class="easyui-dialog" title="编辑项目基本信息" 
	style="width:500px;height:500px;"
     data-options="iconCls:'icon-edit',resizable:true,modal:true,closed: true">
    <form id="details_info_edit_draft_form_${project.id.toString()}" 
    	action="${basePath}/project/edit-detail.action" method="post">
        <p/>
        	项目名:
        <input class="easyui-validatebox" data-options="required:true" name="name" type="text" value="${project.name}"
               style="width:400px"/>
        <p/>

        	以哪家公司名义开展：

        <input  value="<#if project.constructingOrg?exists>${project.constructingOrg.id}</#if>" name="constructingOrgId" type="text">

        <p/>
      		  区域:<input name="areaId" value="${project.area.id}"/>
        <p>
           	 负责部门: <input name="responsibleDivisionId" type="text"/>
        </p>
        <p>
           	 客户单位: <input name="ownerId" type="text"/>
        </p>
        <p>
           	 开工日期: <input name="startDate" type="text" value="<#if project.startDate?exists>${project.startDate}</#if>"/>
           	 计划完工日期: <input name="predictFinishDate" value="<#if project.predictFinishDate?exists>${project.predictFinishDate}</#if>" type="text"/>
        </p>
       		 项目编号:
        <span >${project.projectNumber!"之前未设置项目编码"}</span>
        <span class="muted">(由系统自动生成。项目编码规则＝公司代码 + 客户代码 + 项目区域代码 + 业务类型代码 + 年份代码(13) + 3位流水号)</span>
        <p/>
       		 项目类型:
        <input id="details_projectType_${project.id.toString()}" name="projectType" type="text"/>
        <p/>


		

        	备注：<br/>
        <textarea name="remark" cols="30" rows="3">${project.remark!""}</textarea>
        <hr/>
        <input  name="id" type="hidden" value="${project.id.toString()}">
        <button type="submit">
            保存为草稿
        </button>
    </form>

</div>

<script type="text/javascript">
    $(function(){

        var form = $('#details_info_edit_draft_form_${project.id.toString()}');

        //业主单位
        proj_app.owner_selector(form.find('input[name="ownerId"]'));

        //区域
        var area_id = form.find('input[name="areaId"]');
        proj_app.area_selector(area_id);


        //负责单位
        var responsible_id = form.find('input[name="responsibleDivisionId"]');
        proj_app.organizational_scope(responsible_id);

        //开工日期，谋利完工日期
        var startbox = form.find('input[name="startDate"]');
        var finishedbox = form.find('input[name="predictFinishDate"]');
        proj_app.start_finished_date_box({
            startbox: startbox,
            startbox_requied: true,
            finishedbox: finishedbox,
            finishedbox_requied: true
        });

        //项目类型
        proj_app.project_type_selector('#details_projectType_${project.id.toString()}').combobox('setValue','${project.projectType}');


        //以谁名义开展项目
        proj_app.constructing_party('input[name="constructingOrgId"]');

        form.form({
            success : function(data){
                data = evalJSON(data);
                if (data && data.errorInfo) {
                    imessager.alert( data.errorInfo);
                    return;
                }
                $('#details_info_edit_dialog_${project.id.toString()}').dialog('close');
                layout_center_refreshTab('${project.name}');
                return;
            }
        });

    });

</script>