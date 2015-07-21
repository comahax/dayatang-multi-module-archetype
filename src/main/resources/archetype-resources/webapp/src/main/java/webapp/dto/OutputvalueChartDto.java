#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.webapp.commons.TimeBucket;
import ${package}.domain.DictionaryCategory;
import ${package}.domain.Dictionary;
import ${package}.domain.InternalOrganization;
import ${package}.domain.OutputValue;
import ${package}.query.DictionaryQuery;
import ${package}.query.OutputValueQuery;
import com.dayatang.utils.DateUtils;
import org.apache.struts2.json.annotations.JSON;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 产值的报表的参数
 * User: zjzhai
 * Date: 13-4-26
 * Time: 下午3:13
 */
public class OutputvalueChartDto {


    /**
     * x轴分类
     */
    private List<String> xAxisCategories;

    /**
     * 系列
     */
    private SortedSet<ColumnChartSeriesDto> series;

    private OutputvalueChartDto() {
    }


    /**
     * 项目类型的产值饼图
     *
     * @param start
     * @param end
     * @param timeBucket
     * @param grantedScope
     * @return
     */
    public static OutputvalueChartDto createProjecttypePieChartParams(Date start, Date end, TimeBucket timeBucket, InternalOrganization grantedScope) {
        OutputvalueChartDto result = new OutputvalueChartDto();
        SortedSet<ColumnChartSeriesDto> series = new TreeSet<ColumnChartSeriesDto>();

        int startYear = DateUtils.getYear(start);
        int endYear = DateUtils.getYear(end);
        for (int year = startYear; year <= endYear; year++) {
            for (Dictionary type : DictionaryQuery.create().category(DictionaryCategory.PROJECT_TYPE).list()) {
                OutputValueQuery query = OutputValueQuery.immediateScopeOf(grantedScope).year(year).projectType(type.getSerialNumber());
                BigDecimal total = OutputValue.totalOutputValueOf(query.list());
                ColumnChartSeriesDto seriesDto = ColumnChartSeriesDto.create(type.getText(), SeriesType.PIE);
                seriesDto.append(millionOf(total));
                series.add(seriesDto);
            }
        }
        result.series = series;
        return result;

    }


    /**
     * 客户分类产值报表
     *
     * @param start    开始日期
     * @param end      结束日期
     * @param scopeOrg 调用此方法者的访问范围
     * @return
     */
    public static OutputvalueChartDto createCustomPieChartParams(Date start, Date end, InternalOrganization scopeOrg) {
        OutputvalueChartDto result = new OutputvalueChartDto();
        SortedSet<ColumnChartSeriesDto> series = new TreeSet<ColumnChartSeriesDto>();

        int startYear = DateUtils.getYear(start);
        int endYear = DateUtils.getYear(end);
        for (int year = startYear; year <= endYear; year++) {
            for (Dictionary each : DictionaryQuery.create().category(DictionaryCategory.OWNER_TYPE).list()) {
                OutputValueQuery query = OutputValueQuery.immediateScopeOf(scopeOrg).year(year).ownerType(each.getSerialNumber());
                BigDecimal total = OutputValue.totalOutputValueOf(query.list());
                ColumnChartSeriesDto seriesDto = ColumnChartSeriesDto.create(each.getText(), SeriesType.PIE);
                seriesDto.append(millionOf(total));
                series.add(seriesDto);
            }
        }
        result.series = series;
        return result;
    }

    /**
     * 客户分类产值报表
     *
     * @param start      开始日期
     * @param end        结束日期
     * @param timeBucket 时间段：年，季，月
     * @param scopeOrg   调用此方法者的访问范围
     * @return
     */
    public static OutputvalueChartDto createCustomColumnChartParams(Date start, Date end, TimeBucket timeBucket, InternalOrganization scopeOrg) {
        OutputvalueChartDto result = new OutputvalueChartDto();

        List<String> xAxisCategories = new ArrayList<String>();
        SortedSet<ColumnChartSeriesDto> series = new TreeSet<ColumnChartSeriesDto>();

        int startYear = DateUtils.getYear(start);
        int endYear = DateUtils.getYear(end);
        for (int year = startYear; year <= endYear; year++) {
            for (Dictionary each : DictionaryQuery.create().category(DictionaryCategory.OWNER_TYPE).list()) {
                setXAxisCategories(xAxisCategories, each.getText());
                for (int i = 1; i <= timeBucket.getCount(); i++) {
                    OutputValueQuery query = timeBucket.wrap(OutputValueQuery.immediateScopeOf(scopeOrg).ownerType(each.getSerialNumber()), year, i);
                    BigDecimal total = OutputValue.totalOutputValueOf(query.list());
                    ColumnChartSeriesDto seriesDto = getColumnSeriesIfExist(timeBucket.getSeriesName(year, i), series);
                    seriesDto.append(millionOf(total));
                    series.add(seriesDto);
                }
            }
        }
        result.xAxisCategories = xAxisCategories;
        result.series = series;
        return result;
    }

