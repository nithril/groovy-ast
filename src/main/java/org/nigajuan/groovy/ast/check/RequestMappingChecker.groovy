package org.nigajuan.groovy.ast.check

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.messages.SyntaxErrorMessage
import org.codehaus.groovy.syntax.SyntaxException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.util.UriTemplate

/**
 * Created by nithril on 29/03/14.
 */
class RequestMappingChecker {

    final SourceUnit sourceUnit
    final MethodNode methodNode

    RequestMappingChecker(SourceUnit sourceUnit, MethodNode methodNode) {
        this.sourceUnit = sourceUnit
        this.methodNode = methodNode
    }

    public List<Map> check() {

        List<Map> errors = []

        //find RequestMapping annotation
        AnnotationNode requestMapping = methodNode.annotations.find {
            RequestMapping.equals(it.classNode.getTypeClass())
        }

        if (!requestMapping) {
            return errors
        }

        String[] requestMappingPath = asStrings(requestMapping.getMember("value"))


        //Extract RequestMappingVariable
        Set<String> requestMappingVariables = new HashSet<>()

        requestMappingPath.each {
            UriTemplate template = new UriTemplate(it)
            requestMappingVariables.addAll(template.getVariableNames())
        }

        //Extract PathVariable from methods parameters
        Map<String, Parameter> pathVariableMap = [:]
        methodNode.parameters.each { parameter ->
            AnnotationNode pathVariable = parameter.annotations.find {
                PathVariable.equals(it.classNode.getTypeClass())
            }
            if (pathVariable) {
                String pathName = asString(pathVariable.getMember("value")) ?: parameter.name
                pathVariableMap[pathName] = parameter

            }
        }

        //find if RequestPathVariable are declared as PathVariable parameters method
        requestMappingVariables.each {
            if (!pathVariableMap.containsKey(it)) {
                addError("Error: @RequestMapping pattern {${it}} not declared as @PathVariable".toString(), requestMapping)
            }
        }

        //find if PathVariable are declared as RequestPathVariable parameters method
        pathVariableMap.each {
            if (!requestMappingVariables.contains(it.key)) {
                addError("Error: '@PathVariable ${it.key}' not declared in the @RequestMapping value".toString(), it.value)
            }
        }


        return errors

    }

    String asString(Expression value) {
        if (value instanceof ConstantExpression) {
            ConstantExpression ce = (ConstantExpression) value;
            return ce.getValue().toString()
        } else if (value instanceof PropertyExpression) {
            PropertyExpression pe = (PropertyExpression) value;
            return pe.getPropertyAsString()
        }
        return null
    }

    List<String> asStrings(Expression expression) {
        if (expression instanceof ListExpression) {
            return expression.expressions.collect {
                asString(it)
            }
        } else {
            String value = asString(expression)
            if (value){
                return [value]
            }
        }
        return []
    }

    void addError(String msg, ASTNode astNode){
        sourceUnit.getErrorCollector().addErrorAndContinue(new SyntaxErrorMessage(
                new SyntaxException(msg + '\n', astNode.getLineNumber(), astNode.getColumnNumber(), astNode.getLastLineNumber(), astNode.getLastColumnNumber()),
                sourceUnit))
    }

}
