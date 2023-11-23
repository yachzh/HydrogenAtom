import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.terminal.PostscriptTerminal;
import com.panayotis.gnuplot.JavaPlot;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import com.panayotis.gnuplot.style.Style;

public class JavaWrite {
   double[] x, y;

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

   public void saveFigure(String xLabel, String yLabel, String figureName) {
      JavaPlot testPlot = new JavaPlot();
      PostscriptTerminal epsf = new PostscriptTerminal(figureName);
      epsf.setColor(true);
      testPlot.setTerminal(epsf);
      testPlot.getAxis("x").setLabel(xLabel);
      testPlot.getAxis("y").setLabel(yLabel);
      PlotStyle myPlotStyle = new PlotStyle();
      myPlotStyle.setStyle(Style.LINES);
      myPlotStyle.setLineWidth(2);
      myPlotStyle.setLineType(NamedPlotColor.RED);

      double[][] myArray = new double[x.length][2];

      for (int j = 0; j < x.length; j++) {
         myArray[j][0] = x[j];
         myArray[j][1] = y[j];
      }

      DataSetPlot testDataSetPlot = new DataSetPlot(myArray);
      testDataSetPlot.setPlotStyle(myPlotStyle);
      testDataSetPlot.setTitle(yLabel);
      testPlot.addPlot(testDataSetPlot);

      try {
         testPlot.plot();
      } catch (Exception ex) {
         System.out.println(ex.toString());
      }
   }

   public void plot() {
      double[][] myArray = new double[x.length][2];

      for (int j = 0; j < x.length; j++) {
         myArray[j][0] = x[j];
         myArray[j][1] = y[j];
      }

      JavaPlot testPlot = new JavaPlot();
      PlotStyle myPlotStyle = new PlotStyle();
      myPlotStyle.setStyle(Style.LINES);
      myPlotStyle.setLineWidth(2);
      myPlotStyle.setLineType(NamedPlotColor.GREEN);

      DataSetPlot testDataSetPlot = new DataSetPlot(myArray);
      testDataSetPlot.setPlotStyle(myPlotStyle);
      testDataSetPlot.setTitle("testPlot");
      testPlot.addPlot(testDataSetPlot);

      try {
         testPlot.plot();
      } catch (Exception ex) {
         System.out.println(ex.toString());
      }
   }
}
