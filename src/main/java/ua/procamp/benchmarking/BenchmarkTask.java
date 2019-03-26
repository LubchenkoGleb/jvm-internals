package ua.procamp.benchmarking;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 5, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.MILLISECONDS)
public class BenchmarkTask {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkTask.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    @State(Scope.Thread)
    public static class ThreadState {
        int[] integers = IntStream.range(0, 100000)
                .map(i -> ThreadLocalRandom.current().nextInt(100))
                .toArray();
    }

    @Benchmark
    public void loopPerfomance(ThreadState state, Blackhole blackhole) {
        int result = 0;
        for (int integer : state.integers) {
            result += integer;
        }
        blackhole.consume(result);
    }

    @Benchmark
    public void streamPerfomance(ThreadState state, Blackhole blackhole) {
        int result = IntStream.of(state.integers).sum();
        blackhole.consume(result);
    }


//    Conclusions: as a bigger array for processing as less difference between loop and stream performance
//    Results: size 100     loop: 28.9      stream 194.977
//    Results: size 100     loop: 315.429   stream 1216.489
//    Results: size 100     loop: 3039.967  stream 5525.622
//    Results: size 100     loop: 30092.725 stream 30558.979

    @Benchmark
    public void sequentialStreamPerformance(ThreadState state, Blackhole blackhole) {
        int result = IntStream.of(state.integers).parallel().sum();
        blackhole.consume(result);
    }

    @Benchmark
    public void parallelStreamPerformance(ThreadState state, Blackhole blackhole) {
        int result = IntStream.of(state.integers).parallel().sum();
        blackhole.consume(result);
    }

//    Conclusions:  the parallel streams can achieve some benefits only in case if operations in the processing chain
//                  are heavy and complicated but for lightweight operations such as adding numbers it's better only
//                  in some middle size range because in too small size it spends to many time for creation of
//                  stream and thread, but for huge size in spend too many time for reducing operations (not sure :) )
//    Results: size 100         parallel: 218643.469      sequential 277221.887
//    Results: size 1000        parallel: 151071.499      sequential 204367.231
//    Results: size 10_000      parallel: 220160.460      sequential 204367.231
//    Results: size 100_000     parallel: 518264.149      sequential 489045.345
//    Results: size 1_000_000   parallel: 326533.187      sequential 271309.214


    @Benchmark
    public void exceptionWithStackTracePerformance(Blackhole blackhole) {
        String result;
        try {
            throw new RuntimeExceptionWithStackTrace("message1");
        } catch (RuntimeExceptionWithStackTrace e) {
            result = e.getMessage();
        }
        blackhole.consume(result);
    }

    @Benchmark
    public void exceptionWithoutStackTracePerfomance(Blackhole blackhole) {
        String result;
        try {
            throw new RuntimeExceptionWithoutStackTrace("message2");
        } catch (RuntimeExceptionWithoutStackTrace e) {
            result = e.getMessage();
        }
        blackhole.consume(result);
    }

//    Results:  exceptionWithStackTracePerformance      1732.593
//              exceptionWithoutStackTracePerformance    25.343
//    Conclusions: If you build business logic based on exceptions you should think about turing off stack trace
}
