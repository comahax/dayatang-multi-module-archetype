Array.prototype.uniq = function() {
	var tempArr = [];
	var tempObj = {};
	for ( var i = 0; i < this.length; i++) {
		if (!(this[i] in tempObj) || !(this[i] === tempObj[this[i]])) {
			tempArr.push(this[i]);
			tempObj[this[i]] = this[i]; // 这个可以用保存重复的元素
		}
	}
	return tempArr;
};



/**
 * 画透视图
 * 
 * @param url
 *            请求地址
 * @param obj
 *            chart装载容器

 */
function DytChart(url, options) {
	this.url = url;
	this.data = [];
	this.columns = {};
	this.dataloaded = false;
	this.options = options;
	this.error = options.error == undefined ? {} : options.error;
	this.loadData = function() {
		var tempData = {}, tempColumns = {};
		$.ajax({
			url : this.url,
			type : 'GET',
			dataType : 'json',
			async : false,
			success : function(reponseData) {
				tempColumns = reponseData.columns;
				tempData = reponseData.data;
			},
			error : function() {
				if ($.isEmptyObject(url)) {
					// 请求地址未填写
					alert('请填写数据请求地址');
				} else {
					// 请求地址有误,或没有请求权限
				}
			}
		});
		// 赋值,可直接获取data的值
		this.columns = tempColumns;
		this.data = tempData;
		this.dataloaded = true;
	}
	/**
	 * 获取页面初始化数据,重新加载
	 * 
	 * @returns 结构化数据对象 : { city:{ name:'框架项目', data : ['框架项目一','框架项目二'] },
	 *          province:{ name:'市', data:['广州','湖南'] }, ... ... ... }
	 */
	this.getPagetData = function() {
		var returnObj = {}, tempData = this.data, columns = this.columns;
		if (tempData == null || columns == null || tempData.length <= 0) {
			this.loadError();
		}
		if (this.dataloaded) {
			// 根据this
			for ( var i in columns) {
				returnObj[i] = {};
				if (i === options.cal.name) {
					returnObj[i]['data'] = getAllPushList(tempData, i);
				} else {
					returnObj[i]['data'] = getAllPushList(tempData, i).uniq();
				}
				returnObj[i]['name'] = columns[i];
			}
		} else {
			returnObj = null;
		}
		return returnObj;
	}
	/**
	 * 加载错误
	 */
	this.loadError = function() {
		var errorTarget = this.error.target, errorText = this.error.message;
		if (!$.isEmptyObject(this.error) && !$.isEmptyObject(errorTarget)) {
			var message = $.isEmptyObject(errorText) == true ? '' : errorText;
			if ($(errorTarget).length > 0) {
				var toggleError = function() {
					$(errorTarget).slideToggle('fast', function() {
						$(this).find('span:eq(0)').text(message);
					});
				};
				toggleError();
				$('button:[data-dismiss="alert"]').bind('click', toggleError);
			}
		}
	}
	this.getData = function() {
		return this.data;
	}
	this.getColumns = function(){
		return this.columns;
	}
	/**
	 * 根据某个属性获取其值的不重复列表
	 */
	var getAllPushList = function(dataArr, column) {
		var list = [];
		$.each(dataArr, function(index, item) {
			list.push(item[column]);
		});
		return list;
	};
	// 默认加载
	this.loadData();
};

function hideOption(selects,optionValue){
	
	$(selects).each(function(index,item){
		var $this = $(this).find('select').eq(0);
		$($this).find('option[value="'+optionValue+'"]').hide();
	});
}
function showOptions(selects,optionValue){
	$(selects).each(function(index,item){
		var $this = $(this).find('select').eq(0);
		$($this).find('option[value="'+optionValue+'"]').show();
	});
}


/**
 * 获取页面加载时的panel，用于绑定行和列的字段筛选
 */
function getPageLoadPanel(pageColumns,target){
	var selects = target.columnTarget+','+target.rowTarget+','+target.calTarget;
	for(p in pageColumns){
		var option = $('<option value="'+p+'">'+pageColumns[p]+'</option>');
		$(selects).find('select:eq(0)').append(option);
	}
	
	var hasSelectOptions = {},calItem ;
	
	$(selects).change(function(){
		var $this = $(this).find('select').eq(0);
		var $selected = $this.find('option:selected').eq(0);
		$this.find('option:eq(0)').attr('selected',true);
		var selectVal = $selected.val(),selectText = $selected.text();

		if(hasSelectOptions[selectVal]){
			$('div.alert-error').slideToggle('fast', function() {
				$(this).find('span:eq(0)').text('该选项已经被选择，请勿重复选择。');
			}).delay(2000).slideUp('fast',null);
			return;
		}else{
			if($this.parent('div').attr('id') === $(target.calTarget).attr('id')){
				var item = $this.prev('a');
				//其他已经选择了
				if(item.length > 0){
					//如果当前已经选择，则替换元素内容即可
//					console.log(selectVal);
					var pvalue = item.attr('value');
//					$this.find('option[value="'+pvalue+'"]').show();
					showOptions(selects,pvalue);
					$(item).attr('value',selectVal).html(selectText+'<i class="icon-remove icon-white"></i>');
					hasSelectOptions[pvalue] = null;
					hasSelectOptions[selectVal] = selectText;
					hideOption(selects,selectVal);
//					$this.find('option[value="'+selectVal+'"]').hide();
					return;
				}
				
				//如果没有选择，并且选择的只有一个
			}
			var span = $('<a class="btn btn-success" href="javascript:;" style="margin:5px;" value="'+selectVal+'">'+selectText+'<i class="icon-remove icon-white"></i></a>');
			span.click(function(){
				//删除
				var pvalue = $(this).attr('value');
				showOptions(selects,pvalue);
//				$this.find('option[value="'+pvalue+'"]').show();
				$(this).remove();
				hasSelectOptions[pvalue] = null;
			});
			hasSelectOptions[selectVal] = selectText;
			hideOption(selects,selectVal);
//			$selected.hide();
			//查找其他select的hide选项，看是否已经选中。
			$this.before(span);
		}
	
	});
}

//封装attrs
;

(function($){
	// jQuery plugin wrapper
    $.fn.attrs = function() {
        return attrs(this[0]);
    };
    
    $.fn.attrValues = function(prop){
    	return attrValues(this,prop);
    }
    
    function attrValues(e,prop){
    	var columnVals = [];
 		$.each(e,function(index,item){
 			var tempVal = $(item).attr('value');
 			columnVals.push(tempVal);
 		});
// 		console.log(columnVals);
 		return columnVals;
    }
 
    // return a map of the element attributes
    function attrs(e) {
        var a = e.attributes, // attributes object shorcut
            l = a.length, // object length
            map = {}, // map object
            i = 0, // for the loop
            v; // hold current node value in the loop
 
        // loop through attributes and build our map
        for (;i < l; i++) {
            v = a[i].nodeValue;
            if (v) map[a[i].nodeName] = v;
        };
 
        // for contentEditable we check that it is really set due to IE
        if (map.contentEditable) {
            var div = document.createElement('<div />').appendChild(e);
            if (!(div.innerHTML.toLowerCase().indexOf('contenteditable') !== -1)) {
                delete map.contentEditable;
            };
        };
 
        // return the map object
        return map;
    };
})(jQuery);


