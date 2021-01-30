package com.jobportal.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encrypt {
	
	private String data;
	private final String algorithm = "SHA-256";
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	/**
	 * Method to encrypt an input using SHA-256 and salt
	 * @param data - what you want to be encrypted
	 * @param algorithm - SHA-256
	 * @param salt - randomly generated string to increase security
	 * @return - array {encrypted data, salt for reference}
	 * @throws NoSuchAlgorithmException - should never be thrown
	 */
	public static String[] generateHash(String data, String algorithm, byte[] salt) throws NoSuchAlgorithmException{
		//automagically encrypts the data
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		digest.update(salt);
		byte[] hash = digest.digest(data.getBytes());
		//the hash and salt are digested into a byte array, so we must convert to string for ease of storage
		String stringHash = bytesToStringHex(hash);
		String stringSalt = bytesToStringHex(salt);
		String[] hashAndSalt = {stringHash, stringSalt};
		return hashAndSalt;
	}
	
	/**
	 * Method to encrypt an input without salt
	 * @param data - to be encrypted
	 * @param algorithm - SHA-256
	 * @return - encrypted data
	 * @throws NoSuchAlgorithmException - should never be thrown
	 */
	public static String generateHash(String data, String algorithm) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		byte[] hash = digest.digest(data.getBytes());
		String generatedHash = bytesToStringHex(hash);
		return generatedHash;
	}
	
	/**
	 * Method to convert from a byte array to a string 
	 * @param bytes - byte array you wish to stringify
	 * @return - String representation of the byte array
	 */
	public static String bytesToStringHex(byte[] bytes) {
		//honestly I have no clue how this works
		char[] hexChars = new char[bytes.length * 2];
		for(int j=0; j< bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j*2] = hexArray[v >>> 4];
			hexChars[j*2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * Method to convert from hexidecimal string to byte array
	 * @param s - hexidecimal string you wish to convert to byte array
	 * @return - byte array representation of the hexidecimal string
	 */
	public static byte[] hexStringToByteArray(String s) {
		//again, no real clue what's going on here
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	/**
	 * Method to create a randomly generated salt in byte array form
	 * @return - byte array of random bytes used to encrypt
	 */
	public static byte[] createSalt() {
		byte[] bytes = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}
	
}
