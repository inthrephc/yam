package yam.ds;

/**
 * Singly linked node shared by {@link MyLinkedList}, {@link MyStack} and {@link MyQueue}.
 *
 * @param <T> type of the stored value
 */
class Node<T> {

    T data;
    Node<T> next;

    Node(T data) {
        this.data = data;
        this.next = null;
    }
}
