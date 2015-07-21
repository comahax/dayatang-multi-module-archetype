#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="/page/include.jsp"/>
    <title>日海通信工程项目管理系统</title>
    <style type="text/css">
        .dialog-content{
            position: relative;
        }
    </style>
</head>
<body class="easyui-layout">

<!-- 页头 -->
<div data-options="region:'north',href:'${symbol_dollar}{basePath}/layout/north.action'"
     style="height: 60px; overflow: hidden;" class="logo"></div>

<!-- 功能导航 -->
<div data-options="region:'west',title:'功能导航'"
     style="width: 150px; overflow: hidden; padding-top: 10px;">
<ul id="imanus" class="easyui-tree">
<li data-options="iconCls:'icon-boss',state: 'closed'">
    <span>个人空间</span>
    <ul>
        <li><span><a class="westMenu" src="${symbol_dollar}{basePath}/personal/base-info.action"
                     href="javascript:;">个人信息</a></span></li>
        <li>
                    <span><a class="westMenu" src="${symbol_dollar}{basePath}/personal/network-disk.action"
                             href="javascript:;">个人网盘</a></span>
        </li>
        <li>
            <span><a class="westMenu" src="${symbol_dollar}{basePath}/personal/modify-psw.action" href="javascript:;">修改密码</a></span>
        </li>
    </ul>
</li>
<li data-options="iconCls:'icon-gears'">
    <span>项目管理</span>
    <ul>
        <shiro:hasPermission name="reportOutput:add">
            <li data-options="iconCls:'icon-report' ">
                        <span><a href="javascript:;" class="westMenu"
                                 src="${symbol_dollar}{basePath}/outputvalue/report.action">汇报产值</a></span>
            </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="project:establish">
            <li data-options="iconCls:'icon-apply'">
                <span><a  href="${symbol_dollar}{basePath}/project/pre.action">立项</a></span>
            </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="project:view">
            <li data-options="iconCls:'icon-tip'">
                    <span><a id="project_list_westmenu" class="westMenu" href="${symbol_pound}"
                             src="${symbol_dollar}{basePath}/project/list.action">项目列表</a></span>
            </li>
        </shiro:hasPermission>

        <%--  <li data-options="iconCls:'icon-tip'">
              <span><a class="westMenu" href="javascript:;"
                       src="${symbol_dollar}{basePath}/sub-project/list.action">单点列表</a></span>
          </li>--%>
    </ul>
</li>
<li data-options="iconCls:'icon-form'">
    <span>款项</span>
    <ul>
         <shiro:hasPermission name="invoiceMgmt">
            <li>
                <span>
                    <a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/receipt-invoice/list.action">总包发票管理</a>
                </span>
            </li>
         </shiro:hasPermission>
    </ul>
</li>
<li data-options="iconCls:'icon-form'">
    <span>合同管理</span>
    <ul>
    <shiro:hasPermission name="singleContract:view">
            <li>
                    <span>
                        <a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/single-contract/list.action">单项合同列表</a>
                    </span>
            </li>
    </shiro:hasPermission>
    <shiro:hasPermission name="frameworkContract:view">
        <li>
            <span>
                <a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/framework-contract/list.action">框架合同列表</a>
            </span>
        </li>
    </shiro:hasPermission>
    <shiro:hasPermission name="subContract:view">
            <li>
                    <span>
                        <a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/subcontract/list.action">分包合同列表</a>
                    </span>
            </li>
    </shiro:hasPermission>

    <%-- <li>
             <span><a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/invoice/list.action">发票管理</a></span>
         </li>--%>
    </ul>
</li>
<li data-options="iconCls:'icon-form'">
    <span>报表</span>
    <ul>
        <li><span><a class="westMenu" href="javascript:;"
                     src="${symbol_dollar}{basePath}/pivot/outputvalue-over.action">产值概况报表</a></span></li>
        <%--<li><span><a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/bidding/list.action">高级产值报表</a></span></li>--%>
    </ul>
