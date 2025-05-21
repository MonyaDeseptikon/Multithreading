
import java.util.concurrent.locks.ReentrantLock;

public class Lock {
    static Integer res = Integer.valueOf(0);

    public static void main(String[] args) {

        ReentrantLock locker = new ReentrantLock(); // создаем заглушку
        for (int i = 0; i < 5; i++) {

            Thread t = new Thread(new CommonResource(locker));
            t.start();
        }
    }


    static class CommonResource implements Runnable {

        ReentrantLock locker;

        CommonResource(ReentrantLock lock) {
            this.locker = lock;
        }

        public void run() {

            locker.lock(); // устанавливаем блокировку
            try {

                for (int i = 1; i < 5; i++) {

                    System.out.printf("%s %d \n", Thread.currentThread().getName(), res);
                    res++;
                    Thread.sleep(100);

                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                locker.unlock(); // снимаем блокировку
            }
        }
    }


}








