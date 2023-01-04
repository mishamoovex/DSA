package list;

import java.util.Iterator;

public class LinkedList<T> implements ListInterface<T>, Iterable<T> {

    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size > 0;
    }

    @Override
    public void clear() {
        for (Node<T> trav = head; trav != null; ) {
            Node<T> next = trav.next;
            trav.data = null;
            trav.next = null;
            trav = next;
        }
        head = tail = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).data;
    }

    @Override
    public T getFirst() {
        checkNotEmpty();
        return head.data;
    }

    @Override
    public T getLast() {
        checkNotEmpty();
        return tail.data;
    }

    @Override
    public T set(int index, T element) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        T oldData = node.data;
        node.data = element;
        return oldData;
    }

    @Override
    public void add(T element) {
        addLast(element);
    }

    @Override
    public void addFirst(T element) {
        if (isEmpty()) {
            head = tail = new Node<>(element, null);
        } else {
            head = new Node<>(element, head);
        }
        size++;
    }

    @Override
    public void addLast(T element) {
        if (isEmpty()) {
            head = tail = new Node<>(element, null);
        } else {
            tail.next = new Node<>(element, null);
        }
        size++;
    }

    @Override
    public boolean remove(Object obj) {
        if (head.data.equals(obj)) {
            if (head.next == null) {
                tail.data = null;
                tail = null;
            }
            head.data = null;
            head = null;
            size--;
            return true;
        } else {

            for (Node<T> trav1 = head, trav2 = trav1.next; trav2 != null; trav1 = trav1.next, trav2 = trav2.next) {
                if (trav2.data.equals(obj)) {
                    Node<T> tmp = trav2;
                    trav2 = trav2.next;
                    trav1.next = trav2;
                    tmp.data = null;
                    tmp.next = null;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public T removeAt(int index) {
        return null;
    }

    @Override
    public T removeFirst() {
        checkNotEmpty();
        Node<T> first = head;
        Node<T> next = head.next;
        T removedElement = first.data;
        first.next = null;
        first.data = null;
        head = next;
        if (next == null) {
            tail = null;
        }
        size--;
        return removedElement;
    }


    @Override
    public T removeLast() {
        checkNotEmpty();
        if (head.next == null) {
            head.data = null;
            head = null;
        } else {
            Node<T> preEndNode = getNode(size - 2);
            preEndNode.next = null;
        }
        T removedElement = tail.data;
        tail.data = null;
        tail = null;
        size--;
        return removedElement;
    }

    private T remove(Node<T> node) {
        Node<T> trav1;
        Node<T> trav2;


        return null;
    }

    @Override
    public int indexOf(Object obj) {
        return 0;
    }

    @Override
    public boolean contains(Object obj) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private Node<T> getNode(int index) {
        Node<T> trav = head;

        for (int i = 0; i < index; i++) {
            trav = trav.next;
        }
        return trav;
    }

    private void checkNotEmpty() {
        if (isEmpty()) throw new RuntimeException("Empty list");
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isElementIndex(int index) {
        return index > 0 && index < size;
    }
}
