import java.util.Optional;
import java.util.function.Function;

public class Log<T> {
    private final T value;
    private final String log;

    private Log(T value) {
        this.value = value;
        this.log = "";
    }

    private Log(T value, String log) {
        this.value = value;
        this.log = log;
    }

    private T get() {
        return this.value;
    }

    private String getLog() {
        return this.log;
    }

    static <T> Log<T> of(T value) {
        return Log.of(value, "");
    }

    static <T> Log<T> of(T value, String log) {
        return Optional.ofNullable(value)
            .flatMap(val
                     -> Optional.ofNullable(log)
                            .filter(x -> !(val instanceof Log))
                            .map(x -> new Log<T>(val, x)))
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid arguments"));
    }

    <U> Log<U> map(Function<? super T, ? extends U> mapper) {
        U u = mapper.apply(this.get());
        return new Log<U>(u, this.getLog());
    }

    <U> Log<U> flatMap(Function<? super T, ? extends Log<? extends U>> mapper) {
        Log<? extends U> result = mapper.apply(this.get());
        String str = "";
        if (this.getLog() != "") {
            str += this.getLog();
        }
        if (result.getLog() != "") {
            if (str != "") {
                str += '\n';
            }
            str += result.getLog();
        }
        return (str != "") ? Log.of(result.get(), str) : Log.of(result.get());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Log<?> other) {
            return this.get().equals(other.get()) &&
                this.getLog().equals(other.getLog());
        }

        return false;
    }

    @Override
    public String toString() {
        if (this.getLog() != "") {
            return "Log[" + this.get() + "]\n" + this.getLog();
        }
        return "Log[" + this.get() + "]";
    }
}
