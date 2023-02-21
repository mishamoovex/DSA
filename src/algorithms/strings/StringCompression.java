package algorithms.strings;

public class StringCompression {

    public static void main(String[] args) {
        String s = "aaBBccciLLLLLLLL";
        System.out.println(compress(s));
    }

    private static String compress(String string) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 0; i < string.length(); i++) {
            if (i + 1 >= string.length() || string.charAt(i) != string.charAt(i + 1)) {
                sb.append(string.charAt(i));
                sb.append(count);
                count = 1;
            } else {
                count++;
            }
        }
        return sb.toString();
    }
}
