import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Course extends KeyableMap<Assessment> implements Keyable {
    Course(String key) {
        super(key);
    }

    Course(String key, KeyableMap<Assessment> assessments) {
        super(key, assessments.getMap());
    }

    public Course put(Assessment test) {
        return new Course(this.getKey(), super.put(test));
    }

    public Optional<Assessment> get(String test) {
        return super.get(test);
    }
}