</li>
<%-- <li data-options="iconCls:'icon-form'">
     <span>招投标管理</span>
     <ul>
         <li>
             <span><a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/bidding/list.action">投标列表</a></span>
         </li>
     </ul>
 </li>--%>
<li data-options="iconCls:'icon-layout'">
    <span>组织机构</span>
    <ul>
        <%--<li>
            <span>
                <a class="westMenu" href="javascript:;"
                   src="${symbol_dollar}{basePath}/organizational-struct/employee.action">人员管理 </a>
            </span>
        </li>--%>
        <shiro:hasPermission name="institution:view">
            <li>
                    <span>
                        <a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/organizational-struct/tree.action">人员管理</a>
                    </span>
            </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="institution:edit">
            <li>
                    <span>
                        <a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/organizational-struct/edit.action">编辑机构树</a>
                    </span>
            </li>
        </shiro:hasPermission>
    </ul>
</li>
<li data-options="iconCls:'icon-layout'">
    <span>单位管理</span>
    <ul>
        <shiro:hasPermission name="ownerOrganization:view">
            <li>
                <span><a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/owner/list.action">客户单位</a></span>
            </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="supervisorOrganization:view">
            <li>
                <span><a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/supervisor-org/list.action">监理单位</a></span>
            </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="designerOrganization:view">
            <li>
                    <span><a class="westMenu" href="javascript:;"
                             src="${symbol_dollar}{basePath}/design-org/list.action">设计单位</a></span>
            </li>
        </shiro:hasPermission>


        <shiro:hasPermission name="constructteamOrganization:view">
            <li>
                <span><a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/cooperation-org/list.action">合作单位</a></span>
            </li>
        </shiro:hasPermission>

    </ul>
</li>

<shiro:hasPermission name="licenseQueryMgmt">
<li data-options="iconCls:'icon-base'">
    <a class="westMenu" href="javascript:;"
       src="${symbol_dollar}{basePath}/license/list.action">证照管理</a></span>
</li>
</shiro:hasPermission>

<shiro:hasPermission name="roleMgmt">
    <li data-options="iconCls:'icon-layout'">
        <span>角色用户权限管理</span>
        <ul>
            <shiro:hasPermission name="role:edit">
                <li>
                    <span><a class="westMenu" href="javascript:;" src="${symbol_dollar}{basePath}/role/list.action">角色管理</a></span>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="role:edit">
                <li>
                    <span><a class="westMenu"
                             href="javascript:;"
                             src="${symbol_dollar}{basePath}/permission/setup.action">权限设置</a></span>
                </li>
            </shiro:hasPermission>

        </ul>
    </li>
</shiro:hasPermission>
<li data-options="iconCls:'icon-base'">
    <span>系统</span>
    <ul>
        <shiro:hasPermission name="process:deploy">
            <li data-options="iconCls:'icon-gears'"><span>
                    <a class="westMenu" href="javascript:;"
                       src="${symbol_dollar}{basePath}/project-type/list.action">项目类型管理</a></span>
            </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="process:deploy">
            <li data-options="iconCls:'icon-gears'"><span>
                    <a id="worktable_list_processes" class="westMenu" href="javascript:;"
                       src="${symbol_dollar}{basePath}/process/list-processes.action">流程定义</a></span>
            </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="dictionary:view">
            <li data-options="iconCls:'icon-gears'"><span>
                    <a class="westMenu" href="javascript:;"
                       src="${symbol_dollar}{basePath}/dictionary/index.action">字典管理</a></span>
            </li>
        </shiro:hasPermission>


        <shiro:hasPermission name="specialty:view">
            <li data-options="iconCls:'icon-gears'"><span>
                    <a class="westMenu" href="javascript:;"
                       src="${symbol_dollar}{basePath}/specialty/list.action">专业管理</a></span>
            </li>
        </shiro:hasPermission>

    </ul>
</li>
</ul>
</div>
<!-- /功能导航 -->


