
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.lang.Math;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import static java.lang.Character.*;

public class BobLabst
{
	public static void main(String...args)
	{
		JFrame j = new JFrame();  //JFrame is the window; window is a depricated class
		MyPanelb m = new MyPanelb();
		j.setSize(m.getSize());
		j.add(m); //adds the panel to the frame so that the picture will be drawn
			      //use setContentPane() sometimes works better then just add b/c of greater efficiency.

      //adds mouse listener
      j.addMouseListener(m);
      //adds key listener
      j.addKeyListener(m);
      
		j.setVisible(true); //allows the frame to be shown.

		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes the dialog box exit when you click the "x" button.
	}

}







class MyPanelb extends JPanel implements ActionListener, MouseListener, KeyListener
{
	//instance variables
	private Timer time;
	private int x,y, rx, ry;
	private int add;
   private int change;
   private int[] a;
   
	
	MyPanelb()
	{
		time = new Timer(15, this); //sets delay to 15 millis and calls the actionPerformed of this class.
		setSize(2000, 1500);
		setVisible(true); //it's like calling the repaint method.
		time.start();
		add=1;
		x=y=600;
	   change = 1;
      a = new int[]{2000, 800, 500, 1200};
      
	}
	
   //resets the screen and redraws the objects
	public void paintComponent(Graphics g)
	{  
      drawBack(g);
      
      //draws objects
		drawSq(g,x,y);
		drawC(g,x,y);
      drawM(g, rx, ry);
      
	}
	public void drawBack(Graphics g){
      //draws grass
      g.setColor(Color.GREEN);
		g.fillRect(0,0,2000,250);
      g.fillRect(0,450,2000,250);
      g.fillRect(0,900,2000,250);
      
      //draws road
      g.setColor(Color.BLACK);
      g.fillRect(0,250,2000,200);
      g.fillRect(0,700,2000,200);
      
      //draws the lines in the roads
      g.setColor(Color.YELLOW);
      
      g.fillRect(0,325,100,50);
      g.fillRect(300,325,100,50);
      g.fillRect(600,325,100,50);
      g.fillRect(900,325,100,50);
      g.fillRect(1200,325,100,50);
      g.fillRect(1500,325,100,50);
      g.fillRect(1800,325,100,50);
      
      g.fillRect(0,775,100,50);
      g.fillRect(300,775,100,50);
      g.fillRect(600,775,100,50);
      g.fillRect(900,775,100,50);
      g.fillRect(1200,775,100,50);
      g.fillRect(1500,775,100,50);
      g.fillRect(1800,775,100,50);
      
      
   }
   //redraws the square to make it seem like the square is moving
   public void drawSq(Graphics g, int x, int y){
      
      int ny = 825;
      for(int k = 0; k < a.length;k++){
         int nx = (int) (Math.random()*25);
         a[k] = a[k] - nx;
         if (a[k] < 50){
            a[k] = 2000;
         }
         g.setColor(Color.BLUE);
         g.fillRect(a[k], ny, 100, 75);
         g.setColor(Color.WHITE);
         g.fillRect(a[k]+5, ny+5, 10, 65);
         if (k % 2 == 0){
            ny -= 125;
         }else{
            ny -= 325;
         }
         
      }
   }
   
   //redraws the imported image to make it seem like the square is moving
   public void drawC(Graphics g, int x, int y){
      try
		{
			Image car = ImageIO.read(new File("p.png"));
			g.drawImage(car, 150+x, 155+y, null);
		}
		catch(Exception e)	{}



   }
   
   //redraws the oval at the location of where the mouse was pressed
	public void drawM(Graphics g, int x, int y){
      g.setColor(Color.YELLOW);
		g.fillOval(x-20,y-20,50,50);
      g.setColor(Color.BLACK);
      g.fillOval(x-5, y, 5, 5);
      g.fillOval(x+5, y, 5, 5);
      g.fillRect(x-3, y+ 10, 20, 2);
      
      

   }
   //checks to see if the object has moved outside the bounds and changes the x and y values
	public void actionPerformed(ActionEvent e)
	{
		
		if (x >=1200)
			x=1;
		if (y >= 500)
         y=1;	
		x+=add;
		y+=add;
      /*
		if(x==200 && y==200)
			add*=-1;
		if(x==10 && y==10)
			add*=-1;		
	`*/
		repaint();  // call to paintComponent
	}
	
   //sets the location of the oval to where the mouse was clicked
   public void mouseClicked(MouseEvent e)
	{
		rx = e.getX();
		
      playSound("sound.wav");
	}
   
   //these 4 methods override the methods in the Mouselistener class to not cause compile time error
   public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

   //it sets the background color to the key that the user press
   public void keyPressed(KeyEvent e)
	{
		  int code = e.getKeyCode();

		  switch (toUpperCase(code))
		  {
		  	case 'W': ry-=10; break;
		  	case 'S': ry+=10; break;
		  	//case '3': change = 3; break;
		  }

   }
   
   
   //these 2 methods override the methods in the keylistener class to not cause compile time error
   public void keyReleased(KeyEvent e)	{}
	public void keyTyped(KeyEvent e){}
   
   public static synchronized void playSound(String soundFile) {
   new Thread(new Runnable() {
   //plays sounds
     public void run() {
       try {
         File f = new File("./" + soundFile);
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
         Clip clip = AudioSystem.getClip();
         clip.open(audioIn);
         clip.start();
 
       } catch (Exception e) {
         System.err.println(e.getMessage());
       }
     }
   }).start();
   }  
}