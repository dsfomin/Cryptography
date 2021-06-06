package main.algorithm;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Session {
	private static Session session;
	public BigInteger globalKeyP;
	public BigInteger globalKeyQ;
	public BigInteger globalKeyG;
	private final static int L = 512;
	private final static int CERTAINTY = 20;

	public static Session getInstance() {
		if (session == null)
			session = new Session();
		return session;
	}

	private Session() {
		// ==Q==
		globalKeyQ = new BigInteger(160, CERTAINTY, new SecureRandom());

		// ==P==
		BigInteger tempP;
		BigInteger tempP2;
		SecureRandom rand = new SecureRandom();
		do {
			tempP = new BigInteger(L, CERTAINTY, rand);
			tempP2 = tempP.subtract(BigInteger.ONE);
			tempP = tempP.subtract(tempP2.remainder(globalKeyQ));
		} while (!tempP.isProbablePrime(CERTAINTY) || tempP.bitLength() != L);
		BigInteger p = tempP;
		globalKeyP = p;

		// ==G==
		BigInteger p1 = globalKeyP.subtract(BigInteger.ONE);
		BigInteger exp = p1.divide(globalKeyQ);
		BigInteger tempg;
		Random random = new Random();
		do {
			tempg = new BigInteger(p1.bitLength(), random);
		} while (tempg.compareTo(p1) != -1 && tempg.compareTo(BigInteger.ONE) != 1);
		globalKeyG = tempg.modPow(exp, p);
	}

	public Pair<BigInteger, BigInteger> getPrivateKey() {
		// Private key
		BigInteger privateKey = new BigInteger(getGlobalKeyQ().bitLength(), new SecureRandom());
		while (privateKey.compareTo(globalKeyQ) != -1) {
			privateKey = new BigInteger(getGlobalKeyQ().bitLength(), new SecureRandom());
		}
		// Public key
		BigInteger publicKey = getGlobalKeyG().modPow(privateKey, getGlobalKeyP());
		return new Pair<>(privateKey, publicKey);
	}

	public BigInteger getGlobalKeyP() {
		return globalKeyP;
	}

	public BigInteger getGlobalKeyQ() {
		return globalKeyQ;
	}

	public BigInteger getGlobalKeyG() {
		return globalKeyG;
	}
}
