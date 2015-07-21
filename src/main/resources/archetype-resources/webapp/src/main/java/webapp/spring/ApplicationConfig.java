#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dayatang.configuration.ConfigurationFactory;
import com.dayatang.configuration.WritableConfiguration;
import ${package}.application.CommonsApplication;
import ${package}.application.EventApplication;
import ${package}.application.ExcelApplication;
import ${package}.application.NoticeApplication;
import ${package}.application.ProjApplication;
import ${package}.application.SecurityApplication;
import ${package}.application.impl.SecurityApplicationImpl;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@ImportResource({"classpath*:applicationContext-activiti.xml", "classpath*:applicationContext-tx.xml"})
@EnableTransactionManagement
public class ApplicationConfig {

    @Bean
    public CommonsApplication commonsApplication() {
        return new ${package}.application.impl.CommonsApplicationImpl();
    }

    @Bean
    public ProjApplication projApplication() {
        return new ${package}.application.impl.ProjApplicationImpl();
    }

    @Bean
    public SecurityApplication securityApplication() {
        return new SecurityApplicationImpl();
    }

    @Bean
    public EventApplication eventApplication() {
        return new ${package}.application.impl.EventApplicationImpl();
    }

    @Bean
    public NoticeApplication noticeApplication() {
        return new ${package}.application.impl.NoticeApplicationImpl();
    }

    @Bean
    public ExcelApplication excelApplication() {
        return new ${package}.application.impl.ExcelApplicationImpl();
    }

    @Bean
    public ConfigurationFactory configurationFactory() {
        return new ConfigurationFactory();
    }


    /**
     * 任务调度器
     *
     * @return
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor result = new ThreadPoolTaskExecutor();
        result.setCorePoolSize(10);
        result.setMaxPoolSize(50);
        result.setQueueCapacity(1000);
        result.setKeepAliveSeconds(300);
        result.setRejectedExecutionHandler(callerRunsPolicy());
        return result;
    }

    @Bean
    public ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy() {
        return new ThreadPoolExecutor.CallerRunsPolicy();
    }

    @Bean(name = "AppConfig")
    public WritableConfiguration configuration() {
        return configurationFactory().fromClasspath("/conf.properties");
    }


}
