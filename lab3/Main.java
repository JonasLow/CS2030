import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

void main() {
    Scanner sc = new Scanner(System.in);
    int numOfServers = sc.nextInt();
    int numOfCustomers = sc.nextInt();

    sc.nextLine(); // removes trailing newline
    List<Pair<Integer,Pair<Double,Double>>> arrivals = sc.useDelimiter("\n")
        .tokens()
        .map(line -> {
            List<String> token = Stream.of(line.split(" ")).toList();
            return new Pair<Integer,Pair<Double,Double>>(Integer.parseInt(token.get(0)),
                    new Pair<Double,Double>(Double.parseDouble(token.get(1)), 
                        Double.parseDouble(token.get(2))));
        }).toList();

    State state = new Simulator(numOfServers, numOfCustomers, arrivals).run();
    System.out.println(state);
}
