import java.util.Optional;

public class WaitEvent extends Event {
    private final Server server;
    private final boolean isFirstWait;
    private static final int THIRD = 3;
    private static final double WAIT_INCREMENT = 0.00001;

    public WaitEvent(Customer customer, double eventTime, Server server,
                     boolean isFirstWait) {
        super(eventTime, customer, THIRD);
        this.server = server;
        this.isFirstWait = isFirstWait;
    }

    public WaitEvent(Customer customer, double eventTime, Server server) {
        this(customer, eventTime, server, true);
    }

    @Override
    public Pair<Event, Shop> next(Shop shop) {
        Server currentServer =
            shop.findServer(server.getId())
                .orElseThrow(()
                                 -> new IllegalStateException(
                                     "Server not found in shop"));

        if (shop.isServerAvailable(currentServer)) {
            Server updatedServer =
                currentServer.startServing(customer).removeNextCustomer();
            Shop updatedShop = shop.update(updatedServer);

            ServeEvent serveEvent =
                new ServeEvent(this.customer, this.eventTime, updatedServer);
            return new Pair<>(serveEvent, updatedShop);
        } else {
            double newEventTime = this.eventTime + WAIT_INCREMENT;

            WaitEvent updatedWaitEvent = new WaitEvent(
                this.customer, newEventTime, currentServer, false);

            Server updatedServer =
                currentServer.addCustomerToQueue(this.customer);

            Shop updatedShop = shop.update(updatedServer)
                                   .recordWaitingTime(WAIT_INCREMENT);
            return new Pair<>(updatedWaitEvent, updatedShop);
        }
    }

    @Override
    public String toString() {
        return isFirstWait
            ? String.format("%.3f %s waits at %s", this.eventTime,
                            this.customer, this.server)
            : "";
    }
}
