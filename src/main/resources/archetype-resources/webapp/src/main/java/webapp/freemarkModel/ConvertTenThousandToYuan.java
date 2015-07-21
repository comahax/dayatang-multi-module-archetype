#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.freemarkModel;

import ${package}.commons.BigDecimalUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Freemarker模板方法
 * User: Administrator
 * Date: 13-7-18
 * Time: 下午5:13
 */
public class ConvertTenThousandToYuan implements TemplateMethodModel {
    @SuppressWarnings("rawtypes")
	@Override
    public Object exec(List arguments) throws TemplateModelException {
        if (null == arguments || arguments.size() < 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        return BigDecimalUtils.convertTenThousandToYuan((BigDecimal)arguments.get(0)) ;
    }
}
