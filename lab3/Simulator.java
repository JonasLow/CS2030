import java.util.List;

class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final List<Pair<Integer, Pair<Double, Double>>> arrivals;

    Simulator(int numOfServers, int numOfCustomers,
              List<Pair<Integer, Pair<Double, Double>>> arrivals) {
        this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals;
    }

    State run() {
        Shop shop = new Shop(numOfServers);

        PQ<Event> eventQueue = new PQ<>();

        for (Pair<Integer, Pair<Double, Double>> arrival : arrivals) {
            int customerId = arrival.t();
            double arrivalTime = arrival.u().t();
            double serviceTime = arrival.u().u();
            Customer customer =
                new Customer(customerId, arrivalTime, serviceTime);
            eventQueue = eventQueue.add(new ArriveEvent(customer, arrivalTime));
        }

        State state = new State(eventQueue, shop);

        while (!state.isEmpty()) {
            state = state.next();
        }

        return state;
    }
}
