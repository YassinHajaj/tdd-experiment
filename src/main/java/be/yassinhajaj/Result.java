package be.yassinhajaj;

public class Result<T> {
    private final T object;
    private final RuntimeException error;

    private Result(T object, RuntimeException error) {
        this.object = object;
        this.error = error;
    }

    public static <T> Result<T> ok(T object) {
        return new Result<T>(object, null);
    }

    public static <T> Result<T> error(RuntimeException error) {
        return new Result<T>(null, error);
    }

    public T getOrThrow() {
        if (object == null) {
            throw error;
        }
        return object;
    }
}
