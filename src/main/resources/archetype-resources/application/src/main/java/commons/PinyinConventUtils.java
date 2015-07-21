#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.commons;

import opensource.jpinyin.PinyinHelper;

/**
 * User: zjzhai
 * Date: 13-6-13
 * Time: 上午11:00
 */
public class PinyinConventUtils {

    /**
     * 将汉字转成拼音
     * @param hanz 汉字
     * @return
     */
    public static String conventToShortPinyin(String hanz) {
        return PinyinHelper.getShortPinyin(hanz);
    }

}
