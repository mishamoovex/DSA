package array;

public interface Array<T> {

    int size();

    boolean isEmpty();

    void clear();

    T get(int index);

    void set(int index, T element);

    void add(T element);

    boolean remove(Object obj);

    T removeAt(int index);

    int indexOf(Object obj);

    boolean contains(Object obj);
}
