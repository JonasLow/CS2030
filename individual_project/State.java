import java.util.Optional;
import java.util.stream.Stream;

public class State {
    private final PQ<Event> eventQueue;
    private final Shop shop;
    private final String event;
    private final boolean processed;

    State(Shop shop, Customer customer) {
        this.eventQueue = new PQ<>();
        this.shop = shop;
        this.event = customer + " arrives\n";
        this.processed = false;
    }

    State(Shop shop) {
        this.eventQueue = new PQ<>();
        this.shop = shop;
        this.event = "";
        this.processed = false;
    }

    State(PQ<Event> eventQueue, Shop shop) {
        this.eventQueue = eventQueue;
        this.shop = shop;
        this.event = "";
        this.processed = false;
    }

    private State(PQ<Event> eventQueue, Shop shop, String event,
                  boolean processed) {
        this.eventQueue = eventQueue;
        this.shop = shop;
        this.event = event;
        this.processed = processed;
    }

    public Shop getShop() {
        return this.shop;
    }

    public boolean isEmpty() {
        return this.eventQueue.poll().t().map(event -> false).orElse(true) &&
            this.processed;
    }

    public State next() {
        Pair<Optional<Event>, PQ<Event>> pollResult = this.eventQueue.poll();
        PQ<Event> updatedQueue = pollResult.u();

        return pollResult.t()
            .map(nextEvent -> {
                Pair<Event, Shop> nextResult = nextEvent.next(this.shop);
                Event resultingEvent = nextResult.t();
                Shop updatedShop = nextResult.u();

                String eventString = nextEvent.toString().isBlank()
                                         ? this.event
                                         : this.event + "\n" + nextEvent;

                if (nextEvent.getPriority() == 1) {
                    return new State(updatedQueue, updatedShop, eventString,
                                     false);
                }

                return new State(updatedQueue.add(resultingEvent), updatedShop,
                                 eventString, false);
            })
            .orElse(new State(updatedQueue, this.shop, this.event, true));
    }

    @Override
    public String toString() {
        return this.event.trim();
    }
}
