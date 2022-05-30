import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Shreshta Yadav
 * @version 1.0
 * @userid Syadav73
 * @GTID 903688946
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is either too high or too low!");
        } else if (data == null) {
            throw new IllegalArgumentException("The data you entered is null!");
        }
        //index = 0
        //size = index
        if (size == 0) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
            size++;
        } else if (index == 0) {
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(data);
            newNode.setNext(head.getNext());
            newNode.setData(head.getData());
            head.setNext(newNode);
            head.setData(data);
            size++;
        } else if (size == index) {
            addToFront(data);
            head = head.getNext();
            //size++;
        } else {
            CircularSinglyLinkedListNode<T> currentNode = head;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNext();
            }
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode(data, currentNode.getNext());
            currentNode.setNext(newNode);
            size++;
        }

    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null!");
        }
        this.addAtIndex(0, data);
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null!");
        }
        this.addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is either too high or too low!");
        }
        T removedNodeData;
        CircularSinglyLinkedListNode<T> currentNode = head;
        if (index == 0) {
            removedNodeData = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
        } else {
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNext();
            }
            removedNodeData = currentNode.getNext().getData();
            currentNode.setNext(currentNode.getNext().getNext());
        }
        size--;
        return removedNodeData;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty!");
        }
        return this.removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty!");
        }
        return this.removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This node is not in the list!");
        }
        if (index == 0) {
            return head.getData();
        }
        CircularSinglyLinkedListNode<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                return currentNode.getData();
            } else {
                currentNode = currentNode.getNext();
            }
        }
        return null;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        head = null;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) { //not working
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        CircularSinglyLinkedListNode<T> currentNode = head;
        T lastOccuranceData = null;
        int lastOccurranceIndex = 0;
        for (int i = 0; i < size; i++) {
            if (data == currentNode.getData()) {
                lastOccuranceData = currentNode.getData();
                lastOccurranceIndex = i;
            }
            currentNode = currentNode.getNext();
        }
        if (lastOccuranceData == null) {
            throw new NoSuchElementException("Data passeed in is not in the list!");
        }
        removeAtIndex(lastOccurranceIndex);
        return lastOccuranceData;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            array[i] = currentNode.getData();
            currentNode = currentNode.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
