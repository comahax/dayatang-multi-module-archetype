#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;
import com.dayatang.domain.QuerySettings;

public class AreaTest {
	@Mock
	private EntityRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		AbstractEntity.setRepository(repository);
	}

	@After
	public void tearDown() throws Exception {
		AbstractEntity.setRepository(null);
	}

	@Test
	public void test() {
		
		
		Area area = new Area();
		area.setName("广东省");
		area.setLevel(Area.PROVINCE_LEVEL);
		area.setLeftValue(79);
		area.setRightValue(1117);

		assertEquals(area, area.getProvince());
		assertNull(area.getCity());
		assertNull(area.getCounty());

		Area guangxi = new Area();
		guangxi.setName("广西");
		guangxi.setLeftValue(1118);
		guangxi.setRightValue(1393);
		guangxi.setLevel(Area.PROVINCE_LEVEL);
		Area area1 = new Area();
		area1.setName("桂林");
		area1.setLevel(Area.CITY_LEVEL);
		area1.setLeftValue(1189);
		area1.setRightValue(1226);
		QuerySettings<Area> querySettings = QuerySettings.create(Area.class).lt("leftValue", 1189).gt("rightValue", 1226).eq("level", 2 - 1);
		when(repository.getSingleResult(querySettings)).thenReturn(guangxi);

		assertEquals(guangxi, area1.getProvince());
		assertNull(area1.getCounty());
		assertEquals(area1, area1.getCity());

	}

	@Test
	public void testFindbyAreaName() {
		Area area = new Area();
		area.setCode("551500");
		area.setName("广东省");
		QuerySettings<Area> settings = QuerySettings.create(Area.class);
		settings.eq("name", "广东省");
		when(repository.getSingleResult(settings)).thenReturn(area);

		Area result = Area.findByAreaName("广东省");
		assertEquals(area, result);
	}

	@Test
	public void test3() {
		Object[] obj = new Object[7];
		obj[1] = 1;
		obj[6] = 6;
		assertEquals(1, obj[1]);
		Object[] obj1 = new Object[obj.length + 1];
		System.arraycopy(obj, 0, obj1, 0, obj.length);
		assertEquals(1, obj1[1]);
		assertEquals(6, obj1[6]);
		assertNull(obj1[obj.length]);

	}
}
