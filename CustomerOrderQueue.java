public class CustomerOrderQueue {
    private CustomerOrder[] orderList;
    private int numOrders;
    private int length;


    /**
     *
     * @return return the priority queue
     *
     */
    public CustomerOrder[] getOrderList() {
        minHeap();
        return orderList;
    }

    /**
     *
     * @return return the number of orders
     *
     */
    public int getNumOrders() {
        return this.numOrders;
    }

    /**
     * Constructor of the class.
     * TODO: complete the default Constructor of the class
     *
     * Initilalize a new CustomerOrder array with the argument passed.
     *
     */
    public CustomerOrderQueue(int capacity) {
        this.length = capacity;
        this.numOrders = 0;
        orderList = new CustomerOrder[capacity];
    }

    /**
     * TODO: insert a new customer order into the priority queue.
     *
     * @return return the index at which the customer was inserted
     *
     */
    public int insert(CustomerOrder c) {
        if (numOrders >= length) {
            return -1;
        }
        orderList[numOrders] = c;
        int current = numOrders;

        while (orderList[current].compareTo(orderList[parent(current)]) == 1 && current > 0) {
            swap(current, parent(current));
            current = parent(current);
        }
        numOrders += 1;
        return current;
    }

    /**
     * TODO: remove the customer with the highest priority from the queue
     *
     * @return return the customer removed
     *
     */
    public CustomerOrder delMax() {
        if (numOrders == 0) {
            return null;
        }
        CustomerOrder popped = orderList[0];
        if (numOrders == 1) {
            orderList[--numOrders] = null;
            return popped;
        } else {
            orderList[0] = orderList[--numOrders];
            orderList[numOrders] = null;
            minHeapify(0);
            return popped;
        }
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
     * TODO: check if the priority queue is empty or not
     *
     * @return return true if the queue is empty; false else
     *
     */
    public boolean isEmpty() {
        return size() == 0;
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

    //Reorders the minHeap
    private void minHeapify (int i) {
        if (!isLeaf(i)) {
            if (leftChild(i) < length && rightChild(i) < length) {
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
                    //If Right Child exist, but not left (Should never happen bc it's a heap lol)
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
            } else if (leftChild(i) < length && rightChild(i) >= length) {
                if (orderList[leftChild(i)] != null) {
                    if (orderList[i].compareTo(orderList[leftChild(i)]) == -1) {
                        swap(i, leftChild(i));
                        minHeapify(leftChild(i));
                    }
                }
            } else if (rightChild(i) < length && leftChild(i) >= length) {
                if (orderList[rightChild(i)] != null) {
                    if (orderList[i].compareTo(orderList[rightChild(i)]) == -1) {
                        swap(i, rightChild(i));
                        minHeapify(rightChild(i));
                    }
                }
            }
        }
    }

    //Swaps item in array
    private void swap(int current, int parent) {
        CustomerOrder temp = orderList[current];
        orderList[current] = orderList[parent];
        orderList[parent] = temp;
    }

    private boolean isLeaf(int i) {
        if (rightChild(i) > length || leftChild(i) > length) {
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

    public void minHeap() {
        for (int i = (numOrders - 1 / 2); i >= 1; i--) {
            minHeapify(i);
        }
    }
}
