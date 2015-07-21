#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.taglibs;

import ${package}.commons.BigDecimalUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 将传的值转成万元显示
 * User: zjzhai
 * Date: 13-6-5
 * Time: 下午7:06
 */
public class TenThousandDisplayTag extends TagSupport {
    private static final long serialVersionUID = 831324399952805374L;

    private BigDecimal val;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print(BigDecimalUtils.convertYuanToTenThousand(val));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public void setVal(BigDecimal val) {
        this.val = val;
    }
}
