public class NumJava {
   // T = dx/2.0*[f(x0)+2f(x1)+...+2f(xn-1)+f(xn)]
   public static double trapz(double[] fx, double[] x) {
      int numOfPoints = x.length;
      double dx = x[1] - x[0];
      double sum = dx / 2.0 * fx[0];

      for (int i = 1; i < numOfPoints - 1; i++) {
         sum += dx * fx[i];
      }

      sum += dx / 2.0 * fx[numOfPoints - 1];
      return sum;
   }

   // public static double secant(UserFunction f, double e1, double e2, double eps) {
   //    while (Math.abs(e1 - e2) > eps) {
   //       double wf1 = f.value(e1);
   //       double wf2 = f.value(e2);
   //       double e3 = e2 - wf2 * (e2 - e1) / (wf2 - wf1);
   //       e1 = e2;
   //       e2 = e3;
   //    }

   //    return e1;
   // }
}
