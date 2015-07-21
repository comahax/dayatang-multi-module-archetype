#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<jsp:include page="/page/include.jsp"/>
<table class="easyui-datagrid">
    <thead>
    <tr>

        <th data-options="field:'processName',align:'center'" width="100">流程名</th>
        <th data-options="field:'id',hidden:true" width="100">id</th>
        <th data-options="field:'title',align:'center'" width="100">事项</th>
        <th data-options="field:'initiator',align:'center' " width="100">发起人</th>
        <th data-options="field:'startTime',align:'center' " width="100">发起时间</th>
        <th data-options="field:'endTime',align:'center'" width="100">流程结束时间</th>
        <th data-options="field:'description',align:'center' " width="100">描述</th>
        <th data-options="field:'activityName',align:'center',formatter:getActiveName" width="100">当前节点</th>
        <th data-options="field:'processInstanceId',hidden:true" width="100">流程实例</th>
        <th>是否通过</th>

    </tr>
    </thead>
    <tbody>
    <s:iterator id="process" value="results">
        <tr>
            <td><s:property value="getProcessName(${symbol_pound}process)"/></td>
            <td><s:property value="getTitle(${symbol_pound}process)"/></td>
            <td><s:property value="getInitiator(${symbol_pound}process)"/></td>
            <td><s:date name="${symbol_pound}process.startTime" format="yyyy-MM-dd HH:mm:ss"/></td>
            <td><s:date name="${symbol_pound}process.endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
            <td><s:checkbox name="isPassed(${symbol_pound}process)" theme="simple"/></td>
        </tr>
    </s:iterator>
    </tbody>
</table>


