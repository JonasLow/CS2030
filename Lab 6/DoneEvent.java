import java.util.Optional;

class DoneEvent extends Event {
    DoneEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    public String toString() {
        String eventTime = String.format("%.3f", this.eventTime);
        return eventTime + " " + customer + " done";
    }

    public Pair<Optional<Event>, Shop> next(Shop shop) {
        return new Pair<>(Optional.empty(), shop);
    }

    public boolean isDone() {
        return true;
    }

    @Override
    public int addDone() {
        return 1;
    }
}