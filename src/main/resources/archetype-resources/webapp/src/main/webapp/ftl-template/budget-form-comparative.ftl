<h4>
    项目收入情况
</h4>
<table class="table table-bordered">
    <thead>
        <tr>
            <td>&nbsp;</td>
            <td>金额（变更前）</td>
            <td>金额（变更后）</td>
        </tr>
    </thead>
    <tr>
        <td width="200">预估总收入</td>
        <td >${convertYuanToTenThousand(project.estimatedIncome!0)}</td>
        <td >${estimatedIncome}</td>
    </tr>
</table>


<h4>成本预算情况</h4>
<table  class="table table-bordered">
    <thead>
        <tr>
            <th>成本名称</th>
            <th>金额（变更前）</th>
            <th>金额（变更后）</th>
            <th>说明</th>
        </tr>
    </thead>
    <tbody>
    <tr>
        <td >运作费用</td>
        <td>${convertYuanToTenThousand(project.operation!0)}</td>
        <td>${OPERATION!0}</td>
        <td>指项目运作发生的车辆、交通、差旅、房租、通信、办公、临时雇佣等运作费用</td>
    </tr>
    <tr>
        <td>人力费用</td>
        <td>${convertYuanToTenThousand(project.salary!0)}</td>
        <td>${SALARY!0}</td>
        <td>指项目人员的薪酬、福利等人工成本费用</td>
    <tr>
        <td>业务费用</td>
        <td>${convertYuanToTenThousand(project.market!0)}</td>
        <td>${MARKET!0}</td>
        <td>指市场拓展费用</td>
    </tr>
    <tr>
        <td>设备折旧费用</td>
        <td>${convertYuanToTenThousand(project.deviceDepreciation!0)}</td>
        <td>${DEVICE_DEPRECIATION!0}</td>
        <td>指电脑、手机、打印机、传真机、软件、食品、仪表等设备资产折旧，一般按三年折旧</td>
    </tr>
    <tr>
        <td>耗材、辅材费用</td>
        <td>${convertYuanToTenThousand(project.auxiliaryMaterial!0)}</td>
        <td>${AUXILIARY_MATERIAL!0}</td>
        <td>指自购的易耗工具、材料等费用</td>
    </tr>
    <tr>
        <td>分包费用</td>
        <td>${convertYuanToTenThousand(project.subcontractCost!0)}</td>
        <td>${SUBCONTRACT!0}</td>
        <td>指分包给其他公司、施工队的外包费用</td>
    </tr>
    <tr>
        <td>主材费用</td>
        <td>${convertYuanToTenThousand(project.mainMaterial!0)}</td>
        <td>${MAIN_MATERIAL!0}</td>
        <td>包括外部采购和内部采购的主材费用</td>
    </tr>
    <tr>
        <td>资金占用费用</td>
        <td>${convertYuanToTenThousand(project.fundOccupation!0)}</td>
        <td>${FUND_OCCUPATION!0}</td>
        <td>根据项目使用资金额及时间进行估算，按银行同期货款利率上浮20%计算
        </td>
    </tr>
    <tr>
        <td>税金费用</td>
        <td>${convertYuanToTenThousand(project.taxes!0)}</td>
        <td>${TAXES!0}</td>
        <td></td>
    </tr>

    <tr>
        <td>其他费用</td>
        <td>${convertYuanToTenThousand(project.otherCost!0)}</td>
        <td>${OTHER!0}</td>
        <td>勘察设计及安全费</td>
    </tr>

    <#if customBudgets?exists>
    <#list customBudgets as eachCustomBudget>
        <tr>
            <td>${eachCustomBudget.name}</td>
            <td><#if eachCustomBudget.preEditAmount?exists>${convertYuanToTenThousand(eachCustomBudget.preEditAmount)}</#if></td>
            <td><#if eachCustomBudget.amount?exists>${convertYuanToTenThousand(eachCustomBudget.amount)}</#if></td>
            <td>${eachCustomBudget.remark!""}</td>
        </tr>
    </#list>
    </#if>
    <tr>
        <td>成本合计</td>
        <td >${convertYuanToTenThousand(project.totalBudgetAmount!0)}</td>
        <td >${totalBudgetAmount!0}</td>
        <td>&nbsp;</td>
    </tr>
    </tbody>
</table>

<h4>资金占用说明（变更前）</h4>
<table class="table table-bordered">
    <thead><tr><th>成本说明</th><th>开始日期</th><th>占用时间(年)</th><th>预计占用资金</th><th>利率</th><th>占用资金成本</th><th>备注</th></tr>
    </thead><tbody>
${historyCapitotakeupsHtml}
</tbody></table>

<h4>资金占用说明（变更后）</h4>
<table class="table table-bordered">
    <thead><tr><th>成本说明</th><th>开始日期</th><th>占用时间(年)</th><th>预计占用资金</th><th>利率</th><th>占用资金成本</th><th>备注</th></tr>
    </thead><tbody>
${capitaltotakeupsHtml}
</tbody></table>

<h4>项目毛利情况（变更前）</h4>
<table  class="table table-bordered ">
    <tr>
        <td>毛利润</td>
        <td>${convertYuanToTenThousand(project.grossProfit!0)}</td>
        <td>毛利率</td>
        <td>${project.grossMargin!0}%</td>
    </tr>
    <tr>
        <td>企业管理成本</td>
        <td>${convertYuanToTenThousand(project.enterpriseManagementCosts!0)}</td>
        <td>企业所得税</td>
        <td>${convertYuanToTenThousand(project.enterpriseIncomeTax!0)}</td>

    </tr>
    <tr>
        <td>净利润</td>
        <td>${convertYuanToTenThousand(project.netProfit!0)}</td>
        <td>净利率</td>
        <td>${project.netProfitMargin!0}%</td>
    </tr>
</table>

<h4>项目毛利情况（变更后）</h4>
<table  class="table table-bordered ">
    <tr>
        <td>毛利润</td>
        <td>${grossProfit!0}</td>
        <td>毛利率</td>
        <td>${grossMargin!0}%</td>
    </tr>
    <tr>
        <td>企业管理成本</td>
        <td>${enterpriseManagementCosts!0}</td>
        <td>企业所得税</td>
        <td>${enterpriseIncomeTax!0}</td>

    </tr>
    <tr>
        <td>净利润</td>
        <td>${netProfit!0}</td>
        <td>净利率</td>
        <td>${netProfitMargin!0}%</td>
    </tr>
</table>
