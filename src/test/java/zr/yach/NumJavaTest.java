package zr.yach;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class NumJavaTest {
   // Example function: f(x) = x^2
   private double function(double x) {
      return x * x;
   }

   @Test
   public void testIntegration() {
      // Define the integration bounds
      double lowerBound = 0.0;
      double upperBound = 1.0;

      // Number of points for the trapezoidal rule
      int n = 10000; // More points for better accuracy
      double[] x = new double[n];
      double[] f = new double[n];

      // Populate x and f arrays
      for (int i = 0; i < n; i++) {
         x[i] = lowerBound + (upperBound - lowerBound) * i / (n - 1);
         f[i] = function(x[i]);
      }

      // Calculate the integral using NumJava's trapz method
      double actual = NumJava.trapz(f, x);
      System.out.println("integral of x^2 in [0,1]");
      System.out.println("NumJava::trapz " + actual);

      // Calculate the integral using Apache Commons Math's SimpsonIntegrator
      UnivariateFunction univariateFunction = new UnivariateFunction() {
         @Override
         public double value(double x) {
            return function(x);
         }
      };

      // Create a SimpsonIntegrator
      SimpsonIntegrator integrator = new SimpsonIntegrator();
      double expected = integrator.integrate(n, univariateFunction, lowerBound, upperBound);
      System.out.println("Apache::simpson " + expected);

      // Compare results
      Assertions.assertEquals(expected, actual, 1e-6); // Use a tolerance for floating-point comparison
   }

}


