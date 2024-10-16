public class Transaction {
    private final Seating plan;
    private final String type;
    private final String log;

    Transaction(final String type, Seating plan) {
        this.type = type;
        this.log = "";
        this.plan = plan;
    }

    Transaction(final String type, Seating plan, String log) {
        this.type = type;
        this.log = log;
        this.plan = plan;
    }

    public Transaction transact(Seating seating) {
        return this;
    }

    public Transaction transact(Transaction t) {
        return this;
    }

    public Seating getPlan() {
        return this.plan;
    }

    public String getLog() {
        return this.log;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%s", this.type, this.log, this.plan);
    }
}