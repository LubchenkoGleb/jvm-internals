package ua.procamp.runtimeporxy;

import java.lang.reflect.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextServiceProxyInitializer {

    public static TextServiceInterface init() {
        return (TextServiceInterface) Proxy.newProxyInstance(
                TextService.class.getClassLoader(),
                new Class[]{TextServiceInterface.class},
                (proxy, method, methodArgs) -> {
                    if (method.getName().equals("variable")) {
                        String patternString = (String) methodArgs[0];
                        return replaceText(patternString);
                    } else {
                        return method.invoke(proxy, methodArgs);
                    }
                });

    }

    private static String replaceText(String template) {

        Pattern pattern = Pattern.compile("\\$\\{.*?}");
        Matcher matcher = pattern.matcher(template);

        return matcher.replaceAll(matchResult -> {
            String group = matchResult.group();
            return System.getProperty(group.replaceAll("[${}]", ""), "NULL");
        });
    }
}
