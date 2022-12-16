public class BST<Key extends Comparable<Key>, Value>
{
    private Node root;
    private int N;

    // CONSTRUCTOR
    public BST()
    {
        this.root = null;
        this.N = 0;
    }

    // PUBLIC METHODS

    //
    // insert a new (key, val) into tree
    // or replace value of existing key
    //
    public void insert(Key key, Value val)
    {
        //TO BE IMPLEMENTED
        root = insertNode(root, key, val);

    }

    Node insertNode(Node node, Key key, Value val) {
        if (node == null) {
            N++;
            return new Node(key, val);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = insertNode(node.left, key, val);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insertNode(node.right, key, val);
        } else {
            node.val = val;
        }
        node.height = auxHeight(node);
        return node;
    }

    //
    // get the value associated with the given key;
    // return null if key doesn't exist
    //
    public Value get(Key key)
    {
        if (key == null) {
            return null;
        }
        //TO BE IMPLEMENTED
        Node node = retrieve(root, key);
        if (node == null) {
            return null;
        }
        return node.val;
    }

    Node retrieve(Node node, Key key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            return retrieve(node.left, key);
        } else if (key.compareTo(node.key) > 0){
            return retrieve(node.right, key);
        } else {
            return node;
        }
    }

    //
    // return true if the tree
    // is empty and false
    // otherwise
    //
    public boolean isEmpty()
    {
        return root == null;
    }

    //
    // return the number of Nodes
    // in the tree
    //
    public int size()
    {
        return N;
    }

    //
    // returns the height of the tree
    //
    public int height()
    {
        return height(root);
    }

    //
    // returns the height of node
    // with given key in the tree;
    // return -1 if the key does
    // not exist in the tree
    //
    public int height(Key key)
    {
        //TO BE IMPLEMENTED
        if (key == null) {
            return -1;
        }
        Node node = retrieve(root, key);
        return auxHeight(node);
    }

    private int auxHeight(Node node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(auxHeight(node.left), auxHeight(node.right));
    }

    //
    // return a String version of the tree
    // level by level
    //
    public String toString()
    {
        String str = "";
        Pair x = null;
        Queue<Pair> queue = new Queue<Pair>(N);
        int level = 0;
        queue.enqueue(new Pair(root, level));
        str += "Level 0: ";

        while(!queue.isEmpty())
        {
            x = queue.dequeue();
            Node n = x.node;

            if(x.depth > level)
            {
                level++;
                str += "\nLevel " + level + ": ";
            }

            if(n != null)
            {
                str += "|" + n.toString() + "|";
                queue.enqueue(new Pair(n.left, x.depth + 1));
                queue.enqueue(new Pair(n.right, x.depth + 1));
            }
            else
                str += "|null|";
        }

        str += "\n";
        return str;
    }


    // PRIVATE METHODS

    //
    // return the height of x
    // or -1 if x is null
    //
    private int height(Node x)
    {
        if(x == null)
            return -1;

        return x.height;
    }

    // NODE CLASS
    public class Node
    {
        Key key;
        Value val;
        Node left, right;
        int height;

        public Node(Key key, Value val)
        {
            this.key = key;
            this.val = val;
        }

        public String toString()
        {
            return "(" + key + ", " + val + "): " + height;
        }
    }

    // PAIR CLASS
    public class Pair
    {
        Node node;
        int depth;

        public Pair(Node node, int depth)
        {
            this.node = node;
            this.depth = depth;
        }
    }

}
