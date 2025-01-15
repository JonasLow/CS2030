import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Encapsulates the result of a query: for a bus stop and a search string,
 * it stores a map of bus services that servce stops with matching name.
 * e.g., stop 12345, name "MRT", with map contains:
 *    96: 11223 "Clementi MRT"
 *    95:  22334 "Beuno Vista MRT"
 *
 * @author: cs2030 (orig. Ooi Wei Tsang)
 */
class BusRoutes {
    final BusStop stop;
    final String name;
    final Map<BusService, CompletableFuture<List<BusStop>>> services;

    /**
     * Constructor for creating a bus route.
     * @param stop The first bus stop.
     * @param name The second bus stop.
     * @param services The list of/et bus services between the two stops.
     */
    BusRoutes(BusStop stop, String name,
              Map<BusService, CompletableFuture<List<BusStop>>> services) {
        this.stop = stop;
        this.name = name;
        this.services = services;
    }

    /**
     * Return a string describing the bus route.
     * @return The first line contains the query information:
     *     bus stop id and search string.  The remaining line contains 
     *     the bus services and matching stops served.
     */
    public CompletableFuture<String> description() {
        CompletableFuture<String> result = CompletableFuture.completedFuture(
            "Search for: " + this.stop + " <-> " + name + ":\n");
        result = result.thenApply(x -> x + "From " + this.stop + "\n");

        List<CompletableFuture<String>> descriptions =
            services.entrySet()
                .stream()
                .map(entry -> {
                    BusService service = entry.getKey();
                    CompletableFuture<List<BusStop>> stops =
                        services.get(service);
                    return describeService(service, stops);
                })
                .toList();

        CompletableFuture<String> finalRes = descriptions.stream().reduce(
            CompletableFuture.completedFuture(""),
            (acc, desc) -> acc.thenCombine(desc, (x, y) -> x + y));
        return result.thenCombine(finalRes, (x, y) -> x + y);
    }

    /**
     * Return a string representation a bus service and its matching stops.
     * @return The first line contains the query information:
     *     bus stop id and search string.  The remaining line contains 
     *     the bus services and matching stops served.
     */

    public CompletableFuture<String> describeService(
        BusService service, CompletableFuture<List<BusStop>> cfStops) {
        return cfStops.thenApply(services -> {
            if (services.isEmpty()) {
                return "";
            }
            return services.stream()
                .filter(x -> x != this.stop)
                .reduce("- Can take " + service + " to:\n",
                        (str, x)
                            -> str += "  - " + x + "\n",
                        (str1, str2) -> str1 + str2);
        });
    }
}
