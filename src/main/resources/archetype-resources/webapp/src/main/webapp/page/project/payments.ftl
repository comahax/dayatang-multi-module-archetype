<#setting number_format=",##0.00">
<#setting date_format="${dateFormat}">
<!DOCTYPE html>
<html>
<head>
<#include "/page/scripts.ftl"/>
</head>
<body>
<div class="easyui-tabs">
    <div title="当前收支预算情况 ">
        <#if !project.totalBudgetAmount?exists || (project.totalBudgetAmount <= 0)>
        <div class="alert alert-error">此项目未设置收支预算，请设置！设置方法：填写此项目的资金预算变更表，并提交。等待审批结果。</div>
        </#if>

        <div style="color: red;clear: both;">单位：万元</div>
        总收入：<input value="${convertYuanToTenThousand(project.estimatedIncome!0)}"
                   type="text"
                   class="easyui-numberbox"
                   data-options="disabled:true,groupSeparator: ','"/>
        <hr/>
        成本合计：<input value="${convertYuanToTenThousand(project.totalBudgetAmount!0)}" type="text"
                    class="easyui-numberbox"
                    data-options="disabled:true,groupSeparator: ','"/>
        <table class="table table-bordered" style="width: 550px;">
        <#assign indexArray=(0 .. (budgets?size -1)) />
        <#list indexArray as index >
            <#if index%2 == 0>
                <tr>
                    <td> <#if budgets[index]?exists>${budgets[index].name!""}</#if></td>
                    <td>
                        <#if budgets[index]?exists>
                            ${convertYuanToTenThousand(budgets[index].amount!0.0)}
                    </#if>
                    </td>
                    <td><#if budgets[index+1]?exists>${budgets[index+1].name!""}</#if></td>
                    <td> <#if budgets[index+1]?exists>
                            ${convertYuanToTenThousand(budgets[index+1].amount!0.0)}
                 </#if></td>
                </tr>
            </#if>
        </#list>
        </table>

    <#if hasCapitaltotakeup >
        <div class="easyui-panel" title="资金占用费用情况"
             style="width: 550px;"
             data-options="closable:false,collapsible:true">
            <table class="easyui-datagrid">
                <thead>
                <tr>
                    <th data-options="field:'costCaption'">成本说明</th>
                    <th data-options="field:'startDate'">开始日期</th>
                    <th data-options="field:'yearCount',align:'right'">占用时间（年）</th>
                    <th data-options="field:'expectedFunds',align:'right'">预计占用资金</th>
                    <th data-options="field:'interestRate',align:'right'">利率(%)</th>
                    <th data-options="field:'costFunds',align:'right'">占用资金成本</th>
                    <th data-options="field:'remark'">备注</th>
                </tr>
                </thead>
                <tbody>
                    <#list occupationOfFunds as   ccupationOfFund>
                    <tr>
                        <td>${ccupationOfFund.costCaption}</td>
                        <td>${ccupationOfFund.startDate}</td>
                        <td>${ccupationOfFund.yearCount}</td>
                        <td>${convertYuanToTenThousand(ccupationOfFund.expectedFunds)}</td>
                        <td>${ccupationOfFund.interestRate}</td>
                        <td>${convertYuanToTenThousand(ccupationOfFund.costFunds)}</td>
                        <td>${ccupationOfFund.remark}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </#if>
        <hr/>
        <table class="table table-bordered" style="width: 550px;">
            <tr>
                <td>毛利润</td>
                <td><input type="text"
                           class="easyui-numberbox" value="${convertYuanToTenThousand(project.grossProfit!0)}"
                           data-options="disabled:true,groupSeparator: ','"/></td>
                <td>毛利率</td>
                <td><input value="${project.grossMargin!0}" type="text" class="easyui-numberbox"
                           data-options="disabled:true,suffix:'%'"/></td>
            </tr>
            <tr>
                <td>企业管理成本</td>
                <td><input value="${convertYuanToTenThousand(project.enterpriseManagementCosts!0)}"
                    type="text" class="easyui-numberbox" data-options="disabled:true,groupSeparator: ','"/></td>
                <td>企业所得税</td>
                <td><input value="${convertYuanToTenThousand(project.enterpriseIncomeTax!0)}" type="text"
                    class="easyui-numberbox" data-options="disabled:true,groupSeparator: ','"/></td>

            </tr>
            <tr>
                <td>净利润</td>
                <td><input value="${convertYuanToTenThousand(project.netProfit!0)}" type="text" class="easyui-numberbox"
                    data-options="disabled:true,groupSeparator: ','"/></td>
                <td>净利率</td>
                <td><input value="${project.netProfitMargin!0}" type="text" class="easyui-numberbox" data-options="disabled:true,suffix:'%'"/></td>
            </tr>
        </table>




</div>

    <!--当前收支情况-->
<#if histories?exists>
    <#list histories as history>
    <div title="${history.createTime?string('yyyy-MM-dd')}"
         data-options="href:'${basePath}/project/budget-history.action?projectId=${project.id.toString()}&id=${history.id.toString()}'"> </div>
    </#list>
</#if>
</div>
</body>
</html>




