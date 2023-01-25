package queue;

public interface Queue<T> {


    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions.
     *
     * @param e the element to add
     * @return {@code true} if the element was added to this queue, else
     * {@code false}
     * @throws NullPointerException if the specified element is null and
     *                              this queue does not permit null elements
     */
    boolean offer(T e);


    /**
     * Retrieves and removes the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    T poll();

    /**
     * Retrieves, but does not remove, the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    T peek();

    /**
     * Returns the number of elements in this deque.
     *
     * @return the number of elements in this deque
     */
    int size();

    /**
     * Returns {@code true} if this collection contains no elements.
     *
     * @return {@code true} if this collection contains no elements
     */
    boolean isEmpty();

    /**
     * Removes all the elements from this collection.
     */
    void clear();

    /**
     * Returns {@code true} if this deque contains the specified element.
     *
     * @param e element whose presence in this deque is to be tested
     * @return {@code true} if this deque contains the specified element
     * @throws NullPointerException if the specified element is null and this
     *                              deque does not permit null elements
     */
    boolean contains(T e);
}
