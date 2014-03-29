package org.nigajuan.groovy.ast.transformation

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * Created by nithril on 27/03/14.
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class CoerceTransformation implements ASTTransformation {


    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        if (!astNodes) return
        if (!astNodes[0]) return
        if (!astNodes[1]) return
        if (!(astNodes[0] instanceof AnnotationNode)) return
        if (!(astNodes[1] instanceof MethodNode)) return

        MethodNode annotatedMethod = astNodes[1]
        ClassNode returnType = annotatedMethod.getReturnType()

        new MyClassCodeVisitorSupport(sourceUnit, returnType).visitMethod(annotatedMethod)
    }

    static class MyClassCodeVisitorSupport extends ClassCodeVisitorSupport{
        SourceUnit sourceUnit
        ClassNode returnType

        MyClassCodeVisitorSupport(SourceUnit sourceUnit, ClassNode returnType) {
            this.sourceUnit = sourceUnit
            this.returnType = returnType
        }

        @Override
        void visitReturnStatement(ReturnStatement statement) {
            if (statement.getExpression() instanceof ListExpression){
                ListExpression listExpression = (ListExpression)statement.getExpression()
                ArgumentListExpression argumentListExpression = new ArgumentListExpression()
                argumentListExpression.setSourcePosition(listExpression)
                argumentListExpression.expressions.addAll(listExpression.expressions)
                ConstructorCallExpression callExpression = new ConstructorCallExpression(returnType , argumentListExpression)
                callExpression.setSourcePosition(listExpression)
                statement.setExpression(callExpression)
            }
        }

    }
}