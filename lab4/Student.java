import java.util.Optional;
import java.util.stream.Stream;

public class Student extends KeyableMap<Course> implements Keyable {
    Student(String name) {
        super(name);
    }

    Student(String name, KeyableMap<Course> courses) {
        super(name, courses.getMap());
    }

    public Student put(Course course) {
        return new Student(this.getKey(), super.put(course));
    }

    public Optional<Course> get(String mod) {
        return super.get(mod);
    }
}
