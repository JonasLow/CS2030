import java.util.Optional;

class Expr<T> {
    private final T value;
    private final Optional<Operator<T>> operator;
    private final Optional<Expr<T>> left;
    private final Optional<Expr<T>> right;

    private Expr(T value) {
        this.value = value;
        this.operator = Optional.empty();
        this.left = Optional.empty();
        this.right = Optional.empty();
    }

    private Expr(T value, Optional<Operator<T>> operator,
                 Optional<Expr<T>> left, Optional<Expr<T>> right) {
        this.value = value;
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public Expr(Expr<T> expr) {
        this.value = expr.value;
        this.operator = expr.operator;
        this.left = expr.left;
        this.right = expr.right;
    }

    static <T> Expr<T> of(T value) {
        return new Expr<>(value);
    }

    public Expr<T> op(Operator<T> otherOperator, T otherValue) {
        Expr<T> otherExpr = Expr.of(otherValue);

        return this.operator
            .map(current -> {
                if (otherOperator.compareTo(current) > 0) {
                    return new Expr<>(this.value, Optional.of(otherOperator),
                                      Optional.of(this),
                                      Optional.of(otherExpr));
                } else {
                    return new Expr<>(
                        this.value, Optional.of(current), this.left,
                        Optional.of(new Expr<>(
                            otherExpr.value, Optional.of(otherOperator),
                            this.right, Optional.of(otherExpr))));
                }
            })
            .orElse(new Expr<>(this.value, Optional.of(otherOperator),
                               Optional.of(this), Optional.of(otherExpr)));
    }

    public Expr<T> op(Operator<T> operator, Expr<T> expr) {
        return new Expr<>(this.value, Optional.of(operator), Optional.of(this),
                          Optional.of(expr));
    }

    public T evaluate() {
        return this.operator
            .map(op
                 -> op.apply(left.orElseThrow().evaluate(),
                             right.orElseThrow().evaluate()))
            .orElse(value);
    }

    @Override
    public String toString() {
        return this.operator
            .map(op
                 -> "(" +
                        op.apply(this.left.orElseThrow().evaluate(),
                                 right.orElseThrow().evaluate()) +
                        ")")
            .orElse("(" + this.value + ")");
    }
}
