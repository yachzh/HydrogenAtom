package zr.yach;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JavaWrite {
   private double[] x, y;

   public JavaWrite(double[] x, double[] y) {
      this.x = x.clone();
      this.y = y.clone();
   }

   public void saveData(String fileName) {
      PrintWriter fw;

      try {
         fw = new PrintWriter(fileName);

         for (int i = 0; i < x.length; i++) {
            fw.println(x[i] + "   " + y[i]);
         }

         fw.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
   }
}
