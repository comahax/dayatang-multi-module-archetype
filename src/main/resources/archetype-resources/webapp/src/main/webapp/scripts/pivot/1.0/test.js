var headColumns = {
	"rows" : [
			{
				"year" : {
					"name" : "年",
					"data" : [ 2010, 2011 ]
				}
			},
			{
				"section" : {
					"name" : "季",
					"data" : [ [ 1, 2 ], [ 1, 2 ] ]
				}
			},
			{
				"month" : {
					"name" : "月",
					"data" : [ [ [ 1, 2, 3 ], [ 4, 5, 6 ] ],
							[ [ 1, 2, 3 ], [ 4, 5, 6 ] ] ]
				}
			}
	//以此类推，N维数组
	],
	"dataColumns" : {
		"privence" : [ {
			"name" : "广东",
			"city" : [ {
				"name" : "清远"
			}, {
				"name" : "顺德"
			} ]
		}, {
			"name" : "湖南",
			"city" : [ {
				"name" : "清远"
			}, {
				"name" : "顺德"
			} ]
		} ]
	}
};

var b = {};