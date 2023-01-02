import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FizzBuzzThreads {
    volatile static int i = 0;
   static List<String> list = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {

        for (i = 1; i <= 30; i++) {
            new Number(i).start();
            new Fizz(i).start();
            new FizzBuzz(i).start();
            new Buzz(i).start();
        }
       // System.out.println(list);
        final String collect = list.stream().collect(Collectors.joining(", "));
        System.out.println(collect);
    }

    static class Fizz extends Thread {
        public Fizz(int i) { this.i = i; }
        private int i;

        @Override
        public void run() {
            while (true) {
                synchronized (list) {
                    if (i % 15 == 0) break;
                    if (i % 3 == 0) {
                        list.add("fizz");
                    }
                    break;
                }
            }
        }
    }

    static class Buzz extends Thread {
        public Buzz(int i) {
            this.i = i;
        }
        private int i;
        @Override
        public void run() {
            while (true) {
                synchronized (list) {
                    if (i % 15 == 0) break;
                    if (i % 5 == 0) list.add("buzz");
                    break;
              }
            }
        }
    }

    static class FizzBuzz extends Thread {
        public FizzBuzz(int i) {
            this.i = i;
        }
        private int i;

        @Override
        public void run() {
            while (true) {
                synchronized (list) {
                    if (i % 15 == 0) {
                        list.add("fizzbuzz");
                    }
                    break;
                }
            }
        }
    }
    static class Number extends Thread {
        public Number(int i) { this.i = i; }
        private int i;
        @Override
        public void run() {
            while (true) {
                synchronized (list) {
                    if ((i % 3 != 0) && (i % 5 != 0)) list.add(Integer.toString(i));
                    break;
                }
            }
        }
    }

}
