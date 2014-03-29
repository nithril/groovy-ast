package script

import org.codehaus.groovy.ast.MethodNode
import org.nigajuan.groovy.ast.check.RequestMappingChecker

/**
 * Created by nithril on 29/03/14.
 */


beforeVisitMethod { MethodNode methodNode ->


    new RequestMappingChecker(context.source , methodNode).check()



}