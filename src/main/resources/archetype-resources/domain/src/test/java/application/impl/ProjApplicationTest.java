#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ${package}.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;
import ${package}.application.ProjApplication;

public class ProjApplicationTest {

	private ProjApplication application = new ProjApplicationImpl();

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



}
