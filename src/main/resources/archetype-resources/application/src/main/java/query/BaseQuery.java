#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.*;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuery<T extends AbstractEntity> {
	protected QuerySettings<T> querySettings;
	private EntityRepository repository;

	public BaseQuery(QuerySettings<T> querySettings) {
		super();
		this.querySettings = querySettings;
	}

	public EntityRepository getRepository() {
		if (repository == null) {
			repository = InstanceFactory.getInstance(EntityRepository.class);
		}
		return repository;
	}

	public void setRepository(EntityRepository repository) {
		this.repository = repository;
	}

	public BaseQuery<T> id(Long id) {
		querySettings.eq("id", id);
		return this;

	}

	public BaseQuery<T> descId() {
		querySettings.desc("id");
		return this;
	}

	/**
	 * 求得结果集的记录数
	 * 
	 * @return
	 */
	public int resultsSize() {
		List<T> results = getRepository().find(querySettings);
		return results == null ? 0 : results.size();
	}

	/**
	 * 返回查询后的实体列表
	 * 
	 * @return
	 */
	public List<T> list() {
		return getRepository().find(querySettings);
	}

	/**
	 * 返回查询后的实体列表
	 * 
	 * @return
	 */
	public List<T> list(int from, int to) {
		List<T> results = getRepository().find(querySettings);
		if (results != null) {
			results = results.subList(from, to);
		}
		return results;
	}

	public BaseQuery<T> or(QueryCriterion... queryCriterion) {
		querySettings.or(queryCriterion);
		return this;
	}

	public BaseQuery<T> and(QueryCriterion... queryCriterion) {
		querySettings.and(queryCriterion);
		return this;
	}

	/**
	 * 返回查询后的实体ID列表
	 * 
	 * @return
	 */
	public List<Long> idList() {
		List<Long> results = new ArrayList<Long>();
		for (T t : getRepository().find(querySettings)) {
			results.add(t.getId());
		}
		return results;
	}

	/**
	 * 返回拼装好的querySettings
	 * 
	 * @return
	 */
	public QuerySettings<T> build() {
		return querySettings;
	}

	/**
	 * 反回一个结果
	 * 
	 * @return
	 */
	public T getSingleResult() {
		return getRepository().getSingleResult(querySettings);
	}

	public BaseQuery<T> disabledIf(Boolean disabled) {
		if (null == disabled) {
			return this;
		}
		return disabled ? disabled() : enabled();
	}

	public BaseQuery<T> disabled() {
		querySettings.eq("disabled", true);
		return this;
	}

	public BaseQuery<T> enabled() {
		querySettings.eq("disabled", false);
		return this;
	}

	public BaseQuery<T> descCreated() {
		querySettings.desc("created");
		return this;
	}

	public BaseQuery<T> ascCreated() {
		querySettings.asc("created");
		return this;
	}



}
