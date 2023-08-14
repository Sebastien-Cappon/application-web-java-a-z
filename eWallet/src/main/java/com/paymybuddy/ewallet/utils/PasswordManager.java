package com.paymybuddy.ewallet.utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

@Component
public class PasswordManager {

	public byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);

		return salt;
	}

	private static String toHex(byte[] array) throws NoSuchAlgorithmException {
		BigInteger bigInteger = new BigInteger(1, array);
		String hex = bigInteger.toString(16);

		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
		byte[] bytes = new byte[hex.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}

	public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		int iterations = 1000;
		byte[] salt = getSalt();

		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, 64 * 8);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();

		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}

	public boolean checkPassword(String passwordInput, String passwordStored) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String[] passwordStoredParts = passwordStored.split(":");

		int passwordStoredIterations = Integer.parseInt(passwordStoredParts[0]);
		byte[] passwordStoredSalt = fromHex(passwordStoredParts[1]);
		byte[] passwordStoredHash = fromHex(passwordStoredParts[2]);

		PBEKeySpec pbeKeySpec = new PBEKeySpec(passwordInput.toCharArray(), passwordStoredSalt, passwordStoredIterations, passwordStoredHash.length * 8);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		byte[] passwordInputHash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();

		// ^ bitwise operator is an EOR. If two bytes are the same, the result is 0. 
		// | bitwise operator is an OR. If diff |= value, diff is equal to 0 only if both diff and something are equals to 0.
		int diff = passwordStoredHash.length ^ passwordInputHash.length;
		for (int i = 0; i < passwordStoredHash.length && i < passwordInputHash.length; i++) {
			diff |= passwordStoredHash[i] ^ passwordInputHash[i];
		}

		// == results true if diff is equal to 0, false if it's not.
		// These 2 new notions are probably the most interesting ones of the Project 6 backend.
		return diff == 0;
	}
}
