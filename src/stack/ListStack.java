package stack;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;

public class ListStack<T> implements Stack<T>, Iterable<T> {

    private final LinkedList<T> list = new LinkedList<>();

    //Create an empty Stack
    public ListStack() {
    }

    //Create a Stack with an initial element
    public ListStack(T element) {
        push(element);
    }

    //Return the number of elements in the Stack
    @Override
    public int size() {
        return list.size();
    }

    //Check is the Stack is empty
    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }

    //Push an element on the Stack
    @Override
    public void push(T element) {
        list.addLast(element);
    }

    //Pop an element off the Stack
    //Throws an error is the Stack is empty
    @Override
    public T pop() {
        checkNotEmpty();
        return list.removeLast();
    }

    //Peek the top of the stack without removing an element
    //Throws an exception if the stack is empty
    @Override
    public T peek() {
        checkNotEmpty();
        return list.getLast();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        return list.toString();
    }

    private void checkNotEmpty() {
        if (isEmpty()) throw new EmptyStackException();
    }
}
