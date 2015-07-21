#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<jsp:include page="/page/include.jsp"/>
<div style="padding: 10px;">
<a href="javascript:;" onclick="edit_personal_base_info_handler();" class="easyui-linkbutton">编辑个人信息</a>
<hr/>

姓名：
<s:property value="person.name"/>    <p/>
性别：
<s:property value="getText(person.gender)"/> <p/>
职位：
<s:property value="person.title"/>  <p/>
手机：
<s:property value="person.mobile"/>  <p/>
电话：
<s:property value="person.tel"/>   <p/>
邮箱：
<s:property value="person.email"/>  <p/>
QQ：
<s:property value="person.qq"/>    <p/>
</div>
<hr/>
<div style="padding: 10px;">
     当前用户名:<s:property value="currentUsername"/>
    <p/>
    <s:if test="editUsernameAble">
    <div class="alert">如果您有未处理的流程任务、发起的流程，修改用户名将会导致您任务丢失。用户名只能修改一次！</div>
    <form id="edit_username_form" action="${symbol_dollar}{basePath}/user/edit-username-submit.action" method="post">
        <input type="text" name="username" value="${symbol_dollar}{username}" class="easyui-validatebox" data-options="required:true" />
        <button type="submit">修改用户名</button>
    </form>
    <script>
        ${symbol_dollar}('${symbol_pound}edit_username_form').form({
            success : function(data){
                data = evalJSON(data);

                if (data.errorInfo) {
                    imessager.alert(data.errorInfo);
                    return;
                }
                alert('修改成功，请使用新用户名重新登录！');
                location.href = "${symbol_dollar}{basePath}/logout.action";
            }
        });
    </script>
    </s:if>

    <table title="拥有角色" class="easyui-datagrid" >
        <thead>
        <tr>
            <th data-options="field:'orgname'">所在机构</th>
            <th data-options="field:'rolename'">角色</th>

        </tr>
        </thead>
        <tbody>
        <s:iterator id="each" value="roleAssignments">
            <tr>
               <td>${symbol_dollar}{each.organization.name}</td>
                <td>${symbol_dollar}{each.role.description}(${symbol_dollar}{each.role.name})</td>
            </tr>
        </s:iterator>
        </tbody>
    </table>

</div>

<div id="edit_personal_base_info_dialog" class="easyui-dialog"
     title="编辑个人资料" style="width:400px;height:400px;padding: 20px;"
     data-options="iconCls:'icon-boss',closed:true, resizable:true,modal:true"   >

    <form id="edit_personal_base_info_form"   action="${symbol_dollar}{basePath}/personal/edit-base-info.action" method="post">
        姓名：<input name="name" class="easyui-validatebox" data-options="required:true" type="text" value="<s:property value="person.name"/>"/> <p/>
        性别：
        <select id="personal_base_info_gender" name="gender">
            <option value="MALE" >男</option>
            <option value="FEMALE">女</option>
        </select><p/>
        职位： <input name="title" type="text" value="<s:property value="person.title"/>"/> <p/>
        手机：<input name="mobile" type="text" value="<s:property value="person.mobile"/>"/> <p/>
        电话：<input name="tel" type="text" value="<s:property value="person.tel"/>"/>   <p/>
        邮箱*： <input name="email" class="easyui-validatebox"
                    data-options="required:true,validType:'email'"
                    type="text"
                    value="<s:property value="person.email"/>"/> <p/>
        QQ:<input name="qq" type="text" value="<s:property value="person.qq"/>"/> <p/>
        <a href="javascript:;" onclick="edit_personal_base_info_save();" class="easyui-linkbutton">保存</a>
    </form>
</div>

<script type="text/javascript">

    ${symbol_dollar}(function(){
        ${symbol_dollar}('${symbol_pound}personal_base_info_gender').val('${symbol_dollar}{person.gender}');
    });

    function edit_personal_base_info_handler(){
        ${symbol_dollar}('${symbol_pound}edit_personal_base_info_dialog').dialog('open');
    }


    function edit_personal_base_info_save(){
        ${symbol_dollar}('${symbol_pound}edit_personal_base_info_form').form('submit',{
            success : function(data){
                data = evalJSON();
                if (data.errorInfo) {
                    imessager.alert(data.errorInfo);
                    return;
                }
                ${symbol_dollar}('${symbol_pound}edit_personal_base_info_dialog').dialog('close');
                layout_center_refreshTab('个人信息');
            }
        });

    }
</script>
