import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Server {
    private final int id;
    private final boolean isServing;
    private final List<Customer> waitingCustomer;
    private final int maxQueueSize;

    public Server(int id, int maxQueueSize) {
        this.id = id;
        this.isServing = false;
        this.waitingCustomer = List.of();
        this.maxQueueSize = maxQueueSize;
    }

    public Server(int id, boolean isServing, List<Customer> waitingCustomer,
                  int maxQueueSize) {
        this.id = id;
        this.isServing = isServing;
        this.waitingCustomer = waitingCustomer;
        this.maxQueueSize = maxQueueSize;
    }

    public boolean isIdle() {
        return !isServing;
    }

    public Server startServing(Customer customer) {
        List<Customer> updatedQueue = waitingCustomer.stream()
                                                .filter(c -> c.getId() != customer.getId())
                                                .toList();
        return new Server(this.id, true, updatedQueue,
                          this.maxQueueSize);
    }

    public Server stopServing() {
        return new Server(this.id, false, this.waitingCustomer,
                          this.maxQueueSize);
    }

    public boolean canQueue() {
        return waitingCustomer.size() < maxQueueSize;
    }

    public boolean hasWaitingCustomers() {
        return this.waitingCustomer.size() > 0;
    }

    public Server addCustomerToQueue(Customer customer) {
        if (canQueue() && !waitingCustomer.contains(customer)) {
            List<Customer> updatedQueue =
                Stream.concat(waitingCustomer.stream(), Stream.of(customer))
                    .sorted()
                    .toList();
            return new Server(this.id, this.isServing, updatedQueue,
                              this.maxQueueSize);
        }
        return this;
    }

    public Server removeNextCustomer() {
        if (waitingCustomer.size() > 0) {
            List<Customer> updatedQueue =
                waitingCustomer.stream().skip(1).toList();
            return new Server(this.id, this.isServing, updatedQueue,
                              this.maxQueueSize);
        }
        return this;
    }

    public boolean isWaitingForCustomer(Customer customer) {
        return waitingCustomer.stream().anyMatch(
            c -> c.getId() == customer.getId() && !isServing);
    }

    public boolean isServingCustomer(Customer customer) {
        return waitingCustomer.stream().anyMatch(
            c -> c.getId() == customer.getId() && isServing);
    }

    public Server serve(Customer customer, double endTime) {
        return new Server(this.id, true, this.waitingCustomer,
                          this.maxQueueSize);
    }

    public int getId() {
        return id;
    }

    public List<Customer> getCustomer() {
        return this.waitingCustomer;
    }

    @Override
    public String toString() {
        return "server " + id;
    }
}
