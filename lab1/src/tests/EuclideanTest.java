package tests;

import algorithms.Euclidean;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

class EuclideanTest {
    public static final int COUNT_OF_TESTS = 1000;
    public static final int COUNT_OF_BITS = 512;
    @Test
    public void gcdExtendedTest(){
        Random rand = new Random();
        for (int i = 0; i < COUNT_OF_TESTS; i++){
            BigInteger a = new BigInteger(COUNT_OF_BITS, rand);
            BigInteger b = new BigInteger(COUNT_OF_BITS, rand);
            Euclidean.Result res = Euclidean.extendedEuclid(a, b);
            Assert.assertEquals(a.gcd(b), res.getGcd());
            Assert.assertEquals(a.multiply(res.getFirstCoefficient()).add(b.multiply(res.getSecondCoefficient())), res.getGcd());
        }
    }
}