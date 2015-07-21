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
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/highcharts/3.0.1/highcharts.js"></script>
    <script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/highcharts/3.0.1/highcharts-more.js"></script>
</head>
<body>
<div  style="width:350px;padding-top: 10px;float: left;" >
    <h3>1、设置报表筛选条件</h3>
    <hr/>
    <div  class="easyui-panel" title="日期范围" data-options="border:false" style="width:auto;height:150px; padding:10px;">

        开始年份：<input id="pivot_fast_outputvalut_startdate_input" class="easyui-numberspinner" style="width:80px;"
                    data-options="min:1995,max:2100,increment:1,precision:0" value="${symbol_dollar}{currentYear}">
        <p/>
        结束年份：<input id="pivot_fast_outputvalut_enddate_input" class="easyui-numberspinner" style="width:80px;"
                    required="required" data-options="min:1995,max:2100,increment:1,precision:0" value="${symbol_dollar}{currentYear}">

        <p/>
        时间段：<select id="pivot_outputvalue_time_bucket" name="timeBucket">
        <option value="${symbol_dollar}{year}">年</option>
        <option value="${symbol_dollar}{quarter}">季度</option>
        <option value="${symbol_dollar}{month}">月</option>
    </select>
    </div>

    <div  class="easyui-panel" title="机构范围" data-options="border:false"  style="width:auto;padding:10px;">
        <div id="pivot_fast_outputvalue_internalorg"></div>
    </div>
</div>

<div  style="padding-top: 10px;float: left;margin-left: 10px;">
    <h3>2、生成报表</h3>
    <hr/>
    <a id="pivot_fast_output_over"  class="easyui-linkbutton">产值概况报表A</a>
    <p/>
    <a id="pivot_fast_output_over1"  class="easyui-linkbutton">产值概况报表B</a>
    <p/>
    <a id="pivot_fast_output_custom"  class="easyui-linkbutton">客户分类产值报表</a>
    <input id="pivot_custom_checkbox_pie" type="checkbox" name="pivot_type" value="pie"> 饼状图</input>&nbsp;
    <input id="pivot_custom_checkbox_column" type="checkbox" name="pivot_type" value="column"> 柱状图</input>
    <p/>

    <a id="pivot_fast_output_projecttype"  class="easyui-linkbutton">项目分类饼状图</a>
    <p/>
    <a id="pivot_outputvalue_output" class="easyui-linkbutton">导出为excel</a>
    <p/>

</div>


<div id="outputvalue_over_window" class="easyui-window" title="报表显示" style="width:650px; height:500px;padding:5px;"
     data-options="iconCls:'icon-ok',resize:true, closed:true, split:true,
                   onResize:function(width, height){ var chart = ${symbol_dollar}('${symbol_pound}pivot_outputvalue_over_report').highcharts();  if (chart) {chart.setSize(width - 80, height - 80);}}">
    <div  id="pivot_outputvalue_over_report" ></div>
</div>

<div id="outputvalue_over1_window" class="easyui-window" title="报表显示" style="width:650px; height:500px;padding:5px;"
     data-options="iconCls:'icon-ok',resize:true, closed:true, split:true,
                   onResize:function(width, height){ var chart = ${symbol_dollar}('${symbol_pound}pivot_outputvalue_over1_report').highcharts();  if (chart) {chart.setSize(width - 80, height - 80);}}">
    <div  id="pivot_outputvalue_over1_report" ></div>
</div>

<div id="outputvalue_custom_window" class="easyui-window" title="客户分类产值柱状报表" style="width:650px; height:500px;padding:5px;"
     data-options="iconCls:'icon-ok',resize:true, closed:true, split:true,
            onResize:function(width, height){
             var chart = ${symbol_dollar}('${symbol_pound}pivot_outputvalue_custom_report').highcharts();  if (chart) {chart.setSize(width - 80, height - 80);}
            }">
    <div id="pivot_outputvalue_custom_report"></div>
</div>

<div id="outputvalue_custom_pie_window" class="easyui-window" title="客户分类产值饼状报表" style="width:650px; height:500px;padding:5px;"
     data-options="iconCls:'icon-ok',resize:true, closed:true, split:true,
            onResize:function(width, height){
             var chart = ${symbol_dollar}('${symbol_pound}pivot_outputvalue_custom_pie_report').highcharts();  if (chart) {chart.setSize(width - 80, height - 80);}
            }">
    <div id="pivot_outputvalue_custom_pie_report"></div>
</div>

