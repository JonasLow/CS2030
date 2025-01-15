public class ServeEvent extends Event {
    private final Server server;
    private static final int SECOND = 2;

    public ServeEvent(Customer customer, double eventTime, Server server) {
        super(eventTime, customer, SECOND);
        this.server = server;
    }

    public Pair<Event, Shop> next(Shop shop) {
        double serviceDuration = shop.getServiceTimeSupplier().get();
        double doneTime = this.eventTime + serviceDuration;

        Server updatedServer = server.startServing(customer);
        Shop updatedShop = shop.update(updatedServer);

        return new Pair<>(new DoneEvent(this.customer, doneTime, updatedServer),
                          updatedShop.recordServedCustomer());
    }

    public String toString() {
        return String.format("%.3f %s serves by %s", this.eventTime,
                             this.customer, this.server);
    }
}
