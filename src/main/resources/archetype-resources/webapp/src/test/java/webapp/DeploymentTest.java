#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp;
import java.io.FileInputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dayatang.domain.InstanceFactory;
import ${package}.webapp.spring.ApplicationConfig;
import ${package}.webapp.spring.PersistenceHibernateConfig;
import ${package}.webapp.spring.SecurityShiroConfig;
import com.dayatang.spring.factory.SpringInstanceProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceHibernateConfig.class, ApplicationConfig.class, SecurityShiroConfig.class})
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class DeploymentTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private RepositoryService repositoryService;

	@Before
	public void setUp() throws Exception {
		InstanceFactory.setInstanceProvider(new SpringInstanceProvider(applicationContext));
	}

	@Ignore
	@Test
	public void deployProcesses() throws Exception {
		String barFileName = getClass().getResource("/bidding.bar").toURI().toURL().getFile();
		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(barFileName));
		String deploymentId = repositoryService.createDeployment().addZipInputStream(inputStream).deploy().getId();
		System.out.println("===========================" + deploymentId);
	}
	
}
