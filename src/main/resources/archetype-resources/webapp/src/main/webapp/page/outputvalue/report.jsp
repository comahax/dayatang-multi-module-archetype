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
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/highcharts/3.0.1/highcharts.js"></script>
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/highcharts/3.0.1/highcharts-more.js"></script>
</head>
<body>

<form id="outputvalue_report_form" action="${symbol_dollar}{basePath}/outputvalue/report-submit.action" method="post">
    项目名： <input id="outputvalue_report_constructings_selector" name="projectIds" style="width:500px;"/>

    <div id="outputvalue_report_constructings_table_toolbar">
        项目名：<input id="outputvalue_report_constructings_table_toolbar_searchname" type="text" missingMessage="关键字"
                   width="380px"/>
        <a href="javascript:;" onclick="outputvalue_report_constructings_table_toolbar_search_handler();" class="easyui-linkbutton"
           iconCls="icon-search"
          >搜索</a>
    </div>
    年：<input id="outputvalue_report_year" name="year"
             type="text"
             class="easyui-numberspinner"
             style="width:70px;"
             data-options="required:true,min:1970, max:2500,precision:0"/>&nbsp;&nbsp;
    月份：<input id="outputvalue_report_month"
              name="month"
              type="text"
              class="easyui-numberspinner"
              style="width:38px;"
              data-options="required:true,min:1,max:12,precision:0"/>


    产值：<input type="text" name="outputValues" class="easyui-numberbox"
              data-options="min:0,suffix:'元'"/>


    <a href="javascript:;" onclick="outputvalue_report_form_submit();"
       class="easyui-linkbutton">提交</a>
    <span class="muted">重复报产值将会覆盖原有的产值</span>
</form>

<div id="outputvalue_report_pivot" style="width: auto; height: 300px; margin: 0 auto">
</div>


<!--已汇报的-->
<div style="margin-top: 20px;">
    <table id="outputvalue_report_reported_list" class="easyui-datagrid"
    <%--url="${symbol_dollar}{basePath}/outputvalue/list-json-of-project.action?id=${symbol_dollar}{project.id}"--%>
           fitColumns="true"
           singleSelect="true"
           data-options="toolbar:'${symbol_pound}outputvalue_report_list_toolbar'"
            >
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">id</th>
            <th data-options="field:'projectId',hidden:true">projectId</th>
            <th data-options="field:'quarter'">季度</th>
            <th data-options="field:'year', formatter : function(value,row,index){ return value + '-' + row.month }">
                年月
            </th>
            <th data-options="field:'province'">省</th>
            <th data-options="field:'city'">市</th>
            <th data-options="field:'county'">县</th>
            <th data-options="field:'ownerCategory'">业主类型</th>
            <th data-options="field:'projectType'">项目类型</th>
            <th data-options="field:'org2'">子公司</th>
            <th data-options="field:'org3'">事业部</th>
            <th data-options="field:'org4'">项目部</th>
            <th data-options="field:'numericalValue', formatter : function(value, row, index){ return amountNumberFormatter(value); } ">
                产值
            </th>
        </tr>
        </thead>
    </table>
    <div id="outputvalue_report_list_toolbar">
        <a class="easyui-linkbutton" href="javascript:;"
           onclick="outputvalue_report_list_destroy_reported_handler();">删除</a>
    </div>
</div>


<script type="text/javascript">

${symbol_dollar}(function () {
    ${symbol_dollar}('${symbol_pound}outputvalue_report_year').numberspinner({
        increment: 1,
        value: new Date().getFullYear()
    });
    ${symbol_dollar}('${symbol_pound}outputvalue_report_month').numberspinner({
        increment: 1,
        value: new Date().getMonth() + 1
    });


    ${symbol_dollar}('${symbol_pound}outputvalue_report_constructings_table').datagrid({
        url: "${symbol_dollar}{basePath}/project/business-operationsabled.action"
    });

    var obj = ${symbol_dollar}('${symbol_pound}outputvalue_report_constructings_selector');
    obj.combogrid({
        nowrap:"false",
        mode: 'remote',
        panelWidth: 600,
        idField: 'id',
        textField: 'name',
        url: "<%=basePath%>project/business-operationsabled.action",
        toolbar: '${symbol_pound}outputvalue_report_constructings_table_toolbar',
        columns: [
            [
                {field:'id', title: 'id', hidden: true},
                {field:'name', title: '项目名', width: 350},
                {field:'estimatedIncome',title:'预估总收入(万元)', align:'right', width: 100, formatter: convertYuanToThusandYuanFormatter},
                {field:'outputvalueCompleted', align:'right',  title: '已报产值', width: 80, formatter: amountNumberFormatter}
            ]
        ],
        onChange: function () {
            var g = ${symbol_dollar}('${symbol_pound}outputvalue_report_constructings_selector').combogrid('grid');	// get datagrid object
            var r = g.datagrid('getSelected');	// get the selected row
            var data = outputvalue_report_refresh_reported_list_outputvalue(r.id);

            //显示产值报表
            outputvalue_report_init_chart('${symbol_pound}outputvalue_report_pivot', data);
        }
    });

});

