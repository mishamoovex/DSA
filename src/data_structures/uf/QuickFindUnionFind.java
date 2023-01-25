package data_structures.uf;


import java.util.Scanner;

/**
 * The {@code QuickFindUF} class represents a <em>union–find data type</em>
 * (also known as the <em>disjoint-sets data type</em>).
 * It supports the classic <em>union</em> and <em>find</em> operations,
 * along with a <em>count</em> operation that returns the total number
 * of sets.
 * <p>
 * The union–find data type models a collection of sets containing
 * <em>n</em> elements, with each element in exactly one set.
 * The elements are named 0 through <em>n</em>–1.
 * Initially, there are <em>n</em> sets, with each element in its
 * own set. The <em>canonical element</em> of a set
 * (also known as the <em>root</em>, <em>identifier</em>,
 * <em>leader</em>, or <em>set representative</em>)
 * is one distinguished element in the set. Here is a summary of
 * the operations:
 * <ul>
 * <li><em>find</em>(<em>p</em>) returns the canonical element
 *     of the set containing <em>p</em>. The <em>find</em> operation
 *     returns the same value for two elements if and only if
 *     they are in the same set.
 * <li><em>union</em>(<em>p</em>, <em>q</em>) merges the set
 *     containing element <em>p</em> with the set containing
 *     element <em>q</em>. That is, if <em>p</em> and <em>q</em>
 *     are in different sets, replace these two sets
 *     with a new set that is the union of the two.
 * <li><em>count</em>() returns the number of sets.
 * </ul>
 * p>
 * The canonical element of a set can change only when the set
 * itself changes during a call to <em>union</em>&mdash;it cannot
 * change during a call to either <em>find</em> or <em>count</em>.
 * p>
 * This implementation uses <em>quick find</em>.
 * The constructor takes &Theta;(<em>n</em>) time, where <em>n</em>
 * is the number of sites.
 * The <em>find</em>, <em>connected</em>, and <em>count</em>
 * operations take &Theta;(1) time; the <em>union</em> operation
 * takes &Theta;(<em>n</em>) time.
 */
public class QuickFindUnionFind implements UnionFind {

    private final int[] id;    // id[i] = component identifier of i
    private int count;         // number of components

    /**
     * Initializes an empty union-find data structure with
     * {@code n} elements {@code 0} through {@code n-1}.
     * Initially, each element is in its own set.
     *
     * @param n the number of elements
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public QuickFindUnionFind(int n) {
        checkCapacity(n);
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    /**
     * Returns the canonical element of the set containing element {@code p}.
     *
     * @param p an element
     * @return the canonical element of the set containing {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    @Override
    public int find(int p) {
        checkElementIndex(p);
        return id[p];
    }

    /**
     * Merges the set containing element {@code p} with the set
     * containing element {@code q}.
     *
     * @param p one element
     * @param q the other element
     * @throws IllegalArgumentException unless both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    @Override
    public void union(int p, int q) {
        checkElementIndex(p);
        checkElementIndex(q);

        int pId = find(p);
        int qId = find(q);

        if (pId == qId) return;

        //Override p indices with q indices
        for (int i = 0; i < id.length; i++) {
            if (i == pId) id[i] = qId;
        }
        count--;
    }

    /**
     * Returns true if the two elements are in the same set.
     *
     * @param p one element
     * @param q the other element
     * @return {@code true} if {@code p} and {@code q} are in the same set;
     * {@code false} otherwise
     * @throws IllegalArgumentException unless both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Returns the number of sets.
     *
     * @return the number of sets (between {@code 1} and {@code n})
     */
    @Override
    public int count() {
        return count;
    }

    private void checkElementIndex(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    private void checkCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Size <= 0 not allowed");
        }
    }

    /**
     * Reads an integer {@code n} and a sequence of pairs of integers
     * (between {@code 0} and {@code n-1}) from standard input, where each integer
     * in the pair represents some element;
     * if the elements are in different sets, merge the two sets
     * and print the pair to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        QuickFindUnionFind uf = new QuickFindUnionFind(n);
        while (scanner.hasNext()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            if (uf.find(p) == uf.find(q)) continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
            System.out.println(uf.count() + " components");
        }
    }

}
