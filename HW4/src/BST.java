import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 * @userid YOUR USER ID HERE (i.e. gburdell3)
 * @GTID YOUR GT ID HERE (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot make a tree with null data");
        }
        for (T node: data) {
            add(node);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        root = addHelper(root, data);
    }

    /**
     * Recursively finds the next location for a leaf to be added
     * @param curr BSTNode representing where the program is in the tree.
     * @param data Data that needs to be added to the tree.
     * @return BSTNode that is to be added to the tree.
     */
    private BSTNode<T> addHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        BSTNode<T> dummy = new BSTNode<T>(data);
        root = removeHelper(root, data, dummy);
        size--;
        return dummy.getData();
    }

    /**
     * Method that does the recursion and removes the node from the BST.
     * @param curr BSTNode representing the node the program is currently on.
     * @param data T representing the data that needs to be removed.
     * @param dummy BSTNode placeholder that temporarily stores the data that needs to be removed.
     * @return BSTNode dummy that holds the data that needs to be removed from the BST.
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the BST!");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummy));
        } else {
            //dummy.setData(curr.getData());
            if (curr.getLeft() != null && curr.getRight() != null) {
                BSTNode<T> dummy2 = new BSTNode<T>(data);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            } else if (curr.getLeft() != null) {
                return curr.getLeft();
            } else if (curr.getRight() != null) {
                return curr.getRight();
            } else {
                return null;
            }
        }
        return curr;
    }

    /**
     * Helper method that removes the BSTNode with successor node.
     * @param curr BSTNode representing the node the program is currently on.
     * @param dummy2 BSTNode represnting the node that is to be removed.
     * @return the node that was removed.
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy2) {
        if (curr.getLeft() == null) {
            dummy2.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy2));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data is null!");
        }
        BSTNode<T> output = getHelper(root, data);
        return output.getData();
    }

    /**
     * Helper method that looks for a specific node in the BST.
     * @param curr BSTNode representing the node the program is currently on.
     * @param data the data being searched for.
     * @return the node being searched for.
     */
    private BSTNode<T> getHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the BST!");
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelper(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(curr.getRight(), data);
        } else {
            return curr;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null!");
        }
        return containsHelper(root, data);
    }

    /**
     * Helper method that looks for a specific node in the BST, and returns true if it is.
     * @param curr BSTNode representing the node the program is currently on.
     * @param data the data being searched for.
     * @return the node being searched for.
     */
    private boolean containsHelper(BSTNode<T> curr, T data) {
        if (curr != null) {
            if (curr.getData() == data) {
                return true;
            } else if (data.compareTo(curr.getData()) < 0) {
                return containsHelper(curr.getLeft(), data);
            } else if (data.compareTo(curr.getData()) > 0) {
                return containsHelper(curr.getRight(), data);
            }
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> output = new ArrayList<T>();
        output = preOrderHelper(root, output);
        return output;
    }

    /**
     * Helper method that adds the data to the output list.
     * @param curr node the program is currently on.
     * @param output ArrayList representing the BST.
     * @return ArrayList representing the BST.
     */
    private ArrayList<T> preOrderHelper(BSTNode<T> curr, ArrayList<T> output) {
        if (curr != null) {
            output.add(curr.getData());
            preOrderHelper(curr.getLeft(), output);
            preOrderHelper(curr.getRight(), output);
        }
        return output;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> output = new ArrayList<T>();
        return inOrderHelper(root, output);
    }

    /**
     * Helper method that adds the data to the output list.
     * @param curr node the program is currently on.
     * @param output ArrayList representing the BST.
     * @return ArrayList representing the BST.
     */
    private List<T> inOrderHelper(BSTNode<T> curr, ArrayList<T> output) {
        if (curr != null) {
            inOrderHelper(curr.getLeft(), output);
            output.add(curr.getData());
            inOrderHelper(curr.getRight(), output);
        }
        return output;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> output = new ArrayList<T>();
        output = postOrderHelper(root, output);
        return output;
    }

    /**
     * Helper method that adds the data to the output list.
     * @param curr node the program is currently on.
     * @param output ArrayList representing the BST.
     * @return ArrayList representing the BST.
     */
    private ArrayList<T> postOrderHelper(BSTNode<T> curr, ArrayList<T> output) {
        if (curr != null) {
            postOrderHelper(curr.getLeft(), output);
            postOrderHelper(curr.getRight(), output);
            output.add(curr.getData());
        }
        return output;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> output = new ArrayList<T>();
        LinkedList<BSTNode<T>> q = new LinkedList<BSTNode<T>>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.removeFirst();
            output.add(curr.getData());
            if (curr.getLeft() != null) {
                q.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                q.add(curr.getRight());
            }
        }
        return output;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return heightHelper(root);
    }

    /**
     * Method to find the height of the root, but adding one to the higher child height.
     * @param curr BST node the program is currently on.
     * @return int representing the height of the root.
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return Math.max(heightHelper(curr.getRight()),
                    heightHelper(curr.getLeft())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("One of the data values are null!");
        } else if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException("data entered is not in the list!");
        } else {
            List<T> output = new ArrayList<>();
            BSTNode<T> commonAncestor = new BSTNode<T>(null);
            if (data1.compareTo(data2) > 0) {
                commonAncestor = findCommonAncestor(data2, data1, root, commonAncestor);
            } else {
                commonAncestor = findCommonAncestor(data1, data2, root, commonAncestor);
            }
            buildPath(commonAncestor, output, data1, 0, commonAncestor);
            buildPath(commonAncestor, output, data2, -1, commonAncestor);
            return output;
        }
    }

    /**
     * finds the common ancestor node.
     * @param lowerValue lower input node.
     * @param higherValue higher input node.
     * @param curr Node the program is on.
     * @param commonAncestor node shared by data1 and data2.
     * @return the common ancestor node between data1 and data2
     */
    private BSTNode<T> findCommonAncestor(T lowerValue, T higherValue, BSTNode<T> curr, BSTNode<T> commonAncestor) {
        if (curr.getData().compareTo(higherValue) <= 0 && curr.getData().compareTo(lowerValue) >= 0) {
            commonAncestor.setLeft(curr.getLeft());
            commonAncestor.setRight(curr.getRight());
            commonAncestor.setData(curr.getData());
            return commonAncestor;
        } else if (curr.getData().compareTo(higherValue) > 0) {
            findCommonAncestor(lowerValue, higherValue, curr.getLeft(), commonAncestor);
        } else if (curr.getData().compareTo(lowerValue) < 0) {
            findCommonAncestor(lowerValue, higherValue, curr.getRight(), commonAncestor);
        } else if (curr.getData() == lowerValue || curr.getData() == higherValue) {
            commonAncestor.setLeft(curr.getLeft());
            commonAncestor.setRight(curr.getRight());
            commonAncestor.setData(curr.getData());
            return commonAncestor;
        }
        return commonAncestor;
    }

    /**
     * Creates half of the path list by finding the lower Node.
     * @param curr Node the program is on.
     * @param output ArrayList that represents the path.
     * @param node Node whose path is being recorded in output.
     * @param index int representing whether or not values should be added to be beginning or end of the output.
     * @param ancestor node shared by data1 and data2.
     * @return path ArrayList with half of the path.
     */
    private List<T> buildPath(BSTNode<T> curr, List<T> output, T node, int index, BSTNode<T> ancestor) {
        if (index == -1) {
            if (curr.getData() != ancestor.getData()) {
                output.add(curr.getData());
            }
            if (node.compareTo(curr.getData()) > 0) {
                buildPath(curr.getRight(), output, node, index, ancestor);
            } else if (node.compareTo(curr.getData()) < 0) {
                buildPath(curr.getLeft(), output, node, index, ancestor);
            }
        } else if (index == 0) {
            output.add(0, curr.getData());
            if (node.compareTo(curr.getData()) > 0) {
                buildPath(curr.getRight(), output, node, index, ancestor);
            } else if (node.compareTo(curr.getData()) < 0) {
                buildPath(curr.getLeft(), output, node, index, ancestor);
            }
        }
        return output;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
