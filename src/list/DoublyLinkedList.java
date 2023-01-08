package list;

import java.util.Iterator;
import java.util.LinkedList;

public class DoublyLinkedList<T> implements Iterable<T>, ListInterface<T> {

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    //Internal node class to represent data
    private static class Node<T> {
        T data;
        Node<T> prev, next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
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
        return size == 0;
    }

    @Override
    public void clear() {
        Node<T> trav = head;
        while (trav != null) {
            Node<T> next = trav.next;
            trav.prev = trav.next = null;
            trav.data = null;
            trav = next;
        }
        head = tail = null;
        size = 0;
    }

    private Node<T> getNode(int index) {
        int i;
        Node<T> trav;

        if (index < size / 2) {
            for (i = 0, trav = head; i < index; i++) {
                trav = trav.next;
            }
        } else {
            for (i = size - 1, trav = tail; i > index; i--) {
                trav = trav.prev;
            }
        }
        return trav;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).data;
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public T set(int index, T element) {
        checkElementIndex(index);
        Node<T> x = getNode(index);
        T oldVal = x.data;
        x.data = element;
        return oldVal;
    }

    /**
     * Add an element to the beginning of thin linked list, O(1)
     */
    @Override
    public void addFirst(T element) {
        if (isEmpty()) {
            head = tail = new Node<>(element, null, null);
        } else {
            head.prev = new Node<>(element, null, head);
            head = head.prev;
        }
        size++;
    }

    @Override
    public void addLast(T element) {
        if (isEmpty()) {
            head = tail = new Node<>(element, null, null);
        } else {
            tail.next = new Node<>(element, tail, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T element) {
        addLast(element);
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
    public T removeFirst() {
        checkNotEmpty();
        //Extract the data at the head and move the head
        //pointer forwards one node
        T data = head.data;
        Node<T> new_head = head.next;
        head.data = null;
        head.next = null;
        head = new_head;
        //If the list is empty set the tail to null as well
        if (head == null) {
            tail.data = null;
            tail = null;
        }
        //Do the memory clean up of the previous(removed) node
        else {
            head.prev = null;
        }
        size--;
        return data;
    }

    @Override
    public T removeLast() {
        checkNotEmpty();
        //Extract the data at the tail and move the head
        //pointer backwards one node
        T data = tail.data;
        Node<T> new_tail = tail.prev;
        tail.data = null;
        tail.prev = null;
        tail = new_tail;
        //If the list is empty set head to null as well
        if (tail == null) {
            head.data = null;
            head = null;
        }
        //Do the memory clean up of the next(removed) node
        else {
            tail.next = null;
        }
        size--;
        return data;
    }

    private T remove(Node<T> node) {
        //If the node to remove is somewhere either at the
        //head or the tail handle those independently
        if (node.prev == null) removeFirst();
        if (node.next == null) removeLast();
        //Make the pointers of adjacent nodes skip over 'node'
        node.next.prev = node.prev;
        node.prev.next = node.next;
        //Temporary store the data we want to return
        T data = node.data;
        //Memory cleanup
        node.data = null;
        node.prev = node.next = null;
        //Change the list size
        --size;
        //Return the data at the node we just removed;
        return data;
    }

    /**
     * Remove a node at a particular index, O(n)
     */
    @Override
    public T removeAt(int index) {
        //Make sure the index provided is valid
        checkElementIndex(index);
        Node<T> trav = getNode(index);
        return remove(trav);
    }

    /**
     * Remove a particular value in the linked list, O(n)
     */
    @Override
    public boolean remove(Object obj) {
        if (obj == null) {
            for (Node<T> trav = head; trav != null; trav = trav.next) {
                if (trav.data == null) {
                    remove(trav);
                    return true;
                }
            }
        } else {
            for (Node<T> trav = head; trav != null; trav = trav.next) {
                if (trav.data.equals(obj)) {
                    remove(trav);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Find the index of a particular value in the linked list, 0(n)
     */
    @Override
    public int indexOf(Object obj) {
        int index = 0;
        Node<T> trav;

        //Support searching for null
        if (obj == null) {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (trav.data == null) {
                    return index;
                }
            }
        }
        //Support for non-null object
        else {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (trav.data.equals(obj)) {
                    return index;
                }
            }
        }
        return -1;
    }

    /**
     * Check is a value contained withing the linked list
     */
    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
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

    private void checkNotEmpty() {
        if (isEmpty()) throw new RuntimeException("Empty list");
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Tells if the argument is the index of an existing element.
     */
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
}
