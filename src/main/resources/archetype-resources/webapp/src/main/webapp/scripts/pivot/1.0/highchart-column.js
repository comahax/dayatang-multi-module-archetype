//数据透视图
//基于数据透视表的json数据+highcharts库实现数据透视图
/**
 * @license highchartsPivotTable V1.0
 * highcharts简单封装
 * 期待使用方式:
 * $('div#xxx').ptChart({options});在某个div元素上加载一个图表
 * 默认可使用$('div#xxx').ptChart({[xAxis横轴值],[{name:'',data:[,,,]},{name:'',data:[,,,]}]});x轴和y轴的数据
 * 为了避免冲突,用匿名方法包裹自身的方法
 * @author wenxiang.Zhou
 */
(function($) {
	var defaults = {
			chart: {
	            renderTo: '',
	            //右边距控制用于显示legend
	            marginRight: 130,
	            //下边距控制用于显示x轴的标题
	            marginBottom: 50
	        },
	        title: {
	            text: '默认主标题'
	        },
	        //填充值
	        series: [],
	    	credits: {
	    		enabled: true,
	    		text: '大雅堂',
	    		href: 'http://www.dayatang.com'
	    	},
	    	tooltip: {
	    		 formatter: function() {
	    		        return '<b>'+this.series.name+'</b> : '+ this.y;
	    		     },
                percentageDecimals: 1
            },
	    	//pie特有
	    	plotOptions : {
				pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    showInLegend: true,
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        formatter: function() {
                            return '<b>'+ this.point.name +'</b>: '+ Math.round(this.percentage*100)/100 +' %';
                        }
                    }
                }
			},
			legend : {
				// horizontal:水平展开,可选项还有vertical:垂直展开
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'top',
	            // x偏移值
	            x: -10,
	            // y偏移值
	            y: 150,
	            borderWidth: 1
	            
			}
		};
	//扩展这个方法到jquery
	$.fn.extend({
		ptChart : function(params,type) {
			//出图之前扩展处理，用于增强特定图表的实现功能
			switch (type) {
			case 'pie':
				//饼图
				break;
			case 'line':
				//线性图
				break;
			case 'area':
				break;
			// 可扩展其他图形
			default:
				// 默认柱状图
				defaults.chart.type = 'column';
				break;
			}
			var options = $.extend(defaults, params);
			//组装参数
			return this.each(function(){
				options.chart.renderTo = $(this).attr('id');
				var tempChart = new Highcharts.Chart(options);
			});
		}
	});
})(jQuery);
