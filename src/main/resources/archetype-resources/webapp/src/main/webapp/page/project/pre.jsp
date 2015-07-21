#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
            + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="/page/include.jsp"/>

    <title>日海通信工程项目管理系统－立项</title>
</head>
<body>
<div class="container">

<div class="row">
    <div class="span4"><a href="${symbol_dollar}{basePath}/">返回工作台</a></div>
</div>
<div class="row">
    <div class="span4 offset2"><h2>项目立项收支预算审批表</h2></div>
</div>
<div class="row">
<form id="project_pre_form" action="${symbol_dollar}{basePath}/project/save-draft.action" method="post">

<div class="span10">
    <div style="color: red;">单位：万元</div>
    <table class="table">
        <tbody>
        <tr>
            <td>项目名称</td>
            <td colspan="3"><input class="easyui-validatebox" data-options="required:true" name="name" type="text"
                                   style="width:300px"/></td>
            <td>项目类型</td>
            <td><input id="pre_projectType" name="projectType" type="text"/></td>
        </tr>
        <tr>
            <td style="width:100px;">项目编号</td>
            <td><span id="project_pre_projectnumber"></span>
                <span class="muted">由系统自动生成</span></td>
            <td style="width:100px;">开工日期</td>
            <td><input name="startDate" type="text"/></td>
            <td style="width:100px;">竣工日期</td>
            <td><input name="predictFinishDate" type="text"/></td>
        </tr>
        <tr>
            <td>以哪家公司名义开展</td>
            <td colspan="2"><input id="pre_constructing_part" value="${symbol_dollar}{grantedScope.company.id}" name="constructingOrgId" type="text"></td>
            <td>负责部门</td>
            <td colspan="2"><input name="responsibleDivisionId" type="text"/></td>
        </tr>
        <tr>
            <td>客户名称及联系人</td>
            <td colspan="2"><input name="ownerId" type="text"/><span class="icon-help" style="width: 20px;"></span></td>
            <td>区域</td>
            <td colspan="2"><input name="areaId"/></td>
        </tr>
        <tr>
            <td>备注</td>
            <td colspan="5"><textarea name="remark" cols="30" rows="3"></textarea>
        </tr>

        </tbody>
    </table>
    <hr/>
    <h3>项目收入情况</h3>
    <table>
        <tbody>
        <tr>
            <td style="width:120px;">总收入</td>
            <td><input id="project_pre_extimatedincome"
                       type="text"
                       name="estimatedIncome"
                       class="easyui-numberbox" data-options="min:0,groupSeparator: ','"/></td>
            <td>按实际收款产值预计，控制在±10％以内，<span style="color: red;">如项目类型为产品销售业务，此项不含税价</span></td>
        </tr>
        </tbody>
    </table>
    <div class="easyui-tabs">
        <div title="成本预算情况" style="padding:5px;">
            <table>
                <thead>
                <tr>
                    <th style="width:120px;">成本名称</th>
                    <th>金额</th>
                    <th>填写说明</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>运作费用</td>
                    <td><input expenditureAmounts="expenditureAmounts" name="operation" class="easyui-numberbox" data-options="min:0,value:0,onChange:pre_expenditure_amount_input"/>
                    </td>
                    <td>指项目运作发生的车辆、交通、差旅、房租、通信、办公、临时雇佣等运作费用</td>
                </tr>
                <tr>
                    <td>人力费用</td>
                    <td><input type="text" expenditureAmounts="expenditureAmounts" name="salary" class="easyui-numberbox" data-options="min:0,value:0,onChange:pre_expenditure_amount_input"/>
                    </td>
                    <td>指项目人员的薪酬、福利等人工成本费用</td>
                </tr>
                <tr>
                    <td>业务费用</td>
                    <td><input type="text" expenditureAmounts="expenditureAmounts" name="market" class="easyui-numberbox" data-options="min:0,value:0,onChange:pre_expenditure_amount_input"/>
                    </td>
                    <td>指市场拓展费用</td>
                </tr>
                <tr>
                    <td>设备折旧费用</td>
                    <td><input type="text" expenditureAmounts="expenditureAmounts" name="deviceDepreciation" class="easyui-numberbox" data-options="min:0,value:0,onChange:pre_expenditure_amount_input"/>
                    </td>
                    <td>指电脑、手机、打印机、传真机、软件、食品、仪表等设备资产折旧，一般按三年折旧</td>
                </tr>
                <tr>
                    <td>耗材、辅材费用</td>
                    <td><input type="text" expenditureAmounts="expenditureAmounts" name="auxiliaryMaterial" class="easyui-numberbox" data-options="min:0,value:0,onChange:pre_expenditure_amount_input"/>
                    </td>
                    <td>指自购的易耗工具、材料等费用</td>
                </tr>
                <tr>
                    <td>分包费用</td>
                    <td><input type="text" expenditureAmounts="expenditureAmounts" name="subcontract" class="easyui-numberbox" data-options="min:0,value:0,onChange:pre_expenditure_amount_input"/>
                    </td>
                    <td>指分包给其他公司、施工队的外包费用</td>
                </tr>
                <tr>
                    <td>主材费用</td>
                    <td><input type="text" expenditureAmounts="expenditureAmounts" name="mainMaterial" class="easyui-numberbox" data-options="min:0,value:0,onChange:pre_expenditure_amount_input"/>
                    </td>
                    <td>包括外部采购和内部采购的主材费用</td>
                </tr>
                <tr>
                    <td>资金占用费用</td>
                    <td><input id="pre_expenditureAmounts_input"
                               type="text" expenditureAmounts="expenditureAmounts" name="fundOccupation" class="easyui-numberbox" data-options="min:0,value:0,disabled:true,onChange:pre_expenditure_amount_input"/>

                    </td>
                    <td>根据项目使用资金额及时间进行估算，按银行同期货款利率上浮20%计算</td>
                </tr>
                <tr>
                    <td>税金费用</td>
                    <td><input type="text" expenditureAmounts="expenditureAmounts" name="taxes" class="easyui-numberbox" data-options="min:0,value:0,onChange:pre_expenditure_amount_input"/>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td>其他费用</td>
                    <td><input type="text" expenditureAmounts="expenditureAmounts" name="other" class="easyui-numberbox" data-options="min:0,value:0,onChange:pre_expenditure_amount_input"/>
                    </td>
                    <td>勘察设计及安全费</td>
                </tr>

                <tr id="project_pre_total_cost_tr">
                    <td>成本合计</td>
                    <td colspan="2">
                        <input id="project_pre_total_cost" class="easyui-numberbox" type="text" data-options="min:0,disabled:true,value:0"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div title="添加自定义费用" class="easyui-panel" style="width:680px;" collapsible="true" collapsed="false">
            	<table>
            	 <tr id="project_pre_custom_budget_tr">
                    <td>
                       费用类型： <select id="project_pre_custom_budget_selector" >
                            <option></option>
                            <s:iterator value="customBudgets" id="customBudget">
                            	<option value="<s:property value="${symbol_pound}customBudget.serialNumber"/>" remark="<s:property value="${symbol_pound}customBudget.remark"/>">
                            	<s:property value="${symbol_pound}customBudget.text"/></option>
                            </s:iterator>
                        </select>
                    </td>
                    <td>金额：<input id="custom_budget_amount" type="text"
                    name="custom_budget_amount" class="easyui-numberbox" data-options="min:0, value:0, groupSeparator: ',', precision: 2"/> </td>
                   <td > 备注：<span id="project_pre_custom_budget_remark_td"></span></td>
                </tr>
            	</table>
            	<a href="javascript:;" onclick="project_pre_custom_budget_add();" class="easyui-linkbutton" iconCls="icon-add" >添加</a>
            </div>
        </div>

        <div title="资金占用费用估算说明">
            <div class="pull-right">
                <a id="add_fund_btn" class="easyui-linkbutton" onclick="${symbol_dollar}('${symbol_pound}cost_fund_dialog').dialog('open');" iconCls="icon-add">添加</a>
            </div>
            <div style="clear: both;">
                <table id="pre_cost_fund_grid" class="table">
                    <tr>
                        <th>成本说明</th>
                        <th>开始日期</th>
                        <th>占用时间(年)</th>
                        <th>预计占用资金</th>
                        <th>利率</th>
                        <th>占用资金成本</th>
                        <th>备注</th>
                        <th></th>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <h3>项目利润情况</h3>
    <table>
        <tbody>
        <tr>
            <td>毛利润</td>
            <td><input id="project_pre_grossprofit_input"
                       name="grossProfit" type="text"
                       class="easyui-numberbox" data-options="disabled:true,groupSeparator: ','"/></td>
            <td>毛利率</td>
            <td><input id="project_pre_grossmargin_input" name="grossMargin" type="text" class="easyui-numberbox"
                       data-options="disabled:true,suffix:'%'"/></td>
        </tr>
        <tr>
            <td>企业管理成本</td>
            <td><input id="project_pre_enterprise_management_costs" name="enterpriseManagementCosts"
                       type="text" class="easyui-numberbox" data-options="groupSeparator: ','"/></td>
            <td>企业所得税</td>
            <td><input id="project_pre_enterprise_income_tax" name="enterpriseIncomeTax" type="text"
                       class="easyui-numberbox" data-options="groupSeparator: ','"/></td>

        </tr>
        <tr>
            <td>净利润</td>
            <td><input id="project_pre_net_profit" name="netProfit" type="text" class="easyui-numberbox"
                       data-options="disabled:true,groupSeparator: ','"/></td>
            <td>净利率</td>
            <td><input id="project_pre_net_profit_margin" name="netProfitMargin" type="text" class="easyui-numberbox" data-options="disabled:true,suffix:'%'"/></td>
        </tr>
        </tbody>
    </table>
    <h3>上传附件</h3>
    <input type="file" name="uploads" id="project_pre_upload_file"/>
    <div id="project_pre_upload_file_queue"></div>
    <div class="muted">说明：可以同时上传多个附件</div>

    <div>
        注：
        <ul>
            <li>企业管理成本指企业管理人员、行政人员、财务人员等分摊成本，在此只需考虑子公司涉及的管理成本</li>
            <li>净利润 ＝ 毛利润 － 企业管理成本 － 企业所得税</li>
            <li>净利率 ＝ 净利润 / 总收入， 请控制成本争取净利率10%以上，原则上净利率低于8%的项目不予承接，如有特殊情况，需要说明具体原因，由广东日海总经理签批。</li>
        </ul>
    </div>


    <hr/>
