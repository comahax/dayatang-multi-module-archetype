/**
 * 透视表工具,基于原始数据出透视表
 * 
 * @author wenxiang.Zhou
 */
; !
function($) {

    $.fn.defaults = {
        SPLIT_KEY: ':::',
        CAL_TYPES: ['sum', 'avg'],
        filters: [],
        // 列标签
        columnKeys: [],
        // 行标签
        rowKeys: [],
        // 目标table
        targetTable: null,
        cal: {
            // 计算的数值列
            name: '',
            // 计算方式
            type: 'sum'
        },
        //页面内容target
        target:{
        	
        }
    }

    /**
     * 生成透视表
     */
    $.fn.createTable = function(url, options, pivot) {
        var $this = $(this);
        options = $.extend({},$.fn.defaults, options);
        
//        var pivot = new DytChart(url,options);

        $this.empty();
        return this.each(function() {
            var table = new DytPivotTable(options, pivot.getData(), this, pivot.getColumns());
            table.init().drawTable();
        });
    }
    
} (window.jQuery);

/**
 * 画
 */
var DytPivotTable = function(options, initData, table, initColumns) {

    this.row = {};
    this.column = {};

    var columnKeysLength = options.columnKeys.length,
    rowKeysLength = options.rowKeys.length;

    /**
	 * 获取过滤后的数据
	 */
    this.getDataAfterFilter = function() {

        // 多个筛选条件,从原始数据rows中过滤数据
        if (options.filters == null || options.filters.length == 0) {
            return initData;
        }
        var arr = $.grep(initData,function(item, index) {
            var filters = options.filters,
            propertypeName = '',
            pvalues = '';
            for (var i = 0; i < filters.length; i++) {
                var everyItem = filters[i];
                propertypeName = everyItem.name;
                pvalues = everyItem.value;
            }
            //需jquery支持
            return $.inArray(item[propertypeName], pvalues) == -1 ? false: true;
        });
        return arr;
    }
    /**
	 * 初始化数据，用于设置所有方法通用的属性
	 */
    this.init = function() {
        var initFilterData = this.getDataAfterFilter(),
        getData = function(type) {
            var data = {},
            keyArr = [];

            $.each(initFilterData, function(index, item) {
                var newValue = [];

                switch (type) {
                case 'column':
                    $.each(options.columnKeys,
                    function(i, col) {
                        newValue.push(item[col]);
                    });
                    break;
                case 'row':
                    $.each(options.rowKeys,
                    function(i, row) {
                        newValue.push(item[row]);
                    });
                    break;
                }
                var newKey = newValue.join(options.SPLIT_KEY);
                if ($.isEmptyObject(data[newKey])) {
                    // 复制新数据
                    data[newKey] = new Array(item);
                    keyArr.push(newKey);
                } else {
                    data[newKey].push(item);
                }

            });
            return {
                'data': data,
                'keys': keyArr
            };
        }
        this.row = getData('row');
        this.column = getData('column');

        return this;
    }

    this.drawIntoTable = function(data) {

        if (data == null || data.length == 0) return;

        var columns = options.columnKeys,
        rows = options.rowKeys,
        // 行的组数据
        columnsLength = data[0].length,
        // 行长度
        rowsLength = data.length;

        if (data != null) {
            var eachData = columns.concat(data).concat('sum'),
            thead = $('<thead></thead>'),
            tbody = $('<tbody></tbody>'),
            // 右下角总和值
            maxResult = 0,
            // console.log(newResult);
            columnValueResult = [];
            // 循环每一行
            $.each(eachData,
            function(index, item) {

                // 判断如果不是对象，则说明是列名
                item = typeof(item) === 'string' ? new Array(columnsLength) : item;
                var tr = $('<tr></tr>'),
                tempItem = rows.concat(item).concat('sum'),
                // 每一行的计算结果,默认是0
                calResult = 0;

                // 循环每一行的每一个单元格
                $.each(tempItem,
                function(colIndex, colItem) {

                    var grid = 'th';
                    if (index >= columns.length) {
                        grid = 'td';
                    }
                    // 要加入的是TD还是TH
                    var tempGrid = '<' + grid + '>&nbsp;</' + grid + '>';

                    // 如果是最后一行,则设置结果
                    if (index == eachData.length - 1 && colIndex < tempItem.length - 1 && colIndex > rows.length - 1) {
                        tempGrid = '<' + grid + '>' + columnValueResult[colIndex] + '</' + grid + '>';
                    }
                    if (! (colItem == undefined)) {
                        // 获取第colIndex列的值合计
                        if (columnValueResult[colIndex] != null) {
                            // TODO :
                            // 检查相加的值是否为null,如果为null设置为0
                            columnValueResult[colIndex] += colItem[options.cal.name] == null ? 0 : colItem[options.cal.name];

                        } else {
                            columnValueResult[colIndex] = colItem[options.cal.name] == null ? 0 : colItem[options.cal.name];
                        }
                        // 不包括最后一行的全部TH
                        if (index < columns.length - 1) {
                            tempGrid = '<' + grid + '>&nbsp;</' + grid + '>';
                        } else if (index == columns.length - 1 && colIndex == tempItem.length - 1) {
                            // thead的最后一个TH
                            tempGrid = '<' + grid + '>合计</' + grid + '>';
                        } else {
                            // 正文数据
                            var tempCalresult = colItem[options.cal.name] == undefined ? '': colItem[options.cal.name];

                            if (colIndex >= rows.length && index < eachData.length - 1) {
                                tempGrid = '<' + grid + '>' + tempCalresult + '</' + grid + '>';
                            }
                            // 最后一格计算后的数据（从tbody开始到last-1行）
                            calResult += tempCalresult == '' ? 0 : tempCalresult;
                            if (colIndex == tempItem.length - 1 && index < eachData.length - 1) {
                                // 累计最后总和
                                maxResult += parseInt(calResult, 10);
                                tempGrid = '<' + grid + '>' + calResult + '</' + grid + '>';
                            } else if (colIndex == tempItem.length - 1 && index == eachData.length - 1) {
                                // 右下角的总和值
                                tempGrid = '<' + grid + '>' + maxResult + '</' + grid + '>';
                            }
                        }
                    }

                    tr.append($(tempGrid));
                });
                if (index < columns.length) {
                    thead.append(tr);
                } else {
                    tbody.append(tr);
                }
            });
            $(table).append(thead).append(tbody);
        }

        return this;
    }

    this.drawTableLeftRow = function() {

        var rows = options.rowKeys,
        rowspanArr = [],
        rowValueArr = [];
        $.each(this.row.keys,
        function(index, item) {
            var sp = item.split(options.SPLIT_KEY);
            // 2010-1-1
            $.each(sp,
            function(spindex, spitem) {
                if (rowspanArr[spindex] == null) {
                    rowspanArr[spindex] = [spitem];
                } else {
                    rowspanArr[spindex].push(spitem);
                }

                if (rowValueArr[spindex] == null) {
                    rowValueArr[spindex] = [spitem];
                } else {
                    rowValueArr[spindex].push(spitem);
                }

            });
        });

        $.each(rowspanArr,
        function(index, item) {
            var obj = [],
            icount = 0;
            $.each(item,
            function(i, t) {
                icount++;
                if (! (item[i + 1] == t)) {
                    obj.push(icount);
                    icount = 0;
                }
            });
            rowspanArr[index] = obj;
        });

        var tr = $(table).find('tr');

        // 设置行头的值
        $.each(tr.slice(columnKeysLength, tr.length - 1),
        function(index, item) {
            for (var i = 0; i < rows.length; i++) {
                $(item).find('td').eq(i).text(rowValueArr[i][index]).css({
                    fontWeight: 'bold',
                    color: 'green'
                });
            }
        });
        //		
        // 合并
        for (var i = rowspanArr.length - 1; i >= 0; i--) {
            var t = rowspanArr[i],
            v = 0;
            $.each(t,
            function(ii, tt) {
                v += (ii == 0 ? 0 : tt);
                tr.eq(columnKeysLength + v).find('td').eq(i).attr('rowspan', tt);
                // 剩下的全部删除
                tr.slice(columnKeysLength + v + 1, columnKeysLength + v + tt).find('td:eq(' + i + ')').hide()
            });
        }

        var columns = initColumns;
        // 设置行的文本值
        $.each(rows,
        function(index, item) {
            var ll = columnKeysLength - 1;
            tr.eq(ll).find('th').eq(index).text(columns[item]);
        });

        return this;
    }

    this.getData = function(type) {

        // 计算的列
        var calColumn = options.cal.name,
        columnData = this.column.data,
        allData = type === 'row' ? $.extend(true, {},
        this.row.data) : $.extend(true, {},
        this.column.data);

        for (newrow in allData) {
            var newArr = allData[newrow];
            // 内部筛选列
            var allColumnData = {};
            $.each(newArr,
            function(index, item) {
                var newValue = [];
                $.each(options.columnKeys,
                function(i, column) {
                    newValue.push(item[column]);
                });
                var newKey = newValue.join(options.SPLIT_KEY);
                if ($.isEmptyObject(allColumnData[newKey])) {
                    allColumnData[newKey] = item;
                } else {
                    // TODO:计算
                    allColumnData[newKey][calColumn] += item[calColumn];
                }
            });
            allData[newrow] = allColumnData;
        }
        // 3:根据行的数据筛选列字段,并组合成table所需的基本数据
        var tempNewRowData = $.extend(true, {},
        allData);

        var newResult = [];

        for (n in tempNewRowData) {
            var cols = [];
            if (type === 'row') {
                for (col in columnData) {
                    var tdObject = tempNewRowData[n][col];
                    if (tdObject == null) {
                        cols.push(0);
                    } else {
                        cols.push(tdObject);
                    }

                }
            } else {
                for (v in tempNewRowData[n]) {
                    cols.push(tempNewRowData[n][v]);
                }
            }
            newResult.push(cols);
        }
        return newResult;
    }

    /**
	 * 合并表头
	 */
    this.drawTableHeader = function() {
        var rowKeysLength = options.rowKeys.length,
        rowspanArr = [],
        rowValueArr = [];
        $.each(this.column.keys,
        function(index, item) {
            var sp = item.split(options.SPLIT_KEY);
            // 2010-1-1
            $.each(sp,
            function(spindex, spitem) {
                if (rowspanArr[spindex] == null) {
                    rowspanArr[spindex] = [spitem];
                } else {
                    rowspanArr[spindex].push(spitem);
                }

                if (rowValueArr[spindex] == null) {
                    rowValueArr[spindex] = [spitem];
                } else {
                    rowValueArr[spindex].push(spitem);
                }

            });
        });

        // 数组中相邻元素相同的次数统计，并生成数组相同的以次数为项的新数组
        $.each(rowspanArr,
        function(index, item) {
            var obj = [];
            var icount = 0;
            $.each(item,
            function(i, t) {
                icount++;
                if (! (item[i + 1] == t)) {
                    obj.push(icount);
                    icount = 0;
                }
            });
            rowspanArr[index] = obj;
        });

        var tr = $(table).find('tr');

        var theadTr = $(table).find('thead > tr');

        var endIndex = theadTr.find('th').length - 1;
        // 设置列的值
        $.each(theadTr,
        function(index, item) {
            // 从行字段的长度到第一行的长度-1
            var ths = $(item).find('th').slice(rowKeysLength, endIndex);
            $.each(ths,
            function(thindex, th) {
                $(th).text(rowValueArr[index][thindex]).css({
                    fontWeight: 'bold',
                    color: 'green'
                });
            });
        });
        // 合并列
        for (var i = 0; i < rowspanArr.length; i++) {
            var t = rowspanArr[i];
            var v = 0;
            $.each(t,
            function(ii, tt) {
                v += (ii == 0 ? 0 : tt);

                var th = tr.eq(i).find('th');
                var slice = th.slice(rowKeysLength + v + 1, rowKeysLength + v + tt);
                var coslpan = th.eq(rowKeysLength + v);
                slice.text('-').hide();
                coslpan.attr('colspan', tt);
            });
        }

        return this;
    }
    /**
	 * 合并空格处
	 */
    this.spanAllEmpty = function() {
        // 所有的tr
        var trs = $(table).find('tr');
        // 合并上面
        $.each(trs.slice(0, columnKeysLength - 1),
        function(i, dom) {
            if (i == 0) {
                $(dom).find('th').eq(0).attr('rowspan', columnKeysLength - 1).attr('colspan', rowKeysLength);
                $(dom).find('th').slice(1, rowKeysLength).hide();
                $(dom).find('th:last').attr('rowspan', columnKeysLength - 1);
            } else {
                $(dom).find('th').slice(0, rowKeysLength).hide();
                $(dom).find('th:last').hide();
            }
        });
        // 合并左下
        trs.last().find('td:eq(0)').attr('colspan', rowKeysLength).html('<strong>合计</strong>');
        trs.last().find('td').slice(1, rowKeysLength).hide();
        return this;
    }

    /**
	 * 画table
	 */
    this.drawTable = function() {
        var rowResult = this.getData('row');
        var columnResult = this.getData('column');
        if (rowResult != null ) {
            this.drawIntoTable(rowResult).drawTableLeftRow().drawTableHeader(columnResult).spanAllEmpty();
        }
    }

};