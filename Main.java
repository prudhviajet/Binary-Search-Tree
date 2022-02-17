import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.List;
import java.io.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.effect.*;
import javax.swing.JOptionPane;

class BTview extends Pane {
    
    private BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    private double radius = 25; // tree node Radius
    private double vGap = 49; // gap between two levels 
    
    BTview(BinarySearchTree<Integer> tree) {
        this.tree = tree;
        setstatus("The Tree is empty");
    }

    public void setstatus(String message) {
        getChildren().add(new Text(10, 120, message));
    }

    public void displayTree() {
        this.getChildren().clear();
        if (tree.getRoot() != null) {
            displayTree(tree.getRoot(), getWidth() / 2, vGap, getWidth() / 5);
        }
    }

    private void displayTree (TreeNode <Integer> root, double x, double y, double hGap) {
        
        if (root.left != null) {
            // Draw line to left node
            getChildren().add(new Line(x - hGap, y + vGap, x, y));
            // Draw left subtree 
            displayTree(root.left, x - hGap, y + vGap, hGap / 2);
        }

        if (root.right != null) {
            // Draw line to right node
            getChildren().add(new Line(x + hGap, y + vGap, x, y));
            // Draw right subtree 
            displayTree(root.right, x + hGap, y + vGap, hGap / 2);
        }

        // Display a node
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.AQUA);
        circle.setStroke(Color.YELLOW);
        getChildren().addAll(circle, new Text(x - 7, y + 4, root.element + ""));
    }
    
}

