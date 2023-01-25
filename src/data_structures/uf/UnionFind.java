package data_structures.uf;

public interface UnionFind {

    int find(int p);

    void union(int p, int q);

    boolean connected(int p, int q);

    int count();
}
