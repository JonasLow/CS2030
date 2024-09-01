class Point {
    private final double x;
    private final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return "point (" + String.format("%.3f", this.x) + ", " 
            + String.format("%.3f", this.y) + ")";
    }

    public double distanceTo(Point pt) {
        return Math.sqrt(Math.pow(pt.x - this.x, 2) + Math.pow(pt.y - this.y, 2));
    }

    public Point midPoint(Point pt) {
        return new Point((this.x + pt.x) / 2, (this.y + pt.y) / 2);
    }

    public double angleTo(Point pt) {
        double dy = pt.y - this.y;
        double dx = pt.x - this.x;

        if (dx == 0) {
            if (dy > 0) {
                return Math.PI / 2;
            } else if (dy < 0) {
                return -Math.PI / 2;
            } else {
                return 0;
            }
        }

        if (dy == 0) {
            if (dx > 0) {
                return 0.0;
            } else if (dx < 0) {
                return Math.PI;
            }
        }

        if (dx < 0) {
            return Math.atan(dy / dx) - Math.PI;
        } else if (dy < 0) {
            return Math.atan(dy / dx) + 2 * Math.PI;
        } else {
            return Math.atan(dy / dx);
        }
    }

    public Point moveTo(double theta, double d) {
        return new Point((this.x + d * Math.cos(theta)), (this.y + d * Math.sin(theta)));
    }
}
