import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Shresht Yadav
 * @version 1.0
 * @userid Syadav73
 * @GTID 903688946
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * <p>
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null or length 0");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text or comparator is null/empty");
        }
        List<Integer> matches = new ArrayList<Integer>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        int[] failureTable = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;
        while (i <= text.length() - pattern.length()) {
            while (j < pattern.length() && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j = j + 1;
            }
            if (j == 0) {
                i = i + 1;
            } else {
                if (j == pattern.length()) {
                    matches.add(i);
                }
                int nextAlignment = failureTable[j - 1];
                i = i + j - nextAlignment;
                j = nextAlignment;
            }
        }
        return matches;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * <p>
     * The table built should be the length of the input pattern.
     * <p>
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     * <p>
     * Ex. pattern = ababac
     * <p>
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     * <p>
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null or length 0");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null/empty");
        }
        int[] failureTable = new int[pattern.length()];
        int i = 0;
        int j = 1;
        failureTable[0] = 0;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                i++;
                failureTable[j] = i;
                j = j + 1;
            } else {
                if (i == 0) {
                    failureTable[j] = 0;
                    j = j + 1;
                } else {
                    i = failureTable[i - 1];
                }
            }
        }
        return failureTable;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * <p>
     * Make sure to implement the buildLastTable() method before implementing
     * this method. Do NOT implement the Galil Rule in this method.
     * <p>
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null or length 0");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text or comparator is null/empty");
        }
        List<Integer> matches = new ArrayList<Integer>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (pattern.length() + i > text.length()) {
                return matches;
            }
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j = j - 1;
            }
            if (j == -1) {
                matches.add(i);
                i = i + 1;
            } else {
                int shiftedIndex;
                if (lastTable.containsKey(text.charAt(i + j))) {
                    shiftedIndex = lastTable.get(text.charAt(i + j));
                } else {
                    shiftedIndex = -1;
                }
                if (shiftedIndex < j) {
                    i = i + (j - shiftedIndex);
                } else {
                    i = i + 1;
                }
            }
        }
        return matches;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * <p>
     * Ex. pattern = octocat
     * <p>
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * <p>
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null or length 0");
        }
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < pattern.length(); i++) {
            map.put(pattern.charAt(i), i);
        }
        return map;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     * <p>
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     * <p>
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     * <p>
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     * c is the integer value of the current character, and
     * i is the index of the character
     * <p>
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     * <p>
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     * <p>
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     * <p>
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     * <p>
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     * = (142910419 - 98 * 113 ^ 3) * 113 + 121
     * = 170236090
     * <p>
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     * <p>
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null or length 0");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text or comparator is null/empty");
        }
        List<Integer> matches = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        int patternHash = 0;
        int textHash = 0;
        for (int i = 0; i < pattern.length(); i++) {
            int exponent = pattern.length() - i - 1;
            patternHash = patternHash + pattern.charAt(i) * power(exponent);
            textHash = textHash + text.charAt(i) * power(exponent);
        }
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (patternHash == textHash) {
                int j = 0;
                while (j < pattern.length() && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    matches.add(i);
                }
            }
            i++;
            if (i <= text.length() - pattern.length()) {
                textHash = (textHash - (text.charAt(i - 1) * power(pattern.length() - 1)))
                        * BASE + text.charAt(i + pattern.length() - 1);
            }
        }
        return matches;
    }

    /**
     * Helper method that returns the power of BASE needed.
     * @param exponent the power BASE needs to be raised by.
     * @return Base^exponent.
     */
    private static int power(int exponent) {
        int num = 1;
        if (exponent == 0) {
            return 1;
        } else if (exponent == 1) {
            return BASE;
        } else {
            for (int i = 1; i <= exponent; i++) {
                num = num * BASE;
            }
        }
        return num;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     * <p>
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     * <p>
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                                    CharSequence text,
                                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is null or length 0");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("text or comparator is null/empty");
        }
        List<Integer> matches = new ArrayList<Integer>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int[] failureTable = buildFailureTable(pattern, comparator);
        int n = text.length();
        int m = pattern.length();
        int i = 0;
        int w = 0;
        int k = m - failureTable[m - 1];
        while (i <= n - m) {
            if (pattern.length() + i > text.length()) {
                return matches;
            }
            int j = m - 1;
            while (j >= w && comparator.compare(pattern.charAt(j), text.charAt(i + j)) == 0) {
                j = j - 1;
            }
            if (j < w) {
                matches.add(i);
                w = m - k;
                i = i + k;
            } else {
                w = 0;
                int shiftedIndex;
                if (lastTable.containsKey(text.charAt(i + j))) {
                    shiftedIndex = lastTable.get(text.charAt(i + j));
                } else {
                    shiftedIndex = -1;
                }
                if (shiftedIndex < j) {
                    i = i + (j - shiftedIndex);
                } else {
                    i = i + 1;
                }
            }
        }
        return matches;
    }
}
