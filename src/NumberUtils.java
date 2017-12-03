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
}
