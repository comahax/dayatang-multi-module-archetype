#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

/**
 * 系列类型
 * User: zjzhai
 * Date: 13-5-13
 * Time: 下午4:27
 */
public enum SeriesType {

    COLUMN,

    PIE,

    SPLINE;

    public String text() {
        return this.name().toLowerCase();
    }

}
