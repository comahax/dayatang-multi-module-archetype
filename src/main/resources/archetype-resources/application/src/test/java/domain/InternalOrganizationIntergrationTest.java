#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import ${package}.AbstractIntegrationTest;

public class InternalOrganizationIntergrationTest extends AbstractIntegrationTest {

	@Ignore
	@Test
	public void test() {
		InternalOrganization parent = InternalOrganization.get(74l);
		InternalOrganization child = new InternalOrganization("新机构");
		child.setHomePage("home page");
		assertNull(child.getId());
		parent.createChild(child);
		assertNotNull(child.getId());

		Long id = child.getId();
		parent.createChild(child);

		assertEquals(id, child.getId());

	}

	@Ignore
	@Test
	public final void testQuery() {
		InternalOrganization internal = InternalOrganization.get(11l);
		assertNotNull(internal);
		for(InternalOrganization each : internal.getImmediateChildren()){
			System.out.println("${symbol_escape}n==================  " + each.getId());
		}
	}

}
