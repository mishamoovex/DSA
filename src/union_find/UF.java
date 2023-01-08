package union_find;

public interface UF {

    int find(int p);

    void union(int p, int q);

    boolean connected(int p, int q);

    int count();
}
