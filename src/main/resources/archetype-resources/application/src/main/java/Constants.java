#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.Date;

import com.dayatang.utils.DateUtils;

/**
 * 项目常量定义。
 * 
 * @author yyang
 * 
 */
public interface Constants {

	public static final String CHARSET = "charset";

	public static final String UPLOAD_DIR = "upload.dir";

	public static final String DATE_FORMAT = "date.format";

	public static final String TIME_FORMAT = "time.format";

	public static final String DATE_TIME_FORMAT = "date.time.format";

	public static final String NUMBER_FORMAT = "number.format";

	public static final String PERCENTAGE_FORMAT = "percentage.format";

	public static final Date MIN_DATE = DateUtils.parseDate("2000-01-01");

	// 文件管理：一个文件夹中包含的最大文件数
	public static final long FILE_MAX_IN_A_FOLD = 3000;
}
