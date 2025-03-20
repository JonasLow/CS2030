class Reject extends Transaction {
    private final int billing;

    Reject(Transaction prev, Seating plan, int billing) {
        super("REJECTED:", plan, String.format("%s\nnot billed %d", prev.getLog(), billing));
        this.billing = billing;
    }
}