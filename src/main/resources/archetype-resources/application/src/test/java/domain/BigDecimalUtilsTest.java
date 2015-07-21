#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import ${package}.commons.BigDecimalUtils;
import org.junit.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BigDecimalUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		BigDecimal amount = new BigDecimal(111);
		BigDecimal amount1 = new BigDecimal(11);
		assertTrue(BigDecimalUtils.ltZero(new BigDecimal(-1)));
		assertFalse(BigDecimalUtils.ltZero(new BigDecimal(0)));
		assertFalse(BigDecimalUtils.leZero(amount));
		assertFalse(BigDecimalUtils.geZero(new BigDecimal(-9)));
		assertTrue(BigDecimalUtils.geZero(new BigDecimal(9)));
		assertTrue(BigDecimalUtils.geZero(new BigDecimal(0)));
		assertFalse(BigDecimalUtils.gtZero(new BigDecimal(-1)));
		assertFalse(BigDecimalUtils.gtZero(new BigDecimal(0)));

		assertTrue(BigDecimalUtils.aGtb(amount, amount1));
		assertFalse(BigDecimalUtils.aGtb(new BigDecimal(11), amount));

		assertEquals(new BigDecimal("10.00"), BigDecimalUtils.percentage(new BigDecimal(10), new BigDecimal(100)));
		assertEquals(new BigDecimal("100.00"), BigDecimalUtils.percentage(new BigDecimal(100), new BigDecimal(100)));
		assertEquals(new BigDecimal("0"), BigDecimalUtils.percentage(new BigDecimal(100), new BigDecimal(0)));

	}

	
	

}
