import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

public class CyclicBarierTimeCountDown {

    static final AtomicLong startTime = new AtomicLong(System.currentTimeMillis());
    static List<Long> list = Collections.synchronizedList(new ArrayList<>());
    volatile static long timer;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Work1(cyclicBarrier);
        new Work5(cyclicBarrier);

    }

    static class TimeCountDown extends Thread {
        @Override
        public void run() {
            System.out.println(" Time Start ");
        }
    }

    static class Work1 extends Thread {

        CyclicBarrier cyclicBarrier;

        public Work1(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
            start();
        }

        @Override
        public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the latch...\n", getName());
                cyclicBarrier.await();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timer = System.currentTimeMillis() - startTime.get();
                System.out.println(" Left seconds " + timer / 1000);
            }

        }
    }

    static class Work5 extends Thread {
        CyclicBarrier cyclicBarrier;

        public Work5(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
            start();
        }

        @Override
        public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the latch...\n", getName());
                cyclicBarrier.await();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    sleep(5000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("***** Left 5 seconds");
            }

        }
    }
}
