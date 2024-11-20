package zr.yach;
import org.jblas.DoubleMatrix;
import org.jblas.Eigen;

/**
 * Variational calculation for the H atom
 *
 * @author <a href="mailto:yachao.zhang@pku.edu.cn">Yachao Zhang</a>
 * @version November 23, 2023 12:55 PM
 */
public class HydrogenAtom {
   private int n;             // number of Gaussian type orbitals (GTOs), exp(-a*r^2)
   private double[] aVector;  // exponents of GTOs
   private double[] cVector;  // linear coefficients of GTOs
   private DoubleMatrix H, S; // Hamiltonian and overlap matrix
   private DoubleMatrix C, E; // solution of HC = ESC
   private int qn = 0;        // quantum number
   private double[] rVector;  // position
   private double[] phi;      // electron wavefunction
   private double[] rho;      // electron density
   private int numOfPoints;   // number of grid points

   public HydrogenAtom(double rmin, double rmax, int numOfPoints) {
      this.numOfPoints = numOfPoints;
      phi = new double[numOfPoints];
      rho = new double[numOfPoints];
      double h = (rmax - rmin) / (numOfPoints - 1);
      rVector = new double[numOfPoints];

      for (int i = 0; i < numOfPoints; i++) {
         rVector[i] = rmin + i * h;
      }

      System.out.println("Starting value of r: r0 = " + rmin + " Bohr");
      System.out.println("Truncation of r: rmax =  " + rmax + " Bohr");
      System.out.println("Number of grid points: " + numOfPoints);

      n = 4;         // use four GTOs

      System.out.println("\n" + n + " Gaussian type orbitals are used\n");

      // J. M. Thijssen, Computational Physics (second edition), P35
      aVector = new double[n];
      aVector[0] = 13.00773;
      aVector[1] = 1.962079;
      aVector[2] = 0.444529;
      aVector[3] = 0.1219492;

      for (int i = 0; i < n; i++) {
         System.out.println("alpha" + i + " = " + aVector[i]);
      }

      System.out.println("");
   }

   public void setQuantumNumber(int qn) {
      this.qn = qn;
   }

   public double[] getPosition() {
      return rVector;
   }

   public void setOverlap() {
      double[][] overlapM = new double[n][n];;

      for (int p = 0; p < n; p++) {
         for (int q = 0; q <= p; q++) {
            overlapM[p][q] = Math.pow(Math.PI / (aVector[p] + aVector[q]), 1.5);

            if (p != q) {
               overlapM[q][p] = overlapM[p][q]; // S is a symmetric matrix
            }
         }
      }

      S = new DoubleMatrix(overlapM);
      System.out.println("Overlap Matrix S");
      Utility.printDoubleMatrix(S);

   }

   public void setHamiltonian() {
      double[][] hamiltonM = new double[n][n];;

      for (int p = 0; p < n; p++) {
         for (int q = 0; q <= p ; q++) {
            double kinetic = 3 * aVector[p] * aVector[q] * Math.pow(Math.PI, 1.5) / Math.pow(aVector[p] + aVector[q], 2.5);
            double coulomb = -2 * Math.PI / (aVector[p] + aVector[q]);
            hamiltonM[p][q] = kinetic + coulomb;

            if (p != q) {
               hamiltonM[q][p] = hamiltonM[p][q]; // H is a symmetric matrix
            }
         }
      }

      H = new DoubleMatrix(hamiltonM);
      System.out.println("Hamiltonian Matrix H");
      Utility.printDoubleMatrix(H);

   }

   /**
    * Solve the generalized eigenvalue problem
    * HC = ESC
    *
    */
   public void solve() {
      setOverlap();     // update S
      setHamiltonian();    // update H
      DoubleMatrix[] eig = Eigen.symmetricGeneralizedEigenvectors(H, S);
      C = eig[0];
      E = eig[1];
      System.out.println("Eigenvalue Matrix E");
      Utility.printDoubleMatrix(E);
      System.out.println();
      System.out.println("Coefficient Matrix C");
      Utility.printDoubleMatrix(C);
   }

   public double getEigenvalue() {
      return E.get(qn, 0);
   }

   public double[] getEigenstate() {
      updatePhi();
      normalize();
      return phi;
   }

   public double[] getDensity() {
      updatePhi();
      normalize();
      updateRho();
      return rho;
   }

   public double linearCombination(double r) {
      double wave = 0;

      for (int i = 0; i < n; i++) {
         wave += cVector[i] * Math.exp(-aVector[i] * r * r);
      }

      return wave;
   }

   public void updatePhi() {
      cVector = C.getColumn(qn).toArray();

      for (int i = 0; i < numOfPoints; i++) {
         phi[i] = linearCombination(rVector[i]);
         rho[i] = phi[i] * phi[i];
      }
   }

   public void updateRho() {
      for (int i = 0; i < numOfPoints; i++) {
         rho[i] = phi[i] * phi[i];
      }
   }

   public void normalize() {
      double intRho = NumJava.trapz(rho, rVector);
      double factor = 1 / Math.sqrt(intRho);

      for (int i = 0; i < numOfPoints; i++) {
         phi[i] *= factor;
      }
   }

   public double[] getAnalyticPhi() {
      double[] waveFunction = new double[numOfPoints];
      double[] electronDensity = new double[numOfPoints];

      for (int i = 0; i < numOfPoints; i++) {
         waveFunction[i] = 2 * Math.exp(-rVector[i]);
         electronDensity[i] = waveFunction[i] * waveFunction[i];
      }

      double intDensity = NumJava.trapz(electronDensity, rVector);
      double normFactor = 1 / Math.sqrt(intDensity);

      for (int i = 0; i < numOfPoints; i++) {
         waveFunction[i] *= normFactor;
      }

      return waveFunction;
   }
}
