package algorithms;

import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.IntStream;

public class Montgomery {

    private final BigInteger modulus;
    private final int reducerBits;
    private final BigInteger reciprocal;
    private final BigInteger mask;
    private final BigInteger factor;
    private final BigInteger convertedOne;

    // The modulus must be an odd number at least 3
    public Montgomery(BigInteger modulus) {
        Objects.requireNonNull(modulus);
        if (!modulus.testBit(0) || modulus.compareTo(BigInteger.ONE) <= 0)
            throw new IllegalArgumentException("Modulus must be an odd number at least 3");
        this.modulus = modulus;

        reducerBits = (modulus.bitLength() / 8 + 1) * 8;  // This is a multiple of 8

        BigInteger reducer = BigInteger.ONE.shiftLeft(reducerBits);  // This is a power of 256
        mask = reducer.subtract(BigInteger.ONE);
        assert reducer.compareTo(modulus) > 0 && reducer.gcd(modulus).equals(BigInteger.ONE);

        reciprocal = reducer.modInverse(modulus);
        factor = reducer.multiply(reciprocal).subtract(BigInteger.ONE).divide(modulus);
        convertedOne = reducer.mod(modulus);
    }

    public BigInteger multiply(BigInteger x, BigInteger y) {
        assert x.signum() >= 0 && x.compareTo(modulus) < 0;
        assert y.signum() >= 0 && y.compareTo(modulus) < 0;
        BigInteger product = x.multiply(y);
        BigInteger temp = product.and(mask).multiply(factor).and(mask);
        BigInteger reduced = product.add(temp.multiply(modulus)).shiftRight(reducerBits);
        BigInteger result = reduced.compareTo(modulus) < 0 ? reduced : reduced.subtract(modulus);
        assert result.signum() >= 0 && result.compareTo(modulus) < 0;
        return result;
    }

    public BigInteger pow(BigInteger x, BigInteger y) {
        assert x.signum() >= 0 && x.compareTo(modulus) < 0;
        if (y.signum() == -1)
            throw new IllegalArgumentException("Negative exponent");

        BigInteger z = convertedOne;
        for (int i = 0, len = y.bitLength(); i < len; i++) {
            if (y.testBit(i))
                z = multiply(z, x);
            x = multiply(x, x);
        }
        return z;
    }

    public static void main(String[] args) {
        // y*x = 1 mod n --> find x
        BigInteger y = new BigInteger("37");
        BigInteger n = new BigInteger("17");
        Montgomery montgomery = new Montgomery(n);
        IntStream.range(1, 300).forEach(i -> {
            BigInteger multiply = montgomery.multiply(y, new BigInteger(String.valueOf(i)));
            if (multiply.equals(BigInteger.ONE)) System.out.println(i);
        });
    }

    // The range of x is unlimited
    public BigInteger convertIn(BigInteger x) {
        return x.shiftLeft(reducerBits).mod(modulus);
    }

    // The range of x is unlimited
    public BigInteger convertOut(BigInteger x) {
        return x.multiply(reciprocal).mod(modulus);
    }

}
