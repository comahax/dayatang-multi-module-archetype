#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;

public class SpecialtyTest {

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
	public void testCreateSpecialtiesFromId() {
		Long[] specialtyIds = new Long[3];
		for (long i = 0; i < specialtyIds.length; i++) {
			specialtyIds[(int) i] = (i + 1);
			when(repository.get(Specialty.class, i + 1)).thenReturn(new Specialty("name" + i));
		}
		Set<Specialty> specialties = new HashSet<Specialty>();
		specialties.addAll(Specialty.getSpecialtiesFromId(specialtyIds));

		for (long i = 0; i < specialtyIds.length; i++) {
			verify(repository).get(Specialty.class, i + 1);
		}
		assertEquals(specialtyIds.length, specialties.size());
		
	}

	@Test
	public void testCreateSpecialtiesFromIdNull() {
		Set<Specialty> results;
        results = Specialty.getSpecialtiesFromId( new Long[0]);
        assertTrue(results.isEmpty());
	}

}
