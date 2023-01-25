package data_structures.bst;

public class BinarySearchTree<T extends Comparable<T>> {

    //Track the number of nodes in this BST
    private int nodeCount = 0;

    //This BST is a rooted tree, so we maintain a handle on the root node
    private Node<T> root = null;

    //Internal node containing node references
    //and the actual node data
    private static class Node<T> {
        T data;
        Node<T> left, right;

        public Node(Node<T> left, Node<T> right, T data) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    //Check if this binary tree is empty
    public boolean isEmpty() {
        return nodeCount == 0;
    }

    //Get the number of nodes in this binary tree
    public int size() {
        return nodeCount;
    }

    //Add an element to this binary tree. Returns true
    //if we successfully perform an insertion
    public boolean add(T element) {
        //Check if the value already exists in this
        //binary tree, if it does ignore adding it
        if (contains(element)) {
            return false;
        }
        //Otherwise add this element ot the binary tree
        else {
            root = add(root, element);
            nodeCount++;
            return true;
        }
    }

    //Method to recursively add a value in the binary tree
    private Node<T> add(Node<T> node, T element) {
        //Base case: found a leaf node
        if (node == null) {
            node = new Node<>(null, null, element);
        }
        //Place lower elements values in the left subtree
        else {
            if (element.compareTo(node.data) < 0) {
                node.left = add(node.left, element);
            } else {
                node.right = add(node.right, element);
            }
        }
        return node;
    }

    //Remove a value from this binary tree, if it exists
    public boolean remove(T element) {
        //Make sure the node we want to remove
        //actually exists before we remove it
        if (contains(element)) {
            root = remove(root, element);
            nodeCount--;
            return true;
        } else {
            return false;
        }
    }

    private Node<T> remove(Node<T> node, T element) {
        //Where this base case happens?
        if (node == null) return null;

        int cmp = element.compareTo(node.data);

        //Dig into left subtree, the value we're looking
        //for is smaller than the current value
        if (cmp < 0) {
            node.left = remove(node.left, element);
        }
        //Dig into right subtree, the value we're looking
        //for is greater than the current value
        else if (cmp > 0) {
            node.right = remove(node.right, element);
        }
        //Found the node we wish to remove
        else {
            //This is the case with only a right subtree or
            //no subtree at all. In this situation just
            //swap the node we with to remove with its right child.
            if (node.left == null) {
                Node<T> rightChild = node.right;
                node.data = null;
                return rightChild;
            }
            //This is the case with only a left subtree or
            //no subtree at all. In this situation just
            //swap the node we with to remove with its left child.
            else if (node.right == null) {
                Node<T> leftNode = node.left;
                node.data = null;
                return leftNode;
            }
            //When removing a node from a binary tree with two links the
            //successor of the node removed can either be the largest
            //value in the left subtree or the smallest value in the right
            //subtree. In this implementation I have decided to find the
            //smallest value in the right subtree with can be found be
            //traversing as far left as possible in the right subtree.
            else {
                //Find the leftmost (find min) node in the right subtree
                Node<T> tmp = findMin(node.right);
                //Swap the data
                node.data = tmp.data;
                //Got into the right subtree and remove the leftmost node we
                //found and swapped data with. This prevents us from having
                //two nodes in our tree with the same value
                node.right = remove(node.right, tmp.data);

                //If instead we wanted to find the largest node in the left
                //subtree as opposed to smallest node in the right subtree
                //here is what we would do:
                //Node<T> tmp = findMax(node.left);
                //node.data = tmp.data;
                //node.left = remove(node.left, tmp.data);
            }
        }
        return node;
    }

    //Helper method to find the leftmost node (which has the smallest value)
    private Node<T> findMin(Node<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    //Helper method to find the rightmost node (which has the largest value)
    private Node<T> findMax(Node<T> node) {
        while (node.right != null) node = node.right;
        return node;
    }

    public boolean contains(T element) {
        return contains(root, element);
    }

    //Recursive method to find an element in the tree
    private boolean contains(Node<T> node, T element) {
        //Base case: reached bottom, value not found
        if (node == null) return false;
        //Get comparison result
        int cmp = element.compareTo(node.data);
        //Dig int the left subtree because the value we're
        //looking for is smaller than the current value
        if (cmp < 0) return contains(node.left, element);
            //Dig into the right subtree because the value we're
            //looking for is greater than the current value
        else if (cmp > 0) return contains(node.right, element);
            //We found the value we were looking for
        else return true;
    }

    //Computes the height of the tree, O(n)
    public int height() {
        return height(root);
    }

    //Recursive helper method to compute the height of the tree
    private int height(Node<T> node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

}
