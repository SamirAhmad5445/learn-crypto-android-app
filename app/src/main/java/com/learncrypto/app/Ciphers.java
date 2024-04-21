package com.learncrypto.app;

public class Ciphers {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Ciphers() {}

    public static class Shift {
        public final static String CIPHER_NAME = "Shift Cipher";

        public static String encrypt(String s, char k) {
            String ciphertext = "";
            s = s.toLowerCase();
            if ((k >= 'A' && k <= 'Z')) {
                k -= 'A';
                k += 'a';
            }

            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if (c >= 'a' && c <= 'z') {
                    int x = c - 'a';
                    int y = k - 'a';
                    int z = (x + y) % 26;
                    z += 'a';
                    ciphertext += (char) z;
                } else
                    ciphertext += c;
            }
            return ciphertext;
        }

        public static String decrypt(String c, char k) {
            String plaintext = "";
            c = c.toLowerCase();
            if ((k >= 'A' && k <= 'Z')) {
                k -= 'A';
                k += 'a';
            }

            char p;
            for (int i = 0; i < c.length(); i++) {
                p = c.charAt(i);
                if (p >= 'a' && p <= 'z') {
                    int x = p - 'a';
                    int y = k - 'a';
                    int z = (x - y) % 26;
                    z += 'a';
                    plaintext += (char) z;
                } else
                    plaintext += c;
            }
            return plaintext;
        }
    }

    public static class Affine {
        public final static String CIPHER_NAME = "Affine Cipher";

        public static String encrypt(String s, char a, char b) { // plain && key
            String ciphertext = "";
            s = s.toLowerCase();
            if ((a >= 'A' && a <= 'Z')) {
                a -= 'A';
                a += 'a';
            }
            if ((b >= 'A' && b <= 'Z')) {
                b -= 'A';
                b += 'a';
            }

            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if ((c >= 'a' && c <= 'z')) {
                    int x = c - 'a';
                    int ka = a - 'a';
                    int kb = b - 'a';
                    int z = (ka * x + kb) % 26;
                    z += 'a';
                    ciphertext += (char) z;
                } else
                    ciphertext += c;
            }
            return ciphertext;
        }

        public static String decrypt(String c, char a, char b) {
            String plaintext = "";
            c = c.toLowerCase();
            if ((a >= 'A' && a <= 'Z')) {
                a -= 'A';
                a += 'a';
            }
            if ((b >= 'A' && b <= 'Z')) {
                b -= 'A';
                b += 'a';
            }

            char p;
            for (int i = 0; i < c.length(); i++) {
                p = c.charAt(i);
                if (p >= 'a' && p <= 'z') {
                    int x = p - 'a';
                    int ka = a - 'a';
                    int kb = b - 'a';

                    int inv = -1;
                    if (isKeyInvertible(a)) {
                        for (int j = 1; j < 26; j++) {
                            if ((ka * j % 26) == 1) {
                                inv = j;
                                break;
                            }
                        }
                    } else {
                        return "";
                    }

                    int z = (inv * (x - kb + 26)) % 26;
                    z += 'a';
                    plaintext += (char) z;
                } else
                    plaintext += c;
            }
            return plaintext;
        }

        public static boolean isKeyInvertible(char a) {
            int ka = a - 'a';
            return Utils.GCD(26, ka) == 1;
        }
    }

    public static class Utils {
        public static int GCD(int m, int n) {
            if (n == 0)
                return m;
            int r = m % n;
            return GCD(n, r);
        }
    }

}
