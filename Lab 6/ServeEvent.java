import java.util.Optional;
import java.util.function.Supplier;

class ServeEvent extends Event {
    private final Server server;

    ServeEvent(Customer customer, double eventTime, Server server) {
        super(customer, eventTime);
        this.server = server;
    }

    public Pair<Optional<Event>, Shop> next(Shop shop) {
        Supplier<Double> serviceTimeSupplier = shop.getServiceTimeSupplier();
        double doneEventTime = eventTime + serviceTimeSupplier.get();

        Server updatedServer = server.serve(eventTime, serviceTimeSupplier.get());
        Shop updatedShop = shop.update(updatedServer);

        return new Pair<Optional<Event>, Shop>(Optional.<Event>of(
                new DoneEvent(customer, doneEventTime)), shop);
    }

    public String toString() {
        String eventTime = String.format("%.3f", this.eventTime);
        return eventTime + " " + customer + " serves by " + server;
    }

    @Override
    public int addServed() {
        return 1;
    }

    @Override
    public boolean isServe() {
        return true;
    }
}