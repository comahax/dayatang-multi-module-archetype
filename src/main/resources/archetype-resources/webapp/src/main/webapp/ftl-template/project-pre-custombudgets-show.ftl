<#list customBudgets as each>
<tr><td>${each.name}</td><td>${convertYuanToTenThousand(each.amount)}</td><td>${each.remark}</td></tr>
</#list>