#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static org.junit.Assert.*;


import org.junit.Ignore;
import org.junit.Test;

import ${package}.AbstractIntegrationTest;
@Ignore
public class ProjectIntegrationTest extends AbstractIntegrationTest {


 
    @Test
    public void test1() {
        for (OutputValue each :
                OutputValue.findAll(OutputValue.class)) {
            System.out.println(each.toString());
        }
    }
}