</div>
</form>
<input type="hidden" value="false" name="draft">
<input type="hidden" name="projectId" id="projectId"/>

</div>
<div class="row">
    <div class="span10">
        <div class="pull-left">
            <a href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}project_pre_form').submit();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交并等待审批</a>
        </div>
        <div class="pull-right">
            <a href="javascript:;" onclick="set_draft_true_submit_form();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存为草稿</a>
        </div>
    </div>

</div>

<div class="row">
    <div class="span4 offset3"><a href="${symbol_dollar}{basePath}/">返回工作台</a></div>
</div>
</div>


<div id="cost_fund_dialog" class="easyui-dialog" title="添加资金占用，单位：万元" style="width: 500px;height: 350px;" resizable="true" modal="true" closed="true">
    <form id="cost_fund_dialog_form" method="post">
        <table class="table">
            <tr>
                <td>成本说明</td>
                <td><input name="costCaption" class="easyui-validatebox" data-options="required:true"/></td>
                <td>开始日期</td>
                <td><input type="text" name="startDate" class="easyui-datebox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td> 预计占用资金</td>
                <td><input id="pre_expectedFunds_input" name="expectedFunds" type="text" class="easyui-numberbox" data-options="required:true,onChange:expectedFunds_yearCount_interestRate_change"/></td>
                <td> 占用时间（年）</td>
                <td><input id="pre_yearCount_input" name="yearCount" class="easyui-numberspinner" style="width:80px;"
                           data-options="min:0,max:100,increment:1,required:true,onChange:expectedFunds_yearCount_interestRate_change" value="1"></td>
            </tr>
            <tr>
                <td>利率</td>
                <td><input id="pre_interestRate_input" name="interestRate" type="text" value="8" class="easyui-numberbox" data-options="required:true,suffix:'%',onChange:expectedFunds_yearCount_interestRate_change"/></td>
                <td>占用资金成本</td>
                <td><input id="pre_costFunds" name="costFunds" type="text" class="easyui-numberbox" data-options="required:true,disabled:true"/></td>
            </tr>
            <tr>
                <td>备注</td>
                <td colspan="3"><textarea name="remark"></textarea></td>
            </tr>
        </table>
        <a href="javascript:;" onclick="${symbol_dollar}('${symbol_pound}cost_fund_dialog_form').submit();" class="easyui-linkbutton">提交</a>
    </form>
