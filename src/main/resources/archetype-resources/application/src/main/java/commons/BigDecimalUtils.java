#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.commons;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

	public static boolean eqZero(final BigDecimal that) {
		return BigDecimal.ZERO.compareTo(that) == 0;
	}

	public static boolean leZero(final BigDecimal that) {
		return BigDecimal.ZERO.compareTo(that) >= 0;
	}

	// 小于0
	public static boolean ltZero(BigDecimal that) {
		return BigDecimal.ZERO.compareTo(that) > 0;
	}

	public static boolean geZero(final BigDecimal that) {
		return BigDecimal.ZERO.compareTo(that) <= 0;
	}

	// 大于0
	public static boolean gtZero(final BigDecimal that) {
		return BigDecimal.ZERO.compareTo(that) < 0;
	}

	/**
	 * a大于b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean aGtb(final BigDecimal a, final BigDecimal b) {
		return gtZero(a.subtract(b));
	}

	/**
	 * a大于等于b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean aGeb(final BigDecimal a, final BigDecimal b) {
		return geZero(a.subtract(b));
	}

	/**
	 * 将万元转成元
	 * 
	 * @param tenThousand
	 */
	public static BigDecimal convertTenThousandToYuan(BigDecimal tenThousand) {
		if (null == tenThousand) {
			return BigDecimal.ZERO;
		}
		return tenThousand.multiply(new BigDecimal(10000));
	}

	/**
	 * 将元转成万元
	 * 
	 * @param yuan
	 */
	public static BigDecimal convertYuanToTenThousand(BigDecimal yuan) {
		if (null == yuan) {
			return BigDecimal.ZERO;
		}
		return yuan.divide(new BigDecimal(10000), RoundingMode.CEILING);
	}

	public static BigDecimal percentage(BigDecimal dividend, BigDecimal divisor) {
		if (null == divisor || eqZero(divisor)) {
			return BigDecimal.ZERO;
		}
		return dividend.divide(divisor, 2, RoundingMode.CEILING).multiply(new BigDecimal(100));
	}

}
