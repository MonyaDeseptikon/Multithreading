
class SharedResource {
    private int data;
    private boolean availableProd = false;


    public synchronized void produce(int value) throws InterruptedException {
        while (availableProd) {
            wait();
        }
        data = value;

        availableProd = true;
        notifyAll();

    }

    public synchronized int consume() throws InterruptedException {

        while (!availableProd) {
            wait();
        }
        availableProd = false;

        notifyAll();

        return data;
    }


}

class WaitNotifyExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Produced: " + i);
                    resource.produce(i);

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {

            try {

                for (int i = 0; i < 5; i++) {

                    int value = resource.consume();
                    System.out.println("Consumed: " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer2 = new Thread(() -> {

            try {
                for (int i = 0; i < 5; i++) {
                    int value = resource.consume();
                    System.out.println("Consumed2: " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });


        producer.start();
        consumer.start();
        consumer2.start();
    }
}



