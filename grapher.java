import java.awt.*;  
import javax.swing.*; 

public class grapher extends JPanel
{
     public void paintComponent (Graphics g)
     {
          super.paintComponent(g);
          setBackground(Color.black);
          setForeground(Color.cyan);

          for (int x = 0; x < 400; x++){
            for (int y = 0; y < 400; y++){
                double x_scaled = x / 100.0 - 2.0;
                double y_scaled = y / 100.0 - 2.0;

                double func = x_scaled * x_scaled - y_scaled * y_scaled;
                
                float hue = (float)(func + 4);
                drawPoint(x, y, g);
                g.setColor(Color.getHSBColor(hue / .0f, 1,1));
            }
          }
          //Color[] gradientColors = {Color.BLUE, Color.green, Color.yellow, Color.red};
     }

     public void drawPoint(int x, int y, Graphics g){
        g.drawLine(x, y, x, y);
     }

     public static void main(String[ ] args)
     {
          //Establish the frame
          grapher d = new grapher( );
          JFrame f = new JFrame("Title of the frame");
          f.add(d); //adds DisplayGraphics to the frame for viewing
          f.setSize(400,400); //sets the coordinate size of frame
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes frame
          f.setLocationRelativeTo(null); //centers the frame on screen          
          f.setVisible(true); //makes frame appear on screen
     }
}

