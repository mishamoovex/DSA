package stack;

import java.util.EmptyStackException;

public class NodeBasedStack<T> implements Stack<T> {

    private int size;
    private Node<T> tail;
    private Node<T> head;

    private static class Node<T> {
        public T data;
        public Node<T> prev;
        public Node<T> next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public void push(T elem) {
        if (isEmpty()) {
            tail = head = new Node<>(elem, null, null);
        } else {
            tail.next = new Node<>(elem, tail, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public T pop() {
        checkNotEmpty();
        Node<T> new_tail = tail.prev;
        T removedValue = tail.data;
        tail.data = null;
        tail.prev = null;
        tail = new_tail;
        if (tail != null) {
            tail.next = null;
        }
        size--;
        return removedValue;
    }

    @Override
    public T peek() {
        return tail.data;
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            Node<T> trav = head;
            while (trav != null) {
                sb.append(trav.data);
                trav = trav.next;
                if (trav != null) {
                    sb.append(", ");
                }
            }
            return sb.append("]").toString();
        }
    }
}