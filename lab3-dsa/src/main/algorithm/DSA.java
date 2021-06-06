package main.algorithm;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class DSA {

    public static Pair<BigInteger, BigInteger> sign(String message, BigInteger publicG, BigInteger publicP,
                                                    BigInteger publicQ, BigInteger privateX) {
        // K
        BigInteger k = new BigInteger(publicQ.bitLength(), new SecureRandom());
        while (k.compareTo(publicQ) != -1 && k.compareTo(BigInteger.ZERO) != 1) {
            k = new BigInteger(publicQ.bitLength(), new SecureRandom());
        }
        // R
        BigInteger r = publicG.modPow(k, publicP).mod(publicQ);
        // S
        MessageDigest md;
        BigInteger s = BigInteger.ONE;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes());
            BigInteger messageHash = new BigInteger(md.digest());
            s = (k.modInverse(publicQ).multiply(messageHash.add(privateX.multiply(r)))).mod(publicQ);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair<>(r, s);
    }

    public static Boolean verify(String message, BigInteger r, BigInteger s,
                                 BigInteger publicG, BigInteger publicP,
                                 BigInteger publicQ, BigInteger privateY) {
        MessageDigest md;
        BigInteger v = BigInteger.ZERO;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes());

            BigInteger messageHash = new BigInteger(md.digest());
            // W
            BigInteger w = s.modInverse(publicQ);
            // U1
            BigInteger u1 = messageHash.multiply(w).mod(publicQ);
            // U2
            BigInteger u2 = r.multiply(w).mod(publicQ);
            // V
            v = ((publicG.modPow(u1, publicP).multiply(privateY.modPow(u2, publicP))).mod(publicP)).mod(publicQ);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return v.compareTo(r) == 0;
    }
}
