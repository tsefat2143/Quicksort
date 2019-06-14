import java.util.*;

public class HW3 {
   static Random rand = new Random();

   // Swaps the elements at indices i and j.
   private static void swap(int[] a, int i, int j) {
      int temp = a[i];
      a[i] = a[j];
      a[j] = temp;
   }

   // Sorts the elements in the range a[low..high].
   private static void insertionsort(int[] a, int low, int high) {
      for (int i = low+1; i <= high; ++i) {
         int temp = a[i], j = i-1;          // Save the current element
         while (j >= low && a[j] > temp) {  // Shift all elements greater than it to the right
            a[j+1] = a[j];
            --j;
         }
         a[j+1] = temp;                     // Insert the current element into the correct spot
      }
   }

// ***Modify this method to randomly shuffle the array before sorting***
   public static void quicksort(int[] a) {
	   int high = a.length - 1;
	   for (int i = 0; i <= a.length - 1; ++i) { // version 1 j = random integer such that i ≤ j ≤ n-1 Swap a[i] and a[j] 
		   int low = i;
		   int random = rand.nextInt(high-low+1)+low; // Generates a random integer between low and high (inclusive)
		   swap(a,i,random);
	   }
      quicksort(a, 0, a.length-1);
      
   }
   // ***Modify this method to use insertion sort for small subarrays***
   // Sorts the elements in the range a[low..high].
   private static void quicksort(int[] a, int low, int high) {
	   if (high - low <= 1024) {
  			insertionsort(a,low,high);
	   }
	   else {
	   if (low >= high)
         return;
      int pivotIndex = partition(a, low, high); // Partition the array into two halves
      quicksort(a, low, pivotIndex);            // Sort the left half
      quicksort(a, pivotIndex+1, high);         // Sort the right half
   	
   }
	   return;
   }

   // Partitions the array and returns the pivot index such that a[low..pivotIndex] <= pivotValue <= a[pivotIndex+1..high].
   // Note that pivotValue will not necessarily be at a[pivotIndex].
   // This implementation uses Hoare's partitioning scheme.
   private static int partition(int[] a, int low, int high) {
      int pivotValue = a[low];               // Choose the leftmost element in the range as the pivot
      int i = low-1, j = high+1;
      while(true) {
         do {i++;} while(a[i] < pivotValue); // Find an element >= pivot
         do {j--;} while(a[j] > pivotValue); // Find an element <= pivot
         if (i < j) swap(a, i, j);           // If they're in the wrong order, swap them
         else return j;
      }
   }

   // Returns true if the array is sorted.  Otherwise returns false.
   private static boolean isSorted(int[] a) {
      for (int i = 0; i < a.length-1; ++i)
         if (a[i] > a[i+1])
            return false;
      return true;
   }

   // Sorts each row of the 2D array and measures the average runtime and number of comparisons.
   private static void runTest(int[][] arrays) {
      long start = System.currentTimeMillis();
      for (int i = 0; i < NUM_ARRAYS; ++i) // Sort each array
         quicksort(arrays[i]);
      long end = System.currentTimeMillis();
      System.out.println("Average runtime in seconds: " + (end-start) / 1000.0 / NUM_ARRAYS);
      
      for (int i = 0; i < NUM_ARRAYS; ++i) { // Verify that all arrays are sorted
         if (!isSorted(arrays[i])) {
            System.out.println("The arrays are not sorted");
            return;
         }
      }
   }

   static final int ARRAY_SIZE = 100000, NUM_ARRAYS = 100;
   public static void main(String[] args) {
      // Create the arrays
      int[][] randomArray = new int[NUM_ARRAYS][ARRAY_SIZE];
      int[][] sortedArray = new int[NUM_ARRAYS][ARRAY_SIZE];
      
      // Fill the arrays with values
      for (int i = 0; i < NUM_ARRAYS; ++i) {
         for (int j = 0; j < ARRAY_SIZE; ++j) {
            randomArray[i][j] = rand.nextInt();
            sortedArray[i][j] = j;
         }
      }

      System.out.println("***Random arrays***");
      runTest(randomArray);

      System.out.println("\n***Already-sorted arrays***");
      runTest(sortedArray);
   }
}
