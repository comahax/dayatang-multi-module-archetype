<div style="color: red;">单位：万元</div>
<#setting number_format=",##0.00">

<#-- 如果没有设置以哪家公司的名义开展项目---->
<#if !project.constructingOrg?exists>
	<div class="alert alert-error">
	因未设置项目以哪家公司的名义开展，所以，不要变更资金预算。请编辑项目基本信息，设置项目以哪家公司名义开展。
	</div>
	<#else>
	<form id="cost_payments_form_${project.id.toString()}" method="post"
      action="${basePath}/project/change-capital-budget-entry-submit.action">
<input type="hidden" name="projectId" value="${project.id.toString()}"/>

<h3>项目收入情况</h3>
<table>
    <tbody>
    <tr>
        <td style="width:120px;">总收入</td>
        <td><input id="edit_cost_extimatedincome_${project.id.toString()}"
                   type="text"
                   value="${convertYuanToTenThousand(project.estimatedIncome)}"
                   name="estimatedIncome"
                   class="easyui-numberbox" data-options="min:0,groupSeparator: ','"/></td>
        <td>按实际收款产值预计，控制在±10％以内</td>
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
                <th>说明</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>运作费用</td>
                <td><input expenditureAmounts="expenditureAmounts_${project.id.toString()}"
                           value="${convertYuanToTenThousand(project.operation!0)}"
                           name="operation"
                           class="easyui-numberbox"
                           data-options="min:0,value:0,onChange:expenditure_amount_input_${project.id.toString()}"/>
                </td>
                <td>指项目运作发生的车辆、交通、差旅、房租、通信、办公、临时雇佣等运作费用</td>
            </tr>
            <tr>
                <td>人力费用</td>
                <td><input type="text"
                           value="${convertYuanToTenThousand(project.salary!0)}"
                           expenditureAmounts="expenditureAmounts_${project.id.toString()}" name="salary"
                           class="easyui-numberbox"
                           data-options="min:0,value:0,onChange:expenditure_amount_input_${project.id.toString()}"/>
                </td>
                <td>指项目人员的薪酬、福利等人工成本费用</td>
            </tr>
            <tr>
                <td>业务费用</td>
                <td><input type="text"
                           value="${convertYuanToTenThousand(project.market!0)}"
                           expenditureAmounts="expenditureAmounts_${project.id.toString()}" name="market"
                           class="easyui-numberbox"
                           data-options="min:0,value:0,onChange:expenditure_amount_input_${project.id.toString()}"/>
                </td>
                <td>指市场拓展费用</td>
            </tr>
            <tr>
                <td>设备折旧费用</td>
                <td><input type="text"
                           value="${convertYuanToTenThousand(project.deviceDepreciation!0)}"
                           expenditureAmounts="expenditureAmounts_${project.id.toString()}" name="deviceDepreciation"
                           class="easyui-numberbox"
                           data-options="min:0,value:0,onChange:expenditure_amount_input_${project.id.toString()}"/>
                </td>
                <td>指电脑、手机、打印机、传真机、软件、食品、仪表等设备资产折旧，一般按三年折旧</td>
            </tr>
            <tr>
                <td>耗材、辅材费用</td>
                <td><input type="text"
                           value="${convertYuanToTenThousand(project.auxiliaryMaterial!0)}"
                           expenditureAmounts="expenditureAmounts_${project.id.toString()}" name="auxiliaryMaterial"
                           class="easyui-numberbox"
                           data-options="min:0,value:0,onChange:expenditure_amount_input_${project.id.toString()}"/>
                </td>
                <td>指自购的易耗工具、材料等费用</td>
            </tr>
            <tr>
                <td>分包费用</td>
                <td><input
                        value="${convertYuanToTenThousand(project.subcontractCost!0)}"
                        type="text" expenditureAmounts="expenditureAmounts_${project.id.toString()}" name="subcontract"
                        class="easyui-numberbox"
                        data-options="min:0,value:0,onChange:expenditure_amount_input_${project.id.toString()}"/>
                </td>
                <td>指分包给其他公司、施工队的外包费用</td>
            </tr>
            <tr>
                <td>主材费用</td>
                <td><input value="${convertYuanToTenThousand(project.mainMaterial!0)}"
                           type="text"
                           expenditureAmounts="expenditureAmounts_${project.id.toString()}" name="mainMaterial"
                           class="easyui-numberbox"
                           data-options="min:0,value:0,onChange:expenditure_amount_input_${project.id.toString()}"/>
                </td>
                <td>包括外部采购和内部采购的主材费用</td>
            </tr>
            <tr>
                <td>资金占用费用</td>
                <td><input id="project_change_capital_budget_expenditureAmounts_input_${project.id.toString()}"
                           value="${convertYuanToTenThousand(project.fundOccupation!0)}"
                           type="text" expenditureAmounts="expenditureAmounts_${project.id.toString()}"
                           name="fundOccupation" class="easyui-numberbox"
                           data-options="min:0,value:0,disabled:true,onChange:expenditure_amount_input_${project.id.toString()}"/>

                </td>
                <td>根据项目使用资金额及时间进行估算，按银行同期货款利率上浮20%计算</td>
            </tr>
            <tr>
                <td>税金费用</td>
                <td><input
                        value="${convertYuanToTenThousand(project.taxes!0)}"
                        type="text" expenditureAmounts="expenditureAmounts_${project.id.toString()}" name="taxes"
                        class="easyui-numberbox"
                        data-options="min:0,value:0,onChange:expenditure_amount_input_${project.id.toString()}"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td>其他费用</td>
                <td><input
                        value="${convertYuanToTenThousand(project.otherCost!0)}"
                        type="text" expenditureAmounts="expenditureAmounts_${project.id.toString()}"
                        name="other" class="easyui-numberbox"
                        data-options="min:0,value:0,onChange:expenditure_amount_input_${project.id.toString()}"/>
                </td>
                <td>勘察设计及安全费</td>
            </tr>
            <#if customBudgets?exists>
                <#list customBudgets as customBudget>
                <tr>
                    <td>${customBudget.name!""}</td>
                    <td><input
                            value="${convertYuanToTenThousand(customBudget.amount!0.0)}"
                            type="text"
                            expenditureAmounts="expenditureAmounts_${customBudget.serialNumber!""}_${project.id.toString()}"
                            name="customBudgetAmount"
                            class="easyui-numberbox"
                            data-options="min:0,value:0,disabled:true"/>
                        <input name="customBudgetName" value="${customBudget.serialNumber!""}" type="hidden"/>
                    </td>
                    <td><a href="javascript:;" onclick="remove_capitaltotakeup_form_grid_${project.id.toString()}(this);
                            ">移除</a>
                    ${customBudget.remark!""}</td>
                </tr>
                </#list>
            </#if>
            <tr id="project_change_capital_budget_total_cost_tr_${project.id.toString()}">
                <td>成本合计</td>
                <td colspan="2">
                    <input
                            value="${convertYuanToTenThousand(project.totalBudgetAmount!0)}"
                            id="paments_total_cost_${project.id.toString()}" class="easyui-numberbox" type="text"
                            data-options="min:0,disabled:true,value:0"/>
                </td>
            </tr>
            </tbody>
        </table>
        <#if customBudgetTypes?exists>
        <div title="添加自定义费用" class="easyui-panel" style="width:680px;" collapsible="true" collapsed="false">
            <table>
                <tr id="project_change_capital_budget_custom_budget_tr_${project.id.toString()}">
                    <td>
                        费用类型：
                        <select id="project_change_capital_budget_custom_budget_selector_${project.id.toString()}">
                        <option></option>
                    <#list customBudgetTypes as type>
                        <option value="${type.serialNumber.toString()}" remark="${type.remark.toString()}">
                        ${type.text.toString()}</option>
                    </#list>
                    </select>
                    </td>
                    <td>金额：<input id="custom_budget_amount_${project.id.toString()}" type="text"
                                   class="easyui-numberbox"
                                   name="asdfasdfasdf"
                                  data-options="min:0, value:0, groupSeparator: ',', precision: 2"/></td>
                    <td> 备注：<span id="project_change_capital_budget_custom_budget_remark_td_${project.id.toString()}"></span></td>
                </tr>
            </table>
            <a href="javascript:;" onclick="project_change_capital_budget_custom_budget_add_${project.id.toString()}();" class="easyui-linkbutton"
               iconCls="icon-add">添加</a>
        </div>
        <#else >
            <div style="color:red;">本系统目前还没有添加自定义成本类型，请联系管理员！</div>
        </#if>
    </div>


    <div title="资金占用费用估算说明">
        <div class="pull-right">
            <a id="paments_add_fund_btn_${project.id.toString()}" class="easyui-linkbutton"
               onclick="$('#paments_cost_fund_dialog_${project.id.toString()}').dialog('open');"
               iconCls="icon-add">添加</a>
        </div>
        <div style="clear: both;">
            <table id="paments_cost_fund_grid_${project.id.toString()}" class="table">
                <thead>
                <tr>
                    <th>成本说明</th>
                    <th>开始日期</th>
                    <th>占用时间(年)</th>
                    <th>预计占用资金</th>
                    <th>利率</th>
                    <th>占用资金成本</th>
                    <th>备注</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                    <#if hasCapitaltotakeup>
                    <#list occupationOfFunds as   capitaltotakeup>
                    <#assign index = capitaltotakeup_index/>
                    <tr>
                        <td>
                        ${capitaltotakeup.costCaption}
                            <input type="hidden" name="capitaltotakeup[${index?string('###')}].costCaption"
                                   value="${capitaltotakeup.costCaption}"/></td>
                        <td>${capitaltotakeup.startDate?string('yyyy-MM-dd')}
                            <input type="hidden" name="capitaltotakeup[${index?string('###')}].startDate"
                                   value="${capitaltotakeup.startDate?string('yyyy-MM-dd')}"/></td>
                        <td>${capitaltotakeup.yearCount}
                            <input type="hidden" name="capitaltotakeup[${index?string('###')}].yearCount"
                                   value="${capitaltotakeup.yearCount}"/></td>
                        <td>${convertYuanToTenThousand(capitaltotakeup.expectedFunds)}
                            <input type="hidden" name="capitaltotakeup[${index?string('###')}].expectedFunds"
                                   value="${convertYuanToTenThousand(capitaltotakeup.expectedFunds)}"/></td>
                        <td>${capitaltotakeup.interestRate}
                            <input type="hidden" name="capitaltotakeup[${index?string('###')}].interestRate"
                                   value="${capitaltotakeup.interestRate}"/></td>
                        <td>${convertYuanToTenThousand(capitaltotakeup.costFunds)?string(",##0.0#")}
                            <input type="hidden" name="capitaltotakeup[${index?string('###')}].costFunds"
                                   value="${convertYuanToTenThousand(capitaltotakeup.costFunds)}"/></td>
                        <td>${capitaltotakeup.remark}
                            <input type="hidden" name="capitaltotakeup[${index?string('###')}].remark"
                                   value="${capitaltotakeup.remark}"/></td>
                        <td><a href="javascript:;"
                               onclick="remove_capitaltotakeup_form_grid_${project.id.toString()}(this);">删除</a></td>
                    </tr>
                    </#list>
                    </#if>
                </tbody>
            </table>
        </div>
    </div>

