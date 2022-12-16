public class RLRBT<Key extends Comparable<Key>, Value>
{
    private Node root;
    private int N;

    // CONSTRUCTOR 
    public RLRBT()
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
        Node node = new Node(key, val);
        if (size() == 0) {
            root = node;
            node.isRed = false;
            N++;
        }
        root = insertNode(root, key, val);
        node.isRed = false;
        root.isRed = false;
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
        //Corrects left leaning red
        if (node.left != null && node.left.isRed) {
            node = rotateRight(node);
        }
        //If right leaning red has red red parent child relationship
        if ((node.right != null && node.right.isRed) && (node.right.right != null && node.right.right.isRed)) {
            node = rotateLeft(node);
        }
        //Color flips so that node cannot have two red child
        if ((node.left != null && node.left.isRed) && (node.right != null && node.right.isRed)) {
            colorFlip(node);
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
    // return String representation of tree
    // level by level
    //
    public String toString()
    {
        String ret = "Level 0: ";
        Pair x = null;
        Queue<Pair> queue = new Queue<Pair>(N);
        int level = 0;
        queue.enqueue(new Pair(root, level));

        while(!queue.isEmpty())
        {
            x = queue.dequeue();
            Node n = x.node;

            if(x.depth > level)
            {
                level++;
                ret += "\nLevel " + level + ": ";
            }

            if(n != null)
            {
                ret += "|" + n.toString() + "|";
                queue.enqueue(new Pair(n.left, x.depth + 1));
                queue.enqueue(new Pair(n.right, x.depth + 1));
            }
            else
                ret += "|null|";
        }

        ret += "\n";
        return ret;
    }


    //
    // return the black height of the tree
    //
    public int blackHeight()
    {
        //TO BE IMPLEMENTED
        if (size() == 0 || size() == 1) {
            return 0;
        }
        return auxBlackHeight(root) - 1;
    }

    private int auxBlackHeight(Node node) {
        int lHeight = 0;
        int rHeight = 0;
        if (node.left != null) {
            lHeight = auxBlackHeight(node.left);
        }
        if (node.right != null) {
            rHeight = auxBlackHeight(node.right);
        }
        if (!node.isRed) {
            return 1 + Math.max(lHeight, rHeight);
        } else {
            return Math.max(lHeight, rHeight);
        }
    }

    // PRIVATE METHODS 

    //
    // swap colors of two Nodes
    //
    private void swapColors(Node x, Node y)
    {
        if(x.isRed == y.isRed)
            return;

        boolean temp = x.isRed;
        x.isRed = y.isRed;
        y.isRed = temp;
    }

    //
    // rotate a link to the right
    //
    private Node rotateRight(Node x)
    {
        Node temp = x.left;
        x.left = temp.right;
        temp.right = x;
        swapColors(x, temp);
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        temp.height = Math.max(height(temp.left), x.height) + 1;
        return temp;
    }

    //
    // rotate a link to the left
    //
    private Node rotateLeft(Node x)
    {
        Node temp = x.right;
        x.right = temp.left;
        temp.left = x;
        swapColors(x, temp);
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        temp.height = Math.max(height(temp.right), x.height) + 1;
        return temp;
    }

    //
    // color flip
    //
    private Node colorFlip(Node x)
    {
        if(x.left == null || x.right == null)
            return x;

        if(x.left.isRed == x.right.isRed)
        {
            x.left.flip();
            x.right.flip();
            x.flip();
        }

        return x;
    }

    //
    // return the neight of Node x
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
        boolean isRed;

        public Node(Key key, Value val)
        {
            this.key = key;
            this.val = val;
            this.isRed = true;
        }

        public String toString()
        {
            return "(" + key + ", " + val + "): "
                    + height + "; " + (this.isRed?"R":"B");
        }

        public void flip()
        {
            this.isRed = !this.isRed;
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
