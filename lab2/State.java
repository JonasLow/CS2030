import java.util.Optional;

public class State {
    private final Shop shop;
    private final Optional<Customer> customer;
    private final String event;

    State(Shop shop, Customer customer) {
        this.shop = shop;
        this.customer = Optional.of(customer);
        this.event = customer + " arrives"
                     + "\n";
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
        Customer nextCustomer = state.customer.orElseThrow();
        Optional<Server> nextServer = this.shop.findServer(nextCustomer);
        StringBuilder updatedEvent =
            new StringBuilder(this.event.equals("") ? state.event : this.event);

        if (!updatedEvent.toString().contains(nextCustomer + " arrives")) {
            updatedEvent.append(nextCustomer + " arrives"
                                + "\n");
        }

        return nextServer
            .map(server -> {
                server = server.serve(nextCustomer, 1.0);
                Shop updatedShop = this.shop.update(server);
                updatedEvent.append(nextCustomer + " served by " + server +
                                    "\n");
                return new State(updatedShop, nextCustomer,
                                 updatedEvent.toString());
            })
            .orElseGet(() -> {
                updatedEvent.append(nextCustomer + " leaves\n");
                return new State(this.shop, nextCustomer,
                                 updatedEvent.toString());
            });
    }

    public String toString() {
        if (this.event == "") {
            return "";
        } else {
            return this.event;
        }
    }
}
