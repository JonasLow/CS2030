import java.util.function.BinaryOperator;

class Operator<T> implements Comparable<Operator> {
    private final BinaryOperator<T> binOp;
    private final int precedence;

    private Operator(BinaryOperator<T> binOp, int precedence) {
        this.binOp = binOp;
        this.precedence = precedence;
    }

    static <T> Operator<T> of(BinaryOperator<T> binOp, int precedence) {
        return new Operator<>(binOp, precedence);
    }

    public T apply(T a, T b) {
        return this.binOp.apply(a, b);
    }

    @Override
    public int compareTo(Operator other) {
        return Integer.compare(this.precedence, other.precedence);
    }

    @Override
    public String toString() {
        return "Operator of precedence " + this.precedence;
    }
}
