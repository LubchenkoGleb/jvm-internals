package ua.procamp.benchmarking;

public class RuntimeExceptionWithoutStackTrace extends RuntimeException {
    public RuntimeExceptionWithoutStackTrace(String message) {
        super(message, null, true, false);
    }
}
