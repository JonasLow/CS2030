import java.util.List;
import java.util.stream.IntStream;

class Top5 {
    private final List<String> list;
    private static final int top = 5;

    Top5() {
        this.list = List.<String>of("", "", "", "", "");
    }

    Top5(List<String> list) {
        if (list.size() != this.top) {
            throw new IllegalArgumentException(
                "List must contain exactly 5 elements");
        }
        this.list = list;
    }

    Try<Top5> add(int i, String str) {
        if (i >= list.size() || i < 0) {
            return Try.of(() -> {
                throw new IllegalStateException("index " + i + " out of range");
            });
        }

        List<String> updated = IntStream.range(0, list.size())
                                   .mapToObj(x -> x == i ? str : list.get(x))
                                   .toList();
        return Try.of(() -> new Top5(updated));
    }

    Try<String> get(int i) {
        if (i >= list.size() || i < 0) {
            return Try.of(() -> {
                throw new IllegalStateException("index " + i + " out of range");
            });
        }

        return Try.of(() -> this.list.get(i));
        
    }

    Try<Integer> find(String s) {
        return Try.of(
            ()
                -> IntStream.range(0, list.size())
                       .filter(x -> list.get(x).equals(s))
                       .findFirst()
                       .orElseThrow(()
                                        -> new IllegalStateException(
                                            s + " not among top 5")));
    }

    public String toString() {
        return this.list.toString();
    }
}
