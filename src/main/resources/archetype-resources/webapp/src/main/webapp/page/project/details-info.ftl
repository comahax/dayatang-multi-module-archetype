<#setting date_format="${dateFormat}">
<html>
<head>
<#include "/page/scripts.ftl"/>
</head>
<body>
<div id="project_details${project.id.toString()}" style="padding: 0 0 30px 18px;">
    <div class="span3">
        工期进度－ 剩余天数：${project.remainingDuration!0}
        <div style="width:90%" id="project_details_project_process"></div>
    </div>
    <div class="span3">
        收款进度
        总包发票开票总额：<span id="project_details_totalreceiptinvoice_amount"></span>
        <br/>
        收款总和：<span id="project_details_totalreceipt_amount"></span>

        <div class="easyui-progressbar" data-options="value:${receiptRatio!0},text:' 回款率：{value}%'" style="width:300px;"></div>
    </div>

    <div style="clear:both;margin-top: 20px;">
        项目名称：${project.name}
        <p/>
    <#if project.constructingOrg?exists>
        <#if project.constructingOrg.id == grantedScope.company.id>
            以本公司名义开展
        <#else>
            挂靠公司：<b>${project.constructingOrg.name}</b>
        </#if>
        <p/>
    <#else>
        <form id="project_details_info_setconstructingOrg_form"
              action="${basePath}/project/set-constructing.action">
            <div class="alert alert-error">
                请设置挂靠公司，如果是以本公司名义开展的项目，选择本公司。挂靠公司指以哪家公司的名义开展项目。
            </div>
            挂靠公司：<input value="<#if project.constructingOrg?exists>${project.constructingOrg.id}</#if>" name="constructingOrgId" type="text">
            <input type="hidden" value="${project.id.toString()}" name="projectId"/>
            <input type="submit" value="保存"/>
        </form>
        <script type="text/javascript">
            $(function () {
                var form = $('#project_details_info_setconstructingOrg_form');
                proj_app.constructing_party(form.find('input[name="constructingOrgId"]'));
                form.form({
                    success: function (data) {
                        data = evalJSON(data);
                        if (data.name) {
                            form.before('<div>以<b>' + data.name + '</b>名义开展项目</div>');
                            form.remove();
                        } else {
                            alert(data.errorInfo);
                        }
                    }
                });
            });
        </script>
    </#if>
        客户单位：${ownerName}
    <#if ownerContactName?exists>
        &nbsp;<a onclick="show_outer_person_info_dialog('${ownerContactId!0}');"
                 href="javascript:;">${ownerContactName}</a>
    </#if>
        <p/>
        区域：<span>${project.area.fullName!""}</span>

        <p/>
        负责部门：<span id="project_details_info_responsible_division">${project.responsibleDivision.fullName}</span>
    	<#if project.constructing >
    		<a id="project_detail_info_change_responsible_btn" href="javascript:;" onclick="project_detail_info_change_responsible_dialog_open();" >更改</a>
    		<div id="project_detail_info_change_responsible_dialog" class="easyui-dialog" closed="true" style="width:400px;height:200px;" title="更改负责单位">
    			负责单位：<input id="project_detail_info_change_responsible_id" name="responsibleDivisionId" type="text"/>
    			<a href="javascript:;" onclick="changeResponsibleDivision();" >确定</a>
    		</div>
    		  <script type="text/javascript">
    			$(function(){
    				//负责单位
			        proj_app.organizational_scope('#project_detail_info_change_responsible_id');
    			});
    			function changeResponsibleDivision(){
    				var responsible_id = $('#project_detail_info_change_responsible_id').combotree('getValue');
    				var result = getJSON('${basePath}/project/change-responsible-division.action?responsibleDivisionId=' + responsible_id + '&id=${project.id.toString()}');
    				if(result && result.errorInfo){
    					alert(result.errorInfo);
    					$('#project_detail_info_change_responsible_dialog').dialog('close');
    				}else{
    					$('#project_details_info_responsible_division').html(result.fullName);
    					$('#project_detail_info_change_responsible_btn').remove();
    					$('#project_detail_info_change_responsible_dialog').dialog('destroy')
    					
    				}
    			}
                function project_detail_info_change_responsible_dialog_open(){
                    $('#project_detail_info_change_responsible_dialog').dialog('open');
                }
    		</script>
    	</#if>

        <p/>
        开工日期：

    <#if project.startDate?exists>
    ${project.startDate!""}
    <#else>
        <form id="project_details_info_setstartdate_form"
              action="${basePath}/project/set-start-date.action" method="post">
            <input name="startDate" class="easyui-datebox" date-options="required: true" type="text"/>
            <input type="hidden" name="projectId" value="${project.id.toString()}"/>
            <input type="submit" value="保存"/>
        </form>
        <script type="text/javascript">
            $(function () {
                var form = $('#project_details_info_setstartdate_form');
                form.form({
                    success: function (data) {
                        data = evalJSON(data);
                        if (data.errorInfo) {
                            alert(data.errorInfo);
                        } else {
                            var date = form.find('input[name="startDate"]').val();
                            form.before(date);
                            form.remove();
                        }
                    }//end success
                });//end form
            });
        </script>
    </#if>
        &nbsp;&nbsp;

    <#if project.predictFinishDate?exists>
        计划完工日期：${project.predictFinishDate!""}
    <#else>
        计划完工日期：
        <form id="project_details_info_setpredictFinishDate_form"
              action="${basePath}/project/set-predict-finish-date.action" method="post">
            <input name="predictFinishDate" class="easyui-datebox" date-options="required: true" type="text"/>
            <input type="hidden" name="projectId" value="${project.id.toString()}"/>
            <input type="submit" value="保存"/>
        </form>
        <script type="text/javascript">
            $(function () {
                var form = $('#project_details_info_setpredictFinishDate_form');
                form.form({
                    success: function (data) {
                        data = evalJSON(data);
                        if (data.errorInfo) {
                            alert(data.errorInfo);
                        } else {
                            var date = form.find('input[name="predictFinishDate"]').val();
                            form.before(date);
                            form.remove();
                        }
                    }//end success
                });//end form
            });
        </script>
    </#if>
        <p/>
        竣工日期：${project.finishDate!""}
        <p/>
        结项日期：${project.closeDate!""}
        <p/>
        项目编号：<span>${project.projectNumber!""}</span>

        <p/>
        项目类型：<span>${projectType!""}</span>
        <p/>
        备注：${project.remark!""}
        <p/>

    <#if project.draft >
        <#include "edit-details-tpl.ftl"/>
    </#if>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var current_date = new Date();
        $('#project_details_project_process').html(
                create_project_duration_processer('${project.durationProcesser}', {
                    startDate: '${project.startDate!""}',
                    predictFinishDate: '${project.predictFinishDate!""}'
                }));
        $('#project_details_totalreceipt_amount').html('${totalReceiptAmount!0}元');
        $('#project_details_totalreceiptinvoice_amount').html('${totalReceiptInvoiceAmount!0}元');
    });

    /**
     * 返回一个工期进度条
     * @param value
     * @return {*|jQuery}
     */
    function create_project_duration_processer(value, options) {
        var settings = $.extend({
            tip: '',
            startDate: '',
            predictFinishDate: ''
        }, options || {});

        var color = '#7fb80e';
        if (value > 70 && value < 100) {
            color = '#cc0000';
        } else if (value == 100) {
            color = 'blue';
        } else {
            color = '#7fb80e'
        }

        var result = '<div style="clear:both;width:100%">' + settings.tip + '</div>';

        result += '<div style="width:100%;border:1px solid #ccc;">' +
                '<div style="clear: both;width:' + value + '%;background:' + color + ';color:#fff">' + value + '%</div>';

        result += '<div style="clear: both;"><span style="float: left;">' + settings.startDate + '</span>' +
                '<span style="float: right;">' + settings.predictFinishDate + '</span></div>';
        return result;

    }
</script>
 </body>
</html>
