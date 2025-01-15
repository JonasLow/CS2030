import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

class Shop {
    private final List<Server> servers;
    private final Supplier<Double> serviceTimeSupplier;
    private final int remainingCapacity;

    Shop(int numServer) {
        this.servers = IntStream.rangeClosed(1, numServer)
                .mapToObj(n -> new Server(n, 1))
                .toList();
        this.serviceTimeSupplier = () -> 1.0;
        this.remainingCapacity = 1;
    }

    Shop(List<Server> servers) {
        this.servers = servers;
        this.serviceTimeSupplier = () -> 1.0;
        this.remainingCapacity = 1;
    }

    Shop(int numServer, Supplier<Double> serviceTimeSupplier, int remainingCapacity) {
        this.servers = IntStream.rangeClosed(1, numServer)
                .mapToObj(n -> new Server(n, 1))
                .toList();
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.remainingCapacity = remainingCapacity;
    }

    Supplier<Double> getServiceTimeSupplier() {
        return this.serviceTimeSupplier;
    }

    public String toString() {
        return servers.toString();
    }

    Optional<Server> findServer(Customer customer) {
        return this.servers.stream()
                .filter(server -> server.canServe(customer))
                .findFirst();
    }

    Shop update(Server newServer) {
        return new Shop(this.servers.stream()
                .map(server -> {
                    if (server.isSame(newServer)) {
                        return newServer;
                    } else {
                        return server;
                    }
                })
                .toList());
    }

    Optional<Server> findQueue() {
        return this.servers.stream()
                .filter(server -> server.getRemainingCapacity() > 0)
                .findFirst();
    }
}
