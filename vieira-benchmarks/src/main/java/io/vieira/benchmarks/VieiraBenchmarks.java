package io.vieira.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import io.vieira.service.VieiraService;

@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class VieiraBenchmarks {
    
    private VieiraService service;
    private Random random;
    private String[] keys;
    private static final int KEY_COUNT = 1000;
    
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
    public void benchmarkPut(Blackhole bh) {
        String key = "key" + random.nextInt(KEY_COUNT);
        String value = "value" + System.currentTimeMillis();
        service.put(key, value);
        bh.consume(key); // Consume something to prevent dead code elimination
    }
    
    @Benchmark
    public void benchmarkGet(Blackhole bh) {
        String key = keys[random.nextInt(KEY_COUNT)];
        Object value = service.get(key);
        bh.consume(value);
    }
    
    @Benchmark
    public void benchmarkDelete(Blackhole bh) {
        String key = keys[random.nextInt(KEY_COUNT)];
        service.remove(key);
        bh.consume(key); // Consume something to prevent dead code elimination
    }
    
    @Benchmark
    public void benchmarkMixedOperations(Blackhole bh) {
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
    public void benchmarkReadWriteRatio(Blackhole bh) {
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