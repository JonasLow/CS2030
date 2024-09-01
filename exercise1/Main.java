import java.util.List;
import java.util.stream.IntStream;

Circle createUnitCircle(Point p, Point q) {
	Point m = p.midPoint(q);
	double dm = p.distanceTo(m);
	return new Circle(m.moveTo(p.angleTo(q) + Math.PI / 2, Math.sin(Math.acos(dm))), 1.0);
}


int findCoverage(Circle c, List<Point> points) {
    int coverage = 0;
    for (Point point : points) {
        if (c.contains(point)) {
            coverage++;
        }
    }
    return coverage;
}


int findMaxDiscCoverage(List<Point> points) {
	int numOfPoints = points.size();

	return IntStream.range(0, numOfPoints - 1)
		.boxed()
		.flatMap(i -> IntStream.range(i + 1, numOfPoints)
			.mapToObj(j -> {
				Point p = points.get(i);
				Point q = points.get(j);
				double distPQ = p.distanceTo(q);
				if (distPQ <=2.0 && distPQ > 0.0) {
					Circle c = createUnitCircle(p, q);
					return findCoverage(c, points);
				} else {
					return 0;
				}
			}))
		.reduce(0, (a, b) -> a > b ? a : b);
}

void main() {}
