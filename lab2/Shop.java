import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Shop {
    private final List<Server> serverList;

    Shop(int numOfServers) {
        this.serverList = IntStream.rangeClosed(1, numOfServers)
                              .mapToObj(x -> new Server(x))
                              .collect(Collectors.toList());
    }

    Shop(List<Server> serverList) {
        this.serverList = serverList;
    }

    public Optional<Server> findServer(Customer customer) {
        return this.serverList.stream()
            .filter(x -> x.canServe(customer))
            .findFirst();
    }

    public Shop update(Server server) {
        return new Shop(this.serverList.stream()
                            .map(x -> x.equals(server) ? server : x)
                            .collect(Collectors.toList()));
    }

    public String toString() {
        return this.serverList.stream()
            .map(x -> x.toString())
            .collect(Collectors.joining(", ", "[", "]"));
    }
}
