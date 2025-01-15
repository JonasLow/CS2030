class Server {
    private final int identifier;
    private final double availableTime;
    private final int remainingCapacity;

    Server(int identifier, int remainingCapacity) {
        this.identifier = identifier;
        this.availableTime = 0.0;
        this.remainingCapacity = remainingCapacity;
    }

    private Server(int identifier, double availableTime, int remainingCapacity) {
        this.identifier = identifier;
        this.availableTime = availableTime;
        this.remainingCapacity = remainingCapacity;
    }

    Server serve(double serveEventTime, double servingTime) {
        double newAvailableTime = Math.max(serveEventTime, this.availableTime) + servingTime;
        return new Server(this.identifier, newAvailableTime, remainingCapacity);
    }

    Server enterQueue() {
        return new Server(this.identifier, this.availableTime, this.remainingCapacity - 1);
    }

    Server exitQueue() {
        return new Server(this.identifier, this.availableTime, this.remainingCapacity + 1);
    }

    boolean isSame(Server other) {
        return this.identifier == other.identifier;
    }

    boolean canServe(Customer customer) {
        return this.availableTime <= customer.getArrivalTime();
    }

    public String toString() {
        return "server " + this.identifier;
    }

    public int getRemainingCapacity() {
        return this.remainingCapacity;
    }

    public Double getAvailableTime() {
        return this.availableTime;
    }
}
