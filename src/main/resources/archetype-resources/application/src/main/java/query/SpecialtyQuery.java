#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import ${package}.domain.Specialty;

public class SpecialtyQuery extends BaseQuery<Specialty> {

	public SpecialtyQuery() {
		super(QuerySettings.create(Specialty.class));
	}

	public static SpecialtyQuery create() {
		return new SpecialtyQuery();
	}
}
