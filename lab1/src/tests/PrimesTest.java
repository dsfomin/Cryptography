package tests;

import algorithms.Ferma;
import algorithms.MillerRabin;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.IntStream;


public class PrimesTest {

    static final int COUNT_OF_TESTS = 100;
    static final int COUNT_OF_BITS = 512;

    final int[] primesSmall = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
            43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
            101, 103, 107, 109, 113, 127, 131, 137, 139, 149,
            151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199,
            271, 331, 397, 547, 631, 919, 1657, 1801, 1951, 2269, 2437,
            2791, 3169, 3571, 4219, 4447, 5167, 5419, 6211, 7057, 7351,
            8269, 9241, 10267, 11719, 12097, 13267, 13669, 16651, 19441, 19927, 22447, 23497};

    final int[] notPrimesSmall = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22,
            24, 25, 26, 27, 28, 30, 32, 33, 34, 35, 36, 38,
            39, 40, 42, 44, 45, 46, 48, 49, 50, 51, 52, 54};

    @Test
    public void primeAlgorithmsTest() {

        Random random = new Random();

        IntStream.range(0, COUNT_OF_TESTS).mapToObj(i -> BigInteger.probablePrime(COUNT_OF_BITS, random)).forEach(testNumber -> {
            Assert.assertTrue(Ferma.checkPrime(testNumber, 50));
            Assert.assertTrue(MillerRabin.checkPrime(testNumber, 50));
        });

        for (int value : primesSmall) {
            Assert.assertTrue(Ferma.checkPrime(BigInteger.valueOf(value), 50));
            Assert.assertTrue(MillerRabin.checkPrime(BigInteger.valueOf(value), 50));
        }

        for (int value : notPrimesSmall) {
            Assert.assertFalse(Ferma.checkPrime(BigInteger.valueOf(value), 50));
            Assert.assertFalse(MillerRabin.checkPrime(BigInteger.valueOf(value), 50));
        }

    }
}