#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/page/scripts.jsp"/>
</head>
<body>
<table id="project_list_table"
       nowrap="false"
       singleSelect="true"
       rownumbers="true"
       pagination="true"
       pageSize="20"
       fit="true"
       data-options="onDblClickRow:project_list_table_db_click_handler,onClick:project_list_table_click_hander"
       toolbar="${symbol_pound}project_list_tb"
        >
    <thead data-options="frozen:true">
    <tr>
        <th data-options="field:'name'" rowspan="2" width="150" >名称</th>
    </tr>
    </thead>
    <thead>
    <tr>
        <th data-options="field:'addSubProjectable',hidden:true">设置基本信息是否可以添加单点工程</th>
        <th data-options="field:'id',hidden:true" rowspan="2">id</th>
        <th data-options="field:'status'" rowspan="2"  width="75">项目状态</th>
        <th data-options="field:'projectNumber'" rowspan="2">项目编号</th>
        <th colspan="4">工期</th>
        <th colspan="3">产值</th>
        <th colspan="3">回款</th>
    </tr>
    <tr>
        <th data-options="field:'startDate'"  width="75">开工日期</th>
        <th data-options="field:'predictFinishDate'" width="75">计划完工</th>
        <th data-options="field:'finishDate'" width="75">竣工</th>
        <th data-options="field:'durationProcesser',formatter: remaining_duration_formatter" width="75">进度</th>
        <th data-options="field:'duration',hidden:true">总工期</th>

        <th data-options="field:'totalOutputvalue', formatter:amountNumberFormatter, align: 'right'">预估总产值</th>
        <th data-options="field:'outputvalueCompleted', formatter:amountNumberFormatter, align: 'right'">已完成产值</th>
        <th data-options="field:'outputvalueCompletedRatio',formatter: function(value, row, index){return (value || 0) + '%'}"  width="50">
            完成比
        </th>

        <th data-options="field:'totalReceiptInvoiceAmount', formatter:amountNumberFormatter, align: 'right'">已开票</th>
        <th data-options="field:'totalReceiptAmount', formatter:amountNumberFormatter,align:'right'">已回款</th>
        <th data-options="field:'receivableRatio',formatter: function(value, row, index){return (value || 0) + '%'}">
            回款率
        </th>
    </tr>
    </thead>

</table>
<!--工具栏-->
<div id="project_list_tb" style="padding:5px;height:auto">
    负责机构：<input id="project_list_org_scope_selector"  /> &nbsp;|&nbsp;
    项目状态：<select id="projectStatusSelect"  style="width:200px;">
    <option></option>
    <s:iterator id="eachStatus" value="projectStatues">
        <option value="${symbol_dollar}{eachStatus}"><s:property value="${symbol_pound}eachStatus.getCnText()"/></option>
    </s:iterator>
