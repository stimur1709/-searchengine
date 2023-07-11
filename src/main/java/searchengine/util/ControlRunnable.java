package searchengine.util;

import java.util.List;

public class ControlRunnable {

    private ControlRunnable() {

    }

    public static void start(List<Thread> threads) {
        threads.forEach(Thread::start);
    }

}
