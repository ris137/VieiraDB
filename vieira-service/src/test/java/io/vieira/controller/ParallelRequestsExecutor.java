package io.vieira.controller;

import io.vieira.model.PutRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelRequestsExecutor {
    private static final MyHTTPClient rc = new MyHTTPClient();
    private static final int THREAD_POOL_SIZE = 1000;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        // List to hold Future objects
        List<Future<String>> futures = new ArrayList<>();

        // Submit tasks to the executor
        for (int i = 0; i < 1_000_000; i++) {
            futures.add(
                    (Future<String>)
                            executorService.submit(
                                    () -> {
                                        try {
                                            rc.update(new PutRequest("key", "value"));
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    }));
        }
    }
}
