package coerce

import groovy.transform.TypeChecked
import org.nigajuan.groovy.ast.MyBean
import org.nigajuan.groovy.ast.annotation.CoerceReturn

class CoerceNOk {


    @CoerceReturn
    @TypeChecked
    public MyBean createBean() {
        return [10, 1000,""]
    }


}

