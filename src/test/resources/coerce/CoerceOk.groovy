package coerce

import groovy.transform.TypeChecked
import org.nigajuan.groovy.ast.MyBean
import org.nigajuan.groovy.ast.annotation.CoerceReturn

class CoerceOk {


     @CoerceReturn
     @TypeChecked
     public MyBean createBean() {
         return [10, "foo"]
     }


}

