package yam.ds;

import java.util.NoSuchElementException;

/**
 * LIFO stack built on linked nodes; every operation is O(1).
 * Used by the iterative DFS flood fill ({@code FloodFill.revealDfs}).
 *
 * @param <T> element type
 */
public class MyStack<T> {

    private Node<T> top;
    private int size;

    public MyStack() {
        // TODO: Start as an empty stack (top = null, size = 0).
    }

    /** Puts item on top of the stack. */
    public void push(T item) {
        // TODO: Create a node pointing to the old top and make it the new top.
    }

    /**
     * Removes and returns the top item.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public T pop() {
        // TODO: Check for empty, move top to top.next and return the old top's data.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns the top item without removing it.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public T peek() {
        // TODO: Check for empty and return top.data.
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
        // TODO: Remove all items (top = null, size = 0).
    }
}
