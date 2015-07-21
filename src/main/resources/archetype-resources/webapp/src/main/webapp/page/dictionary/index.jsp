#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/page/scripts.jsp"/>
</head>
<body>

<div class="easyui-tabs" fit="true">
<s:iterator value="dictionaryCategories" id="each">
    <div title="<s:text name="%{${symbol_pound}each}"/>" fit="true" >
        <iframe src="${symbol_dollar}{basePath}/dictionary/details.action?category=<s:property value="%{${symbol_pound}each}"/>" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>
    </div>

</s:iterator>
</div>
</body>
</html>
