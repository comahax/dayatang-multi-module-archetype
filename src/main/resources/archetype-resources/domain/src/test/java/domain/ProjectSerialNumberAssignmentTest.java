#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.EntityRepository;
import com.dayatang.domain.QuerySettings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class ProjectSerialNumberAssignmentTest {
    @Mock
    private EntityRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        AbstractEntity.setRepository(repository);
    }

    @Test
    public void testGetNextLatestSerialNumber() {

        ProjectSerialNumberAssignment assignment = new ProjectSerialNumberAssignment();
        assignment.setCompanyId(1l);
        assignment.setSerialNumber("009");
        assignment.setYearCode("12");

        when(repository.getSingleResult(QuerySettings.create(ProjectSerialNumberAssignment.class).eq("companyId", 1l).eq("yearCode", "12").desc("id"))).thenReturn(assignment);

        assertEquals("010",assignment.getNextSerialNumber());


        ProjectSerialNumberAssignment assignment1 = new ProjectSerialNumberAssignment();
        assignment1.setCompanyId(2l);
        assignment1.setSerialNumber("020");
        assignment1.setYearCode("12");

        when(repository.getSingleResult(QuerySettings.create(ProjectSerialNumberAssignment.class).eq("companyId", 2l).eq("yearCode", "12").desc("id"))).thenReturn(assignment1);

        assertEquals("021",assignment1.getNextSerialNumber());


        ProjectSerialNumberAssignment assignment2 = new ProjectSerialNumberAssignment();
        assignment2.setCompanyId(3l);
        assignment2.setSerialNumber("100");
        assignment2.setYearCode("12");

        when(repository.getSingleResult(QuerySettings.create(ProjectSerialNumberAssignment.class).eq("companyId", 3l).eq("yearCode", "12").desc("id"))).thenReturn(assignment2);

        assertEquals("101",assignment2.getNextSerialNumber());

    }

    @After
    public void tearDown() throws Exception {
        AbstractEntity.setRepository(null);
    }


}