<div id="outputvalue_projecttype_column_window" class="easyui-window" title="项目类型产值柱状报表" style="width:650px; height:500px;padding:5px;"
     data-options="iconCls:'icon-ok',resize:true, closed:true, split:true,
            onResize:function(width, height){
             var chart = ${symbol_dollar}('${symbol_pound}outputvalue_projecttype_column_report').highcharts();  if (chart) {chart.setSize(width - 80, height - 80);}
            }">
    <div id="outputvalue_projecttype_column_report"></div>
</div>

<div id="outputvalue_projecttype_pie_window" class="easyui-window" title="项目类型产值饼状报表" style="width:650px; height:500px;padding:5px;"
     data-options="iconCls:'icon-ok',resize:true, closed:true, split:true,
            onResize:function(width, height){
             var chart = ${symbol_dollar}('${symbol_pound}pivot_outputvalue_projecttype_pie_report').highcharts();  if (chart) {chart.setSize(width - 80, height - 80);}
            }">
    <div id="pivot_outputvalue_projecttype_pie_report"></div>
</div>


<script type="text/javascript">
${symbol_dollar}(function(){
    //生成产值概况表
    ${symbol_dollar}('${symbol_pound}pivot_fast_output_over').click(function () {
        ${symbol_dollar}('${symbol_pound}outputvalue_over_window').window('open');
        var data = pivot_outputvalue_get_data('<%=basePath%>outputvalue/over-query-json.action');
        pivot_outputvalue_create_over_report('${symbol_pound}pivot_outputvalue_over_report',data.series, data.xAxisCategory, data.timeBucket);

    });

    ${symbol_dollar}('${symbol_pound}pivot_fast_output_over1').click(function(){
        ${symbol_dollar}('${symbol_pound}outputvalue_over1_window').window('open');
        var data = pivot_outputvalue_get_data('<%=basePath%>outputvalue/over1-query-json.action');
        pivot_outputvalue_create_over_report('${symbol_pound}pivot_outputvalue_over1_report',data.series, data.xAxisCategory, data.timeBucket,{
            xAxis: {
                labels: {
                    y:50,
                    rotation: 50
                }
            }
        });

    });

    ${symbol_dollar}('${symbol_pound}pivot_fast_output_over1').click(function(){
        ${symbol_dollar}('${symbol_pound}outputvalue_over1_window').window('open');
        var data = pivot_outputvalue_get_data('<%=basePath%>outputvalue/over1-query-json.action');
        pivot_outputvalue_create_over_report('${symbol_pound}pivot_outputvalue_over1_report',data.series, data.xAxisCategory, data.timeBucket,{
            xAxis: {
                labels: {
                    y:50,
                    rotation: 50
                }
            }
        });

    });


    //项目类型报表
    ${symbol_dollar}('${symbol_pound}pivot_fast_output_projecttype').click(function(){
        ${symbol_dollar}('${symbol_pound}outputvalue_projecttype_pie_window').window('open');

        var pieData =  pivot_outputvalue_get_data('<%=basePath%>outputvalue/projecttype-pie-query-json.action');
        pivot_outputvalue_create_pie_report('${symbol_pound}pivot_outputvalue_projecttype_pie_report',
                {series:[transferDataToPie(pieData)]});
        //将服务器传来的数据进行加工
        function transferDataToPie(pieData){
            var pieResultData = {};
            pieResultData.type = 'pie';
            pieResultData.data = [];
            pieResultData.name = "项目类型分类";
            ${symbol_dollar}.each(pieData.series, function(i, pie){
                var item = [];
                item.push(pie.name);
                var val = pie.data[0] ? pie.data[0] : 0;
                item.push(val);
                pieResultData.data.push(item);
            });
            return pieResultData;
        }

    });


    //客户分类产值报表
    ${symbol_dollar}('${symbol_pound}pivot_fast_output_custom').click(function(){

        if (${symbol_dollar}('${symbol_pound}pivot_custom_checkbox_pie').is(':checked')) {

            create_pie_custom();

        }
        if (${symbol_dollar}('${symbol_pound}pivot_custom_checkbox_column').is(':checked')) {
            create_column_custom();
        }
        function create_column_custom(){
            ${symbol_dollar}('${symbol_pound}outputvalue_custom_window').window('open');

            var columnData = pivot_outputvalue_get_data('<%=basePath%>outputvalue/custom-column-query-json.action');
            pivot_outputvalue_create_over_report('${symbol_pound}pivot_outputvalue_custom_report',
                    columnData.series,
                    columnData.xAxisCategory,
                    columnData.timeBucket
            );
        }

        function create_pie_custom(){
            ${symbol_dollar}('${symbol_pound}outputvalue_custom_pie_window').window('open');

            var pieData =  pivot_outputvalue_get_data('<%=basePath%>outputvalue/custom-pie-query-json.action');
            pivot_outputvalue_create_pie_report('${symbol_pound}pivot_outputvalue_custom_pie_report',{series:[transferDataToPie(pieData)]});
            //将服务器传来的数据进行加工
            function transferDataToPie(pieData){
                var pieResultData = {};
                pieResultData.type = 'pie';
                pieResultData.data = [];
                pieResultData.name = "客户分类";
                ${symbol_dollar}.each(pieData.series, function(i, pie){
                    var item = [];
                    item.push(pie.name);

                    var val = pie.data[0] ? pie.data[0] : 0;
                    item.push(val);
                    pieResultData.data.push(item);
                });
                return pieResultData;
            }

        }
    });

});

