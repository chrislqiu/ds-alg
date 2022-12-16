import java.security.NoSuchAlgorithmException;


public class BetterCustomerOrderQueue {
    private CustomerOrder[] orderList;
    private CustomerOrderHash table;
    private int numOrders;

    /**
     *
     * Return the CustomerOrderQueue
     *
     */
    public CustomerOrder[] getOrderList() {
        minHeap();
        return orderList;
    }

    /**
     *
     * Return the number of orders in the queue
     *
     */
    public int getNumOrders() {
        return numOrders;
    }


    /**
     * Constructor of the class.
     * TODO: complete the default Constructor of the class
     *
     * Initialize a new CustomerOrderQueue and CustomerOrderHash
     *
     */
    public BetterCustomerOrderQueue(int capacity) {
        orderList = new CustomerOrder[capacity];
        table = new CustomerOrderHash(capacity);
        this.numOrders = 0;
    }

    /**
     * TODO: insert a new customer order.
     *
     * @return return the index at which the customer was inserted;
     * return -1 if the Customer could not be inserted
     *
     */
    public int insert(CustomerOrder c) throws NoSuchAlgorithmException {
        if (numOrders >= orderList.length || table.get(c.getName()) != null) {
            return -1;
        }
        orderList[numOrders] = c;
        int current = numOrders;
        numOrders += 1;

        while (orderList[current].compareTo(orderList[parent(current)]) == 1 && current > 0) {
            swap(current, parent(current));
            current = parent(current);
        }
        c.setPosInQueue(current);
        table.put(c);
        return c.getPosInQueue();
    }

    /**
     * TODO: remove the customer with the highest priority from the queue
     *
     * @return return the customer removed
     *
     */
    public CustomerOrder delMax() throws NoSuchAlgorithmException {
        if (numOrders == 0) {
            return null;
        }
        CustomerOrder popped = orderList[0];
        table.remove(orderList[0].getName());
        popped.setPosInQueue(-1);
        if (numOrders == 1) {
            orderList[--numOrders] = null;
        } else {
            orderList[0] = orderList[--numOrders];
            orderList[0].setPosInQueue(0);
            orderList[numOrders] = null;
            minHeapify(0);
        }
        return popped;
    }

    /**
     * TODO: return but do not remove the customer with the maximum priority
     *
     * @return return the customer with the maximum priority
     *
     */
    public CustomerOrder getMax() {
        return orderList[0];
    }

    /**
     * TODO: check if the priority queue is empty or not
     *
     * @return return true if the queue is empty; false else
     *
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * TODO: return the number of Customers currently in the queue
     *
     * @return return the number of Customers currently in the queue
     *
     */
    public int size() {
        return numOrders;
    }

    /**
     *
     * TODO: return the CustomerOrder with the given name
     *
     */
    public CustomerOrder get(String name) throws NoSuchAlgorithmException {
        return table.get(name);
    }

    /**
     *
     * TODO: remove and return the CustomerOrder with the specified name from the queue;
     * TODO: return null if the CustomerOrder isn't in the queue
     *
     */
    public CustomerOrder remove(String name) throws NoSuchAlgorithmException {
        if (table.get(name) == null) {
            return null;
        }
        CustomerOrder out = table.get(name);
        table.remove(name);
        if (size() == 1) {
            orderList[numOrders - 1] = null;
        } else {
            int swappedPos = out.getPosInQueue();
            swap(out.getPosInQueue(), numOrders - 1);
            orderList[numOrders - 1] = null;
            if (swappedPos != numOrders - 1)
                minHeapify(swim(swappedPos));
        }
        numOrders--;
        out.setPosInQueue(-1);
        return out;
    }

    /**
     *
     * TODO: update the orderDeliveryTime of the Customer with the specified name to newTime
     *
     */
    public void update(String name, int newTime) throws NoSuchAlgorithmException {
        CustomerOrder updateC = table.get(name);
        if (updateC == null) {
            return;
        }
        remove(name);
        updateC.setOrderDeliveryTime(newTime);
        insert(updateC);

    }

    //Swaps item in array
    private void swap(int current, int parent) {
        orderList[current].setPosInQueue(parent);
        orderList[parent].setPosInQueue(current);
        CustomerOrder temp = orderList[current];
        orderList[current] = orderList[parent];
        orderList[parent] = temp;
    }

    private boolean isLeaf(int i) {
        if (rightChild(i) > orderList.length || leftChild(i) > orderList.length) {
            return true;
        }
        return false;
    }
    //Parent
    private int parent(int i) {
        return (i - 1) / 2;
    }
    //Left Child
    private int leftChild(int i) {
        return (i * 2) + 1;
    }
    //Right Child
    private int rightChild(int i) {
        return (i * 2) + 2;
    }

    public int swim(int i) {

        while (i > 0 && orderList[i].compareTo(orderList[parent(i)]) == -1) {
            swap(i, parent(i));
            i = (i - 1) / 2;
        }

        return i;
    }
    public void minHeap() {
        for (int i = (numOrders - 1 / 2); i >= 1; i--) {
            minHeapify(i);
        }
    }

    //Reorders the minHeap
    private void minHeapify (int i) {
        if (!isLeaf(i)) {
            if (leftChild(i) < orderList.length && rightChild(i) < orderList.length) {
                //If both children exists
                if (orderList[leftChild(i)] != null && orderList[rightChild(i)] != null) {
                    if (orderList[i].compareTo(orderList[leftChild(i)]) == -1 ||
                            orderList[i].compareTo(orderList[rightChild(i)]) == -1) {
                        if (orderList[leftChild(i)].compareTo(orderList[rightChild(i)]) == 1) {
                            swap(i, leftChild(i));
                            minHeapify(leftChild(i));
                        } else {
                            swap(i, rightChild(i));
                            minHeapify(rightChild(i));
                        }
                    }
                    //If Right Child exist, but not left
                } else if (orderList[leftChild(i)] == null && orderList[rightChild(i)] != null) {
                    if (orderList[i].compareTo(orderList[rightChild(i)]) == -1) {
                        swap(i, rightChild(i));
                        minHeapify(rightChild(i));
                    }
                    //If Left Child exist, but not right
                } else if (orderList[leftChild(i)] != null && orderList[rightChild(i)] == null) {
                    if (orderList[i].compareTo(orderList[leftChild(i)]) == -1) {
                        swap(i, leftChild(i));
                        minHeapify(leftChild(i));
                    }
                }
            } else if (leftChild(i) < orderList.length && rightChild(i) >= orderList.length) {
                if (orderList[leftChild(i)] != null) {
                    if (orderList[i].compareTo(orderList[leftChild(i)]) == -1) {
                        swap(i, leftChild(i));
                        minHeapify(leftChild(i));
                    }
                }
            } else if (rightChild(i) < orderList.length && leftChild(i) >= orderList.length) {
                if (orderList[rightChild(i)] != null) {
                    if (orderList[i].compareTo(orderList[rightChild(i)]) == -1) {
                        swap(i, rightChild(i));
                        minHeapify(rightChild(i));
                    }
                }
            }
        }
    }
}
