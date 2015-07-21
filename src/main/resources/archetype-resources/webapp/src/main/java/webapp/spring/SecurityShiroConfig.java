#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import ${package}.application.SecurityApplication;
import ${package}.domain.PasswordEncoder;
import ${package}.infra.LocalSecurityRealm;
import ${package}.infra.PasswordEncoderSha1;

@Configuration
public class SecurityShiroConfig {
	
	@Autowired
	SecurityApplication securityApplication;
	
	@Bean
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean result = new ShiroFilterFactoryBean();
		result.setSecurityManager(securityManager());
		result.setLoginUrl("/login.action");
		result.setSuccessUrl("/worktable.action");
		result.setUnauthorizedUrl("/worktable.action");
		result.setFilters(filters());
		result.setFilterChainDefinitions(filterChainDefinitions());
		return result;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager result = new DefaultWebSecurityManager(localRealm());
		result.setCacheManager(cacheManager());
		return result;
	}

	@Bean
	public LocalSecurityRealm localRealm() {
		return new LocalSecurityRealm(securityApplication, credentialsMatcher());
	}

	@Bean
	public CredentialsMatcher credentialsMatcher() {
		return new HashedCredentialsMatcher("SHA-1");
	}
	
	private CacheManager cacheManager() {
		EhCacheManager result = new EhCacheManager();
		result.setCacheManagerConfigFile("classpath:ehcache.xml");
		return result;
	}

	private Map<String, Filter> filters() {
		Map<String, Filter> results = new HashMap<String, Filter>();
		results.put("authc", new PassThruAuthenticationFilter());
		return results;
	}

	private String filterChainDefinitions() {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/security-shiro.properties")));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				result.append(line + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			throw new RuntimeException("File 'security-shiro-local.properties' cannot be read!", e);
		}
		//System.out.println(result.toString());
		return result.toString();
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor result = new AuthorizationAttributeSourceAdvisor();
		result.setSecurityManager(securityManager());
		return result;
	}
	
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean() {
		MethodInvokingFactoryBean result = new MethodInvokingFactoryBean();
		result.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		result.setArguments(new Object[] {securityManager()});
		return result;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoderSha1();
	}
}
