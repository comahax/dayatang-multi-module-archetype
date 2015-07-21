<link id="easyuiTheme" rel="stylesheet" href="${basePath}/scripts/easyui/themes/metro-blue/easyui.css" type="text/css"/>
<link rel="stylesheet" href="${basePath}/scripts/easyui/themes/icon.css" type="text/css"/>
<link rel="stylesheet" href="${basePath}/styles/application.css"/>
<!-- easyui控件 -->
<script type="text/javascript" src="${basePath}/scripts/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/scripts/easyui/jquery.easyui.min.js"></script>

<script type="text/javascript" src="${basePath}/scripts/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>


<script type="text/javascript" src="${basePath}/scripts/easyui/easyuiPanelOnMove.js"></script>
<script type="text/javascript" src="${basePath}/scripts/easyui-ex/ex.js"></script>
<link rel="stylesheet" href="${basePath}/scripts/uploadify/uploadify.css"/>
<script type="text/javascript" src="${basePath}/scripts/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${basePath}/scripts/jquery-download/download.js"></script>

<script type="text/javascript" >
$.ajaxSettings.async = false;

proj_app = {
    web_root: '${basePath}/',
    project_pre_dialog_id: 'project_pre_dialog', //project_pre_dialog 为立项弹出框ID，不能修改
    //拿到当前用户所拥有的角色
    get_currentuser_roles: function (options) {
        var settings = $.extend({
            url: '${basePath}/user/current-users-roles.action'
        }, options || {});

        return getJSON(settings.url);
    },

    //角色选择器：列出可分配的角色列表的json
    assigned_role: function (inputId, options) {
        var selector = returnJqObject(inputId);
        var settings = $.extend({
            url: '${basePath}/role/assigned-json.action',
            width: '300'
        }, options || {});

        var data = getJSON(settings.url);

        selector.combotree({
            width: settings.width,
            data: data4TreeData(data.rows)
        });

        //将服务器端传过来的负责部门数据转成树的格式
        function data4TreeData(orgs) {
            if (!orgs) {
                return;
            }
            var results = [];
            $.each(orgs, function (i, v) {
                var item = {id: '', text: ''};
                item.id = v.id;
                item.text = v.description;
                results.push(item);
            });

            return results;
        }// responsibleData4TreeData

    },
    //访问范围内的在建项目
    constructings_project_selector: function (inputId, init_id,options) {
        if (!init_id) {
            init_id = 0;
        }
        var settings = $.extend({
            url: "${basePath}/project/constructings-json.action"
        }, options || {});
        var obj = returnJqObject(inputId);
        obj.combogrid({
            value: init_id,
            mode: 'remote',
            panelWidth: 450,
            idField: 'id',
            textField: 'name',
            url: settings.url,
            toolbar: [
                {
                    text: '搜索',
                    iconCls: 'icon-search',
                    handler: function () {
                        $.messager.prompt('搜索', '请输入项目的关键字:', function (r) {
                            var datagrid = obj.combogrid('grid');
                            datagrid.datagrid('load', {name: r});
                            obj.combogrid("showPanel");
                        });

                    }
                }
            ],
            columns: [
                [
                    {field: 'id', title: 'id', hidden: true},
                    {field: 'name', title: '项目名', width: 250},
                    {field: 'startDate', title: '开工日期', width: 80},
                    {field: 'predictFinishDate', title: '计划完工', width: 80},
                    {field: 'remark', title: '备注', width: 100}
                ]
            ]
        });
    },
    //项目类型选择器
    project_type_selector: function (inputId) {
        var result = returnJqObject(inputId);

        var data = getJSON('${basePath}/project-type-json.action');
        result.combobox({
            valueField: 'serialNumber',
            textField: 'text',
            data: data.rows,
            multiple: false
        });

        return result;

    },
    //初始化权限树
    init_permissions_tree: function (ulElement, options, role_id) {
        var settings = $.extend({
            data: {
                key: {
                    title: 'name',
                    name: 'description'
                },
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPId: 0
                },
                keep: {
                    parent: true
                }
            }
        }, options || {});

        var data = getJSON('${basePath}/permission/all-json.action');
        data = proj_app.convert_permission_serverdata_to_clientdata(data.results);
        $.fn.zTree.init($(ulElement), settings, data);

    },

    //将服务器端传来的数据转成客户端需要的
    convert_permission_serverdata_to_clientdata: function (data) {
        if (!data) {
            return [];
        }
        var result = [];

        $.each(data, function (i, v) {
            var node = v;
            node.isParent = true;
            result.push(node);
        });
        return result;
    },

    /**
     *字典选择器
     * @param inputId
     * @param url
     * @returns {{}}
     */
    dictionary_selector: function (inputId, url) {
        var result = returnJqObject(inputId);

        var data = getJSON(url);
        result.combobox({
            valueField: 'serialNumber',
            textField: 'text',
            data: data.results,
            multiple: false
        });

        return result;
    },
    //收款类型选择器
    receipt_category_selector: function (inputId) {
        return proj_app.dictionary_selector(inputId, '${basePath}/receipt/categories-json.action')
    },
    //合同类型选择器
    contract_category_selector: function (inputId) {
        return proj_app.dictionary_selector(inputId, '${basePath}/contract/categories-json.action')
    },

    //施工单位类型选择器
    cooperation_selector: function (inputId) {

        var selector = returnJqObject(inputId);
        var data = getJSON('${basePath}/cooperation-org/list-name-id-json-enabled.action');
        selector.combobox({
            valueField: 'id',
            textField: 'name',
            data: data.rows,
            multiple: false
        });

        return selector;

    },

    //创建可作为开展项目的单位的选择器组件
    //url el. "
    //${basePath}/organizational-struct/widthin-scope-json.action?parentId="
    constructing_party: function (inputId, options) {
        var settings = $.extend({
            url: '${basePath}/organizational-struct/contracting-party.action',
            width: '200',
            required: true
        }, options || {});

        var tree = returnJqObject(inputId);
        var data = getJSON(settings.url);

        tree.combotree({
            width: settings.width,
            required: settings.requied,
            data: responsibleData4TreeData(data.results)
        });

        //将服务器端传过来的负责部门数据转成树的格式
        function responsibleData4TreeData(orgs) {
            if (!orgs) {
                return;
            }
            var results = [];
            $.each(orgs, function (i, v) {
                var item = {id: '', text: '', state: 'closed', children: []};
                item.id = v.id;
                item.text = v.name;
                item.attributes = v;
                results.push(item);
            });

            return results;
        }// responsibleData4TreeData

        return tree;
    } // /create_organizational_scope
    ,
    /**
     *内部机构的选择器
     */
    internalorg_selector: function (inputId, options) {
        if (options) {
            options.url = '${basePath}/organizational-struct/children-of.action?parentId=';
        } else {
            options = {url: '${basePath}/organizational-struct/children-of.action?parentId='};
        }
        proj_app.organizational_scope(inputId, options);
    },
    //创建负责单位选择器组件
    //url el. "
    //${basePath}/organizational-struct/widthin-scope-json.action?parentId="
    organizational_scope: function (inputId, options) {
        var settings = $.extend({
            url: '${basePath}/organizational-struct/within-scope-json.action?parentId=',
            width: '200',
            required: true,
            onChange: function () {
            }
        }, options || {});

        var tree = returnJqObject(inputId);
        var data = getJSON(settings.url);
        tree.combotree({
            width: settings.width,
            required: settings.requied,
            data: responsibleData4TreeData(data.results),
            onChange: settings.onChange,
            onBeforeExpand: treeExpendHandler
        });


        //将服务器端传过来的负责部门数据转成树的格式
        function responsibleData4TreeData(orgs) {
            if (!orgs) {
                return;
            }
            var results = [];
            $.each(orgs, function (i, v) {
                var item = {id: '', text: '', state: 'closed', children: []};
                item.id = v.id;
                item.text = v.name;
                item.attributes = v;
                results.push(item);
            });

            return results;
        }// responsibleData4TreeData

        //单击树，展开树的事件处理
        function treeExpendHandler(node) {
            $.getJSON(settings.url + node.id, function (childData) {
                if (!childData || !childData.results) {
                    return;
                }
                tree.combotree("tree").tree('append', {
                    parent: node.target,
                    onChange: settings.onChange,
                    data: responsibleData4TreeData(childData.results)
                });
            });
        }
    } // /create_organizational_scope
    ,
    //业主及业主联系人选择器
    owner_selector: function (inputId, options, init_info) {
        var settings = $.extend({
            url: '${basePath}/owner/list-name-json.action',
            required: true,
            width: '200',
            contact_data_url: '${basePath}/owner/id-name-people-json.action?id=',
            contractInputName: 'ownerContacterId',
            onSelect: function () {
            }
        }, options || {});
        proj_app.organization_info_selector(inputId, settings, init_info);

    },

    //监理单位选择器
    supervisor_selector: function (inputId, options, init_info) {
        var settings = $.extend({
            url: '${basePath}/supervisor-org/id-name-json.action',
            required: true,
            width: '200',
            contact_data_url: '${basePath}/supervisor-org/id-name-people-json.action?id=',
            contractInputName: 'supervisorPersonId',
            onSelect: function () {
            }
        }, options || {});
        proj_app.organization_info_selector(inputId, settings, init_info);

    },

    //我公司做为合同方时的下拉框
    partiables_selector: function (inputId, options, init_info) {
        var settings = $.extend({
            url: '${basePath}/organizational-struct/partiables.action',
            required: true,
            width: '200',
            contact_data_url: '${basePath}/person/id-name-json.action?orgId=',
            contractInputName: 'partBContractId',
            onSelect: function () {
            }
        }, options || {});
        proj_app.organization_info_selector(inputId, settings, init_info);
    },

    //合同单位
    cooperator_selector : function(inputId, options, init_info){
        var settings = $.extend({
            url: '${basePath}/cooperation-org/list-name-id-json-enabled.action',
            required: true,
            width: '200',
            contact_data_url: '${basePath}/cooperation-org/list-people-json.action?id=',
            contractInputName: 'partBContactId',
            onSelect: function () {
            }
        }, options || {});
        proj_app.organization_info_selector(inputId, settings, init_info);
    },

    /**
     * 机构及其联系人的级联选择器
     * @param inputId  机构input元素的ID
     * @param options
     * @param init_info 初始化机构信息
     */
    organization_info_selector: function (inputId, options, init_info) {
        var settings = $.extend({
            url: '',
            required: true,
            width: '200',
            contact_data_url: '',  //类似于：owner/id-name-people-json.action?id=
            contractInputName: 'partAContractId',  //TODO 英文拼写应该为  contactInputName
            onSelect: function () {
            }
        }, options || {});
        var org = returnJqObject(inputId);

        var contact_input_id = '';

        if (!org.attr('contact_input_id')) {
            contact_input_id = 'all_owner_selector_contact_' + proj_app.integer_random();
            org.attr('contact_input_id', contact_input_id);
            var contacter = $('<input/>').attr('name', settings.contractInputName).attr('id', contact_input_id);
            org.after(contacter);
        } else {
            contact_input_id = org.attr('contact_input_id');
        }

        var contacter = $('#' + contact_input_id);


        var data = getJSON(settings.url);


        org.combobox({
            requied: settings.requied,
            valueField: 'id',
            textField: 'name',
            data: data.results ? data.results : data.rows,
            onChange: orgChange
        });
        if (init_info && init_info.organization) {
            org.combobox('setValue', init_info.organization.id);
        }

        function orgChange(newValue, oldValue) {
            var url = settings.contact_data_url + newValue;
            var data = getJSON(url);

            if (!data || !data.results) {
                return;
            }

            contacter.combobox({
                valueField: 'id',
                textField: 'name',
                data: data.results
            });

            if (data.results[0]) {
                contacter.combobox('select', data.results[0].id);
            }


            if (init_info && init_info.person) {
                $.each(data.results, function (i, v) {
                    if (v.id == init_info.person.id) {
                        contacter.combobox('select', init_info.person.id);
                    }
                });
            }
        }
    },

    //所有发包方
    all_owners_selector: function (inputId, options, init_owner) {
        var settings = $.extend({
            url: '${basePath}/owner/list-name-json.action',
            required: true,
            width: '200',
            contact_data_url: '${basePath}/owner/id-name-people-json.action?id=',
            contractInputName: 'partAContractId'  //TODO 英文拼写应该为  contactInputName

        }, options || {});
        proj_app.organization_info_selector(inputId, settings, init_owner);

    },



    //区域选择器
    // url el. area-json.action?parentId=
    area_selector: function (inputId, options) {
        var settings = $.extend({
            url: '${basePath}/area-json.action?parentId=',
            required: true,
            width: '200',
            area: {}
        }, options || {});
        var areaJsonUrl = settings.url;

        var area_tree = returnJqObject(inputId);

        var data = getJSON(areaJsonUrl + "-1");

        area_tree.combotree({
            width: settings.width,
            required: settings.required,
            data: areaData4TreeData(data.results),
            onSelect: settings.onSelect,
            onBeforeExpand: treeExpendHandler
        });


        //单击树，展开树的事件处理
        function treeExpendHandler(node) {
            $.getJSON(areaJsonUrl + node.id, function (childData) {
                if (!childData || !childData.results) {
                    return;
                }
                area_tree.combotree("tree").tree('append', {
                    parent: node.target,
                    data: areaData4TreeData(childData.results)
                });
            });
        }

        //将服务器端传过来的area数据转成树的格式的
        function areaData4TreeData(areas) {
            if (!areas) {
                return;
            }
            var results = [];
            $.each(areas, function (i, v) {
                var item = {id: '', text: '', state: 'closed', children: []};
                item.id = v.id;
                item.text = v.name;
                item.attributes = v;
                results.push(item);
            });

            return results;
        }// areaData4TreeData
        return area_tree; //返回区域树

    } //	/area_selector

    ,
    //接入点类型的选择器。
    aptype_selector: function (inputId) {
        $(inputId).combobox({
            valueField: 'serialNumber',
            textField: 'text'
        });

        var url = '${basePath}/subproject/aptypes-json.action';
        var data = getJSON(url);
        $(inputId).combobox({data: data.results});

    },
    /**
     * datagrid组件中格式化organization的显示
     * @param value
     * @param row
     * @param index
     * @return {*}
     */
    grid_formatter_organization: function (value, row, index) {
        return !row ? "" : row.name;
    },
    //专业选择器
    speciaties_selector: function (inputId, options) {
        var settings = $.extend({
            url: '${basePath}/specialty/list-enabled-json.action'
        }, options || {});

        var selector = returnJqObject(inputId);
        var data = getJSON(settings.url);
        selector.combobox({
            valueField: 'id',
            textField: 'name',
            requied: true,
            data: data.results,
            multiple: true
        });
        return selector;
    },

    //金钱输入框的格式化
    amount_input: function (inputId, options) {
        var settings = $.extend({
            min: 0,
            suffix: '元',
            groupSeparator: ',',
            precision: 2
        }, options || {});

        var input = returnJqObject(inputId);

        input.numberbox(settings);
    }
    // /amount_input
    ,
    //开始日期到结束日期，开始日期不能大于结束日期
    start_finished_date_box: function (options) {
        var settings = $.extend({
            startbox: {},
            startbox_requied: true,
            finishedbox: {},
            finishedbox_requied: true
        }, options || {});


        var startbox = settings.startbox;
        var startbox_id = startbox.attr('id');
        if (!startbox_id) {
            startbox_id = Math.floor(Math.random() * 10000);
            startbox.attr('id', startbox_id);
        }
        var finishedbox = settings.finishedbox;

        if (startbox) {
            startbox.datebox({
                width: 90,
                required: settings.startbox_requied,
                onSelect: function (date) {
                    finishedbox.datebox('showPanel');
                }
            });
            finishedbox.datebox({
                width: 90,
                required: settings.finishedbox_requied,
                validType: "dateLtOther['#" + startbox_id + "']",
                onSelect: function (finishDate) {

                }
            });
        }

    },
    //毛利率
    gross_margin: function (grossMargin, grossMarginProcess) {
        grossMarginProcess.slider({
            mode: 'h',
            tipFormatter: function (value) {
                return value + '%';
            },
            onChange: function (newValue, oldValue) {
                grossMargin.numberspinner('setValue', grossMarginProcess.val());
            }
        });
        grossMargin.numberspinner({
            min: 0,
            max: 100,
            precision: 2,
            fix: true,
            suffix: ' %',
            editable: true,
            onChange: function (newValue, oldValue) {
                grossMarginProcess.slider('setValue', newValue);
            }
        });
    },
    //得到随机整数
    integer_random: function (number) {
        number = number || 100000;
        return Math.floor(Math.random() * number);
    },
    //设计单位选择器
    designorg_selector: function (inputId, options) {

        var settings = $.extend({
            url: '${basePath}/design-org/id-name-json.action',
            required: true,
            width: '300',
            contractInputName: 'designPersonId',
            onSelect: function () {
            }
        }, options || {});
        var org = returnJqObject(inputId);

        var contacter = $('<input/>').attr('name', settings.contractInputName);
        org.after(contacter);

        var data = getJSON(settings.url);

        org.combobox({
            requied: settings.requied,
            valueField: 'id',
            textField: 'name',
            data: data.results,
            onChange: orgChange
        });//owner_selector

        function orgChange(newValue, oldValue) {
            var url = "${basePath}/design-org/id-name-people-json.action?id=" + newValue;
            var data = getJSON(url);
            if (!data || !data.results) {
                return;
            }

            contacter.combobox({
                valueField: 'id',
                textField: 'name',
                data: data.results
            });
            if (data.results[0]) {
                contacter.combobox('select', data.results[0].id);
            }
        }
    },
    //将ajax返回的json数据转成combotree能用的
    convert_org_json_to_combotree_data: function (orgs) {
        if (!orgs) {
            return;
        }
        var results = [];
        $.each(orgs, function (i, v) {
            var item = {id: '', text: '', state: 'closed', children: []};
            item.id = v.id;
            item.text = v.name;
            item.attributes = v;
            results.push(item);
        });
        return results;
    }
};


