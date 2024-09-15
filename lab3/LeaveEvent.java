public class LeaveEvent extends Event {
    LeaveEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }

    public Pair<Event, Shop> next(Shop shop) {
        return new Pair<>(this, shop);
    }

    @Override
    public boolean isLeaving() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%.1f %s leaves", super.eventTime, super.customer);
    }
}
