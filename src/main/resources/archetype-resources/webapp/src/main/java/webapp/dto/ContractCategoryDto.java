#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.ContractCategory;
import ${package}.webapp.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午3:13
 */
public class ContractCategoryDto {

    private String name;

    private String text;

    private ContractCategoryDto() {
    }

    public static List<ContractCategoryDto> all(BaseAction action){
        List<ContractCategoryDto> results = new ArrayList<ContractCategoryDto>();
        for(ContractCategory each : ContractCategory.values()){
            ContractCategoryDto dto = new ContractCategoryDto();
            dto.name = each.name();
            dto.text = action.getText(dto.name);
            results.add(dto);
        }
        return results;

    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
