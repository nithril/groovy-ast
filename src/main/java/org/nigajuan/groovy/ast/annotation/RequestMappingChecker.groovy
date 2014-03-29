package org.nigajuan.groovy.ast.annotation

import org.codehaus.groovy.transform.GroovyASTTransformationClass
import org.nigajuan.groovy.ast.check.RequestMappingCheckerTransformation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention (RetentionPolicy.SOURCE)
@Target ([ElementType.METHOD])
@GroovyASTTransformationClass (classes = RequestMappingCheckerTransformation)
public @interface RequestMappingChecker {

    boolean biCheck() default true;
}