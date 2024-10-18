import java.util.Optional;

public Log<Integer> sum(int n) {
    if (n == 0) {
        return Log.of(0, "hit base case!");
    } else {
        return sum(n - 1).map(total -> total + n)
                        .flatMap(totalSum -> Log.of(totalSum, "adding " + n));
    }
}

public Log<Integer> f(int n) {
    if (n == 1) {
        return Log.of(1, "1");
    } else if (n % 2 == 0) {
        return Log.of(1, n + " / 2").flatMap(result -> f(n / 2));
    } else {
        return Log.of(1, "3(" + n + ") + 1").flatMap(result -> f(3 * n + 1));
    }
}
