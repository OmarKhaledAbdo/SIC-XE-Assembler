import java.util.Arrays;

public class NumberUtils {
    static public String adjustSize(String str, Integer length) {
        if(str.length() > length) {
            return str.substring(str.length() - length);
        } else {
            char[] chars = new char[length - str.length()];
            Arrays.fill(chars, '0');
            String zeroes = new String(chars);
            return zeroes + str;
        }
    }

    public static String binaryToHex(String bin) {
        int dec = Integer.parseInt(bin,2);
        String hex = Integer.toString(dec,16).toUpperCase();
        return hex;
    }
}
