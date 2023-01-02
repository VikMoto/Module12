import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class CountDownTimeStudy {

    static final AtomicLong startTime = new AtomicLong(System.currentTimeMillis());
    static List<Long> list = Collections.synchronizedList(new ArrayList<>());
    volatile static long timer;
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Work1 work1 = new Work1("OneSecond Thread", countDownLatch);
        Work5 work2 = new Work5("FiveSecond Thread", countDownLatch);
        work1.start();
        work2.start();
        Thread.sleep(10);
        countDownLatch.countDown();
    }
    static class Work1 extends Thread {
        private CountDownLatch countDownLatch;

        public Work1(String name, CountDownLatch countDownLatch) {

            this.countDownLatch = countDownLatch;
            setName(name);
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
        private CountDownLatch countDownLatch;

        public Work5(String name, CountDownLatch countDownLatch) {

            this.countDownLatch = countDownLatch;
            setName(name);
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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

