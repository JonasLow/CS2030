public class ServeEvent extends Event {
    private final Server server;

    ServeEvent(Customer customer, Server server, double eventTime) {
        super(eventTime, customer);
        this.server = server;
    }

    @Override
    public Pair<Event, Shop> next(Shop shop) {
        double finishTime = this.eventTime + this.customer.serveTill(0);
        Server updatedServer = this.server.serve(this.customer, this.eventTime);
        Shop updatedShop = shop.update(updatedServer);
        return new Pair<>(new DoneEvent(this.customer, finishTime),
                          updatedShop);
    }

    @Override
    public String toString() {
        return String.format("%.1f %s serve by %s", this.eventTime,
                             this.customer, this.server);
    }
}
