#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
select{
width:auto;
}
</style>
<title>投标情况</title>
</head>
<body>
	<div class="container">
		<jsp:include page="/nav.jsp" />
		<div class="row">
			<div class="span12">
				<a class="blackLink" href="<%=basePath%>bidding/bidding-list.action">招投标</a>
				<br/>
				<a  class="locationReload"><s:property value="bidding.projectName"/></a>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<p class="height1"/>
				<table class="table table-condensed addButton">
					<tbody>
					<tr>
						<th width="90px;" >项目名称</th>
						<td><s:property value="bidding.projectName"/></td>
						<th width="90px;" >项目金额</th>
						<td>
							<shiro:hasPermission name="bidding:edit">
							<s:if test="bidding.projectAmount == null">
								<div>
								<form id="projectAmountForm" action="<%=basePath%>bidding/bidding-project-amount-set-submit.action" >
									<input  class="pan2"  id="projectAmount" name="projectAmount" type="text" />
									<input name="biddingId" value="<s:property value='bidding.id'/>" type="hidden"/>
									<button class="btn btn-small">确定</button>
								</form>
								</div>
							</s:if>
							<s:else>
								<span class="numeral"><s:property value="bidding.projectAmount"/></span>
							</s:else>
							</shiro:hasPermission>
						</td>
						<th width="90px;">业主单位</th>
						<td><s:property value="bidding.owner.name"/></td>
					</tr>
					<tr>
						<th colspan="1">报名时间范围</th>
						<td colspan="1"><s:property value="bidding.signUpDateRange.startDate"/>&nbsp;至&nbsp;<s:property value="bidding.signUpDateRange.endDate"/></td>
						<th colspan="2">资格预审时间范围</th>
						<td colspan="2"><s:property value="bidding.prequalificationRange.startDate"/>&nbsp;至&nbsp;<s:property value="bidding.prequalificationRange.endDate"/></td>
					</tr>
					<tr>
						<th>委托人</th>
						<td colspan="1">
						<s:property value="bidding.principal.name"/>
						<shiro:hasPermission name="bidding:edit">
						<s:if test="bidding.principal == null">
							<div>
							<form action="<%=basePath%>bidding/bidding-principal-set-submit.action" >
								<select name="principalId" style="width:150px;">
								<s:iterator id="principal" value="principals">
								<option value="<s:property value="${symbol_pound}principal.id"/>"><s:property value="${symbol_pound}principal.name"/>--<s:property value="${symbol_pound}principal.organization.name"/></option>
								</s:iterator>
								</select>
								<input name="biddingId" value="<s:property value='bidding.id'/>" type="hidden"/>
								<button type="submit" class="btn btn-small">确定</button>
							</form>
							</div>
						</s:if>
						</shiro:hasPermission>
						</td>
						<th >实际报名时间</th>
						<td>
							<s:property value="bidding.applyDate"/>
							<shiro:hasPermission name="bidding:edit">
							<s:if test="bidding.applyDate == null">
							<div>
							<form id="applyDateForm" action="<%=basePath%>bidding/bidding-apply-date-set-submit.action">
								<input id="applyDate" class="span2" name="applyDate" type="text"/>
								<input name="biddingId" value="<s:property value='bidding.id'/>"  type="hidden"/>
								<button type="submit" class="btn btn-small">确定</button>
							</form>
							</div>
							</s:if>
							</shiro:hasPermission>
						</td>
						<th>实际投标日期</th>
						<td>
							<s:property value="bidding.tenderDate"/>
							<shiro:hasPermission name="bidding:edit">
							<s:if test="bidding.tenderDate == null">
							<div>
							<form id="tenderDateForm" action="<%=basePath%>bidding/bidding-tender-date-set-submit.action">
								<input id="tenderDate" class="span2" name="tenderDate" type="text"/>
								<input name="biddingId" value="<s:property value='bidding.id'/>"  type="hidden"/>
								<button type="submit" class="btn btn-small">确定</button>
							</form>
							</div>
							</s:if>
							</shiro:hasPermission>
						</td>
					</tr>
					<tr>
						<th >保证金金额</th>
						<td>
							<shiro:hasPermission name="bidding:edit">
							<s:if test="bidding.earnestMoneyInfo == null || bidding.earnestMoneyInfo.amountOfEarnestMoney == null">
							<div>
							<form id="amountOfEarnestMoneyForm" action="<%=basePath%>bidding/bidding-earnestmoney-set-submit.action">
								<input class="span2" id="amountOfEarnestMoney" name="amountOfEarnestMoney" type="text"/>
								<input name="biddingId" value="<s:property value='bidding.id'/>"  type="hidden"/>
								<button type="submit" class="btn btn-small">确定</button>
							</form>
							</div>
							</s:if>
							<s:else>
								<span class="numeral"><s:property value="bidding.earnestMoneyInfo.amountOfEarnestMoney"/></span>
							</s:else>
							</shiro:hasPermission>
						</td>
						<th >保证金预估归还日期</th>
						<td>
							<s:property value="bidding.earnestMoneyInfo.estimatedReturnTime"/>
							<shiro:hasPermission name="bidding:edit">
							<s:if test="bidding.earnestMoneyInfo == null || bidding.earnestMoneyInfo.estimatedReturnTime == null">
							<div>
							<form id="estimatedReturnTimeForm" action="<%=basePath%>bidding/bidding-earnestmoney-set-submit.action">
								<input id="estimatedReturnTime" class="span2" name="estimatedReturnTime" type="text"/>
								<input name="biddingId" value="<s:property value='bidding.id'/>"  type="hidden"/>
								<button type="submit" class="btn btn-small">确定</button>
							</form>
							</div>
							</s:if>
							</shiro:hasPermission>
						</td>
						<th>保证金实际归还日期</th>
						<td>
							<s:property value="bidding.earnestMoneyInfo.returnTime"/>
							<shiro:hasPermission name="bidding:edit">
							<s:property value="bidding.earnestMoneyInfo.remark"/>
							<s:if test="bidding.earnestMoneyInfo == null || bidding.earnestMoneyInfo.returnTime == null">
							<div>
								<form id="returnTimeForm" action="<%=basePath%>bidding/bidding-earnestmoney-set-submit.action">
									<input id="returnTime" class="span2" name="returnTime" type="text"/>
									<textarea name="remark" rows="3" cols="3"></textarea>
									<input name="biddingId" value="<s:property value='bidding.id'/>"  type="hidden"/>
									<button type="submit" class="btn btn-small">确定</button>
								</form>
							</div>
							</s:if>
							</shiro:hasPermission>
						</td>
					</tr>
					<tr>
						<th>实际资格预审时间</th>
						<td>
							<s:property value="bidding.prequalificationDate"/>
							<shiro:hasPermission name="bidding:edit">
							<s:if test="bidding.prequalificationDate == null">
							<div>
							<form id="prequalificationDateForm" action="<%=basePath%>bidding/bidding-prequalification-date-set-submit.action">
								<input id="prequalificationDate" class="span2" name="prequalificationDate" type="text"/>
								<input name="biddingId" value="<s:property value='bidding.id'/>"  type="hidden"/>
								<button type="submit" class="btn btn-small">确定</button>
							</form>
							</div>
							</s:if>
							</shiro:hasPermission>
						</td>
						<td>投标结果</td>
						<td colspan="5">
							<s:if test="bidding.isUnkown()">
							<div>
							<form id="biddingStatusForm" action="<%=basePath%>bidding/bidding-result-set-submit.action">
							<select name="biddingStatus">
								<option value="BIDDING_WIN">成功</option>
								<option value="BIDDING_FAIL">失败</option>
							</select>
							<input name="biddingId" value="<s:property value='bidding.id'/>"  type="hidden"/>
							<button type="submit" class="btn btn-small">确定</button>
							</form>
							</div>
							</s:if>
							<s:else>
								<s:if test="bidding.biddingStatus.toString() == 'BIDDING_WIN'">
								<span class="label label-success">中标</span>
								</s:if>
								<s:else>
								<span class="label label-important">不中标</span>
								</s:else>
							</s:else>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=basePath%>scripts/My97DatePicker/4.72/WdatePicker.js"></script>
	<script src="<%=basePath %>scripts/tiny_mce/3/tiny_mce.js" type="text/javascript" ></script>
	<!-- 校验 -->
	<script src="<%=basePath%>scripts/jquery-validation/1.9.0/jquery.validate.js" type="text/javascript"></script>
	<!-- 金钱格式化 -->
	<script src="<%=basePath%>scripts/dayaUtils/0.1/jquery.dayaFormatAmount.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/application.js"></script>
	<script type="text/javascript">
	${symbol_dollar}(function(){
		formatAmount();
		validate();
		initDatePicker('${symbol_pound}applyDate');
		initDatePicker('${symbol_pound}tenderDate');
		initDatePicker('${symbol_pound}estimatedReturnTime');
		initDatePicker('${symbol_pound}returnTime');
		initDatePicker('${symbol_pound}prequalificationDate');
		initSimpleEditor('remarkComment');
	});
	
	function formatAmount(){
		${symbol_dollar}.formatAmount('${symbol_pound}projectAmount');
		${symbol_dollar}.formatAmount('${symbol_pound}amountOfEarnestMoney');
	}
	
	function validate(){
		if(${symbol_dollar}('${symbol_pound}returnTimeForm').size() > 0){
			validateOneForm('${symbol_pound}returnTimeForm');
			${symbol_dollar}('${symbol_pound}returnTime').rules('add',{
				required : true,
				messages: {
					required: "保证金归还日期必填"
				}
			});	
		}
		if(${symbol_dollar}('${symbol_pound}estimatedReturnTimeForm').size() > 0){
			validateOneForm('${symbol_pound}estimatedReturnTimeForm');
			${symbol_dollar}('${symbol_pound}estimatedReturnTime').rules('add',{
				required : true,
				messages: {
					required: "保证金预估归还时间必填"
				}
			});	
		}
		if(${symbol_dollar}('${symbol_pound}amountOfEarnestMoneyForm').size() > 0){
			validateOneForm('${symbol_pound}amountOfEarnestMoneyForm');
			${symbol_dollar}('${symbol_pound}amountOfEarnestMoney').rules('add',{
				required : true,
				messages: {
					required: "保证金金额必填"
				}
			});	
		}
		
		if(${symbol_dollar}('${symbol_pound}prequalificationDateForm').size() > 0){
			validateOneForm('${symbol_pound}prequalificationDateForm');
			${symbol_dollar}('${symbol_pound}prequalificationDate').rules('add',{
				required : true,
				messages: {
					required: "实际预审时间必填"
				}
			});	
		}
		
		if(${symbol_dollar}('${symbol_pound}tenderDateForm').size() > 0){
			validateOneForm('${symbol_pound}tenderDateForm');
			${symbol_dollar}('${symbol_pound}tenderDate').rules('add',{
				required : true,
				messages: {
					required: "投标日期必填"
				}
			});	
		}
		if(${symbol_dollar}('${symbol_pound}applyDateForm').size() > 0){
			validateOneForm('${symbol_pound}applyDateForm');
			${symbol_dollar}('${symbol_pound}applyDate').rules('add',{
				required : true,
				messages: {
					required: "报名日期必填"
				}
			});	
		}
		if(${symbol_dollar}('${symbol_pound}projectAmountForm').size() > 0){
			validateOneForm('${symbol_pound}projectAmountForm');
			${symbol_dollar}('${symbol_pound}projectAmount').rules('add',{
				required : true,
				messages: {
					required: "项目金额必填"
				}
			});	
		}
	}
	</script>
</body>
</html>