<!-- 工作台 -->
<div data-options="region:'center'" style="overflow: hidden;">
    <div id="layout_center_tabs" style="overflow: hidden;">

        <div title="工作台" data-options="closable:false">
            <div class="easyui-tabs">
                <div title="<a href='${symbol_dollar}{basePath}/task/fill-in-task-form.action'>我的任务(${symbol_dollar}{tasksTotal})</a>" id="myTaskTab">

                </div>
                <div title="我发起的流程" data-options="closable:false">
                    <table id="myProcess" class="easyui-datagrid"
                           data-options="url:'<%=basePath%>process/my-running-processes-json.action',
                                   fitColumns:true,
                                   singleSelect:true,
                                   pagination : true,
                                   pageSize:20"
                            >
                        <thead>
                        <tr>
                            <th data-options="field:'processName',align:'center'" width="60">流程名</th>
                            <th data-options="field:'id',hidden:true,align:'center'" width="100">id</th>
                            <th data-options="field:'title',align:'center'" width="100">事项</th>
                            <th data-options="field:'initiator',align:'center'" width="60">发起人</th>
                            <th data-options="field:'activityName',align:'center',formatter:getMyActiveName" width="80">当前节点</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div title="历史流程" data-options="closable:false">
                    <table id="historyProcess"  class="easyui-datagrid"
                           data-options="url:'<%=basePath%>process/my-histories.action',
                                   fitColumns:true,
                                   singleSelect:true,
                                   pagination : true,
                                   pageSize:20"
                            >
                        <thead>
                        <tr>
                            <th data-options="field:'processName',align:'center'" width="80">流程名</th>
                            <th data-options="field:'id',hidden:true">id</th>
                            <th data-options="field:'title',align:'center'" width="80">事项</th>
                            <th data-options="field:'initiator',align:'center' " width="60">发起人</th>
                            <th data-options="field:'endTime',align:'center'" width="80">发起时间</th>
                            <th data-options="field:'startTime',align:'center'" width="80">流程结束时间</th>
                            <th data-options="field:'branchRemrk',align:'center'"  width="80">描述</th>
                            <th data-options="field:'activityName',align:'center',formatter:function(){return '<font color=${symbol_escape}'green${symbol_escape}'>已完成</font>'}" width="80">当前节点</th>
                            <th data-options="field:'branchApproved',align:'center'" width="50">是否通过</th>
                        </tr>
                        </thead>
                    </table>
                </div>

            </div>
        </div>
    </div>
    <div id="layout_center_tabsMenu" style="width: 120px; display: none;">
        <div type="refresh">刷新</div>
        <div class="menu-sep"></div>
        <div type="close">关闭</div>
        <div type="closeOther">关闭其他</div>
        <div type="closeAll">关闭所有</div>
    </div>
</div>







<script type="text/javascript">



    function getActiveName(value,row,index){
        return '<a pid='+row.processInstanceId+' href="javascript:;" onclick="showProcess('+row.processInstanceId+')">'+value+'</a>';
    }
    function getMyActiveName(value,row,index){
        return '<a pid='+row.id+' href="javascript:;" onclick="showProcess('+row.id+')">'+value+'</a>';
    }

    function showProcess(processInstanceId){
        graphTrace({
            pid:processInstanceId,
            ctx:'${symbol_dollar}{basePath}'
        });
    }






</script>

