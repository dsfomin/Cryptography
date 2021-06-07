package algorithms;

import java.math.BigInteger;

public class Euclidean {

    public static Result extendedEuclid(BigInteger a, BigInteger b) {
        if(b.compareTo(BigInteger.ZERO) == 0) {
            return new Result(a, BigInteger.ONE, BigInteger.ZERO);
        }
        Result r = extendedEuclid(b, a.mod(b));
        BigInteger x = r.getSecondCoefficient();
        BigInteger y = r.getFirstCoefficient().subtract(a.divide(b).multiply(x));
        return new Result(r.getGcd(), x, y);
    }

    public static class Result {
        private final BigInteger firstCoefficient;
        private final BigInteger secondCoefficient;
        private final BigInteger gcd;

        public Result(BigInteger gcd, BigInteger firstCoefficient, BigInteger secondCoefficient) {
            this.firstCoefficient = firstCoefficient;
            this.secondCoefficient = secondCoefficient;
            this.gcd = gcd;
        }

        public BigInteger getFirstCoefficient() {
            return firstCoefficient;
        }

        public BigInteger getSecondCoefficient() {
            return secondCoefficient;
        }

        public BigInteger getGcd() {
            return gcd;
        }
    }
}