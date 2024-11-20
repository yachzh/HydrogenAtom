package zr.yach;

public class NumJava {
   // T = dx/2.0*[f(0)+2f(1)+...+2f(n-2)+f(n-1)]
   public static double trapz(double[] f, double[] x) {
      int numOfPoints = x.length;
      double dx = x[1] - x[0];
      double sum = dx / 2.0 * f[0];

      for (int i = 1; i < numOfPoints - 1; i++) {
         sum += dx * f[i];
      }

      sum += dx / 2.0 * f[numOfPoints - 1];
      return sum;
   }
}