<script type="text/javascript">
    ${symbol_dollar}(function(){



        //整体布局标签页上的菜单
        ${symbol_dollar}('${symbol_pound}layout_center_tabsMenu').menu({
            onClick: function (item) {
                var curTabTitle = ${symbol_dollar}(this).data('tabTitle');
                var type = ${symbol_dollar}(item.target).attr('type');

                if (type === 'refresh') {
                    layout_center_refreshTab(curTabTitle);
                    return;
                }

                if (type === 'close') {
                    var t = ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getTab', curTabTitle);
                    if (t.panel('options').closable) {
                        ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('close', curTabTitle);
                    }
                    return;
                }

                var allTabs = ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('tabs');
                var closeTabsTitle = [];

                ${symbol_dollar}.each(allTabs, function () {
                    var opt = ${symbol_dollar}(this).panel('options');
                    if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
                        closeTabsTitle.push(opt.title);
                    } else if (opt.closable && type === 'closeAll') {
                        closeTabsTitle.push(opt.title);
                    }
                });

                for (var i = 0; i < closeTabsTitle.length; i++) {
                    ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('close', closeTabsTitle[i]);
                }
            }
        });
//整体布局中间块
        ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs({
            fit: true,
            border: false,
            onContextMenu: function (e, title) {
                e.preventDefault();
                ${symbol_dollar}('${symbol_pound}layout_center_tabsMenu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                }).data('tabTitle', title);
            },
            tools: [
                {
                    iconCls: 'icon-reload',
                    handler: function () {
                        var href = ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getSelected').panel('options').href;
                        if (href) {
                            var index = ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getTabIndex', ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getSelected'));
                            ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getTab', index).panel('refresh');
                        } else {
                            var panel = ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getSelected').panel('panel');
                            var frame = panel.find('iframe');
                            try {
                                if (frame.length > 0) {
                                    for (var i = 0; i < frame.length; i++) {
                                        frame[i].contentWindow.document.write('');
                                        frame[i].contentWindow.close();
                                        frame[i].src = frame[i].src;
                                    }
                                    if (${symbol_dollar}.browser.msie) {
                                        CollectGarbage();
                                    }
                                }
                            } catch (e) {
                            }
                        }
                    }
                },
                {
                    iconCls: 'icon-cancel',
                    handler: function () {
                        var index = ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getTabIndex', ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getSelected'));
                        var tab = ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getTab', index);
                        if (tab.panel('options').closable) {
                            ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('close', index);
                        } else {
                            ${symbol_dollar}.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭', 'error');
                        }
                    }
                }
            ]
        });// end tab
//west 导航
        ${symbol_dollar}('.westMenu').click(function () {
            var self = ${symbol_dollar}(this);
            var title = self.html();
            var url = self.attr('src');
            layout_center_addTabFun({
                title: title,
                closable: true,
                content : "<iframe src="+ url +" frameborder=${symbol_escape}"0${symbol_escape}" style=${symbol_escape}"border:0;width:100%;height:99.4%;${symbol_escape}"></iframe>"
            });
            return false;
        });
    });  // /jquery
    //关闭tab窗口
    function layout_center_close_current_tab() {
        var index = ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getTabIndex', ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getSelected'));
        var tab = ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getTab', index);
        if (tab.panel('options').closable) {
            ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('close', index);
        } else {
            ${symbol_dollar}.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭', 'error');
        }
    }

    //整体布局的刷新按钮
    function layout_center_refreshTab(title) {
        ${symbol_dollar}('${symbol_pound}layout_center_tabs').tabs('getTab', title).panel('refresh');
    }

    //整体布局的添加标签页
    function layout_center_addTabFun(opts) {
        var t = ${symbol_dollar}('${symbol_pound}layout_center_tabs');

        if (t.tabs('exists', opts.title)) {
            t.tabs('select', opts.title);
        } else {
            if(opts.href && !opts.content){
                opts.content = "<iframe src="+ opts.href +" frameborder=${symbol_escape}"0${symbol_escape}" style=${symbol_escape}"border:0;width:100%;height:99.4%;${symbol_escape}"></iframe>";
                opts.href = null;
            }
            if(!opts.href && !opts.content){
                alert('参数不全，无法确定跳转页面！');
                return
            }
            t.tabs('add', opts);
        }
    }
</script>


<%--前端数据库，用于缓存前端数据 --%>
<div id="front_db"></div>
<script type="text/javascript" src="${symbol_dollar}{basePath}/scripts/process/process-diagram.js"></script>

</body>
</html>