</div>


<h3>项目利润情况</h3>
<table>
    <tbody>
    <tr>
        <td>毛利润</td>
        <td><input id="paments_grossprofit_input_${project.id.toString()}"
                   name="grossProfit" type="text"
                   value="${convertYuanToTenThousand(project.grossProfit!0)}"
                   class="easyui-numberbox" data-options="disabled:true,groupSeparator: ','"/></td>
        <td>毛利率</td>
        <td><input id="payments_grossmargin_input_${project.id.toString()}"
                   value="${project.grossMargin!0}"
                   name="grossMargin" type="text" class="easyui-numberbox"
                   data-options="disabled:true,suffix:'%'"/></td>
    </tr>
    <tr>
        <td>企业管理成本</td>
        <td><input id="paments_enterprise_management_costs_${project.id.toString()}"
                   value="${convertYuanToTenThousand(project.enterpriseManagementCosts!0)}"
                   name="enterpriseManagementCosts"
                   type="text" class="easyui-numberbox" data-options="groupSeparator: ','"/></td>
        <td>企业所得税</td>
        <td><input id="payments_enterprise_income_tax_${project.id.toString()}"
                   value="${convertYuanToTenThousand(project.enterpriseIncomeTax!0)}"
                   name="enterpriseIncomeTax" type="text"
                   class="easyui-numberbox" data-options="groupSeparator: ','"/></td>

    </tr>
    <tr>
        <td>净利润</td>
        <td><input id="payments_net_profit_${project.id.toString()}"
                   value="${convertYuanToTenThousand(project.netProfit!0)}"
                   name="netProfit" type="text" class="easyui-numberbox"
                   data-options="disabled:true,groupSeparator: ','"/></td>
        <td>净利率</td>
        <td><input id="payments_net_profit_margin_${project.id.toString()}"
                   value="${project.netProfitMargin!0}"
                   name="netProfitMargin" type="text" class="easyui-numberbox" data-options="disabled:true,suffix:'%'"/>
        </td>
    </tr>
    </tbody>
