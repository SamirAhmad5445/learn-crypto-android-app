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

    public static class Substitution {
        public final static String CIPHER_NAME = "Substitution Cipher";
        public static String encrypt(String s) {
            String ciphertext = "";
            s = s.toLowerCase();

            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);

                if (c == 'a')
                    ciphertext += 'X';
                else if (c == 'b')
                    ciphertext += 'N';
                else if (c == 'c')
                    ciphertext += 'Y';
                else if (c == 'd')
                    ciphertext += 'A';
                else if (c == 'e')
                    ciphertext += 'H';
                else if (c == 'f')
                    ciphertext += 'P';
                else if (c == 'g')
                    ciphertext += 'O';
                else if (c == 'h')
                    ciphertext += 'G';
                else if (c == 'i')
                    ciphertext += 'Z';
                else if (c == 'j')
                    ciphertext += 'Q';
                else if (c == 'k')
                    ciphertext += 'W';
                else if (c == 'l')
                    ciphertext += 'B';
                else if (c == 'm')
                    ciphertext += 'T';
                else if (c == 'n')
                    ciphertext += 'S';
                else if (c == 'o')
                    ciphertext += 'F';
                else if (c == 'p')
                    ciphertext += 'L';
                else if (c == 'q')
                    ciphertext += 'R';
                else if (c == 'r')
                    ciphertext += 'C';
                else if (c == 's')
                    ciphertext += 'V';
                else if (c == 't')
                    ciphertext += 'M';
                else if (c == 'u')
                    ciphertext += 'U';
                else if (c == 'v')
                    ciphertext += 'E';
                else if (c == 'w')
                    ciphertext += 'K';
                else if (c == 'x')
                    ciphertext += 'J';
                else if (c == 'y')
                    ciphertext += 'D';
                else if (c == 'z')
                    ciphertext += 'I';
                else
                    ciphertext += c;
            }
            return ciphertext;
        }
        public static String decrypt(String c) {
            String plaintext = "";
            c = c.toUpperCase();

            char p;
            for (int i = 0; i < c.length(); i++) {
                p = c.charAt(i);

                if (p == 'A')
                    plaintext += 'd';
                else if (p == 'B')
                    plaintext += 'l';
                else if (p == 'C')
                    plaintext += 'r';
                else if (p == 'D')
                    plaintext += 'y';
                else if (p == 'E')
                    plaintext += 'v';
                else if (p == 'F')
                    plaintext += 'o';
                else if (p == 'G')
                    plaintext += 'h';
                else if (p == 'H')
                    plaintext += 'e';
                else if (p == 'I')
                    plaintext += 'z';
                else if (p == 'J')
                    plaintext += 'x';
                else if (p == 'K')
                    plaintext += 'w';
                else if (p == 'L')
                    plaintext += 'p';
                else if (p == 'M')
                    plaintext += 't';
                else if (p == 'N')
                    plaintext += 'b';
                else if (p == 'O')
                    plaintext += 'g';
                else if (p == 'P')
                    plaintext += 'f';
                else if (p == 'Q')
                    plaintext += 'j';
                else if (p == 'R')
                    plaintext += 'q';
                else if (p == 'S')
                    plaintext += 'n';
                else if (p == 'T')
                    plaintext += 'm';
                else if (p == 'U')
                    plaintext += 'u';
                else if (p == 'V')
                    plaintext += 's';
                else if (p == 'W')
                    plaintext += 'k';
                else if (p == 'X')
                    plaintext += 'a';
                else if (p == 'Y')
                    plaintext += 'c';
                else if (p == 'Z')
                    plaintext += 'i';
                else
                    plaintext += c;
            }
            return plaintext;
        }
    }

    public static class Vigenere {
        public final static String CIPHER_NAME = "Vigenere Cipher";

        static String encrypt(String plaintext, String key) {
            String ciphertext = "";
            plaintext = plaintext.toUpperCase();
            key = key.toUpperCase();

            if (key.length() == 1) {
                char k = key.charAt(0);
                return Shift.encrypt(plaintext, k);
            } else {
                int j = 0;
                for (int i = 0; i < plaintext.length(); i++) {
                    int p = plaintext.charAt(i);
                    int k = key.charAt(j++);

                    if (p < 65 || p > 90)
                        ciphertext += (char) p;
                    else {
                        int ch = k + p;
                        ch %= 26;
                        ch += 65;
                        ciphertext += (char) ch;
                    }
                    if (j == key.length())
                        j = 0;
                }
            }
            return ciphertext;
        }

        static String decrypt(String ciphertext, String key) {
            String plaintext = "";
            ciphertext = ciphertext.toUpperCase();
            key = key.toUpperCase();

            if (key.length() == 1) {
                char k = key.charAt(0);
                return Shift.decrypt(ciphertext, k);
            } else {
                int j = 0;
                for (int i = 0; i < ciphertext.length(); i++) {
                    int c = ciphertext.charAt(i);
                    int k = key.charAt(j++);
                    if (c < 65 || c > 90)
                        plaintext += (char) c;
                    else {
                        int ch = c - k + 26;
                        ch %= 26;
                        ch += 65;
                        plaintext += (char) ch;
                    }
                    if (j == key.length())
                        j = 0;
                }

            }
            return plaintext;
        }
    }

    public static class Permutation {
        public final static String CIPHER_NAME = "Permutation Cipher";

        public static String encrypt(String message, int key[]) {
            String cipherText = "";
            message = message.toLowerCase();

            while (message.length() % key.length != 0)
                message += '_';

            for (int i = 0; i < message.length(); i += key.length) {
                for (int j = 0; j < key.length; j++)
                    cipherText += message.charAt(key[j] - 1 + i);
            }

            String result = "";
            for (int i = 0; i < cipherText.length(); i++) {
                if (cipherText.charAt(i) != '_')
                    result += cipherText.charAt(i);
            }

            return result;
        }

        public static String decrypt(String cipherText, int[] key) {
            String message = "";
            cipherText = cipherText.toLowerCase();

            while (cipherText.length() % key.length != 0)
                cipherText += '_';

            for (int i = 0; i < cipherText.length(); i += key.length) {
                for (int j = 0; j < key.length; j++)
                    message += cipherText.charAt(key[j] - 1 + i);
            }

            String result = "";
            for (int i = 0; i < message.length(); i++) {
                if (message.charAt(i) != '_')
                    result += message.charAt(i);
            }

            return result;
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
