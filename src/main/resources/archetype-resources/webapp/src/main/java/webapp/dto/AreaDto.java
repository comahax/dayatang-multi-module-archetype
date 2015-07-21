#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.util.ArrayList;
import java.util.List;

import ${package}.domain.Area;

public class AreaDto {

	private Long id;

	// 编码
	private String code;
	// 名称
	private String name;

	// 层级
	private Integer level;

	private String parentCode;

    private String fullName;

	public AreaDto(Area area) {
		id = area.getId();
		code = area.getCode();
		name = area.getName();
		level = area.getLevel();
        fullName = area.getFullName();
		parentCode = area.getParentCode();
	}

	public static List<AreaDto> childrenOf(Area area) {
		List<AreaDto> results = new ArrayList<AreaDto>();

		if (null == area || null == area.getChildren()) {
			return results;
		}

		for (Area each : area.getChildren()) {
			results.add(new AreaDto(each));
		}

		return results;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Integer getLevel() {
		return level;
	}

	public String getParentCode() {
		return parentCode;
	}

    public String getFullName() {
        return fullName;
    }
}
