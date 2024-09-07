import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

static final double SERVICE_TIME = 1.0;

record Pair<T, U>(T t, U u) {}

void main() {
    Scanner sc = new Scanner(System.in);
    int numOfServers = sc.nextInt();
    int numOfCustomers = sc.nextInt();

    sc.nextLine();    // removes trailing newline
    List<Pair<Integer, Double>> arrivals =
        sc.useDelimiter("\n")
            .tokens()
            .map(line -> {
                List<String> token = Stream.of(line.split(" ")).toList();
                return new Pair<Integer, Double>(
                    Integer.parseInt(token.get(0)),
                    Double.parseDouble(token.get(1)));
            })
            .toList();

    State state =
        new Simulator(numOfServers, numOfCustomers, arrivals, SERVICE_TIME)
            .run();
    System.out.println(state);
}