/*
 消息通知器
 */
var imessager = {
    alert: function (options) {
        if ('string' == typeof options) {

            $.messager.alert("提示", options);
            return;
        }

        var settings = $.extend({
            title: "提示",
            content: ''
        }, options || {});
        $.messager.alert(settings.title, settings.content);
    },
    success_tip: function () {
        this.tip('操作成功');
    },
    tip: function (content) {
        $.messager.show({
            title: '提示',
            msg: content,
            timeout: 1500,
            showType: 'slide'
        });
    },
    confirm: function (question, callback, title) {
        $.messager.confirm(title || "请确认", question, callback);
    },
    fail_tip: function () {
        this.alert('操作失败');
    },
    /**
     *用于处理简单的ajax请求返回的结果
     * @param data
     */
    default_ajax_result_tip: function (data) {
        if (!data || (data && (data.errorInfo || data.result == false))) {

            imessager.alert('后台出错，请联系管理员');
            return;
        }

        if (data.result == false) {
            imessager.alert('后台出错，请联系管理员');
            return;
        }

        if (data.errorInfo) {
            imessager.alert(data.errorInfo);
            return;
        }

        imessager.success_tip();

    },
    /**
     * 回调处理
     * @param result
     * @param callback
     **/
    ajaxInfo: function (result, callback) {
        this.default_ajax_result_tip(result);
        if (!result.errorInfo) {
            (callback && typeof(callback) === "function") && callback();
        }
    },
    tipCall: function (content, callback) {
        (callback && typeof(callback) === "function") && callback();
    }

};