public class Main extends Application {
    @Override
    public void start(Stage primaryStage)  {
   
        BinarySearchTree <Integer> tree = new BinarySearchTree <>(); //create the tree
        BorderPane pane1 = new BorderPane();
        pane1.setStyle("-fx-background-color:lightgreen");
        BTview view = new BTview(tree); //create the view
        pane1.setCenter(view);
        try{
            FileInputStream is = new FileInputStream("d:\\logo.jpg");
            Image im = new Image(is);

            ImageView iv = new ImageView(im);
            iv.setFitHeight(100);
            iv.setFitWidth(100);
            pane1.getChildren().add(iv);
        } catch(Exception e){System.out.println(e);}

        TextField textKey = new TextField();
        textKey.setPrefColumnCount(3);
        textKey.setAlignment(Pos.BASELINE_RIGHT);
        Button btInsert = new Button("Insert");
        Button btDelete = new Button("Delete");
        Button btSearch = new Button("Search");
        Button btInOrder = new Button("InOrder");
        Button btHeight = new Button("Height");
        Button btNumber = new Button("No.Of vertices");
        Button btPostOrder = new Button("PostOrder");
        Button btPreOrder = new Button("PreOrder");
        HBox hbox1 = new HBox(5);
        hbox1.getChildren().addAll(new Label("Enter an integer : "), textKey, btInsert, btDelete, btSearch, btInOrder, btPostOrder, btPreOrder, btHeight, btNumber);
        hbox1.setAlignment(Pos.CENTER);
        pane1.setBottom(hbox1);
        

        btInsert.setOnAction(e -> {
            int fix = Integer.parseInt(textKey.getText());
            if (tree.scr(fix)) { // if fix is in the tree already
                view.displayTree();
                view.setstatus(fix + " is already in the tree");
                JOptionPane.showMessageDialog(null, fix + " is already in the tree");
            } else {
                tree.enter(fix); // Insert a new fix in to the tree
                view.displayTree();
                view.setstatus(fix + " is inserted in the tree");
            }
        });

        btDelete.setOnAction(e -> {
            int lop = Integer.parseInt(textKey.getText());
            if (!tree.scr(lop)) { // if lop is not in the tree
                view.displayTree();
                view.setstatus(lop + " is not in the tree");
            } else {
                tree.del(lop); // Delete the lop from the tree
                view.displayTree();
                view.setstatus(lop + " is deleted from the tree");
            }
        });
        
        btSearch.setOnAction(e -> {
           int sar = Integer.parseInt(textKey.getText());
           if(!tree.scr(sar)){ // if sar is found in the tree 
               //view.displayTree();
               //view.setstatus(key + " is not in the tree");
               JOptionPane.showMessageDialog(null, sar + " is not found in the tree");
           } else { // if sar is not found in the tree
               //view.displayTree();
               //view.setstatus(key + " is in the tree");
               JOptionPane.showMessageDialog(null, sar + " is found in the tree");
           }
        });
        
        btInOrder.setOnAction(e -> {
           StringBuffer inorderstring = new StringBuffer(); // to show the in order traversal of the tree
           tree.inorder(tree.root,inorderstring);
           //view.displayTree();
           //view.setstatus("inorder traversal: "+inorderstring);
           JOptionPane.showMessageDialog(null, "inorder traversal of the tree : " + inorderstring);
        });
        
        btHeight.setOnAction(e -> {
            int height;
            height = tree.height(tree.root);// to show the height of the tree 
            //view.displayTree();
            //view.setstatus("Height : " + height);
            JOptionPane.showMessageDialog(null, "Height of the Tree is : " + height);
        });
        
        btNumber.setOnAction(e -> {
            StringBuffer inorderstring = new StringBuffer(); // to show the number of vertices in the tree
            tree.inorder(tree.root,inorderstring);
            String[] number  ;
            number = inorderstring.toString().split(" ") ;
            //view.displayTree();
            //view.setstatus("number of vertices : " + number.length);
            JOptionPane.showMessageDialog(null, "Number of Vertices in the Tree are : " + number.length );
        });
        
        btPostOrder.setOnAction(e -> {
           StringBuffer postorderstring = new StringBuffer(); // to show the post order traversal of the tree
           tree.postorder(tree.root,postorderstring);
           //view.displayTree();
           //view.setstatus("postorder traversal : " + postorderstring);
           JOptionPane.showMessageDialog(null, "postorder traversal of the tree : " + postorderstring);
        });
        
        btPreOrder.setOnAction(e -> {
           StringBuffer preorderstring = new StringBuffer(); // to show the pre order traversal of the tree
           tree.preorder(tree.root,preorderstring);
           //view.displayTree();
           //view.setstatus("preorder traversal : " + preorderstring);
           JOptionPane.showMessageDialog(null, "preorder traversal of the tree : " + preorderstring);
        });

        // Create a scene and place the pane in the stage
        Scene scene1 = new Scene(pane1, 1000, 700);
        primaryStage.setTitle("Binary Search Tree Animation");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class TreeNode<E> {
    public E element;
    public TreeNode<E> left;
    public TreeNode<E> right;

    public TreeNode(E a) {
        element = a;
    }
}

class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {
    protected TreeNode<E> root;
    protected int size = 0;

    public BinarySearchTree() {}

    public BinarySearchTree(E[] particles) {
        for (int i = 0; i < particles.length; i++){
            add(particles[i]);
        }    
    }

    @Override
    public boolean scr(E a) {
        TreeNode<E> component = root; 
        while (component != null) {
            if (a.compareTo(component.element) < 0) {
                component = component.left;
            } else if (a.compareTo(component.element) > 0) {
                component = component.right;
            } else // number matches component.number
                return true; // number is found
        }
        return false;
    }

    @Override
    public boolean enter(E a) {
        if (root == null){
            root = creaNewnode(a); 
        } else {
            TreeNode<E> parent = null;
            TreeNode<E> component = root;
            while (component != null)
                if (a.compareTo(component.element) < 0) {
                    parent = component;
                    component = component.left;
                } else if (a.compareTo(component.element) > 0) {
                    parent = component;
                    component = component.right;
                } else
                    return false; // duplicate node is not inserted

                if (a.compareTo(parent.element) < 0){
                    parent.left = creaNewnode(a);
                } else{
                    parent.right = creaNewnode(a);
                }
        }
        size++;
        return true; // number is inserted successfully
    }

    protected TreeNode<E> creaNewnode(E a) {
        return new TreeNode<>(a);
    }

     // In order traversal 
    public void inorder(TreeNode<E> root,StringBuffer s) {
        if (root == null){
            return;
        }    
        inorder(root.left, s);
        s.append(root.element + " ");
        inorder(root.right, s);
    }
    
    // Post order traversal 
    public void postorder(TreeNode<E> root,StringBuffer s) {
        if (root == null){
            return;
        }    
        postorder(root.left, s);
        postorder(root.right, s);
        s.append(root.element + " ");
    }
    
    // Pre order traversal 
    public void preorder(TreeNode<E> root,StringBuffer s) {
        if (root == null){
            return;
        }   
        s.append(root.element + " ");
        postorder(root.left, s);
        postorder(root.right, s);
    }
    
    int height(TreeNode<E> root){
        if(root == null){
            return 0;
        }
        int leftheight = height(root.left);
        int rightheight = height(root.right);
        if(leftheight > rightheight){
            return leftheight + 1;
        }else{
            return rightheight + 1;
        }
    }

    @Override 
    public int getSize() {
        return size;
    }

    public TreeNode<E> getRoot() {
        return root;
    }

    public List<TreeNode<E>> path(E a) {
        List<TreeNode<E>> list = new java.util.ArrayList<>();
        TreeNode<E> component = root; 
        while (component != null) {
            list.add(component); 
            if (a.compareTo(component.element) < 0) {
                component = component.left;
            } else if (a.compareTo(component.element) > 0) {
                component = component.right;
            } else
                break;
        }
         return list;
    }

    @Override
    public boolean del(E a) {
        TreeNode<E> parent = null;
        TreeNode<E> component = root;
        while (component != null) {
            if (a.compareTo(component.element) < 0) {
                parent = component;
                component = component.left;
            } else if (a.compareTo(component.element) > 0) {
                parent = component;
                component = component.right;
            } else
                break;
        }
        if (component == null)
            return false; // number is not in the tree

        // Type 1: current has no left child
        if (component.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = component.right;
            } else {
                if (a.compareTo(parent.element) < 0)
                    parent.left = component.right;
                else
                    parent.right = component.right;
            }
        } else {
            // Type 2: The current node has a left child 
            TreeNode<E> parentOfRightMost = component;
            TreeNode<E> rightMost = component.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; 
            }

            // Replace the number in component by the number in rightmost node
            component.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost){
                parentOfRightMost.right = rightMost.left;
            }    
            else{
                parentOfRightMost.left = rightMost.left;
            }   
        }

