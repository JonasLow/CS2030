import java.util.Optional;

public class State {
    private final Shop shop;
    private final Optional<Customer> customer;
    private final String event;

    State(Shop shop, Customer customer) {
        this.shop = shop;
        this.customer = Optional.of(customer);
        this.event = customer + " arrives";
    }

    State(Shop shop) {
        this.shop = shop;
        this.customer = Optional.empty();
        this.event = "";
    }

    private State(Shop shop, Customer customer, String event) {
        this.shop = shop;
        this.customer = Optional.of(customer);
        this.event = event;
    }

    public State next(State state) {
        Customer nextCustomer = state.customer.get();
        Optional<Server> nextServer = this.shop.findServer(nextCustomer);
        String updatedEvent = this.event.isEmpty() ? state.event : this.event;
        updatedEvent += "\n" + nextCustomer + " arrives";

        if (nextServer.isPresent()) {
            Server server = nextServer.get().serve(nextCustomer,
                                                   nextCustomer.serveTill(0.0));
            Shop updatedShop = this.shop.update(server);
            updatedEvent += "\n" + nextCustomer + " served by " + server;
            return new State(updatedShop, nextCustomer, updatedEvent);
        } else {
            updatedEvent += "\n" + nextCustomer + " leaves";
            return new State(this.shop, nextCustomer, updatedEvent);
        }
    }

    public String toString() {
        return this.event.isEmpty() ? "" : this.event;
    }
}
