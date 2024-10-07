import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

void main() {}

public boolean isPrime(int n) {
    return n > 1 && IntStream.range(2, n).noneMatch(i -> n % i == 0);
}

public IntStream twinPrimes(int n) {
    return IntStream.rangeClosed(2, n).filter(
        x
        -> (isPrime(x) && isPrime(x + 2)) ||
               (x > 2 && isPrime(x) && isPrime(x - 2)));
}

public String reverse(String str) {
    return IntStream.range(0, str.length())
        .mapToObj(i -> str.charAt(i))
        .reduce("", (c1, c2) -> c2 + c1, (x, y) -> y + x);
}

public int countRepeats(List<Integer> list) {
    return IntStream.range(0, list.size() - 1)
        .filter(
            i
            -> list.get(i) == list.get(i + 1) &&
                   (i + 2 == list.size() || list.get(i + 1) != list.get(i + 2)))
        .reduce(0, (x, y) -> x + 1);
}
