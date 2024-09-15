import java.util.Optional;

public class ArriveEvent extends Event {
    ArriveEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }

    @Override
    public Pair<Event, Shop> next(Shop nextShop) {
        return nextShop.findServer(this.customer)
            .map(server -> {
                if (server.canServe(this.customer)) {
                    Server updatedServer = server.serve(customer);
                    return new Pair<Event, Shop>(
                        new ServeEvent(this.customer, updatedServer,
                                       this.eventTime),
                        nextShop.update(updatedServer));
                } else {
                    return new Pair<Event, Shop>(
                        new LeaveEvent(this.customer, this.eventTime),
                        nextShop);
                }
            })
            .orElseGet(() -> {
                return new Pair<Event, Shop>(
                    new LeaveEvent(this.customer, this.eventTime), nextShop);
            });
    }

    @Override
    public String toString() {
        return String.format("%.1f %s arrives", this.eventTime, this.customer);
    }
}
