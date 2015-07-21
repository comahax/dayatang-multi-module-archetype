#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ${package}.AbstractIntegrationTest;
@Ignore
public class OutputValueIntegrationTest extends AbstractIntegrationTest {

	
	@Test
	public void test() {

		Project project = new Project("frameworkProject1");
		Area area = Area.get(2l);
		assertNotNull(area);
		project.setArea(area);
		project.setResponsibleDivision(InternalOrganization.get(74l));
		project.save();
		assertNotNull(project.getId());

		SubProject subProject = new SubProject("单点工程 1");
		subProject.setProject(project);
		subProject.setResponsibleDivision(InternalOrganization.get(27l));
		subProject.setArea(Area.get(88l));
		subProject.save();
		assertNotNull(subProject.getId());

		Specialty guandao = new Specialty("管道");
		guandao.save();
		assertNotNull(guandao.getId());
		SpecialtyProject specialtyProject = new SpecialtyProject(guandao);
		specialtyProject.setSubProject(subProject);
		specialtyProject.save();
		assertNotNull(specialtyProject.getId());

		for (int i = 1; i < 10; i++) {
			Monthly monthly = new Monthly(2012, i);
			OutputValue outputValue = application.reportOutputValue(monthly, specialtyProject, new BigDecimal(100 * i));
			assertNotNull(outputValue.getId());
		}

		Specialty guanglan = new Specialty("光缆");
		guanglan.save();
		assertNotNull(guanglan.getId());
		SpecialtyProject specialtyProject1 = new SpecialtyProject(guanglan);
		specialtyProject1.setSubProject(subProject);
		specialtyProject1.save();
		assertNotNull(specialtyProject1.getId());

		for (int i = 1; i < 12; i++) {
			Monthly monthly = new Monthly(2012, i);
			OutputValue outputValue = application.reportOutputValue(monthly, specialtyProject1, new BigDecimal(100 * i));
			assertNotNull(outputValue.getId());
		}

		SubProject subProject2 = new SubProject("单点工程 2");
		subProject2.setProject(project);
		subProject2.setResponsibleDivision(InternalOrganization.get(12l));
		subProject2.setArea(Area.get(35l));
		subProject2.save();
		assertNotNull(subProject2.getId());
		Specialty yitihua = new Specialty("一体化");
		yitihua.save();
		SpecialtyProject specialtyProject2 = new SpecialtyProject(yitihua);
		specialtyProject2.setSubProject(subProject2);
		specialtyProject2.save();

		for (int i = 1; i < 12; i++) {
			Monthly monthly1 = new Monthly(2012, i);
			OutputValue outputValue1 = application.reportOutputValue(monthly1, specialtyProject2, new BigDecimal(1000));
			assertNotNull(outputValue1.getId());
		}

	}



}
