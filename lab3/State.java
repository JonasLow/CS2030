import java.util.Optional;
import java.util.stream.Stream;

public class State {
    private final PQ<Event> eventQueue;
    private final Shop shop;
    private final Optional<Customer> customer;
    private final String event;
    private final boolean processed;

    State(Shop shop, Customer customer) {
        this.eventQueue = new PQ<>();
        this.shop = shop;
        this.customer = Optional.of(customer);
        this.event = customer + " arrives\n";
        this.processed = false;
    }

    State(Shop shop) {
        this.eventQueue = new PQ<>();
        this.shop = shop;
        this.customer = Optional.empty();
        this.event = "";
        this.processed = false;
    }

    private State(Shop shop, Customer customer, String event) {
        this.eventQueue = new PQ<>();
        this.shop = shop;
        this.customer = Optional.of(customer);
        this.event = event;
        this.processed = false;
    }

    State(PQ<Event> eventQueue, Shop shop) {
        this.eventQueue = eventQueue;
        this.shop = shop;
        this.customer = Optional.empty();
        this.event = "";
        this.processed = false;
    }

    private State(PQ<Event> eventQueue, Shop shop, String event,
                  boolean processed) {
        this.eventQueue = eventQueue;
        this.shop = shop;
        this.customer = Optional.empty();
        this.event = event;
        this.processed = processed;
    }

    public boolean isEmpty() {
        return this.eventQueue.isEmpty() && this.processed;
    }

    public State next() {
        Pair<Optional<Event>, PQ<Event>> pollResult = this.eventQueue.poll();
        Optional<Event> nextEventOpt = pollResult.t();
        PQ<Event> updatedQueue = pollResult.u();

        return nextEventOpt
            .map(nextEvent -> {
                Pair<Event, Shop> nextResult = nextEvent.next(this.shop);
                Event resultingEvent = nextResult.t();
                Shop updatedShop = nextResult.u();

                String newEvent;
                if (this.event == "") {
                    newEvent = this.event + nextEvent.toString();
                } else {
                    newEvent = this.event + "\n" + nextEvent.toString();
                }

                if (nextEvent.isDone() || nextEvent.isLeaving()) {
                    return new State(updatedQueue, updatedShop, newEvent,
                                     false);
                }

                return new State(updatedQueue.add(resultingEvent), updatedShop,
                                 newEvent, false);
            })
            .orElse(new State(updatedQueue, this.shop, this.event, true));
    }

    public State next(State state) {
        Customer nextCustomer = state.customer.orElseThrow();
        Optional<Server> nextServer = this.shop.findServer(nextCustomer);
        String updatedEvent = this.event.equals("") ? state.event : this.event;
        final String nextEvent;

        if (!updatedEvent.contains(nextCustomer + " arrives")) {
            nextEvent = updatedEvent + (nextCustomer + " arrives\n");
        } else {
            nextEvent = updatedEvent;
        }

        return nextServer
            .map(server -> {
                server.serve(nextCustomer, nextCustomer.serveTill(0));
                Shop updatedShop = this.shop.update(server);
                String finalEvent =
                    nextEvent + (nextCustomer + " served by " + server + "\n");
                return new State(updatedShop, nextCustomer, finalEvent);
            })
            .orElseGet(() -> {
                String finalEvent = nextEvent + (nextCustomer + " leaves\n");
                return new State(this.shop, nextCustomer, finalEvent);
            });
    }

    public String toString() {
        return this.event;
    }
}
