package ua.procamp.benchmarking;

public class RuntimeExceptionWithStackTrace extends RuntimeException {
    public RuntimeExceptionWithStackTrace(String message) {
        super(message, null, true, true);
    }
}
