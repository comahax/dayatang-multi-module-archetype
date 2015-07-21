/**
 * 透视图工具
 * @author wenxiang.Zhou
 * @
 */
;

(function($) {
	
	/**
	 * 扩展数组去重复
	 * @returns {Array}
	 */
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
	
	var validate = function(options){
		var validateFlag = false;
		// 如果计算方式不包含在默认计算方式中
		if (!($.inArray(options.cal.type, [ 'sum', 'avg' ]) >= 0)){
			return validateFlag;
		}

		switch (options.chartType) {
		case 'pie':
			if(!$.isEmptyObject(options.legend)){
				validateFlag = true;
			}
			break;
		case 'line':
			//线性图
			break;
		case 'area':
			break;
		// 可扩展其他图形
		default:
			if(!$.isEmptyObject(options.cal) && !$.isEmptyObject(options.legend) && !$.isEmptyObject(options.x)){
				validateFlag = true;
			}
			break;
		}
		return validateFlag;
	};
	
	$.fn.extend({
		
		/**
		 * 画图
		 * @param options
		 * @returns {Boolean}
		 */
		drawChart : function(options) {
			//验证必要参数
			if (!validate(options)) {
				return false;
			}
			// 根据筛选条件筛选数据
			this.options = $.extend(this.options, options);
			switch (options.chartType) {
			case 'pie':
				this.drawPieChart();
				break;
			case 'line':
				//线性图
				break;
			case 'area':
				break;
			default:
				this.drawColumnChart();
				break;
			}
		},
		/**
		 * 画表
		 * @param options
		 * @returns {Boolean}
		 */
		drawColumnChart : function(){
			var chartData= this.getColumnData();
			var chartOptions = {
				xAxis : {
					categories : chartData.categories,
					labels : {
						 rotation: 30,
			             align: 'left'
					}
				},
				series : chartData.series
			};
			chartOptions = $.extend(chartOptions, this.options.chartDefaults);
			$(this).ptChart(chartOptions, 'column');
		},
		
		//TODO:优化
		drawPieChart : function(){
			var chartData= this.getPieData();
			
			var allValue = 0;
			$.each(chartData.series,function(index,v){
				var tempV = 0;
				for(var i = 0 ; i <  v.data.length ; i++){
					tempV += v.data[i];
				}
				allValue += tempV;
			});
			
			var pieSeries = [];
			$.each(chartData.series,function(index,v){
				var tempV = 0;
				for(var i = 0 ; i <  v.data.length ; i++){
					tempV += v.data[i];
				}
				//计算
				var item = [];
				item[0] = v.name;
				item[1] = Math.round((tempV/allValue*10000))/100;
				pieSeries.push(item);
			});
			var chartOptions = {
					series : [{
						type:'pie',
						name:'所占比例',
						data:pieSeries
					}]
				};
			chartOptions = $.extend(chartOptions,this.options.chartDefaults);
			$(this).ptChart(chartOptions,'pie');
		},
		// 根据筛选条件筛选过后的值
		getFilterData : function() {
			// 多个筛选条件,从原始数据rows中过滤数据
			if($.fn.options.filters == null || $.fn.options.filters.length == 0){
				this.currentRows = this.rows;
				return this.rows;
			}
			//修复
			this.currentRows = this.rows;
			var arr = $.grep(this.rows, function(item, index) {
				var filters = $.fn.options.filters;
				var propertypeName = '', pvalues = '';
				for ( var i = 0; i < filters.length; i++) {
					var everyItem = filters[i];
					propertypeName = everyItem.name;
					pvalues = everyItem.value;
				}
				return $.inArray(item[propertypeName], pvalues) == -1 ? false
						: true;
			});
			this.currentRows = arr;
//			print("筛选条件过滤后数据条数:" + this.currentRows.length);
			return arr;
		},
		getColumnData : function(){
			return this.getChartData();
		},
		getPieData : function(){
			//获取饼图所需的数据
			return this.getChartData();
		},
		/**
		 * 线性图和柱状图所需要的数据
		 * 
		 * @returns {___anonymous1758_1759}
		 */
		getChartData : function() {
			this.getFilterData();
			var chartData = {};
			/**
			 * 过滤轴,不重复
			 */
			var legends = new Array();
			var currentRows = this.currentRows;
			if (currentRows && currentRows.length > 0) {
				$.each(currentRows, function(index, item) {
					legends.push(item[$.fn.options.legend]);
				});
			}
			legends = legends.uniq();
			/**
			 * 通过轴获取每个轴下的所有数据
			 */
			var legendValueGroups = {};

			for ( var i = 0; i < legends.length; i++) {
				var legend = legends[i];
				var legendValueArr = new Array();
				$.each(currentRows, function(ind, item) {
					if (item[$.fn.options.legend] == legend) {
						legendValueArr.push(item);
					}
				});
				legendValueGroups[legend] = legendValueArr;
			}
			/**
			 * 组装series所需的数组
			 */
			var seriesList = [];

			var categories = [];

			var xs = {};
			
			$.each(currentRows, function(index, item) {
				xs[item[$.fn.options.x]] = item[$.fn.options.x];
			});
			
			var les = {};
			var flag = true;
			for (legendName in legendValueGroups) {
				var seriesItem = {};
				seriesItem.name = legendName;
				var callArr = {};
				$.each(legendValueGroups[legendName], function(index, item) {
					xs[item[$.fn.options.x]] = item[$.fn.options.x];
					var xVal = callArr[item[$.fn.options.x]];
					if (xVal) {
						callArr[item[$.fn.options.x]].push(item);
					} else {
						callArr[item[$.fn.options.x]] = [ item ];
					}
				});
				
				// 计算
				var legendData = [];
				for (calData in xs) {
					if (flag) {
						categories.push(calData);
					}
					if(callArr[calData]){
						var numberArr = $.fn.getNumberArrFormObjectArr(
								callArr[calData], $.fn.options.cal.name);
						var resultNumber = $.fn.cal(numberArr);
						legendData.push(resultNumber);
					}else{
						legendData.push(0);
					}
				}
				// 开关
				flag = false;
				seriesItem.data = legendData;
				seriesList.push(seriesItem);
			}

			chartData.categories = categories;
			chartData.series = seriesList;
			return chartData;
		},
		//扩展draw其他
		/**
		 * 所有的计算方法
		 * 
		 * @param dataArr
		 *            数字数组
		 * @returns 计算结果
		 */
		cal : function(dataArr) {
			if (!this.options.cal.type)
				return;
			var result = 0;
			switch (this.options.cal.type) {
			case 'avg':
				result = eval(dataArr.join('+')) / dataArr.length;
				break;
			default:
				// 求和
				result = eval(dataArr.join('+'));
				break;
			}
			return result;
		},
		/**
		 * 
		 * @param objectArr
		 *            目标数组
		 * @param p
		 *            对象数组元素的某个属性名,其值是数字,用于计算
		 * @returns 数字组成的数组
		 */
		getNumberArrFormObjectArr : function(objectArr, p) {
			var objarr = new Array();
			if (!$.isArray(objectArr) || objectArr.length < 1)
				return null;
			// 计算
			$.each(objectArr, function(index, item) {
				objarr.push(item[p]);
			});
			return objarr;
		},
		/**
		 * 默认参数
		 */
		options : {
			url : null
		},
		/**
		 * 
		 */
		rows : null,
		//筛选的当前行!
		currentRows : null
	});
	
	/**
	 * 一次性初始化数据
	 */
	$.initData = function(rawData) {
		$.fn.rows = rawData.data;
		$.fn.columnNames= rawData.columns;
//		print($.fn.rows);
	};

	function print(objs){
		window.console.log(objs);
	};

})(jQuery);

