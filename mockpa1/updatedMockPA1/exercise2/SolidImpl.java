public class SolidImpl implements Solid, Shape3D {
    private final Shape3D shape;
    private final double density;

    SolidImpl(Shape3D shape, double density) {
        this.shape = shape;
        this.density = density;
    }

    @Override
    public double volume() {
        return this.shape.volume();
    }

    @Override
    public double mass() {
        return this.shape.volume() * this.density;
    }

    @Override
    public String toString() {
        return "SolidImpl";
    }
}
