import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

void main() {}

// Task 1
boolean isPrime(int n) {
    return n > 1 && IntStream.range(2, n).noneMatch(x -> n % x == 0);
}

IntStream twinPrimes(int n) {
    return IntStream.rangeClosed(1, n).filter(
        x -> isPrime(x) && (isPrime(x + 2) || isPrime(x - 2)));
}

// Task 2
String reverse(String str) {
    return IntStream.rangeClosed(1, str.length())
        .mapToObj(i -> str.charAt(str.length() - i))
        .reduce("", (s, ch) -> s + ch, (s1, s2) -> s1 + s2);
}

// Task 3
int countRepeats(List<Integer> list) {
    return IntStream.range(0, list.size() - 1)
        .filter(i
                -> (list.get(i).equals(list.get(i + 1)) && (i + 2 == list.size()) || (list.get(i).equals(list.get(i + 1)) &&
                       !list.get(i + 1).equals(list.get(i + 2)))))
        .reduce(0, (x, y) -> x + 1);
}
