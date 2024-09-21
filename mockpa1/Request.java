public class Request {
    private final int distance;
    private final int pax;
    private final int time;

    Request(int distance, int pax, int time) {
        super();
        this.distance = distance;
        this.pax = pax;
        this.time = time;
    }
    
    public int computeFare(Service service) {
        return service.computeFare(this.distance, this.pax, this.time);
    }

    @Override
    public String toString() {
        return this.distance + "km for " + this.pax + "pax @ " + this.time + "hrs";
    }
}