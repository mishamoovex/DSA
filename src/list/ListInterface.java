package list;

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

    T removeAt(int index);

    T removeFirst();

    T removeLast();

    boolean remove(Object obj);

    int indexOf(Object obj);

    boolean contains(Object obj);
}
