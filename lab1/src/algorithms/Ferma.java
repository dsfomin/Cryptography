package algorithms;
import java.math.BigInteger;
import java.util.Random;

public class Ferma {

    private final static Random rand = new Random();

    public Ferma(){}

    public static boolean checkPrime(BigInteger n, int iteration) {
        if (n.equals(BigInteger.ONE))
            return false;
        for (int i = 0; i < iteration; i++) {
            BigInteger a = getRandomFermaBase(n);
            a = a.modPow(n.subtract(BigInteger.ONE), n);
            if (!a.equals(BigInteger.ONE))
                return false;
        }
        return true;
    }

    private static BigInteger getRandomFermaBase(BigInteger n) {
        while (true) {
            final BigInteger a = new BigInteger(n.bitLength(), rand);
            if (BigInteger.ONE.compareTo(a) <= 0 && a.compareTo(n) < 0) {
                return a;
            }
        }
    }

}