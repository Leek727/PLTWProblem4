import java.awt.*;
import javax.swing.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.Scanner;
import java.util.Random;

public class grapher extends JPanel {
     private ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
     private String func = "";
     private int width = 800;
     private int height = 400;
     private int rangeX = 4;
     private int rangeY = 4;

     // evaluate all points of a function
     private String evalFunc() {
          // js script to evaluate function over a range of points
          // not a virus i promise
          System.out.println("Calculating points...");
          System.out.println("Running JS...");
          String calculateScript = "var points = []; var x = 0; var y = 0;                             " +
                    "for (var i = 0; i < " + rangeX * 100 + "; i++) {                     " +
                    "points[i] = [];                                                " +
                    "for (var j = 0; j < " + rangeY * 100 + "; j++) {                 " +
                    "x = i / 100 - " + rangeX / 2 + "; y = j / 100 -" + rangeY / 2 + "; " +
                    "points[i][j] = eval(\"" + func + "\")                      " +
                    "}                                                              " +
                    "}                                                                  " +
                    "points.toString();                                                 ";

          try {
               // create a evaluable string for js engine, and return the output

               return (String) engine.eval(calculateScript);
          } catch (ScriptException e) {
               System.out.println(e);
               return "";
          }
     }


     // graphing / rendering function
     public void draw(Graphics g) {
          setBackground(Color.WHITE);

          // declare used vars
          // 2d array of points for the graph, index[x][y] = f(x,y)
          double[][] points = new double[width][height];
          String pts = evalFunc();
          String[] vals = pts.split(",");
          int counter = 0;
          for (int x = 0; x < rangeX * 100; x++) {
               for (int y = 0; y < rangeY * 100; y++) {
                    points[x][y] = Double.valueOf(vals[counter]);
                    counter++;
               }
          }

          // render points and draw contour lines
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

          System.out.println("Rendering Top View...");
          // render 2d point array
          float hueScale = 0.0f;
          double pt = 0.0;

          for (int x = 0; x < width / 2; x++) {
               for (int y = 0; y < height; y++) {
                    pt = points[x][y];

                    // render point
                    drawPoint(x, height - y, g);
                    hueScale = ((float) (pt - min) / (float) (max - min + 2));

                    g.setColor(Color.getHSBColor(1.0f - hueScale, 1, 1));
               }

          }

          System.out.println("Rendering contour lines...");
          // iterate from mix to max of step size contour_res and render points that are
          // close to the current step
          System.out.println("Max value: " + Math.round(max * 100) / 100 + "\nMin value: " + Math.round(min * 100) / 100);
          contour_res = (max - min) / 5.0;

          g.setColor(Color.WHITE);
          for (double i = min; i < max; i += contour_res) {
               for (int x = 0; x < width/2; x++) {
                    for (int y = 0; y < height; y++) {
                         if (points[x][y] < i + contour_res / 100 && points[x][y] > i - contour_res / 100) {
                              drawPoint(x, height - y, g);
                         }
                    }
               }
          }

          System.out.println("Rendering Side View...");
          
          // draw side view
          for (int x = 0; x < width / 2; x++) {
               for (int y = 0; y < height; y++) {
                    pt = points[x][height - 1 - y];

                    // render point
                    // System.out.println(height - (int)(pt*25.0));
                    drawPoint(x + width / 2, height / 2 - (int) (pt * 25.0), g);

                    hueScale = ((float) (pt - min) / (float) (max - min + 2));
                    g.setColor(Color.getHSBColor(1.0f - hueScale, 1, 1));
               }
          }

          System.out.println("Drawing Axes...");
          // draw axes
          g.setColor(Color.BLACK);
          for (int i = 0; i < width; i += width / 4) {
               g.drawLine(i, 0, i, height);
          }
          g.drawLine(0, height / 2, width, height / 2);

          // draw text
          g.setFont(new Font("Helvetica", Font.BOLD, 17)); 
          g.setColor(Color.BLACK);
          g.drawString("Height Map Top View", width/4 - 100, 80);
          g.drawString("Height Map Side View", width - width/4 - 100, 80);
     
          // axes test
          g.setFont(new Font("Helvetica", Font.BOLD, 12));
          g.drawString("x", 20, height/2 - 10);
          g.drawString("y", width/4 + 10, 50);

          g.drawString("x", 20 + width/2, height/2 - 10);
          g.drawString("y", width/4 + 10 + width/2, 50);
     
          System.out.println("Done!");
     }

     public void drawPoint(int x, int y, Graphics g) {
          g.drawLine(x, y, x, y);
     }

     public static void main(String[] args) {
          grapher graph = new grapher();

          // get user input for function
          System.out.println("     ==========================================================");
          System.out.println("Welcome to the best 3d graphing program ever!\nThe left side is a top view of the graph, and the right side is a side view.\nThe color gradient represents the height of the function at that point.");
          System.out.println("     ==========================================================");
          System.out.println("The function is inputted as a JavaScript expression of x and y.\nFor example, x*x+y*y would be a paraboloid.\nBe sure to use Math.cos(x) instead of cos(x) and Math.sin(x) instead of sin(x).");
          System.out.println("     ==========================================================");
          System.out.print("Enter your function (in JavaScript syntax) or press enter for a cool random function: ");

          Scanner inp = new Scanner(System.in);
          graph.func = inp.nextLine();
          inp.close();

          if (graph.func.equals("")) {
               String coolFunctions[] = {"Math.cos(x)*Math.sin(y)", "x*x-y*y", "x*x+y*y", "Math.sin(2 * (x*x + y*y))"};
               String coolFuncNames[] = {"waves", "saddle", "paraboloid", "ripple"};
               int coolIndex = new Random().nextInt(coolFunctions.length);
               graph.func = coolFunctions[coolIndex];
               System.out.println("\n\n\nChosen function: " + graph.func + "\n" + "Function name: " + coolFuncNames[coolIndex] + "\n");
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
