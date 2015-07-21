#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;

public class SubProjectTest {
	@Mock
	private EntityRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		AbstractEntity.setRepository(repository);
	}

	@After
	public void tearDown() throws Exception {
		AbstractEntity.setRepository(null);
	}

	@Test
	public void testAddSpecialty() {

		Collection<Specialty> specialties = new ArrayList<Specialty>();
		Specialty guandao = new Specialty("管道");
		Specialty guanglan = new Specialty("光缆");
		Specialty jizhan = new Specialty("基站");
		jizhan.setId(1l);
		specialties.add(guandao);
		specialties.add(guanglan);

		Project project = new Project("项目");
		SpecialtyProject jizhanProj = new SpecialtyProject(new Specialty("基站"));
		SubProject subProject = new SubProject("广州基站");
		long subProjectId = 1l;
		subProject.setId(subProjectId);
		subProject.setProject(project);
		jizhanProj.setSubProject(subProject);

		String sql = "SELECT DISTINCT o.specialty FROM SpecialtyProject o WHERE o.subProject = :subProject";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subProject", subProject);

		when(repository.find(sql, params, Specialty.class)).thenReturn(Arrays.asList(jizhan));

		SubProject result = subProject.addSpecialty(jizhan);

		assertEquals(1, SpecialtyProject.specialtiesOfSubProject(result).size());


	}

}
