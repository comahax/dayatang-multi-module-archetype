#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=basePath%>scripts/jquery-chosen/0.9.9/chosen.css" rel="stylesheet" />
<title>发起投标请求</title>
</head>
<body>
	<div class="container">
		<jsp:include page="/nav.jsp" />
		<div class="row">
			<div class="span12">
				<a class="blackLink" href="<%=basePath%>bidding/bidding-list.action">招投标</a>
				<br/>
				<a class="locationReload">发起投标请求</a>
			</div>
		</div>
		<div class="row">
			<div class="span10 well well-small">
				<s:form id="formId" cssClass="form-horizontal" action="bidding-request-submit.action"  theme="simple"  enctype="multipart/form-data">
						<div class="control-group">
							<label class="control-label"><span style="color: red;">*</span>项目名</label>
							<div class="controls">
								<s:textfield cssClass="span8" id="projectName" name="biddingRequest.projectName"></s:textfield>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" >业主单位</label>
							<div class="controls">
								<s:select id="ownerId" name="ownerId" listKey="id" listValue="name" list="owners" theme="simple"></s:select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" >
								甲方发布公告日期</label>
							<div class="controls">
								<s:textfield id="releaseDate" cssClass="span2"  name="biddingRequest.releaseDate"></s:textfield>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" >报名时间</label>
							<div class="controls">
								<s:textfield id="applyStartDate" name="biddingRequest.signUpDateRange.startDate" cssClass="span2"></s:textfield> 至 
								<s:textfield id="applyEndDate" name="biddingRequest.signUpDateRange.endDate" cssClass="span2"></s:textfield>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">资格预审时间</label>
							<div class="controls">
								<s:textfield id="prequalificationStartDate" name="biddingRequest.prequalificationRange.startDate" cssClass="span2" theme="simple"></s:textfield> 至 
								<s:textfield id="prequalificationEndDate" name="biddingRequest.prequalificationRange.endDate" cssClass="span2" theme="simple"></s:textfield>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">投标时间 </label>
							<div class="controls">
								<s:textfield id="biddingDate" name="biddingRequest.partABiddingDate" cssClass="span2" ></s:textfield>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">项目金额 </label>
							<div class="controls">
								<s:textfield id="projectAmount" name="biddingRequest.projectAmount" cssClass="span2" ></s:textfield>元
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">附件</label>
							<div class="controls">
								<input name="uploads" type="file" >
							</div>
						</div>
						<p></p>
						<p></p>
						<p></p>
						<p></p>
						<div class="control-group">
							<label class="control-label">公告内容</label>
							<div class="controls">
								<s:textarea id="content" name="biddingRequest.content" cssClass="span8"></s:textarea>
							</div>
						</div>
						<shiro:hasPermission name="biddingRequest:add">
						<div  class="control-group">
							<div class="controls">
								<button id="iloadingBtn" type="button" class="btn btn-primary">发布</button>&nbsp;or&nbsp;
								<a class="windowReturn">取消</a>
							</div>
						</div>
						</shiro:hasPermission>
				</s:form>
			</div>
		</div>
	</div>
	<!-- end container -->
	
	<script src="<%=basePath%>scripts/jquery-form/3.14/jquery.form.js" type="text/javascript"></script>
		<script src="<%=basePath %>scripts/jquery-chosen/0.9.9/chosen.jquery.min.js" type="text/javascript" ></script>
	
	
	<!-- 校验 -->
	<script src="<%=basePath%>scripts/jquery-validation/1.9.0/jquery.validate.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/My97DatePicker/4.72/WdatePicker.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/tiny_mce/3/tiny_mce.js" type="text/javascript"></script>
	<!-- 金钱格式化 -->
	<script src="<%=basePath%>scripts/dayaUtils/0.1/jquery.dayaFormatAmount.js" type="text/javascript"></script>
	<script src="<%=basePath%>scripts/application.js" type="text/javascript"></script>
	<script type="text/javascript">
		${symbol_dollar}(function(){
			var validator = validate();
			initApplyRange();
			initSimpleEditor('content');
			initDatePicker('${symbol_pound}releaseDate');
			initDatePicker('${symbol_pound}biddingDate');
			initDatePicker('${symbol_pound}prequalificationStartDate');
			initDatePicker('${symbol_pound}prequalificationEndDate');
			${symbol_dollar}.formatAmount('${symbol_pound}projectAmount');
			
			var ownerChosen = ${symbol_dollar}('${symbol_pound}ownerId').chosen();
			ownerChosen.trigger('change');
			
			formAjaxPost('${symbol_pound}formId', '${symbol_pound}iloadingBtn', validator, function(json){
				if(json.result){
					location.href = "<%=basePath%>worktable/worktable.action";
				}else{
					alert("出错");
				}
			});
			
		});
		function validate(){
			validateOneForm('${symbol_pound}formId');
			${symbol_dollar}('${symbol_pound}projectName').rules('add',{
				required : true,
				messages: {
					required: "项目名必填"
				}
			});		
		}
		
		function initApplyRange() {
			initDatePicker('${symbol_pound}applyStartDate');
			initDatePicker('${symbol_pound}applyEndDate', {
				minDate : '${symbol_pound}F{${symbol_dollar}dp.${symbol_dollar}D(${symbol_escape}'applyStartDate${symbol_escape}');}',
				maxDate : '%y+50'
			});
		}
	</script>
</body>
</html>