</table>
<div>
    注：
    <ul>
        <li>企业管理成本指企业管理人员、行政人员、财务人员等分摊成本，在此只需考虑子公司涉及的管理成本</li>
        <li>净利润 ＝ 毛利润 － 企业管理成本 － 企业所得税</li>
        <li>净利率 ＝ 净利润 / 总收入， 请控制成本争取净利率10%以上，原则上净利率低于8%的项目不予承接，如有特殊情况，需要说明具体原因，由广东日海总经理签批。</li>
    </ul>
</div>
<hr/>
<a onclick="$('#cost_payments_form_${project.id.toString()}').submit();" href="javascript:;"
   class="easyui-linkbutton"

   data-options="iconCls:'icon-save'">保存</a>


</form>
<div id="paments_cost_fund_dialog_${project.id.toString()}" class="easyui-dialog" title="添加资金占用，单位：万元"
     style="width: 500px;height: 350px;" resizable="true" modal="true" closed="true">
    <form id="paments_cost_fund_dialog_form_${project.id.toString()}" method="post">
        <table class="table">
            <tr>
                <td>成本说明</td>
                <td><input name="costCaption" class="easyui-validatebox" data-options="required:true"/></td>
                <td>开始日期</td>
                <td><input type="text" name="startDate" class="easyui-datebox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td> 预计占用资金</td>
                <td><input id="payments_expectedFunds_input_${project.id.toString()}" name="expectedFunds" type="text"
                           class="easyui-numberbox"
                           data-options="required:true,onChange:expectedFunds_yearCount_interestRate_change_${project.id.toString()}"/>
                </td>
                <td> 占用时间（年）</td>
                <td><input id="payments_yearCount_input_${project.id.toString()}" name="yearCount"
                           class="easyui-numberspinner" style="width:80px;"
                           data-options="min:0,max:100,increment:1,required:true,onChange:expectedFunds_yearCount_interestRate_change_${project.id.toString()}"
                           value="1"></td>
            </tr>
            <tr>
                <td>利率</td>
                <td><input id="payments_interestRate_input_${project.id.toString()}" name="interestRate" type="text"
                           value="8" class="easyui-numberbox"
                           data-options="required:true,suffix:'%',onChange:expectedFunds_yearCount_interestRate_change_${project.id.toString()}"/>
                </td>
                <td>占用资金成本</td>
                <td><input id="payments_costFunds_${project.id.toString()}" name="costFunds" type="text"
                           class="easyui-numberbox" data-options="required:true,disabled:true"/></td>
            </tr>
            <tr>
                <td>备注</td>
                <td colspan="3"><textarea name="remark"></textarea></td>
            </tr>
        </table>
        <a href="javascript:;" onclick="$('#paments_cost_fund_dialog_form_${project.id.toString()}').submit();"
           class="easyui-linkbutton">提交</a>
    </form>
