package be.yassinhajaj.withouttdd;

public class Result<T> {
    private final T object;
    private final Throwable error;

    private Result(T object, Throwable error) {
        this.object = object;
        this.error = error;
    }

    public static <T> Result<T> ok(T object) {
        return new Result<T>(object, null);
    }

    public static <T> Result<T> error(Throwable error) {
        return new Result<T>(null, error);
    }

    public T getOrThrow() throws Throwable {
        if (object == null) {
            throw error;
        }
        return object;
    }
}
