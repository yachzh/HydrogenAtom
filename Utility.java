import org.jblas.DoubleMatrix;
import Jama.Matrix;

public class Utility {
   public static void stringToDouble(String[] strArray, double[] numArray) {
      for (int i = 0; i < strArray.length; i++) {
         numArray[i] = Double.parseDouble(strArray[i]);
      }
   }

   public static void printArray(double[][] array) {
      Matrix outMatrix = new Matrix(array);
      outMatrix.print(12, 6);
   }

   public static void printDoubleMatrix(DoubleMatrix matrix) {
      double[][] array = matrix.toArray2();
      Matrix outMatrix = new Matrix(array);
      outMatrix.print(12, 6);
   }

   public static double[] getColumn(Matrix A, int cIndex) {
      int n = A.getRowDimension();
      double[] columnVector = new double[n];

      for (int i = 0; i < n; i++) {
         columnVector[i] = A.get(i, cIndex);
      }

      return columnVector;
   }
}
