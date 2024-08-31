import java.util.Optional;

class Circle {
	private final Optional<Point> centre;
	private final double radius;

	Circle(Point centre, double radius) {
		this.centre = Optional.of(centre);
		this.radius = radius;
	}

	Circle(double radius) {
		this.centre = Optional.empty();
		this.radius = radius;
	}

	public String toString() {
		return "Circle " +
			this.centre.map(c -> "at " + c).orElse("") +
			" with radius " + this.radius;
	}

	boolean isOverlap(Circle circle) {
		return this.centre
			.flatMap(c -> circle.centre
				.map(k -> c.distanceTo(k) < this.radius + circle.radius))
			.orElse(false);
	}
}


class Point {
    final double x;
    final double y;

	Point(double x, double y) {
		this.x = x;
	    this.y = y;
	}

	double distanceTo(Point other) {
	    return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
