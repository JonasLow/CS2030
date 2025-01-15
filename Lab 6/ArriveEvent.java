import java.util.Optional;
import java.util.function.Supplier;

class ArriveEvent extends Event {

    ArriveEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    public String toString() {
        String eventTime = String.format("%.3f", this.eventTime);
        return eventTime + " " + customer + " arrives";
    }

    public Pair<Optional<Event>, Shop> next(Shop shop) {

        Optional<Server> availServer = shop.findServer(this.customer);

        return availServer
                // server is available; ArriveEvent -> instantly ServeEvent
                .map(server -> {
                    return new Pair<>(Optional.<Event>of(
                            new ServeEvent(customer, eventTime, server)), shop);
                })
                // no server available
                .orElseGet(() -> {
                    Optional<Server> availQueue = shop.findQueue();

                    return availQueue
                            .map(server -> {
                                Shop updatedShop = shop.update(server.enterQueue());
                                return new Pair<>(Optional.<Event>of(
                                        new WaitEvent(customer, eventTime, server)), updatedShop);
                            })
                            .orElseGet(() -> {
                                return new Pair<>(Optional.<Event>of(
                                        new LeaveEvent(customer, eventTime)), shop);
                            });
                });

    }

    @Override
    public boolean isArrive() {
        return true;
    }
}