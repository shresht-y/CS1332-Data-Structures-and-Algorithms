import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;


/**
 * This is a basic set of unit tests for ArrayQueue and LinkedQueue.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class QueueStudentTest {

    private static final int TIMEOUT = 200;
    private ArrayQueue<String> array;
    private LinkedQueue<String> linked;

    @Before
    public void setup() {
        array = new ArrayQueue<>();
        linked = new LinkedQueue<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayQueue.INITIAL_CAPACITY],
                array.getBackingArray());
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayEnqueue() {
        array.enqueue("0a");    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPop() {
        String temp = "0a";

        array.enqueue(temp);    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        array.enqueue("5a");    // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, array.size());

        assertSame(temp, array.dequeue());  // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, array.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPeek() {
        String temp = "0a";

        array.enqueue(temp);    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, array.size());

        assertSame(temp, array.peek());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedEnqueue() {
        linked.enqueue("0a");   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");   // 0a, 1a, 2a, 3a
        linked.enqueue("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertEquals("0a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("1a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("2a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("3a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("4a", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeue() {
        String temp = "0a";

        linked.enqueue(temp);   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");    // 0a, 1a, 2a, 3a
        linked.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        linked.enqueue("5a");    // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, linked.size());

        assertSame(temp, linked.dequeue()); // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertEquals("1a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("2a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("3a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("4a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("5a", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPeek() {
        String temp = "0a";

        linked.enqueue(temp);   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");    // 0a, 1a, 2a, 3a
        linked.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, linked.size());

        assertSame(temp, linked.peek());
    }

    //JUnits by Thaneesh Babu Krishnasamy

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullQueueEnqueue() {
        array.enqueue(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testEmptyQueueDequeue() {
        array.dequeue();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testEmptyQueuePeek() {
        array.peek();
    }

    @Test(timeout = TIMEOUT)
    public void testQueueDequeueAndEnqueue() {
        array.enqueue("1a");
        array.enqueue("2a");
        array.dequeue();

        assertEquals(1, array.size());

        array.enqueue("3a");

        Object[] expected = {null, "2a", "3a", null, null, null, null, null, null};

        assertEquals("2a", array.peek());
        assertEquals(2, array.size());
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testQueueEnqueueWrap1() {
        array.enqueue("0a");   // 0a

        array.dequeue();

        assertEquals(0, array.size());

        array.enqueue("1a");   // null, 1a
        array.enqueue("2a");   // null, 1a, 2a
        array.enqueue("3a");   // null, 1a, 2a, 3a
        array.enqueue("4a");   // null, 1a, 2a, 3a, 4a
        array.enqueue("5a");   // null, 1a, 2a, 3a, 4a, 5a
        array.enqueue("6a");   // null, 1a, 2a, 3a, 4a, 5a, 6a
        array.enqueue("7a");   // null, 1a, 2a, 3a, 4a, 5a, 6a, 7a
        array.enqueue("8a");   // null, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a

        array.enqueue("9a");

        Object[] expected = {"9a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a"};

        assertEquals(9, array.size());
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testQueueEnqueueWrap2() {
        array.enqueue("0a");   // 0a
        array.enqueue("1a");   // 0a, 1a
        array.enqueue("2a");   // 0a, 1a, 2a
        array.enqueue("3a");   // 0a, 1a, 2a, 3a

        array.dequeue();
        array.dequeue();
        array.dequeue();

        assertEquals(1, array.size());

        array.enqueue("4a");   // null, null, null, 3a, 4a
        array.enqueue("5a");   // null, null, null, 3a, 4a, 5a
        array.enqueue("6a");   // null, null, null, 3a, 4a, 5a, 6a
        array.enqueue("7a");   // null, null, null, 3a, 4a, 5a, 6a, 7a
        array.enqueue("8a");   // null, null, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("9a");   // 9a, null, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("10a");  // 9a, 10a, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("11a");  // 9a, 10a, 11a, 3a, 4a, 5a, 6a, 7a, 8a

        Object[] expected = {"9a", "10a", "11a", "3a", "4a", "5a", "6a", "7a", "8a"};

        assertEquals(9, array.size());
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testQueueWrapAndDequeue1() {
        array.enqueue("0a");   // 0a
        array.enqueue("1a");   // 0a, 1a
        array.enqueue("2a");   // 0a, 1a, 2a
        array.enqueue("3a");   // 0a, 1a, 2a, 3a

        array.dequeue();
        array.dequeue();
        array.dequeue();

        assertEquals(1, array.size());

        array.enqueue("4a");   // null, null, null, 3a, 4a
        array.enqueue("5a");   // null, null, null, 3a, 4a, 5a
        array.enqueue("6a");   // null, null, null, 3a, 4a, 5a, 6a
        array.enqueue("7a");   // null, null, null, 3a, 4a, 5a, 6a, 7a
        array.enqueue("8a");   // null, null, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("9a");   // 9a, null, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("10a");  // 9a, 10a, null, 3a, 4a, 5a, 6a, 7a, 8a

        array.dequeue();

        Object[] expected = {"9a", "10a", null, null, "4a", "5a", "6a", "7a", "8a"};

        assertEquals(7, array.size());
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testQueueWrapAndDequeue2() {
        array.enqueue("0a");   // 0a
        array.enqueue("1a");   // 0a, 1a
        array.enqueue("2a");   // 0a, 1a, 2a
        array.enqueue("3a");   // 0a, 1a, 2a, 3a

        array.dequeue();
        array.dequeue();
        array.dequeue();

        assertEquals(1, array.size());

        array.enqueue("4a");   // null, null, null, 3a, 4a
        array.enqueue("5a");   // null, null, null, 3a, 4a, 5a
        array.enqueue("6a");   // null, null, null, 3a, 4a, 5a, 6a
        array.enqueue("7a");   // null, null, null, 3a, 4a, 5a, 6a, 7a
        array.enqueue("8a");   // null, null, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("9a");   // 9a, null, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("10a");  // 9a, 10a, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("11a");  // 9a, 10a, 11a, 3a, 4a, 5a, 6a, 7a, 8a

        array.dequeue();

        array.enqueue("12a");  // 9a, 10a, 11a, 12a, 4a, 5a, 6a, 7a, 8a

        Object[] expected = {"9a", "10a", "11a", "12a", "4a", "5a", "6a", "7a", "8a"};

        assertEquals(9, array.size());
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testQueueResize() {
        array.enqueue("0a");   // 0a
        array.enqueue("1a");   // 0a, 1a
        array.enqueue("2a");   // 0a, 1a, 2a
        array.enqueue("3a");   // 0a, 1a, 2a, 3a
        array.enqueue("4a");   // 0a, 1a, 2a, 3a, 4a
        array.enqueue("5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        array.enqueue("6a");   // 0a, 1a, 2a, 3a, 4a, 5a, 6a
        array.enqueue("7a");   // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a
        array.enqueue("8a");   // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("9a");   // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a
        Object[] expected = {"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "9a", null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testQueueUnwrapAfterResize() {
        array.enqueue("0a");   // 0a
        array.enqueue("1a");   // 0a, 1a
        array.enqueue("2a");   // 0a, 1a, 2a
        array.enqueue("3a");   // 0a, 1a, 2a, 3a

        array.dequeue();
        array.dequeue();
        array.dequeue();

        assertEquals(1, array.size());

        array.enqueue("4a");   // null, null, null, 3a, 4a
        array.enqueue("5a");   // null, null, null, 3a, 4a, 5a
        array.enqueue("6a");   // null, null, null, 3a, 4a, 5a, 6a
        array.enqueue("7a");   // null, null, null, 3a, 4a, 5a, 6a, 7a
        array.enqueue("8a");   // null, null, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("9a");   // 9a, null, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("10a");  // 9a, 10a, null, 3a, 4a, 5a, 6a, 7a, 8a
        array.enqueue("11a");  // 9a, 10a, 11a, 3a, 4a, 5a, 6a, 7a, 8a

        array.enqueue("12a");

        Object[] expected = {"3a", "4a", "5a", "6a", "7a", "8a", "9a", "10a", "11a", "12a", null, null, null, null, null, null, null, null};

        assertEquals(10, array.size());
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullLinkedQueueEnqueue() {
        linked.enqueue(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testEmptyLinkedQueueDequeue() {
        linked.dequeue();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testEmptyLinkedQueuePeek() {
        linked.peek();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedQueueDequeueAndEnqueue() {
        linked.enqueue("1a");
        linked.enqueue("2a");
        linked.dequeue();

        assertEquals(1, linked.size());

        linked.enqueue("3a");

        assertEquals(2, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertEquals("2a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("3a", cur.getData());
    }
}