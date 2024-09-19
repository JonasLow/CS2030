public class Customer {
    private final int ident;
    private final double arrTime;
    private final double serviceTime;

    Customer(int ident, double arrTime) {
        this.ident = ident;
        this.arrTime = arrTime;
        this.serviceTime = 0;
    }

    Customer(int ident, double arrTime, double serviceTime) {
        this.ident = ident;
        this.arrTime = arrTime;
        this.serviceTime = serviceTime;
    }

    public boolean canBeServed(double time) {
        return (this.arrTime >= time);
    }

    public double serveTill(double time) {
        return this.serviceTime + time;
    }

    public int compare(Customer other) {
        return Integer.compare(this.ident, other.ident);
    }

    @Override
    public String toString() {
        return "customer " + this.ident;
    }
}
