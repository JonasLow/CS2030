public class Sphere implements Shape3D {
    private final double radius;
    private static final double FOUR_THIRDS = 4.0 / 3.0;
    private static final double PI = Math.PI;

    Sphere(double radius) {
        this.radius = radius;
    }

    @Override
    public double volume() {
        return FOUR_THIRDS * PI * Math.pow(this.radius, 3);
    }

    @Override
    public String toString() {
        return String.format("sphere [%.2f]", this.radius);
    }
}
