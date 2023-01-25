package algorithms.sorting;

import java.util.Arrays;

/**
 * The {@code Selection} class provides static methods for sorting an
 * array using <em>selection sort</em>.
 * This implementation makes ~ &frac12; <em>n</em><sup>2</sup> compares to sort
 * any array of length <em>n</em>, so it is not suitable for sorting large arrays.
 * It performs exactly <em>n</em> exchanges.
 * <p>
 * This sorting algorithm is not stable. It uses &Theta;(1) extra memory
 * (not including the input array).
 * <p>
 */
public class SelectionSort {

    private SelectionSort() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param arr the array to be sorted
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        if (arr == null) return;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(arr[j], arr[min])) min = j;
            }
            exchange(arr, i, min);
            assert isSorted(arr, 0, i);
        }
        assert isSorted(arr);
    }


    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }


    // exchange a[i] and a[j]
    private static <T extends Comparable<T>> void exchange(T[] a, int i, int j) {
        T swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/

    // is the array a[] sorted?
    private static <T extends Comparable<T>> boolean isSorted(T[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static <T extends Comparable<T>> boolean isSorted(T[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }


    /**
     * Reads in a sequence of strings from standard input; selection sorts them;
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

        SelectionSort.sort(a);

        System.out.println(Arrays.toString(a));
    }

}