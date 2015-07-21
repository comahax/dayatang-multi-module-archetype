#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dayatang.domain.InstanceFactory;
import ${package}.application.ProjApplication;
import ${package}.webapp.spring.ApplicationConfig;
import ${package}.webapp.spring.PersistenceHibernateConfig;
import ${package}.webapp.spring.SecurityShiroConfig;
import com.dayatang.spring.factory.SpringInstanceProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceHibernateConfig.class, ApplicationConfig.class, SecurityShiroConfig.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public abstract class AbstractIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	protected ProjApplication application;

	@Before
	public void setUp() throws Exception {
		SpringInstanceProvider provider = new SpringInstanceProvider(applicationContext);
		InstanceFactory.setInstanceProvider(provider);
	}

	@After
	public void tearDown() throws Exception {
		InstanceFactory.setInstanceProvider(null);
	}

}
