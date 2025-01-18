package com.dencode.logic.dencoder;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

import org.mifmi.commons4j.util.StringUtilz;

import com.dencode.logic.dencoder.annotation.Dencoder;
import com.dencode.logic.dencoder.annotation.DencoderFunction;
import com.dencode.logic.model.DencodeCondition;

@Dencoder(type = "string", method = "string.base58", hasEncoder = true, hasDecoder = true, useOe = true, useNl = true)
public class StringBase58Dencoder {

    private static final char[] BASE58_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final BigInteger BASE58 = BigInteger.valueOf(58);

    private StringBase58Dencoder() {
        // NOP
    }

    @DencoderFunction
    public static String encStrBase58(DencodeCondition cond) {
        byte[] binValue = cond.valueAsBinary();
        return encodeBase58(binValue);
    }

    @DencoderFunction
    public static String decStrBase58(DencodeCondition cond) {
        return decodeBase58(cond.value(), cond.charset());
    }

    private static String encodeBase58(byte[] input) {
        BigInteger value = new BigInteger(1, input);
        StringBuilder encoded = new StringBuilder();

        // Convert to Base58
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divAndRem = value.divideAndRemainder(BASE58);
            value = divAndRem[0];
            encoded.append(BASE58_ALPHABET[divAndRem[1].intValue()]);
        }

        // Preserve leading zeros
        for (byte b : input) {
            if (b == 0) {
                encoded.append(BASE58_ALPHABET[0]);
            } else {
                break;
            }
        }

        return encoded.reverse().toString();
    }

    private static String decodeBase58(String input, Charset charset) {
        if (!StringUtilz.isASCII(input)) {
            return null; // Base58 strings are ASCII
        }

        BigInteger value = BigInteger.ZERO;

        // Decode Base58 string
        for (char c : input.toCharArray()) {
            int digit = indexOfBase58Char(c);
            if (digit == -1) {
                return null; // Invalid character
            }
            value = value.multiply(BASE58).add(BigInteger.valueOf(digit));
        }

        // Convert BigInteger back to byte array
        byte[] decoded = value.toByteArray();

        // Remove leading zero byte added by BigInteger
        if (decoded.length > 1 && decoded[0] == 0) {
            byte[] trimmed = new byte[decoded.length - 1];
            System.arraycopy(decoded, 1, trimmed, 0, trimmed.length);
            decoded = trimmed;
        }

        // Preserve leading zeros
        int leadingZeros = 0;
        for (char c : input.toCharArray()) {
            if (c == BASE58_ALPHABET[0]) {
                leadingZeros++;
            } else {
                break;
            }
        }

        byte[] result = new byte[leadingZeros + decoded.length];
        System.arraycopy(decoded, 0, result, leadingZeros, decoded.length);

        return new String(result, charset);
    }

    private static int indexOfBase58Char(char c) {
        for (int i = 0; i < BASE58_ALPHABET.length; i++) {
            if (BASE58_ALPHABET[i] == c) {
                return i;
            }
        }
        return -1; // Character not found in Base58 alphabet
    }
}
