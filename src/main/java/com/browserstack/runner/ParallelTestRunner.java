package com.browserstack.runner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.browserstack.tests.BStackDemoTest;
import com.browserstack.tests.BStackLocalTest;

/**
 * Runs both sample tests concurrently. Each Runnable creates its own session
 * against the BrowserStack hub; the SDK reports each one independently.
 */
public class ParallelTestRunner {

    public static void main(String[] args) throws InterruptedException {
        List<Runnable> tests = Arrays.asList(
                new BStackDemoTest(),
                new BStackLocalTest());

        ExecutorService executor = Executors.newFixedThreadPool(tests.size());
        for (Runnable test : tests) {
            executor.submit(test);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
    }
}
