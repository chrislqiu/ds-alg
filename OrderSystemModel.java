import java.security.NoSuchAlgorithmException;

public class OrderSystemModel {
    private BetterCustomerOrderQueue orderList;
    private int capacityThreshold;
    private int ordersDelayed;
    private int ordersOnTime;
    private int ordersCanceled;
    private int time;
    private int totalDelayTime;

    public int getOrdersDelayed() {
        return ordersDelayed;
    }

    public int getOrdersOnTime() {
        return ordersOnTime;
    }

    public int getOrdersCanceled() {
        return ordersCanceled;
    }

    public int getTotalDelayTime() {
        return totalDelayTime;
    }

    public BetterCustomerOrderQueue getOrderList() {
        return orderList;
    }

    /**
     * Constructor of the class.
     *
     * Initialize a new OrderSystemModel and OrderSystemModel
     *
     */
    public OrderSystemModel(int capacityThreshold) {
        this.capacityThreshold = capacityThreshold;
        this.orderList = new BetterCustomerOrderQueue(this.capacityThreshold);
        this.ordersDelayed = 0;
        this.ordersOnTime = 0;
        this.ordersCanceled = 0;
        this.time = 0;
        this.totalDelayTime = 0;
    }


    /**
     *
     * TODO: Process a new CustomerOrder with a given name.
     *
     */
    public String process(String name, int orderTime, int deliveryTime) throws NoSuchAlgorithmException {
        CustomerOrder newCus = new CustomerOrder(name, orderTime, deliveryTime);
        if (orderList.size() != orderList.getOrderList().length) {
            orderList.insert(newCus);
            return name;
        } else if (newCus.getOrderDeliveryTime() < orderList.getMax().getOrderDeliveryTime()) {
            complete(newCus);
            return null;
        } else {
            CustomerOrder complete = orderList.delMax();
            complete(complete);
            orderList.insert(newCus);
            return complete.getName();
        }
    }

    /**
     *
     * TODO: Complete the highest priority order
     *
     */
    public String completeNextOrder() throws NoSuchAlgorithmException {
        if (orderList.size() == 0) {
            return null;
        }
        CustomerOrder complete = orderList.delMax();
        complete(complete);
        return complete.getName();
    }

    /**
     *
     * TODO: Update the delivery time of the order for the given name
     *
     */
    public String updateOrderTime(String name, int newDeliveryTime) throws NoSuchAlgorithmException {
        if (orderList.get(name) == null) {
            return null;
        }
        CustomerOrder complete = orderList.get(name);
        if (newDeliveryTime < orderList.getMax().getOrderDeliveryTime()) {
            if (newDeliveryTime < time) {
                cancelOrder(name);
                return name;
            }
            complete(complete);
            orderList.remove(name);
            return name;
        }  else {
            orderList.update(name, newDeliveryTime);
            return null;
        }
    }

    /**
     *
     * TODO: Cancel the order for the given name
     *
     */
    public CustomerOrder cancelOrder(String name) throws NoSuchAlgorithmException {
        if (orderList.get(name) != null) {
            CustomerOrder temp = orderList.remove(name);
            ordersCanceled++;
            return temp;
        }
        return null;
    }

    private void complete(CustomerOrder customer) {
        int delayTime = time - customer.getOrderDeliveryTime();
        if (delayTime <= 0) {
            ordersOnTime++;
        } else {
            ordersDelayed++;
            totalDelayTime += delayTime;
        }
        time += 1;
    }
}
