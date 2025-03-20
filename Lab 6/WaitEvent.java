import java.util.Optional;

class WaitEvent extends Event {
    private final Server server;

    WaitEvent(Customer customer, Double eventTime, Server server) {
        super(customer, eventTime);
        this.server = server;
    }

    public String toString() {
        String eventTime = String.format("%.3f", this.eventTime);
        return eventTime + " " + customer + " wait at " + server;
    }

    @Override
    public Pair<Optional<Event>, Shop> next(Shop shop) {
        Double availableTime = server.getAvailableTime();
        Server servingServer = server.exitQueue();
        Shop updatedShop = shop.update(servingServer);
        return new Pair<Optional<Event>, Shop>(Optional.<Event>of(
                new ServeEvent(customer, availableTime, servingServer)), updatedShop);
    }

    private Server getServer() {
        return this.server;
    }

    public double getWaitTime() {
        return server.getAvailableTime() - this.eventTime;
    }

    @Override
    public Optional<Event> process(Shop shop) {
        // waiter finally available
        if (this.getServer().getAvailableTime() <= this.getTime()) {
            Pair<Optional<Event>, Shop> nextPair = this.next(shop);
            return nextPair.t();

            // else server still not available; add WaitEvent back
        } else {
            return Optional.<Event>of(this);
        }
    }
}