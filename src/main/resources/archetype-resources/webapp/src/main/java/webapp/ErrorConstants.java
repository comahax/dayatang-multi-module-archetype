#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp;

public interface ErrorConstants {

    /**
     * 无权访问该机构
     */
	final static String FORBIDDEN_TO_INTERNALORGANIZATION = "FORBIDDEN_TO_INTERNALORGANIZATION";

    /**
     * 保存失败
     */
    final static String SAVE_FAILURE = "SAVE_FAILURE";

    /**
     * 您找的资源不存在
     */
    final static String RESOUCES_NOT_FOUND = "RESOUCES_NOT_FOUND";


    /**
     * 重置密码失败
     */
    final static String RESET_PASSWORD_FAIL = "RESET_PASSWORD_FAIL";

    /**
     * 邮箱是必须的。
     */
    final static String THE_EMAIL_IS_REQUIRED = "THE_EMAIL_IS_REQUIRED";


    /**
     * 邮箱不存在
     */
    final static String THE_EMAIL_IS_NOT_EXIST = "THE_EMAIL_IS_NOT_EXIST";

    /**
     * 输入的字符串和图片中的字符串不匹配
     */
    final static String CAPTCHA_VERIFY_ERROR = "CAPTCHA_VERIFY_ERROR";

    /**
     * 机构已经被引用
     */
    final static String INTERNAL_IS_REFERENCE = "INTERNAL_IS_REFERENCE";

    /**
     * 邮箱已经存在
     */
    final String THE_EMAIL_IS_EXISTS  =  "THE_EMAIL_IS_EXISTS";
}
