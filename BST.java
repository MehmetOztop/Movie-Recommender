import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Binary Search Tree BST
 * @author uzaycetin
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root; // root of BST
    // Inner class
    private ArrayList<String> Movies= new ArrayList<String>();
    private ArrayList<Integer> Users= new ArrayList<Integer>();
    private class Node {
        private Key key; // key
        private Value val; // associated value
        private Node left, right; // links to subtrees
        
        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
        public String toString(){return " ("+key+ ":"+ val + ") ";}
    }
    ////////////////////////////////////////////////////////////////////////////
    
    public String getRoot(){
        return root.toString();
    }

    
    
    ///////////////////////////////////////////////////////////////////////////
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\// 
    /**
     * deleteMin().
     * we go left until finding a Node
     * that has a null left link and then replace the link to that node by
     * its right link (simply by returning the right link in the recursive
     * method). 
     */
    public void deleteMin(){root = deleteMin(root);}
    private Node deleteMin(Node x){
        if (x.left == null) return x.right; // min is the node without left child
        x.left = deleteMin(x.left); // advance untill min is found
        return x;
    }
    
    public void delete(Key key){ root = delete(root, key); }
    private Node delete(Node x, Key key) {
        // node to be deleted can not found
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            // if less go left
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            // if greater go right
            x.right = delete(x.right, key);
        } else {
            // x is the node to be deleted
            // easy case zero or one child
            if (x.right == null) {// maybe one child at left, return it or null
                return x.left;
            }
            if (x.left == null) {// maybe one child at right, return it or null
                return x.right;
            }
            // hard case two childs
            Node t = x; // t and x are the nodes to be deleted
            x = min(t.right); // x is min at the right subtree
            x.right = deleteMin(t.right); 
            // x.right becomes a new subtree after removal of its min
            x.left = t.left; // nobody is pointing to t
        }
        return x;
    }

    
    
    ////////////////////////////////////////////////////////////////////////////
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//
    // Recursive Show: in order traversal.
    public void show(){
        show(root);
        System.out.println();
    }
    public void show(Node x){
        if(x == null) return;
        show(x.left);
        System.out.print(" ("+x.key+ ":"+ x.val + ") ");
        show(x.right);
    }
    public ArrayList<String> getMovies() {//returns all movies at the BST
    	 getMovies(root);
    	 Movies=new ArrayList<String>(new LinkedHashSet<>(Movies));//removes duplicated items
    	 return Movies;
    }
    public void getMovies(Node x) {//this is inspired by the show() method
    	if(x==null) return;
        getMovies(x.left);
    	Movies.add((String)x.key);
    	getMovies(x.right);
        
    }
    
    public ArrayList<Integer> getUsers() {//returns all users at the BST
   	 getUsers(root);
   	 Users = new ArrayList<Integer>(new LinkedHashSet<>(Users));//removes duplicated items
   	 return Users;
   }
   public void getUsers(Node x) {//this is inspired by the show() method
   	if(x==null) return;
       getUsers(x.left);
   	   Users.add((Integer)x.key);
   	   getUsers(x.right);
       
   }
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//     
    public Node min(){
        return min(root);
    }
    public Node min(Node x){
        if(x.left == null) return x;
        return min(x.left);
    }
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//
    ////////////////////////////////////////////////////////////////////////////
      
    ///////////////////////////////////////////////////////////////////////////
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\// 
    public String max(){
        return max(root);
    }
    public String max(Node x){
        if(x.right== null) return " ("+x.key+ ":"+ x.val + ") ";
        return max(x.right);
    }
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//
    ////////////////////////////////////////////////////////////////////////////
   
    
    
    ////////////////////////////////////////////////////////////////////////////
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//
    // public search calls for private search
    public Value get(Key key) {
        // search inside the tree from the root. 
        // Note that: (private) root is invisible outside the tree
        return get(root, key);
    }
    /**
     * Private Search: Recursive Search Algorithm
     * Return value associated with key in the subtree rooted at x
     * @param x : root node of the subtree
     * @param key : search key
     * @return : value if key is found, null otherwise
     */
    private Value get(Node x, Key key) { 
        // return null if subtree is empty 
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        // search recursively in the appropriate subtree
        // if less, go left
        if (cmp < 0) {
            return get(x.left, key); // move left subtree, if key is smaller
        } 
        // if greater, go right
        else if (cmp > 0) {
            return get(x.right, key); // move right subtree, if key is smaller
        } 
        // if found, return value
        else {
            return x.val; // return value if found at the root of the subtree
        }
    }
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//
    ////////////////////////////////////////////////////////////////////////////
    
    
    

    
    ////////////////////////////////////////////////////////////////////////////
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//
    // public insert call
    public void put(Key key, Value val) { 
        root = put(root, key, val);
    }
    /**
     * Public insert call:
     * Recursive logic: 
     *  if empty, return new node to the call
     *  ow compare with node x, update x's left or right link
     * @param x : root node of the subtree
     * @param key : key-value pair to be inserted
     * @param val : key-value pair to be inserted
     * @return 
     */
    private Node put(Node x, Key key, Value val) {
        // Insertion occurs only at the bottom, return new node  
        if (x == null) {
            return new Node(key, val); // node count N is 1
        }
        // compare search key against the root node x
        int cmp = key.compareTo(x.key);
        // update link from parent x to its child
        if (cmp < 0) {
            // set left link, to the resulting left subtree after insertion
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            // set right link, to the resulting right subtree after insertion
            x.right = put(x.right, key, val);
        } else {
            // update already-existing key with new value
            x.val = val;
        }
        return x;
    }
    //\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//\\////\\//
    ////////////////////////////////////////////////////////////////////////////
}