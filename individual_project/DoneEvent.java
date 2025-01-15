public class DoneEvent extends Event {
    private final Server server;
    private static final int FIRST = 1;

    public DoneEvent(Customer customer, double eventTime, Server server) {
        super(eventTime, customer, FIRST);
        this.server = server;
    }

    public Pair<Event, Shop> next(Shop shop) {
        Shop updatedShop = shop.finishService(server);
        return new Pair<>(this, updatedShop);
    }

    public String toString() {
        return String.format("%.3f %s done", this.eventTime,
                             this.customer);
    }
}
