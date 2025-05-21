import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;

class ConditionLock extends Thread {
    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        Condition dataWrite = lock.newCondition();
        Condition dataRead = lock.newCondition();
        List<Integer> dataOnServer = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        SharedResourceCond resource = new SharedResourceCond();
        Thread producer = new Thread(() -> {
            try {
                while (!dataOnServer.isEmpty()) {

                    lock.lockInterruptibly();

                    dataWrite.await();
                    resource.produce(dataOnServer.getFirst());
                    System.out.println("Produced: " + dataOnServer.getFirst());

                    System.out.println(dataOnServer.getFirst());
                    dataOnServer.remove(dataOnServer.removeFirst());
                    dataRead.signal();

                    lock.unlock();

                }
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (!dataOnServer.isEmpty()) {

                    lock.lockInterruptibly();
                    dataWrite.signal();

                    dataRead.await();
                    int value = resource.consume();
                    System.out.println("Consumed: " + value);

                    lock.unlock();
                }
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer2 = new Thread(() -> {
            try {
                while (!dataOnServer.isEmpty()) {

                    lock.lockInterruptibly();
                    dataWrite.signal();

                    dataRead.await();
                    int value = resource.consume();
                    System.out.println("Consumed2: " + value);
                    lock.unlock();
                }
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });


        producer.start();
        consumer.start();
        consumer2.start();

    }
}

class SharedResourceCond {
    private int data;

    public void produce(int value) throws InterruptedException {
        data = value;
    }

    public int consume() throws InterruptedException {
        return data;
    }
}

