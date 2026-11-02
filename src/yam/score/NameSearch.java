package yam.score;

public class NameSearch {
    /**
     * Searches for the first occurrence of a pattern in a text using the brute-force algorithm.
     * @param text is the text to search in
     * @param pattern is the pattern to search for
     * @return the index of the first occurrence of the pattern in the text, or -1 if not found
     */
    public static int bruteForce(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        if (m == 0) {
            return 0; // Empty pattern matches at index 0
        }
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return i; // Pattern found at index i
            }
        }
        return -1; // Pattern not found
    }
    
    /**
     * Searches for the first occurrence of a pattern in a text using the Knuth-Morris-Pratt (KMP) algorithm.
     * @param text is the text to search in
     * @param pattern is the pattern to search for
     * @return the index of the first occurrence of the pattern in the text, or -1 if not found
     */
    public static int kmp(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        if (m == 0) {
            return 0; // Empty pattern matches at index 0
        }
        int[] lps = computeLPSArray(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern
        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == m) {
                return i - j; // Pattern found at index i - j
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return -1; // Pattern not found
    }

    /**
     * Computes the Longest Proper Prefix which is also Suffix (LPS) array for the given pattern.
     * @param pattern is the pattern for which to compute the LPS array
     * @return the LPS array
     */
    private static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}