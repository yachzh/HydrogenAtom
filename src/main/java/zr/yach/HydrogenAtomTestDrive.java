package zr.yach;

/**
 * Describe class <code>HydrogenAtomTestDrive</code> here.
 *
 * @author <a href="mailto:yachao.zhang@pku.edu.cn">Yachao Zhang</a>
 * @version November 23, 2023 12:55 PM
 */
public class HydrogenAtomTestDrive {
   public static void main(String[] args) {
      double rmin = 0;
      double rmax = 5.0;
      int numOfPoints = 1000;
      HydrogenAtom atom = new HydrogenAtom(rmin, rmax, numOfPoints);
      atom.solve();
      System.out.println("Ground state energy: " + atom.getEigenvalue() + " Hartree");

      double[] position = atom.getPosition();
      // ground state wave function
      double[] wave = atom.getEigenstate(); // numerical solution
      JavaWrite jw = new JavaWrite(position, wave);
      jw.saveData("numHAtom.dat");
      System.out.println("Numerical wave function written to numHAtom.dat");
      double[] anaPhi = atom.getAnalyticPhi(); // analytical form
      JavaWrite jw2 = new JavaWrite(position, anaPhi);
      jw2.saveData("anaHAtom.dat");
      System.out.println("Analytical wave function written to anaHAtom.dat");
   }
}
