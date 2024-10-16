import java.util.function.Function;
import java.util.function.Supplier;

interface Try<T> {
    static <T> Try<T> of(Supplier<? extends T> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    T get();

    T orElse(T other);

    T orElseGet(Supplier<? extends T> supplier);

    <R> Try<R> map(Function<? super T, ? extends R> mapper);

    <R> Try<R> flatMap(
        Function<? super T, ? extends Try<? extends R>> flatmapper);

    boolean equals(Try<T> tryObj);

    private static <T> Try<T> success(T t) {
        return new Try<T>() {
            public T get() {
                return t;
            }

            public T orElse(T other) {
                return t;
            }

            public T orElseGet(Supplier<? extends T> s) {
                return t;
            }

            public <R> Try<R> map(Function<? super T, ? extends R> mapper) {
                return Try.of(() -> mapper.apply(t));
            }

            public <R> Try<R> flatMap(
                Function<? super T, ? extends Try<? extends R>> flatmapper) {
                return flatmapper.apply(t).map(Function.<R>identity());
            }

            public boolean equals(Try<T> obj) {
                if (this == obj) {
                    return true;
                } else {
                    try {
                        return t.equals(obj.get());
                    } catch (Exception e) {
                        return false;
                    }
                }
            }

            public String toString() {
                return "Success: " + t;
            }
        };
    }

    private static <T> Try<T> fail(Exception ex) {
        return new Try<T>() {
            public T get() {
                throw new RuntimeException(ex);
            }

            public T orElse(T other) {
                return other;
            }

            public T orElseGet(Supplier<? extends T> s) {
                return s.get();
            }

            public <R> Try<R> map(Function<? super T, ? extends R> mapper) {
                return Try.<R>fail(ex);
            }

            public <R> Try<R> flatMap(
                Function<? super T, ? extends Try<? extends R>> flatmapper) {
                return Try.<R>fail(ex);
            }

            public boolean equals(Try<T> obj) {
                if (this == obj) {
                    return true;
                } else {
                    try {
                        obj.get();
                        return false;
                    } catch (Exception e) {
                        return ex.getMessage().equals(
                            e.getCause().getMessage());
                    }
                }
            }

            public String toString() {
                return "Failure: " + ex;
            }
        };
    }
}
