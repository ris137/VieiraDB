package io.vieira.benchmarks;

import io.vieira.service.VieiraService;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class VieiraConcurrentBenchmarks {

    private VieiraService service;
    private Random random;
    private String[] keys;
    private static final int KEY_COUNT = 1000;
    private static final int THREAD_COUNT = 4;

    @Setup
    public void setup() {
        service = new VieiraService();
        random = new Random();
        keys = new String[KEY_COUNT];

        // Pre-populate with some data
        for (int i = 0; i < KEY_COUNT; i++) {
            String key = "key" + i;
            String value = "value" + i;
            keys[i] = key;
            service.put(key, value);
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
    @Threads(THREAD_COUNT)
    public void concurrentPut(Blackhole bh) {
        String key = "key" + random.nextInt(KEY_COUNT);
        String value = "value" + System.currentTimeMillis();
        service.put(key, value);
        bh.consume(key);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
    @Threads(THREAD_COUNT)
    public void concurrentGet(Blackhole bh) {
        String key = keys[random.nextInt(KEY_COUNT)];
        Object value = service.get(key);
        bh.consume(value);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
    @Threads(THREAD_COUNT)
    public void concurrentMixedOperations(Blackhole bh) {
        int operation = random.nextInt(3);
        String key = keys[random.nextInt(KEY_COUNT)];

        switch (operation) {
            case 0:
                service.put(key, "value" + System.currentTimeMillis());
                bh.consume(key);
                break;
            case 1:
                Object value = service.get(key);
                bh.consume(value);
                break;
            case 2:
                service.remove(key);
                bh.consume(key);
                break;
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
    @Threads(THREAD_COUNT)
    public void concurrentReadWriteRatio(Blackhole bh) {
        // 80% reads, 20% writes
        if (random.nextDouble() < 0.8) {
            String key = keys[random.nextInt(KEY_COUNT)];
            Object value = service.get(key);
            bh.consume(value);
        } else {
            String key = "key" + random.nextInt(KEY_COUNT);
            String value = "value" + System.currentTimeMillis();
            service.put(key, value);
            bh.consume(key);
        }
    }
}
