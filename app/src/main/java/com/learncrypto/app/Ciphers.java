package com.learncrypto.app;

import java.util.ArrayList;

public class Ciphers {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private as the db helper class.
    private Ciphers() {}

    public static class Shift {
        public final static String CIPHER_NAME = "Shift Cipher";

        static String encrypt(String s, char k) {
            return Affine.encrypt(s, 'b', k);
        }

        static String decrypt(String s, char k) {
            return Affine.decrypt(s, 'b', k);
        }
    }

    public static class Affine {
        public final static String CIPHER_NAME = "Affine Cipher";

        public static String encrypt(String s, char a, char b) {
            StringBuilder ciphertext = new StringBuilder();
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
                    ciphertext.append((char) z);
                } else
                    ciphertext.append(c);
            }
            return ciphertext.toString();
        }
        public static String decrypt(String c, char a, char b) {
            StringBuilder plaintext = new StringBuilder();
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
                    if (Utils.GCD(26, ka) == 1) {
                        for (int j = 1; j < 26; j++) {
                            if ((ka * j % 26) == 1) {
                                inv = j;
                                break;
                            }
                        }
                    } else {
                        System.out.println("Error!! Inverse isn't exist.");
                        return "";
                    }

                    int z = (inv * (x - kb + 26)) % 26;
                    z += 'a';
                    plaintext.append((char) z);
                } else
                    plaintext.append(p);
            }
            return plaintext.toString();
        }

        public static boolean isKeyInvertible(char a)  {
            int ka = a - 'a';
            return Utils.GCD(26, ka) == 1;
        }

        public static String getKeyInverse(String key) {
            int ka = key.charAt(0) - 'a';

            int inv = -1;
            if (Utils.GCD(26, ka) == 1) {
                for (int j = 1; j < 26; j++) {
                    if ((ka * j % 26) == 1) {
                        inv = j;
                        break;
                    }
                }
            } else {
                System.out.println("Error!! Inverse isn't exist.");
                return "";
            }
            char a = (char)(inv + 'a');
            return String.valueOf(a);
        }
    }

    public static class Substitution {
        public final static String CIPHER_NAME = "Substitution Cipher";

        public final static String[] ALPHABET = {
                "A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z"
        };

        public final static String[] ENCRYPTION_PERMUTATION = {
                "X", "N", "Y", "A", "H", "P", "O", "G", "Z", "Q",
                "W", "B", "T", "S", "F", "L", "R", "C", "V", "M",
                "U", "E", "K", "J", "D", "I"
        };

        public final static String[] DECRYPTION_PERMUTATION = {
                "D", "L", "R", "Y", "V", "O", "H", "E", "Z", "X",
                "W", "P", "T", "B", "G", "F", "J", "Q", "N", "M",
                "U", "S", "K", "A", "C", "I"
        };

        public static String encrypt(String s) {
            StringBuilder ciphertext = new StringBuilder();
            s = s.toLowerCase();

            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);

                if (c == 'a')
                    ciphertext.append('X');
                else if (c == 'b')
                    ciphertext.append('N');
                else if (c == 'c')
                    ciphertext.append('Y');
                else if (c == 'd')
                    ciphertext.append('A');
                else if (c == 'e')
                    ciphertext.append('H');
                else if (c == 'f')
                    ciphertext.append('P');
                else if (c == 'g')
                    ciphertext.append('O');
                else if (c == 'h')
                    ciphertext.append('G');
                else if (c == 'i')
                    ciphertext.append('Z');
                else if (c == 'j')
                    ciphertext.append('Q');
                else if (c == 'k')
                    ciphertext.append('W');
                else if (c == 'l')
                    ciphertext.append('B');
                else if (c == 'm')
                    ciphertext.append('T');
                else if (c == 'n')
                    ciphertext.append('S');
                else if (c == 'o')
                    ciphertext.append('F');
                else if (c == 'p')
                    ciphertext.append('L');
                else if (c == 'q')
                    ciphertext.append('R');
                else if (c == 'r')
                    ciphertext.append('C');
                else if (c == 's')
                    ciphertext.append('V');
                else if (c == 't')
                    ciphertext.append('M');
                else if (c == 'u')
                    ciphertext.append('U');
                else if (c == 'v')
                    ciphertext.append('E');
                else if (c == 'w')
                    ciphertext.append('K');
                else if (c == 'x')
                    ciphertext.append('J');
                else if (c == 'y')
                    ciphertext.append('D');
                else if (c == 'z')
                    ciphertext.append('I');
                else
                    ciphertext.append(c);
            }
            return ciphertext.toString();
        }

        public static String decrypt(String c) {
            StringBuilder plaintext = new StringBuilder();
            c = c.toUpperCase();

            char p;
            for (int i = 0; i < c.length(); i++) {
                p = c.charAt(i);

                if (p == 'A')
                    plaintext.append('d');
                else if (p == 'B')
                    plaintext.append('l');
                else if (p == 'C')
                    plaintext.append('r');
                else if (p == 'D')
                    plaintext.append('y');
                else if (p == 'E')
                    plaintext.append('v');
                else if (p == 'F')
                    plaintext.append('o');
                else if (p == 'G')
                    plaintext.append('h');
                else if (p == 'H')
                    plaintext.append('e');
                else if (p == 'I')
                    plaintext.append('z');
                else if (p == 'J')
                    plaintext.append('x');
                else if (p == 'K')
                    plaintext.append('w');
                else if (p == 'L')
                    plaintext.append('p');
                else if (p == 'M')
                    plaintext.append('t');
                else if (p == 'N')
                    plaintext.append('b');
                else if (p == 'O')
                    plaintext.append('g');
                else if (p == 'P')
                    plaintext.append('f');
                else if (p == 'Q')
                    plaintext.append('j');
                else if (p == 'R')
                    plaintext.append('q');
                else if (p == 'S')
                    plaintext.append('n');
                else if (p == 'T')
                    plaintext.append('m');
                else if (p == 'U')
                    plaintext.append('u');
                else if (p == 'V')
                    plaintext.append('s');
                else if (p == 'W')
                    plaintext.append('k');
                else if (p == 'X')
                    plaintext.append('a');
                else if (p == 'Y')
                    plaintext.append('c');
                else if (p == 'Z')
                    plaintext.append('i');
                else
                    plaintext.append(p);
            }
            return plaintext.toString();
        }
    }

    public static class Permutation {
        public final static String CIPHER_NAME = "Permutation Cipher";

        static String Check(int[] key) {
            int n = 1, b = 0;
            for (int i = 0; i < key.length; i++) {
                b = 0;
                for (int k : key)
                    if (k == n) {
                        b = 1;
                        break;
                    }
                n++;
            }
            if (b == 1)
                return " ";
            return "Permutation Key is Invalid";
        }

        static String encrypt(String s, String k) {
            String[] k1 = k.split(" ");

            ArrayList<Integer> al = new ArrayList<>();
            for (String a : k1) {
                if (!a.isEmpty())
                    al.add(Integer.parseInt(a) % 26);
            }

            int[] key = new int[al.size()];
            for (int i = 0; i < key.length; i++)
                key[i] = al.get(i);

            return Encrypt(s, key);
        }

        static String Encrypt(String message, int[] key) {
            StringBuilder cipherText = new StringBuilder();
            message = message.toLowerCase();

            String checkResult = Check(key);
            if (!checkResult.equals(" ")) {
                return checkResult;
            }

            for (int k : key) {
                if (k > key.length) {
                    return "Input of The Key is Out The Range";
                }
            }

            StringBuilder messageBuilder = new StringBuilder(message);
            while (messageBuilder.length() % key.length != 0)
                messageBuilder.append('_');
            message = messageBuilder.toString();

            for (int i = 0; i < message.length(); i += key.length) {
                for (int k : key) cipherText.append(message.charAt(k - 1 + i));
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < cipherText.length(); i++)
                if (cipherText.charAt(i) != '_')
                    result.append(cipherText.charAt(i));

            return result.toString();
        }

        static int[] getInverseKey(int[] key) {
            int[] inverseKey = new int[key.length];
            for (int i = 0; i < key.length; i++) {
                inverseKey[key[i] - 1] = i + 1;
            }
            return inverseKey;
        }

        static String decrypt(String s, String k) {
            String[] k1 = k.split(" ");

            ArrayList<Integer> al = new ArrayList<>();
            for (String a : k1) {
                if (!a.isEmpty())
                    al.add(Integer.parseInt(a) % 26);
            }

            int[] key = new int[al.size()];
            for (int i = 0; i < key.length; i++)
                key[i] = al.get(i);

            return Decrypt(s, key);
        }

        static String Decrypt(String cipherText, int[] key) {
            StringBuilder message = new StringBuilder();
            cipherText = cipherText.toLowerCase();

            String checkResult = Check(key);
            if (!checkResult.equals(" ")) {
                return checkResult;
            }

            for (int k : key) {
                if (k > key.length) {
                    return "Input of The Key is Out The Range";
                }
            }

            StringBuilder cipherTextBuilder = new StringBuilder(cipherText);
            while (cipherTextBuilder.length() % key.length != 0)
                cipherTextBuilder.append('_');
            cipherText = cipherTextBuilder.toString();

            int[] inverseKey = getInverseKey(key);

            for (int i = 0; i < cipherText.length(); i += key.length) {
                for (int j = 0; j < key.length; j++)
                    message.append(cipherText.charAt(inverseKey[j] - 1 + i));
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < message.length(); i++)
                if (message.charAt(i) != '_')
                    result.append(message.charAt(i));

            return result.toString();
        }

        public static int[] getPermutationKeyFromString(String keyString) {
            String[] bits = keyString.split(" ");
            int[] key = new int[bits.length];
            for(int i = 0; i < bits.length; i++) {
                key[i] = Integer.parseInt(bits[i]);
            }

            return key;
        }

        public static String getPermutationKeyFromArray(int[] key) {
            StringBuilder keyString = new StringBuilder();

            for (int bit : key)
                keyString.append(bit).append(" ");

            return keyString.toString().trim();
        }
    }

    public static class Vigenere {
        public final static String CIPHER_NAME = "Vigenere Cipher";

        public static String encrypt(String plaintext, String key) {
            StringBuilder ciphertext = new StringBuilder();
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
                        ciphertext.append((char) p);
                    else {
                        int ch = k + p;
                        ch %= 26;
                        ch += 65;
                        ciphertext.append((char) ch);
                    }
                    if (j == key.length())
                        j = 0;
                }
            }
            return ciphertext.toString();
        }

        public static String decrypt(String ciphertext, String key) {
            StringBuilder plaintext = new StringBuilder();
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
                        plaintext.append((char) c);
                    else {
                        int ch = c - k + 26;
                        ch %= 26;
                        ch += 65;
                        plaintext.append((char) ch);
                    }
                    if (j == key.length())
                        j = 0;
                }

            }
            return plaintext.toString();
        }
    }

    public static class Hill {
        private static int size;

        public final static String CIPHER_NAME = "Hill Cipher";

        static String encrypt(String str, String k) {
            String[] k1 = k.split(" ");
            int[][] key;
            int length;
            if (k1.length == 4) {
                key = new int[2][2];
                length = 2;
            } else if (k1.length == 9) {
                key = new int[3][3];
                length = 3;
            } else
                return "Invalid Key";

            StringBuilder s = new StringBuilder();
            str = str.toUpperCase();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                    s.append(str.charAt(i));
            }

            int cnt = 0;
            for (int i = 0; i < length; i++)
                for (int j = 0; j < length; j++)
                    key[i][j] = Integer.parseInt(k1[cnt++]) % 26;

            return Encrypt(s.toString(), key);
        }

        static String Encrypt(String plaintext, int[][] keyMatrix) {
            size = plaintext.length();
            int blockSize = keyMatrix.length;
            plaintext = plaintext.toLowerCase();
            StringBuilder ciphertext = new StringBuilder();

            StringBuilder plaintextBuilder = new StringBuilder(plaintext);
            while (plaintextBuilder.length() % keyMatrix.length != 0)
                plaintextBuilder.append('a');
            plaintext = plaintextBuilder.toString();

            for (int i = 0; i < plaintext.length(); i += blockSize) {
                int[] block = new int[blockSize];

                for (int j = 0; j < blockSize; j++) // act
                    block[j] = plaintext.charAt(i + j) - 'a';

                for (int j = 0; j < blockSize; j++) {
                    int sum = 0;
                    for (int k = 0; k < blockSize; k++)
                        sum += block[k] * keyMatrix[k][j];
                    sum += 26;
                    sum %= 26;
                    sum += 'a';
                    ciphertext.append((char) sum);
                }
            }
            return ciphertext.toString();
        }

        static String decrypt(String str, String k) {
            String[] k1 = k.split(" ");
            int[][] key;
            int length;
            if (k1.length == 4) {
                key = new int[2][2];
                length = 2;
            } else if (k1.length == 9) {
                key = new int[3][3];
                length = 3;
            } else
                return "Invalid Key";

            StringBuilder s = new StringBuilder();
            str = str.toUpperCase();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                    s.append(str.charAt(i));
            }

            int cnt = 0;
            for (int i = 0; i < length; i++)
                for (int j = 0; j < length; j++)
                    key[i][j] = Integer.parseInt(k1[cnt++]) % 26;

            return Decrypt(s.toString(), key);
        }

        static String Decrypt(String ciphertext, int[][] keyMatrix) {

            ciphertext = ciphertext.toLowerCase();
            if (keyMatrix.length == 2 || keyMatrix.length == 3) {
                int blockSize = keyMatrix.length;
                StringBuilder plaintext = new StringBuilder();

                StringBuilder ciphertextBuilder = new StringBuilder(ciphertext);
                while (ciphertextBuilder.length() % keyMatrix.length != 0)
                    ciphertextBuilder.append('a');
                ciphertext = ciphertextBuilder.toString();

                for (int i = 0; i < ciphertext.length(); i += blockSize) {
                    int[] block = new int[blockSize];

                    for (int j = 0; j < blockSize; j++)
                        block[j] = ciphertext.charAt(i + j) - 'a';

                    int[][] keyInverse = invertMatrix(keyMatrix);
                    for (int j = 0; j < blockSize; j++) {
                        int sum = 0;
                        for (int k = 0; k < blockSize; k++) { // t_
                            assert keyInverse != null;
                            sum += block[k] * keyInverse[k][j]; //
                        }
                        sum += 26;
                        sum %= 26;
                        sum += 'a';
                        plaintext.append((char) sum);
                    }
                }
                StringBuilder res = new StringBuilder();
                for (int i = 0; i < size; i++)
                    res.append(plaintext.charAt(i));
                return res.toString();
            }
            return "";
        }

        static int det(int a, int b, int c, int d) {
            return (((a * d) % 26) - ((b * c) % 26) + 26) % 26;
        }

        static int getInverse(int a) {
            if (Utils.GCD(a, 26) == 1) {
                for (int j = 1; j < 26; j++) {
                    if ((a * j) % 26 == 1)
                        return j;
                }
            }
            return -1;
        }

        static int[][] invertMatrix(int[][] k) {
            if (k.length == 2) {
                int det = det(k[0][0], k[0][1], k[1][0], k[1][1]);
                det %= 26;

                if (det == 0) {
                    System.out.println("Matrix isn't invertible!");
                    return null;
                }

                int inv2x2 = getInverse(det);
                if (inv2x2 == -1) {
                    System.out.println("Inverse isn't Exist");
                    return null;
                }

                int[][] inverse = new int[2][2];
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        inverse[0][0] = k[1][1] * inv2x2 % 26;
                        inverse[0][1] = (-k[0][1] + 26) * inv2x2 % 26;
                        inverse[1][0] = (-k[1][0] + 26) * inv2x2 % 26;
                        inverse[1][1] = k[0][0] * inv2x2 % 26;
                    }
                }
                return inverse;
            }

            int det = (k[0][0] * k[1][1] * k[2][2]) % 26
                    + (k[0][1] * k[1][2] * k[2][0]) % 26
                    + (k[0][2] * k[1][0] * k[2][1]) % 26
                    - (k[0][1] * k[1][0] * k[2][2]) % 26
                    - (k[0][0] * k[1][2] * k[2][1]) % 26
                    - (k[0][2] * k[1][1] * k[2][0]) % 26;
            det += 26;
            det %= 26;

            if (det == 0) {
                System.out.println("Matrix isn't invertible!");
                return null;
            }

            int inv3x3 = getInverse(det);
            if (inv3x3 == 0) {
                System.out.println("Inverse isn't Exist");
                return null;
            }

            // Calculate the adjugate of the matrix
            int[][] adjugate = new int[3][3];
            adjugate[0][0] = det(k[1][1], k[1][2], k[2][1], k[2][2]) % 26;
            adjugate[0][1] = (-det(k[1][0], k[1][2], k[2][0], k[2][2]) + 26) % 26;
            adjugate[0][2] = det(k[1][0], k[1][1], k[2][0], k[2][1]) % 26;
            adjugate[1][0] = (-det(k[0][1], k[0][2], k[2][1], k[2][2]) + 26) % 26;
            adjugate[1][1] = det(k[0][0], k[0][2], k[2][0], k[2][2]) % 26;
            adjugate[1][2] = (-det(k[0][0], k[0][1], k[2][0], k[2][1]) + 26) % 26;
            adjugate[2][0] = det(k[0][1], k[0][2], k[1][1], k[1][2]) % 26;
            adjugate[2][1] = (-det(k[0][0], k[0][2], k[1][0], k[1][2]) + 26) % 26;
            adjugate[2][2] = det(k[0][0], k[0][1], k[1][0], k[1][1]) % 26;

            // Calculate the inverse matrix
            int[][] inverse = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    inverse[i][j] = (adjugate[j][i] * inv3x3 + 26) % 26;
            }
            return inverse;
        }

        public static int[][] getHillKeyFromString(String keyString) {
            String[] bits = keyString.split(" ");
            int matrixSize = (int) Math.sqrt(bits.length);
            int[][] key = new int[matrixSize][matrixSize];
            int n = 0;

            for(int i = 0; i < matrixSize; i++) {
                for(int j = 0; j < matrixSize; j++) {
                    key[i][j] = Integer.parseInt(bits[n++]);
                }
            }

            return key;
        }

        public static String getHillKeyFromMatrix(int[][] key) {
            StringBuilder keyString = new StringBuilder();
            for (int[] row : key)
                for (int bit : row)
                    keyString.append(bit).append(" ");

            return keyString.toString().trim();
        }
    }

    public static class SPN {
        private static int size = 0;
        public final static String CIPHER_NAME = "SPN Cipher";
        private static final int[] s_Box = { 0xE, 0x4, 0xD, 0x1, 0x2, 0xF, 0xB, 0x8, 0x3, 0xA, 0x6, 0xC, 0x5, 0x9, 0x0, 0x7 };
        private static final int[] s_Box_Inverse = { 0xE, 0x3, 0x4, 0x8, 0x1, 0xC, 0xA, 0xF, 0x7, 0xD, 0x9, 0x6, 0xB, 0x2,
                0x0, 0x5 };

        private static final int[] p_Box = { 0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15 };

        static ArrayList<Integer> generateRoundKeys(int key) {
            ArrayList<Integer> roundKeys = new ArrayList<>();
            for (int i = 0; i < 11; ++i) {
                key = ((key << 1) | (key >> 15)) & 0xFFFF; // Circular left shift
                roundKeys.add(key);
            }
            return roundKeys;
        }

        public static int convertStringToHex(String input) {
            int result = 0;
            StringBuilder hexBuilder = new StringBuilder();
            input = input.toUpperCase();
            for (char c : input.toCharArray()) {
                // Get the ASCII value of the character and convert it to hexadecimal
                String hexValue = Integer.toHexString(c);
                // Append the hexadecimal value to the StringBuilder
                hexBuilder.append(hexValue.toUpperCase().charAt(1));
            }
            try {
                result = Integer.parseInt(hexBuilder.toString(), 16);
                return result;
            } catch (Exception err) {
                err.printStackTrace();
            }
            return result;
        }

        public static String convertHexToString(String hexString, String message) {
            StringBuilder sb = new StringBuilder();
            hexString = hexString.toUpperCase();
            for (int i = 0; i < hexString.length(); i++) {
                // Get each pair of hexadecimal characters
                String hexPair;
                if (message.charAt(i) >= '@' && message.charAt(i) <= 'O')
                    hexPair = "4" + hexString.charAt(i);
                else
                    hexPair = "5" + hexString.charAt(i);
                // Convert the hexadecimal pair to integer
                int decimalValue = Integer.parseInt(hexPair, 16);
                // Convert the integer to its corresponding character
                char c = (char) decimalValue;
                // Append the character to the StringBuilder
                sb.append(c);
            }
            return sb.toString();
        }

        static String Encrypt(String message, ArrayList<Integer> roundKeys) {
            int plaintext = convertStringToHex(message);

            int x = plaintext;
            int y = 0;
            for (int k = 0; x != 0; k++) {
                plaintext ^= roundKeys.get(0);

                for (int i = 0; i < roundKeys.size() - 2; ++i) {
                    // Substitution (S-box)
                    int sBoxOutput = 0;
                    for (int j = 0; j < 4; ++j) {
                        int nibble = (plaintext >> (j * 4)) & 0xF;
                        sBoxOutput |= s_Box[nibble] << (j * 4);
                    }

                    // Permutation (P-box)
                    int pBoxOutput = 0;
                    for (int j = 0; j < 16; j++) {
                        if ((sBoxOutput & (1 << j)) != 0) {
                            pBoxOutput |= (1 << p_Box[j]);
                        }
                    }

                    // Key addition
                    plaintext = pBoxOutput ^ roundKeys.get(i + 1);
                }

                int sBoxOutput = 0;
                for (int j = 0; j < 4; ++j) {
                    int nibble = (plaintext >> (j * 4)) & 0xF;
                    sBoxOutput |= s_Box[nibble] << (j * 4);
                }
                plaintext = sBoxOutput ^ roundKeys.get(roundKeys.size() - 1);

                y |= plaintext << (k * 16);
                x >>= 16;
            }

            return convertHexToString(Integer.toHexString(y), message);
        }

        static String Decrypt(String cipher, ArrayList<Integer> roundKeys) {
            int ciphertext = convertStringToHex(cipher);

            int x = ciphertext;
            int y = 0;
            for (int k = 0; x != 0; k++) {
                ciphertext ^= roundKeys.get(roundKeys.size() - 1);

                int sBoxOutput = 0;
                for (int j = 0; j < 4; ++j) {
                    int nibble = (ciphertext >> (j * 4)) & 0xF;
                    sBoxOutput |= s_Box_Inverse[nibble] << (j * 4);
                }

                ciphertext = sBoxOutput;

                for (int i = roundKeys.size() - 2; i > 0; i--) {
                    // Key addition
                    ciphertext ^= roundKeys.get(i);

                    // Inverse permutation (P-box)
                    int pBoxOutput = 0;
                    for (int j = 0; j < 16; ++j) {
                        if ((ciphertext & (1 << j)) != 0) {
                            pBoxOutput |= (1 << p_Box[j]);
                        }
                    }

                    // Inverse substitution (S-box)
                    sBoxOutput = 0;
                    for (int j = 0; j < 4; ++j) {
                        int nibble = (pBoxOutput >> (j * 4)) & 0xF;
                        sBoxOutput |= s_Box_Inverse[nibble] << (j * 4);
                    }

                    ciphertext = sBoxOutput;
                }
                ciphertext ^= roundKeys.get(0);

                y |= ciphertext << (k * 16);
                x >>= 16;
            }

            return convertHexToString(Integer.toHexString(y), cipher);
        }

        public static String encrypt(String Input, int key) {
            if (key > 99999999)
                return "The Key is Out of The Range";
            StringBuilder input = new StringBuilder();
            Input = Input.toUpperCase();
            for (int i = 0; i < Input.length(); i++) {
                if (Input.charAt(i) >= 'A' && Input.charAt(i) <= 'Z')
                    input.append(Input.charAt(i));
            }
            ArrayList<Integer> roundKeys = SPN.generateRoundKeys(key);
            size = input.length();
            while (input.length() % 4 != 0)
                input.append('_');

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < input.length(); i += 4) {
                String muh = input.substring(i, i + 4);
                muh = SPN.Encrypt(muh, roundKeys);
                sb.append(muh);
            }

            return sb.toString();
        }

        public static String decrypt(String input, int key) {
            if (key > 99999999)
                return "The Key is Out of The Range";

            ArrayList<Integer> roundKeys = SPN.generateRoundKeys(key);
            StringBuilder inputBuilder = new StringBuilder(input.toUpperCase());
            while (inputBuilder.length() % 4 != 0)
                inputBuilder.append('_');
            input = inputBuilder.toString();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < input.length(); i += 4) {
                String muh = input.substring(i, i + 4);
                muh = SPN.Decrypt(muh, roundKeys);
                sb.append(muh);
            }

            StringBuilder res = new StringBuilder();
            for (int i = 0; i < size; i++) {
                res.append(sb.charAt(i));
            }

            return res.toString();
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