</div>
<script type="text/javascript">


    $(function () {

        <#if occupationOfFunds?exists>
            var i = new Number('${occupationOfFunds?size!0}');
        <#else>
            var i = 0;
        </#if>
        $('#paments_cost_fund_dialog_form_${project.id.toString()}').form({
            onSubmit: function () {
                if (!$(this).form('validate')) {
                    return false;
                }
                add_cost_to_table_${project.id.toString()}('#paments_cost_fund_grid_${project.id.toString()}', $(this).serializeObject(), i);
                i++;
                recompute_cost_funds_${project.id.toString()}('#paments_cost_fund_grid_${project.id.toString()}');
                $(this).form('reset');
                return false;
            }
        });

        //自定义成本类型，选择器
        $('#project_change_capital_budget_custom_budget_selector_${project.id.toString()}').change(function(){
            var self = $(this);
            var selected = self.find("option:selected");
            $('#project_change_capital_budget_custom_budget_remark_td_${project.id.toString()}').html(selected.attr('remark'));
        });

        //成本预算
        var total_cost_input_id = 'paments_total_cost_${project.id.toString()}';

        $('#paments_total_cost_${project.id.toString()}').numberbox({
            onChange: amount_onchange_callback
        });

        $('#edit_cost_extimatedincome_${project.id.toString()}').numberbox({
            onChange: amount_onchange_callback
        });
        function amount_onchange_callback(newVal, oldVal) {
            //收入 edit_cost_extimatedincome_${project.id.toString()}
            var edit_cost_extimatedincome_${project.id.toString()} = new Number($('#edit_cost_extimatedincome_${project.id.toString()}').numberbox('getValue'));
            //成本
            var total_cost = $('#' + total_cost_input_id).numberbox('getValue');
            //毛利润
            var project_change_capital_budget_grossprofit = new Number(edit_cost_extimatedincome_${project.id.toString()}) - new Number(total_cost);
            $('#paments_grossprofit_input_${project.id.toString()}').numberbox('setValue', project_change_capital_budget_grossprofit);

            //毛利率
            $('#payments_grossmargin_input_${project.id.toString()}').numberbox('setValue', (project_change_capital_budget_grossprofit / edit_cost_extimatedincome_${project.id.toString()}) * 100);
            enterprise_amount_onchange_callback(newVal, oldVal);
        };


        //企业管理成本
        $('#paments_enterprise_management_costs_${project.id.toString()}').numberbox({
            onChange: enterprise_amount_onchange_callback
        });

        //计算企业所得税
        $('#payments_enterprise_income_tax_${project.id.toString()}').numberbox({
            onChange: enterprise_amount_onchange_callback
        });

        //企业管理成本与企业所得税变化时的回调
        function enterprise_amount_onchange_callback(newVal, oldVal) {
            var project_change_capital_budget_grossprofit = $('#paments_grossprofit_input_${project.id.toString()}').numberbox('getValue');

            var paments_enterprise_management_costs = $('#paments_enterprise_management_costs_${project.id.toString()}').numberbox('getValue');

            var payments_enterprise_income_tax = $('#payments_enterprise_income_tax_${project.id.toString()}').numberbox('getValue');

            //计算净利润
            var payments_net_profit = project_change_capital_budget_grossprofit - paments_enterprise_management_costs - payments_enterprise_income_tax;
            $('#payments_net_profit_${project.id.toString()}').numberbox('setValue', payments_net_profit);


            //收入
            var edit_cost_extimatedincome = $('#edit_cost_extimatedincome_${project.id.toString()}').numberbox('getValue');

            //计算净利率 = 净利润 / 收入
            var payments_net_profit_margin = (payments_net_profit / edit_cost_extimatedincome) * 100;
            $('#payments_net_profit_margin_${project.id.toString()}').numberbox('setValue', payments_net_profit_margin);
        }


        //将项目的资金占用费用添加到表格中
        function add_cost_to_table_${project.id.toString()}(tableElement, data, i) {
            var tr = $('<tr></tr>');
            tr.append(createTD('capitaltotakeup[' + i + '].costCaption', data.costCaption));
            tr.append(createTD('capitaltotakeup[' + i + '].startDate', data.startDate));
            tr.append(createTD('capitaltotakeup[' + i + '].yearCount', data.yearCount));
            tr.append(createTD('capitaltotakeup[' + i + '].expectedFunds', data.expectedFunds));
            tr.append(createTD('capitaltotakeup[' + i + '].interestRate', data.interestRate));
            tr.append(createTD('capitaltotakeup[' + i + '].costFunds', data.costFunds));
            tr.append(createTD('capitaltotakeup[' + i + '].remark', data.remark));
            var delete_btn = $('<a href="javascript:;">删除</a>').click(remove_capitaltotakeup_form_grid_${project.id.toString()});
            tr.append($('<td></td>').append(delete_btn));

            $(tableElement).append(tr);


            function createTD(name, value) {
                var input = $('<input type="hidden"/>').attr('name', name).attr('value', value);
                var result = $('<td></td>');
                result.append(input).append(value);
                return result;
            }
        }


        $('#cost_payments_form_${project.id.toString()}').form({
            success: function (data) {
                data = evalJSON(data);
                if (data && data.errorInfo) {
                    alert(data.errorInfo);
                    layout_center_refreshTab('${project.name}');
                } else {
                    alert('提交变更成功！');
                    layout_center_refreshTab('${project.name}');
                }
            }
        });

    });// $();




    function remove_capitaltotakeup_form_grid_${project.id.toString()}(btn) {
        var self = $(btn || this);
        self.parent().parent().remove();
        recompute_cost_funds_${project.id.toString()}('#paments_cost_fund_grid_${project.id.toString()}');
        expenditure_amount_input_${project.id.toString()}();
    }

    //重新计算企业资金占用成本
    function recompute_cost_funds_${project.id.toString()}(tableElement) {
        var total = 0;
        $(tableElement).find('input[name*="].costFunds"]').each(function (i, v) {
            total += new Number($(v).val());
        });
        $('#project_change_capital_budget_expenditureAmounts_input_${project.id.toString()}').numberbox('setValue', total);

    }

    //重新计算资金占用情况
    function expectedFunds_yearCount_interestRate_change_${project.id.toString()}() {
        var expectedFunds = $('#payments_expectedFunds_input_${project.id.toString()}').numberbox('getValue');
        var yearCount = $('#payments_yearCount_input_${project.id.toString()}').numberspinner('getValue');
        var interestRate = $('#payments_interestRate_input_${project.id.toString()}').numberbox('getValue');
        $('#payments_costFunds_${project.id.toString()}').numberbox('setValue', (expectedFunds * yearCount * (interestRate / 100)).toFixed(2));
    }

    //项目成本的变化
    function expenditure_amount_input_${project.id.toString()}() {
        var total = 0;
        $('input[expenditureAmounts*="expenditureAmounts_"]').each(function (i, v) {
            total += new Number($(v).val());
        });
        $('#paments_total_cost_${project.id.toString()}').numberbox('setValue', total);
    }

    /**
     * 添加自定义成本
     * @private
     */
    function project_change_capital_budget_custom_budget_add_${project.id.toString()}(){
        var selector = $('#project_change_capital_budget_custom_budget_selector_${project.id.toString()}');
        var selected = selector.find("option:selected");
        var serialNumber = selector.val();
        if(!serialNumber){
            return;
        }
        var name = selected.text();

        var remark = selected.attr('remark');
        var amount = $('#custom_budget_amount_${project.id.toString()}').numberbox('getValue');
        var templte = Mustache.render($('#custom_budget_tpl_${project.id.toString()}').html(), {name:name,
            val:serialNumber, remark : remark, amount:amount});

        $('#project_change_capital_budget_total_cost_tr_${project.id.toString()}').before(templte);
        expenditure_amount_input_${project.id.toString()}();
    }

</script>
<script type="text/templte" id="custom_budget_tpl_${project.id.toString()}">

    <tr>
        <td>{{name}}</td>
        <td>{{amount}}<input type="hidden"  expenditureAmounts="expenditureAmounts_${project.id.toString()}" name="customBudgetAmount" value="{{amount}}" />
            <input  type="hidden" name="customBudgetName" value="{{val}}"/>
        </td>
        <td>
            <!-- 此处name属性不能删，用于确定a标签的唯一性 -->
            <a name="{{name}}{{amount}}" href="javascript:;" onclick="remove_capitaltotakeup_form_grid_${project.id.toString()}(this);">移除</a>
            {{remark}}
        </td>
    </tr>

</script>
<script type="text/javascript" src="${basePath}/scripts/mustache/mustache.js"></script>
	
	
</#if><#--/未设置以哪家公司名义开展项目--->


