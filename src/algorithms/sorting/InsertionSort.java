package algorithms.sorting;


import java.util.Arrays;

/**
 * The {@code Insertion} class provides static methods for sorting an
 * array using insertion sort.
 * <p>
 * In the worst case, this implementation makes ~ &frac12; <em>n</em><sup>2</sup>
 * compares and ~ &frac12; <em>n</em><sup>2</sup> exchanges to sort an array
 * of length <em>n</em>. So, it is not suitable for sorting large arbitrary
 * arrays. More precisely, the number of exchanges is exactly equal to the
 * number of inversions. So, for example, it sorts a partially-sorted array
 * in linear time.
 * <p>
 * This sorting algorithm is stable.
 * It uses &Theta;(1) extra memory (not including the input array).
 */
public class InsertionSort {

    // This class should not be instantiated.
    private InsertionSort() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param a the array to be sorted
     */
    public static <T extends Comparable<T>> void sort(T[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }


    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }


    // exchange a[i] and a[j]
    private static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/

    private static <T extends Comparable<T>> boolean isSorted(T[] a) {
        return isSorted(a, 0, a.length);
    }

    // is the array a[lo..hi) sorted
    private static <T extends Comparable<T>> boolean isSorted(T[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }


    /**
     * Reads in a sequence of strings from standard input; insertion sorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Integer[] a = new Integer[8];
        a[0] = 3;
        a[1] = 1;
        a[2] = 4;
        a[3] = 2;
        a[4] = 7;
        a[5] = 6;
        a[6] = 5;
        a[7] = 8;

        System.out.println(Arrays.toString(a));

        InsertionSort.sort(a);

        System.out.println(Arrays.toString(a));
    }
}
