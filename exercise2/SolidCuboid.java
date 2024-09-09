public class SolidCuboid extends Cuboid implements Solid {
    private final SolidImpl solidImpl;

    SolidCuboid(double height, double width, double length, double density) {
        super(height, width, length);
        this.solidImpl = new SolidImpl(this, density);
    }

    public double mass() {
        return this.solidImpl.mass();
    }

    @Override
    public String toString() {
        return "solid-" + super.toString() +
            String.format(" with a mass of %.2f", this.mass());
    }
}
