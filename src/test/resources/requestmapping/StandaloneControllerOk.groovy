package requestmapping

import groovy.transform.TypeChecked
import org.nigajuan.groovy.ast.annotation.RequestMappingChecker
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by nithril on 29/03/14.
 */
class StandaloneControllerOk {


    @RequestMappingChecker
    @RequestMapping(value = ["/path/{foo}", "/path/{bar}"])
    public void test(@PathVariable String foo , @PathVariable("bar") String bar_) {

    }

}
