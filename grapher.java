import java.awt.*;
import javax.swing.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.Scanner; 

public class grapher extends JPanel {
     private ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
     private String func = "";
     private int width = 400;
     private int height = 400;

     // returns output of user defined function with x and y as parameters
     private double evalFunc(double x, double y){
          try {
               // create a evaluable string for js engine, and return the output
               return (double) engine.eval(func.replace("x", "("+Double.toString(x)+")").replace("y", "("+Double.toString(y)+")"));
          }
          catch (ScriptException e) {
               System.out.println(e);
               return 0;
          }
     }

     // main graphing function
     public void draw(Graphics g) {
          setBackground(Color.WHITE);


          // declare used vars
          // 2d array of points for the graph, index[x][y] = f(x,y)
          double[][] points = new double[width][height];
          double x_scaled;
          double y_scaled;
          double current_point = 0.0;
          float hue = 0.0f;
          for (int x = 0; x < width; x++) {
               for (int y = 0; y < height; y++) {
                    x_scaled = x / 100.0 - 2.0;
                    y_scaled = y / 100.0 - 2.0;

                    // interpolation routine to calculate graph quicker
                    if ((x == 0 || y == 0) || (y % 5 == 0 || x % 5 == 0)){
                         current_point = evalFunc(x_scaled, y_scaled);

                         hue = (float) (current_point + 4);
                    }
                    points[x][y] = current_point;



                    // render point
                    drawPoint(x, height-y, g);
                    g.setColor(Color.getHSBColor(1.0f - (hue / 10.0f), 1, 1));
               }

          }

          // draw contour lines
          double contour_res = 0.0;
          double max = -100000;
          double min = 100000;
          
          // find min and max of the function
          for (int x = 0; x < width; x++) {
               for (int y = 0; y < height; y++) {
                    if (points[x][y] > max) {
                         max = points[x][y];
                    }
                    if (points[x][y] < min) {
                         min = points[x][y];
                    }
               }
          }

          // iterate from mix to max of step size contour_res and render points that are close to the current step
          System.out.println(min + " " + max);
          contour_res = (max - min) / 5.0;
          System.out.println(contour_res);

          g.setColor(Color.WHITE);
          for (double i = min; i < max; i+=contour_res) {
               for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                         if (points[x][y] < i + contour_res / 40 && points[x][y] > i - contour_res / 40){
                              drawPoint(x,height-y, g);
                         }
                    }
               }
          }


     }

     public void drawPoint(int x, int y, Graphics g) {
          g.drawLine(x, y, x, y);
     }

     public static void main(String[] args) {
          grapher graph = new grapher();
          // get user input for function
          System.out.print("Enter your function (in JavaScript syntax) or press enter for default: ");

          Scanner inp = new Scanner(System.in);
          graph.func = inp.nextLine();
          inp.close();

          if (graph.func.equals("")) {
               //graph.func = "x*x-y*y";
               graph.func = "Math.cos(x)*Math.sin(y)";
          }

          // awt setup
          JFrame f = new JFrame("3D Grapher");
          f.add(graph);
          f.setSize(graph.width, graph.height); // set window size
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // quit program when you close frame
          f.setLocationRelativeTo(null); // centers frame
          f.setVisible(true);
          graph.draw(f.getGraphics());
          
     }
}