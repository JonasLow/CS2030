public abstract class Event implements Comparable<Event> {
    protected final double eventTime;
    protected final Customer customer;
    protected final int priority;
    private static final double HUNDRED_THOUSAND = 100000.0;

    public Event(double eventTime, Customer customer, int priority) {
        this.eventTime = eventTime;
        this.customer = customer;
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(Event other) {
        double roundedThisTime = Math.round(this.eventTime * HUNDRED_THOUSAND) / HUNDRED_THOUSAND;
        double roundedOtherTime = Math.round(other.eventTime * HUNDRED_THOUSAND) / HUNDRED_THOUSAND;
        int timeComparison = Double.compare(roundedThisTime, roundedOtherTime);
        if (timeComparison != 0) {
            return timeComparison;
        }
        int priorityComp = Integer.compare(this.priority, other.priority);
        if (priorityComp != 0) {
            return priorityComp;
        } else {
            return this.customer.compareTo(other.customer);
        }
    }

    public abstract Pair<Event, Shop> next(Shop shop);
}
