import java.util.Optional;
import java.util.stream.Stream;

public class Roster extends KeyableMap<Student> implements Keyable {
    Roster(String ay) {
        super(ay);
    }

    Roster(String ay, KeyableMap<Student> students) {
        super(ay, students.getMap());
    }

    public Roster put(Student student) {
        return new Roster(this.getKey(), super.put(student));
    }

    public Optional<Student> get(String name) {
        return super.get(name);
    }

    public String getGrade(String name, String mod, String test) {
        return super.get(name)
            .flatMap(student -> student.get(mod))
            .flatMap(course -> course.get(test))
            .map(assessment -> assessment.getGrade())
            .orElse(
                String.format("No such record: %s %s %s", name, mod, test));
    }

    public Roster add(String name, String mod, String test, String grade) {
        Student student = super.get(name).orElse(new Student(name));
        Course course = student.get(mod).orElse(new Course(mod));
        course = course.put(new Assessment(test, grade));
        student = student.put(course);
        return this.put(student);
    }
}
