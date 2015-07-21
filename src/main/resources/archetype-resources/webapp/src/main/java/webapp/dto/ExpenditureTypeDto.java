#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.ExpenditureType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 支出类型DTO
 *
 * @author zjzhai
 */
public class ExpenditureTypeDto {

    private String name;

    private String description;

    private String text;

    private BigDecimal amount = BigDecimal.ZERO;

    public ExpenditureTypeDto(ExpenditureType type, BigDecimal amount) {
        name = type.name();
        text = type.getText();
        description = type.getDescription();
        this.amount = amount;
    }

    public static List<ExpenditureTypeDto> generate() {
        List<ExpenditureTypeDto> results = new ArrayList<ExpenditureTypeDto>();

        for (ExpenditureType type : ExpenditureType.values()) {
            results.add(new ExpenditureTypeDto(type, BigDecimal.ZERO));
        }
        return results;
    }

    public static List<ExpenditureTypeDto> createBy(Map<ExpenditureType, BigDecimal> expenditureTypes) {
        List<ExpenditureTypeDto> results = new ArrayList<ExpenditureTypeDto>();
        for (ExpenditureType each : ExpenditureType.values()) {
            BigDecimal amount = null == expenditureTypes ? BigDecimal.ZERO : expenditureTypes.get(each);
            results.add(new ExpenditureTypeDto(each, amount));
        }
        return results;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public BigDecimal getAmount() {
        return amount;
    }


}
