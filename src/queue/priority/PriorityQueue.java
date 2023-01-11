package queue.priority;

public interface PriorityQueue<T extends Comparable<T>> {

    int size();

    boolean isEmpty();

    T peek();

    T poll();

    void add(T element);

    boolean remove(T element);

    T removeAt(int i);

    boolean contains(T element);

    int indexOf(T element);

    void clear();

    boolean isMinHeap(int k);

}
