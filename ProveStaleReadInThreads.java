
/**
 * This class proves the stale read of instance variables in Threads. It is difficult to reproduce due to optimization/ rearrangement of byte codes that jvm does.
 * However in this case Runner thread should run indefinitely unless you make Runner.started volatile type.
 * i.e. public volatile boolean started = false;
 **/
public class ProveStaleReadInThreads {

    public static void main(String[] args) {

        Runner runner = new Runner();

        Thread thread = new Thread(runner);

        thread.start();

        while (!runner.started) {
            // wait for Runner to set it true so that this loops end
        }
        runner.running = false;

        System.out.println("Test completed! ");

    }

    static class Runner implements Runnable {

        public boolean started = false;
        public boolean running = true;

        @Override public void run() {

            while (running) {
                System.out.println("Runner is still running ....");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                started = true;

            }
        }
    }
}
