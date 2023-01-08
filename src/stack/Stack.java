package stack;

public interface Stack<T> {

    // return the number of elements in the stack
    int size();

    // return if the stack is empty
    boolean isEmpty();

    // push the element on the stack
    void push(T elem);

    // pop the element off the stack
    T pop();

    T peek();
}
