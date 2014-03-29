package org.nigajuan.groovy.ast.check

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * Created by nithril on 27/03/14.
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class RequestMappingCheckerTransformation implements ASTTransformation {


    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        if (!astNodes) return
        if (!astNodes[0]) return
        if (!astNodes[1]) return
        if (!(astNodes[0] instanceof AnnotationNode)) return
        if (!(astNodes[1] instanceof MethodNode)) return

        MethodNode annotatedMethod = astNodes[1]

        new RequestMappingChecker(sourceUnit , annotatedMethod).check()
    }
}