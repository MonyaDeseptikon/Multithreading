import java.util.concurrent.Executor;

public final class Main {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        System.out.println("main do something");
        Runnable multithr = () -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("newThread do something");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                System.out.println("interrupted");
            }
        };
        Thread thread= new Thread(multithr);
        thread.start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("finished");
//        ClassThread classThread = new ClassThread();
//        classThread.start();
//        classThread.run();

    }

}
