import java.util.function.Function;

class StateM<T, S> extends AbstractStateM<T, S> {
    private StateM(Function<S, Pair<T, S>> f) {
        super(f);
    }

    private StateM(T value) {
        super(value);
    }

    static <T, S> StateM<T, S> unit(T value) {
        return new StateM<>(value);
    }

    static <T> StateM<T, T> get() {
        return new StateM<>(x -> new Pair<T, T>(x, x));
    }

    static <S> StateM<Nothing, S> put(S value) {
        return new StateM<>(x -> new Pair<Nothing, S>(new Nothing(), value));
    }

    static StateM<Nothing, Integer> inc() {
        return new StateM<>(s -> new Pair<Nothing, Integer>(new Nothing(), s + 1));
    }

    static StateM<Integer, Integer> fib(int n) {
        return inc().flatMap(nothing -> {
            if (n <= 1) {
                return unit(n);
            } else {
                return fib(n - 1).flatMap(x ->
                        fib(n - 2).flatMap(y ->
                                unit(x + y)));
            }
        });
    }

    public <R> StateM<R, S> flatMap(Function<T, StateM<R, S>> mapper) {
        return new StateM<>(seed -> {
            Pair<T, S> result = this.accept(seed);
            StateM<R, S> newState = mapper.apply(result.first());
            return newState.accept(result.second());
        });
    }

    @Override
    public String toString() {
        return "StateM";
    }
}
