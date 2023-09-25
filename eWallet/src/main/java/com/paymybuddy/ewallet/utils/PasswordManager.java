package com.paymybuddy.ewallet.utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

/**
 * A class that contains a set of methods for hashing, salting, encrypting and
 * decrypting passwords.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Component
public class PasswordManager {

	/**
	 * A method that generate a random 16 bytes array using SHA1PRNG. A salt is a
	 * random "word" juxtaposed to a password after encryption.
	 *
	 * @return A <code>byte</code> array.
	 */
	public byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);

		return salt;
	}

	/**
	 * A method that converts a <code>byte</code> array into an usable
	 * <code>String</code>
	 *
	 * @return A <code>String</code>.
	 */
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

	/**
	 * A method that converts an usable <code>String</code> into a <code>byte</code>
	 * array.
	 *
	 * @return A <code>byte</code> array.
	 */
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
		byte[] bytes = new byte[hex.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}

	/**
	 * A method that encrypts a non-encrypted password using PBKDF2WithHmacSHA1, then
	 * juxtaposes iterations and salt before the resulting <code>String</code>,
	 * separating them with colons.
	 *
	 * @return A <code>String</code>.
	 */
	public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		int iterations = 1000;
		byte[] salt = getSalt();

		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, 64 * 8);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();

		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}

	/**
	 * This method splits password retrieved from database using colons as separators
	 * to recover the iterations, the salt and the encrypted password. It then
	 * encrypts the password passed as first parameter, juxtaposes the recovered
	 * salt and the iteration and compares the recult byte by byte with the password
	 * stored in the database (second parameter).
	 *
	 * @singularity <code>^</code> bitwise operator is an EOR. If two bytes are the
	 *              same, the result is 0. <code>|</code> bitwise operator is an OR.
	 *              If <code>diff |= value</code>, <code>diff
	 *              is equal to 0 only if both diff and something are equals to 0.
	 *              ==</code> results true if diff is equal to 0, false if it's not.
	 *
	 * @return A <code>boolean</code>.
	 */
	public boolean checkPassword(String passwordInput, String passwordStored) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String[] passwordStoredParts = passwordStored.split(":");

		int passwordStoredIterations = Integer.parseInt(passwordStoredParts[0]);
		byte[] passwordStoredSalt = fromHex(passwordStoredParts[1]);
		byte[] passwordStoredHash = fromHex(passwordStoredParts[2]);

		PBEKeySpec pbeKeySpec = new PBEKeySpec(passwordInput.toCharArray(), passwordStoredSalt,
				passwordStoredIterations, passwordStoredHash.length * 8);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		byte[] passwordInputHash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
 
		int diff = passwordStoredHash.length ^ passwordInputHash.length;
		for (int i = 0; i < passwordStoredHash.length && i < passwordInputHash.length; i++) {
			diff |= passwordStoredHash[i] ^ passwordInputHash[i];
		}

		return diff == 0;
	}
}