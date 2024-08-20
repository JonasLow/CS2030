import java.util.stream.*;

public class Cartesian {
	public static IntStream dot(int a, int b) {
		return IntStream.rangeClosed(a, b).
			flatMap(x -> IntStream.rangeClosed(a, b).
				map(y -> x * y));
	}


	record Pair<T, U>(T t, U u) {};


	public static Stream<Pair<Integer, Integer>> product(int a, int b) {
		return IntStream.rangeClosed(a, b).
			boxed().
			flatMap(x -> IntStream.rangeClosed(a, b).
				mapToObj(y -> new Pair<Integer, Integer>(x, y)));
	}
}
