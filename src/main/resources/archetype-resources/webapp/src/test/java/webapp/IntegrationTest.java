#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import ${package}.domain.InternalOrganization;
import ${package}.domain.Person;
import ${package}.domain.PersonUser;
import ${package}.domain.Role;
import ${package}.domain.RoleAssignment;

@Ignore
public class IntegrationTest extends AbstractIntegrationTest {

	@Test
	public void test1() {
		Person person = new Person();
		person.setEmail("dddd " + +new Random().nextInt() + "@123.com");
		person.setName("name");
		person.setOrganization(InternalOrganization.get(1));

		PersonUser user = new PersonUser("11123213" + new Random().nextInt(), "dddd", person);

		RoleAssignment assignment = new RoleAssignment(user, Role.get(2l), InternalOrganization.get(1));

		application.saveSomeEntities(person, user, assignment);

		assertNotNull(person.getId());
		assertNotNull(user.getId());
		assertNotNull(assignment.getId());
	}

}
