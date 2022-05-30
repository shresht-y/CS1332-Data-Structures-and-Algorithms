import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is a basic set of unit tests for Sorting.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class SortingStudentTest {

    private static final int TIMEOUT = 200;
    private TeachingAssistant[] tas;
    private TeachingAssistant[] tasByName;
    private ComparatorPlus<TeachingAssistant> comp;


    // STABILITY TESTING
    /* basically, using objects with IDs to test that when sorted,
       the IDs of the objects remain in the same order that they
       were present in the original list
     */
    StringNum a1 = new StringNum("A", 1);
    StringNum b1 = new StringNum("B", 1);
    StringNum b2 = new StringNum("B", 2);
    StringNum c1 = new StringNum("C", 1);
    StringNum c2 = new StringNum("C", 2);
    StringNum c3 = new StringNum("C", 3);
    StringNum d1 = new StringNum("D", 1);
    StringNum d2 = new StringNum("D", 2);
    StringNum d3 = new StringNum("D", 3);
    StringNum d4 = new StringNum("D", 4);
    StringNum e1 = new StringNum("E", 1);
    StringNum e2 = new StringNum("E", 2);
    StringNum e3 = new StringNum("E", 3);
    StringNum e4 = new StringNum("E", 4);
    StringNum e5 = new StringNum("E", 5);
    StringNum[] listAscend = {a1, b1, c1, d1, e1, b2, c2, d2, e2, c3, d3, e3, d4, e4, e5};
    StringNum[] correctAscend = {a1, b1, b2, c1, c2, c3, d1, d2, d3, d4, e1, e2, e3, e4, e5};
    StringNum[] listDescend = {e5, e4, e3, e2, e1, d4, d3, d2, d1, c3, c2, c1, b2, b1, a1};
    StringNum[] correctDescend = {a1, b2, b1, c3, c2, c1, d4, d3, d2, d1, e5, e4, e3, e2, e1};
    private Comparator<StringNum> compS = StringNum.getNameComparator();

    static class StringNum implements Comparable<StringNum> {
        private final String s;
        private final int n;
        public StringNum(String s, int n) {
            this.s = s;
            this.n = n;
        }
        @Override
        public int compareTo(StringNum that) {
            return this.s.compareTo(that.s); //Ordering by the string
        }
        @Override
        public String toString() {
            return this.s + ":" + this.n;
        }

        public String getName() {
            return s;
        }

        public static ComparatorPlus<StringNum> getNameComparator() {
            return new ComparatorPlus<StringNum>() {
                @Override
                public int compare(StringNum ta1,
                                   StringNum ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortStability() {
        Sorting.cocktailSort(listAscend, compS);
//        Uncomment these lines to see where the "unstableness" occurs
//        printArr(listAscend);
//        printArr(correctAscend);
        assertArrayEquals(listAscend, correctAscend);

        Sorting.cocktailSort(listDescend, compS);
//        printArr(listDescend);
//        printArr(correctDescend);
        assertArrayEquals(listDescend, correctDescend);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortStability() {
        Sorting.mergeSort(listAscend, compS);
        assertArrayEquals(listAscend, correctAscend);

        Sorting.mergeSort(listDescend, compS);
        assertArrayEquals(listDescend, correctDescend);
    }

    // END OF STABILITY TESTING BLOCK

    @Before
    public void setUp() {
        /*
            Unsorted Names:
                index 0: Aleksandr
                index 1: Grant
                index 2: Mahima
                index 3: Keely
                index 4: Harneet
                index 5: Shayar
                index 6: Yotam
                index 7: Tri
                index 8: Robert
                index 9: Isha
         */

        /*
            Sorted Names:
                index 0: Aleksandr
                index 1: Grant
                index 2: Harneet
                index 3: Isha
                index 4: Keely
                index 5: Mahima
                index 6: Robert
                index 7: Shayar
                index 8: Tri
                index 9: Yotam
         */

        tas = new TeachingAssistant[10];
        tas[0] = new TeachingAssistant("Aleksandr");
        tas[1] = new TeachingAssistant("Grant");
        tas[2] = new TeachingAssistant("Mahima");
        tas[3] = new TeachingAssistant("Keely");
        tas[4] = new TeachingAssistant("Harneet");
        tas[5] = new TeachingAssistant("Shayar");
        tas[6] = new TeachingAssistant("Yotam");
        tas[7] = new TeachingAssistant("Tri");
        tas[8] = new TeachingAssistant("Robert");
        tas[9] = new TeachingAssistant("Isha");
        tasByName = new TeachingAssistant[10];
        tasByName[0] = tas[0];
        tasByName[1] = tas[1];
        tasByName[2] = tas[4];
        tasByName[3] = tas[9];
        tasByName[4] = tas[3];
        tasByName[5] = tas[2];
        tasByName[6] = tas[8];
        tasByName[7] = tas[5];
        tasByName[8] = tas[7];
        tasByName[9] = tas[6];

        comp = TeachingAssistant.getNameComparator();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testSelectionException1() {
        Sorting.selectionSort(null, comp);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testSelectionException2() {
        Sorting.selectionSort(tas, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCockTailException1() {
        Sorting.cocktailSort(null, comp);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCockTailException2() {
        Sorting.cocktailSort(tas, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeSortException1() {
        Sorting.mergeSort(null, comp);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeSortException2() {
        Sorting.mergeSort(tas, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickSortException1() {
        Sorting.quickSort(null, comp, new Random());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickSortException2() {
        Sorting.quickSort(tas, null, new Random());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickSortException3() {
        Sorting.quickSort(tas, comp, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLSDRadixException() {
        Sorting.lsdRadixSort(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testHeapSortException() {
        Sorting.heapSort(null);
    }

    @Test(timeout = TIMEOUT)
    public void testArraySize0() {
        Sorting.heapSort(new LinkedList<Integer>());
        Sorting.lsdRadixSort(new int[0]);
        Sorting.quickSort(new TeachingAssistant[0], comp, new Random());
        Sorting.mergeSort(new TeachingAssistant[0], comp);
        Sorting.cocktailSort(new TeachingAssistant[0], comp);
        Sorting.selectionSort(new TeachingAssistant[0], comp);
    }

    @Test(timeout = TIMEOUT)
    public void testSelectionSort() {
        Sorting.selectionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 45 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSort() {
        Sorting.cocktailSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 26 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 21 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        Sorting.quickSort(tas, comp, new Random(234));
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 27 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[]{54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[]{-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort2() {
        int[] unsortedArray = new int[]{54, 28, 58, 84, 20, 122, -85, 3, -60, -120};
        int[] sortedArray = new int[]{-120, -85, -60, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }

    public static void printArr(StringNum[] arr) {
        for (StringNum s: arr)
            System.out.print(s+", ");
        System.out.println();
    }

    /**
     * Class for testing proper sorting.
     */
    private static class TeachingAssistant {
        private String name;

        /**
         * Create a teaching assistant.
         *
         * @param name name of TA
         */
        public TeachingAssistant(String name) {
            this.name = name;
        }

        /**
         * Get the name of the teaching assistant.
         *
         * @return name of teaching assistant
         */
        public String getName() {
            return name;
        }

        /**
         * Set the name of the teaching assistant.
         *
         * @param name name of the teaching assistant
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<TeachingAssistant> getNameComparator() {
            return new ComparatorPlus<TeachingAssistant>() {
                @Override
                public int compare(TeachingAssistant ta1,
                                   TeachingAssistant ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof TeachingAssistant
                    && ((TeachingAssistant) other).name.equals(this.name);
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}