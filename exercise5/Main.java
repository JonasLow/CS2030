import java.time.Instant;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.List;

/**
 * This program finds different ways one can travel by bus (with a bit 
 * of walking) from one bus stop to another.
 *
 * @author: cs2030 (orig. Ooi Wei Tsang)
 */
class Main {

    static CompletableFuture<BusRoutes> processQuery(String query) {
        Scanner sc = new Scanner(query);
        BusStop srcBusStop = new BusStop(sc.next());
        String searchString = sc.next();
        sc.close();
        return BusSg.findBusServicesBetween(srcBusStop, searchString);
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        Scanner sc = new Scanner(System.in);
        List<CompletableFuture<BusRoutes>> cfList = sc
            .useDelimiter("\n")
            .tokens()
            .map(x -> CompletableFuture.supplyAsync(() -> x.trim().replace("\"", "")))
            .map(x -> x.thenCompose(y -> processQuery(y)))
            .toList();
        // Route.description() gets a completablefuture(string)
        cfList.stream()
            .map(x -> x.thenCompose(route -> route.description()))
            .map(x -> x.join())
            .forEach(string -> System.out.println(string));
        Instant stop = Instant.now();
        System.out.printf("Took %,dms\n",  
            Duration.between(start, stop).toMillis());
    }
}
 
