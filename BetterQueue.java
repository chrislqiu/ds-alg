import java.awt.*;

/**
 * @implNote implement a queue using a circular array with initial capacity 8.
 *
 * Implement BetterQueueInterface and add a constructor
 *
 * You are explicitly forbidden from using java.util.Queue and any subclass
 * (including LinkedList, for example) and any other java.util.* library EXCEPT java.util.Objects.
 * Write your own implementation of a Queue.
 *
 * Another great example of why we are implementing our own queue here is that
 * our queue is actually FASTER than Java's LinkedList (our solution is 2x faster!). This is due
 * to a few reasons, the biggest of which are 1. the overhead associated with standard library
 * classes, 2. the fact that Java's LinkedList doesn't store elements next to each other, which
 * increases memory overhead for the system, and 3. LinkedList stores 2 pointers with each element,
 * which matters when you store classes that aren't massive (because it increases the size of each
 * element, making more work for the system).
 *
 * @param <E> the type of object this queue will be holding
 */
public class BetterQueue<E> implements BetterQueueInterface<E> {

    /**
     * Initial size of queue.  Do not decrease capacity below this value.
     */
    private final int INIT_CAPACITY = 8;


    /**
     * If the array needs to increase in size, it should be increased to
     * old capacity * INCREASE_FACTOR.
     *
     * If it cannot increase by that much (old capacity * INCREASE_FACTOR > max int),
     * it should increase by CONSTANT_INCREMENT.
     *
     * If that can't be done either throw OutOfMemoryError()
     *
     */
    private final int INCREASE_FACTOR = 2;
    private final int CONSTANT_INCREMENT = 1 << 5; // 32



    /**
     * If the number of elements stored is < capacity * DECREASE_FACTOR, it should decrease
     * the capacity of the UDS to max(capacity * DECREASE_FACTOR, initial capacity).
     *
     */
    private final double DECREASE_FACTOR = 0.5;


    /**
     * Array to store elements in (according to the implementation
     * note in the class header comment).
     *
     * Circular arrays work as follows:
     *
     *   1. Removing an element increments the "first" index
     *   2. Adding an element inserts it into the next available slot. Incrementing
     *      the "last" index WRAPS to the front of the array, if there are spots available
     *      there (if we have removed some elements, for example).
     *   3. The only way to know if the array is full is if the "last" index
     *      is right in front of the "first" index.
     *   4. If you need to increase the size of the array, put the elements back into
     *      the array starting with "first" (i.e. "first" is at index 0 in the new array).
     *   5. No other implementation details will be given, but a good piece of advice
     *      is to draw out what should be happening in each operation before you code it.
     *
     *   hint: modulus might be helpful
     */
    private E[] queue;
    private E[] newQ;
    private int front = 0;
    private int back = 0;
    private int currentSize = INIT_CAPACITY;
    private int numberOfObjects = 0;

    /**
     * Constructs an empty queue
     */
    @SuppressWarnings("unchecked")
    public BetterQueue(){
        //todo
        queue = (E[]) new Object [INIT_CAPACITY];
    }

    /**
     * Add an item to the back of the queue
     *
     * @param item item to push
     * @throws NullPointerException if the specified element is null
     */
    public void add(E item) {
        //todo
        if (item == null) {
            throw new NullPointerException();
        }
        if (size() >= queue.length) {
            if ((currentSize * INCREASE_FACTOR) < Integer.MAX_VALUE) {
                currentSize *= INCREASE_FACTOR;
            } else if ((currentSize + CONSTANT_INCREMENT) < Integer.MAX_VALUE) {
                currentSize += CONSTANT_INCREMENT;
            } else {
                throw new OutOfMemoryError();
            }
            newQ = (E[]) new Object[currentSize];
            for (int i = 0; i < queue.length; i++) {
                int oldPtr = (front + i) % queue.length;
                newQ[i] = queue[oldPtr];
            }
            back = size();
            queue = newQ;
            front = 0;
        }

        queue[back] = item;
        back = (back + 1) % queue.length;
        numberOfObjects++;

        if (size() < (currentSize * DECREASE_FACTOR)) {
            currentSize *= DECREASE_FACTOR;
            newQ = (E[]) new Object[currentSize];
            for (int i = 0; i < newQ.length; i++) {
                int oldPtr = (front + i) % queue.length;
                newQ[i] = queue[oldPtr];
            }
            queue = newQ;
            front = 0;
            back = size();
        }
    }

    /**
     * Returns the front of the queue (does not remove it) or <code>null</code> if the queue is empty
     *
     * @return front of the queue or <code>null</code> if the queue is empty
     */
    public E peek() {
        //todo
        if (isEmpty()) {
            return null;
        }
        return queue[front];
    }

    /**
     * Returns and removes the front of the queue
     *
     * @return the head of the queue, or <code>null</code> if this queue is empty
     */
    public E remove() {
        //todo
        if (isEmpty()) {
            return null;
        }

        if (size() < (currentSize * DECREASE_FACTOR)) {
            currentSize *= DECREASE_FACTOR;
            newQ = (E[]) new Object[currentSize];
            for (int i = 0; i < newQ.length; i++) {
                int oldPtr = (front + i) % queue.length;
                newQ[i] = queue[oldPtr];
            }
            queue = newQ;
            front = 0;
            back = size();
        }

        E output = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        numberOfObjects--;

        return output;
    }

    /**
     * Returns the number of elements in the queue
     *
     * @return integer representing the number of elements in the queue
     */
    public int size() {
        //todo
        return numberOfObjects;
    }

    /**
     * Returns whether the queue is empty
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        //todo
        return size() == 0;
    }

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     *
     * @param g graphics object to draw on
     */
    public void draw(Graphics g) {
        //DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if(g != null) g.getColor();
        //todo GRAPHICS DEVELOPER:: draw the queue how we discussed
    }
}
