import java.util.concurrent.atomic.AtomicInteger;



class Synchronized {
    static int objectToLock = 0;

    public static void main(String[] args) {
        Synchronized mThr = new Synchronized();

        mThr.createThread();
        mThr.createThread();

    }

    synchronized private void createThread() {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();
    }
}

class MyThread extends Thread {


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int count = Synchronized.objectToLock++;
            System.out.println("objectToLock = " + count + Thread.currentThread().getName());
        }
    }


}


class Synchronized2 {


    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        Runnable task = () -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " do something");
            }
        };
        Thread thread = new Thread(task);
        thread.start();

        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + " do something");
            for (int i = 0; i < 10; i++) {
                Thread.currentThread().sleep(1000);
                System.out.println("step " + i);
            }

        }


    }
}

class Synchronized3 {


    public static void main(String[] args) {
        ObjectToLock objectToLockOne = new ObjectToLock();
        ObjectToLock objectToLockTwo = new ObjectToLock();
        getThread(objectToLockTwo, objectToLockOne);
        getThread(objectToLockOne, objectToLockTwo);
    }

    private static void getThread(ObjectToLock objectToLockOne, ObjectToLock objectToLockTwo) {
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("run: " + Thread.currentThread().getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        objectToLockTwo.stepOne(objectToLockOne);
    }
}).start();
    }


    static class ObjectToLock {
          void stepOne(ObjectToLock object) {
            System.out.println("steoOne: " + Thread.currentThread().getName());
            object.stepTwo(this);
        }

          void stepTwo(ObjectToLock object) {
            System.out.println("stepTwo: " + Thread.currentThread().getName());
              System.out.println(object.toString());
        }

        @Override
        public String toString() {
            return this.getClass().getEnclosingClass().getName();
        }
    }
}


