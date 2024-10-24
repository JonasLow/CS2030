import java.util.List;
import java.util.Optional;

class Fraction extends AbstractNum<List<Integer>> {
    private Fraction(Optional<List<Integer>> frac) {
        super(frac);
    }

    static Fraction of(Integer num, Integer denom) {
        if (valid.test(num) && valid.test(denom) && denom != 0) {
            List<Integer> frac = List.of(num, denom);
            return new Fraction(Optional.of(frac));
        }
        return new Fraction(Optional.empty());
    }

    public Fraction add(Fraction other) {
        // If either fraction is invalid, return NaN
        if (!this.isValid() || !other.isValid()) {
            return new Fraction(Optional.empty());
        }

        return this.opt
            .flatMap(thisList -> other.opt.flatMap(otherList -> {
                int a = thisList.get(0);    // this numerator
                int b = thisList.get(1);    // this denominator
                int c = otherList.get(0);    // other numerator
                int d = otherList.get(1);    // other denominator

                // Compute a * d
                Num a_mul_d = Num.of(a).mul(Num.of(d));

                // Compute b * c
                Num b_mul_c = Num.of(b).mul(Num.of(c));

                // Compute a * d + b * c
                Num numerator = a_mul_d.add(b_mul_c);

                // Compute b * d
                Num denominator = Num.of(b).mul(Num.of(d));

                // Now, construct the new Fraction only if both numerator and denominator are valid
                return numerator.opt.flatMap(
                    n_num
                    -> denominator.opt.map(
                        n_denom -> Fraction.of(n_num, n_denom)));
            }))
            .orElse(new Fraction(Optional.empty()));
    }

    public Fraction sub(Fraction other) {
        if (!this.isValid() || !other.isValid()) {
            return new Fraction(Optional.empty());
        }

        return this.opt
            .flatMap(thisList -> other.opt.flatMap(otherList -> {
                int a = thisList.get(0);    // this numerator
                int b = thisList.get(1);    // this denominator
                int c = otherList.get(0);    // other numerator
                int d = otherList.get(1);    // other denominator

                // Compute a * d
                Num a_mul_d = Num.of(a).mul(Num.of(d));

                // Compute b * c
                Num b_mul_c = Num.of(b).mul(Num.of(c));

                // Compute a * d - b * c
                Num numerator = a_mul_d.sub(b_mul_c);

                // Compute b * d
                Num denominator = Num.of(b).mul(Num.of(d));

                // Now, construct the new Fraction only if both numerator and denominator are valid
                return numerator.opt.flatMap(
                    n_num
                    -> denominator.opt.map(
                        n_denom -> Fraction.of(n_num, n_denom)));
            }))
            .orElse(new Fraction(Optional.empty()));
    }

    public Fraction mul(Fraction other) {
        if (!this.isValid() || !other.isValid()) {
            return new Fraction(Optional.empty());
        }

        return this.opt
            .flatMap(thisList -> other.opt.flatMap(otherList -> {
                int a = thisList.get(0);    // this numerator
                int b = thisList.get(1);    // this denominator
                int c = otherList.get(0);    // other numerator
                int d = otherList.get(1);    // other denominator

                // Compute a * c
                Num numerator = Num.of(a).mul(Num.of(c));

                // Compute b * d
                Num denominator = Num.of(b).mul(Num.of(d));

                // Now, construct the new Fraction only if both numerator and denominator are valid
                return numerator.opt.flatMap(
                    n_num
                    -> denominator.opt.map(
                        n_denom -> Fraction.of(n_num, n_denom)));
            }))
            .orElse(new Fraction(Optional.empty()));
    }

    public Fraction reduce() {
        if (!this.isValid()) {
            return new Fraction(Optional.empty());
        }

        Fraction temp = this;

        return temp.opt
            .flatMap(list -> {
                int a = list.get(0);    // Numerator
                int b = list.get(1);    // Denominator

                Num numerator = Num.of(a);
                Num denominator = Num.of(b);

                // Compute GCD using the Num class's gcd method
                Num gcd = numerator.gcd(denominator);

                // Divide numerator and denominator by GCD
                Num reducedNumerator = numerator.div(gcd);
                Num reducedDenominator = denominator.div(gcd);

                // Extract integer values safely and create a new Fraction
                return reducedNumerator.opt.flatMap(
                    n_num
                    -> reducedDenominator.opt.flatMap(
                        n_denom -> Fraction.of(n_num, n_denom)));
            })
            .orElse(new Fraction(Optional.empty()));
    }

    @Override
    public String toString() {
        return this.opt.map(list -> list.get(0) + " : " + list.get(1))
            .orElse("NaN");
    }
}
