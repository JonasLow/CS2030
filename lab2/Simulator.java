import java.util.List;

class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final List<Pair<Integer,Double>> arrivals;
    private final double serviceTime;

    Simulator(int numOfServers, int numOfCustomers, 
            List<Pair<Integer,Double>> arrivals, double serviceTime) {
        this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals;
        this.serviceTime = serviceTime;
    }

    State run() {
        Shop shop = new Shop(numOfServers);
        return arrivals.stream()
            .map(x -> new State(shop, new Customer(x.t(), x.u())))
            .reduce(new State(shop),
                    (x, y) -> x.next(y));
    }
}

