package algorithm;

public class Util {

    public static byte[] convertIntToByte(int[] integerArray, int length) {
        byte[] int_to_byte = new byte[length];
        for (int i = 0; i < length; i++) {
            int_to_byte[i] = (byte) ((integerArray[i / 4] >>> (i % 4) * 8) & 0xff);
        }

        return int_to_byte;
    }

    public static int[] convertByteToInt(byte[] arr, int length) {
        int[] byte_to_int = new int[length];

        int counter = 0;
        for (int i = 0; i < byte_to_int.length; i++) {
            byte_to_int[i] = ((arr[counter++] & 0xff)) |
                    ((arr[counter++] & 0xff) << 8) |
                    ((arr[counter++] & 0xff) << 16) |
                    ((arr[counter++] & 0xff) << 24);
        }
        return byte_to_int;

    }

    public static byte[] hexStringToByteArray(String s) {
        int string_len = s.length();
        byte[] data = new byte[string_len / 2];
        for (int i = 0; i < string_len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    public static int[] bytesToWords(byte[] userkey, int c) {
        int[] bytes_to_words = new int[c];
        for (int i = 0, off = 0; i < c; i++)
            bytes_to_words[i] = ((userkey[off++] & 0xFF)) | ((userkey[off++] & 0xFF) << 8)
                    | ((userkey[off++] & 0xFF) << 16) | ((userkey[off++] & 0xFF) << 24);
        return bytes_to_words;
    }

    // ROTATE LEFT METHOD
    public static int rotateLeft(int val, int pas) {
        return (val << pas) | (val >>> (32 - pas));
    }
    //ROTATE RIGHT METHOD
    public static int rotateRight(int val, int pas) {
        return (val >>> pas) | (val << (32 - pas));
    }
}
