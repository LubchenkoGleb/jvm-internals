package ua.procamp.runtimeporxy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextServiceProxyInitializerTest {


    @Test
    void testRegex() {

        System.setProperty("param1", "VALUE1");
        System.setProperty("param2", "VALUE2");

        TextServiceInterface textServiceProxy = TextServiceProxyInitializer.init();

        String negativeTemplate = "lala ${not_found_param1} and ${not_found_param2}";
        String negativeCaseResult = textServiceProxy.variable(negativeTemplate);
        assertEquals("lala NULL and NULL", negativeCaseResult);

        String halfMatchTemplate = "lala ${param1} and ${not_found_param2}";
        String halfMatchResult = textServiceProxy.variable(halfMatchTemplate);
        assertEquals("lala VALUE1 and NULL", halfMatchResult);

        String positiveTemplate = "lala ${param1} and ${param2}";
        String positiveResult = textServiceProxy.variable(positiveTemplate);
        assertEquals("lala VALUE1 and VALUE2", positiveResult);
    }
}
