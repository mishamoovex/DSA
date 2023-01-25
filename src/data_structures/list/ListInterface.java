package data_structures.list;

public interface ListInterface<T> {

    int size();

    boolean isEmpty();

    void clear();

    T get(int index);

    T getFirst();

    T getLast();

    T set(int index, T element);

    void add(T element);

    void addFirst(T element);

    void addLast(T element);

    boolean remove(Object obj);

    T removeAt(int index);

    T removeFirst();

    T removeLast();

    int indexOf(Object obj);

    boolean contains(Object obj);
}
