package org.nigajuan.groovy.ast

import org.codehaus.groovy.control.MultipleCompilationErrorsException
import org.springframework.core.io.ClassPathResource
import org.testng.annotations.Test

class RequestMappingCheckerTest {


    @Test(expectedExceptions = MultipleCompilationErrorsException)
    public void testExtensionController_NOk() {
        String script = new ClassPathResource("/requestmapping/ExtensionControllerNOk.groovy").inputStream.getText("UTF-8")
        GroovyClassLoader invoker = new GroovyClassLoader()
        invoker.parseClass(script , "test.groovy")
    }

    @Test
    public void testExtensionController_Ok() {
        String script = new ClassPathResource("/requestmapping/ExtensionControllerOk.groovy").inputStream.getText("UTF-8")
        GroovyClassLoader invoker = new GroovyClassLoader()
        invoker.parseClass(script , "test.groovy")
    }


    @Test//(expectedExceptions = MultipleCompilationErrorsException)
    public void testStandaloneController_NOk() {
        String script = new ClassPathResource("/requestmapping/StandaloneControllerNOk.groovy").inputStream.getText("UTF-8")
        GroovyClassLoader invoker = new GroovyClassLoader()
        invoker.parseClass(script , "test.groovy")
    }

    @Test
    public void testStandaloneController_Ok() {
        String script = new ClassPathResource("/requestmapping/StandaloneControllerOk.groovy").inputStream.getText("UTF-8")
        GroovyClassLoader invoker = new GroovyClassLoader()
        invoker.parseClass(script , "test.groovy")
    }
}

