package data_structures.stack;

public interface Stack<T> {

    int size();

    boolean isEmpty();

    void push(T elem);

    T pop();

    T peek();
}
