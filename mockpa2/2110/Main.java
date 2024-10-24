import java.util.List;

class Main {
    static double simulate(int seed, int n) {
        Circle circle = new Circle(new Point(0, 0), 1.0);

        List<Double> random =
            Rand.randRange(seed, x -> (2.0) * x / (Integer.MAX_VALUE - 1) - 1.0)
                .limit(n * 2)
                .toList();

        long count = 0;
        for (int i = 0; i < random.size(); i += 2) {
            double x = random.get(i);
            double y = random.get(i + 1);
            Point p = new Point(x, y);

            if (circle.contains(p)) {
                count++;
            }
        }

        return 4.0 * count / n;
    }
}
