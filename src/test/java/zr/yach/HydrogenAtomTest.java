package zr.yach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class HydrogenAtomTest {

   @Test
   public void testGetEigenvalue() {
      double rmin = 0;
      double rmax = 5.0;
      int numOfPoints = 1000;
      HydrogenAtom atom = new HydrogenAtom(rmin, rmax, numOfPoints);
      atom.solve();
      System.out.println("Ground state energy: " + atom.getEigenvalue() + " Hartree");

      // Check if the ground state energy is as expected
      double expectedGroundStateEnergy = -0.499278;
      Assertions.assertEquals(expectedGroundStateEnergy, atom.getEigenvalue(), 1e-6);
   }
}
