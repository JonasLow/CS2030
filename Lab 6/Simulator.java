import java.util.List;
import java.util.function.Supplier;

class Simulator {
    private final int numServers;
    private final int remainingCapacity; // qMax
    private final Supplier<Double> serviceTime;
    private final int numOfCustomers;
    private final List<Pair<Integer, Double>> arrivals;
    private final PQ<Event> pq;
    private final Shop shop;

    Simulator(int numServers, int remainingCapacity, Supplier<Double> serviceTime,
            int numOfCustomers, List<Pair<Integer, Double>> arrivals) {

        this.numServers = numServers;
        this.remainingCapacity = remainingCapacity;
        this.arrivals = arrivals;
        this.pq = new PQ<Event>();
        this.shop = new Shop(numServers);
        this.serviceTime = serviceTime;
        this.numOfCustomers = numOfCustomers;

        for (Pair<Integer, Double> arrival : arrivals) {
            int customerID = arrival.t();
            double arrivalTime = arrival.u();
            pq.add(new ArriveEvent(new Customer(customerID, arrivalTime, serviceTime),
                    arrivalTime));
        }
    }

    String run() {
        State currState = new State(pq, shop);
        while (!currState.isEmpty()) {
            currState = currState.next();
        }

        double totalWaitTime = currState.getTotalWaitTime();
        int numDone = currState.getNumDone();
        double avgWaitTime = currState.getNumDone() == 0 ? 0 : totalWaitTime / numDone;
        int numServed = currState.getNumServed();
        int numLeft = currState.getNumLeft();
        String stats = String.format("[%.3f %d %d]", avgWaitTime, numServed, numLeft);

        return currState.toString().trim() + "\n" + stats;
    }
}