//搜索项目
function outputvalue_report_constructings_table_toolbar_search_handler() {
    var name = ${symbol_dollar}('${symbol_pound}outputvalue_report_constructings_table_toolbar_searchname').val();

    ${symbol_dollar}('${symbol_pound}outputvalue_report_constructings_selector').combogrid('grid').datagrid('load', {
        name: name
    });
    ${symbol_dollar}('${symbol_pound}outputvalue_report_constructings_selector').combogrid('showPanel');

}

//提交产值
function outputvalue_report_form_submit() {
    ${symbol_dollar}('form${symbol_pound}outputvalue_report_form').form('submit', {
        success: function (data) {
            data = evalJSON(data);
            if (!data || !data.result) {
                imessager.alert('汇报失败');
                return;
            }
            outputvalue_report_form_refresh_grid_chart();
        }
    });
}

//删除已汇报产值
function outputvalue_report_list_destroy_reported_handler() {
    if (!confirm('确认删除？')) {
        return;
    }

    var grid = ${symbol_dollar}('${symbol_pound}outputvalue_report_reported_list');

    var id = datagrid_utils.selected_row_id(grid);
    var projectId = datagrid_utils.selected_row_attr(grid, 'projectId');
    var url = '${symbol_dollar}{basePath}/outputvalue/destroy.action?id=' + id + '&projectId=' + projectId;

    getJSON(url);
    datagrid_utils.delete_grid_selected_row(grid);
    outputvalue_report_form_refresh_grid_chart();

}

//刷新列表及报表
function outputvalue_report_form_refresh_grid_chart() {
    var id = ${symbol_dollar}('${symbol_pound}outputvalue_report_constructings_selector').combogrid('getValue');
    var data = outputvalue_report_refresh_reported_list_outputvalue(id);
    if (data) {
        outputvalue_report_init_chart('${symbol_pound}outputvalue_report_pivot', data);
    } else {
        ${symbol_dollar}('${symbol_pound}outputvalue_report_pivot').highcharts({
            series: [],
            xAxis: {
                categories: []
            }
        });

    }
}


//初始化报表
function outputvalue_report_init_chart(container, data) {

    //报表参数
    var pivot_opts = outputvalue_report_create_chart_opts(data);

    //设置x轴
    setX_Axis(data, pivot_opts);

    var result = ${symbol_dollar}(container).highcharts(pivot_opts);


    function setX_Axis(data, pivot_opts) {
        if (!data || !data.rows || data.rows.length <= 0) {
            return pivot_opts;
        }

        pivot_opts.series.push(
                {name: (data.rows[0].org2 || '') + '${symbol_escape}${symbol_escape}' + (data.rows[0].org3 || '') + '${symbol_escape}${symbol_escape}' + (data.rows[0].org4 || ''),
                    data: []});
        ${symbol_dollar}.each(data.rows, function (i, v) {
            pivot_opts.xAxis.categories.push(v.year + '${symbol_escape}${symbol_escape}' + v.month);
            pivot_opts.series[0].data.push(v.numericalValue);
        });

    }

    function outputvalue_report_create_chart_opts(data) {
        var totalvalue = get_total_outputvalue_from_data(data);
        return {
            chart: {
                type: 'column',
                height: 300
            },
            title: {
                text: '项目产值报表'
            },
            xAxis: {
                categories: []
            }, yAxis: {
                min: 0,
                title: {
                    text: '产值（元）'
                }
            },
            subtitle: {
                text: '已报总产值：' + totalvalue ? totalvalue.toFixed(2) : 0
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr>' +
                        '<td style="padding:0"><b>{point.y:.1f} 元</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: []
        };
    }

    //合计出项目已报的产值
    function get_total_outputvalue_from_data(data) {
        if (!data || !data.rows) {
            return 0;
        }
        var result = 0;
        ${symbol_dollar}.each(data.rows, function (i, v) {
            result += v.numericalValue;
        });
        return result;
    }

    return result;


}


//刷新已报产值列表
function outputvalue_report_refresh_reported_list_outputvalue(projectId) {
    var url = '${symbol_dollar}{basePath}/outputvalue/list-json-of-project.action?id=' + projectId;
    var data = getJSON(url);
    if (data) {
        ${symbol_dollar}('${symbol_pound}outputvalue_report_reported_list').datagrid('loadData', data);
    } else {
        ${symbol_dollar}('${symbol_pound}outputvalue_report_reported_list').datagrid('loadData', {rows: []});
    }

    return data;
}
</script>

</body>
</html>

