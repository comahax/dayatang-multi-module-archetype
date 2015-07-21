#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import javax.persistence.MappedSuperclass;

import com.dayatang.domain.AbstractEntity;

@MappedSuperclass
public abstract class AbstractCommonEntity extends AbstractEntity {

	private static final long serialVersionUID = 557093722691911443L;

	// 是否有效
	private boolean disabled = false;

	public void disable() {
		disabled = true;
	}

	public void enable() {
		disabled = false;
	}

	public boolean isDisabled() {
		return disabled;
	}

}
