#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 报表的系统
 * User: zjzhai
 * Date: 13-4-26
 * Time: 下午3:19
 */
public class ColumnChartSeriesDto implements Comparable<ColumnChartSeriesDto> {

    /**
     * 系列类型
     */
    private String type = SeriesType.COLUMN.text();

    private String name;

    private List<BigDecimal> data = new ArrayList<BigDecimal>();

    public static ColumnChartSeriesDto createColumn(String name) {
        return create(name, SeriesType.COLUMN);
    }

    public static ColumnChartSeriesDto createPie(String name) {
        return create(name, SeriesType.PIE);
    }

    public static ColumnChartSeriesDto create(String name, SeriesType type){
        ColumnChartSeriesDto dto = new ColumnChartSeriesDto();
        dto.type = type.text();
        dto.name = name;
        return dto;
    }

    public ColumnChartSeriesDto append(BigDecimal value) {
        data.add(value);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<BigDecimal> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnChartSeriesDto)) return false;

        ColumnChartSeriesDto seriesDto = (ColumnChartSeriesDto) o;

        if (name != null ? !name.equals(seriesDto.name) : seriesDto.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).toHashCode();
    }

    @Override
    public int compareTo(ColumnChartSeriesDto o) {
        RuleBasedCollator collator = (RuleBasedCollator) Collator.getInstance(Locale.CHINA);
        return collator.compare(name, o.getName());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
