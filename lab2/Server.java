public class Server {
    private final int ident;
    private final double nextAvailTime;

    Server(int ident) {
        this.ident = ident;
        this.nextAvailTime = 0;
    }

    private Server(int ident, double nextAvailTime) {
        this.ident = ident;
        this.nextAvailTime = nextAvailTime;
    }

    public Server serve(Customer c, double serviceTime) {
        double newAvailTime = c.serveTill(serviceTime);
        return new Server(this.ident, newAvailTime);
    }

    public boolean canServe(Customer c) {
        return (c.canBeServed(this.nextAvailTime));
    }

    @Override
    public String toString() {
        return "server " + this.ident;
    }

    public boolean equals(Server other) {
        if (this.ident == other.ident) {
            return true;
        }
        return false;
    }
}
