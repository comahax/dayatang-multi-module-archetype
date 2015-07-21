#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import ${package}.domain.ChengBao;
import ${package}.domain.Project;

public class ChengBaoQuery extends BaseQuery<ChengBao> {
	private ChengBaoQuery() {
		super(QuerySettings.create(ChengBao.class));
	}

	public static ChengBaoQuery create() {
		return new ChengBaoQuery();
	}

	public ChengBaoQuery project(Project project) {
		querySettings.eq("project", project);
		return this;
	}

	public ChengBaoQuery id(long chengBaoId) {
		querySettings.eq("id", chengBaoId);
		return this;
	}

}
