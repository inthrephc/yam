package yam.ds;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Singly linked list with head and tail references.
 * Used for neighbor lists ({@code Board.getNeighbors}) and the sorted leaderboard.
 * Implements {@link Iterable}, so it works in for-each loops.
 *
 * @param <T> element type
 */
public class MyLinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        // TODO: Start as an empty list (head = tail = null, size = 0).
    }

    /** Inserts item at the front. O(1). */
    public void addFirst(T item) {
        // TODO: Link a new node before head; also set tail if the list was empty.
    }

    /** Appends item at the end. O(1). */
    public void addLast(T item) {
        // TODO: Link a new node after tail; also set head if the list was empty.
    }

    /**
     * Inserts item at position index (0 = front, size = end). O(n).
     *
     * @throws IndexOutOfBoundsException if {@code index < 0 || index > size}
     */
    public void add(int index, T item) {
        // TODO: Validate index. Reuse addFirst/addLast for the two ends; otherwise walk to
        //       node (index - 1) and link the new node after it.
    }

    /**
     * Inserts item before the first element that is greater than it, so a sorted list stays
     * sorted. Equal elements keep insertion order (the new one goes after them). O(n).
     */
    public void addSorted(T item, Comparator<? super T> comparator) {
        // TODO: Walk from head to the first node with comparator.compare(node.data, item) > 0
        //       and insert item before it; append at the end if there is no such node.
    }

    /**
     * Returns the element at index. O(n).
     *
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size}
     */
    public T get(int index) {
        // TODO: Validate index, walk index steps from head and return the data.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Removes and returns the first element. O(1).
     *
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst() {
        // TODO: Unlink head and return its data; set tail to null if the list becomes empty.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Removes and returns the element at index. O(n).
     *
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size}
     */
    public T remove(int index) {
        // TODO: Validate index. Reuse removeFirst for index 0; otherwise unlink the node from its
        //       predecessor (update tail if the removed node was the last one).
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int size() {
        // TODO: Return size.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isEmpty() {
        // TODO: Return true if size is 0.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void clear() {
        // TODO: Remove all elements (head = tail = null, size = 0).
    }

    @Override
    public Iterator<T> iterator() {
        // TODO: Return a new LinkedListIterator starting at head.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Returns the elements as {@code "[a, b, c]"} (for debugging). */
    @Override
    public String toString() {
        // TODO: Walk the list and build the text with a StringBuilder.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Iterates from head to tail. {@code remove()} is not supported. */
    private class LinkedListIterator implements Iterator<T> {

        private Node<T> current;

        LinkedListIterator(Node<T> start) {
            // TODO: Remember the start node in current.
        }

        @Override
        public boolean hasNext() {
            // TODO: Return true while current is not null.
            throw new UnsupportedOperationException("Not implemented yet");
        }

        @Override
        public T next() {
            // TODO: Throw NoSuchElementException if there is no next element; otherwise
            //       return current.data and move current to the next node.
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
