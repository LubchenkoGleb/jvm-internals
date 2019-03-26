package ua.procamp.threadlocal;

public class ThreadLocalTest {

    private ThreadLocal<String> dataHolder = new ThreadLocal<>();

    public static void main(String[] args) {

        ThreadLocalTest threadLocalTest = new ThreadLocalTest();

        Runnable th1Runnable = () -> {
            try {
                threadLocalTest.dataHolder.set("Val1");
                System.out.println("Th1 value set");
                Thread.sleep(5000);
                System.out.println("Th1 ThreadLocalValue: " + threadLocalTest.dataHolder.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable th2Runnable = () -> {
            try {
                Thread.sleep(1000);
                threadLocalTest.dataHolder.set("Val2");
                System.out.println("Th2 value set");
                Thread.sleep(5000);
                System.out.println("Th2 ThreadLocalValue: " + threadLocalTest.dataHolder.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(th1Runnable).start();
        new Thread(th2Runnable).start();
    }
}
