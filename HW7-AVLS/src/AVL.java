import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Shresht Yadav
 * @userid syadav73
 * @GTID 903688946
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot make a tree with null data");
        }
        for (T node: data) {
            add(node);
        }
    }

    /**
     * Right rotation method.
     * @param node the node that must be rotated.
     * @return the new parent node.
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) { //node = c
        AVLNode<T> b = node.getLeft(); //B
        node.setLeft(b.getRight()); //Cs left child
        b.setRight(node); // check for null
        heightBFUpdate(node);
        heightBFUpdate(b);
        return b;
    }

    /**
     * left rotation method.
     * @param node the node that must be rotated.
     * @return the new parent node.
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> b = node.getRight();
        node.setRight(b.getLeft());
        b.setLeft(node);
        heightBFUpdate(node);
        heightBFUpdate(b);
        return b;
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        root = addHelper(root, data);
    }

    /**
     * recursive helper for add method.
     * @param curr node the program is currently on.
     * @param data data that must be added.
     * @return the new parent node after appropriate rotations.
     */
    private AVLNode<T> addHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        return balanceHelper(curr);
    }

    /**
     * Method that balances the AVL.
     * @param node the node that is unbalanced.
     * @return new parent node.
     */
    private AVLNode<T> balanceHelper(AVLNode<T> node) {
        heightBFUpdate(node);
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(leftRotation(node.getLeft()));
            }
            node = rightRotation(node);
        } else if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rightRotation(node.getRight()));
            }
            node = leftRotation(node);
        }
        return node;
    }

    /**
     * updates heights and balance factors for a node.
     * @param node node that needs updated height/BF.
     */
    private void heightBFUpdate(AVLNode<T> node) {
        int leftNode;
        int rightNode;
        if (node.getLeft() == null) {
            leftNode = -1;
        } else {
            leftNode = node.getLeft().getHeight();
        }
        if (node.getRight() == null) {
            rightNode = -1;
        } else {
            rightNode = node.getRight().getHeight();
        }
        node.setHeight(1 + Math.max(leftNode, rightNode));
        node.setBalanceFactor(leftNode - rightNode);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        AVLNode<T> dummy = new AVLNode<T>(null);
        root = removeHelper(root, data, dummy);
        size--;
        return dummy.getData();
    }

    /**
     * recursive method for remove.
     * @param curr current node the program is on.
     * @param data data that needs to be removed.
     * @param dummy dummy node for the purpose of proper grabbing of successor node.
     * @return node that has been removed.
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the BST!");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            if (curr.getLeft() != null && curr.getRight() != null) {
                AVLNode<T> dummy2 = new AVLNode<T>(data);
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
        return balanceHelper(curr);
    }

    /**
     * removes the successor node for the given node.
     * @param curr current node the program is on.
     * @param dummy2 parent node that will be returned.
     * @return the successor node.
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> dummy2) {
        if (curr.getLeft() == null) {
            dummy2.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy2));
        }
        return balanceHelper(curr);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data is null!");
        }
        AVLNode<T> output = getHelper(root, data);
        return output.getData();
    }

    /**
     * Helper method that looks for a specific node in the AVL.
     * @param curr AVLNode representing the node the program is currently on.
     * @param data the data being searched for.
     * @return the node being searched for.
     */
    private AVLNode<T> getHelper(AVLNode<T> curr, T data) {
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
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null!");
        }
        return containsHelper(root, data);
    }

    /**
     * Helper method that looks for a specific node in the AVL, and returns true if it is.
     * @param curr AVLNode representing the node the program is currently on.
     * @param data the data being searched for.
     * @return the node being searched for.
     */
    private boolean containsHelper(AVLNode<T> curr, T data) {
        if (curr != null) {
            if (curr.getData().compareTo(data) == 0) {
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
     * The predecessor is the largest node that is smaller than the current data.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null!");
        }
        AVLNode<T> output = new AVLNode<>(data);
        predecessorHelper(root, data, output);
        if (output.getData().compareTo(data) == 0) {
            return null;
        } else {
            return output.getData();
        }
    }

    /**
     * recursive helper method for predecessor method.
     * @param curr current node the program is on.
     * @param data data that needs to be evaluated for predecessor.
     * @param closestData the predecessor node to data.
     * @return the predecessor node.
     */
    private AVLNode<T> predecessorHelper(AVLNode<T> curr, T data, AVLNode<T> closestData) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the AVL!");
        } else if (data.compareTo(curr.getData()) < 0) {
            if (data.compareTo(curr.getData()) > 0) {
                closestData.setData(curr.getData());
            }
            predecessorHelper(curr.getLeft(), data, closestData);
        } else if (data.compareTo(curr.getData()) > 0) {
            if (data.compareTo(curr.getData()) > 0) {
                closestData.setData(curr.getData());
            }
            predecessorHelper(curr.getRight(), data, closestData);
        } else {
            if (curr.getLeft() != null) {
                closestData = getPredecessor(curr.getLeft(), closestData);
                return closestData;
            } else {
                return closestData;
            }
        }
        return closestData;
    }

    /**
     * grabs the predecessor node if left tree is not null.
     * @param curr parent of subtree.
     * @param predecessor predecessor node.
     * @return the predecessor node.
     */
    private AVLNode<T> getPredecessor(AVLNode<T> curr, AVLNode<T> predecessor) {
        if (curr.getRight() == null) {
            predecessor.setData(curr.getData());
            return predecessor;
        } else {
            getPredecessor(curr.getRight(), predecessor);
        }
        return predecessor;
    }


    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("k is not valid!");
        }
        ArrayList<T> output = new ArrayList<T>();
        kSmallestHelper(root, output, k);
        return output;
    }

    /**
     * recursive helper method for grabbing the smallest k elements.
     * @param curr node the program is currently on.
     * @param output list of the k smallest nodes.
     * @param k amount of nodes desired in the output.
     * @return list of output nodes.
     */
    private List<T> kSmallestHelper(AVLNode<T> curr, ArrayList<T> output, int k) {
        if (curr != null) {
            if (output.size() < k) {
                kSmallestHelper(curr.getLeft(), output, k);
            }
            if (output.size() < k) {
                output.add(curr.getData());
            }
            if (output.size() < k) {
                kSmallestHelper(curr.getRight(), output, k);
            }
        }
        return output;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}