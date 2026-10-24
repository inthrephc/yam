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
        this.top = null;
        this.size = 0;
    }

    /** Puts item on top of the stack. */
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the top item.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Returns the top item without removing it.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return top.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        top = null;
        size = 0;
    }
}
