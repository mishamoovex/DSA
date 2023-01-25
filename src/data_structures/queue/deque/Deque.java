package data_structures.queue.deque;

/**
 * A linear collection that supports element insertion and removal at
 * both ends.  The name <i>deque</i> is short for "double ended data_structures.queue"
 * and is usually pronounced "deck".  Most {@code Deque}
 * implementations place no fixed limits on the number of elements
 * they may contain, but this interface supports capacity-restricted
 * deques as well as those with no fixed size limit.
 */
public interface Deque<E> {

    /**
     * Inserts the specified element at the front of this deque unless it would
     * violate capacity restrictions.
     *
     * @param e the element to add
     * @return {@code true} if the element was added to this deque, else
     * {@code false}
     * @throws NullPointerException if the specified element is null and this
     *                              deque does not permit null elements
     */
    boolean offerFirst(E e);

    /**
     * Inserts the specified element at the end of this deque unless it would
     * violate capacity restrictions.
     *
     * @param e the element to add
     * @return {@code true} if the element was added to this deque, else
     * {@code false}
     * @throws NullPointerException if the specified element is null and this
     *                              deque does not permit null elements
     */
    boolean offerLast(E e);

    /**
     * Retrieves and removes the first element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * @return the head of this deque, or {@code null} if this deque is empty
     */
    E pollFirst();

    /**
     * Retrieves and removes the last element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * @return the tail of this deque, or {@code null} if this deque is empty
     */
    E pollLast();

    /**
     * Retrieves, but does not remove, the first element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * @return the head of this deque, or {@code null} if this deque is empty
     */
    E peekFirst();

    /**
     * Retrieves, but does not remove, the last element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * @return the tail of this deque, or {@code null} if this deque is empty
     */
    E peekLast();

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
    boolean contains(E e);
}


