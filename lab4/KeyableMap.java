import java.util.Optional;
import java.util.stream.Stream;

public class KeyableMap<V extends Keyable> implements Keyable {
    private final String key;
    private final ImmutableMap<String, V> iMap;

    KeyableMap(String key) {
        this.key = key;
        this.iMap = new ImmutableMap<>();
    }

    KeyableMap(String key, ImmutableMap<String, V> iMap) {
        this.key = key;
        this.iMap = iMap;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public ImmutableMap<String, V> getMap() {
        return this.iMap;
    }

    public Optional<V> get(String thing) {
        return this.iMap.get(thing);
    }

    public KeyableMap<V> put(V item) {
        return new KeyableMap<V>(this.key, this.iMap.put(item.getKey(), item));
    }

    @Override
    public String toString() {
        String items = this.iMap.entrySet()
                           .stream()
                           .map(entry -> entry.getValue().toString())
                           .reduce((a, b) -> a + ", " + b)
                           .orElse("");
        return String.format("%s: {%s}", this.key, items);
    }
}
