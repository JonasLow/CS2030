import java.util.Optional;

abstract class Event implements Comparable<Event> {
    protected final double eventTime;
    protected final Customer customer;

    Event(Customer customer, double eventTime) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    protected double getTime() {
        return this.eventTime;
    }

    public boolean isDone() {
        return false;
    }

    public boolean isServe() {
        return false;
    }

    public boolean isArrive() {
        return false;
    }

    public abstract String toString();

    public abstract Pair<Optional<Event>, Shop> next(Shop shop);

    public int compareTo(Event other) {
        if (Double.compare(this.eventTime, other.eventTime) != 0) {
            return Double.compare(this.eventTime, other.eventTime);
        } else if (this.isArrive() && other.isServe()) {
            return 1;
        } else if (this.isServe() && other.isArrive()) {
            return -1;
        } else if (this.isDone() && !other.isDone()) {
            return -1;
        } else if (!this.isDone() && other.isDone()) {
            return 1;
        } else {
            return this.customer.compare(other.customer);
        }
    }

    public Optional<Event> process(Shop shop) {
        Pair<Optional<Event>, Shop> nextPair = this.next(shop);

        return nextPair.t(); // returnt the next event
    }

    public double getWaitTime() {
        return 0;
    }

    public int addServed() {
        return 0;
    }

    public int addLeft() {
        return 0;
    }

    public int addDone() {
        return 0;
    }
}