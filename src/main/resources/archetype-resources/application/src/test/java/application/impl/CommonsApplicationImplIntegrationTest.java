#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.impl;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import ${package}.AbstractIntegrationTest;
import ${package}.domain.InternalOrganization;

public class CommonsApplicationImplIntegrationTest extends AbstractIntegrationTest {

	@Ignore
	@Test
	public void testCreateOrganization() {
		InternalOrganization root = createRootOrg();
		InternalOrganization finDept = new InternalOrganization("Finacial");
		application.createChildInternalOrganization(finDept, root);
		InternalOrganization devDept = new InternalOrganization("Development");
		application.createChildInternalOrganization(devDept, root);
		InternalOrganization java = new InternalOrganization("Java Team");
		application.createChildInternalOrganization(java, devDept);

		Set<InternalOrganization> children = root.getImmediateChildren();
		assertTrue(children.contains(finDept));
		assertTrue(children.contains(devDept));
		assertFalse(root.getImmediateChildren().contains(java));
		assertTrue(devDept.getImmediateChildren().contains(java));
		assertEquals(devDept, java.getParent());
		assertEquals(root, devDept.getParent());
		assertEquals(root, finDept.getParent());

		application.removeEntity(java);
		application.removeEntity(finDept);
		application.removeEntity(devDept);
		application.removeEntity(root);
	}

	private InternalOrganization createRootOrg() {
		InternalOrganization result = new InternalOrganization("root");
		result.setLeftValue(10000);
		result.setRightValue(10001);
		result.setLevel(0);
		return application.saveEntity(result);
	}

}
