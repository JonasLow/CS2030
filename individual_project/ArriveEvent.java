import java.util.Optional;

public class ArriveEvent extends Event {
    private static final int FOURTH = 4;

    public ArriveEvent(Customer customer, double eventTime) {
        super(eventTime, customer, FOURTH);
    }

    public Pair<Event, Shop> next(Shop shop) {
        return shop.findIdleServer()
            .<Pair<Event, Shop>>map(server -> {
                ServeEvent serveEvent =
                    new ServeEvent(this.customer, this.eventTime, server);
                return new Pair<>(serveEvent, shop);
            })
            .orElseGet(
                ()
                    -> shop.findQueueableServer()
                           .<Pair<Event, Shop>>map(server -> {
                               WaitEvent waitEvent = new WaitEvent(
                                   this.customer, this.eventTime, server);
                               return new Pair<>(
                                   waitEvent,
                                   shop.queueCustomer(server, this.customer));
                           })
                           .orElseGet(() -> {
                               LeaveEvent leaveEvent = new LeaveEvent(
                                   this.customer, this.eventTime);
                               return new Pair<>(leaveEvent, shop);
                           }));
    }

    public String toString() {
        return String.format("%.3f %s arrives", this.eventTime, this.customer);
    }
}
