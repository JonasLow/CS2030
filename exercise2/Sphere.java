public class Sphere implements Shape3D {
    private static final double FOUR_THIRDS = 4.0 / 3.0;
    private static final double PI = Math.PI;
    private final double radius;

    Sphere(double radius) {
        this.radius = radius;
    }

    public double volume() {
        return FOUR_THIRDS * PI * this.radius * this.radius * this.radius;
    }

    @Override
    public String toString() {
        return String.format("sphere [%.2f]", this.radius);
    }
}
