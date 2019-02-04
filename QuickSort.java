import java.util.ArrayList;

/**
 *
 * @author uzaycetin
 */
public class QuickSort {
    private static boolean less(Comparable v, Comparable w) {return v.compareTo(w) < 0;}
    private static void exch(Comparable[] a, int i, int j) {Comparable t = a[i];a[i] = a[j];a[j] = t;}
    
    
    
    public static void shuffle(Comparable [] a){
        if (a == null) return;
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = (int)(Math.random() * (n - i) + i);// between i and n-1
            Comparable temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    public static void show(Comparable[] a) {
        if (a == null) return;
        for (int i = 0; i < a.length; i++) {System.out.print(a[i] + " ");}
        System.out.println();
    }
    
    
    public static void sort(Comparable[] a){
        shuffle(a); // Eliminate dependence on input.
        sort(a, 0, a.length - 1);
    }
    private static void sort(Comparable[] a, int lo, int hi){
        // Improvement for tiny arrays: Cutoff to insertion sort.
        if (hi <= lo) return; //if (hi <= lo + Cutoff) { Insertion.sort(a, lo, hi); return; }
        int j = partition(a, lo, hi); // Partition 
        sort(a, lo, j-1); // Sort left part a[lo .. j-1].
        sort(a, j+1, hi); // Sort right part a[j+1 .. hi].
    }
    private static int partition(Comparable[] a, int lo, int hi){ // Partition into a[lo..i-1], a[i], a[i+1..hi].
        int i = lo, j = hi + 1; // left and right scan indices
        Comparable v = a[lo]; // partitioning item
        while (true) { 
            // Scan left: until greater entry is found
            // increment i while a[i] is less than v 
            while (less(a[++i], v)) if (i == hi) break;
            
            // Scan right: until smaller entry is found
            // decrement j while a[j] is greater than v,
            while (less(v, a[--j])) if (j == lo) break;
            
            // main loop exits when the scan indices i and j cross
            if (i >= j) break;// Note that the #compares is order N
            
            // exchange the two misplaced entries
            exch(a, i, j);
        }
        exch(a, lo, j); // Put v = a[j] into position
        return j; // with a[lo..j-1] <= a[j] <= a[j+1..hi].
    }

}
