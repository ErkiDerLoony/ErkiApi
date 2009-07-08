/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki’s API.
 * 
 * Erki’s API is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package erki.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class provides static utility methods from a cryptographic context.
 * 
 * @author Edgar Kalkowski
 */
public class Crypto {
    
    /**
     * Computes the SHA-512 sum of a given string message.
     * 
     * @param message
     *        The message whose SHA-512 sum shall be computed.
     * @return The SHA-512 sum of {@code message}.
     * @throws NoSuchAlgorithmException
     *         if the java vm used does not implement the SHA-512 algorithm.
     */
    public static String toSHA(String message) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.update(message.getBytes());
        return toHexString(digest.digest());
    }
    
    /**
     * Computes the md5 sum of a message.
     * 
     * @param message
     *        The string whose md5 shall be computed.
     * @return The md5 sum of {@code message}.
     * @throws NoSuchAlgorithmException
     *         if the java vm used does not implement the md5 algorithm.
     */
    public static String toMD5(String message) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(message.getBytes());
        return toHexString(digest.digest());
    }
    
    /**
     * Converts a byte to a hexadecimal string representation. Is only used by
     * {@link #toHexString(byte[])}.
     * 
     * @param b
     *        The byte to convert.
     * @param buffer
     *        The {@link StringBuffer} into which the string representation of the byte's hex value
     *        is printed.
     */
    private static void byte2hex(byte b, StringBuffer buffer) {
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f' };
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buffer.append(hexChars[high]);
        buffer.append(hexChars[low]);
    }
    
    /**
     * Takes a byte array and converts it into a string representation.
     * 
     * @param bytes
     *        The byte array to convert.
     * @return A string describing the hexadecimal values of the byte array.
     */
    public static String toHexString(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        int len = bytes.length;
        
        for (int i = 0; i < len; i++) {
            byte2hex(bytes[i], buffer);
        }
        
        return buffer.toString();
    }
    
    /**
     * This method takes a hex string (as for example generated by {@link #toHexString(byte[])}) and
     * converts it back to a byte array.
     * 
     * @param hex
     *        The hex string.
     * @return A byte array representing the same values that the string described.
     */
    public static byte[] toBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        
        for (int i = 0; i < hex.length() / 2; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        
        return bytes;
    }
}
