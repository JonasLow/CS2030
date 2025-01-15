import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Shop {
    private final List<Server> servers;
    private final Supplier<Double> serviceTimeSupplier;
    private final double totalWaitingTime;
    private final int servedCustomers;
    private final int leftCustomers;

    public Shop(int numOfServers, Supplier<Double> serviceTimeSupplier,
                int maxQueueSize) {
        this.servers = Stream.iterate(1, id -> id + 1)
                           .limit(numOfServers)
                           .map(id -> new Server(id, maxQueueSize))
                           .toList();
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.totalWaitingTime = 0.0;
        this.servedCustomers = 0;
        this.leftCustomers = 0;
    }

    private Shop(List<Server> servers, Supplier<Double> serviceTimeSupplier,
                 double totalWaitingTime, int servedCustomers,
                 int leftCustomers) {
        this.servers = servers;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.totalWaitingTime = totalWaitingTime;
        this.servedCustomers = servedCustomers;
        this.leftCustomers = leftCustomers;
    }

    public Optional<Server> findIdleServer() {
        return servers.stream().filter(Server::isIdle).findFirst();
    }

    public Optional<Server> findQueueableServer() {
        return servers.stream().filter(Server::canQueue).findFirst();
    }

    public Optional<Server> findServer(Customer customer) {
        return servers.stream()
            .filter(server
                    -> server.isServingCustomer(customer) ||
                           server.isWaitingForCustomer(customer))
            .findFirst();
    }

    public Optional<Server> findServer(int id) {
        return servers.stream()
            .filter(server
                    -> server.getId() == id)
            .findFirst();
    }

    private List<Server> updateServers(Server updatedServer) {
        return servers.stream()
            .map(server
                 -> server.getId() == updatedServer.getId() ? updatedServer
                                                            : server)
            .toList();
    }

    public Shop queueCustomer(Server server, Customer customer) {
        Server updatedServer = server.addCustomerToQueue(customer);
        List<Server> newServers = updateServers(updatedServer);
        return new Shop(newServers, this.serviceTimeSupplier, totalWaitingTime,
                        servedCustomers, leftCustomers);
    }

    public Shop serveCustomer(Server server, Customer customer,
                              double additionalWaitingTime) {
        Server updatedServer = server.startServing(customer);
        List<Server> newServers = updateServers(updatedServer);
        double newTotalWaitingTime = totalWaitingTime + additionalWaitingTime;
        return new Shop(newServers, this.serviceTimeSupplier,
                        newTotalWaitingTime, servedCustomers + 1, leftCustomers);
    }

    public Shop finishService(Server server) {
        Server updatedServer = findServer(server.getId()).orElse(server);
        updatedServer = updatedServer.stopServing();
        List<Server> newServers = updateServers(updatedServer);
        Shop updatedShop =
            new Shop(newServers, serviceTimeSupplier, totalWaitingTime,
                     servedCustomers, leftCustomers);
        return updatedShop;
    }

    public Shop update(Server server) {
        List<Server> newServers = updateServers(server);
        return new Shop(newServers, serviceTimeSupplier, totalWaitingTime,
                        servedCustomers, leftCustomers);
    }

    public boolean isServerAvailable(Server targetServer) {
        return servers.stream()
            .filter(server -> server.getId() == targetServer.getId())
            .findFirst()
            .map(Server::isIdle)
            .orElse(false);
    }

    public Shop recordLeftCustomer() {
        return new Shop(this.servers, this.serviceTimeSupplier,
                        this.totalWaitingTime, this.servedCustomers,
                        this.leftCustomers + 1);
    }

    public Shop recordServedCustomer() {
        return new Shop(this.servers, this.serviceTimeSupplier,
                        this.totalWaitingTime, this.servedCustomers + 1,
                        this.leftCustomers);
    }

    public Shop recordWaitingTime(double waitedTime) {
        return new Shop(this.servers, this.serviceTimeSupplier,
                        this.totalWaitingTime + waitedTime,
                        this.servedCustomers, this.leftCustomers);
    }

    public double getAverageWaitingTime() {
        return this.servedCustomers == 0
            ? 0
            : this.totalWaitingTime / this.servedCustomers;
    }

    public int getServedCustomers() {
        return this.servedCustomers;
    }

    public int getLeftCustomers() {
        return this.leftCustomers;
    }

    public Supplier<Double> getServiceTimeSupplier() {
        return this.serviceTimeSupplier;
    }

    public String toString() {
        return this.servers.toString() + " : " +
            this.servers.stream().map(x -> x.getCustomer()).toList().toString();
    }
}
