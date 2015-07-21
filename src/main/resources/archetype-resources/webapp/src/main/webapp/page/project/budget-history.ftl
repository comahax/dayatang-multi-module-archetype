<#setting number_format=",##0.00">
<div  style="color: red;clear: both;">单位：万元</div>
总收入：<input value="${convertYuanToTenThousand(history.estimatedIncome!0)}"
        type="text"
        class="easyui-numberbox"
        data-options="disabled:true,groupSeparator: ','"/>
<hr/>
成本合计：<input value="${convertYuanToTenThousand(history.totalBudgetAmount!0)}" type="text"
class="easyui-numberbox"
data-options="disabled:true,groupSeparator: ','"/>
<table class="table table-bordered " style="width: 550px;">
    <#assign indexArray=(0 .. (customBudgets?size -1)) />
    <#list indexArray as index >
        <#if index%2 == 0>
            <tr>
                <td> <#if customBudgets[index]?exists>${customBudgets[index].name!""}</#if></td>
                <td>
                    <#if customBudgets[index]?exists>
                        ${convertYuanToTenThousand(customBudgets[index].amount!0.0)}
                    </#if>
                </td>
                <td><#if customBudgets[index+1]?exists>${customBudgets[index+1].name!""}</#if></td>
                <td> <#if customBudgets[index+1]?exists>
                    ${convertYuanToTenThousand(customBudgets[index+1].amount!0.0)}
                </#if></td>
            </tr>
        </#if>
    </#list>
</table>
<#if history.capitaltotakeups?exists && (history.capitaltotakeups?size > 0)>
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
        <#list history.capitaltotakeups as   ccupationOfFund>
        <tr>
            <td>${ccupationOfFund.costCaption}</td>
            <td>${ccupationOfFund.startDate}</td>
            <td>${ccupationOfFund.yearCount}</td>
            <td>${convertYuanToTenThousand(ccupationOfFund.expectedFunds!0)}</td>
            <td>${ccupationOfFund.interestRate}</td>
            <td>${convertYuanToTenThousand(ccupationOfFund.costFunds!0)}</td>
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
                   class="easyui-numberbox" value="${convertYuanToTenThousand(history.grossProfit!0)}"
            data-options="disabled:true,groupSeparator: ','"/></td>
        <td>毛利率</td>
        <td><input value="${history.grossMargin!0}" type="text" class="easyui-numberbox"
                   data-options="disabled:true,suffix:'%'"/></td>
    </tr>
    <tr>
        <td>企业管理成本</td>
        <td><input value="${convertYuanToTenThousand(history.enterpriseManagementCosts!0)}"
            type="text" class="easyui-numberbox" data-options="disabled:true,groupSeparator: ','"/></td>
        <td>企业所得税</td>
        <td><input value="${convertYuanToTenThousand(history.enterpriseIncomeTax!0)}" type="text"
            class="easyui-numberbox" data-options="disabled:true,groupSeparator: ','"/></td>

    </tr>
    <tr>
        <td>净利润</td>
        <td><input value="${convertYuanToTenThousand(history.netProfit!0)}" type="text" class="easyui-numberbox"
            data-options="disabled:true,groupSeparator: ','"/></td>
        <td>净利率</td>
        <td><input value="${history.netProfitMargin!0}" type="text" class="easyui-numberbox" data-options="disabled:true,suffix:'%'"/></td>
    </tr>
</table>