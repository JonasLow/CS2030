public class SolidImpl implements Solid {
    private final Shape3D shape3D;
    private final double density;

    SolidImpl(Shape3D shape3D, double density) {
        this.shape3D = shape3D;
        this.density = density;
    }

    public double volume() {
        return this.shape3D.volume();
    }

    public double mass() {
        return this.shape3D.volume() * this.density;
    }

    @Override
    public String toString() {
        return "SolidImpl";
    }
}
