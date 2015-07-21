#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import javax.persistence.Entity;

/**
 * 承包合同
 * 
 * @author zjzhai
 * 
 */
@Entity
public abstract class ChengBao extends Contract {

	private static final long serialVersionUID = 6219263394021338992L;

	public static ChengBao get(long id) {
		return ChengBao.get(ChengBao.class, id);
	}

}
