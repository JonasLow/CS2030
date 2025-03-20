import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Simulator {
    private final PQ<Event> eventQueue;
    private final Shop shop;
    private final int numOfCustomers;

    public Simulator(int numOfServers, int maxQueueSize,
                     Supplier<Double> serviceTimeSupplier, int numOfCustomers,
                     List<Pair<Integer, Double>> arrivals) {
        this.numOfCustomers = numOfCustomers;

        this.shop = new Shop(numOfServers, serviceTimeSupplier, maxQueueSize);

        PQ<Event> initialQueue = new PQ<>();
        for (Pair<Integer, Double> pair : arrivals) {
            int id = pair.t();
            double time = pair.u();
            Customer customer = new Customer(id, time);
            initialQueue = initialQueue.add(new ArriveEvent(customer, time));
        }
        this.eventQueue = initialQueue;
    }

    public String run() {
        String output = "";
        State currentState = new State(eventQueue, shop);
        State prevState = currentState;

        output =
            Stream
                .iterate(currentState,
                         state
                         -> !state.isEmpty(),
                         state -> state.next())
                .map(x
                     -> x + "\n" +
                            String.format("[%.3f %d %d]",
                                          x.getShop().getAverageWaitingTime(),
                                          x.getShop().getServedCustomers(),
                                          x.getShop().getLeftCustomers()) +
                            '\n')
                .reduce("", (x, y) -> y);

        return output.trim();
    }
}
