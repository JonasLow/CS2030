import java.util.Optional;

class LeaveEvent extends Event {

    LeaveEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    public Pair<Optional<Event>, Shop> next(Shop shop) {
        return new Pair<>(Optional.empty(), shop);
    }

    public String toString() {
        return eventTime + " " + customer + " leaves";
    }

    @Override
    public int addLeft() {
        return 1;
    }
}