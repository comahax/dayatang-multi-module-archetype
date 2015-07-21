#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;

public class DictionaryQuery extends BaseQuery<Dictionary> {

	public DictionaryQuery() {
		super(QuerySettings.create(Dictionary.class));
	}

	public static DictionaryQuery create() {
		return new DictionaryQuery();
	}

	public DictionaryQuery category(DictionaryCategory category) {
		querySettings.eq("category", category).asc("sortOrder");
		return this;
	}

	public DictionaryQuery ascSortOrder() {
		querySettings.asc("sortOrder");
		return this;
	}

}