</div>


<script type="text/javascript">

    //提交申请的按钮的操作
    function set_draft_true_submit_form(){
        ${symbol_dollar}('input[name=draft]').val(true);
        ${symbol_dollar}('${symbol_pound}project_pre_form').submit();
    }



    ${symbol_dollar}(function () {
        //初始化立项表
        var form = ${symbol_dollar}('${symbol_pound}project_pre_form');

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
        proj_app.project_type_selector('${symbol_pound}pre_projectType');


        //以谁名义开展项目
        proj_app.constructing_party('input[name="constructingOrgId"]');

        uploadfile('${symbol_pound}project_pre_upload_file', '${symbol_dollar}{basePath}/project/upload-docs.action', {
            queueID : 'project_pre_upload_file_queue',
            onQueueComplete: function (file, data, response) {
                upload_complete_call_back();
            },'onUploadStart' : function(file) {
                ${symbol_dollar}("${symbol_pound}project_pre_upload_file").uploadify("settings", "formData", {
                    id : ${symbol_dollar}('${symbol_pound}projectId').val()
                });
            }
        });

        //立项表单提交
        form.form({
            success: function (data) {
                data = evalJSON(data);
                if (data && data.errorInfo) {
                    var error = data.errorInfo;
                    alert(error + " 项目可以在项目列表中找到。");
                    location.href = "${symbol_dollar}{basePath}/";
                } else {
                    var draft = ${symbol_dollar}('input[name="draft"]').val();
                    ${symbol_dollar}('${symbol_pound}projectId').val(data.id);
                    if (${symbol_dollar}('${symbol_pound}project_pre_upload_file_queue > .uploadify-queue-item').size() > 0) {
                        ${symbol_dollar}('${symbol_pound}project_pre_upload_file').uploadify('upload', "*");
                    }else{
                        upload_complete_call_back();
                    }
                }
            }
        });

        function upload_complete_call_back(){

            var draft = false;
            if (${symbol_dollar}('input[name="draft"]').val() == 'true') {
                draft = true;
            }
            if (!draft) {
                getJSON('${symbol_dollar}{basePath}/project/pre-submit.action?id=' + ${symbol_dollar}('${symbol_pound}projectId').val() , function(data){
                    if (data && data.errorInfo) {
                        var error = data.errorInfo;
                        alert(error + " 项目可以在项目列表中找到。");
                        location.href = "${symbol_dollar}{basePath}/";
                    }
                });
            }
            setTimeout("location.href='${symbol_dollar}{basePath}/'", 2000);
            ${symbol_dollar}.messager.show({msg: '保存成功，2秒后自动跳转到工作台，或者直接<a href="${symbol_dollar}{basePath}/">跳转</a>'});
        }
        
        //自定义成本类型，选择器
        ${symbol_dollar}('${symbol_pound}project_pre_custom_budget_selector').change(function(){
        	var self = ${symbol_dollar}(this);
        	var selected = self.find("option:selected");
        	${symbol_dollar}('${symbol_pound}project_pre_custom_budget_remark_td').html(selected.attr('remark'));
        });


    });

    //添加自定义成本费用
    function project_pre_custom_budget_add(){
        var selector = ${symbol_dollar}('${symbol_pound}project_pre_custom_budget_selector');
        var selected = selector.find("option:selected");
        var serialNumber = selector.val();
        if(!serialNumber){
            return;
        }
        var name = selected.text();

        var remark = selected.attr('remark');
        var amount = ${symbol_dollar}('${symbol_pound}custom_budget_amount').numberbox('getValue');
        var templte = Mustache.render(${symbol_dollar}('${symbol_pound}custom_budget_tpl').html(), {name:name, val:serialNumber, remark : remark, amount:amount});

        ${symbol_dollar}('${symbol_pound}project_pre_total_cost_tr').before(templte);
        pre_expenditure_amount_input();
    }

    //项目成本的变化
    function pre_expenditure_amount_input() {
        var total = 0;
        ${symbol_dollar}('input[expenditureAmounts="expenditureAmounts"]').each(function (i, v) {
            total += new Number(${symbol_dollar}(v).val());
        });
        ${symbol_dollar}('${symbol_pound}project_pre_total_cost').numberbox('setValue', total);
    }


    //重新计算资金占用情况
    function expectedFunds_yearCount_interestRate_change() {
        var expectedFunds = ${symbol_dollar}('${symbol_pound}pre_expectedFunds_input').numberbox('getValue');
        var yearCount = ${symbol_dollar}('${symbol_pound}pre_yearCount_input').numberspinner('getValue');
        var interestRate = ${symbol_dollar}('${symbol_pound}pre_interestRate_input').numberbox('getValue');
        ${symbol_dollar}('${symbol_pound}pre_costFunds').numberbox('setValue', (expectedFunds * yearCount * (interestRate / 100)).toFixed(2));
    }


    ${symbol_dollar}(function () {
        var i = 0;
        ${symbol_dollar}('${symbol_pound}cost_fund_dialog_form').form({
            onSubmit: function () {
                if (!${symbol_dollar}(this).form('validate')) {
                    return false;
                }
                add_cost_to_table('${symbol_pound}pre_cost_fund_grid', ${symbol_dollar}(this).serializeObject(), i);
                i++;
                recompute_cost_funds('${symbol_pound}pre_cost_fund_grid');
                ${symbol_dollar}(this).form('reset');
                return false;
            }
        });


        //成本预算
        var total_cost_input_id = 'project_pre_total_cost';

        ${symbol_dollar}('${symbol_pound}project_pre_total_cost').numberbox({
            onChange: amount_onchange_callback
        });

        ${symbol_dollar}('${symbol_pound}project_pre_extimatedincome').numberbox({
            onChange: amount_onchange_callback
        });
        function amount_onchange_callback(newVal, oldVal) {
            //收入 project_pre_extimatedincome
            var project_pre_extimatedincome = new Number(${symbol_dollar}('${symbol_pound}project_pre_extimatedincome').numberbox('getValue'));
            //成本
            var total_cost = ${symbol_dollar}('${symbol_pound}' + total_cost_input_id).numberbox('getValue');
            //毛利润
            var project_pre_grossprofit = new Number(project_pre_extimatedincome) - new Number(total_cost);
            ${symbol_dollar}('${symbol_pound}project_pre_grossprofit_input').numberbox('setValue', project_pre_grossprofit);

            //毛利率
            ${symbol_dollar}('${symbol_pound}project_pre_grossmargin_input').numberbox('setValue', (project_pre_grossprofit / project_pre_extimatedincome) * 100);
            enterprise_amount_onchange_callback(newVal, oldVal);
        };


        //企业管理成本
        ${symbol_dollar}('${symbol_pound}project_pre_enterprise_management_costs').numberbox({
            onChange: enterprise_amount_onchange_callback
        });

        //计算企业所得税
        ${symbol_dollar}('${symbol_pound}project_pre_enterprise_income_tax').numberbox({
            onChange: enterprise_amount_onchange_callback
        });

        //企业管理成本与企业所得税变化时的回调
        function enterprise_amount_onchange_callback(newVal, oldVal) {
            var project_pre_grossprofit = ${symbol_dollar}('${symbol_pound}project_pre_grossprofit_input').numberbox('getValue');

            var project_pre_enterprise_management_costs = ${symbol_dollar}('${symbol_pound}project_pre_enterprise_management_costs').numberbox('getValue');

            var project_pre_enterprise_income_tax = ${symbol_dollar}('${symbol_pound}project_pre_enterprise_income_tax').numberbox('getValue');

            //计算净利润
            var project_pre_net_profit = project_pre_grossprofit - project_pre_enterprise_management_costs - project_pre_enterprise_income_tax;
            ${symbol_dollar}('${symbol_pound}project_pre_net_profit').numberbox('setValue', project_pre_net_profit);


            //收入
            var project_pre_extimatedincome = ${symbol_dollar}('${symbol_pound}project_pre_extimatedincome').numberbox('getValue');

            //计算净利率 = 净利润 / 收入
            var project_pre_net_profit_margin = (project_pre_net_profit / project_pre_extimatedincome) * 100;
            ${symbol_dollar}('${symbol_pound}project_pre_net_profit_margin').numberbox('setValue', project_pre_net_profit_margin);

            if (project_pre_net_profit_margin <= 8) {
                alert('提示：净利率已低于8%!');
            }
        }


    });

    //将项目的资金占用费用添加到表格中
    function add_cost_to_table(tableElement, data, i) {
        var tr = ${symbol_dollar}('<tr></tr>');
        tr.append(createTD('capitaltotakeup[' + i + '].costCaption', data.costCaption));
        tr.append(createTD('capitaltotakeup[' + i + '].startDate', data.startDate));
        tr.append(createTD('capitaltotakeup[' + i + '].yearCount', data.yearCount));
        tr.append(createTD('capitaltotakeup[' + i + '].expectedFunds', data.expectedFunds));
        tr.append(createTD('capitaltotakeup[' + i + '].interestRate', data.interestRate));
        tr.append(createTD('capitaltotakeup[' + i + '].costFunds', data.costFunds));
        tr.append(createTD('capitaltotakeup[' + i + '].remark', data.remark));
        var delete_btn = ${symbol_dollar}('<a href="javascript:;">删除</a>').click(function () {
            var self = ${symbol_dollar}(this);
            self.parent().parent().remove();
            recompute_cost_funds('${symbol_pound}pre_cost_fund_grid');
        });
        tr.append(${symbol_dollar}('<td></td>').append(delete_btn));

        ${symbol_dollar}(tableElement).append(tr);


        function createTD(name, value) {
            var input = ${symbol_dollar}('<input type="hidden"/>').attr('name', name).attr('value', value);
            var result = ${symbol_dollar}('<td></td>');
            result.append(input).append(value);
            return result;
        }
    }

    //重新计算企业资金占用成本
    function recompute_cost_funds(tableElement) {
        var total = 0;
        ${symbol_dollar}(tableElement).find('input[name*="].costFunds"]').each(function (i, v) {
            total += new Number(${symbol_dollar}(v).val());
        });
        ${symbol_dollar}('${symbol_pound}pre_expenditureAmounts_input').numberbox('setValue', total);

    }

    //移除自定义费用
    function project_pre_remove_custom_budget(self){
        ${symbol_dollar}(self).parent().parent().remove();
        pre_expenditure_amount_input();
    }

</script>

<script type="text/templte" id="custom_budget_tpl">

    <tr>
        <td>{{name}}</td>
        <td>{{amount}}<input type="hidden" expenditureAmounts="expenditureAmounts" name="customBudgetAmount" value="{{amount}}" />
            <input  type="hidden" name="customBudgetName" value="{{val}}"/>
        </td>
        <td>
            <!-- 此处name属性不能删，用于确定a标签的唯一性 -->
            <a name="{{name}}{{amount}}" href="javascript:;" onclick="project_pre_remove_custom_budget(this);">移除</a>
            {{remark}}
        </td>
    </tr>

</script>
<script type="text/javascript" src="<%=basePath%>scripts/mustache/mustache.js"></script>

</body>
</html>