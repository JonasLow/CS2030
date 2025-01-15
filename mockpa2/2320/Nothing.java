class Nothing {
    static Nothing nothing() {
        return new Nothing();
    }

    @Override
    public String toString() {
        return "-";
    }
}
