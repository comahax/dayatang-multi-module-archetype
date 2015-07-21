#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import ${package}.AbstractIntegrationTest;

public class AreaIntegrationTest extends AbstractIntegrationTest {
	
	@Ignore
	@Test
	public void testGetChildren() {
		Area guangdong = Area.get(5);
		Area zhanjiang = Area.get(91);
		Area wuchuan = Area.get(2763);
		assertTrue(guangdong.getChildren().contains(zhanjiang));
		assertTrue(zhanjiang.getChildren().contains(wuchuan));
		assertFalse(guangdong.getChildren().contains(wuchuan));
	}

	@Ignore
	@Test
	public void testGetParent() {
		Area guangdong = Area.get(5);
		Area zhanjiang = Area.get(91);
		Area wuchuan = Area.get(2763);
		assertEquals(zhanjiang, wuchuan.getParent());
		assertEquals(guangdong, zhanjiang.getParent());
	}
	
	

	@Ignore
	@Test
	public void test() {
		Area china = Area.get(-1L);
		System.out.println(china.getChildren().size());
		process(china);
	}

	private void process(Area parent) {
		int leftValue = parent.getLeftValue() + 1;
		int level = parent.getLevel() + 1;
		for (Area each : parent.getChildren()) {
			each.setLeftValue(leftValue);
			//int rightValue = leftValue + (each.getAllChildrenWithSelf().size() - 1) * 2 + 1;
			int rightValue = 0;
			each.setRightValue(rightValue);
			each.setLevel(level);
			application.saveEntity(each);
			process(each);
			leftValue = rightValue + 1;
		}
		
		
	}

}
