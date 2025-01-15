import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

class Functle<T extends Movable<T>> {
    private final Consumer<T> forwardMovement;
    private final Consumer<T> backwardMovement;

    private Functle(Consumer<T> f, Consumer<T> b) {
        this.forwardMovement = f;
        this.backwardMovement = b;
    }

    static <T extends Movable<T>> Functle<T> of() {
        return new Functle<T>(x -> {}, x -> {});
    }

    public Functle<T> forward(int step) {
        return new Functle<T>(
            this.forwardMovement.andThen(x -> x.moveForward(step)), y -> {
                y.moveForward(-step);
                this.backwardMovement.accept(y);
            });
    }

    public Functle<T> left(int step) {
        return new Functle<T>(
            this.forwardMovement.andThen(x -> x.turnLeft(step)), y -> {
                y.turnLeft(-step);
                this.backwardMovement.accept(y);
            });
    }

    public Functle<T> backward(int step) {
        return this.forward(-step);
    }

    public Functle<T> right(int step) {
        return this.left(-step);
    }

    public Functle<T> reverse() {
        return new Functle<T>(
            this.forwardMovement.andThen(this.backwardMovement), x -> {});
    }

    public Functle<T> andThen(Functle<T> other) {
        return new Functle<T>(
            this.forwardMovement.andThen(other.forwardMovement),
            other.backwardMovement.andThen(this.backwardMovement));
    }

    public Functle<T> loop(int n) {
        if (n == 0) {
            return Functle.<T>of();
        }
        Functle<T> newThis = this;

        for (int i = 1; i < n; i++) {
            newThis = newThis.andThen(this);
        }
        return newThis;
    }

    public Functle<T> comeHome() {
        return new Functle<>(
            movable -> {
                Stream.iterate(1, x -> x + 1)
                    .takeWhile(x -> {
                        Functle<T> result = this.loop(x);
    
                        Supplier<T> supp = () -> {
                            result.forwardMovement.accept(movable);
                            return movable;
                        };
    
                        if (movable.equals(supp)) {
                            return false;
                        }
    
                        result.backwardMovement.accept(movable);
                        return true;
                    })
                    .map(x -> this.loop(x).forwardMovement)
                    .reduce(this.forwardMovement, (z, k) -> z.andThen(k));
            },
            movable -> {}
        );
    }
    

    public void run(T mov) {
        this.forwardMovement.accept(mov);
    }

    @Override
    public String toString() {
        return "Functle";
    }
}
