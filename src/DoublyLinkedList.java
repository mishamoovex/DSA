import java.util.Iterator;

public class DoublyLinkedList<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    //Internal node class to represent data
    private class Node<T> {
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

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size > 0;
    }

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

    /**
     * Add an element to the beginning of thin linked list, O(1)
     */
    public void addFirst(T element) {
        if (isEmpty()) {
            head = tail = new Node<>(element, null, null);
        } else {
            head.prev = new Node<>(element, null, head);
            head = head.prev;
        }
        size++;
    }

    public void addLast(T element) {
        if (isEmpty()) {
            head = tail = new Node<>(element, null, null);
        } else {
            tail.next = new Node<>(element, tail, null);
            tail = tail.next;
        }
        size++;
    }

    public void add(T element) {
        addLast(element);
    }

    public T peekFirst() {
        checkNotEmpty();
        return head.data;
    }

    public T peekLast() {
        checkNotEmpty();
        return tail.data;
    }

    public T removeFirst() {
        checkNotEmpty();
        //Extract the data at the head and move the head
        //pointer forwards one node
        T data = head.data;
        head = head.next;
        --size;

        if (isEmpty()) {
            //If the list is empty set the tail to null as well
            tail = null;
        } else {
            //Do the memory clean up of the previous(removed) node
            head.prev = null;
        }
        return data;
    }

    public T removeLast() {
        checkNotEmpty();

        T data = tail.data;
        tail = tail.prev;
        --size;

        if (isEmpty()) {
            head = null;
        } else {
            tail.next = null;
        }
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
    public T removeAt(int index) {
        //Make sure the index provided is valid
        if (index < 0 || index >= size) throw new IllegalArgumentException();

        int i;
        Node<T> trav;

        //Search from the front of the list
        if (index < size / 2) {
            for (i = 0, trav = head; i != index; i++) {
                trav = trav.next;
            }
        }
        //Search from the back of the list
        else {
            for (i = size - 1, trav = tail; i != index; i--) {
                trav = trav.prev;
            }
        }
        return remove(trav);
    }

    /**
     * Remove a particular value in the linked list, O(n)
     */
    public boolean remove(Object obj) {
        Node<T> trav;

        //Support searching for null
        if (obj == null) {
            for (trav = head; trav != null; trav = trav.next) {
                if (trav.data == null) {
                    remove(trav);
                    return true;
                }
            }
        }
        //Search for non null object
        else {
            for (trav = head; trav != null; trav = trav.next) {
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
    public int indexOf(Object obj) {
        int index = 0;
        Node<T> trav = head;

        //Support searching for null
        if (obj == null) {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (trav.data == null) {
                    return index;
                }
            }
        }
        //Support for non null object
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
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
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
}
