package data_structures.uf;

public class PathCompressionUF {

    //The number of elements in this union find
    private final int size;

    //Used to track the sizes of each of the components
    private final int[] sz;

    //id[i] points to the parent of i, if id[id] = i then i is a root node
    private final int[] id;

    //Tracks the number of components in the union find
    private int n;

    public PathCompressionUF(int size) {
        if (size <= 0) throw new IllegalArgumentException("Size <= 0 is not allowed");

        this.size = n = size;
        sz = new int[size];
        id = new int[size];

        for (int i = 0; i < size; i++) {
            id[i] = i; //Link to itself (self root)
            sz[i] = 1; //Each component is originally of size one
        }
    }

    //Return whether the elements 'p' and 'q'
    //are in the same components/set
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    //Return the size of the components/set 'p' belongs to
    public int componentsSize(int p) {
        return sz[find(p)];
    }

    //Return the number of elements in this UnionFind/Disjoint set
    public int size() {
        return size;
    }

    //Return the number of remaining components/set
    public int components() {
        return n;
    }

    //Find which component/set `p` belongs to, takes amortized constant time.
    public int find(int p) {
        //Find the root of the component/set
        int root = p;
        while (root != id[root]) {
            root = id[root];
            //An alternative to "two pass compression" - "one path variant"
            //Make every other point in path point to its grandparent
            //root = id[id[root]];
        }
        //Compress the path leading back to the root.
        //Doing this operation is called "two path compression"
        //and is what gives us amortized constant time complexity
        while (p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }
        return root;
    }

    //Unify the components/sets containing elements 'p' and 'q'
    public void union(int p, int q) {
        int root1 = find(p);
        int root2 = find(q);
        //These elements are already in the same group!
        if (root1 == root2) return;
        //Merge two components/sets together.
        //Merge smaller component/set into larger one.
        if (sz[root1] < sz[root2]) {
            sz[root2] += sz[root1];
            id[root1] = root2;
        } else {
            sz[root1] += sz[root2];
            id[root2] = root1;
        }
        //Since the roots found are different we know that the
        //number of components/sets has decreased by one
        n--;
    }

}
