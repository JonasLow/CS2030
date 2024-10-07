import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Maybe<T> {
    private final T value;

    private Maybe(T value) {
        this.value = value;
    }

    static <U> Maybe<U> of(U value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return new Maybe<U>(value);
    }

    static <T> Maybe<T> empty() {
        return new Maybe<T>(null);
    }

    private boolean isEmpty() {
        return this.value == null;
    }

    private T get() {
        return this.value;
    }

    //ifPresent
    void ifPresent(Consumer<? super T> action) {
        if (!this.isEmpty()) {
            action.accept(this.value);
        }
    }

    //ifPresentOrElse
    void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (!this.isEmpty()) {
            action.accept(this.value);
        } else {
            emptyAction.run();
        }
    }

    Maybe<T> filter(Predicate<? super T> pred) {
        if (this.isEmpty()) {
            return this;
        }
        if (!this.isEmpty() && pred.test(this.get())) {
            return this;
        }
        return Maybe.<T>empty();
    }

    <U> Maybe<U> map(Function<? super T, ? extends U> mapper) {
        if (this.isEmpty()) {
            return Maybe.<U>empty();
        }

        U u = mapper.apply(this.get());
        return Maybe.<U>of(u);
    }

    //flatMap
    <U> Maybe<U> flatMap(
        Function<? super T, ? extends Maybe<? extends U>> mapper) {

        if (this.isEmpty()) {
            return Maybe.<U>empty();
        }

        Maybe<? extends U> result = mapper.apply(this.value);
        return Maybe.<U>of(result.get());
    }

    T orElse(T other) {
        if (this.isEmpty()) {
            return other;
        }
        return this.get();
    }

    T orElseGet(Supplier<? extends T> supplier) {
        if (this.isEmpty()) {
            return supplier.get();
        }
        return this.get();
    }

    Maybe<T> or(Supplier<? extends Maybe<? extends T>> supplier) {
        if (!this.isEmpty()) {
            return this;
        }
        return supplier.get().map(x -> x);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Maybe<?> other) {
            if (this.isEmpty() && other.isEmpty()) {
                return true;
            }
            if (this.isEmpty() || other.isEmpty()) {
                return false;
            }
            return this.get().equals(other.get());
        }
        return false;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "Maybe.empty";
        }
        return "Maybe[" + this.get() + "]";
    }
}
