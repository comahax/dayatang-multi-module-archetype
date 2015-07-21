#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import ${package}.AbstractIntegrationTest;
import ${package}.query.InternalOrganizationQuery;
import ${package}.query.SpecialtyProjectQuery;
import ${package}.query.SubProjectQuery;

@Ignore
public class SubProjectIntegrationTest extends AbstractIntegrationTest {
	@Test
	public void test() {
		Project project = Project.get(1l);
		SubProject subProject = new SubProject("name");
		subProject.setProject(project);
		subProject.save();
		assertNotNull(subProject.getId());
		Specialty specialty = Specialty.get(1l);
		subProject.addSpecialty(specialty);

		assertTrue(subProject.getSpecialties().contains(specialty));

	}

	@Test
	public void test2() {
		SubProject subProject = SubProject.get(40l);
		assertNotNull(subProject.getId());
		Specialty specialty = Specialty.get(2l);
		subProject.addSpecialty(specialty);

		assertTrue(subProject.getSpecialties().contains(specialty));
		assertTrue(subProject.getSpecialties().size() == 2);

	}

	@Test
	public void test34() {
		OutputValue.findAll(OutputValue.class);
	}

	@Test
	public final void test3() {
		Specialty specialty = Specialty.get(2l);
		assertNotNull(specialty);

		SubProject subProject = SubProjectQuery.createResponsibleOf(InternalOrganization.get(1l)).enabled().id(36l).getSingleResult();
		assertNotNull(subProject);

		SpecialtyProject specialtyProject = SpecialtyProjectQuery.createResponsibleOf(InternalOrganization.get(1l)).subProject(subProject).specialty(specialty).enabled().getSingleResult();

		assertNotNull(specialtyProject);
	}
}
