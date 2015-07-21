#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.spring;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.dayatang.domain.EntityRepository;
import com.dayatang.hibernate.EntityRepositoryHibernate;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class PersistenceHibernateConfig {

	@Value("${symbol_dollar}{jdbc.driverClassName}")
	private String driverClassName;

	@Value("${symbol_dollar}{jdbc.url}")
	private String url;

	@Value("${symbol_dollar}{jdbc.username}")
	private String username;

	@Value("${symbol_dollar}{jdbc.password}")
	private String password;

	@Value("${symbol_dollar}{jdbc.generateDdl}")
	private boolean jpaGenerateDdl;

	@Value("${symbol_dollar}{hibernate.dialect}")
	private String hibernateDialect;

	@Value("${symbol_dollar}{hibernate.show_sql}")
	private boolean hibernateShowSql;

	@Value("${symbol_dollar}{hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddlAuto;

	public PersistenceHibernateConfig() {
		super();
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		final ComboPooledDataSource result = new ComboPooledDataSource();
		try {
			result.setDriverClass(driverClassName);
		} catch (PropertyVetoException e) {
			throw new DataAccessResourceFailureException("Cannot access JDBC Driver: " + driverClassName, e);
		}
		result.setJdbcUrl(url);
		result.setUser(username);
		result.setPassword(password);
		result.setMinPoolSize(10);
		result.setMaxPoolSize(300);
		result.setInitialPoolSize(10);
		result.setAcquireIncrement(5);
		result.setMaxStatements(0);
		result.setIdleConnectionTestPeriod(60);
		result.setAcquireRetryAttempts(30);
		result.setBreakAfterAcquireFailure(false);
		result.setTestConnectionOnCheckout(false);
		return result;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean result = new LocalSessionFactoryBean();
		result.setDataSource(dataSource());
		result.setPackagesToScan(new String[] { "${package}.domain" });
		result.setHibernateProperties(hibernateProperties());
		//result.setEventListeners(initEventListeners());
		return result;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		final HibernateTransactionManager result = new HibernateTransactionManager();
		result.setSessionFactory(sessionFactory().getObject());
		return result;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	// use this to inject additional properties in the EntityManager
	private final Properties hibernateProperties() {
		Properties results = new Properties();
		//results.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
		results.setProperty("hibernate.dialect", hibernateDialect);
		results.setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate4.SpringSessionContext");
		return results;
	}

	@Bean
	public EntityRepository repository() {
		return new EntityRepositoryHibernate();
	}
}
