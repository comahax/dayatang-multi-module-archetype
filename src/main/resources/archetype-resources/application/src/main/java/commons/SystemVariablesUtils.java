#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.commons;

import com.dayatang.configuration.WritableConfiguration;
import com.dayatang.domain.InstanceFactory;

/**
 * 系统变量的操作
 * User: Administrator
 * Date: 13-5-16
 * Time: 下午7:33
 */
public class SystemVariablesUtils {


    /**
     * 系统默认密码
     */
    private static final String DEFAULT_PASSWORD = "default.password";


    /**
     * 系统通知的标题
     */
    private static final String SYS_NOTICE_TITLE = "sysnotice.title";

    /**
     * 系统名称
     */
    private static final String THE_SYSTEM_NAME = "the.system.name";



    /**
     * 系统监控的邮箱
     */
    private static final String SYS_MONITOR = "sysmonitor.email";

    private static WritableConfiguration configuration;


    public final static String getDefaultPassword() {
        return getConfiguration().getString(DEFAULT_PASSWORD);
    }


    /**
     * 得到系统消息通知的标题
     *
     * @return
     */
    public static final String getSysnoticeTitle() {
        return getConfiguration().getString(SYS_NOTICE_TITLE);
    }

    /**
     * 本系统的名称
     */
    public static final String getTheSystemName() {
        return getConfiguration().getString(THE_SYSTEM_NAME);
    }

    /**
     * 系统监控的邮箱
     *
     * @return
     */
    public static final String getSysMonitorEmailAddress() {
        return getConfiguration().getString(SYS_MONITOR);
    }




    /**
     * 获得系统配置接口，用于获取和更改设置项。
     *
     * @return
     */
    private static WritableConfiguration getConfiguration() {
        if (configuration == null) {
            configuration = InstanceFactory.getInstance(WritableConfiguration.class);
        }
        return configuration;
    }

}
