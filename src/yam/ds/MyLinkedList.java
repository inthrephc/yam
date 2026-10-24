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
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /** Inserts item at the front. O(1). */
    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = head;
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        size++;
    }

    /** Appends item at the end. O(1). */
    public void addLast(T item) {
        Node<T> newNode = new Node<>(item);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Inserts item at position index (0 = front, size = end). O(n).
     *
     * @throws IndexOutOfBoundsException if {@code index < 0 || index > size}
     */
    public void add(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            addFirst(item);
        } else if (index == size) {
            addLast(item);
        } else {
            Node<T> prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            Node<T> newNode = new Node<>(item);
            newNode.next = prev.next;
            prev.next = newNode;
            size++;
        }
    }

    /**
     * Inserts item before the first element that is greater than it, so a sorted list stays
     * sorted. Equal elements keep insertion order (the new one goes after them). O(n).
     */
    public void addSorted(T item, Comparator<? super T> comparator) {
        if (head == null || comparator.compare(head.data, item) > 0) {
            addFirst(item);
            return;
        }
        Node<T> current = head;
        while (current.next != null && comparator.compare(current.next.data, item) <= 0) {
            current = current.next;
        }
        if (current.next == null) {
            addLast(item);
        } else {
            Node<T> newNode = new Node<>(item);
            newNode.next = current.next;
            current.next = newNode;
            size++;
        }
    }

    /**
     * Returns the element at index. O(n).
     *
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size}
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Removes and returns the first element. O(1).
     *
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        T data = head.data;
        head = head.next;
        size--;
        if (head == null) {
            tail = null;
        }
        return data;
    }

    /**
     * Removes and returns the element at index. O(n).
     *
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size}
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            return removeFirst();
        }
        Node<T> prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        Node<T> target = prev.next;
        prev.next = target.next;
        if (target == tail) {
            tail = prev;
        }
        size--;
        return target.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator(head);
    }

    /** Returns the elements as {@code "[a, b, c]"} (for debugging). */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    /** Iterates from head to tail. {@code remove()} is not supported. */
    private class LinkedListIterator implements Iterator<T> {

        private Node<T> current;

        LinkedListIterator(Node<T> start) {
            this.current = start;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}
