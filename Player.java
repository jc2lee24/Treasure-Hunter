import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {

    private int x;
    private int y;

    private Image landPlayer, waterPlayer;

    public Player(int x, int y){
        this.x = x; 
        this.y = y;

        try{
            landPlayer = ImageIO.read(new File("landModel.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            waterPlayer = ImageIO.read(new File("waterModel.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void drawLand(Graphics g){
        g.setColor(Color.yellow);
        g.drawImage(landPlayer, x, y, null);
    }
    
    public void drawWater(Graphics g){
        g.setColor(Color.yellow);
        g.drawImage(waterPlayer, x, y, null);
    }
}
