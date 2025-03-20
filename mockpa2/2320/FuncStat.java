class FuncStat {
    private static final int ZERO = 0;
    private final int count;
    private final int maxDepth;
    private final int current;

    FuncStat() {
        this.count = ZERO;
        this.maxDepth = ZERO;
        this.current = ZERO;
    }

    FuncStat(int count, int maxDepth, int current) {
        this.count = count;
        this.maxDepth = maxDepth;
        this.current = current;
    }

    public FuncStat increment() {
        return new FuncStat(this.count + 1, this.current + 1, Math.max(maxDepth, this.current + 1));
    }

    public FuncStat decrement() {
        return new FuncStat(this.count, this.current - 1, maxDepth);
    }

    @Override
    public String toString() {
        return "[count=" + count + " maxDepth=" + maxDepth + "]";
    }
}