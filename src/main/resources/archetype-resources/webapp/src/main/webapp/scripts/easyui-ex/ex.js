$.extend($.fn.combo.methods, {
    /**
     * 禁用combo文本域
     * @param {Object} jq
     * @param {Object} param stopArrowFocus:是否阻止点击下拉按钮时foucs文本域
     * stoptype:禁用类型，包含disable和readOnly两种方式
     */
    disableTextbox: function (jq, param) {
        return jq.each(function () {
            param = param || {};
            var textbox = $(this).combo("textbox");
            var that = this;
            var panel = $(this).combo("panel");
            var data = $(this).data('combo');
            if (param.stopArrowFocus) {
                data.stopArrowFocus = param.stopArrowFocus;
                var arrowbox = $.data(this, 'combo').combo.find('span.combo-arrow');
                arrowbox.unbind('click.combo').bind('click.combo', function () {
                    if (panel.is(":visible")) {
                        $(that).combo('hidePanel');
                    } else {
                        $("div.combo-panel").panel("close");
                        $(that).combo('showPanel');
                    }
                });
                textbox.unbind('mousedown.mycombo').bind('mousedown.mycombo', function (e) {
                    e.preventDefault();
                });
            }
            textbox.prop(param.stoptype ? param.stoptype : 'disabled', true);
            data.stoptype = param.stoptype ? param.stoptype : 'disabled';
        });
    },
    /**
     * 还原文本域
     * @param {Object} jq
     */
    enableTextbox: function (jq) {
        return jq.each(function () {
            var textbox = $(this).combo("textbox");
            var data = $(this).data('combo');
            if (data.stopArrowFocus) {
                var that = this;
                var panel = $(this).combo("panel");
                var arrowbox = $.data(this, 'combo').combo.find('span.combo-arrow');
                arrowbox.unbind('click.combo').bind('click.combo', function () {
                    if (panel.is(":visible")) {
                        $(that).combo('hidePanel');
                    } else {
                        $("div.combo-panel").panel("close");
                        $(that).combo('showPanel');
                    }
                    textbox.focus();
                });
                textbox.unbind('mousedown.mycombo');
                data.stopArrowFocus = null;
            }
            textbox.prop(data.stoptype, false);
            data.stoptype = null;
        });
    }
});

/**
 *  datagrid的扩展
 */
$.extend($.fn.datagrid.defaults, {
    onLoadSuccess: function (data) {
        if (!data || !data.rows || data.rows.length == 0) {
            var body = $(this).data().datagrid.dc.body2;
            var columns =  $(this).data().datagrid.options.columns;
            var columns_length = columns.length;
            var colspan = columns[ columns_length > 0 ? columns_length - 1 : 0].length;
            body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;" colspan="'+ colspan +'">没有数据</td></tr>');
        }
    }
});


$.extend($.fn.numberspinner.defaults, {
    precision : 2,
    increment : 100,
    min:0
});


$.extend($.fn.numberbox.defaults, {
    precision : 2
});


$.fn.datebox.defaults.formatter = function(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+m+'-'+d;
}

