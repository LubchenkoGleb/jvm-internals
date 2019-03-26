package ua.procamp.classloader;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

public class Task1Runner {

    public static void main(String[] args)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException,
            InterruptedException, InvocationTargetException, NoSuchMethodException {


        while (true) {
            CustomClassLoader loader = new CustomClassLoader();

            TextService main = (TextService) loader.findClass("ua.procamp.classloader.TextServiceImpl")
                    .getDeclaredConstructor().newInstance();

            System.out.println(main.staticText() + ": " + LocalDateTime.now().toString());
            Thread.sleep(3000);
        }
    }
}
