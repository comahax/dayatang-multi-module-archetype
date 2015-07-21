#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 项目的分包比例 -->
<jsp:include page="/page/include.jsp"/>


<div>
    总产值： <input type="text" class="easyui-numberbox"
                value="${symbol_dollar}{project.totalOutputvalue}"
                data-options="precision:2,
                           suffix:'元',
                           formatter:amountNumberFormatter,
                           disabled:true"/>
    <a href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}subcontracting_list_edit_total_dialog${symbol_dollar}{project.id}').dialog('open');">更改</a>

    <div id="subcontracting_list_edit_total_dialog${symbol_dollar}{project.id}"
         class="easyui-dialog" title="更改项目总产值" style="width:400px;height:150px;padding: 30px;"
         data-options="
         iconCls:'icon-edit',
         resizable:false,
         modal:true,
         closed:true">
        <form id="subcontracting_list_edit_total_form${symbol_dollar}{project.id}" action="${symbol_dollar}{basePath}/outputvalue/edit-total-value-of-project.action">
        总产值：  <input name="numberValue" class="easyui-numberbox"
               data-options="
               min:0,suffix:'元',
               required:true,
               formatter:amountNumberFormatter"
               type="text"
               value="${symbol_dollar}{project.totalOutputvalue}"/>
        <input name="id" type="hidden" value="${symbol_dollar}{project.id}">
        <button type="submit">确定</button>
        </form>

        <script type="text/javascript">
            ${symbol_dollar}(function(){
                ${symbol_dollar}('${symbol_pound}subcontracting_list_edit_total_form${symbol_dollar}{project.id}').form({
                    success:function(data){
                        data = evalJSON(data);
                        if(!data.numberValue){
                            imessager.fail_tip();
                        }
                        ${symbol_dollar}('${symbol_pound}project_details_tabs${symbol_dollar}{project.id}').tabs('getTab', '分包比例').panel('refresh');
                        ${symbol_dollar}('${symbol_pound}subcontracting_list_edit_total_dialog${symbol_dollar}{project.id}').dialog('close');
                    }

                });
            });

        </script>
    </div>
    <div>
        已分包产值总和：<input type="text" class="easyui-numberbox"
                       value="${symbol_dollar}{project.outputvalueDistributiveShareTotal}"
                       data-options="precision:2,
                           suffix:'元',
                           formatter:amountNumberFormatter,
                           disabled:true"/>
    </div>
    <div>
        应分包付款总和：<input type="text" class="easyui-numberbox"
                       value="${symbol_dollar}{project.outputvaluePayableTotal}"
                       data-options="precision:2,
                           suffix:'元',
                           formatter:amountNumberFormatter,
                           disabled:true"/>
    </div>
    <div>毛利率：${symbol_dollar}{project.grossMargin}%</div>
    <div>毛利润：
        <input type="text" class="easyui-numberbox" value="${symbol_dollar}{project.grossProfit}"
               data-options="precision:2,
                           formatter:amountNumberFormatter,
                           disabled:true"/></div>




</div>

<table id="subcontract_grid_${symbol_dollar}{project.id}" class="easyui-datagrid"
       rownumbers="true"
       showFooter="true"
       singleSelect="true"
       showFooter="true"
       url="${symbol_dollar}{basePath}/subcontracting/list-json.action?id=${symbol_dollar}{project.id}"
       toolbar="${symbol_pound}subcontract_list_grid_toolbar${symbol_dollar}{project.id}"
        >
    <thead>
    <tr>
        <th data-options="
            field:'cooperationOrganization',
            width:100,
            formatter :  function(value, row, index){ return !value ? '' : value.name;}">
            合作单位</th>
        <th data-options="field:'distributiveShare',width:100,formatter:amountNumberFormatter">分配份额</th>
        <th data-options="field:'subcontractingRatio', formatter : function(value, row, index){ return !value ? '' : value + '%'}">分包比例</th>
        <th data-options="field:'payable',width:100,formatter:amountNumberFormatter">应付</th>
    </tr>
    </thead>
</table>

<div id="subcontract_list_grid_toolbar${symbol_dollar}{project.id}" style="padding:5px;height:auto">
    <div>
        添加分包：
        <form id="subcontract_set_cooperationts_form${symbol_dollar}{project.id}" method="post">
            合作单位
            <input id="project_details_cooperation_selector${symbol_dollar}{project.id}"
                   name="orgIds" data-options="required:true" type="text" />
            分配份额
            <input id="project_details_distributive${symbol_dollar}{project.id}"
                   name="distributives" class="easyui-numberspinner" style="width:180px;"
                       data-options="required:true,
                                  suffix:'元',
                                  formatter:amountNumberFormatter"
                       type="text"/>
            分包比例<input id="project_details_ratio${symbol_dollar}{project.id}"
                       name="ratios"
                       class="easyui-numberspinner" style="width:80px;"
                       data-options="required:true, max:100,increment:10, suffix:'%'" type="text"/>
            <a onclick="subcontract_add_btn_handler${symbol_dollar}{project.id}();" href="javascript:;" class="easyui-linkbutton">添加</a>
        </form>

    </div>
    <div style="margin-bottom:5px">
        <a href="javascript:;"
           onclick="subcontract_list_remove_btn_handler${symbol_dollar}{project.id}();"
           class="easyui-linkbutton" iconCls="icon-remove" plain="true">
            移除合作单位
        </a>
    </div>
</div>

<script type="text/javascript">



    ${symbol_dollar}(function(){
        //初始化合作单位
        proj_app.cooperation_selector('${symbol_pound}project_details_cooperation_selector${symbol_dollar}{project.id}');
    });

    //添加分包比例
    function subcontract_add_btn_handler${symbol_dollar}{project.id}(){
        var form = ${symbol_dollar}('${symbol_pound}subcontract_set_cooperationts_form${symbol_dollar}{project.id}');
        subcontract_utils.submitSubcontracting('&' + form.serialize());
    }

    //删除分包比例
    function subcontract_list_remove_btn_handler${symbol_dollar}{project.id}(){
        datagrid_utils.delete_grid_selected_row(${symbol_dollar}('${symbol_pound}subcontract_grid_${symbol_dollar}{project.id}'));
        subcontract_utils.submitSubcontracting();
    }


    var subcontract_utils = {
        getUrlTemplate : function(orgId, distributive, ratio){
            return "orgIds=" + orgId  + "&distributives=" + distributive + "&ratios=" + ratio + "&";
        },
        getUrlResult   : function(data, baseUrl){
            var result =  baseUrl;
            ${symbol_dollar}.each(data.rows || [], function(i, v){
                if(v && v.cooperationOrganization){
                    result += subcontract_utils.getUrlTemplate(v.cooperationOrganization.id, v.distributiveShare, v.subcontractingRatio );
                }
            });
            return result;
        },
        submitSubcontracting : function(otherUrlParameter){
            var grid = ${symbol_dollar}('${symbol_pound}subcontract_grid_${symbol_dollar}{project.id}');
            var data = grid.datagrid('getData');

            var url = '${symbol_dollar}{basePath}/subcontracting/set-subcontracting.action?id=${symbol_dollar}{project.id}&';
            url = subcontract_utils.getUrlResult(data, url);
            if(otherUrlParameter){
                url += otherUrlParameter;
            }

            getJSON(url, function(result){
                if(result && result.result){
                    ${symbol_dollar}('${symbol_pound}project_details_tabs${symbol_dollar}{project.id}').tabs('getTab', '分包比例').panel('refresh');
                }
            });
        }

    }


    function grid_formatter_organization(value, row, index){
        return !value ? "" : value.name;
    }
</script>

