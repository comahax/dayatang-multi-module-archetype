#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.ExcelErrorInfo;


import java.util.List;

/**
 * excel导入后返回的结果
 * <p/>
 * User: zjzhai
 * Date: 13-4-28
 * Time: 上午11:38
 */
public class ImportResultDto<T> {

    /**
     * 错误信息
     */
    private List<ExcelErrorInfo> errorInfos;

    /**
     * 注入后的实体
     */
    private List<T> entities;

    public ImportResultDto(List<ExcelErrorInfo> errorInfos, List<T> entities) {
        this.errorInfos = errorInfos;
        this.entities = entities;
    }

    public List<T> getEntities() {
        return entities;
    }

    public List<ExcelErrorInfo> getErrorInfos() {
        return errorInfos;
    }
}
