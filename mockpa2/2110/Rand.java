import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

class Rand<T> {
    private final int seed;
    private final Function<Integer, T> func;

    private Rand(int seed, Function<Integer, T> func) {
        this.seed = seed;
        this.func = func;
    }

    static Rand<Integer> of(int seed) {
        return new Rand<Integer>(seed, x -> x);
    }

    static <R> Rand<R> of(int seed, Function<Integer, R> func) {
        return new Rand<>(seed, func);
    }

    public T get() {
        return this.func.apply(this.seed);
    }

    public Rand<T> next() {
        return new Rand<T>(new Random(this.seed).nextInt(Integer.MAX_VALUE),
                           this.func);
    }

    public Stream<T> stream() {
        return Stream.iterate(this, rand -> rand.next())
            .map(random -> random.get());
    }

    static <R> Stream<R> randRange(int seed, Function<Integer, R> pred) {
        return Rand.of(seed).stream().map(x -> pred.apply(x));
    }

    public <R> Rand<R> map(Function<? super T, ? extends R> mapper) {
        Function<Integer, R> newFunc = this.func.andThen(mapper);
        return Rand.of(this.seed, newFunc);
    }

    public <R> Rand<R> flatMap(Function<? super T, Rand<R>> mapper) {
        Function<Integer, R> newFunc = seed -> {
            T t = this.func.apply(seed);
            Rand<R> randR = mapper.apply(t);
            return randR.get();
        };
        return Rand.of(this.seed, newFunc);
    }

    @Override
    public String toString() {
        return "Rand";
    }
}
