abstract class Event implements Comparable<Event> {
    protected final Customer customer;
    protected final double eventTime;

    Event(double eventTime, Customer customer) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    public abstract Pair<Event, Shop> next(Shop shop);

    public boolean isDone() {
        return false;
    }

    public boolean isLeaving() {
        return false;
    }

    @Override
    public int compareTo(Event other) {
        int timeDiff = Double.compare(this.eventTime, other.eventTime);

        if (timeDiff != 0) {
            return timeDiff;
        } else if (this.isDone() && !other.isDone()) {
            return -1;
        } else if (!this.isDone() && other.isDone()) {
            return 1;
        } else {
            return this.customer.compare(other.customer);
        }
    }
}
