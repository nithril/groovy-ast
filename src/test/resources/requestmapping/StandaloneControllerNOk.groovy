package requestmapping

import groovy.transform.TypeChecked
import org.nigajuan.groovy.ast.annotation.RequestMappingChecker
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by nithril on 29/03/14.
 */
class StandaloneController {


    @RequestMappingChecker
    @RequestMapping(value = ["/path/{foo}", "/path/{bar}"])
    public void test(String foo) {

    }

}
