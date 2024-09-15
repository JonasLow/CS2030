public class DoneEvent extends Event {
    DoneEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }

    @Override
    public Pair<Event, Shop> next(Shop shop) {
        return new Pair<>(this, shop);
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%.1f %s done", this.eventTime, this.customer);
    }
}