/**
 * 画透视图
 * 
 * @param url
 *            请求地址
 * @param obj
 *            chart装载容器
 */
function DytChart(url,options) {
	this.url = url;
	this.data = [];
	this.columns = {};
	this.dataloaded = false;
	this.options = options;
	this.error = options.error == undefined ? {} : options.error;
	this.loadData = function() {
		var tempData = {},tempColumns = {};
		$.ajax({
			url : this.url,
			type : 'GET',
			dataType : 'json',
			async : false,
			success : function(reponseData) {
				// 页面获得数据后直接初始化必要数据
				$.initData(reponseData);
				tempColumns = reponseData.columns;
				tempData = reponseData.data;
			},
			error : function() {
				if ($.isEmptyObject(url)) {
					//请求地址未填写
					alert('请填写数据请求地址');
				} else {
					//请求地址有误,或没有请求权限
				}
			}
		});
		//赋值,可直接获取data的值
		this.columns = tempColumns;
		this.data = tempData;
		this.dataloaded = true;
	};
	/**
	 * 获取页面初始化数据,重新加载
	 * @returns 结构化数据对象 :
	 * {
		city:{
			name:'框架项目',
			data : ['框架项目一','框架项目二']
		},
		province:{
			name:'市',
			data:['广州','湖南']
		},
		...
		...
		...
	}
	 */
	this.getInitData = function() {
		var returnObj = {},tempData = this.data,columns = this.columns;
		if(tempData == null || columns == null || tempData.length <= 0){
			this.loadError();
		}
		if (this.dataloaded) {
			//根据this
			for(var i in columns){
				returnObj[i] = {};
				if( i === options.cal.name){
					returnObj[i]['data'] = getAllPushList(tempData,i);
				}else{
					returnObj[i]['data'] = getAllPushList(tempData,i).uniq();
				}
				returnObj[i]['name'] = columns[i];
			}
		}else{
			returnObj = null;
		}
		return returnObj;
	};
	/**
	 * 加载错误
	 */
	this.loadError = function(){
		var errorTarget = this.error.target,errorText = this.error.message;
		if(!$.isEmptyObject(this.error) && !$.isEmptyObject(errorTarget)){
			var message = $.isEmptyObject(errorText) == true ? '' : errorText;
			if($(errorTarget).length > 0){
				var toggleError = function(){
					$(errorTarget).slideToggle('fast',function(){
						$(this).find('span:eq(0)').text(message);
					});
				}; 
				toggleError();
				$('button:[data-dismiss="alert"]').bind('click',toggleError);
			}
		}
	};
	this.getData = function(){
		return this.data;
	};
	/**
	 * 根据某个属性获取其值的不重复列表
	 */
	var getAllPushList = function(dataArr,column){
		var list = [];
		$.each(dataArr,function(index,item){
			list.push(item[column]);
		});
		return list;
	};
	//默认加载
	this.loadData();
};


