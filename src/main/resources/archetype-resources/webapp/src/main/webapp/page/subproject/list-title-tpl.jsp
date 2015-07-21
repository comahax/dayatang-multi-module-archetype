#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<thead data-options="frozen:true">
<tr>
    <th data-options="field:'name'" rowspan="2">名称</th>
    <th data-options="field:'id',hidden:true" rowspan="2">id</th>
</tr>
</thead>
<thead>
<tr>
    <th data-options="field:'version', hidden:true" rowspan="2">version</th>
    <th data-options="field:'apType', formatter : dictionary_show_formatter" rowspan="2">接入类型</th>
    <th colspan="4">工期</th>
    <th data-options="field:'remark'" rowspan="2">备注</th>
    <th colspan="2">设计单位</th>
    <th colspan="2">监理单位</th>
</tr>
<tr>
    <th data-options="field:'startDate'">开工日期</th>
    <th data-options="field:'predictFinishDate'">计划完工</th>
    <th data-options="field:'finishDate'">竣工</th>
    <th data-options="field:'durationProcesser',formatter: remaining_duration_formatter" >进度</th>
    <th data-options="field:'duration',hidden:true">总工期</th>
    <th data-options="field:'designOrganization', formatter : grid_show_name_formatter">单位</th>
    <th data-options="field:'designPerson', formatter : grid_show_name_formatter">联系人</th>
    <th data-options="field:'supervisorOrganization', formatter : grid_show_name_formatter">单位</th>
    <th data-options="field:'supervisorPerson', formatter : grid_show_name_formatter">联系人</th>
</tr>
</thead>