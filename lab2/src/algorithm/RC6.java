package algorithm;

import java.io.*;

import static algorithm.Util.*;

public class RC6 {

    public static String text = "";
    public static String encryptedText = "";
    public static String decryptedText = "";

    private static final int w = 32;
    private static final int r = 20;
    private static int[] S;
    private static final int Pw = 0xb7e15163;
    private static final int Qw = 0x9e3779b9;

    private static int[] keyExtension(byte[] key) {
        int[] S = new int[2 * r + 4];
        S[0] = Pw;

        int c = key.length / (w / 8);
        int[] L = bytesToWords(key, c);

        for (int i = 1; i < (2 * r + 4); i++) {
            S[i] = S[i - 1] + Qw;
        }

        int A, B, i, j;

        A = B = i = j = 0;

        int v = 3 * Math.max(c, (2 * r + 4));

        for (int s = 0; s < v; s++) {
            A = S[i] = rotateLeft((S[i] + A + B), 3);
            B = L[j] = rotateLeft(L[j] + A + B, A + B);
            i = (i + 1) % (2 * r + 4);
            j = (j + 1) % c;

        }
        return S;
    }

    private static byte[] encryption(byte[] keySchArray) {
        int temp, t, u;
        int[] temp_data = new int[keySchArray.length / 4];

        temp_data = convertByteToInt(keySchArray, temp_data.length);
        int A, B, C, D;

        A = temp_data[0];
        B = temp_data[1];
        C = temp_data[2];
        D = temp_data[3];

        B = B + S[0];
        D = D + S[1];

        int lgw = 5;

        byte[] outputArr;
        for (int i = 1; i <= r; i++) {
            t = rotateLeft(B * (2 * B + 1), lgw);
            u = rotateLeft(D * (2 * D + 1), lgw);
            A = rotateLeft(A ^ t, u) + S[2 * i];
            C = rotateLeft(C ^ u, t) + S[2 * i + 1];

            temp = A;
            A = B;
            B = C;
            C = D;
            D = temp;
        }

        A = A + S[2 * r + 2];
        C = C + S[2 * r + 3];

        temp_data[0] = A;
        temp_data[1] = B;
        temp_data[2] = C;
        temp_data[3] = D;

        outputArr = convertIntToByte(temp_data, keySchArray.length);
        return outputArr;
    }

    private static byte[] decryption(byte[] keySchArray) {

        int temp, t, u;
        int A, B, C, D;

        int[] tempDataDecryption = new int[keySchArray.length / 4];


        tempDataDecryption = convertByteToInt(keySchArray, tempDataDecryption.length);


        A = tempDataDecryption[0];
        B = tempDataDecryption[1];
        C = tempDataDecryption[2];
        D = tempDataDecryption[3];


        C = C - S[2 * r + 3];
        A = A - S[2 * r + 2];

        int lgw = 5;

        byte[] outputArr;
        for (int i = r; i >= 1; i--) {
            temp = D;
            D = C;
            C = B;
            B = A;
            A = temp;

            u = rotateLeft(D * (2 * D + 1), lgw);
            t = rotateLeft(B * (2 * B + 1), lgw);
            C = rotateRight(C - S[2 * i + 1], t) ^ u;
            A = rotateRight(A - S[2 * i], u) ^ t;

        }
        D = D - S[1];
        B = B - S[0];


        tempDataDecryption[0] = A;
        tempDataDecryption[1] = B;
        tempDataDecryption[2] = C;
        tempDataDecryption[3] = D;

        outputArr = convertIntToByte(tempDataDecryption, keySchArray.length);
        return outputArr;
    }

    public static void test() {
        testEncryption();
        testDecryption();
    }

    private static String testEncryption(){
        String tempString;
        String given_text;
        String key_value;
        String[] input_text_val;
        BufferedWriter output_to_text_file = null;

        try {

            FileReader input_file = new FileReader("inputEncryption.txt");
            FileWriter output_file = new FileWriter("outputEncryption.txt", false);
            BufferedReader bf = new BufferedReader(input_file);
            bf.readLine();

            given_text = bf.readLine();
            input_text_val = given_text.split(":");
            String text_data = input_text_val[1];
            text = text_data.replace(" ", "");
            key_value = bf.readLine();
            String[] input_key_val = key_value.split(":");
            tempString = input_key_val[1];
            tempString = tempString.replace(" ", "");
            text_data = text_data.replace(" ", "");

            byte[] key = hexStringToByteArray(tempString);
            byte[] W = hexStringToByteArray(text_data);
            S = keyExtension(key);

            byte[] encrypt = encryption(W);
            encryptedText = byteArrayToHex(encrypt);
            encryptedText = encryptedText.replaceAll("..", "$0 ");
            output_to_text_file = new BufferedWriter(output_file);
            output_to_text_file.write("ciphertext: " + encryptedText);
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();

        } finally {
            if (output_to_text_file != null) {
                try {
                    output_to_text_file.close();
                } catch (IOException e) {
                    System.out.println("File cannot be closed");
                    e.printStackTrace();
                }
            }
        }

        encryptedText = encryptedText.replace(" ", "");
        return encryptedText;
    }
    private static String testDecryption(){
        String tempString;
        String given_text;
        String text_data;
        String key_value;
        String[] input_text_val;
        BufferedWriter output_to_text_file = null;
        try {

            FileReader input_file = new FileReader("inputDecryption.txt");
            FileWriter output_file = new FileWriter("outputDecryption.txt", false);
            BufferedReader bf = new BufferedReader(input_file);
            bf.readLine();

            given_text = bf.readLine();
            input_text_val = given_text.split(":");
            text_data = input_text_val[1];
            key_value = bf.readLine();
            String[] input_key_val = key_value.split(":");
            tempString = input_key_val[1];


            tempString = tempString.replace(" ", "");
            text_data = text_data.replace(" ", "");

            byte[] key2 = hexStringToByteArray(tempString);
            byte[] X = hexStringToByteArray(text_data);
            S = keyExtension(key2);
            byte[] decrypt = decryption(X);
            decryptedText = byteArrayToHex(decrypt);
            decryptedText = decryptedText.replaceAll("..", "$0 ");
            output_to_text_file = new BufferedWriter(output_file);
            output_to_text_file.write("plaintext: " + decryptedText);

        } catch (FileNotFoundException e) {
            System.out.println("File not found exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();

        } finally {
            if (output_to_text_file != null) {
                try {
                    output_to_text_file.close();
                } catch (IOException e) {
                    System.out.println("File cannot be closed");
                    e.printStackTrace();
                }
            }
        }

        decryptedText = decryptedText.replace(" ", "");
        return decryptedText;
    }

}
