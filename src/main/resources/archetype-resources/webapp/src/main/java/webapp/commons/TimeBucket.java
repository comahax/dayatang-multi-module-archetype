#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.commons;

import ${package}.domain.Quarter;
import ${package}.query.OutputValueQuery;

/**
 * User: zjzhai
 * Date: 13-4-25
 * Time: 下午7:33
 */
public enum TimeBucket {
    // 月
    MONTH {
        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public OutputValueQuery wrap(OutputValueQuery outputValueQuery,int year, Object obj) {
            outputValueQuery.year(year).month((Integer) obj);
            return outputValueQuery;
        }

        @Override
        public String getSeriesName(int year, int value) {
            return year + "-" + value;
        }
    },
    //季
    QUARTER {
        @Override
        public int getCount() {
            return 4;
        }

        public OutputValueQuery wrap(OutputValueQuery outputValueQuery,int year, Object obj) {
            outputValueQuery.year(year).quarter(Quarter.values()[((Integer) obj) - 1]);
            return outputValueQuery;
        }

        @Override
        public String getSeriesName(int year, int value) {
            return year + "-" + value + "季度";
        }
    },
    //年
    YEAR {
        @Override
        public int getCount() {
            return 1;
        }

        public OutputValueQuery wrap(OutputValueQuery outputValueQuery,int year, Object obj) {
            outputValueQuery.year(year);
            return outputValueQuery;
        }

        @Override
        public String getSeriesName(int year, int value) {
            return "" + year;
        }
    };

    public abstract int getCount();


    //对query的包装
    public abstract OutputValueQuery wrap(OutputValueQuery outputValueQuery,int year, Object obj);


    /**
     * 返回系列名
     *
     * @param year
     * @param value 值
     * @return
     */
    public abstract String getSeriesName(int year, int value);
}
