#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;
import org.apache.commons.lang3.time.DateUtils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

/**
 * User: zjzhai
 * Date: 13-3-31
 * Time: 下午5:26
 */
public class ProjectElementTest {

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
    public void testgetDuration() {
        Project project = new Project("xxxx");
        Date startDate = new Date();
        Date preFinishDate = DateUtils.addDays(startDate, 200);
        project.setStartDate(new Date());
        project.setStatus(ProjectStatus.CONSTRUCTING);
        project.setPredictFinishDate(preFinishDate);
        assertEquals(new Long(200), project.getDuration());

        assertEquals(new Long(200), project.getRemainingDuration());

        project.finish(new Date());
        assertNull(project.getRemainingDuration());
        assertEquals(new Long(200), project.getDuration());

    }

}