    /**
     * 概况产值报表1
     *
     * @param start                 开始日期
     * @param end                   结束日期
     * @param timeBucket            时间段：年，季，月
     * @param internalOrganizations 哪机构下的产值情况
     * @return
     */
    public static OutputvalueChartDto createOverview1ColumnChartParams(Date start, Date end, TimeBucket timeBucket, final List<InternalOrganization> internalOrganizations) {
        OutputvalueChartDto result = new OutputvalueChartDto();

        List<String> xAxisCategories = new ArrayList<String>();
        SortedSet<ColumnChartSeriesDto> series = new TreeSet<ColumnChartSeriesDto>();

        int startYear = DateUtils.getYear(start);
        int endYear = DateUtils.getYear(end);
        for (int year = startYear; year <= endYear; year++) {
            for (InternalOrganization org : internalOrganizations) {
                setXAxisCategories(xAxisCategories, org.getName());
                for (int i = 1; i <= timeBucket.getCount(); i++) {
                    OutputValueQuery query = timeBucket.wrap(OutputValueQuery.immediateScopeOf(org), year, i);
                    BigDecimal total = OutputValue.totalOutputValueOf(query.list());
                    ColumnChartSeriesDto seriesDto = getColumnSeriesIfExist(timeBucket.getSeriesName(year, i), series);

                    seriesDto.append(millionOf(total));
                    series.add(seriesDto);
                }
            }
        }
        result.xAxisCategories = xAxisCategories;
        result.series = series;
        return result;
    }


    /**
     * 产值概况报表生产
     *
     * @param start                 开始日期
     * @param end                   结束日期
     * @param timeBucket            时间段：年，季，月
     * @param internalOrganizations 哪机构下的产值情况
     * @return
     */
    public static OutputvalueChartDto createOverviewColumnChartParams(Date start, Date end, TimeBucket timeBucket, final List<InternalOrganization> internalOrganizations) {
        OutputvalueChartDto result = new OutputvalueChartDto();

        List<String> xAxisCategories = new ArrayList<String>();
        SortedSet<ColumnChartSeriesDto> series = new TreeSet<ColumnChartSeriesDto>();

        int startYear = DateUtils.getYear(start);
        int endYear = DateUtils.getYear(end);
        for (int year = startYear; year <= endYear; year++) {
            for (InternalOrganization org : internalOrganizations) {
                ColumnChartSeriesDto seriesDto = getColumnSeriesIfExist(org.getName(), series);
                for (int i = 1; i <= timeBucket.getCount(); i++) {
                    OutputValueQuery query = timeBucket.wrap(OutputValueQuery.immediateScopeOf(org), year, i);
                    BigDecimal total = OutputValue.totalOutputValueOf(query.list());
                    setXAxisCategories(xAxisCategories, timeBucket.getSeriesName(year, i));
                    seriesDto.append(millionOf(total));
                    series.add(seriesDto);
                }
                series.add(seriesDto);
            }
        }
        result.xAxisCategories = xAxisCategories;
        result.series = series;
        return result;
    }

    private static void setXAxisCategories(List<String> xAxisCategories, String cate) {
        if (xAxisCategories.contains(cate)) {
            return;
        }
        xAxisCategories.add(cate);
    }

    @JSON(serialize = false)
    private static ColumnChartSeriesDto getColumnSeriesIfExist(String serialName, SortedSet<ColumnChartSeriesDto> series) {
        ColumnChartSeriesDto seriesDto = null;
        for (Iterator<ColumnChartSeriesDto> it = series.iterator(); it.hasNext(); ) {
            ColumnChartSeriesDto chartSeriesDto = it.next();
            if (chartSeriesDto != null && chartSeriesDto.getName().equals(serialName)) {
                seriesDto = chartSeriesDto;
            }
        }
        return null == seriesDto ? ColumnChartSeriesDto.createColumn(serialName) : seriesDto;
    }

    /**
     * 将元转成万元
     *
     * @param amount
     * @return
     */
    private static BigDecimal millionOf(BigDecimal amount) {
        if (null == amount) {
            return BigDecimal.ZERO;
        }

        return amount.divide(new BigDecimal(10000), 2, RoundingMode.CEILING);
    }

    public List<String> getxAxisCategories() {
        return xAxisCategories;
    }

    public SortedSet<ColumnChartSeriesDto> getSeries() {
        return series;
    }


}
