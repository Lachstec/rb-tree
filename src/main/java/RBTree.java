import java.util.ArrayDeque;

/**
 * RBTree represents a Red-Black-Tree, a binary search tree that allows
 * fast access to stored elements. This is accomplished by following five rules:
 * 1. Every Node is either red or black
 * 2. The root of the tree is black
 * 3. All null-leafs are black
 * 4. A red node must not have red children
 * 5. All paths from a node to a leaf contain the same amount of black nodes
 */
public class RBTree<T extends Comparable<T>> {

    private Node root;

    public void insert(T value) {
        Node node = root; Node parent = null;
        // Traverse the tree to the left or right depending on the key
        while (node != null) {
            parent = node;
            if (value.compareTo(node.data) < 0) {
                node = node.left;
            } else if (value.compareTo(node.data) > 0) {
                node = node.right;
            } else {
                throw new IllegalArgumentException("BST already contains a node with key " + value);
            }
        }
        // Insert new node
        Node newNode = new Node(value);
        newNode.color = true;
        if (parent == null) {
            root = newNode;
        } else if (value.compareTo(parent.data) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        newNode.parent = parent;
        fixRedBlackPropertiesAfterInsert(newNode);
    }

    private void fixRedBlackPropertiesAfterInsert(Node node) {
        // go up to parent
        Node parent = node.parent;
        // Check for Rule 1 and 2
        if(parent == null) {
            node.color = false;
            return;
        }
        // if parent is black, everything is okay
        if(!parent.color) {
            return;
        }

        // cases from here are with a red parent
        Node grandparent = parent.parent;


        Node uncle = getUncle(parent);
        // Case 3: Both Parent and Uncle are red
        if(uncle != null && uncle.color == true) {
            parent.color = false;
            uncle.color = false;
            grandparent.color = true;

            fixRedBlackPropertiesAfterInsert(parent.parent);
        } else if (parent == grandparent.right) {
            // Case 4: Parent red and right child of grandparent, inner child
            if(node == parent.left) {
                // step 1: rotate the subtree of parent to the right
                rotateRight(parent);
                // update parent because we have rotated the subtree
                parent = node;
            }
            // step 2: rotate the subtree of grandparent to the left
            rotateLeft(grandparent);

            parent.color = false;
            grandparent.color = true;
        } else {
            if(node == parent.right) {
                rotateLeft(parent);

                parent = node;
            }
            rotateRight(grandparent);

            parent.color = false;
            grandparent.color = true;
        }
    }

    public String toDotFile() {
        String header = "digraph G {\ngraph [ratio=.48];\nnode [style=filled, color=black, shape=circle, width=.6, fontname=Helvetica, fontweight=bold, fontcolor=white, fontsize=24, fixedsize=true];\n";
        StringBuilder dotfile = new StringBuilder(header);
        StringBuilder nilValues = new StringBuilder();
        StringBuilder connections = new StringBuilder();
        StringBuilder redNodes = new StringBuilder();

        Node node;
        int nilCounter = 1;
        ArrayDeque<Node> queue = new ArrayDeque<>();
        if(this.root != null) {
            queue.offer(root);
        }
        while(!queue.isEmpty()) {
            node = queue.getFirst();
            queue.pop();
            if(node.left != null) {
                connections.append(String.format("%s -> %s;\n", node.data, node.left.data));
                if(node.color == true) {
                    redNodes.append(String.format("%s,", node.left.data));
                }
                queue.offer(node.left);
            } else {
                String name = String.format("n%d", nilCounter);
                nilValues.append(name).append(",");
                connections.append(String.format("%s -> %s;\n", node.data, name));
                nilCounter += 1;
            }
            if(node.right != null) {
                connections.append(String.format("%s -> %s;\n", node.data, node.right.data));
                if(node.color == true) {
                    redNodes.append(String.format("%s,", node.right.data));
                }
                queue.offer(node.right);
            } else {
                String name = String.format("n%d", nilCounter);
                nilValues.append(name).append(",");
                connections.append(String.format("%s -> %s;\n", node.data, name));
                nilCounter += 1;
            }
        }
        redNodes.deleteCharAt(redNodes.length() - 1);
        redNodes.append("\n[fillcolor=red];\n");
        nilValues.deleteCharAt(nilValues.length() - 1);
        nilValues.append("\n[label=\"NIL\", shape=record, width=.4,height=.25, fontsize=16];\n");
        dotfile.append(redNodes);
        dotfile.append(nilValues);
        dotfile.append(connections);
        dotfile.append("\n}");
        return dotfile.toString();
    }

    private Node getUncle(Node parent) {
        Node grandparent = parent.parent;
        if(grandparent.left == parent) {
            return grandparent.right;
        } else if(grandparent.right == parent){
            return grandparent.left;
        }
        return null;
    }

    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.right = node;
        node.parent = leftChild;
        replaceParentsChild(parent, node, leftChild);
    }

    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.left = node;
        node.parent = rightChild;
        replaceParentsChild(parent, node, rightChild);
    }

    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }
        if (newChild != null) {
            newChild.parent = parent;
        }
    }

    private class Node {
        T data;
        Node left;
        Node right;
        Node parent;
        /**
         * Represents the color of the Node in a RB-Tree.
         * true = red
         * false = black
         */
        boolean color;

        public Node(T data) {
            this.data = data;
            this.color = true;
        }
    }
}
