#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;

import com.dayatang.domain.internal.AndCriterion;
import com.dayatang.domain.internal.EqCriterion;
import ${package}.domain.Area;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Monthly;
import ${package}.domain.OutputValue;
import ${package}.domain.Project;
import ${package}.domain.Quarter;
import ${package}.domain.SingleContract;
import ${package}.domain.Specialty;
import ${package}.domain.SpecialtyProject;
import ${package}.domain.SubProject;
import com.dayatang.utils.DateUtils;

import java.util.*;

import java.util.Date;

public class OutputValueQuery extends BaseQuery<OutputValue> {

    private OutputValueQuery() {
        super(QuerySettings.create(OutputValue.class));
    }


    public static OutputValueQuery immediateScopeOf(InternalOrganization scope) {
        OutputValueQuery query = new OutputValueQuery();
        query.querySettings.eq(OutputValue.getQueryKeyOf(scope), scope);
        return query;
    }

    public OutputValueQuery area(Area area) {
        if (null == area) {
            return this;
        }
        int level = area.getLevel();
        if (level == Area.PROVINCE_LEVEL) {
            querySettings.eq("province", area);
            return this;
        }
        if (level == Area.CITY_LEVEL) {
            querySettings.eq("city", area);
            querySettings.eq("province", area.getParent());
            return this;
        }
        querySettings.eq("county", area);
        querySettings.eq("city", area.getParent());
        querySettings.eq("province", area.getParent().getParent());
        return this;
    }

    public OutputValueQuery year(int year) {
        querySettings.eq("monthly.year", year);
        return this;
    }

    public OutputValueQuery month(int month) {
        querySettings.eq("monthly.month", month);
        return this;
    }

    public OutputValueQuery monthly(Monthly monthly) {
        year(monthly.getYear());
        month(monthly.getMonth());
        return this;
    }

    public OutputValueQuery quarter(Quarter quarter) {
        querySettings.eq("quarter", quarter);
        return this;
    }

    public OutputValueQuery project(Project project) {
        querySettings.eq("project", project);
        return this;
    }

    public OutputValueQuery singleContract(SingleContract singleContract) {
        querySettings.eq("singleContract", singleContract);
        return this;
    }

    public OutputValueQuery subProject(SubProject subProject) {
        querySettings.eq("subProject", subProject);
        return this;
    }

    public OutputValueQuery specailtyProject(SpecialtyProject specailtyProject) {
        querySettings.eq("specialtyProject", specailtyProject);
        return this;
    }

    public OutputValueQuery specialty(Specialty specialty) {
        querySettings.eq("specialtyProject.specialty", specialty);
        return this;
    }

    public OutputValueQuery ascMonthly() {
        querySettings.asc("monthly.year").asc("monthly.month");
        return this;
    }

    public OutputValueQuery descMonthly() {
        querySettings.desc("monthly.year").desc("monthly.month");
        return this;
    }


    public OutputValueQuery ownerType(String ownerType) {
        querySettings.eq("ownerCategory", ownerType);
        return this;
    }


    public OutputValueQuery projectType(String projectType) {
        querySettings.eq("projectType", projectType);
        return this;
    }

    public OutputValueQuery between(Date from, Date to) {
        if (null == from) {
            Date now = new Date();
            from = DateUtils.date(DateUtils.getYear(now),1,1);
        }

        if (null == to) {
            Date now = new Date();
            to = DateUtils.date(DateUtils.getYear(now), 12, 31);
        }
        List<AndCriterion> andCriterions = new ArrayList<AndCriterion>();
        while (from.before(to)) {
            andCriterions.add(new AndCriterion(new EqCriterion("monthly.year", DateUtils.getYear(from)),
                    new EqCriterion("monthly.month", DateUtils.getMonth(from))));
            from = DateUtils.dateAfter(from, 1, Calendar.MONTH);
        }
        querySettings.or(andCriterions.toArray(new AndCriterion[andCriterions.size()])).desc("monthly.year").desc("monthly.month");

        return this;
    }


}