import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Str {
    private final Function<Consumer<String>, String> str;

    private Str(Function<Consumer<String>, String> str) {
        this.str = str;
    }

    public static Str of(String value) {
        return new Str(x -> {
            x.accept("traced Str: " + value);
            return value;
        });
    }

    public static Str of(Supplier<String> supplier) {
        return new Str(x -> {
            String s = supplier.get();
            x.accept("traced Str: " + s);
            return s;
        });
    }

    public Str map(Function<String, String> mapper) {
        return new Str(x -> {
            String s = mapper.apply(this.str.apply(x));
            x.accept("traced map: " + s);
            return s;
        });
    }

    public Str flatMap(Function<String, Str> mapper) {
        return new Str(x -> {
            String s = mapper.apply(this.str.apply(x)).str.apply(x);
            x.accept("traced flatMap: " + s);
            return s;
        });
    }

    public Str join(String other) {
        return this.map(x -> x + other);
    }

    public Str join(Str other) {
        return this.flatMap(x -> other.map(y -> x + y));
    }

    public void run(Consumer<String> action) {
        action.accept(this.str.apply(x -> {}));
    }

    public void print() {
        this.run(x -> System.out.println(x));
    }

    public void trace() {
        this.trace(x -> System.out.println(x));
    }

    public void trace(Consumer<String> tracer) {
        String s = this.str.apply(tracer);
        tracer.accept(s);;
    }
}