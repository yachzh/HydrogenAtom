package zr.yach;
import org.jblas.DoubleMatrix;

public class Utility {
   // Prints a 2D array (matrix) in a formatted way
   public static void printArray(double[][] array) {
      for (double[] row : array) {
         for (double value : row) {
            System.out.printf("%12.6f ", value);
         }

         System.out.println();
      }

      System.out.println();
   }

   // Prints a jblas DoubleMatrix in a formatted way
   public static void printDoubleMatrix(DoubleMatrix matrix) {
      double[][] array = matrix.toArray2();
      printArray(array);
   }
}

