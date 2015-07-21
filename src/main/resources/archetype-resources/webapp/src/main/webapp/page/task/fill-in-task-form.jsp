#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">
        .dialog-content {
            position: relative;
        }
    </style>
    <jsp:include page="/page/include.jsp"/>
    <title>日海通信工程项目管理系统</title>
</head>
<body>

<s:if test="total <= 0 && null == taskForm">
    <div class="container">
        <div class="row">
            <div class="span10">

                当前没有任务，<a href="${symbol_dollar}{basePath}/">返回到工作台</a>
            </div>
        </div>
    </div>

</s:if>

<s:if test="total > 0">
    <div class="container">
        <div class="row">
            <div class="span10">
                <a href="${symbol_dollar}{basePath}/">返回到工作台</a>
                <div id="myTasks" class="easyui-panel" title="我的任务列表(${symbol_dollar}{total})"
                     style="width:auto;height:auto;"
                     data-options="closable:true,
                collapsible:true,minimizable:false,maximizable:false,fit:true,collapsed:true">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>流程名</th>
                            <th>任务名</th>
                            <th>事项</th>
                            <th>发起人</th>
                            <th>发起时间</th>
                            <th>描述</th>
                            <th>所属人</th>
                            <th>已经签领</th>
                            <th>当前节点</th>
                            <th>&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="tasks" id="task">
                            <tr>
                                <td><s:property value="getProcessName(${symbol_pound}task)"/></td>
                                <td><s:property value="${symbol_pound}task.name"/></td>
                                <td><s:property value="getTitle(${symbol_pound}task)"/></td>
                                <td><s:property value="getInitiator(${symbol_pound}task)"/></td>
                                <td><s:property value="${symbol_pound}task.createTime"/></td>
                                <td><s:property value="${symbol_pound}task.description"/></td>
                                <td><s:property value="${symbol_pound}task.owner"/></td>
                                <td><s:property value="${symbol_pound}task.assignee"/></td>
                                <td>
                                    <a onclick="showProcess('<s:property value="${symbol_pound}task.processInstanceId"/>');" href="javascript:;"><s:property value="getActivityName(${symbol_pound}task)"/> </a>
                                </td>
                                <td>
                                    <s:if test="isCurrentTask(${symbol_pound}task)">
                                        当前页面所处理任务
                                    </s:if>
                                    <s:else>
                                        <a href="${symbol_dollar}{basePath}/task/fill-in-task-form.action?taskId=<s:property value="${symbol_pound}task.id"/> ">办理</a>
                                    </s:else>
                                </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>
</s:if>
<s:if test="taskForm != null">
    <div class="container">
        <div class="row">
            <s:form id="fill_in_task_form" action="/task/complete-task.action" method="post" theme="simple">
                ${symbol_dollar}{taskForm}
                <input type="hidden" name="taskId" value="${symbol_dollar}{taskId}" />
                <div class="span10">
                    <a class="easyui-linkbutton" href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}fill_in_task_form').submit();">提交</a>
                </div>
            </s:form>
        </div>
    </div>
</s:if>

<script type="text/javascript">



    function getActiveName(value, row, index) {
        return '<a pid=' + row.processInstanceId + ' href="javascript:;" onclick="showProcess(' + row.processInstanceId + ')">' + value + '</a>';
    }
    function getMyActiveName(value, row, index) {
        return '<a pid=' + row.id + ' href="javascript:;" onclick="showProcess(' + row.id + ')">' + value + '</a>';
    }

    function showProcess(processInstanceId) {
        graphTrace({
            pid: processInstanceId,
            ctx: '${symbol_dollar}{basePath}'
        });
    }

    ${symbol_dollar}(function(){
        ${symbol_dollar}('${symbol_pound}fill_in_task_form').form({
            success : function(data){
                data = evalJSON(data);
                if (!data.result) {
                   alert('后台出错，请联系管理员！');
                   location.reload();
                }


                <s:if test="nextTaskId != null">
                    ${symbol_dollar}.messager.confirm('请确认','提交成功，是否处理下一个任务？',function(r){
                        if (r){
                           location.href = '${symbol_dollar}{basePath}/task/fill-in-task-form.action?taskId=${symbol_dollar}{nextTaskId}';
                        }else{
                            location.href = '${symbol_dollar}{basePath}/';
                        }

                    });
                </s:if>
                <s:else>
                    alert('提交成功，将返回工作台。');
                    location.href = '${symbol_dollar}{basePath}/';
                </s:else>
            }
        });
    });

</script>

<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/process/process-diagram.js"></script>
</body>
</html>


