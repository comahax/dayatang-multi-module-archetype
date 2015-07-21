#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

/**
 * 支出类型
 * @author yyang
 *
 */
public enum ExpenditureType {
    OPERATION {
        @Override
        public String getText() {
            return "运作费用";
        }

        @Override
        public String getDescription() {
            return "指项目运作发生的车辆、交通、差旅、房租、通信、办公、临时雇佣等运作费用";
        }
    },

    SALARY {
		@Override
		public String getText() {
			return "人力费用";
		}

		@Override
		public String getDescription() {
			return "指项目人员的薪酬、福利等人工成本费用";
		}
	},
	

	MARKET {
		@Override
		public String getText() {
			return "业务费用";
		}

		@Override
		public String getDescription() {
			return "指市场拓展费用";
		}
	},
	
	DEVICE_DEPRECIATION {
		@Override
		public String getText() {
			return "设备折旧费用";
		}

		@Override
		public String getDescription() {
			return "指电脑、手机、打印机、传真机、软件、食品、仪表等设备资产折旧，一般按三年折旧";
		}
	},

    AUXILIARY_MATERIAL {
        @Override
        public String getText() {
            return "耗材、辅材费用";
        }

        @Override
        public String getDescription() {
            return "指自购的易耗工具、材料等费用";
        }
    },


	
	SUBCONTRACT {
		@Override
		public String getText() {
			return "分包费用";
		}

		@Override
		public String getDescription() {
			return "指分包给其他公司、施工队的外包费用";
		}
	},

    MAIN_MATERIAL {
        @Override
        public String getText() {
            return "主材费用";
        }

        @Override
        public String getDescription() {
            return "包括外部采购和内部采购的主材费用";
        }
    },

	FUND_OCCUPATION {
		@Override
		public String getText() {
			return "资金占用费用";
		}

		@Override
		public String getDescription() {
			return "根据项目使用资金额及时间进行估算，按银行同期货款利率上浮20%计算";
		}
	},

    TAXES {
		@Override
		public String getText() {
			return "税金费用";
		}

		@Override
		public String getDescription() {
			return " ";
		}
	},

    OTHER {
		@Override
		public String getText() {
			return "其他费用";
		}

		@Override
		public String getDescription() {
			return "勘察设计及安全费";
		}
	};

	
	/**
	 * 获得名称
	 * @return
	 */
	public abstract String getText();
	
	/**
	 * 获得描述
	 * @return
	 */
	public abstract String getDescription();
}
