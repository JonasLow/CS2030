public class LeaveEvent extends Event {
    private static final int FIRST = 1;

    public LeaveEvent(Customer customer, double eventTime) {
        super(eventTime, customer, FIRST);
    }

    public Pair<Event, Shop> next(Shop shop) {
        return new Pair<>(this, shop.recordLeftCustomer());
    }

    public String toString() {
        return String.format("%.3f %s leaves", this.eventTime, this.customer);
    }
}
