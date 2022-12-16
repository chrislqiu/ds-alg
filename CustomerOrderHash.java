import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class CustomerOrderHash {
    private ArrayList<CustomerOrder>[] table;
    private int numOrders;
    private int tableCapacity;

    /**
     * Constructor of the class.
     * TODO: complete the default Constructor of the class
     *
     * Initilalize a new CustomerOrder array with the argument passed.
     *
     */
    public CustomerOrderHash(int capacity) {
        if (isPrime(capacity)) {
            tableCapacity = capacity;
        } else {
            tableCapacity = getNextPrime(capacity);
        }
        this.table = new ArrayList[tableCapacity];
        this.numOrders = 0;
    }


    /**
     *
     * TODO: return the CustomerOrder with the given name
     * TODO: return null if the CustomerOrder is not in the table
     *
     */
    public CustomerOrder get(String name) throws NoSuchAlgorithmException {
        int hashIndex = getHashIndex(name);
        if (hashIndex < 0) {
            hashIndex += table.length;
        }
        CustomerOrder out = null;
        if (table[hashIndex] != null && !table[hashIndex].isEmpty()) {
            for (int i = 0; i < table[hashIndex].size(); i++) {
                if (table[hashIndex].get(i).getName().equals(name)) {
                    out = table[hashIndex].get(i);
                }
            }
        } else {
            return null;
        }
        return out;
    }


    /**
     *
     * TODO: put CustomerOrder c into the table
     *
     */
    public void put(CustomerOrder c) throws NoSuchAlgorithmException {
        int hashIndex = getHashIndex(c.getName());
        if (hashIndex < 0) {
            hashIndex += table.length;
        }
        if (table[hashIndex] != null) {
            if (!table[hashIndex].contains(c)) {
                table[hashIndex].add(c);
                numOrders++;
            }
        } else {
            table[hashIndex] = new ArrayList<>();
            table[hashIndex].add(c);
            numOrders++;
        }
    }



    /**
     *
     * TODO: remove and return the CustomerOrder with the given name;
     * TODO: return null if CustomerOrder doesn't exist
     *
     */
    public CustomerOrder remove(String name) throws NoSuchAlgorithmException {
        int hashIndex = getHashIndex(name);
        if (hashIndex < 0) {
            hashIndex += table.length;
        }
        CustomerOrder out = null;
        if (table[hashIndex] != null && !table[hashIndex].isEmpty()) {
            for (int i = 0; i < table[hashIndex].size(); i++) {
                if (table[hashIndex].get(i).getName().equals(name)) {
                    out = table[hashIndex].get(i);
                    table[hashIndex].remove(i);
                    numOrders--;
                    break;
                }
            }
        }
        return out;
    }


    /**
     *
     * TODO: return the number of Customers in the table
     *
     */
    public int size() {
        return numOrders;
    }



    //get the next prime number p >= num
    private int getNextPrime(int num) {
        if (num == 2 || num == 3)
            return num;

        int rem = num % 6;

        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }

        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }

        return num;
    }

    //determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if (num % 2 == 0) {
            return false;
        }

        int x = 3;

        for (int i = x; i < num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    private int getHashIndex(String s) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] output = digest.digest(s.getBytes());
        String hashName = new String(output);
        int hashIndex = hashName.hashCode();
        return hashIndex % table.length;
    }

    public ArrayList<CustomerOrder>[] getArray() {
        return this.table;
    }

}
