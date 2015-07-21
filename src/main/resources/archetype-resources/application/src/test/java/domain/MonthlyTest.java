#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class MonthlyTest {

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		Monthly monthly = new Monthly(new Date("2012/02/05"));
		assertEquals(2012, monthly.getYear());
		assertEquals(2, monthly.getMonth());
	}
	
	public final void compareTo(){
		Monthly monthly = new Monthly(2012, 1);
		Monthly monthly1 = new Monthly(2012, 1);
		Monthly monthly2 = new Monthly(2012, 2);
		Monthly monthly3 = new Monthly(2014, 2);
		Monthly monthly4 = new Monthly(2011, 2);
		
		assertEquals(1, monthly.compareTo(monthly4));
		assertEquals(-1, monthly4.compareTo(monthly));
		assertEquals(0, monthly.compareTo(monthly1));
		assertEquals(1, monthly2.compareTo(monthly1));
		assertEquals(1, monthly3.compareTo(monthly1));
		
		
		
	}

}
