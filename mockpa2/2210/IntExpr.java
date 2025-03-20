class IntExpr extends AbstractIntExpr {
    private IntExpr(Integer value) {
        super(Expr.of(value));
    }

    private IntExpr(Expr<Integer> expr) {
        super(expr);
    }

    static IntExpr of(int n) {
        return new IntExpr(n);
    }

    public IntExpr add(int n) {
        return new IntExpr(this.op(addition, n));
    }

    public IntExpr sub(int n) {
        return this.add(-n);
    }

    public IntExpr div(int n) {
        if (this.evaluate() < n) {
            return new IntExpr(0);
        } else if (n != 0) {
            return new IntExpr(this.evaluate() / n);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public IntExpr exp(int n) {
        return new IntExpr(this.op(Operator.<Integer>of((x, y) -> {
            int z = 1;
            for (int i = 0; i < y; i++) {
                z *= x;
            }
            return z;
        }, 2), n));
    }

    public IntExpr mul(int n) {
        return new IntExpr(this.op(multiplication, n));
    }
}