        size--;
        return true; // number is deleted successfully
    }

    @Override  
    public java.util.Iterator<E> iterator() {
        return new InorderIterator();
    }

    private class InorderIterator implements java.util.Iterator<E> {
        // Store the elements in a list
        private java.util.ArrayList<E> list = new java.util.ArrayList<>();
        private int current = 0; // Point to the current element in list

        public InorderIterator() {
            inorder(); 
        }

        private void inorder() {
            inorder(root);
        }

        private void inorder(TreeNode<E> root) {
            if (root == null)
                return;
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        }

        @Override 
        public boolean hasNext() {
            if (current < list.size())
                return true;

            return false;
        }

        @Override // Get the current number and move to the next 
        public E next() {
            return list.get(current++);
        }

        @Override // Remove the number returned by the last next()
        public void remove() {
            if (current == 0) 
                throw new IllegalStateException();

            del(list.get(--current));
            list.clear(); // Clear the list
            inorder(); // Rebuild the list
        }
    }

    @Override // clears all elements from the tree 
    public void clear() {
        root = null;
        size = 0;
    }
}

interface Tree<E> extends Collection<E> {
    // Return true if the element is in the tree 
    public boolean scr(E a);

    // Insert element a into the binary tree, Return true if the element is inserted
    public boolean enter(E a);

    // Delete the specified element from the tree, Return true if the element is deleted     
    public boolean del(E a);

    // Get the number of elements in the tree 
    public int getSize();

    // In order traversal 
    public default void inorder() {
    }

    // Post order traversal
    public default void postorder() {
    }

    // Pre order traversal 
    public default void preorder() {
    }

    @Override // return true if the tree is empty 
    public default boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public default boolean contains(Object a) {
        return scr((E) a);
    }

    @Override
    public default boolean add(E a) {
        return enter(a);
    }

    @Override
    public default boolean remove(Object a) {
        return del((E) a);
    }

    @Override
    public default int size() {
        return getSize();
    }
    
    @Override
    public default boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public default boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public default Object[] toArray() {
        return null;
    }

    @Override
    public default <T> T[] toArray(T[] array) {
        return null;
    }
}