package org.nigajuan.groovy.ast

import org.codehaus.groovy.control.MultipleCompilationErrorsException
import org.springframework.core.io.ClassPathResource
import org.testng.Assert
import org.testng.annotations.Test

class CoerceTest {

    @Test(expectedExceptions = MultipleCompilationErrorsException)
    public void testCreateBean_NOk() {
        String script = new ClassPathResource("/coerce/CoerceNOk.groovy").inputStream.getText("UTF-8")
        GroovyClassLoader invoker = new GroovyClassLoader()
        invoker.parseClass(script , "test.groovy")
    }



    @Test
    public void testCreateBean_Ok() {
        String script = new ClassPathResource("/coerce/CoerceOk.groovy").inputStream.getText("UTF-8")
        GroovyClassLoader invoker = new GroovyClassLoader()
        def instance = invoker.parseClass(script , "test.groovy").newInstance().createBean()
        Assert.assertEquals(instance.a , 10)
        Assert.assertEquals(instance.b , "foo")
    }

}

