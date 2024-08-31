import java.util.stream.*;

public class DistinctPrime {
		public static boolean isPrime(int n) {
			return n > 1 && IntStream.range(2, n).
				noneMatch(x -> n % x == 0);
		}


		public static IntStream omega(int n) {
			return IntStream.rangeClosed(1, n).
				map(x -> IntStream.rangeClosed(1, x).
				filter(y -> x % y == 0 && isPrime(y)).
				reduce(0, (a, b) -> a + 1));
		}
}
