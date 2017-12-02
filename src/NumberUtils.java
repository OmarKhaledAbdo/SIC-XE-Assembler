import java.util.Arrays;

 public class NumberUtils {
    static public String addLeadingZeroes (String str) {
        Integer length = (str.length() + 3) / 4 * 4;
        char[] chars = new char[length - str.length()];
        Arrays.fill(chars, '0');
        String zeroes = new String(chars);
        return zeroes + str;
    }
}
