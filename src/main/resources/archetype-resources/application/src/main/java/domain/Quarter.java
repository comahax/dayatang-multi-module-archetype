#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

/**
 * 季节
 * 
 * @author yyang
 * 
 */
public enum Quarter {
	SPRING {
		public String getZHText() {
			return "春季";
		}
	},
	SUMMER {
		public String getZHText() {
			return "夏季";
		}
	},
	AUTUMN {
		public String getZHText() {
			return "秋季";
		}
	},
	WINTER {
		public String getZHText() {
			return "冬季";
		}
	};

	public static Quarter getQuarter(int month) {
		if (month < 4) {
			return SPRING;
		}
		if (month < 7) {
			return SUMMER;
		}
		if (month < 10) {
			return AUTUMN;
		}
		return WINTER;
	}

	public abstract String getZHText();
}