/**
 *数字格式转换工具函数
 * @param number
 * @param decimals小数
 * @param dec_point 小数点分隔符
 * @param thousands_sep 千位分隔符
 * @return {String}
 */
function number_format(number, decimals, dec_point, thousands_sep) {
    number = (number + '').replace(/[^0-9+\-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
            prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
            sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
            dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
            s = '',
            toFixedFix = function (n, prec) {
                var k = Math.pow(10, prec);
                return '' + Math.round(n * k) / k;
            };
    // Fix for IE parseFloat(0.55).toFixed(0) = 0;
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}


/**
 * 将string转成json对象
 * @param string
 * @return {*}
 */
function evalJSON(string) {
    try {
        return (window.JSON && JSON.parse) ? JSON.parse(string) : eval('(' + string + ')');
    } catch (e) {
        return {};
    }
}
;

/**
 * 对$.getJSON的二次封装
 * @param url
 * @param callback
 */
function getJSON(url, callback) {
    var result = {};
    $.getJSON(url, function (data) {
        if (typeof(data) == "string") {
            data = evalJSON(data);
        }
        if (callback) {
            callback(data);
        }
        result = data;
    });
    return result;
}


/**
 * 依赖ztree3.4，初始化组织机构树
 * @param treeContainerId 存放组织树的容器
 * @param initInternalId 初始化要打开的机构Id
 * @param getDataUrl 拿到机构树数据的URL
 * @param zTreeOnClick click的回调函数
 * @param zTreeOnExpandNode 展开树的回调函数
 * @returns {树对象}
 */
function initInstitutionalFrameworkTreeMini(treeContainerId, getDataUrl, zTreeOnClick, options) {
    var settings = $.extend({
        callback: {
            onClick: zTreeOnClick
        }
    }, options || {});
    var treeObj = {};

    var url = getDataUrl;
    var data = getJSON(url);

    if (data) {
        data = data.result;
    }
    if (data) {
        var treeId = "treeDemo" + Math.floor(Math.random() * 1000 + 1);
        var tree = $('<ul></ul>').attr('id', treeId).addClass('ztree');
        $(treeContainerId).html(tree);
        var treeData = evalJSON(data);
        $.each(treeData, function (i, v) {
            v.isParent = true;
        });

        treeObj = $.fn.zTree.init($(tree), settings, treeData);
    }
    return treeObj;
}
;




var datagrid_utils = {

    /**
     * 得到选择行
     */
    selected_row: function (grid) {
        var datagrid = returnJqObject(grid);
        grid = datagrid;
        if (!datagrid) {
            return null;
        }
        var row = datagrid.datagrid('getSelected');
        return row;
    },

    /**
     *返回选择行的Index
     */
    selected_row_index: function (grid) {
        var row = datagrid_utils.selected_row(grid);
        if (!row) {
            return null;
        }
        return grid.getRowIndex(row);
    },


    /**
     *拿到grid的选择行的ID
     *
     * @param datagrid
     * @return {*}
     */
    selected_row_id: function (grid) {
        return datagrid_utils.selected_row_attr(grid, 'id');
    },

    /**
     *拿到grid的选择行的属性
     *
     * @param datagrid
     * @return {*}
     */
    selected_row_attr: function (grid, attr_name) {

        var datagrid = returnJqObject(grid);

        if (!datagrid) {
            return null;
        }
        var row = datagrid.datagrid('getSelected');

        if (row) {
            return row[attr_name];
        }
        return null;
    },

    /**
     * 删除datagrid选定的行
     * @param datagrid
     */
    delete_grid_selected_row: function (datagrid) {
        if (!datagrid) {
            return;
        }
        datagrid = returnJqObject(datagrid);
        var row = datagrid.datagrid('getSelected');
        var index = datagrid.datagrid('getRowIndex', row);
        datagrid.datagrid('deleteRow', index);
    },

    delete_row: function (datagrid, index) {
        if (!datagrid) {
            return;
        }
        datagrid = returnJqObject(datagrid);
        datagrid.datagrid('deleteRow', index);
    },
    fix_detail_row_height: function (datagrid, index) {
        if (!datagrid) {
            return;
        }
        datagrid = returnJqObject(datagrid);
        datagrid.datagrid('fixDetailRowHeight', index);
    }


};


/**
 * 上传附件的通用函数
 * @param inputId
 * @param uploadAction
 * @param options
 * @return {*}
 */
function uploadfile(inputId, uploadAction, options) {
    if (uploadAction) {
        uploadAction += ';jsessionid=${sessionId}';
    }
    var settings = $.extend({
        'formData': {
            'id': ''
        },
        //处理上传的路径，这里使用Struts2是XXX.action
        'uploader': uploadAction || "",
        //提供的swf文件所在路径
        'swf': '${basePath}/scripts/uploadify/uploadify.swf',
        //上传条显示层ID
        'queueID': '',
        'fileSizeLimit': '50MB',
        //上传按钮内容
        'buttonText': '选择文件',
        //上传按钮图片路径，如果不需要则设置为null，就说不设置为空会有bug
        'buttonImage': null,
        //长传之后不取消queueID的内容
        'removeCompleted': false,
        //点击按钮后自动上传
        'auto': false,
        //是否支持多文件上传
        'multi': true,
        //在action中的File类型变量
        'fileObjName': 'uploads',
        //每次最大上传文件数
        'simUploadLimit': 1,
        //有speed和percentage两种，一个显示速度，一个显示完成百分比
        'displayData': 'percentage',
        //上传成功的回调函数
        'onUploadSuccess': function (file, data, response) {

        },
        'onCancel': function (file) {

        },
        'onUploadStart': function (file) {

        },
        'onFallback': function () {
            imessager.alert("您的浏览器不支持Flash，无法使用附件上传功能，请安装Flash。");
        }, 'onUploadError': function (file, errorCode, errorMsg, errorString) {
            alert('文件 ' + file.name + ' 无法上传: ' + errorString);
        }
    }, options || {});
    var input = returnJqObject(inputId);

    //添加一个用于显示文件队列的div
    if (!settings.queueID) {
        var queueId = "upload_queusId_" + Math.floor(Math.random() * 10000);
        var queue = $('<div></div>').attr('id', queueId);
        input.before(queue);
    }
    return input.uploadify(settings);
}

/**
 *返回一个jq对象
 * @param inputId jq的对象，或者
 * @return {{}}
 */
function returnJqObject(inputId) {
    var input = {};
    if ('string' == typeof inputId) {
        input = $(inputId);
        return input;
    }

    if ('object' == typeof inputId) {
        return inputId;
    }
    return input;
}


/**
 * areaFormatter
 */
function areaFormatter(value, row, index) {
    return value ? value.name : "";
}


/**
 *   金额的过滤器
 */
function amountNumberFormatter(value) {
    return number_format(value, 2, '.', ',');
}

/**
 * 将元转成万元的过滤器
 */
function convertYuanToThusandYuanFormatter(value) {
    value = value / 10000;
    return amountNumberFormatter(value);
}

//前端显示字典的内容
function dictionary_show_formatter(value) {
    if (!value) {
        return '';
    }
    return value.text;
}

//显示机构的名称
function grid_show_name_formatter(value, row, index) {
    if (!value) {
        return '';
    }
    return value.name;
}


//显示机构信息
function grid_show_orginfo_formatter(value, row, index) {
    var result = '';
    if (value && value.organization) {
        result += value.organization.name;
        if (value.person) {
            result += ' - ';
            result += value.person.name;
        }
    }

    return result;
}


//项目工期的进度条
function remaining_duration_formatter(value, row, index) {
    return '<div  style="width:70px" > ' + create_grid_processer(value) + '</div>';
}
/**
 * 返回一个进度条
 * @param value
 * @return {*|jQuery}
 */
function create_grid_processer(value, options) {
    var settings = $.extend({
        tip: ''
    }, options || {});

    var color = value > 70 ? '#cc0000' : '#7fb80e';
    return '<div style="width:100%;border:1px solid #ccc;">' +
            '<div style="width:' + value + '%;background:' + color + ';color:#000">' + value + '%' + settings.tip + '</div>'
            + '</div>'

}

function  show_outer_person_info_dialog(id){
    var data = getJSON('${basePath}/outer-person/get.action?id=' + id);
    if (!data.result) {
        return;
    }
    var person = data.result;
    var div = $('<div></div>').css('padding', '30px 0 0 30px');
    var gender = ((person.gender == 'MALE' || person.gender == '男') ? '男' : '女');
    div.append(' 姓名：' + person.name + '<p/>');
    div.append('性别：' + gender + '<p/>');
    div.append('电话：' + person.tel + '<p/>');
    div.append('手机：' + person.mobile + '<p/>');
    div.append('邮箱：' + person.email + '<p/>');
    div.append('QQ：' + person.qq + '<p/>');

    div.dialog({
        title: person.name + '资料',
        width: 300,
        height: 300,
        closed: false,
        cache: false,
        modal: true
    });
}


/**
 * 联系人的formatter
 * @param value
 * @param row
 * @param index
 * @return {string}
 */
function grid_contact_formatter(value, row, index) {
    if (!value) {
        return '未设置联系人';
    }
    var contact = value.mobile ? value.mobile : value.tel;
    return  value.name + "<br/>" + contact;
}



/**
 *   日期1 大于 日期2
 */
$.extend($.fn.validatebox.defaults.rules, {
    dateLtOther: {
        validator: function (value, param) {
            var date1 = new Date(value.replace(/\-/g, "\/"));
            var date2 = new Date($(param[0]).datebox('getValue').replace(/\-/g, "\/"));
            return (date1 - date2) > 0;
        },
        message: "结束日期小于开始日期"
    }
});


/**
 * 将form表单的内容转成json
 * @returns {{}}
 */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 关闭当前Tab页
 */
function layout_center_close_current_tab(){
    var layout_center_tabs = $('#layout_center_tabs').size() > 0 ? $('#layout_center_tabs') : parent.$('#layout_center_tabs');
    var index = layout_center_tabs.tabs('getTabIndex', layout_center_tabs.tabs('getSelected'));
    var tab = layout_center_tabs.tabs('getTab', index);
    if (tab.panel('options').closable) {
        layout_center_tabs.tabs('close', index);
    } else {
        $.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭', 'error');
    }
}

/**
 * 刷新某Tab页
 */
function layout_center_refreshTab(title) {
    var layout_center_tabs  = $('#layout_center_tabs').size() > 0 ? $('#layout_center_tabs') : parent.$('#layout_center_tabs');
    layout_center_tabs.tabs('getTab', title).panel('refresh');
}


</script>