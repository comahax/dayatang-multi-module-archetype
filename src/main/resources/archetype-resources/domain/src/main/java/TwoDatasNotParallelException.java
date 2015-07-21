#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

/**
 * 参数传输不正确 ，当需要同时接收两批数据，但数据数组的下标不平行
 * 
 * @author zjzhai
 * 
 */
public class TwoDatasNotParallelException extends BaseRuntimeException {

	private static final long serialVersionUID = -7456597135625803729L;

	@Override
	public String toString() {
		return "TWO_DATAS_NOT_PARALLEL_EXCEPTION";
	}

}
