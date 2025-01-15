class Pair<T,U> {
    private final T t;
    private final U u;

    private Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    static <T,U> Pair<T,U> of(T t, U u) {
        return new Pair<T,U>(t, u);
    }

    T t() {
        return this.t;
    }

    U u() {
        return this.u;
    }   
}