</select>           &nbsp;|&nbsp;
    <input  class="easyui-searchbox" style="width:300px"
            data-options="searcher:project_list_search_handler, prompt:'请输入',menu:'${symbol_pound}project_list_search_menu'"/>


    <div id="project_list_search_menu" style="width:120px">
        <div data-options="name:'projectName',iconCls:'icon-ok'">项目名</div>
    </div>

    <%--
                <a id="project_list_add_contract_btn" href="javascript:;" class="easyui-linkbutton">添加合同</a>

    <a id="project_list_add_subproject_btn" onclick="project_list_add_subprojects_handler();"
       href="javascript:;" class="easyui-linkbutton">添加单点工程</a>                       --%>
    <%-- <a id="project_list_add_subproject_btn11" href="javascript:project_list_add_subproject_func();"
        class="easyui-linkbutton">添加发票</a>
     <a id="project_list_add_subproject_btn111" href="javascript:project_list_add_subproject_func();"
        class="easyui-linkbutton">添加收款</a>--%>
    <script type="text/javascript">
        function project_list_add_subprojects_handler() {
            var grid = ${symbol_dollar}('${symbol_pound}project_list_table');
            var id = datagrid_utils.selected_row_id(grid);
            if (!id) {
                alert('请选择项目后操作！');
                return;
            }

            var project_name = datagrid_utils.selected_row_attr(grid, 'name');
            //跳转到新的tab
            redirect_to_project_subprojects(id, project_name);
        }

        function project_list_table_db_click_handler(rowIndex, rowData) {
            redirect_to_project_detail(rowData.id, rowData.name);
        }

        function project_list_table_click_hander(rowIndex, rowData) {
            var operations = ${symbol_dollar}('${symbol_pound}project_list_add_subproject_btn, ${symbol_pound}project_list_add_contract_btn, ${symbol_pound}project_list_outputvalue_report_btn');
            if (!rowData.businessOperationsable) {
                operations.linkbutton('disable');
            } else {
                operations.linkbutton('enable');
            }
        }

        /**
         * 跳转到project详细页
         * @param project_id
         * @param project_name
         */
        function redirect_to_project_detail(project_id, project_name) {

            var url =  "${symbol_dollar}{basePath}/project/details.action?id=" + project_id;
            var opts = {
                title: project_name,
                closable: true,
                content : "<iframe src="+ url +" frameborder=${symbol_escape}"0${symbol_escape}" style=${symbol_escape}"border:0;width:100%;height:99.4%;${symbol_escape}"></iframe>"
            }
            var t = parent.${symbol_dollar}('${symbol_pound}layout_center_tabs');
            if (t.tabs('exists', opts.title)) {
                t.tabs('select', opts.title);
            } else {
                if(opts.href && !opts.content){
                    opts.content = "<iframe src="+ opts.href +" frameborder=${symbol_escape}"0${symbol_escape}" style=${symbol_escape}"border:0;width:100%;height:99.4%;${symbol_escape}"></iframe>";
                    opts.href = null;
                }
                if(!opts.href && !opts.content){
                    alert('参数不全，无法确定跳转页面！');
                    return;
                }
                t.tabs('add', opts);
            }

        }


        /**
         * 跳转到project的单点工程的详细页
         * @param project_id
         * @param project_name
         */
        function redirect_to_project_subprojects(project_id, project_name) {
            var url = "${symbol_dollar}{basePath}/subproject/of-project.action?id=" + project_id;
            console.log(url);
            layout_center_addTabFun({
                title: project_name + '-单点工程',
                closable: true,
                content : "<iframe src="+ url +" frameborder=${symbol_escape}"0${symbol_escape}" style=${symbol_escape}"border:0;width:100%;height:99.4%;${symbol_escape}"></iframe>"
            });
        }

    </script>

</div>

<script>

    //搜索项目
    function project_list_search_handler(value,name){
        var param = {};
        param[name] = value;
        ${symbol_dollar}('${symbol_pound}project_list_table').datagrid('load', param);
    }

    ${symbol_dollar}(function () {
        var container = ${symbol_dollar}('${symbol_pound}project_list_div');
        var datagrid = ${symbol_dollar}('${symbol_pound}project_list_table');
        datagrid.datagrid({
            url : '${symbol_dollar}{basePath}/project/list-json.action'
        });

        ${symbol_dollar}('${symbol_pound}project_list_search_handler').click(function () {
            refreshProjectDatagrid();
        });

        ${symbol_dollar}('${symbol_pound}projectStatusSelect').change(function(){
            refreshProjectDatagrid();
        });

        //按机构过滤项目
        proj_app.organizational_scope('${symbol_pound}project_list_org_scope_selector',{onChange: refreshProjectDatagrid});

        function refreshProjectDatagrid(){
            var projectStatus = ${symbol_dollar}('${symbol_pound}projectStatusSelect').val();
            var name = ${symbol_dollar}('${symbol_pound}project_list_search_projectname_input').val();
            var id = ${symbol_dollar}('${symbol_pound}project_list_org_scope_selector').combotree('getValue');

            var grid = ${symbol_dollar}('${symbol_pound}project_list_table');
            grid.datagrid({
                queryParams: {
                    projectStatus: projectStatus,
                    responsibleDivisionId : id,
                    projectName: name
                }
            });
        }




    });





</script>
</body>
</html>