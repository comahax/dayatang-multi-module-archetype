<#list capitaltotakeup as item><tr><td>${item.costCaption}</td><td>${item.startDate?string("yyyy-MM-dd")}</td><td>${item.yearCount}</td><td>${item.expectedFunds}</td><td>${item.interestRate}%</td><td>${item.costFunds}</td><td>${item.remark}</td></tr>
</#list>