/**
 * 导出为excel
 */
${symbol_dollar}('${symbol_pound}pivot_outputvalue_output').click(function(){
    var params = pivot_outputvalue_params();
    var url = "${symbol_dollar}{basePath}/pivot/download-output-value-excel.action?" + 'start=' + params.start + '&end=' + params.end + '&internalScopeId=' + params.org;
    window.open(url,"",'');

});

/**
 *      得到报表的必要数据
 * @return {{time_bucket: (*|jQuery), org: *, xAxisCategory: *, series: *}}
 * @private
 */
function pivot_outputvalue_get_data(url){
    var params = pivot_outputvalue_params();
    var data = getJSON(url + '?start=' +
            params.start +
            '&end=' + params.end
            + '&internalScopeId=' + params.org
            + '&timeBucket=' +  params.time_bucket);

    return {
        xAxisCategory: data.results.xAxisCategories,
        series: data.results.series
    };
};

/**
 *得到请求报表数据的参数
 */
function pivot_outputvalue_params() {
    return {
        time_bucket: ${symbol_dollar}('select${symbol_pound}pivot_outputvalue_time_bucket').val(),
        start: ${symbol_dollar}('${symbol_pound}pivot_fast_outputvalut_startdate_input').numberspinner('getValue') + '-1-1',
        end: ${symbol_dollar}('${symbol_pound}pivot_fast_outputvalut_enddate_input').numberspinner('getValue') + '-12-30',
        org: treeObj.getSelectedNodes()[0] ? treeObj.getSelectedNodes()[0].id : 0
    };
}

/**
 * 创建饼状图
 * @private
 */
function pivot_outputvalue_create_pie_report(container, options){
    var settings = ${symbol_dollar}.extend(true,{},{
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '产值饼状报表'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>',
            percentageDecimals: 1
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '${symbol_pound}000000',
                    connectorColor: '${symbol_pound}000000',
                    formatter: function() {
                        return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(2) +' %';
                    }
                }
            }
        },
        series:[]
    }, options || {});

    ${symbol_dollar}(container).highcharts(settings);
}  // end


/**
 *创建报表
 * @param series 系列数据
 * @param xAxisCategory  x轴数据
 * @param time 时间段
 * @private
 */
function pivot_outputvalue_create_over_report(container, series, xAxisCategory, time, options){
    var settings = ${symbol_dollar}.extend(true, {},{
        chart: {

        },
        title: {
            text: '产值报表'
        },
        subtitle: {
            text: '单位:万元'
        },
        plotOptions: {
            series: {
                dataLabels: {
                    align: 'center',
                    enabled: true
                }
            }
        },
        xAxis: {
            categories:  xAxisCategory ,
            labels: {
                staggerLines: 1
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '产值'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.2f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        legend: {
            // horizontal:水平展开,可选项还有vertical:垂直展开
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom'

        },
        series: series
    }, options || {});

    ${symbol_dollar}(container).highcharts(settings);
}

</script>

<script type="text/javascript">
       //组织机构树的对象
        var treeObj;
        ${symbol_dollar}(function () {
            //初始化机构范围
            var url = "<%=basePath%>organizational-struct/tree-json.action";
            treeObj = initInstitutionalFrameworkTreeMini('${symbol_pound}pivot_fast_outputvalue_internalorg', url, zTreeOnClick);
            function zTreeOnClick(event, treeId, treeNode){

            }

        });
</script>
</body>
</html>