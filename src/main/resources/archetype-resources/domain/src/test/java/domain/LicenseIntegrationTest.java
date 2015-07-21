#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import ${package}.AbstractIntegrationTest;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * User: tune
 * Date: 13-6-5
 * Time: 下午5:05
 */
@Ignore
public class LicenseIntegrationTest extends AbstractIntegrationTest {


    @Test
    public void testRemove(){
        Long id = 2L;
        List<License> licenses = License.findAll(License.class);
        if(licenses != null && licenses.size() > 0){
            License license = licenses.get(0);
            license.remove();
        }
    }
}
