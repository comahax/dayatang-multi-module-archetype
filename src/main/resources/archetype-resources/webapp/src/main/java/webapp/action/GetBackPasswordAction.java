#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;


/**
 * 找回密码
 * User: zjzhai
 * Date: 13-4-11
 * Time: 上午9:39
 */

public class GetBackPasswordAction extends BaseAction {

    private static final long serialVersionUID = -1029225406830363586L;

    private String symbol;

    private String checksum;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }


}
