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
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /** Adds item at the rear. */
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the front item.
     *
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T data = front.data;
        front = front.next;
        size--;
        if (front == null) {
            rear = null;
        }
        return data;
    }

    /**
     * Returns the front item without removing it.
     *
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return front.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
}
