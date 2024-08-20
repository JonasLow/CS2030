import java.util.stream.Stream;

public class Fibonacci {
	public static Stream<Integer> fibo(int n) {
		record Pair<T, U>(T t, U u) {}

		return Stream.iterate(new Pair<Integer, Integer>(1, 1), pair -> new Pair<Integer, Integer>(pair.u, pair.t + pair.u)).
			limit(n).
			map(pair -> pair.t);
	}
}
