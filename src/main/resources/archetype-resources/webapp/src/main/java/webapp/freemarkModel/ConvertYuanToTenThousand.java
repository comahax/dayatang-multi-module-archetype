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
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-7-18
 * Time: 下午5:16
 */
public class ConvertYuanToTenThousand implements TemplateMethodModel {
    @SuppressWarnings("rawtypes")
	@Override
    public Object exec(List arguments) throws TemplateModelException {
        if (null == arguments || arguments.size() < 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        if (null == arguments.get(0)) {
            return BigDecimal.ZERO;
        }
        return BigDecimalUtils.convertYuanToTenThousand(new BigDecimal(((String) arguments.get(0)).replaceAll(",",""))) ;
    }
}
