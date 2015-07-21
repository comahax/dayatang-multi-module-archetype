#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;

public class ProjectTest {

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
	public void test1() {
		Project project = new Project("uiui");
		project.assosiateWithCooperationOrg(new OrganizationInfo(new CooperationOrganization("11"), new Person("22232342")));
		assertEquals(1, project.getProjCooperationOrgInfos().size());
		project.assosiateWithCooperationOrg(new OrganizationInfo(new CooperationOrganization("12"), new Person("22232342")));
		assertEquals(2, project.getProjCooperationOrgInfos().size());
	}

	@Test
	public void testEditable() {

	}

}
