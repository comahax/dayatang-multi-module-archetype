#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.EqCriterion;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import com.dayatang.domain.internal.OrCriterion;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Organization;
import ${package}.domain.Person;

public class PersonQuery extends BaseQuery<Person> {

	public PersonQuery() {
		super(QuerySettings.create(Person.class));
	}

	public static PersonQuery create() {
		return new PersonQuery();
	}

	public <T extends Organization> PersonQuery id(long id) {
		querySettings.eq("id", id);
		return this;
	}

    public PersonQuery  excludeId(long id){
        querySettings.notEq("id", id);
        return this;
    }

	/**
	 * 查询外部机构人员
	 * 
	 * @param org
	 * @return
	 */
	public <T extends Organization> PersonQuery organization(Organization org) {
		querySettings.eq("organization", org);
		return this;
	}

	/**
	 * 在某个机构下的，包括所有层级
	 * 
	 * @param <T>
	 * 
	 * @return
	 */
	public <T extends Organization> PersonQuery subordinateWithSelf(InternalOrganization scope) {
		querySettings.and(new GeCriterion("organization.leftValue", scope.getLeftValue()), new LeCriterion(
				"organization.rightValue", scope.getRightValue()));
		return this;
	}

	/**
	 * 在某个机构下的，包括下一层级及本层级
	 * 
	 * @return
	 */
	public <T extends Organization> PersonQuery immediateWithSelf(InternalOrganization scope) {
		querySettings.and(new GeCriterion("organization.leftValue", scope.getLeftValue()), new LeCriterion(
				"organization.rightValue", scope.getRightValue()),
				new OrCriterion(new EqCriterion("organization.level", scope.getLevel() + 1), new EqCriterion(
						"organization.level", scope.getLevel())));
		return this;
	}

	public PersonQuery nameLike(String name) {
		if (null == name) {
			return this;
		}
		querySettings.containsText("name", name);
		return this;
	}

}
