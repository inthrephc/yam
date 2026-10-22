package yam.ds;

import java.util.NoSuchElementException;

/**
 * FIFO queue built on linked nodes (front = remove side, rear = add side); every operation is O(1).
 * Used by the BFS flood fill ({@code FloodFill.revealBfs}).
 *
 * @param <T> element type
 */
public class MyQueue<T> {

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public MyQueue() {
        // TODO: Start as an empty queue (front = rear = null, size = 0).
    }

    /** Adds item at the rear. */
    public void enqueue(T item) {
        // TODO: Link a new node after rear; also set front if the queue was empty.
    }

    /**
     * Removes and returns the front item.
     *
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        // TODO: Check for empty, move front to front.next and return the old front's data;
        //       set rear to null if the queue becomes empty.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns the front item without removing it.
     *
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        // TODO: Check for empty and return front.data.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isEmpty() {
        // TODO: Return true if size is 0.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int size() {
        // TODO: Return size.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void clear() {
        // TODO: Remove all items (front = rear = null, size = 0).
    }
}
